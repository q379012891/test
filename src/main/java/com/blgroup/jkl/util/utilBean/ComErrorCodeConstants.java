package com.blgroup.jkl.util.utilBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信开放平台全局返回码说明
 * 
 * @Class Name ComErrorCodeConstants
 */
public class ComErrorCodeConstants {
	private Map<Object, Object> map;
    public enum ErrorCode {

        /**
        -1	系统繁忙，此时请开发者稍候再试
        0	请求成功
        40001	获取access_token时AppSecret错误，或者access_token无效。请开发者认真比对AppSecret的正确性，或查看是否正在为恰当的公众号调用接口
        40002	不合法的凭证类型
        40003	不合法的OpenID，请开发者确认OpenID（该用户）是否已关注公众号，或是否是其他公众号的OpenID
        40004	不合法的媒体文件类型
        40005	不合法的文件类型
        40006	不合法的文件大小
        40007	不合法的媒体文件id
        40008	不合法的消息类型
        40009	不合法的图片文件大小
        40010	不合法的语音文件大小
        40011	不合法的视频文件大小
        40012	不合法的缩略图文件大小
        40013	不合法的AppID，请开发者检查AppID的正确性，避免异常字符，注意大小写
        40014	不合法的access_token，请开发者认真比对access_token的有效性（如是否过期），或查看是否正在为恰当的公众号调用接口
        40015	不合法的菜单类型
        40016	不合法的按钮个数
        40017	不合法的按钮个数
        40018	不合法的按钮名字长度
        40019	不合法的按钮KEY长度
        40020	不合法的按钮URL长度
        40021	不合法的菜单版本号
        40022	不合法的子菜单级数
        40023	不合法的子菜单按钮个数
        40024	不合法的子菜单按钮类型
        40025	不合法的子菜单按钮名字长度
        40026	不合法的子菜单按钮KEY长度
        40027	不合法的子菜单按钮URL长度
        40028	不合法的自定义菜单使用用户
        40029	不合法的oauth_code
        40030	不合法的refresh_token
        40031	不合法的openid列表
        40032	不合法的openid列表长度
        40033	不合法的请求字符
        40035	不合法的参数
        40038	不合法的请求格式
        40039	不合法的URL长度
        40050	不合法的分组id
        40051	分组名字不合法
        41001	缺少access_token参数
        41002	缺少appid参数
        41003	缺少refresh_token参数
        41004	缺少secret参数
        41005	缺少多媒体文件数据
        41006	缺少media_id参数
        41007	缺少子菜单数据
        41008	缺少oauth code
        41009	缺少openid
        42001	access_token超时，请检查access_token的有效期，请参考基础支持-获取access_token中，对access_token的详细机制说明
        42002	refresh_token超时
        42003	oauth_code超时
        43001	需要GET请求
        43002	需要POST请求
        43003	需要HTTPS请求
        43004	需要接收者关注
        43005	需要好友关系
        44001	多媒体文件为空
        44002	POST的数据包为空
        44003	图文消息内容为空
        44004	文本消息内容为空
        45001	多媒体文件大小超过限制
        45002	消息内容超过限制
        45003	标题字段超过限制
        45004	描述字段超过限制
        45005	链接字段超过限制
        45006	图片链接字段超过限制
        45007	语音播放时间超过限制
        45008	图文消息超过限制
        45009	接口调用超过限制
        45010	创建菜单个数超过限制
        45015	回复时间超过限制
        45016	系统分组，不允许修改
        45017	分组名字过长
        45018	分组数量超过上限
        46001	不存在媒体数据
        46002	不存在的菜单版本
        46003	不存在的菜单数据
        46004	不存在的用户
        47001	解析JSON/XML内容错误
        48001	api功能未授权，请确认公众号已获得该接口，可以在公众平台官网-开发者中心页中查看接口权限
        50001	用户未授权该api
        61451	参数错误(invalid parameter)
        61452	无效客服账号(invalid kf_account)
        61453	客服帐号已存在(kf_account exsited)
        61454	客服帐号名长度超过限制(仅允许10个英文字符，不包括@及@后的公众号的微信号)(invalid kf_acount length)
        61455	客服帐号名包含非法字符(仅允许英文+数字)(illegal character in kf_account)
        61456	客服帐号个数超过限制(10个客服账号)(kf_account count exceeded)
        61457	无效头像文件类型(invalid file type)
        61450	系统错误(system error)
        61500	日期格式错误
        61501	日期范围错误
        */
    	
    	SYSTEM_STATUS__1("-1", "系统繁忙"),
    	SYSTEM_STATUS_0("0", "请求成功"),
    	SYSTEM_STATUS_40001("40001", "获取access_token时AppSecret错误，或者access_token无效"),
    	SYSTEM_STATUS_40002("40002", "不合法的凭证类型"),
    	SYSTEM_STATUS_40003("40003", "不合法的OpenID"),
    	SYSTEM_STATUS_40004("40004", "不合法的媒体文件类型"),
    	SYSTEM_STATUS_40005("40005", "不合法的文件类型"),
    	SYSTEM_STATUS_40006("40006", "不合法的文件大小"),
    	SYSTEM_STATUS_40007("40007", "不合法的媒体文件id"),
    	SYSTEM_STATUS_40008("40008", "不合法的消息类型"),
    	SYSTEM_STATUS_40009("40009", "不合法的图片文件大小"),
    	SYSTEM_STATUS_40010("40010", "不合法的语音文件大小"),
    	SYSTEM_STATUS_40011("40011", "不合法的视频文件大小"),
    	SYSTEM_STATUS_40012("40012", "不合法的缩略图文件大小"),
    	SYSTEM_STATUS_40013("40013", "不合法的AppID"),
    	SYSTEM_STATUS_40014("40014", "不合法的access_token"),
    	SYSTEM_STATUS_40015("40015", "不合法的菜单类型"),
    	SYSTEM_STATUS_40016("40016", "不合法的按钮个数"),
    	SYSTEM_STATUS_40017("40017", "不合法的按钮个数"),
    	SYSTEM_STATUS_40018("40018", "不合法的按钮名字长度"),
    	SYSTEM_STATUS_40019("40019", "不合法的按钮KEY长度"),
    	SYSTEM_STATUS_40020("40020", "不合法的按钮URL长度"),
    	SYSTEM_STATUS_40021("40021", "不合法的菜单版本号"),
    	SYSTEM_STATUS_40022("40022", "不合法的子菜单级数"),
    	SYSTEM_STATUS_40023("40023", "不合法的子菜单按钮个数"),
    	SYSTEM_STATUS_40024("40024", "不合法的子菜单按钮类型"),
    	SYSTEM_STATUS_40025("40025", "不合法的子菜单按钮名字长度"),
    	SYSTEM_STATUS_40026("40026", "不合法的子菜单按钮KEY长度"),
    	SYSTEM_STATUS_40027("40027", "不合法的子菜单按钮URL长度"),
    	SYSTEM_STATUS_40028("40028", "不合法的自定义菜单使用用户"),
    	SYSTEM_STATUS_40029("40029", "不合法的oauth_code"),
    	SYSTEM_STATUS_40030("40030", "不合法的refresh_token"),
    	SYSTEM_STATUS_40031("40031", "不合法的openid列表"),
    	SYSTEM_STATUS_40032("40032", "不合法的openid列表长度"),
    	SYSTEM_STATUS_40033("40033", "不合法的请求字符"),
    	SYSTEM_STATUS_40035("40035", "不合法的参数"),
    	SYSTEM_STATUS_40038("40038", "不合法的请求格式"),
    	SYSTEM_STATUS_40039("40039", "不合法的URL长度"),
    	SYSTEM_STATUS_40050("40050", "不合法的分组id"),
    	SYSTEM_STATUS_40051("40051", "分组名字不合法"),
    	SYSTEM_STATUS_41001("41001", "缺少access_token参数"),
    	SYSTEM_STATUS_41002("41002", "缺少appid参数"),
    	SYSTEM_STATUS_41003("41003", "缺少refresh_token参数"),
    	SYSTEM_STATUS_41004("41004", "缺少secret参数"),
    	SYSTEM_STATUS_41005("41005", "缺少多媒体文件数据"),
    	SYSTEM_STATUS_41006("41006", "缺少media_id参数"),
    	SYSTEM_STATUS_41007("41007", "缺少子菜单数据"),
    	SYSTEM_STATUS_41008("41008", "缺少oauth code"),
    	SYSTEM_STATUS_41009("41009", "缺少openid"),
    	SYSTEM_STATUS_42001("42001", "access_token超时"),
    	SYSTEM_STATUS_42002("42002", "refresh_token超时"),
    	SYSTEM_STATUS_42003("42003", "oauth_code超时"),
    	SYSTEM_STATUS_43001("43001", "需要GET请求"),
    	SYSTEM_STATUS_43002("43002", "需要POST请求"),
    	SYSTEM_STATUS_43003("43003", "需要HTTPS请求"),
    	SYSTEM_STATUS_43004("43004", "需要接收者关注"),
    	SYSTEM_STATUS_43005("43005", "需要好友关系"),
    	SYSTEM_STATUS_44001("44001", "需要好友关系"),
    	SYSTEM_STATUS_44002("44002", "POST的数据包为空"),
    	SYSTEM_STATUS_44003("44003", "图文消息内容为空"),
    	SYSTEM_STATUS_44004("44004", "文本消息内容为空"),
    	SYSTEM_STATUS_45001("45001", "多媒体文件大小超过限制"),
    	SYSTEM_STATUS_45002("45002", "消息内容超过限制"),
    	SYSTEM_STATUS_45003("45003", "标题字段超过限制"),
    	SYSTEM_STATUS_45004("45004", "描述字段超过限制"),
    	SYSTEM_STATUS_45005("45005", "链接字段超过限制"),
    	SYSTEM_STATUS_45006("45006", "图片链接字段超过限制"),
    	SYSTEM_STATUS_45007("45007", "语音播放时间超过限制"),
    	SYSTEM_STATUS_45008("45008", "图文消息超过限制"),
    	SYSTEM_STATUS_45009("45009", "接口调用超过限制"),
    	SYSTEM_STATUS_45010("45010", "接口调用超过限制"),
    	SYSTEM_STATUS_45015("45015", "回复时间超过限制"),
    	SYSTEM_STATUS_45016("45016", "系统分组，不允许修改"),
    	SYSTEM_STATUS_45017("45017", "分组名字过长"),
    	SYSTEM_STATUS_45018("45018", "分组数量超过上限"),
    	SYSTEM_STATUS_46001("46001", "不存在媒体数据"),
    	SYSTEM_STATUS_46002("46002", "不存在的菜单版本"),
    	SYSTEM_STATUS_46003("46003", "不存在的菜单数据"),
    	SYSTEM_STATUS_46004("46004", "不存在的用户"),
    	SYSTEM_STATUS_47001("47001", "解析JSON/XML内容错误"),
    	SYSTEM_STATUS_48001("48001", "api功能未授权，请确认公众号已获得该接口"),
    	SYSTEM_STATUS_50001("50001", "用户未授权该api"),
    	SYSTEM_STATUS_61451(" 61451", "参数错误(invalid parameter)"),
    	SYSTEM_STATUS_61452(" 61452", "无效客服账号(invalid kf_account)"),
    	SYSTEM_STATUS_61453(" 61453", "无效客服账号(invalid kf_account)"),
    	SYSTEM_STATUS_61454(" 61454", "客服帐号名长度超过限制(仅允许10个英文字符，不包括@及@后的公众号的微信号)(invalid kf_acount length)"),
    	SYSTEM_STATUS_61455(" 61455", "客服帐号名包含非法字符(仅允许英文+数字)(illegal character in kf_account)"),
    	SYSTEM_STATUS_61456(" 61456", "客服帐号个数超过限制(10个客服账号)(kf_account count exceeded)"),
    	SYSTEM_STATUS_61457(" 61457", "无效头像文件类型(invalid file type)"),
    	SYSTEM_STATUS_61450(" 61450", "系统错误(system error)"),
    	SYSTEM_STATUS_61500("61500", "日期格式错误"),
    	SYSTEM_STATUS_61501("61501", "日期范围错误");
        
        private String errorCode;
        private String memo;

        private ErrorCode() {
        };

        private ErrorCode(String errorCode, String memo) {
            this.errorCode = errorCode;
            this.memo = memo;
        }
        
        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }
    }
    public Map<Object, Object> getMap() {
    	map = new HashMap<Object, Object>();
		for(ErrorCode e : ErrorCode.values()) {
			map.put(e.getErrorCode(), e.getMemo());
		}
    	return map;
   }
}
