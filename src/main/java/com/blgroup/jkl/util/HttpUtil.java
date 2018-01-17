package com.blgroup.jkl.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blgroup.jkl.util.utilBean.IdGen;

import net.sf.json.JSONObject;

/**
 * http工具类
 * 
 * @author jzm
 *
 */
public class HttpUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);
	private static final Integer connectionTimeOut = 2000; // 连接2秒超时
	private static final Integer soTimeOut = 3000; // 读取3秒超时

	/**
	 * 发送post请求工具方法
	 * 
	 * @Methods Name HttpPost
	 * @param url
	 * @param method
	 * @param paramMap
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public static String HttpPost(String url, String method, Map paramMap) {
		String uid = IdGen.uuid();
		LOGGER.info(
				"*************id:{};com.bailiangroup.osp.core.service.social.utils.HttpUtil#HttpPost(String,String,Map)",
				uid);
		LOGGER.info("*************url:{},method:{}", url, method);

		String encoding = "UTF-8";
		String webUrl = url + "/" + method;
		if (encoding == null || "".equals(encoding))
			encoding = "UTF-8";
		StringBuffer sBuffer = new StringBuffer();
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// httpClient.set
		// 创建POS方法的实例
		NameValuePair[] pairs = null;
		PostMethod postMethod = new PostMethod(webUrl);
		if (paramMap != null) {
			pairs = new NameValuePair[paramMap.size()];
			Set set = paramMap.keySet();
			Iterator it = set.iterator();
			int i = 0;
			while (it.hasNext()) {
				Object key = it.next();
				Object value = paramMap.get(key);
				if (!HttpUtil.checkNull(value)) {
					pairs[i] = new NameValuePair(key.toString(), value.toString());
				}
				i++;
			}
			postMethod.setRequestBody(pairs);
		}
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeOut); // 连接2秒超时
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeOut);// 读取3秒超时
		try {
			// 执行getMethod
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + postMethod.getStatusLine());
				sBuffer = new StringBuffer();
			} else {
				sBuffer = new StringBuffer(postMethod.getResponseBodyAsString() + "");
			}
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			LOGGER.debug("Please check your provided http address!");
			LOGGER.error("uid:{};the HttpException;exception occured: {}", uid, e.getMessage());
		} catch (IOException e) {
			// 发生网络异常
			LOGGER.error("uid:{};the IOException;exception occured: {}", uid, e.getMessage());
		} finally {
			postMethod.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}
		return sBuffer.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String HttpGet(String webUrl, Map paramMap, String encoding) {
		String uid = IdGen.uuid();
		LOGGER.info(
				"*************id:{};com.bailiangroup.osp.core.service.social.utils.HttpUtil#HttpGet(String,Map,String)",
				uid);
		LOGGER.info("*************webUrl:{},encoding:{}", webUrl, encoding);

		// 设置编码格式
		if (encoding == null || "".equals(encoding)) {
			encoding = "UTF-8";
		}
		String queryString = createLinkString(paramMap);
		webUrl = webUrl + "?" + queryString;
		StringBuffer sBuffer = new StringBuffer();
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		GetMethod gettMethod = null;
		// httpClient.set
		try {
			URI uri = new URI(webUrl, false, encoding);

			gettMethod = new GetMethod(uri.toString());

			gettMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeOut); // 连接2秒超时
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeOut);// 读取3秒超时

			// 执行getMethod
			int statusCode = httpClient.executeMethod(gettMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + gettMethod.getStatusLine());
				sBuffer = new StringBuffer();
			} else {
				sBuffer = new StringBuffer(gettMethod.getResponseBodyAsString() + "");
			}
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			LOGGER.debug("Please check your provided http address!");
			LOGGER.error("uid:{};the HttpException;exception occured: {}", uid, e.getMessage());
		} catch (IOException e) {
			// 发生网络异常
			LOGGER.error("uid:{};the IOException;exception occured: {}", uid, e.getMessage());
		} finally {
			// 释放连接
			gettMethod.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
			LOGGER.info("uid:{};HttpGet close connection;", uid);
		}
		return sBuffer.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String HttpGet(String webUrl, Map paramMap, String encoding, int connectTimeout, int readTimeout) {
		String uid = IdGen.uuid();
		LOGGER.info(
				"*************id:{};com.bailiangroup.osp.core.service.social.utils.HttpUtil#HttpGet(String,Map,String)",
				uid);
		LOGGER.info("*************webUrl:{},encoding:{}", webUrl, encoding);

		// 设置编码格式
		if (encoding == null || "".equals(encoding)) {
			encoding = "UTF-8";
		}
		String queryString = createLinkString(paramMap);
		webUrl = webUrl + "?" + queryString;
		StringBuffer sBuffer = new StringBuffer();
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		GetMethod gettMethod = null;
		// httpClient.set
		try {
			URI uri = new URI(webUrl, false, encoding);

			gettMethod = new GetMethod(uri.toString());

			gettMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectTimeout); // 连接2秒超时
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(readTimeout);// 读取3秒超时

			// 执行getMethod
			int statusCode = httpClient.executeMethod(gettMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + gettMethod.getStatusLine());
				sBuffer = new StringBuffer();
			} else {
				sBuffer = new StringBuffer(gettMethod.getResponseBodyAsString() + "");
			}
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			LOGGER.debug("Please check your provided http address!");
			LOGGER.error("uid:{};the HttpException;exception occured: {}", uid, e.getMessage());
		} catch (IOException e) {
			// 发生网络异常
			LOGGER.error("uid:{};the IOException;exception occured: {}", uid, e.getMessage());
		} finally {
			// 释放连接
			gettMethod.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
			LOGGER.info("uid:{};HttpGet close connection;", uid);
		}
		return sBuffer.toString();
	}

	@SuppressWarnings("rawtypes")
	public static String HttpGet(String webUrl, Map paramMap) {
		return HttpGet(webUrl, paramMap, "GBK");
	}

	@SuppressWarnings("rawtypes")
	public static String HttpGet(String webUrl, Map paramMap, int connectTimeout, int readTimeout) {
		return HttpGet(webUrl, paramMap, "GBK", connectTimeout, readTimeout);
	}

	public static String HttpPostBody(String url, String method, String json) {
		String uid = IdGen.uuid();
		LOGGER.info(
				"id:{};com.bailiangroup.osp.core.service.social.utils.HttpUtil#HttpPostBody() url: {}; method:{}; json:{}",
				uid, url, method, json);
		String message = "";
		OutputStream os = null;
		InputStream is = null;
		try {
			String encoding = "UTF-8";
			String webUrl = url + "/" + method;
			if (encoding == null || "".equals(encoding))
				encoding = "UTF-8";
			URL httpUrl = new URL(webUrl);
			HttpURLConnection http = (HttpURLConnection) httpUrl.openConnection();
			http.setConnectTimeout(connectionTimeOut);
			http.setReadTimeout(soTimeOut);

			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);

			http.connect();
			os = http.getOutputStream();
			os.write(json.getBytes("UTF-8"));// 传入参数
			os.flush();

			is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			message = new String(jsonBytes, "UTF-8");
			LOGGER.info("HttpPostBody end: {return message: " + message + "} endTime:{" + new Date() + "}");

			return message;
		} catch (MalformedURLException e) {
			LOGGER.error("uid:{};the MalformedURLException;exception occured: {}", uid, e.getMessage());
		} catch (IOException e) {
			LOGGER.error("uid:{}; the IOException;exception occured: {}", uid, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("*********uid:{}**********调用接口出现异常,异常信息：{}", uid, e.getMessage());
		} finally {
			try {
				if (os != null)
					os.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				LOGGER.error("uid:{};the IOException;关闭输出输入流是发生网络异常！", uid);
			}
		}
		return PropertiesUtil.SYSTEM_ERROR;
	}

	public static String HttpPostBody(String url, String json) {
		return HttpPostBodyWithContentType(url, json, "application/x-www-form-urlencoded");
	}

	public static String HttpPostJson(String url, String json) {
		return HttpPostBodyWithContentType(url, json, "application/json");
	}

	public static String HttpPostBodyWithContentType(String url, String json, String contentType) {
		String uid = IdGen.uuid();
		LOGGER.info(
				"***********************id:{};com.bailiangroup.osp.core.service.social.utils.HttpUtil#HttpPostBody(String,String)",
				uid);
		LOGGER.info("***********************url: {}; json:{}", url, json);
		OutputStream os = null;
		InputStream is = null;
		String message = "";
		try {
			String encoding = "UTF-8";
			String webUrl = url;
			if (encoding == null || "".equals(encoding))
				encoding = "UTF-8";
			URL httpUrl = new URL(webUrl);
			HttpURLConnection http = (HttpURLConnection) httpUrl.openConnection();

			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type", contentType);
			http.setDoOutput(true);
			http.setDoInput(true);

			http.connect();
			os = http.getOutputStream();
			os.write(json.getBytes("UTF-8"));// 传入参数
			os.flush();

			is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			message = new String(jsonBytes, "UTF-8");
			return message;
		} catch (MalformedURLException e) {
			LOGGER.error("uid:{};the MalformedURLException;exception occured: {}", uid, e.getMessage());
		} catch (IOException e) {
			LOGGER.error("uid:{}; the IOException;exception occured: {}", uid, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("*********uid:{}**********调用接口出现异常,异常信息：{}", uid, e.getMessage());
		} finally {
			try {
				if (os != null)
					os.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				LOGGER.error("uid:{};the IOException;关闭输出输入流是发生网络异常！", uid);
			}
		}
		return PropertiesUtil.SYSTEM_ERROR;
	}

	/**
	 * 发送Get请求工具方法
	 * 
	 * @Methods Name HttpGet
	 * @param url
	 * @param method
	 * @param paramMap
	 * @return String
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String HttpGet(String url, String method, Map paramMap) {
		String uid = IdGen.uuid();
		LOGGER.info(
				"*************id:{};com.bailiangroup.osp.core.service.social.utils.HttpUtil#HttpGet(String,String,Map)",
				uid);
		LOGGER.info("*************url: {}; method:{}; Map:{}", url, paramMap);

		// 设置编码格式
		String encoding = "GBK";
		String webUrl = url + "/" + method;
		if (encoding == null || "".equals(encoding))
			encoding = "GBK";
		String queryString = createLinkString(paramMap);
		webUrl = webUrl + "?" + queryString;
		StringBuffer sBuffer = new StringBuffer();
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		GetMethod gettMethod = null;
		// httpClient.set
		try {
			URI uri = new URI(webUrl, false, encoding);

			gettMethod = new GetMethod(uri.toString());

			gettMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);

			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeOut); // 连接2秒超时
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeOut);// 读取3秒超时

			// 执行getMethod
			int statusCode = httpClient.executeMethod(gettMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + gettMethod.getStatusLine());
				sBuffer = new StringBuffer();
			} else {
				sBuffer = new StringBuffer(gettMethod.getResponseBodyAsString() + "");
			}
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			LOGGER.debug("Please check your provided http address!");
			LOGGER.error("uid:{};the HttpException;exception occured: {}", uid, e.getMessage());
		} catch (IOException e) {
			// 发生网络异常
			LOGGER.error("uid:{};the IOException;exception occured: {}", uid, e.getMessage());
		} finally {
			// 释放连接
			gettMethod.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}
		return sBuffer.toString();
	}

	/**
	 * 发送Get请求工具方法,处理参数有中文字符
	 * 
	 * @Methods Name HttpGet
	 * @param url
	 * @param method
	 * @param paramMap
	 * @return String
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String HttpGetByUtf(String url, String method, Map paramMap) {
		// 设置编码格式
		String encoding = "UTF-8";
		String webUrl = url + "/" + method;
		if (encoding == null || "".equals(encoding))
			encoding = "UTF-8";
		String queryString = createLinkString(paramMap);
		webUrl = webUrl + "?" + queryString;
		StringBuffer sBuffer = new StringBuffer();
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		GetMethod gettMethod = null;
		// httpClient.set
		try {
			URI uri = new URI(webUrl, false, encoding);

			gettMethod = new GetMethod(uri.toString());
			gettMethod.setRequestHeader("Content-type", "application/json");
			gettMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);

			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeOut); // 连接2秒超时
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeOut);// 读取3秒超时

			// 执行getMethod
			int statusCode = httpClient.executeMethod(gettMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + gettMethod.getStatusLine());
				sBuffer = new StringBuffer();
			} else {
				sBuffer = new StringBuffer(gettMethod.getResponseBodyAsString() + "");
			}
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			LOGGER.debug("Please check your provided http address!");
			LOGGER.error("exception occured: {}", e.getMessage());
		} catch (IOException e) {
			// 发生网络异常
			LOGGER.error("exception occured: {}", e.getMessage());
		} finally {
			// 释放连接
			gettMethod.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}
		return sBuffer.toString();
	}

	/**
	 * 执行一个HTTP POST请求，返回请求响应的HTML
	 * 
	 * @param url
	 *            请求的URL地址
	 * @param params
	 *            请求的查询参数,可以为null
	 * @return 返回请求响应的HTML
	 */
	@SuppressWarnings("deprecation")
	public static String doPost(String url, String json) {
		String uid = IdGen.uuid();
		LOGGER.info("*************id:{};com.bailiangroup.osp.core.service.social.utils.HttpUtil#doPost(String,String)",
				uid);
		LOGGER.info("*************url: {}; json:{};", url, json);

		String response = null;
		HttpClient httpClient = new HttpClient();
		PostMethod method = new PostMethod(url);
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeOut); // 连接2秒超时
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeOut);// 读取3秒超时
		// 设置Http Post数据
		try {
			method.setRequestBody(json);
			method.setRequestHeader("Content-type", "application/json");
			httpClient.executeMethod(method);
			// if (method.getStatusCode() == HttpStatus.SC_OK) {
			response = method.getResponseBodyAsString();
			// }
		} catch (IOException e) {
			LOGGER.error("the IOException;exception occured: {}", e.getMessage());
		} catch (Exception e) {
			LOGGER.error("the Exception;exception occured: {}", e.getMessage());
		} finally {
			method.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}
		return response;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}

	public static boolean checkNull(Object target) {
		if (target == null || "".equals(target.toString().trim())
				|| "null".equalsIgnoreCase(target.toString().trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 文件上传到微信服务器
	 * 
	 * @param fileType
	 *            文件类型
	 * @param filePath
	 *            文件路径
	 * @return JSONObject
	 * @throws Exception
	 */
	public static JSONObject uploadMedia(String filePath, String fileType, String url) throws Exception {
		String result = null;
		/*
		 * File file = new File(filePath); if (!file.exists() || !file.isFile())
		 * { throw new IOException("文件不存在"); }
		 */
		URL urlObj = new URL(url);
		// "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="+
		// getAccess_token() + "&type=" + fileType + ""
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
		con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); // post方式不能使用缓存
		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		// 设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
		// 请求正文信息
		// 第一部分：
		StringBuilder sb = new StringBuilder();
		sb.append("--"); // 必须多两道线
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + filePath + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		byte[] head = sb.toString().getBytes("utf-8");
		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);
		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new URL(filePath).openStream());
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();
		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
		out.write(foot);
		out.flush();
		out.close();
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				// System.out.println(line);
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			LOGGER.debug("发送POST请求出现异常！");
			LOGGER.error("exception occured: {}", e.getMessage());
			throw new IOException("数据读取异常");
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		JSONObject jsonObj = JSONObject.fromObject(result);
		return jsonObj;
	}

	/**
	 * 获取媒体文件
	 * 
	 * @param accessToken
	 *            接口访问凭证
	 * @param media_id
	 *            媒体文件id
	 * @param savePath
	 *            文件在服务器上的存储路径
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> getMedia(String url, String method, Map paramMap, String savePath) {
		String success = "";
		// 设置编码格式
		String encoding = "GBK";
		String webUrl = url + "/" + method;
		if (encoding == null || "".equals(encoding)) {
			encoding = "GBK";
		}
		String queryString = createLinkString(paramMap);
		webUrl = webUrl + "?" + queryString;
		StringBuffer sBuffer = new StringBuffer();
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		GetMethod gettMethod = null;
		// httpClient.set
		try {
			URI uri = new URI(webUrl, false, encoding);
			gettMethod = new GetMethod(uri.toString());
			gettMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeOut); // 连接2秒超时
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeOut);// 读取3秒超时
			// 执行getMethod
			int statusCode = httpClient.executeMethod(gettMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + gettMethod.getStatusLine());
				sBuffer = new StringBuffer();
			} else {
				sBuffer = new StringBuffer(gettMethod.getResponseBodyAsString() + "");
				if (StringUtils.isNotBlank(sBuffer) && sBuffer.toString().contains(PropertiesUtil.ERRCODE)) {
					JSONObject jsonObj = JSONObject.fromObject(sBuffer.toString());
					Object errorCode = jsonObj.get(PropertiesUtil.ERRCODE);
					if (!PropertiesUtil.SYSTEM_SUCCESS.equals(errorCode.toString())) {
						// 失败
						return ResultUtil.creObjSucResult(sBuffer.toString(), false);
					}
				}
				URL urlObj = new URL(webUrl);
				HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
				conn.setDoInput(true);
				conn.setRequestMethod("GET");

				if (!savePath.endsWith("/")) {
					savePath += "/";
				}
				String filename = conn.getHeaderField("Content-disposition").split("=")[1].replaceAll("\"", "");
				savePath = savePath + filename;
				BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
				FileOutputStream fos = new FileOutputStream(new File(savePath));
				byte[] buf = new byte[8096];
				int size = 0;
				while ((size = bis.read(buf)) != -1) {
					fos.write(buf, 0, size);
				}
				fos.close();
				bis.close();
				conn.disconnect();
				success = "{\"errcode\":0,\"errmsg\":\"OK\",\"savepath\":\"" + savePath + "\",\"filename\":\""
						+ filename + "\"}";
			}
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			LOGGER.debug("Please check your provided http address!");
			LOGGER.error("exception occured: {}", e.getMessage());
		} catch (IOException e) {
			// 发生网络异常
			LOGGER.error("exception occured: {}", e.getMessage());
		} finally {
			// 释放连接
			gettMethod.releaseConnection();
		}
		return ResultUtil.creObjSucResult(success, true);
	}
}
