package com.blgroup.jkl.context;


public  class SystemContextBuilder  {
	/**
	 * 微信上下文值环境
	 */
	protected  static ThreadLocal<MallContext> weixinContext = new ThreadLocal<MallContext>(); 
	
	/**
	 * 获取当前上下文的信息
	 * @return
	 */
	public  MallContext build() {
		return weixinContext.get();
	}
}
