package com.blgroup.jkl.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blgroup.jkl.controller.BrandController;

/**
 * 
 * @author yuanmj
 *
 */
public class PropertiesUtil {
	private static Logger log = LoggerFactory.getLogger(BrandController.class);
	// 执行成功标识
	public static final String SYSTEM_SUCCESS = "0";
	// 微信开放平台获取access_token填写client_credential
	public static final String GRANT_TYPE_NAME = "grant_type";
	public static final String GRANT_TYPE_VALUE = "client_credential";
	public static final String JS_API = "jsapi";
	public static final String CARD_API = "wx_card";
	
	public static final String APPID_NAME = "appid";
	public static final String SECRET_NAME ="secret";
	public static final String ACCESS_TOKEN = "access_token";
	public static final String TICKET = "ticket";
	public static final String OPEN_ID = "openid";
	public static final String CODE = "code";
	public static final String AUTHORIZATION_CODE = "authorization_code";
	public static final String REDIRECT_URI = "redirect_uri";
	public static final String RESPONSE_TYPE = "response_type";
	public static final String SCOPE = "SCOPE";
	public static final String SNSAPI_BASE = "snsapi_base";
	public static final String STATE = "state";
	public static final String WECHAT_REDIRECT = "wechat_redirect";

	public static final String LANG = "lang";
	public static final String NEXT_OPEN_ID = "next_openid";
	public static final String TYPE = "type";
	public static final String MEDIA_ID = "media_id";
	public static final String FILE_PATH = "filePath";
	public static final String ERRCODE = "errcode";
	public static final String QUESTION_MARK="?";
	public static final String SLASH_MARK="/";
	public static final String AND_MARK="&";
	public static final String EQUALS_SIGE="=";
	public static final String NETWORK_MARK="#";
	
	public static final String SYSTEM_ERROR = "{\"errcode\":-1,\"errmsg\":\"系统繁忙，请稍后再试\"}";
	
	 /**
     * 读取一个属性文件
     * 
     * @param propName
     *            属性文件名称
     * @return 返回属性文件
     * @throws IOException
     *             IO异常
     */
    public static Properties getProperties(String propName) throws IOException {
        if (StringUtil.isEmpty(propName)) {
            return null;
        }

        Properties p = null;
        InputStream in = ClassLoader.getSystemResourceAsStream(propName);
        try {
            if (in == null) {
                in = PropertiesUtil.class.getResourceAsStream(propName);
            }
            if (in == null) {
                in = PropertiesUtil.class.getClassLoader().getResourceAsStream(propName);
            }
            p = getProperties(in);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            in.close();
        }
        return p;
    }

    /**
     * 读取一个属性文件
     * 
     * @param in
     *            文件流
     * @return 返回属性文件
     * @throws IOException
     */
    private static Properties getProperties(InputStream in) throws IOException {
        Properties p = new Properties();
        if (in != null) {
            p.load(in);
            in.close();
        }
        return p;
    }
}
