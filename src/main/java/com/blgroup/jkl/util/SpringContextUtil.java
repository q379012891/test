package com.blgroup.jkl.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by XJM on 2016-07-12.
 */
@Component
public class SpringContextUtil implements ApplicationContextAware{

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext=applicationContext;
    }
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    public static Object getBean(String beanName) throws BeansException{
        return applicationContext.getBean(beanName);
    }
}