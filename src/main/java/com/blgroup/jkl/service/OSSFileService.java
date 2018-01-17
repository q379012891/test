package com.blgroup.jkl.service;

import java.io.InputStream;

import com.blgroup.b2b.common.entity.ResponseData;


public interface OSSFileService {

	String rspFileUpLoad(String operation,String identify,String url,String fileName);
	
	String uploadFile(String operation,String identify,InputStream inputStream,String fileName);
	
}
