/**
* Title: ITokenService.java
* Description:
* @author 姜钟明
* @date 2016年9月23日
* @version 1.0
*/
package com.blgroup.jkl.service;

import com.blgroup.jkl.bean.SocialAccount;

/**
 * Title: ITokenService
 * Description:从redis中取token
 * @author 姜钟明
 * @date 2016年9月24日
 */
public interface ITokenService {
    /**
     * 获取access_token
     * @return
     */
	public String  getToken();
	
	/**
	 * 获取社交账户信息
	 * @return
	 */
	public SocialAccount getSocialAccount();
	
}
