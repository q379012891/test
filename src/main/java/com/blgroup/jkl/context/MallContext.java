package com.blgroup.jkl.context;

public interface MallContext {
	/**
	 * 获取用户上下文
	 * @return
	 */
	public UserContext getUserContext();
	
	/**
	 * 获取系统上下文
	 * @return
	 */
	public SystemContext getSystemContext();
	

}
