package com.blgroup.jkl.context;

import java.util.Date;

public interface UserContext {
	/**
	 * 获取用户token
	 * @return
	 */
	public String getMemberToken();
	
	public String getBrrowserType();
	/**
	 * 当前微信用户的openId
	 * @return
	 */
	public String getOpenId();
	
	/**
	 * 获取会员名称
	 * @return
	 */
	public String getMemberName();
	/**
	 * 获取会员id
	 * @return
	 */
	public String getMemberId();
	
	/**
	 * 过期时间
	 * @return
	 */
	public Date expireTime();
	
	/**
	 * 会员等级
	 * @return
	 */
	public String getMemberLevelCode();
	/**
	 * 
	 * @return
	 */
	public String getCpsCode();
	
	/**
	 * 用户登录用户名
	 * @return
	 */
	public String getPin();
	
	/**
	 * 用户手机
	 * @return
	 */
	public String getMobile();
	
}
