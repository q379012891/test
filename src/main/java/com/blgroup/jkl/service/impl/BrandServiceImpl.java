package com.blgroup.jkl.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.blgroup.jkl.service.IBrandService;
import com.blgroup.jkl.util.HttpUrl;
import com.blgroup.jkl.util.HttpUtilsV2;
import com.blgroup.jkl.util.PropertiesUtil;
import com.blgroup.jkl.util.utilBean.ServiceException;
@Service
public class BrandServiceImpl implements IBrandService {
	private static Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

	public String getBrand() {
		String returnStr = null;
		Properties prop = null;
		HashMap<Object, Object>  map  =   new  HashMap<Object, Object>();
		try {
			prop = PropertiesUtil.getProperties("application.properties");
			//http://10.201.129.177:7210
			returnStr = HttpUtilsV2.HttpPost(prop.getProperty("serviceDomain"), HttpUrl.BRAND_ALL.getCode(),map);
		}catch (IOException e){
			logger.error("method:getProduct(), 获取配置文件错误");
		}catch (ServiceException e) {
			logger.error("method:getProduct(), HttpPost url is {}, method is {}, paramMap is {} ----------调用接口错误---------------", prop.getProperty("serviceDomain"), HttpUrl.BRAND_ALL.getCode(), map);
		}
		logger.debug("method:getProduct(), return:{}, end...", returnStr);
		return returnStr;
	}

}
