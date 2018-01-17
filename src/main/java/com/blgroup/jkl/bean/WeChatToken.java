package com.blgroup.jkl.bean;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

public class WeChatToken implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -917903746978590024L;

	private String token;
	
	private Date expiredTime;
	
	public WeChatToken(){}
	
	public WeChatToken(String token, Date expiredTime){
		
		this.token = token;
		this.expiredTime = expiredTime;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @param expiredTime the expiredTime to set
	 */
	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	/**
	 * @return the expiredTime
	 */
	public Date getExpiredTime() {
		return expiredTime;
	}
	
	public static enum WeChatTokenCd {

		AccessToken, JsAPIToken, CardToken;

		@JsonValue
	    public String toJson() {
	        return name();
	    }

	    @JsonCreator
	    public static WeChatTokenCd fromJson(String value) {
	        return WeChatTokenCd.valueOf(value);
	    }
	}
}



