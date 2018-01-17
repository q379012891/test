package com.blgroup.jkl.util;

import com.blgroup.jkl.context.MallContext;
import com.blgroup.jkl.context.SystemContextBuilder;

public class SystemContextUtil {
	/**
	 * 上下文变量信息
	 */
	private static  SystemContextBuilder contextParam = new SystemContextBuilder();
	/**
	 * 获取上下文信息
	 * @return
	 */
	public static MallContext getMallContext() {
		return contextParam.build();
	}
		
}
