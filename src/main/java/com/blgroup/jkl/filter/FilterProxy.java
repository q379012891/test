package com.blgroup.jkl.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * 基于spring filter代理
 * @author 姜钟明
 *
 */
public class FilterProxy implements Filter{
    private Filter proxy;
	@Override
	public void destroy() {
		proxy.destroy();
		
	}
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		proxy.doFilter(arg0, arg1, arg2);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		ServletContext context = config.getServletContext(); 
		ApplicationContext ac =  WebApplicationContextUtils.getWebApplicationContext(context); 
		String targetBeanName = config.getInitParameter("targetBeanName");
		Object proxy = ac.getBean(targetBeanName);
		this.proxy = (Filter)proxy;
		this.proxy.init(config);
	}

}
