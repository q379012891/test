package com.blgroup.jkl.util;

/**
 * 后端服务地址以及context
 * 
 * @Class Name HttpUrl
 */
public enum HttpUrl {

	// ----------支撑系统URL---------------------
	WE_CHAT_OPEN_URL("https://api.weixin.qq.com"), // 微信开放平台URL
	WE_CHAT_OPEN_MP_URL("https://mp.weixin.qq.com"),
	WE_CHAT_OPEN_FILE_URL("http://file.api.weixin.qq.com"), // 微信开放平台多媒体URL
	WE_CHAT_OPEN_URL_NEW("http://api.weixin.qq.com"), WE_CHAT_OPEN_TOKEN("cgi-bin/token"), // 生成access_token
	WE_CHAT_OPEN_TOKEN_VALIDATE("cgi-bin/getcallbackip"), // 微信服务器IP获取-AcccessToken心跳测试
	WE_CHAT_OPEN_TICKET("cgi-bin/ticket/getticket"), // 生成jsapi_ticket

	WE_CHAT_OPEN_CREATE_MENU("cgi-bin/menu/create"), // 自定义菜单创建
	WE_CHAT_OPEN_GET_MENU("cgi-bin/menu/get"), // 自定义菜单查询
	WE_CHAT_OPEN_DELETE_MENU("cgi-bin/menu/delete"), // 自定义菜单删除
	WE_CHAT_OPEN_CREATE_GROUPS("cgi-bin/groups/create"), // 创建用户分组
	WE_CHAT_OPEN_GET_GROUPS("cgi-bin/groups/get"), // 查询所有分组
	WE_CHAT_OPEN_GETID_GROUPS("cgi-bin/groups/getid"), // 查询用户所在分组
	WE_CHAT_OPEN_UPDATE_GROUPS("cgi-bin/groups/update"), // 修改用户分组名
	WE_CHAT_OPEN_UPDATE_MEMBERS_GROUPS("cgi-bin/groups/members/update"), // 移动用户分组
	WE_CHAT_OPEN_BATCHUPDATE_MEMBERS_GROUPS("cgi-bin/groups/members/batchupdate"), // 批量移动用户分组
	WE_CHAT_OPEN_DELETE_GROUPS("cgi-bin/groups/delete"), // 删除分组
	WE_CHAT_OPEN_USER_INFO("cgi-bin/user/info"), // 获取用户基本信息
	WE_CHAT_OPEN_GET_USER("cgi-bin/user/get"), // 获取用户列表
	WE_CHAT_OPEN_UPDATE_REMARK_INFO("cgi-bin/user/info/updateremark"), // 设置备注名
	WE_CHAT_OPEN_UPLOAD_MEDIA("cgi-bin/media/upload"), // 新增临时素材
	WE_CHAT_OPEN_GET_MEDIA("cgi-bin/media/get"), // 获取临时素材
	WE_CHAT_OPEN_ADDNEWS_MATERIAL("cgi-bin/material/add_news"), // 新增永久图文素材
	WE_CHAT_OPEN_ADDMATERIAL_MATERIAL("cgi-bin/material/add_material"), // 新增永久其他类型素材
	WE_CHAT_OPEN_GETMATERIAL_MATERIAL("cgi-bin/material/get_material"), // 获取永久素材
	WE_CHAT_OPEN_DELMATERIAL_MATERIAL("cgi-bin/material/del_material"), // 删除永久素材
	WE_CHAT_OPEN_UPDATENEWS_MATERIAL("cgi-bin/material/update_news"), // 修改永久图文素材
	WE_CHAT_OPEN_GETMATERIAL_COUNT("cgi-bin/material/get_materialcount"), // 获取素材总数
	WE_CHAT_OPEN_BATCHGET_MATERIAL("cgi-bin/material/batchget_material"), // 获取素材列表
	WE_CHAT_OPEN_UPLOADNEWS_MEDIA("cgi-bin/media/uploadnews"), // 上传图文消息素材
	WE_CHAT_OPEN_SENDALL_MASS("cgi-bin/message/mass/sendall"), // 根据分组进行群发
	WE_CHAT_OPEN_SEND_MASS("cgi-bin/message/mass/send"), // 根据OpenID列表群发
	WE_CHAT_OPEN_GET_MASS("cgi-bin/message/mass/get"), // 查询群发消息发送状态
	WE_CHAT_OPEN_UPLOADVIDEO_MEDIA("cgi-bin/media/uploadvideo"), // 获取视频类文件特殊media_id(消息群发时用)
	WE_CHAT_OPEN_GETUSER_SUMMARY("datacube/getusersummary"), // 获取用户增减数据
	WE_CHAT_OPEN_GETUSER_CUMULATE("datacube/getusercumulate"), // 获取累计用户数据
	WE_CHAT_OPEN_GETARTICLE_SUMMARY("datacube/getarticlesummary"), // 获取图文群发每日数据
	WE_CHAT_OPEN_GETARTICLE_TOTAL("datacube/getarticletotal"), // 获取图文群发总数据
	WE_CHAT_OPEN_GETUSER_READ("datacube/getuserread"), // 获取图文统计数据
	WE_CHAT_OPEN_GETUSER_READ_HOUR("datacube/getuserreadhour"), // 获取图文统计分时数据
	WE_CHAT_OPEN_GETUSER_SHARE("datacube/getusershare"), // 获取图文分享转发数据
	WE_CHAT_OPEN_GETUSER_SHARE_HOUR("datacube/getusersharehour"), // 获取图文分享转发分时数据
	WE_CHAT_OPEN_CUSTOM("cgi-bin/message/custom/send"), // 客服消息发送
	WE_CHAT_OPEN_ACCESS_TOKEN("sns/oauth2/access_token"), // 通过code换取网页授权access_token
	WE_CHEAT_CHANGE_CARD_INFORMATION("card/update?access_token=TOKEN"), // 更改卡券信息接口
	WE_CHEAT_ADDPOI("cgi-bin/poi/addpoi?access_token=TOKEN"), // 创建门店接口
	WE_CHEAT_GETPOILIST("cgi-bin/poi/getpoilist?access_token=TOKEN"), // 查询所有门店
	WE_CHEAT_DELPOI("cgi-bin/poi/delpoi?access_token=TOKEN"), // 删除门店接口
	COMPONENT_ACCESS_TOKEN("cgi-bin/component/api_component_token"), // 获取第三方平台component_access_token
	PRE_AUTH_CODE("cgi-bin/component/api_create_preauthcode"), // 获取预授权码pre_auth_code
	WE_CHAT_OPEN_AUTHORIZE("connect/oauth2/authorize"),// 用户同意授权，获取code
	WE_CHAT_COMPONENTLOGINPAGE("cgi-bin/componentloginpage"),// 用户同意授权，获取code
	API_QUERY_AUTH("cgi-bin/component/api_query_auth"),//通过授权码获取公众号信息
	API_AUTHORIZER_TOKEN("cgi-bin/component/api_authorizer_token"),//通过授权码获取公众号信息
	API_GET_AUTHORIZER_INFO("cgi-bin/component/api_get_authorizer_info"),//通过授权码获取公众号详细信息
	
	// 后台服务接口
	GOOD_DETAIL("/b2b-oms/item/queryItem"),
	// 根据标签分类出所有的品牌
	BRAND_ALL("/b2b-oms/brand/queryBranNoPage"),
	// 根据条件获取商品
	ITEM_ALL("/b2b-oms/item/queryItem"),
	//模糊查询barcode
	GOOD_BARCODE_LIKE("/b2b-oms/goods/likeQueryBarcode"),
	//提交采购单
	PURCHASE_INSERT("/b2b-oms/purchase/addPurchase");
	
	
	private String code;

	private HttpUrl(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

}
