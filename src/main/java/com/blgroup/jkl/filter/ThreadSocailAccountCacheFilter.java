package com.blgroup.jkl.filter;


import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.blgroup.jkl.bean.AppThread;
import com.blgroup.jkl.util.HttpUrl;
import com.blgroup.jkl.util.HttpUtilsV2;
import com.blgroup.jkl.util.SystemUtil;
import com.blgroup.jkl.util.ThreadSocailAccountCacheUtil;
/**
 * 微信基础数据的一级缓存，避免一次请求多次调用社交运营平台
 * @author 姜钟明
 *
 */
@SuppressWarnings("all")
public class ThreadSocailAccountCacheFilter implements Filter {
	
	private Logger logger = LoggerFactory.getLogger(ThreadSocailAccountCacheFilter.class);
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response,
			FilterChain filter) throws IOException, ServletException {
		if(((HttpServletRequest)request).getRequestURI().contains(".html")){
			String logId =   UUID.randomUUID().toString();
			AppThread.setRequest((HttpServletRequest)request);
			AppThread.setResponse((HttpServletResponse)response);
			//打印业务日志,请求参数，时间（JSON无法打印）
			MDC.put("logId",logId);
			logger.debug("***********************************************业务开始**********************************************");
			String URI = ((HttpServletRequest)request).getRequestURI();
			logger.debug("请求URI："+URI);
			logger.debug("时间："+SystemUtil.getCurrentTimeStr());
			StringBuilder body  = new StringBuilder();
			logger.error("请求body内容:"+body);
			Map<String,String[]> param = request.getParameterMap();
			StringBuilder paramList = new StringBuilder();
			for(String key : param.keySet()){
				String[]  vlaues = param.get(key);
				paramList.append(key+":");
				paramList.append("[");
				for(String valueItem :vlaues){
					paramList.append(valueItem);
				}
				paramList.append("],");
			}
			logger.error("请求参数列表：{"+paramList.toString()+"}");
			
		}
		try {
			//当请求开始先清空当前的缓存
			ThreadSocailAccountCacheUtil.reset();
			filter.doFilter(request, response);   
			//请求结束先清空当前的缓存
			ThreadSocailAccountCacheUtil.reset();
			
			
		} catch (Exception e) {
			StringBuilder msg = new StringBuilder("×××××××××××××××××××××××业务异常信息××××××××××××××××××××××××××××××××");
			e.printStackTrace();
			msg.append("exception type:"+e.getClass().getName()+"\n");
			msg.append("exception msg: "+e.getMessage()+"\n");
			StackTraceElement[] stls = e.getStackTrace();
			for(StackTraceElement stl:stls){
				msg.append("class:"+stl.getClassName()+"."+stl.getMethodName()+"():"+stl.getLineNumber()+"\n");
			}
			msg.append("×××××××××××××××××××××××异常描述结束××××××××××××××××××××××××××××××××");
			logger.error(msg.toString());
			throw new IOException();
		}finally{
			MDC.clear();
		}
		if(((HttpServletRequest)request).getRequestURI().contains(".html")){
			logger.debug("***********************************************业务结束**********************************************");
		}
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
	
	

}
