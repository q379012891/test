package com.blgroup.jkl.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.blgroup.b2b.common.entity.MessageResultInfo;
import com.blgroup.b2b.common.utils.JsonUtils;
import com.blgroup.jkl.bean.Item;
import com.blgroup.jkl.service.IItemService;
import com.blgroup.jkl.util.PropertiesUtil;

/**
 * 
 * 推荐商品制器
 * 
 * @ClassName: RecommendCommoditiesController 
 * @author XJM29
 * @date 2017年3月14日 下午10:58:51
 * @version V2.0 
 *
 */
@Controller
public class RecommendCommoditiesController {
	private static Logger log = LoggerFactory.getLogger(RecommendCommoditiesController.class);
	@Autowired
	IItemService ItemServiceImpl;
	
	@RequestMapping(value="/recommendCommodities")
	public ModelAndView sweepCodePurchasing(){
		ModelAndView mv = new ModelAndView();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("isRecommend", "1");
		String item = ItemServiceImpl.getItem(map);
		MessageResultInfo messageResultInfo;
		List<Item> items = new ArrayList<Item>();
		Properties properties=null;
		String baseurl = null;
		String cuturl = null;
		try{
			properties = PropertiesUtil.getProperties("application.properties");
			baseurl = (String)properties.get("img.baseurl");
			cuturl = (String)properties.get("img.cuturl");
			messageResultInfo = JsonUtils.jsonString2Bean(item, MessageResultInfo.class);
			String pageInfoStr=JsonUtils.object2JsonString(messageResultInfo.getResultInfo());
			items = JsonUtils.josn2List(pageInfoStr, Item.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
		mv.getModelMap().put("baseurl", baseurl);
		mv.getModelMap().put("cuturl", cuturl);
		mv.getModelMap().put("items", items);
		mv.getModelMap().put("appId", "wxba2a86a096369cce");
		mv.setViewName("/recommendCommodities");
		return mv;
	}
}
