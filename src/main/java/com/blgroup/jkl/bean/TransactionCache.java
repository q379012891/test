package com.blgroup.jkl.bean;

import java.io.IOException;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blgroup.jkl.util.SystemUtil;

import net.sf.json.JsonConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;
@SuppressWarnings("all")
public class TransactionCache {
	private Transaction transaction = null;
	private static  JsonConfig jsonConfig = null;
	private static JedisPool jedisPool;
	public static CacheConstants CACHE_KEY_CONSTANTS = new CacheConstants();
	private static Logger logger = LoggerFactory.getLogger(TransactionCache.class);
	private Jedis jedis = null;
	public TransactionCache(JedisPool jedisPool){
		this.jedisPool = jedisPool;
		this.jedis = jedisPool.getResource();
		this.transaction = jedis.multi();
		jsonConfig = new JsonConfig();
	}
	
	public void commit(){
		try {
			transaction.exec();
			transaction = null;
		} finally{
			jedisPool.returnResource(jedis);
		}
	}
	
	
	/**
	 * 缓存实体对象
	 * @param key
	 * @param entity
	 * @param second
	 */
	public  void setObject(String key,Serializable entity,int second){
		try {
			byte[] value = SystemUtil.objectToByte(entity);
			this.transaction.set(key.getBytes(), value);
		} catch (Exception e) {
			logger.debug(" do setObject methode  get a exception"+e.getMessage()+" by key "+key+", please check the class or value");
		}
		
	}
	
	
	/**
	 * 重新设置缓存时间
	 * @param key
	 * @param second
	 */
	public  void exprise(String key ,int second){
		transaction.expire(key, second);
	}
	
	
	public  void addTo(String key,String value){
		transaction.append(key, value);
	}
	
	public  void addObjectTo(String key,Serializable value){
		byte[] entity;
		try {
			entity = SystemUtil.objectToByte(value);
			transaction.append(key.getBytes(), entity);
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}
		
	}
	
	/**
	 * 缓存文本对象
	 * @param key
	 * @param value
	 * @param second
	 */
	public  void setString(String key,String value,int second){
		transaction.set(key, value);
		if(second>0){
			transaction.expire(key, second);
		}
	}
	/**
	 *获取缓存中的文本信息
	 * @param key
	 * @return
	 */
	public  String getString(String key){
		Response<String> responses =transaction.get(key);
		transaction.exec();
		return responses.get();
	}
	
	/**
	 *删除缓存中的文本信息
	 * @param key
	 * @return
	 */
	public  void remove(String key){
		transaction.del(key);
	}
	
	/**
	 * 从缓存中获取实体对象
	 * @param key
	 * @param clazz
	 * @return
	 */
	public  <T> T  getObject(String key) {
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
	
	public  byte[] getBytes(String key){
			byte[] bytes =  transaction.get(key.getBytes()).get();
			return bytes;
		
	}
	

}
