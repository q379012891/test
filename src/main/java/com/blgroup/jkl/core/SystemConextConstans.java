package com.blgroup.jkl.core;

public class SystemConextConstans {
	public final static String APP_PATH="appPath";

	// H5路径
	public final static String H5_APP_PATH="h5AppPath";
	//H5评价路径
	public final static String H5_UP_PATH="h5UpPath";
	
	// 微信路径
	public final static String WECHAT_APP_PATH="wechatAppPath";
	//pc图片路径前缀
	public final static String PC_IMG_PREFIX="pcImgPrefix";
	public final static String RESOURCE_PATH="resourcePath";
	public final static String MAP_RESOURCE_PATH="mapResourcePath";
	public final static String APP_ID="appId";
	public final static String PARTNER_KEY="partnerKey";
	public final static String MCH_ID="mch_id";
	
	// Core Metrics 统计配置信息
	// core metrics 用户id
	public final static String COREMETRICS_ID="coremetricsId";
	// core metrics 域名
	public final static String COREMETRICS_DOMAIN="coremetricsDomain";
	// core metrics 下注册微信域名
	public final static String WEIXIN_DOMAIN="weixinDomain";
	
	// 当前是否为生产环境
	public final static String STATUS_PRODUCTION="status_production";
	
	
	//微信系统上下文名称
	public final static String WEI_XIN_SYSTEM_CONTEXT_NAME="systemContext";
	//微信用户上下文名称
	public final static String WEI_XIN_USER_CONTEXT_NAME="userContext";
	
	public final static String WEI_XIN_USER_CONTEXT_OPEN_ID="openId";
	
	//微信首次登陆标志
	public final static String WEI_XIN_USER_INIT_LOGIN_FALG="weixinInitLoginFlag";
	
	public final static String WEI_XIN_USER_NAME="weixinUser";
	
	
	public final static String MEMBER_TOKEN_NAME="member_token";
	
	public final static String MEMBER_ID_NAME="member_id";
	public final static String MEMBER_NAME_NAME="member_name";
	public final static String MEMBER_MOBILE="mobile";
	public final static String MEMBER_PIN="memberPin";
	public final static String OPEN_ID_NAME="openId";
	//会员等级
	public final static String MEMBER_LEVEL_CODE="memberLevelCode";
	
	public final static String EXPIR_TIME_NAME="expirTime";
	
	public final static String OPEN_ID_PRE_PATH_SESSION_NAME="weixin_state";
	
	public final static String LOGIN_PRE_PATH = "";
	//微信
	public final static String WEIXIN_STATE_SESSION_NAME="weixin_state";
	
	//最后选择的菜单URL
	public final static String LAST_CHOIC_MENU="lastChoicMenu";
	
	public final static String LAST_REQUEST_URI="lastUri";
	
	//系统登录flag
	public final static String LOGIN_FLAG_JSON_NAME="weixinLoginFlag";
	
	//系统登录flag
	public final static String SYS_LOGIN_FLAG_JSON_NAME=LOGIN_FLAG_JSON_NAME;
	
	//系统逻辑“真”字符
	public final static String LOGIC_TRUE_STR="1";
	
	//系统逻辑“真”字符
		public final static String LOGIC_TRUE_STR_Y="Y";
	//系统逻辑“假”字符
	public final static String LOGIC_FASE_STR="0";
	
	//登录跳转URL key
	public final static String LOGIN_REDIRCT = "loginRedirct";
	//注册跳转URL key
	public final static String REGISTER_REDIRCT ="registerRedirct";
	//生成的唯一用户对的uuid
	public final static String REGISTER_REDIRCT_UUID="registerRedirctUUID";
	//默认授权code
	public final static String  AUTHO_CODE="authoCode";
	public final static String  AUTHO_LOGIN_CODE="authoLoginCode";
	
	public final static String  HEADER_USER_AGENT="user-agent";
	public final static String  WEIXIN_USER_AGENT="^.*MicroMessenger.*$";
	//浏览器类型 ——微信
	public final static String  BRROWES_TYPE_WEIXIN="weixin";
	
	//返回其他类型
	public final static String  BRROWES_TYPE_OTHER="other";
	
	public final static String  HEADER_HOST_NAME="host";
	
	//cookie 签名key
	public final static String  AUTHO_SIGNATURE="authoSignature";
	//数据字典业态类型code
	public final static String  VALUE_ITEM_GROUP_CODE_FORMATE_TYPE = "01";
	//数据字典业态code(六大分类)
	public final static String  VALUE_ITEM_GROUP_CODE_SHOP_TYPE = "sys_data_site_store_type";
	//默认省（直辖市上海）
	public final static String  VALUE_ADDR_DEFAULT_PROVINCE = "867";
	//默认市（上海-主城区）
	public final static String  VALUE_ADDR_DEFAULT_CITY = "867";
	
	//默认分页数量
	public final static int DEFAULT_PAGE_SIZE = 10;
	//redis最大线程数
	public final static String REDIS_MAX_ACTIVE="redis_maxActive";
	
	public final static String REDIS_MAX_IDLE="redis_maxIdle";
	//redis最大等待时间
	public final static String REDIS_MAX_WAIT="redis_maxWait";
	//redis hostip
	public final static String REDIS_HOST_IP="redis_host_ip";
	//redis 端口
	public final static String REDIS_PORT="redis_port";
	//密码
	public final static String REDIS_PASSWORD="redis.pass";
	
	public final static String WEIXIN_SESSION_MESSAGE_STATUS_PREFIX="SESSION_MSG";
	
	public final static String WSREST_PARAM_ENTITY_LIST="@entityList";
	
	
	public final static String API_SEACHCARPORT="searchcarport";
	
	public final static String RAND_CODE="randCode";
	
	public final static String SUBMIT_CODE="submitCode";
	
	public final static String VALIDATION_INDEX="validationIndex";
	
	//注册提交时的校验码
	public final static String REG_SUBMIT_ID="regSubmitId";
	
	//前台销售渠道显示
	public final static String SELL_CHANNEL_FACE="sell_channel_face";
	//前台积分类型显示
	public final static String POINT_TYPE_FACE="point_type_face";
	
	//H5首页点击下载APP Android
	public final static String POINT_TYPE_DOWNLOAD_ANDROID_APP="android_download_url";
	
	//H5首页点击下载APP IOS
	public final static String POINT_TYPE_DOWNLOAD_IOS_APP="ios_download_url";
	
	public final static String IBL_DEFAULT_LOGO_PATH="ibl_logo_path";
	
	//查询商圈
	public final static String  VALUE_ITEM_GROUP_CODE_SHOP_BIZAREA = "sys_data_site_store_bizarea";
	
	//CPS参数名称
	public final static String  FILED_CPS_PARAM_NAME = "cm_mmc";
	//CPS参数追踪码
	public final static String  FILED_CPS_ADS_UID = "ads_uid";
	
	//CPS的cookie名称
	public final static String  FILED_CPS_COOKIE_NAME = "ads_token";
	
	
	public final static String  FILED_SCAN_MEMBER_EVENT = "scanMemberEvent";
	//订单来源渠道
	public final static String  FILED_ORDER_SOURCE_FROM= "orderSourceFrom";
	//订单来源渠道子id
	public final static String  FILED_ORDER_SOURCE_FROM_ID = "orderSourceFromId";
	
	
	
	//CPS上下文的名称
	public final static String  USER_CONTEXT_CPS_CODE_CONTEXT_NAME = FILED_CPS_PARAM_NAME;
	
	
	public final static String  USER_CONTEXT_EVERYONE_SALE_CONTEXT_NAME = "everyOneSale";
	
	//快到家收货地址
	public final static String  FAST_TO_HOME_ADDRESS = "addressInfo";
	
	//存adverId 来源子渠道     --machineId
	public final static String FILED_CPA_ADVER_ID="adverId";
	
	//存adverChannel 来源渠道
	public final static String FILED_CPA_ADVER_CHANNEL="adverChannel";
	
	//容易网电子屏扫码注册 传过来的设备号  
	public final static String FILED_CPA_MACHINE_ID="machineId";
	//门店
    public final static String USER_CONTEXT_RENRENHUI_SHOP_ID="mendian";
    //业态
    public final static String USER_CONTEXT_RENRENHUI_YETAI_ID="yetaiId";
    //活动id
    public final static String USER_CONTEXT_RENRENHUI_TASK_CODE="taskCode";
    
    
    
    //业态
    public final static String USER_CONTEXT_CPA_BUILD="buid";
    //活动id
    public final static String USER_CONTEXT_CPA_SHOP_ID="store_id";
    
    
    //session key renrenhui
    public final static String FILED_RENRENHUI_INFO="renrenhui_info";
    //营销员id
    public final static String FILED_TO_USER_PUB="codeKey";
    //广告身份标识
    public final static String FILED_CPS_CODE="cpsCode";
    //活动id
    public final static String RENREN_CONTEXT_RETURN_ACTIVITY_ID="activityId";
    
    //百联一级域名地址
    public final static String BL_DOMAIN="bl.com";
    //人人惠关键信息cookie 名称
    public final static String RENREN_KEY_INFO_COOKIE_NAME="renrenhui";
    
    // 奥运联通来源标示
    public final static String RENREN_CONTEXT_FROMFLAG="fromFlag";
    
    /**
   	 * @Fields SESSION_VERIFYCODE_LOGIN : 登录页面验证码
   	 */
   	public static final String SESSION_VERIFYCODE_LOGIN = "SESSION_VERIFYCODE_LOGIN";

   	/**
   	 * @Fields SESSION_VERIFYCODE_SIDEBAR : 侧边栏验证码
   	 */
   	public static final String SESSION_VERIFYCODE_SIDEBAR = "SESSION_VERIFYCODE_SIDEBAR";

   	/**
   	 * @Fields SESSION_VERIFYCODE_POP : 弹出框登录验证码
   	 */
   	public static final String SESSION_VERIFYCODE_POP = "SESSION_VERIFYCODE_POP";
   	/**
   	 * @Fields COOKIE_VERCODE_NAME : cookie中验证码的名称
   	 */
   	public static final String COOKIE_VERCODE_NAME = "isVerCode";
   	/**
   	 * @Fields SESSION_VERIFYCODE_KEY : 公用弹出框验证码
   	 */
   	public static final String SESSION_VERIFYCODE_KEY = "VERIFYCODE_SESSION_KEY";
   	/**
   	 * @Fields SESSION_VERIFYCODE_FORGETPWD : 找回密码验证码
   	 */
   	public static final String SESSION_VERIFYCODE_FORGETPWD = "SESSION_VERIFYCODE_FORGETPWD";
   	
	// MP系统 系统上下文名称
	public final static String MP_SYSTEM_CONTEXT_NAME = "systemContext";
	// MP系统 用户上下文名称
	public final static String MP_USER_CONTEXT_NAME="userContext";
	
	public final static String WEI_XIN_USER_CONTEXT_MOBILE="mobile";

}
