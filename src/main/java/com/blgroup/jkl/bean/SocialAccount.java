package com.blgroup.jkl.bean;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;


public class SocialAccount implements Serializable {
	/**
	 * @author 姜钟明
	 */
	private static final long serialVersionUID = 1L;
	
	private String appId;
	
	private String appsecret;
	//token
	private String accessToken;
	
	final static Properties prop;

	static {
		prop = new Properties();
		String path = SocialAccount.class.getClassLoader()
				.getResource("application.properties").getPath();
		InputStream is = null;
		try {
			is = new FileInputStream(path);
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppsecret() {
		return appsecret;
	}
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
 
}
