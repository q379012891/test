package com.blgroup.jkl.bean;

import java.io.Serializable;

public class ReturnDto implements Serializable{
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 4744725659892347560L;
	private boolean success;//接口返回结果标识，true为成功，false为失败 (基于业务是否异常来判断)
	private String msg;//错误返回时的错误描述;
	private String resCode;//返回码
	private Object obj;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
}
