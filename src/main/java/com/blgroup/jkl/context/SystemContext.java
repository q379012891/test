package com.blgroup.jkl.context;

public interface SystemContext {
	/**
	 * 获取应用的路径
	 * @return
	 */
	public String getAppPath();
	
	/**
	 * 获取资源的路径
	 * @return
	 */
	public String getResourcePath();

	/**
	 * 当前系统是否为生产系统
	 * @return
	 */
	public boolean getStatusProduction();

	// 固定是H5路径
	public String getH5AppPath();
	
}
