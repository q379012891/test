package com.blgroup.jkl.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.blgroup.b2b.common.entity.MessageResultInfo;
import com.blgroup.b2b.common.utils.JsonUtils;
import com.blgroup.jkl.bean.BrandDto;
import com.blgroup.jkl.bean.Item;
import com.blgroup.jkl.bean.QueryBrandReqDto;
import com.blgroup.jkl.service.IBrandService;
import com.blgroup.jkl.service.IItemService;
import com.blgroup.jkl.util.PropertiesUtil;

/**
 * 
 * item商品控制器
 * @ClassName: ItemController 
 * @author XJM29
 * @date 2017年3月16日 下午4:57:20
 * @version V2.0 
 *
 */
@Controller
public class ItemController {
	private static Logger log = LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	IItemService itemServiceImpl;
	/**
	 * 
	 * 方法描述：  通过品牌id或者是否推荐 获取所以的商品
	 * 业务逻辑说明   
	 * @Title: toConfirmPurchasing 
	 * @date 2017年3月14日 下午3:21:05
	 * @author 肖金马
	 * @modifier 
	 * @modifydate 
	 * @return
	 */
	@RequestMapping(value="/toItem")
	public  ModelAndView  toItem(HttpServletRequest request,String  brandId,String  isRecommend   ){
		log.info("---------"+brandId+"-----------"+isRecommend);
		ModelAndView mv = new ModelAndView();
		HashMap<Object, Object>  map   = new  HashMap<Object, Object>();
		if(brandId!=null){
			map.put("brandId", brandId);
		}
		if(isRecommend!=null){
			map.put("isRecommend", isRecommend);
		}
		  MessageResultInfo messageResultInfo; 
		String item = itemServiceImpl.getItem(map);
		Properties properties=null;
		String baseurl = null;
		String cuturl = null;
		try {
			properties = PropertiesUtil.getProperties("application.properties");
			baseurl = (String)properties.get("img.baseurl");
			cuturl = (String)properties.get("img.cuturl");
			messageResultInfo = JsonUtils.jsonString2Bean(item, MessageResultInfo.class);
			String pageInfoStr=JsonUtils.object2JsonString(messageResultInfo.getResultInfo());
			List<Item> dateList = JsonUtils.josn2List(pageInfoStr, Item.class);
			  mv.getModelMap().put("list", dateList);
			  String tagName = URLDecoder.decode(request.getParameter("tagName"),"UTF-8");
			  mv.getModelMap().put("tagName", tagName);
			log.info(dateList+"-------------->");
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.getModelMap().put("baseurl", baseurl);
		mv.getModelMap().put("cuturl", cuturl);
		mv.setViewName("/itemPage");
		return   mv ;
		
	}
	
	
	/**
	 * 
	 * 方法描述：   方法总结
	 * 业务逻辑说明  ：条形码输入框模糊查询显示
	 * 
	 * 1、isShow10=1  时模糊查询前十位数据
	 * 2、isShow10=0  精确根据条件找讯数据
	 * @Title: shouCodeList 
	 * @date 2017年3月14日 下午2:03:49
	 * @author 肖金马
	 * @modifier 
	 * @modifydate 
	 * @param code
	 * @return
	 */
	@RequestMapping("/shouCodeList")
	@ResponseBody
	public String shouCodeList(String code,String isShow10){
		HashMap<Object, Object>  map   = new  HashMap<Object, Object>();
		map.put("barCode", code);
		map.put("isShow10", isShow10);
		String item = itemServiceImpl.getItem(map);
		 MessageResultInfo messageResultInfo; 
		try {
			messageResultInfo = JsonUtils.jsonString2Bean(item, MessageResultInfo.class);
			String pageInfoStr=JsonUtils.object2JsonString(messageResultInfo.getResultInfo());
			List<Item> dateList = JsonUtils.josn2List(pageInfoStr, Item.class);
			log.info(dateList+"-------------->");
			String data="";
			for (int i = 0; i < dateList.size(); i++) {
				String barCode = dateList.get(i).getBarCode();
				data=data+barCode+",";
			}
			return  data;
		} catch (Exception e) {
			e.printStackTrace();
			return  "";
		}
		
	}
}
