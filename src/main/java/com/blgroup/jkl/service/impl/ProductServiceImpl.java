/**
* Title: ITokenService.java
* Description:
* @author 姜钟明
* @date 2016年9月23日
* @version 1.0
*/
package com.blgroup.jkl.service.impl;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.blgroup.jkl.service.ProductService;
import com.blgroup.jkl.util.HttpUrl;
import com.blgroup.jkl.util.HttpUtilsV2;
import com.blgroup.jkl.util.PropertiesUtil;
import com.blgroup.jkl.util.utilBean.ServiceException;

/**
 * Title: ITokenService
 * Description:从redis中取token
 * @author 姜钟明
 * @date 2016年9月24日
 */
@Service
public class ProductServiceImpl implements ProductService{
	private static Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    /**
     * 获取商品信息
     * @return
     */
	public String getProduct(Map<Object, Object> map){
		log.debug("method:getProduct(), start...");
		String returnStr = null;
		Properties prop = null;
		try {
			prop = PropertiesUtil.getProperties("application.properties");
			returnStr = HttpUtilsV2.HttpPost(prop.getProperty("serviceDomain"), HttpUrl.GOOD_DETAIL.getCode(),map);
		}catch (IOException e){
			log.error("method:getProduct(), 获取配置文件错误");
		}catch (ServiceException e) {
			log.error("method:getProduct(), HttpPost url is {}, method is {}, paramMap is {} ----------调用接口错误---------------", prop.getProperty("serviceDomain"), HttpUrl.GOOD_DETAIL.getCode(), map);
		}
		log.debug("method:getProduct(), return:{}, end...", returnStr);
		return returnStr;
	}
	
}
