/**
* Title: ITokenService.java
* Description:
* @author 姜钟明
* @date 2016年9月23日
* @version 1.0
*/
package com.blgroup.jkl.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blgroup.jkl.redis.RedisClientTemplate;
import com.blgroup.jkl.service.WeChatAccessTokenService;
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
public class WeChatAccessTokenServiceImpl implements WeChatAccessTokenService {
	private static Logger log = LoggerFactory.getLogger(WeChatAccessTokenServiceImpl.class);
	@Autowired
	RedisClientTemplate redisClientTemplate;
    /**
     * 获取access_token
     * @return
     */
	@Override
	public String getToken(Map<Object, Object> map, String appId, String secret) {
		log.debug("method:getToken(), start...");
		String accessToken = null;

		String returnStr = null;

		try {
			returnStr = HttpUtilsV2.HttpPost(HttpUrl.WE_CHAT_OPEN_URL.getCode(), HttpUrl.WE_CHAT_OPEN_TOKEN.getCode(),
					map);
		} catch (ServiceException e) {

		}
		if (StringUtils.isNotBlank(returnStr) && !returnStr.contains(PropertiesUtil.ERRCODE)) {
			// 成功
			JSONObject jsonObj = JSONObject.fromObject(returnStr);
			accessToken = jsonObj.get(PropertiesUtil.ACCESS_TOKEN).toString();
			// 写入缓存
			redisClientTemplate.set("accessToken", accessToken, 7100);
		}
		log.debug("method:getToken(), return:{}, end...", accessToken);
		return accessToken;
	}
	
}
