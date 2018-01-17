package com.blgroup.jkl.service.impl;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.blgroup.b2b.common.utils.JsonUtils;
import com.blgroup.jkl.bean.InsertPurchaseReqDto;
import com.blgroup.jkl.service.PurchaseService;
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
public class PurchaseServiceImpl   implements PurchaseService{
	
	private static Logger logger = LoggerFactory.getLogger(PurchaseServiceImpl.class);

	/**
	 * 提交采购单
	 */
	@Override
	public String insertInsertPurchaseReqDto(InsertPurchaseReqDto dto) {
		String returnStr = null;
		Properties prop = null;
		try {
			prop = PropertiesUtil.getProperties("application.properties");
			returnStr = HttpUtilsV2.doPost(prop.getProperty("serviceDomain")+HttpUrl.PURCHASE_INSERT.getCode(), JsonUtils.object2JsonString(dto));
		}catch (IOException e){
			logger.error("method:getProduct(), 获取配置文件错误");
		}catch (ServiceException e) {
			logger.error("method:getProduct(), HttpPost url is {}, method is {}, paramMap is {} ----------调用接口错误---------------", prop.getProperty("serviceDomain"), HttpUrl.GOOD_DETAIL.getCode(), dto);
		}
		logger.debug("method:getProduct(), return:{}, end...", returnStr);
		return returnStr;
	}
	

}
