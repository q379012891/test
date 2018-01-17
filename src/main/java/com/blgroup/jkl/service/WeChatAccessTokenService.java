/**
* Title: ITokenService.java
* Description:
* @author 姜钟明
* @date 2016年9月23日
* @version 1.0
*/
package com.blgroup.jkl.service;

import java.util.Map;

/**
 * Title: ITokenService
 * Description:从redis中取token
 * @author 姜钟明
 * @date 2016年9月24日
 */
public interface WeChatAccessTokenService {
    /**
     * 获取access_token
     * @return
     */
	public String getToken(Map<Object, Object> map, String appId, String secret);
	
}
