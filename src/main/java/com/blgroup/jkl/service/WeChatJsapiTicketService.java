/**
* Title: ITokenService.java
* Description:
* @author 姜钟明
* @date 2016年9月23日
* @version 1.0
*/
package com.blgroup.jkl.service;

/**
 * Title: ITokenService
 * Description:从redis中取token
 * @author 姜钟明
 * @date 2016年9月24日
 */
public interface WeChatJsapiTicketService {
    /**
     * 获取JsapiTicket
     * @return
     */
	public String getJsapiTicket(String accessToken);
	
}
