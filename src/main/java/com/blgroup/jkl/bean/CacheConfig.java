package com.blgroup.jkl.bean;

public class CacheConfig {
	private String host;
	private String port;
	private String maxActive;
	private String initActive;
	private String maxIde;
	private String maxWait;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(String maxActive) {
		this.maxActive = maxActive;
	}
	public String getInitActive() {
		return initActive;
	}
	public void setInitActive(String initActive) {
		this.initActive = initActive;
	}
	public String getMaxIde() {
		return maxIde;
	}
	public void setMaxIde(String maxIde) {
		this.maxIde = maxIde;
	}
	public String getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(String maxWait) {
		this.maxWait = maxWait;
	}

}
