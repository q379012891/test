package com.blgroup.jkl.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.blgroup.b2b.common.entity.MessageResultInfo;
import com.blgroup.b2b.common.utils.JsonUtils;
import com.blgroup.jkl.bean.InsertPurchaseReqDto;
import com.blgroup.jkl.bean.Item;
import com.blgroup.jkl.bean.PurchaseApplyGoods;
import com.blgroup.jkl.bean.PurchaseApplyGoodsImage;
import com.blgroup.jkl.redis.RedisClientTemplate;
import com.blgroup.jkl.service.IItemService;
import com.blgroup.jkl.service.ProductService;
import com.blgroup.jkl.service.PurchaseService;
import com.blgroup.jkl.service.WeChatAccessTokenService;
import com.blgroup.jkl.service.WeChatJsapiTicketService;
import com.blgroup.jkl.util.PropertiesUtil;

/**
 * 扫码采购控制器
 * @author JZM48
 *
 */
@Controller
public class SweepCodePurchasingController {
	@Autowired
	RedisClientTemplate redisClientTemplate;
	@Autowired
	WeChatJsapiTicketService weChatJsapiTicketService;
	@Autowired
	WeChatAccessTokenService weChatAccessTokenService;
	@Autowired
	ProductService productService;
	@Autowired
	IItemService itemServiceImpl;
	@Autowired
	PurchaseService purchaseServiceImpl;
	private static Logger logger = LoggerFactory.getLogger(SweepCodePurchasingController.class);
	
	/**
	 * 进入扫码采购页面
	 * @return
	 */
	@RequestMapping(value="/sweepCodePurchasing")
	public ModelAndView sweepCodePurchasing(HttpServletRequest request){
		logger.info("进入扫码采购页面------------------");
		ModelAndView mv = new ModelAndView();
		//从缓存中获取accessToken
		String accessToken = redisClientTemplate.get("accessToken");
		Properties properties=null;
		try {
			properties = PropertiesUtil.getProperties("application.properties");
		} catch (IOException e) {
		}
		String appId = (String)properties.get("appid");
		String secret = (String)properties.get("appsecret");
		String serviceDomain = (String)properties.get("serviceDomain");
		String baseurl = (String)properties.get("img.baseurl");
		String signature = "";// 签名
		mv.getModelMap().put("appId", appId);
		Long timestamp = new Date().getTime() / 1000;// 时间戳
		mv.getModelMap().put("timestamp", timestamp);
		String noncestr = "b2b_wechat";//自定义字符串
		mv.getModelMap().put("noncestr", noncestr);
		mv.getModelMap().put("serviceDomain", serviceDomain);
		mv.getModelMap().put("baseurl", baseurl);
		String url = "";
		// 从request获取queryString
		String queryString = request.getQueryString();
		if (StringUtils.isNotBlank(queryString)) {
			url = request.getRequestURL() + "?" + queryString;
		} else {
			url = request.getRequestURL() + "";
		}
		// 如果没有accessToken
		if(StringUtils.isBlank(accessToken)){
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put(PropertiesUtil.GRANT_TYPE_NAME, PropertiesUtil.GRANT_TYPE_VALUE);
			map.put(PropertiesUtil.APPID_NAME, appId);
			map.put(PropertiesUtil.SECRET_NAME, secret);
			//重新获取accessToken
			String token =weChatAccessTokenService.getToken(map, appId, secret);
			// 写入缓存
			redisClientTemplate.set("accessToken", token, 7100);
			// 获取jsapiTicket
			String jsapiTicket = weChatJsapiTicketService.getJsapiTicket(token);
			String sha1 = "jsapi_ticket=" + jsapiTicket + "&noncestr=" + noncestr + "&timestamp="
					+ timestamp + "&url=" + url;
			// 加密签名
			signature = getSha1(sha1);
			mv.getModelMap().put("signature", signature);
		}else{
			// 获取jsapiTicket
			String jsapiTicket = weChatJsapiTicketService.getJsapiTicket(accessToken);
			String sha1 = "jsapi_ticket=" + jsapiTicket + "&noncestr=" + noncestr + "&timestamp="
					+ timestamp + "&url=" + url;
			// 加密签名
			signature = getSha1(sha1);
			mv.getModelMap().put("signature", signature);
		}
		mv.setViewName("/SweepCodePurchasing");
		return mv;
	}
	
	@RequestMapping(value="/goods")
	public ModelAndView goods(HttpServletRequest request, String id ,Item g){
		logger.info("goods-----------------id"+id);
		ModelAndView mv = new ModelAndView();
		Properties properties=null;
		try {
			properties = PropertiesUtil.getProperties("application.properties");
		} catch (IOException e) {
			logger.info("获取配置错误------------------");
		}
		String baseurl = (String)properties.get("img.baseurl");
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("barCode", id);
		String item = itemServiceImpl.getItem(map);
		mv.getModelMap().put("id", id);
		 mv.getModelMap().put("baseurl", baseurl);
		 try {
			    MessageResultInfo messageResultInfo = JsonUtils.jsonString2Bean(item, MessageResultInfo.class);
				String pageInfoStr=JsonUtils.object2JsonString(messageResultInfo.getResultInfo());
				 if("[]".equals(pageInfoStr)){
					 mv.setViewName("/addcodeno");
					 String random = Math.random()+"";
					 String replaceAll = random.replace(".", "").replace("0", "");
					 mv.getModelMap().put("fileId",replaceAll);
			 }else{
				 Item itemdate = JsonUtils.josn2List(pageInfoStr, Item.class).get(0);
				 mv.getModelMap().put("cnDesc", itemdate.getCnDesc());
				 mv.getModelMap().put("oriCountry", itemdate.getOriCountry());
				 mv.getModelMap().put("brandId", itemdate.getBrandId());
				 mv.getModelMap().put("itemSpec", itemdate.getItemSpec());
				 mv.getModelMap().put("cateCode", itemdate.getItemCate());
				 mv.getModelMap().put("expirationDate", itemdate.getExpirationDate());
				 mv.getModelMap().put("itemImage", itemdate.getItemImage());
				 
				 mv.getModelMap().put("prodCode", itemdate.getProdCode());
				 mv.getModelMap().put("brandName", itemdate.getBrandName());
				 mv.getModelMap().put("itemName", itemdate.getItemName());
				 mv.setViewName("/good");
			 }
		} catch (Exception e) {
			logger.error("通过条形码获取商品出错 ,跳转没有商品页面------------------",e);
//			 mv.setViewName("/addcodeno");
//			 String random = Math.random()+"";
//			 String replaceAll = random.replace(".", "").replace("0", "");
//			 mv.getModelMap().put("fileId",replaceAll);
		}
		logger.info("barCode-->>>>>>>>>>>>>>>>"+id);

		
		return mv;
	}
	
	/**
	 * 
	 * 方法描述：   方法总结
	 * 业务逻辑说明  ：下一步,跳转确认采购页面
	 * 3、处理…………<br/>
	 * @Title: toConfirmPurchasing 
	 * @date 2017年3月14日 下午3:21:05
	 * @author 肖金马
	 * @modifier 
	 * @modifydate 
	 * @return
	 */
	@RequestMapping(value="/toConfirmPurchasing")
	public  ModelAndView  toConfirmPurchasing(HttpServletRequest  httpServletRequest  ,String date,String date1){
	
		logger.info("toConfirmPurchasing--进入采购单页面>>>>>>>>>>>>>>>>");
		ModelAndView mv = new ModelAndView();
		Properties properties=null;
		try {
			properties = PropertiesUtil.getProperties("application.properties");
		} catch (IOException e) {
			logger.error("-读取配置出错面>>>>>>>>>>>>>>>>",e);
		}
		String baseurl = (String)properties.get("img.baseurl");
		String cuturl = (String)properties.get("img.cuturl");
		if((date!=null)&&(!"".equals(date))){
		List<Item>   list  =  new  ArrayList<>();
		String[] split = date.split(",-,");
			for (int i = 0; i < split.length; i++) {
				String string = split[i].replaceAll(",-", "");
				String[] dateNum = string.split(",");
					String barCode=dateNum[0];
					String num=dateNum[1];
				Map<Object, Object> map = new HashMap<Object, Object>();
				map.put("barCode", barCode);
				String item = itemServiceImpl.getItem(map);
				try {
				MessageResultInfo messageResultInfo = JsonUtils.jsonString2Bean(item, MessageResultInfo.class);
				String pageInfoStr=JsonUtils.object2JsonString(messageResultInfo.getResultInfo());
				Item itemdate = JsonUtils.josn2List(pageInfoStr, Item.class).get(0);
				itemdate.setSaleSum(num);
				//商品名称长度不大于25个字符
				if(itemdate.getItemName().length() > 25){
					String itemName = itemdate.getItemName().substring(0, 25)+"...";
					itemdate.setItemName(itemName);
				}
				list.add(itemdate);
				} catch (Exception e) {
					logger.error("-组装扫描出来的商品出错>>>>>>>>>>>>>>>>",e);
				}
			}
		mv.getModelMap().put("list", list);
		}
		if((date1!=null)&&(!"".equals(date1))){
		//处理申请的图片
		List<PurchaseApplyGoods> goodsList  =  new ArrayList<PurchaseApplyGoods>();
		String[] split2 = date1. split(",!-!,");
		for (int i = 0; i < split2.length; i++) {
			 String[] split3 = split2[i].split(",");
			 PurchaseApplyGoods goods = new  PurchaseApplyGoods();
			 String barCode = split3[0];
			 String num = split3[1];
			 goods.setBarCode(barCode);
			 goods.setSaleSum(Integer.parseInt(num));
			 List<PurchaseApplyGoodsImage> imageList  =  new ArrayList<PurchaseApplyGoodsImage>();//新建一个图片集合
			for (int j = 2; j < split3.length; j++) {
				PurchaseApplyGoodsImage goodsImage = new  PurchaseApplyGoodsImage();
				String imgUrl = split3[j];
				if(!"!-!".equals(imgUrl)){
					goodsImage.setImgUrl(imgUrl.replaceFirst(baseurl, ""));
					imageList.add(goodsImage);
					goods.setPurchaseApplyGoodsImageList(imageList);
				}
			}
			goodsList.add(goods);
		}
		mv.getModelMap().put("goodsList", goodsList);
		}
		
		mv.getModelMap().put("baseurl", baseurl);
		mv.getModelMap().put("cuturl", cuturl);
		mv.setViewName("/contact-information");
		return   mv ;
	}
	
	
	@RequestMapping(value="/toConfirm")
	@ResponseBody
	public  MessageResultInfo  toConfirm(HttpServletRequest  httpServletRequest  ,@RequestParam(required = false) String json){
		MessageResultInfo messageResultInfo = new MessageResultInfo();
		try {
			if(json != null){
				InsertPurchaseReqDto dto = JsonUtils.jsonString2Bean(json, InsertPurchaseReqDto.class);
				String returnStr = purchaseServiceImpl.insertInsertPurchaseReqDto(dto);
				logger.error("提交采购单请求数据______________"+json);
				messageResultInfo = JsonUtils.jsonString2Bean(returnStr, MessageResultInfo.class);
				messageResultInfo.setResultInfo(dto.getPurchaseOrder().getPurPhone());
			}
		} catch (Exception e) {
			logger.error("提交采购单失败!",e);
			messageResultInfo.setResultCode("提交采购单失败");
		}
		return messageResultInfo;
	}

	/**
	 * 
	 * 方法描述：   方法总结
	 * 业务逻辑说明  ：提交采购单成功页面
	 * 3、处理…………<br/>
	 * @Title: toPurchaseSubmittedSuccessfully 
	 * @date 2017年3月24日 下午17:31:05
	 * @author 刘兴涛
	 * @modifier 
	 * @modifydate 
	 * @return
	 */
	@RequestMapping(value="/toPurchaseSubmittedSuccessfully")
	public  ModelAndView  toPurchaseSubmittedSuccessfully(HttpServletRequest  request){
		ModelAndView mv = new ModelAndView();
		String purPhone =  request.getParameter("purPhone");
		mv.getModelMap().put("purPhone", purPhone);
		mv.setViewName("/procurement-successful");
		return   mv ;
	}
 
	@RequestMapping(value="/prev")
	public  ModelAndView  prev(HttpServletRequest  request){
		
		ModelAndView mv = new ModelAndView();
		logger.info("进入prev页面------------------");
		//从缓存中获取accessToken
		Properties properties=null;
		try {
			properties = PropertiesUtil.getProperties("application.properties");
		} catch (IOException e) {
		}
		String appId = (String)properties.get("appid");
		String secret = (String)properties.get("appsecret");
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put(PropertiesUtil.GRANT_TYPE_NAME, PropertiesUtil.GRANT_TYPE_VALUE);
		map.put(PropertiesUtil.APPID_NAME, appId);
		map.put(PropertiesUtil.SECRET_NAME, secret);
		//重新获取accessToken
		String token =weChatAccessTokenService.getToken(map, appId, secret);
		logger.info("accessToken------------------"+token);
		// 写入缓存
		redisClientTemplate.set("accessToken", token, 7100);
		String purPhone =  request.getParameter("purPhone");
		mv.getModelMap().put("purPhone", purPhone);
		mv.setViewName("/key");
		return   mv ;
	}
	
	/**
	 * SHA-1加密
	 * 
	 * @param decript
	 * @return
	 */
	private static String getSha1(String decript) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			logger.error("加密异常，出现错误：信息：{}", e.getLocalizedMessage());
		}
		return "";
	}
	
	public static void main(String[] args) {
		
		String  d="69204599050366,2,-,69204599050365,3,-";
		String[] split = d.split(",-,");
		System.out.println(split);
		
	}
}
