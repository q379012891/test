package com.blgroup.jkl.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.blgroup.b2b.common.entity.ImageUpload;
import com.blgroup.b2b.common.entity.MessageResultInfo;
import com.blgroup.b2b.common.utils.JsonUtils;
import com.blgroup.jkl.bean.BrandDto;
import com.blgroup.jkl.bean.QueryBrandReqDto;
import com.blgroup.jkl.service.IBrandService;
import com.blgroup.jkl.service.OSSFileService;

import net.sf.json.JSONObject;


@Controller
@RequestMapping(value = "/file")
public class FileUploadController {
	private static Logger log = LoggerFactory.getLogger(FileUploadController.class);
	
	private static final String[] DEFAULT_IMG_TYPE = {"jpg", "jpeg", "bmp", "png"};
	
	private static final long DEFAULT_MAX_IMG_SIZE = 1024 * 1024;
	
	@Autowired
	OSSFileService  oSSFileService;
	/**
     * 图片上传
     * @param imgFile
     * @return
      */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.GET)
    @ResponseBody
    public String uploadFile(HttpServletRequest  request  ,String  url) {
    	
    	log.info("上传图片从定向微信项目___________________________url"+url);
         return url;
    
    }
    
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile1(HttpServletResponse response, HttpServletRequest request)  {
    	log.info("图片上传目___________________________url");
    	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    	Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
    	MultipartFile imgFile = null;
    	for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
    		imgFile = entity.getValue();
    		break;
    	}
    	String uploadFile="";
		try {
			uploadFile = oSSFileService.uploadFile("b2b_wechat", "goods", imgFile.getInputStream(), imgFile.getOriginalFilename());
		} catch (IOException e) {
			log.error("图片上传失败",e);
			 
		}
		log.info("图片上传结束");
    	return uploadFile;
    	
    }
    @RequestMapping(value = "/prev", method = RequestMethod.GET)
    public String prev(HttpServletRequest  request  ,String  url) {
    	
    	return "prev";
    	
    }
    
    
    
}
