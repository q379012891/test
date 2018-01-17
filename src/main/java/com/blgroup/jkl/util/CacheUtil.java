package com.blgroup.jkl.util;


import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blgroup.jkl.bean.CacheConstants;
import com.blgroup.jkl.bean.TransactionCache;
import com.blgroup.jkl.core.SystemConextConstans;

import net.sf.json.JSONNull;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/** 缓存/Redis
 * @author 姜钟明
 *
 */
@SuppressWarnings("all")
public class CacheUtil {
	public static JedisPool jedisPool = null;
	public static Object jedisPoolLock = new Object();
	private static JsonConfig jsonConfig = null;
	private static Logger logger = LoggerFactory.getLogger(CacheUtil.class);
	public static CacheConstants CACHE_KEY_CONSTANTS =  new CacheConstants();
	
	static {
		Properties properties=null;
		try {
			properties = PropertiesUtil.getProperties("application.properties");
		} catch (IOException e) {
			logger.error("读取配置文件--redis.properties--- 错误！！！！！！！");
		}
	      String host = (String)properties.get(SystemConextConstans.REDIS_HOST_IP);
	      String port = (String)properties.get(SystemConextConstans.REDIS_PORT);
	      String maxWait = (String)properties.get(SystemConextConstans.REDIS_MAX_WAIT);
	      String maxIdle = (String)properties.get(SystemConextConstans.REDIS_MAX_IDLE);
	      String maxActive = (String)properties.get(SystemConextConstans.REDIS_MAX_ACTIVE);
	      String password = (String)properties.get(SystemConextConstans.REDIS_PASSWORD);
	      
		  //JEDIS 连接缓存服务器，以后会配置到maven文件中

		  JedisPoolConfig config = new JedisPoolConfig();
	      if(maxIdle==null){
	    	  config.setMaxIdle(10);
	      }else{
	    	  config.setMaxIdle(Integer.valueOf(maxIdle));
	      }
	      
	      if(maxWait==null){
	    	  config.setMaxWaitMillis(5000);
	      }else{
	    	  config.setMaxWaitMillis(Integer.valueOf(maxWait));
	      }
          config.setTestOnBorrow(true);
          //处理JSON对NULL值处理的问题
          if(password==null||password.isEmpty()){
        	  jedisPool = new JedisPool(config, host, Integer.valueOf(port));
          }else{
        	  jedisPool = new JedisPool(config, host, Integer.valueOf(port),300,password);
          }
          
          jsonConfig = new JsonConfig();
          jsonConfig.registerDefaultValueProcessor(Integer.class, new DefaultValueProcessor() {
			@Override
			public Object getDefaultValue(Class arg0) {
				return JSONNull.getInstance(); 
			}
		});
        jsonConfig.registerDefaultValueProcessor(Long.class, new DefaultValueProcessor() {
			@Override
			public Object getDefaultValue(Class arg0) {
				return JSONNull.getInstance(); 
			}
		});
         jsonConfig.registerDefaultValueProcessor(Date.class, new DefaultValueProcessor() {
			@Override
			public Object getDefaultValue(Class arg0) {
				return JSONNull.getInstance(); 
			}
		});
        jsonConfig.registerDefaultValueProcessor(String.class, new DefaultValueProcessor() {
			@Override
			public Object getDefaultValue(Class arg0) {
				return JSONNull.getInstance(); 
			}
		});
        jsonConfig.registerDefaultValueProcessor(Boolean.class, new DefaultValueProcessor() {
			@Override
			public Object getDefaultValue(Class arg0) {
				return JSONNull.getInstance(); 
			}
		});
        jsonConfig.registerDefaultValueProcessor(Short.class, new DefaultValueProcessor() {
			@Override
			public Object getDefaultValue(Class arg0) {
				return JSONNull.getInstance(); 
			}
		});
	}
	/**
	 * 缓存实体对象
	 * @param key
	 * @param entity
	 * @param second
	 */
	public static void setObject(String key,Serializable entity,int second){
		Jedis  jedis = null;
		try {
			byte[] value = SystemUtil.objectToByte(entity);
			jedis = jedisPool.getResource();
			jedis.set(key.getBytes(), value);
			jedis.expire(key, second);
			jedis.quit();
		} catch (Exception e) {
			logger.debug(" do setObject methode  get a exception"+e.getMessage()+" by key "+key+", please check the class or value");
		}finally{
			if(jedis!=null){
				jedisPool.returnResource(jedis);
			}
		}
		
	}
	
	/**
	 * 重新设置缓存时间
	 * @param key
	 * @param second
	 */
	public static void exprise(String key ,int second){
		Jedis  jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedisPool.getResource().expire(key, second);
			jedis.quit();
		} finally{
			if(jedis!=null){
				jedisPool.returnResource(jedis);
			}
		}
		
	}
	
	
	public static void addTo(String key,String value){
		Jedis  jedis = null;
		try{
			jedis = jedisPool.getResource();
			jedis.append(key, value);
			jedis.quit();
		}finally{
				if(jedis!=null){
					jedisPool.returnResource(jedis);
				}
			}
	}
	
	/**
	 * 缓存文本对象
	 * @param key
	 * @param value
	 * @param second
	 */
	public static void set(String key,String value,int second){
		Jedis  jedis = null;
		try{
			jedis = jedisPool.getResource();
			jedis.set(key, value);
			jedis.expire(key, second);
			jedis.quit();
		}finally{
				if(jedis!=null){
					jedisPool.returnResource(jedis);
				}
			}
	}
	/**
	 *获取缓存中的文本信息
	 * @param key
	 * @return
	 */
	public static String getString(String key){
		Jedis  jedis = null;
		try{
			jedis = jedisPool.getResource();
			String value =  jedis.get(key);
			jedis.quit();
			return value;
		}finally{
				if(jedis!=null){
					jedisPool.returnResource(jedis);
				}
	    }
	
	}
	
	/**
	 *删除缓存中的文本信息
	 * @param key
	 * @return
	 */
	public static void remove(String key){
		Jedis  jedis =  null;
		try{
			jedis = jedisPool.getResource();
			jedis.del(key);
			jedis.quit();
		}finally{
				if(jedis!=null){
					jedisPool.returnResource(jedis);
				}
			}
	}
	
	/**
	 * 从缓存中获取实体对象
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static <T> T  getObject(String key) {
		byte [] bytes =  getBytes(key);
		try {
			if(bytes==null){
				return null;
			}
			return SystemUtil.byteToObject(bytes);
		} catch (Exception e) {
			logger.error("do getObject methode get Exception message "+e.getMessage()+" by key, please check the class version or remove the cache value");
			return null;
		}
	}
	
	public static byte[] getBytes(String key){
		Jedis  jedis = null;
		try{
			jedis = jedisPool.getResource();
			byte[] bytes =  jedis.get(key.getBytes());
			jedis.quit();
			return bytes;
		}finally{
				if(jedis!=null){
					jedisPool.returnResource(jedis);
				}
			}
		
	}
	
	/**
	 * 获取事物缓存
	 * @return
	 */
	public static TransactionCache getTransaction(){
		return new TransactionCache(jedisPool) ;
	}
	
	
	
	
	
	
	

}
