package com.blgroup.jkl.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blgroup.jkl.core.SystemConextConstans;
/**
 * @author JZM48
 *	微信上下文，暂时没有用到，作为扩展用
 */
@SuppressWarnings("all")
public class WeiXinSystemContextServlet  extends  HttpServlet  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5188368837272098392L;

	/**
	 * 应用路径
	 */
    private static String appPath;
    
    
    /**
     * 资源路径
     */
    private static String resourcePath;

	private static boolean initLock = false;
    
    private static Map<String, String> weixinSystem = new HashMap<String, String>(){

		@Override
		public String put(String key, String value) {
			if(initLock){
				throw new RuntimeException(" please don't change the weixinSystem ");
			}
			return super.put(key, value);
		}
    	
    };
    
    
    private static final  Logger logger = LoggerFactory.getLogger(WeiXinSystemContextServlet.class);
    
	@Override
	public void init() throws ServletException {
		 InputStream in =  this.getClass().getClassLoader().getResourceAsStream("system.properties");
		 Properties properties  =  new Properties();
		 try {
			properties.load(in);
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} 
		 
		 
		for(Object key:properties.keySet()){
				 if(key != null){
					 String value = properties.getProperty(key.toString());
					 weixinSystem.put(key.toString(), value);
					 if(logger.isDebugEnabled()){
						 logger.debug("system context  config "+key+" = "+value);	 
					 }
				 }
		}
		appPath = properties.getProperty(SystemConextConstans.APP_PATH);
		
		initLock = true ;
		super.init();
		
	}


	public static String getAppPath() {
		return appPath;
	}
	


	public static String getResourcePath() {
		return resourcePath;
	}

	public static Map<String, String> getWeixinSystem() {
		return weixinSystem;
	}



	public static boolean isInitLock() {
		return initLock;
	}

}
