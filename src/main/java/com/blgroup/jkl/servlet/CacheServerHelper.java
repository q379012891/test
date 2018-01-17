package com.blgroup.jkl.servlet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.JedisPool;

/**
 * 
 * 
 * @version 1.0
 * 
 * @createDate 2012-9-18
 * 
 * @modifyDate 2012-9-18
 */
public class CacheServerHelper {


    /**
     * redis服务bean
     */
    public static JedisPool jedisPool;

    /**
     * 初始化缓存服务bean
     */
    static {
        ApplicationContext appCtx = new ClassPathXmlApplicationContext(
                "classpath:/spring-context-master.xml");
        jedisPool = (JedisPool) appCtx.getBean("jedisPool");
    }

}
