package com.blgroup.jkl.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.springframework.web.servlet.ModelAndView;

import com.blgroup.b2b.common.entity.MessageResultInfo;
import com.blgroup.b2b.common.utils.JsonUtils;
import com.blgroup.jkl.bean.BrandDto;
import com.blgroup.jkl.bean.QueryBrandReqDto;
import com.blgroup.jkl.service.IBrandService;
import com.blgroup.jkl.util.PropertiesUtil;

/**
 * 品牌控制器
 * @author JZM48
 *
 */
@Controller
public class BrandController {
	private static Logger log = LoggerFactory.getLogger(BrandController.class);
	
	@Autowired
	IBrandService brandServiceImpl;
	/**
	 * 
	 * 方法描述：   品牌供应商列表
	 * 业务逻辑说明   
	 * 3、处理…………<br/>
	 * @Title: toConfirmPurchasing 
	 * @date 2017年3月14日 下午3:21:05
	 * @author 肖金马
	 * @modifier 
	 * @modifydate 
	 * @return
	 */
	@RequestMapping(value="/brand")
	public  ModelAndView  CrossSupplier(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		
		String brand = brandServiceImpl.getBrand();
	    MessageResultInfo messageResultInfo;
	    List<BrandDto>  dateList  =  new  ArrayList<BrandDto>();
	    Properties properties=null;
		try {
			properties = PropertiesUtil.getProperties("application.properties");
			String baseurl = (String)properties.get("img.baseurl");
			String cuturl = (String)properties.get("img.cuturl");
			messageResultInfo = JsonUtils.jsonString2Bean(brand, MessageResultInfo.class);
			String pageInfoStr=JsonUtils.object2JsonString(messageResultInfo.getResultInfo());
		 List<QueryBrandReqDto> QueryBrandReqDto = JsonUtils.josn2List(pageInfoStr, QueryBrandReqDto.class);
		 //分出所有的标签下的商品
		    LinkedHashMap<String, BrandDto> map = new  LinkedHashMap<String, BrandDto>();
		 for (int i = 0; i < QueryBrandReqDto.size(); i++) {
			//品牌名称长度大于10用...代替
			 String brandCnName = QueryBrandReqDto.get(i).getBrandCnName();
			 if(brandCnName != "" && brandCnName != null){
				 if(brandCnName.length() > 6){
					 String subName = brandCnName.substring(0, 6);
					 subName =subName+"...";
					 QueryBrandReqDto.get(i).setBrandCnName(subName);
				 }
			 }
			 
			 String tagName = QueryBrandReqDto.get(i).getTagNames();
			 if(map.containsKey(tagName)){
				 map.get(tagName).getQueryBrandReqDto().add(QueryBrandReqDto.get(i));
				 map.get(tagName).setTagName(tagName);
			 }else{
				 List<QueryBrandReqDto>  ds=  new  ArrayList<QueryBrandReqDto>();
				 ds.add(QueryBrandReqDto.get(i));
				 BrandDto brandDto = new  BrandDto();
				 brandDto.setTagName(tagName);
				 brandDto.setQueryBrandReqDto(ds);
				 map.put(tagName, brandDto);
			 }
		}
			 log.info(map+"");
			 
			 for (Entry<String, BrandDto> entry : map.entrySet()) {
				 BrandDto value = entry.getValue();
				 dateList.add(value);
		    };
		    mv.getModelMap().put("baseurl", baseurl);
		    mv.getModelMap().put("cuturl", cuturl);
		    mv.getModelMap().put("list", dateList);
		    mv.getModelMap().put("ss", "sasa");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		mv.setViewName("/cross-supplier");
		return   mv ;
	}
}
