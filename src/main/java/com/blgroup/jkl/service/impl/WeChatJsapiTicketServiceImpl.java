/**
* Title: ITokenService.java
* Description:
* @author 姜钟明
* @date 2016年9月23日
* @version 1.0
*/
package com.blgroup.jkl.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.blgroup.jkl.service.WeChatJsapiTicketService;
import com.blgroup.jkl.util.HttpUrl;
import com.blgroup.jkl.util.HttpUtilsV2;
import com.blgroup.jkl.util.PropertiesUtil;
import com.blgroup.jkl.util.utilBean.ServiceException;

import net.sf.json.JSONObject;

/**
 * Title: ITokenService
 * Description:从redis中取token
 * @author 姜钟明
 * @date 2016年9月24日
 */
@Service
public class WeChatJsapiTicketServiceImpl implements WeChatJsapiTicketService{
	private static Logger log = LoggerFactory.getLogger(WeChatAccessTokenServiceImpl.class);
    /**
     * 获取JsapiTicket
     * @return
     */
	@Override
	public String getJsapiTicket(String accessToken){
		log.debug("method:getJsapiTicket(), start...");
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put(PropertiesUtil.TYPE, PropertiesUtil.JS_API);
		map.put(PropertiesUtil.ACCESS_TOKEN, accessToken);
		String returnStr = null;
		String jsToken = null;
		try {
			returnStr = HttpUtilsV2.HttpPost(HttpUrl.WE_CHAT_OPEN_URL.getCode(), HttpUrl.WE_CHAT_OPEN_TICKET.getCode(),
					map);
		} catch (ServiceException e) {

		}
		if (StringUtils.isNotBlank(returnStr)) {
			// 成功
			JSONObject jsonObj = JSONObject.fromObject(returnStr);
			if (PropertiesUtil.SYSTEM_SUCCESS.equals(String.valueOf(jsonObj.get(PropertiesUtil.ERRCODE)))) {
				jsToken = jsonObj.get(PropertiesUtil.TICKET).toString();
			}
		}
		log.debug("method:writeJsToken(), return:{}, end...", jsToken);
		return jsToken;
	}
	
}
