package com.blgroup.jkl.bean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 线程变量
 */
public class AppThread {
	/**
	 * 当前用户线程变量
	 */

	private static ThreadLocal<HttpServletRequest> requestVariable = new ThreadLocal<HttpServletRequest>();
	/**
	 * 当前站点线程变量
	 */
	private static ThreadLocal<HttpServletResponse> responseVariable = new ThreadLocal<HttpServletResponse>();
	
	public static HttpServletRequest getRequest(){
		return requestVariable.get();
	}
	
	public static void setRequest(HttpServletRequest req){
		  requestVariable.set(req);
	}
	
	public static HttpServletResponse getResponse(){
		return responseVariable.get();
	}
	
	public static void setResponse(HttpServletResponse res){
		responseVariable.set(res);
	}
}
