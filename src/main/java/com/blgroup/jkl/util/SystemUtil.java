package  com.blgroup.jkl.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import com.blgroup.jkl.core.SystemConextConstans;
@SuppressWarnings("all")
public class SystemUtil {
	
   public static final SimpleDateFormat defaultDateFormate = new SimpleDateFormat("yyyy-MM-dd");
   
   public static final SimpleDateFormat codesTimeFormate = new SimpleDateFormat("yyyyMMddhhmmss");
   
   public static final SimpleDateFormat defaultTimeFormate = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 *将字符日期转换为日期对象 
	 * @param date
	 * @return
	 */
   public static  Date string2Date(String date){
		try {
			return defaultDateFormate.parse(date);
		} catch (Exception e) {
			return null;
		}
   }
   
   /**
    * 获取今天的日期对象
    * @return
    */
   public static Date getToday(){
	   try{
		   return defaultDateFormate.parse( defaultDateFormate.format(new Date())); 
	   }catch(Exception e){
		   return null;
	   }
	   
   }
   
   public static Date getCurrentTime(){
	   try{
		   return new Date(); 
	   }catch(Exception e){
		   return null;
	   }
   }
   
   public static String getCurrentTimeStr(){
	  return defaultTimeFormate.format(getCurrentTime());
   }
   
   public static String getCookieValue(HttpServletRequest request,String key){
	   Cookie [] cookies = request.getCookies();
	   if(cookies==null){
		   return null;
	   }
	   for(Cookie cookie : cookies ){
		   if(cookie.getName().equals(key)){
			   return URLDecoder.decode(cookie.getValue());
		   }
	   }
	   return null;
   }
   
   
   // 通过cookie取得openId
   public static String getOpenIdByCookie(HttpServletRequest request){
	   String aesOpenIdInfo = getCookieValue(request,SystemConextConstans.AUTHO_CODE);
	   if(aesOpenIdInfo==null){
		   return null;
	   }
	   aesOpenIdInfo = base64Decode(aesOpenIdInfo);
		String openid = aesOpenIdInfo
				.substring(32, aesOpenIdInfo.length() - 14);
	   return openid;
   }
   
   
   
   /**
    * 验证当前微信的openId是否还有效
    * @param request
    * @return
    */
   public static boolean weixinAuthoValidate(HttpServletRequest request){
	 try {
		String aesOpenIdInfo = getCookieValue(request, SystemConextConstans.AUTHO_CODE);
		if(aesOpenIdInfo==null){
			return false;
		}
		aesOpenIdInfo = base64Decode(aesOpenIdInfo);
		String randomValue = aesOpenIdInfo.substring(0, 32);
		String endTime = aesOpenIdInfo.substring(aesOpenIdInfo.length() - 14,
				aesOpenIdInfo.length());
		String weixinSignature = getCookieValue(request, SystemConextConstans.AUTHO_SIGNATURE);
		Date endDate = codesTimeFormate.parse(endTime);
		String signature = getWeixinAuthoSignature(randomValue, endDate);
		if (weixinSignature.equals(signature)) {
			Long currentTime = SystemUtil.getToday().getTime();
			if (endDate.getTime()> currentTime) {
				return true;
			}
			return false;
		}
	} catch (Exception e) {
		return false;
	}
	return false;
   }
   
   /**
    * 对微信openId授权加密
    * @param openId
    * @param endTime
    * @return
    */
   public static String getWeixinAuthocCode(String openId,String randomValue,Date endTime){
	   return base64Encode(randomValue+openId+codesTimeFormate.format(endTime));
   }
   /**
    * 对微信openId授权签名
    * @param opneId
    * @param endTime
    * @return
    */
   public static String getWeixinAuthoSignature(String randomValue,Date endTime){
	   return md5(randomValue+codesTimeFormate.format(endTime));
   }
   
   
   /**
    * BASE 64对称加密
    * @param value
    * @return
    */
   public static String base64Encode(String value){
		 byte[] enbytes = Base64.encodeBase64Chunked(value.getBytes());  
		 return new String(enbytes);
   }
   /**
    * BASE 64对称解密
    * @param value
    * @return
    */
   public static String base64Decode(String value){
	    byte[] debytes = Base64.decodeBase64(new String(value).getBytes());  
	    return new String(debytes);  
   }
   
   
   /**
    * MD5加密
    * @param value
    * @return
    */
   public static String md5(String value){
	   return DigestUtils.md5Hex(value);
   }
   
   
   public static Date nextDay(){
	  return  new Date(SystemUtil.getToday().getTime()+1000*60*60*24);
   }
   
   
   public static byte[] objectToByte(Serializable obj) throws IOException{
	   ByteArrayOutputStream  Obaos = new ByteArrayOutputStream();
	   ObjectOutputStream moos = new ObjectOutputStream(Obaos);
	   moos.writeObject(obj);
	   byte[] bytes = Obaos.toByteArray();
	   return bytes;
   }
   
   
   public static<T> T byteToObject(byte bytes[]) throws IOException, ClassNotFoundException{
	   ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
	   ObjectInputStream ois = new ObjectInputStream(bais);
	   return (T)ois.readObject();
   }
   
   public static boolean isMobileNO(String mobiles) {
	   Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
	   Matcher m = p.matcher(mobiles);
	   return m.matches();
	   }
   
   
   public static boolean needLoginVlidation(String openId){
	   System.out.println("----------------------openId="+openId+"-----------------------------");
	   String flag =  CacheUtil.getString(openId+"_"+SystemConextConstans.WEI_XIN_USER_INIT_LOGIN_FALG);
	   System.out.println("---------------------------flag="+flag+"--------------------------------");
	 if(SystemConextConstans.LOGIC_FASE_STR.equals(flag)){
		   return false;
	 }
	  return true;
   }
   
   
   public static void disabledAutoLoginByOpenId(String openId){
	   CacheUtil.set(openId+"_"+SystemConextConstans.WEI_XIN_USER_INIT_LOGIN_FALG, SystemConextConstans.LOGIC_FASE_STR, 60*20);
   }

   
   
   
   public static String getHost(HttpServletRequest request){
	  String host =   request.getHeader(SystemConextConstans.HEADER_HOST_NAME);
	  return "http://"+host.replaceAll(":.*$", "");
   }
   
}
