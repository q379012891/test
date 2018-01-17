package com.blgroup.jkl.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 日志封装类
 * 
 * @version 1.0
 * 
 * @createDate 2012-8-2
 * 
 * @modifyDate 2012-8-14
 */
public class LogTrace {
    private Log log;

    /**
     * 构造
     * 
     * @param clazz
     *            类对象
     */
    @SuppressWarnings("rawtypes")
    public LogTrace(Class clazz) {
        log = LogFactory.getLog(clazz);
    }

    /**
     * 构造
     * 
     * @param name
     *            日志名（类名）
     */
    public LogTrace(String name) {
        log = LogFactory.getLog(name);
    }

    /**
     * 如果允许输出debug级别的日志，则输出
     * 
     * @param message
     *            要输出的信息
     */
    public void debug(Object message) {
        if (isDebugEnabled()) {
            log.debug(message);
        }
    }

    /**
     * 如果允许输出debug级别的日志，则输出
     * 
     * @param message
     *            要输出的信息
     * @param t
     *            异常對象
     */
    public void debug(Object message, Throwable t) {
        if (isDebugEnabled()) {
            log.debug(message, t);
        }
    }

    /**
     * 如果允许输出info级别的日志，则输出
     * 
     * @param message
     *            要输出的信息
     */
    public void info(Object message) {
        if (isInfoEnabled()) {
            log.info(message);
        }
    }

    /**
     * 如果允许输出info级别的日志，则输出
     * 
     * @param message
     *            要输出的信息
     * @param t
     *            异常對象
     */
    public void info(Object message, Throwable t) {
        if (isInfoEnabled()) {
            log.error(message, t);
        }
    }

    /**
     * 如果允许输出warn级别的日志，则输出
     * 
     * @param message
     *            要输出的信息
     */
    public void warn(Object message) {
        if (isWarnEnabled()) {
            log.warn(message);
        }
    }

    /**
     * 如果允许输出warn级别的日志，则输出
     * 
     * @param message
     *            要输出的信息
     * @param t
     *            异常對象
     */
    public void warn(Object message, Throwable t) {
        if (isWarnEnabled()) {
            log.warn(message, t);
        }
    }

    /**
     * 如果允许输出error级别的日志，则输出
     * 
     * @param message
     *            要输出的信息
     */
    public void error(Object message) {
        if (isErrorEnabled()) {
            log.error(message);
        }
    }

    /**
     * 如果允许输出error级别的日志，则输出
     * 
     * @param message
     *            要输出的信息
     * @param t 异常对象      
     */
    public void error(Object message, Throwable t) {
        if (isErrorEnabled()) {
            log.error(message, t);
        }
    }

    /**
     * 是否允许输出debug级别信息
     * 
     * @return 是否允许
     */
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    /**
     * 是否允许输出info级别信息
     * 
     * @return 是否允许
     */
    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    /**
     * 是否允许输出warn级别信息
     * 
     * @return 是否允许
     */
    public boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }

    /**
     * 是否允许输出error级别信息
     * 
     * @return 是否允许
     */
    public boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }
    
    /**
     * Get log
     * @return log
     */
    public Log getLog() {
        return log;
    }
}
