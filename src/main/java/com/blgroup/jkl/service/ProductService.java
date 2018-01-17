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
public interface ProductService {
    /**
     * 获取商品信息
     * @return
     */
	public String getProduct(Map<Object, Object> map);
	
}
