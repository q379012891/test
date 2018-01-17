package com.blgroup.jkl.core;

import com.blgroup.jkl.util.WebDateTimeUtil;

/**
 * 使用常量类
 * @author liuqian
 * @version yyc
 * @since yyc
 * @see 下午6:04:35
 */
public class Constants {
    
    /** "令牌"属性名称 */
    public static final String TOKEN_ATTRIBUTE_NAME = "yaotoken";

    /** "令牌"Cookie名称 */
    public static final String TOKEN_COOKIE_NAME = "yaotoken";

    /** "令牌"参数名称 */
    public static final String TOKEN_PARAMETER_NAME = "yaotoken";
    
    /** "令牌"参数名称 */
    public static final String SERVERNAME_PARAMETER_NAME = "yyc.yao.jd.com";
    
    /** 失效时间 */
    public static final int SESSION_REDIS_TIMEOUT = WebDateTimeUtil.getSessionTime();
    
    /** 失效时间 */
    public static final int USER_COOKIE_TIMEOUT = WebDateTimeUtil.getCookieTime();
    
    /** 最近浏览失效时间 */
    public static final int REVIEW_COOKIE_TIMEOUT = WebDateTimeUtil.getReviewCookieTime();
     
    /**
     *  redis存储userSession
     */
    public static final String USER_SESSION_ATTR_NAME = "userSession";
    
    /**
     * redis存储sessionId
     */
    public static final String SESSION_REDIS_ATTR_NAME = "sessionId";
    /**
     * 是否删除 0：未删除
     */
    public static final String IS_DELETE_NO = "0";
    
}
