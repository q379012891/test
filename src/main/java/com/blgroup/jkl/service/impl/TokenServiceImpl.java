package com.blgroup.jkl.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.blgroup.jkl.bean.SocialAccount;
import com.blgroup.jkl.service.ITokenService;
import com.blgroup.jkl.util.ThreadSocailAccountCacheUtil;

@Service(value = "tokenServiceImpl")
public class TokenServiceImpl implements ITokenService {
	private Logger log = LoggerFactory.getLogger(TokenServiceImpl.class);
	
	/**
	 * 获取token值，如果redis不存在重新获取 暂时未使用
	 * 
	 * @return
	 */
	public String getToken() {
		return	this.getSocialAccount().getAccessToken();
	}
	
	@Override
	public SocialAccount getSocialAccount() {
		SocialAccount  socialAccount = new SocialAccount();
		//首先从一级缓存中获取当前的社交账户信息
		socialAccount = ThreadSocailAccountCacheUtil.getSocialAccount();
		if(null != socialAccount){
			log.debug("-------token:"+socialAccount.getAccessToken()+" -----------------");
		}
		return socialAccount;
	}
}
