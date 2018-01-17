package com.blgroup.jkl.service.impl;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.blgroup.jkl.service.IItemService;
import com.blgroup.jkl.util.HttpUrl;
import com.blgroup.jkl.util.HttpUtilsV2;
import com.blgroup.jkl.util.PropertiesUtil;
import com.blgroup.jkl.util.utilBean.ServiceException;

/**
 * 
 * 商品接口实现类
 * @ClassName: GoodsServiceImpl 
 * @author XJM29
 * @date 2017年3月15日 上午11:02:50
 * @version V2.0 
 *
 */
@Service
public class ItemServiceImpl   implements IItemService{
	
	private static Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
	
	public String demo() {
//		Map<Object, Object> map  =  new  HashMap<>();
//		logger.info("demo------------------->>");
//		logger.debug("method:getProduct(), start...");
//		String returnStr = null;
//		Properties prop = null;
//		try {
//			prop = PropertiesUtil.getProperties("application.properties");
//			returnStr = HttpUtilsV2.HttpPost(prop.getProperty("serviceDomain"), HttpUrl.GOOD_BARCODE_LIKE.getCode(),map);
//		}catch (IOException e){
//			logger.error("method:getProduct(), 获取配置文件错误");
//		}catch (ServiceException e) {
//			logger.error("method:getProduct(), HttpPost url is {}, method is {}, paramMap is {} ----------调用接口错误---------------", prop.getProperty("serviceDomain"), HttpUrl.GOOD_DETAIL.getCode(), map);
//		}
//		logger.debug("method:getProduct(), return:{}, end...", returnStr);
		return null;
	}

	/**
	 * 
	 * 方法描述：   方法总结
	 * 业务逻辑说明  ：
	 * 1、处理…………<br/>
	 * 2、处理…………<br/>
	 * 3、处理…………<br/>
	 * @Title: getGood 
	 * @date 2017年3月15日 下午1:26:40
	 * @author XJM29
	 * @modifier 
	 * @modifydate 
	 * @param map
	 * @return
	 */
	public String getItem(Map<Object, Object> map) {
		String returnStr = null;
		Properties prop = null;
		try {
			prop = PropertiesUtil.getProperties("application.properties");
			//http://10.201.129.177:7210
			logger.info("开始调用参数++++++++++++++"+prop.getProperty("serviceDomain")+"_____"+ HttpUrl.ITEM_ALL.getCode());
			returnStr = HttpUtilsV2.HttpPost(prop.getProperty("serviceDomain"), HttpUrl.ITEM_ALL.getCode(),map);
		}catch (IOException e){
			logger.error("method:getProduct(), 获取配置文件错误");
		}catch (ServiceException e) {
			logger.error("method:getProduct(), HttpPost url is {}, method is {}, paramMap is {} ----------调用接口错误---------------", prop.getProperty("serviceDomain"), HttpUrl.GOOD_DETAIL.getCode(), map);
		}
		logger.debug("method:getProduct(), return:{}, end...", returnStr);
		return returnStr;
	}

}
