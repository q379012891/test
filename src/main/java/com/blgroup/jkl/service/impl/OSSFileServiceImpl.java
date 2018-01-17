package com.blgroup.jkl.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.blgroup.b2b.common.entity.ResponseData;
import com.blgroup.b2b.common.oss.util.ResponseMsg;
import com.blgroup.jkl.service.OSSFileService;
import com.blgroup.jkl.util.OSSUtils;

@Service
public class OSSFileServiceImpl  implements OSSFileService {

	private static final Logger logger = LoggerFactory.getLogger(OSSFileServiceImpl.class);
	
	@Autowired
	private OSSUtils ossUtils;

	@Value("${image.environment}")
	private String IMAGE_ENVIRONMENT;
	
	/**
	 * 后台上传图片
	 * @param operation 业务场景
	 * @param identify  标识码
	 * @param url 上传文件的地址
	 * @param fileName 上传文件名称  如a.text
	 * @return
	 * @throws FileNotFoundException 
	 */
	@Override
	public String uploadFile(String operation,String identify,InputStream inputStream, String fileName) {
		logger.info("===================start==============================");
		String path = null;
		try {
			String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
			long time = System.currentTimeMillis();
			String newFileNameUrl = "b2b/"+ IMAGE_ENVIRONMENT + "/" + operation + "/" + identify + "/" + time + "." + prefix;
			logger.info("=========组装新的路径===================》");
	        PutObjectResult por = ossUtils.upload(newFileNameUrl, inputStream);
	        logger.info("========="+por.getETag());
	        //path = "http://sit-b2b.oss-cn-hangzhou.aliyuncs.com/"+newFileNameUrl;
	        path = newFileNameUrl;
	        logger.info("========="+path);
	        logger.info("===================end==============================");
		} catch (Exception e) {
			logger.info("上传图片出错-----------------------",e);
			e.printStackTrace();
		} 
        return path;
	}
	
	/**
	 * 后台上传图片
	 * @param operation 业务场景
	 * @param identify  标识码
	 * @param url 上传文件的地址
	 * @param fileName 上传文件名称  如a.text
	 * @return
	 * @throws FileNotFoundException 
	 */
	@Override
	public String rspFileUpLoad(String operation,String identify,String url, String fileName) {
		logger.info("===================start==============================");
		String path = null;
		try {
			InputStream inputStream = new FileInputStream(url);
			String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
			long time = System.currentTimeMillis();
			String newFileNameUrl = "b2b/" + IMAGE_ENVIRONMENT + "/" + operation + "/" + identify + "/" + time + "." + prefix;
			logger.info("=========组装新的路径===================》");
			PutObjectResult por = ossUtils.upload(newFileNameUrl, inputStream);
			logger.info("========="+por.getETag());
			path = "http://sit-b2b.oss-cn-hangzhou.aliyuncs.com/"+newFileNameUrl;
			logger.info("========="+path);
			logger.info("===================end==============================");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return path;
	}

	 

}
