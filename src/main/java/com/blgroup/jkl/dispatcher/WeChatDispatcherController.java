package com.blgroup.jkl.dispatcher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blgroup.jkl.util.JsonUtils;

import net.sf.json.JSONObject;


/**
 * 处理微信平台推送事件
 * @author Gavin Ni
 * 
 */
@Controller
@RequestMapping(value = "/dispatcher")
public class WeChatDispatcherController {
	private static Logger LOGGER = LoggerFactory.getLogger(WeChatDispatcherController.class);

	private String token = "b2b_wechat";
	
	private static MessageDigest digest = null;
	
	public WeChatDispatcherController()
	{
		try {
			digest=MessageDigest.getInstance("sha1");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("get MessageDigest instance error:",e);
		}
	}
	/**
	 * 获取公众号的信息
	 * @param userid
	 * @param password
	 * @param rid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/wechatDispatcher", method = {RequestMethod.GET})
	public String validMpToken(HttpServletRequest req,HttpServletResponse res) throws IOException{
		LOGGER.debug("validMpToken() started");
		
		// 获取参数，校验token
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		
		String[] str = { token, timestamp, nonce };
        Arrays.sort(str); // 字典序排序
        String bigStr = str[0] + str[1] + str[2];
		String rs = encipher(bigStr);
		
		LOGGER.debug("validMpToken() ended");
		if (rs.equals(signature)) {
			return echostr;
		}
		return "";
	}
	
	public static String byte2hex(byte[] b) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < b.length; i++) {
            tmp = (Integer.toHexString(b[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
     
    public static String encipher(String strSrc) {
    	
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        digest.update(bt);
        strDes = byte2hex(digest.digest());
        return strDes;
    }
	 
	/**
	 * 获取公众号的信息
	 * @param userid
	 * @param password
	 * @param rid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/wechatDispatcher", method = {RequestMethod.POST})
	public void getWechatAccount(HttpServletRequest req,HttpServletResponse res) {
		LOGGER.debug("wechatDispatcher getWechatAccount() started: {startTime: " + new Date() + "}");
		if(LOGGER.isDebugEnabled())
			LOGGER.debug("dispatcher entering ....");
		StringBuilder messageBody = new StringBuilder();
		BufferedReader reader=null;
		PrintWriter writer =null;
		try{
			reader =   new BufferedReader(new InputStreamReader(req.getInputStream(),"UTF-8"));
			
			res.setCharacterEncoding("utf-8");
			String value = null;
			while((value = reader.readLine())!=null){
				messageBody.append(value);
			}
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("post data from MP:"+messageBody.toString());
			}
			
			// 将得到的消息转换成json格式
			String json = JsonUtils.xmltoJson(messageBody.toString());
			
			JSONObject jsb = JSONObject.fromObject(json);
			// 判断消息类型
			String replyBody = null;
			if (jsb.containsKey("Event")) {
				// 事件类消息处理方法
//				replyBody=handleEventMessage(jsb);
			} else {
				//　非事件类消息处理方法
//				replyBody=handleSessionMessage(jsb);
			}
	
			if(LOGGER.isDebugEnabled())
				LOGGER.debug("reply data to mp:"+replyBody);
			writer = res.getWriter();
			/*if(replyBody!=null){
				writer.write(replyBody);
				writer.flush();
				writer.close();
			}*/
		}catch(Exception e){
			LOGGER.error("process data error:"+messageBody.toString(), e);
		}finally{
			if(reader!=null)
				try {
					reader.close();
				} catch (IOException e) {
				}
			if(writer!=null)
				writer.close();
			

			LOGGER.debug("wechatDispatcher() ended");
		}
		LOGGER.debug("WeChatDispatcherController wechatDispatcher() ended: {endTime: " + new Date() + "}");
	}
}
