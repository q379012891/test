package com.blgroup.jkl.util;

import com.blgroup.jkl.bean.SocialAccount;

public final class ThreadSocailAccountCacheUtil {
	private static final  ThreadLocal<SocialAccount> cache = new ThreadLocal<SocialAccount>();
	
    public final static  SocialAccount getSocialAccount(){
    	return cache.get();
    }
    
    public final static void setSocialAccount(SocialAccount socialAccount){
    	cache.set(socialAccount);
    }
    
    public final static void reset(){
    	cache.set(null);
    }
    
    

}
