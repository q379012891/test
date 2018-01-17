package com.blgroup.jkl.util;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLException;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import com.blgroup.jkl.util.utilBean.IdleConnectionTimeoutThread;
import com.blgroup.jkl.util.utilBean.ServiceException;

import net.sf.json.JSONObject;

/**
 * http工具类
 * @author JZM48
 * @Class Name HttpUtil
 */
public class HttpUtilsV2 {
protected final static Logger LOGGER = LoggerFactory.getLogger(HttpUtilsV2.class);
	
	// 设置HTTP数据传输以及处理超时时间
	// is the timeout to receive data (socket timeout).
	protected final static int HTTP_SOCKET_TIMEOUT = 97 * 1000;
	// The timeout until a connection with the server is established
	private static final int HTTP_CONNNECT_TIMEOUT = 17 * 1000;
	//从连接池获取连接的超时时间，如果连接池里连接都被用了，且超过会抛出超时异常
	private static final int HTTP_CONNNECT_REQUEST_TIMEOUT = 10 * 1000;
	
	protected final static String DEFAULT_ENCODING = "UTF-8";
	protected final static String JSON_CONTENT_TYPE = "application/json";
	
	private static final int IDLE_TIMEOUT_INTERVAL = 3000;
	/**
	 * @see IdleConnectionTimeoutThread#setTimeoutInterval(long)
	 */
	private static final int IDLE_HTTP_CONNNECT_TIMEOUT = 10000;
	
	protected static RequestConfig defaultRequestConfig = null;
	
	private static IdleConnectionTimeoutThread idleConnectionTimeoutThread = null;
	
	private final static ThreadLocal<CloseableHttpClient> THREAD_HTTP_CLENTS = new ThreadLocal<CloseableHttpClient>() {};
	
	private static PoolingHttpClientConnectionManager clientConnectionManager = null;
	
	// MaxTotal表示连接池最大并发连接数
	private final static int DEFALUT_CLIENT_CONNECTION_MAX_TTL = 3500;
	
	// DefaultMaxPerRout表示单路由的最大并发连接数，假设你的业务系统需要调用A和B这两个外部系统的http接口，
	// 那么如果DefaultMaxPerRout=100，那么调用A系统的http接口时，最大并发数就是100。
	private final static int DEFALUT_CLIENT_CONNECTION_MAX_PER_ROUTE_TTL = 1200;
	
	// 尽量不要设置超时重试次数
	private final static boolean HTTP_REQUEST_RETRY_ABLE = false;
	
	static {
		
		Integer clientConnectionMaxTtl= null, clientConnectionMaxPerRouteTtl = null;
		
		try{
			
			clientConnectionMaxTtl = Integer.parseInt(System.getProperty("httpclient.conn.max.total"));
			clientConnectionMaxPerRouteTtl = Integer.parseInt(System.getProperty("hhttpclient.conn.max.per.route"));
		}
		catch (Exception ex){
			
		}
		
		if (clientConnectionMaxTtl== null) {
			
			clientConnectionMaxTtl = DEFALUT_CLIENT_CONNECTION_MAX_TTL;
		}
		
		if (clientConnectionMaxPerRouteTtl== null) {
			
			clientConnectionMaxPerRouteTtl = DEFALUT_CLIENT_CONNECTION_MAX_PER_ROUTE_TTL;
		}
		
		clientConnectionManager = new PoolingHttpClientConnectionManager();
        // Increase max total connection to 200
		clientConnectionManager.setMaxTotal(clientConnectionMaxTtl);
		clientConnectionManager.setDefaultMaxPerRoute(clientConnectionMaxPerRouteTtl);
		
		addConnectionManager(clientConnectionManager);
	}
	
	public synchronized static void addConnectionManager(HttpClientConnectionManager connectionManager) {
		
		if (idleConnectionTimeoutThread == null) {
			idleConnectionTimeoutThread = new IdleConnectionTimeoutThread();
			idleConnectionTimeoutThread.setTimeoutInterval(IDLE_TIMEOUT_INTERVAL);
			idleConnectionTimeoutThread.setConnectionTimeout(IDLE_HTTP_CONNNECT_TIMEOUT);
			idleConnectionTimeoutThread.start();
		}
		idleConnectionTimeoutThread.addConnectionManager(connectionManager);
	}
	
	/**
	 * @since 3.1
	 */
	public synchronized static void removeConnectionManager(HttpClientConnectionManager connectionManager) {
		if (idleConnectionTimeoutThread == null) {
			return;
		}
		idleConnectionTimeoutThread.removeConnectionManager(connectionManager);
	}
	
	/***
	 * Get instance of {@link CloseableHttpClient}
	 * 
	 * @param needRetry indicate whether need try, default as true
	 * @return
	 */
	protected static final CloseableHttpClient getInstance (boolean needRetry) {
		
		HttpRequestRetryHandler retryHandler = null;
		CloseableHttpClient httpClient = THREAD_HTTP_CLENTS.get();
        if ( httpClient == null ) {
        	
        	if ( needRetry ) {
    			
    			// Create retry handler with 3 times
    	    	retryHandler = new HttpRequestRetryHandler() {

    	            public boolean retryRequest( IOException exception, int executionCount, HttpContext context) {
    	            	
    	                if (executionCount >= 3) {  return false; } // 如果已经重试了5次，就放弃
    	                if (exception instanceof InterruptedIOException) {  return false; }
    	                if (exception instanceof UnknownHostException)  {  return false; } // 目标服务器不可达
    	                if (exception instanceof ConnectTimeoutException) {  return false; } //连接被拒绝
    	                if (exception instanceof SSLException) {  return false; } // ssl握手异常
    	                
    	                HttpClientContext clientContext = HttpClientContext.adapt(context);
    	                HttpRequest request = clientContext.getRequest();
    	                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
    	                if (idempotent) {
    	                    // 如果请求是幂等的，就再次尝试
    	                    return true;
    	                }
    	                return false;
    	            }
    	        }; 
    		}
        	
        	httpClient = HttpClients.custom()
        			.setConnectionManager(clientConnectionManager).setRetryHandler(retryHandler).build();
        	THREAD_HTTP_CLENTS.set(httpClient);
        }
		
        return httpClient;
	}
	
	/***
	 * Fire Http Restful Request
	 * @param uriRequest
	 * @return instance of {@link CloseableHttpResponse}
	 * @throws ServiceException
	 */
	protected static CloseableHttpResponse fireHttpRestRequest(HttpUriRequest uriRequest) throws ServiceException {
		
		return HttpUtilsV2.fireHttpRestRequest(uriRequest, HTTP_SOCKET_TIMEOUT, HTTP_REQUEST_RETRY_ABLE);
	}
	
	/***
	 * Fire Http Restful Request
	 * @param uriRequest
	 * @param needRetry
	 * @return
	 * @throws ServiceException
	 */
	protected static CloseableHttpResponse fireHttpRestRequest(HttpUriRequest uriRequest, boolean needRetry) throws ServiceException {
		
		return HttpUtilsV2.fireHttpRestRequest(uriRequest, HTTP_SOCKET_TIMEOUT, needRetry);
	}
	
	/***
	 * Fire Http Restful Request
	 * @param uriRequest
	 * @param httpRequestTimeout
	 * @return
	 * @throws ServiceException
	 */
	protected static CloseableHttpResponse fireHttpRestRequest(HttpUriRequest uriRequest, int httpRequestTimeout) throws ServiceException {
		
		return HttpUtilsV2.fireHttpRestRequest(uriRequest, httpRequestTimeout, HTTP_REQUEST_RETRY_ABLE);
	}

	/***
	 * Fire Http Restful Request
	 * @param uriRequest
	 * @param connectionTimeout
	 * @return instance of {@link CloseableHttpResponse}
	 * @throws ServiceException
	 */
	protected static CloseableHttpResponse fireHttpRestRequest(HttpUriRequest uriRequest, int httpRequestTimeout, boolean needRetry) throws ServiceException {
		
		CloseableHttpResponse httpResponse = null;
		
		//SET RequestConfig for instance of HttpRequestBase
		if (uriRequest != null && uriRequest instanceof HttpRequestBase) {
			
			synchronized (uriRequest){
				
				RequestConfig requestConfig = HttpUtilsV2.buildRequestConfig(httpRequestTimeout);
				
				((HttpRequestBase)uriRequest).setConfig(requestConfig);
				
				// 设置Http Post数据 
		        try {
		        	httpResponse = HttpUtilsV2.getInstance(needRetry).execute(uriRequest);
		        } catch (Exception e) {
		        	
		        	LOGGER.error("fireHttpRestRequest", e);
		        	throw new ServiceException(ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode(), 
		        			ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getMemo() + getRootCause(e).getMessage() );
		        } finally {
		        	
		        }
			}
		}
				
		return httpResponse;
	}
	
	/***
	 * build RequestConfig
	 * @param httpRequestTimeout
	 * @return instance of {@link RequestConfig}
	 */
	protected static final RequestConfig buildRequestConfig(int httpRequestTimeout){
		
		if ( HTTP_SOCKET_TIMEOUT==httpRequestTimeout ) {
			
			if ( defaultRequestConfig == null) {
				
				defaultRequestConfig = RequestConfig.custom()
		                .setSocketTimeout(HttpUtilsV2.HTTP_SOCKET_TIMEOUT)
		                .setConnectTimeout(HttpUtilsV2.HTTP_CONNNECT_TIMEOUT)
		                .setConnectionRequestTimeout(HttpUtilsV2.HTTP_CONNNECT_REQUEST_TIMEOUT)
		                .setStaleConnectionCheckEnabled(true)
		                .build();
			}
			return defaultRequestConfig;
		}
		else{
			return RequestConfig.custom()
					.setSocketTimeout(httpRequestTimeout)
	                .setConnectTimeout(httpRequestTimeout)
	                .setConnectionRequestTimeout(HttpUtilsV2.HTTP_CONNNECT_REQUEST_TIMEOUT)
	                .setStaleConnectionCheckEnabled(true)
	                .build();
		}
	}
	
	/***
	 * Build HttpRequest URi
	 * @param url
	 * @param method
	 * @param paramMap
	 * @return
	 */
	protected static java.net.URI buildUri(String url, String method, Map<String, Object> paramMap) {
		
		java.net.URI uri = null;
		URIBuilder uriBuilder = null;
		try {
			
			if ( !StringUtils.isEmpty(method) ) {
				
				url += "/" + method;
			}
			uriBuilder = new URIBuilder(url);
			
			if ( !CollectionUtils.isEmpty(paramMap) ) {
				
				Set<String> keySet = paramMap.keySet();
				for (String key : keySet) {
					Object value = paramMap.get(key);
					if (value != null) {
						uriBuilder.addParameter(key, value.toString());
					}
				}
			}
			uri = uriBuilder.build();
			
		} catch (URISyntaxException e1) {
		}
		
		return uri;
	}
	
	/**
     * 发送Get请求工具方法
     * @Methods Name HttpGet
     * @Create In Dec 30, 2014 By lihongfei
     * @param url
     * @param paramMap
     * @return String
     */
    @SuppressWarnings({ "rawtypes" })
    public static String HttpGet(String url, Map paramMap) throws ServiceException {
    	
    	return HttpUtilsV2.HttpGet(url, null, paramMap);
    }
	
	/**
     * 发送Get请求工具方法
     * @Methods Name HttpGet
     * @Create In Dec 30, 2014 By lihongfei
     * @param url
     * @param method
     * @param paramMap
     * @return String
     */
    @SuppressWarnings({ "rawtypes" })
    public static String HttpGet(String url, 
    		String method, Map paramMap) throws ServiceException {
    	
    	return HttpUtilsV2.HttpGet(url, method, paramMap, DEFAULT_ENCODING);
    }
    
    /**
     * 发送Get请求工具方法
     * @Methods Name HttpGet
     * @Create In Dec 30, 2014 By lihongfei
     * @param url
     * @param method
     * @param paramMap
     * @param encoding
     * @return String
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static String HttpGet(String url, 
			String method, Map paramMap, String encoding) throws ServiceException {
    	
    	LOGGER.debug("HttpGet url is {},method is {}, paramMap is {}, encoding is {} ",new Object[]{ url, method, paramMap, encoding});
    	
    	StopWatch sw = new StopWatch();
    	sw.start();
    	
    	String response = null;
    	
    	CloseableHttpResponse httpResponse = null;
		
        java.net.URI uri = HttpUtilsV2.buildUri(url, method, paramMap);
        
        if ( StringUtils.isEmpty(encoding) ) {
        	
        	encoding = DEFAULT_ENCODING;
        }
		
		if ( uri != null) {
			
			HttpGet getMethod = new HttpGet(uri);
			/*getMethod.addHeader(HttpHeaders.CONTENT_TYPE, HttpUtilsV2.JSON_CONTENT_TYPE);*/
			getMethod.addHeader(HttpHeaders.CONTENT_ENCODING, encoding);
			
			// 设置Http Post数据       
	        try {
	        	httpResponse = HttpUtilsV2.fireHttpRestRequest(getMethod);
	        	
	        	StatusLine statusLine = httpResponse.getStatusLine();
	        	if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
	        		
	        		HttpEntity entity = httpResponse.getEntity(); 
	        		if ( entity != null) {
	        			response = EntityUtils.toString(entity, encoding);
	        		}
	        	}
	        } catch (ServiceException  e) {
	        	LOGGER.error("HttpGet url is {},paras is {},exception is {}",new Object[]{url, paramMap, e});
	        	throw e;
	        } catch (Exception  e1) {
	        	LOGGER.error("HttpGet url is {},paras is {},exception is {}",new Object[]{url, paramMap, e1});
	        	throw new ServiceException(ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode(),
	        			ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getMemo() + getRootCause(e1).getMessage() );
	        } finally {
	        	
	        	closeConnection(getMethod, httpResponse);
	        } 
		}
		
		sw.stop();
		
        LOGGER.debug("URL is {}, method is {},response is {}, invoke time cost is {} ms. ", new Object[]{url, method, response, sw.getTotalTimeMillis()});
        return response;
    }
    
    /**
     * 发送post请求工具方法
     * @Methods Name HttpPost
     * @Create In 2014年10月28日 By wangfei
     * @param url
     * @param method
     * @param paramMap
     * @return String
     */
    @SuppressWarnings("rawtypes")
	public static String HttpPost(String url, 
			String method, Map paramMap) throws ServiceException {
    	
    	return HttpUtilsV2.HttpPost(url, method, paramMap, HttpUtilsV2.HTTP_CONNNECT_TIMEOUT, HttpUtilsV2.HTTP_REQUEST_RETRY_ABLE);
    }
    
    /***
     * HttpPost Method
     * @param url
     * @param method
     * @param paramMap
     * @param needRetry
     * @return
     * @throws ServiceException
     */
    @SuppressWarnings({ "rawtypes" })
    public static String HttpPost(String url, 
    		String method, Map paramMap, boolean needRetry) throws ServiceException {
    	
    	return HttpUtilsV2.HttpPost(url, method, paramMap, HttpUtilsV2.HTTP_CONNNECT_TIMEOUT, needRetry);
    }
    
    /**
     * 发送post请求工具方法
     * @Methods Name HttpPost
     * @Create In 2014年10月28日 By wangfei
     * @param url
     * @param method
     * @param paramMap
     * @param httpRequestTimeout
     * @return String
     */
    @SuppressWarnings("rawtypes")
	public static String HttpPost(String url, 
			String method, Map paramMap, int httpRequestTimeout) throws ServiceException {
    	
    	return HttpUtilsV2.HttpPost(url, method, paramMap, httpRequestTimeout, HttpUtilsV2.HTTP_REQUEST_RETRY_ABLE);
    }
    
    /**
     * 发送post请求工具方法
     * @Methods Name HttpPost
     * @Create In 2014年10月28日 By wangfei
     * @param url
     * @param method
     * @param paramMap
     * @param connectionTimeout
     * @param needRetry
     * @return String
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static String HttpPost(String url, String method, 
    		Map paramMap, int httpRequestTimeout, boolean needRetry) throws ServiceException {
    	
    	LOGGER.debug("HttpPost url is {}, method is {}, paramMap is {}, httpRequestTimeout is {}, needRetry is {}", 
    			new Object[]{url, method, paramMap, httpRequestTimeout, needRetry});
    	
    	StopWatch sw = new StopWatch();
    	sw.start();
    	
    	String response = null;
    	
    	CloseableHttpResponse httpResponse = null;
        
        java.net.URI uri = HttpUtilsV2.buildUri(url, method, paramMap);
		
		if ( uri != null) {
			
			HttpPost postMethod = new HttpPost(uri);
			postMethod.addHeader(HttpHeaders.CONTENT_TYPE, HttpUtilsV2.JSON_CONTENT_TYPE);
			
			// 设置Http Post数据       
	        try {
	        	httpResponse = HttpUtilsV2.fireHttpRestRequest(postMethod, needRetry);
	        	
	        	StatusLine statusLine = httpResponse.getStatusLine();
	        	if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
	        		
	        		HttpEntity entity = httpResponse.getEntity(); 
	        		if ( entity != null) {
	        			response = EntityUtils.toString(entity, DEFAULT_ENCODING);
	        		}
	        	}
	        } catch (ServiceException  e) {
	        	LOGGER.error("HttpGet url is {},paras is {},exception is {}",new Object[]{url, paramMap, e});
	        	throw e;
	        } catch (Exception  e1) {
	        	LOGGER.error("HttpGet url is {},paras is {},exception is {}",new Object[]{url, paramMap, e1});
	        	throw new ServiceException(ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode(), 
	        			ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getMemo() + getRootCause(e1).getMessage() );
	        } finally {
	        	
	        	closeConnection(postMethod, httpResponse);
	        }
		}
		
		sw.stop();
        LOGGER.debug("URL is {},method is {},response is {}, invoke time cost is {} ms. ", new Object[]{url, method, response, sw.getTotalTimeMillis()});
        return response;
		
    }
    
    /***
     * 发送post请求工具方法 , paramMap as requestBody, JSON 
     * @param url
     * @param paramMap
     * @return
     * @throws ServiceException 
     */
    public static String HttpPostByJson(String url, Map<String,String> paramMap) throws ServiceException {
    	
    	return HttpUtilsV2.HttpPostByJson(url, paramMap, HttpUtilsV2.HTTP_REQUEST_RETRY_ABLE);
    }
    
    /***
     * 发送post请求工具方法 , paramMap as requestBody, JSON 
     * @param url
     * @param paramMap
     * @return
     */
    public static String HttpPostByJson(String url, Map<String,String> paramMap, boolean needRetry) throws ServiceException {
    	
    	LOGGER.debug("HttpPostByJson url is {}, paramMap is {}, needRetry is {}", new Object[]{url, paramMap, needRetry});
    	
    	StopWatch sw = new StopWatch();
    	sw.start();
    	
    	String response = null;
        
    	CloseableHttpResponse httpResponse = null;
		
        java.net.URI uri = HttpUtilsV2.buildUri(url, null, null);
		
		if ( uri != null) {
			
			HttpPost postMethod = new HttpPost(uri);
			postMethod.addHeader(HttpHeaders.CONTENT_TYPE, HttpUtilsV2.JSON_CONTENT_TYPE);
			postMethod.addHeader(HttpHeaders.CONTENT_ENCODING, HttpUtilsV2.DEFAULT_ENCODING);
			
			if (!CollectionUtils.isEmpty(paramMap)) {

				JSONObject jsonObj2 = null;

				jsonObj2 = JSONObject.fromObject(paramMap);

				if (jsonObj2 != null) {

					/*StringEntity requestEntity = new StringEntity(jsonObj2.toString(), ContentType.create("plain/text", HttpUtilsV2.DEFAULT_ENCODING));
					requestEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, HttpUtilsV2.JSON_CONTENT_TYPE));
					requestEntity.setChunked(true);
					postMethod.setEntity(requestEntity);*/
					
					StringEntity requestEntity = new StringEntity(jsonObj2.toString(), ContentType.create(HttpUtilsV2.JSON_CONTENT_TYPE, Consts.UTF_8));
					
					postMethod.setEntity(requestEntity);
				}
			}
			
			// 设置Http Post数据       
	        try {
	        	httpResponse = HttpUtilsV2.fireHttpRestRequest(postMethod, needRetry);
	        	
	        	StatusLine statusLine = httpResponse.getStatusLine();
	        	if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
	        		
	        		HttpEntity entity = httpResponse.getEntity(); 
	        		if ( entity != null) {
	        			response = EntityUtils.toString(entity, HttpUtilsV2.DEFAULT_ENCODING);
	        		}
	        	}
        		
	        } catch (ServiceException  e) {
	        	LOGGER.error("HttpGet url is {},paras is {},exception is {}",new Object[]{url, paramMap, e});
	        	throw e;
	        } catch (Exception  e1) {
	        	LOGGER.error("HttpGet url is {},paras is {},exception is {}",new Object[]{url, paramMap, e1});
	        	throw new ServiceException(ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode(), 
	        			ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getMemo() + getRootCause(e1).getMessage() );
	        } finally {
	        	
	        	closeConnection(postMethod, httpResponse);
	        }
		}
		sw.stop();
		
        LOGGER.debug("doPost url is {}, response is {}, invoke time cost is {} ms.", new Object[]{url, response, sw.getTotalTimeMillis()});
        return response;
    }
    
    /**
     * POST HTTP Request
     * @param url
     * @param json
     * @param httpRequestTimeout 应用自定义HTTP_REQUEST_TIMEOUT, 如果为-1, 则取默认值
     * @return HTTP Response
     */
    public static String doPost(String url, String json, int httpRequestTimeout) throws ServiceException {
    	
    	return HttpUtilsV2.doPost(url, json, httpRequestTimeout, HttpUtilsV2.HTTP_REQUEST_RETRY_ABLE);
    }
    
    /***
     * POST HTTP Request
     * @param url
     * @param json
     * @param connectionTimeout
     * @param needRetry
     * @return
     */
    public static String doPost(String url, String json, int httpRequestTimeout, boolean needRetry) throws ServiceException {
    	
    	LOGGER.debug("doPost url is {}, json is {}, httpRequestTimeout is {}, needRetry is {}", 
    			new Object[]{url, json, httpRequestTimeout, needRetry});
    	
    	StopWatch sw = new StopWatch();
    	sw.start();
    	
    	String response = null;
        
    	CloseableHttpResponse httpResponse = null;
		
        java.net.URI uri = HttpUtilsV2.buildUri(url, null, null);
		
		if ( uri != null) {
			
			HttpPost postMethod = new HttpPost(uri);
			postMethod.addHeader(HttpHeaders.CONTENT_TYPE, HttpUtilsV2.JSON_CONTENT_TYPE);
			postMethod.addHeader(HttpHeaders.CONTENT_ENCODING, HttpUtilsV2.DEFAULT_ENCODING);
			
			/*StringEntity requestEntity = new StringEntity(json, ContentType.create("plain/text", HttpUtilsV2.DEFAULT_ENCODING));
			requestEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, HttpUtilsV2.JSON_CONTENT_TYPE)); 
			requestEntity.setChunked(true);
			
			postMethod.setEntity(requestEntity);*/
			
			StringEntity requestEntity = new StringEntity(json.toString(), ContentType.create(HttpUtilsV2.JSON_CONTENT_TYPE, Consts.UTF_8));
			
			postMethod.setEntity(requestEntity);
			
			
			// 设置Http Post数据       
	        try {
	        	httpResponse = HttpUtilsV2.fireHttpRestRequest(postMethod, httpRequestTimeout, needRetry);
	        	
	        	StatusLine statusLine = httpResponse.getStatusLine();
	        	if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
	        		
	        		HttpEntity entity = httpResponse.getEntity(); 
	        		if ( entity != null) {
	        			response = EntityUtils.toString(entity, HttpUtilsV2.DEFAULT_ENCODING);
	        		}
	        	}
	        } catch (ServiceException  e) {
	        	LOGGER.error("doPost url is {},paras is {},exception is {}",new Object[]{url, json, e});
	        	throw e;
	        } catch (Exception  e1) {
	        	LOGGER.error("doPost url is {},paras is {},exception is {}",new Object[]{url, json, e1});
	        	throw new ServiceException(ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode(), 
	        			ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getMemo() + getRootCause(e1).getMessage());
	        } finally {
	        	
	        	closeConnection(postMethod, httpResponse);
	        }
		}
		
		sw.stop();
        LOGGER.debug("doPost url is {}, response is {}, invoke time cost is {} ms.", new Object[]{url, response, sw.getTotalTimeMillis()});
        return response;
    }
   
    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     * @param url  请求的URL地址
     * @param params  请求的查询参数,可以为null
     * @return 返回请求响应的HTML
     */
    public static String doPost(String url, String json) throws ServiceException {
        
    	return doPost(url, json, HttpUtilsV2.HTTP_SOCKET_TIMEOUT, HttpUtilsV2.HTTP_REQUEST_RETRY_ABLE);
    }
    
    /***
     * 执行一个HTTP POST请求，返回请求响应的HTML
     * @param url
     * @param json
     * @param needRetry
     * @return
     */
    public static String doPost(String url, String json, boolean needRetry) throws ServiceException {
        
    	return doPost(url, json, HttpUtilsV2.HTTP_SOCKET_TIMEOUT, needRetry);
    }

    /**
     * Check Object 是否为NULL
     * @param target
     * @return
     */
    public static boolean checkNull(Object target) {
    	
        if (target == null || "".equals(target.toString().trim()) || "null".equalsIgnoreCase(target.toString().trim())) {
            return true;
        }
        return false;
    }

    /***
     * Do Post HTTPS Request with Cert ignored
     * @param url
     * @param json
     * @return
     */
    public static String doPostHttpsIgnoreCert(String url, String json) throws ServiceException {
    	
    	
    	StopWatch sw = new StopWatch();
    	sw.start();
    	
    	String response = null;
        
    	CloseableHttpResponse httpResponse = null;
		
        java.net.URI uri = HttpUtilsV2.buildUri(url, null, null);
		
		if ( uri != null) {
			
			HttpPost postMethod = new HttpPost(uri);
			postMethod.addHeader(HttpHeaders.CONTENT_TYPE, HttpUtilsV2.JSON_CONTENT_TYPE);
			postMethod.addHeader(HttpHeaders.CONTENT_ENCODING, HttpUtilsV2.DEFAULT_ENCODING);
			
			/*StringEntity requestEntity = new StringEntity(json, ContentType.create("plain/text", HttpUtilsV2.DEFAULT_ENCODING));
			requestEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, HttpUtilsV2.JSON_CONTENT_TYPE)); 
			requestEntity.setChunked(true);
			
			postMethod.setEntity(requestEntity);*/
			
			StringEntity requestEntity = new StringEntity(json.toString(), ContentType.create(HttpUtilsV2.JSON_CONTENT_TYPE, Consts.UTF_8));
			
			postMethod.setEntity(requestEntity);
			
			
			Protocol ptlHttps = new Protocol("https", new SSLProtocolSocketFactory(), 443);   
			String protocolId= "https" + System.currentTimeMillis();
			LOGGER.debug("protocolId", protocolId);
			Protocol.registerProtocol("https", ptlHttps);
			
			// 设置Http Post数据       
	        try {
	        	httpResponse = HttpUtilsV2.fireHttpRestRequest(postMethod);
	        	
	        	StatusLine statusLine = httpResponse.getStatusLine();
	        	if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
	        		
	        		HttpEntity entity = httpResponse.getEntity(); 
	        		if ( entity != null) {
	        			response = EntityUtils.toString(entity, HttpUtilsV2.DEFAULT_ENCODING);
	        		}
	        	}
	        } catch (ServiceException  e) {
	        	LOGGER.error("doPostHttpsIgnoreCert url is {},paras is {},exception is {}",new Object[]{url, json, e});
	        	throw e;
	        } catch (Exception  e1) {
	        	LOGGER.error("doPostHttpsIgnoreCert url is {},paras is {},exception is {}",new Object[]{url, json, e1});
	        	throw new ServiceException(ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode(), 
	        			ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getMemo() + getRootCause(e1).getMessage() );
	        } finally {
        	
				Protocol.unregisterProtocol("https");
	        	closeConnection(postMethod, httpResponse);
	        }
		}
		
		sw.stop();
        LOGGER.debug("doPostHttpsIgnoreCert url is {},input data is {},response is {}, invoke time cost is {} ms.",
        		new Object[]{url, json, response, sw.getTotalTimeMillis()});
        return response;
    }
    
    /** 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    /***
     * safely close http connection
     * 
     * @param httpRequest
     * @param httpResponse
     */
    public static void closeConnection( HttpRequestBase httpRequest, CloseableHttpResponse httpResponse ){
    
    	if ( null != httpResponse ) {
    		
    		try {
        		//Force consume HttpResponse Entity
    			EntityUtils.consume(httpResponse.getEntity());
    		} catch (final IOException ignore) {
    		}
    		
			//using IOUtils to force close HttpResponse
			IOUtils.closeQuietly( httpResponse );
		}
    	
    	if ( null != httpRequest ) {
    		
    		// be sure the connection is released back to the connection manager
    		int httpSocketTimeout = -1;
    		int httpConnectionTimeout = -1;
    		if ( null != httpRequest.getConfig() ) {
    			httpSocketTimeout = httpRequest.getConfig().getSocketTimeout();
    			httpConnectionTimeout = httpRequest.getConfig().getConnectTimeout();
    		}
    		LOGGER.debug("Current_TID {}, HttpSockTimeOut {}, HttpConnectionTimeOut {}", 
    				Thread.currentThread().getId(), httpSocketTimeout, httpConnectionTimeout);
    		httpRequest.releaseConnection();
    	}
    	
		THREAD_HTTP_CLENTS.remove();
		
    }
    
    /***
     * Get Root cause of Exception
     * @param t
     * @return
     */
    public static Throwable getRootCause(Throwable t) {
		
		Throwable rootCause = ExceptionUtils.getRootCause(t);
		
		if ( rootCause!= null){
			
			return rootCause;
		}
		else return t;
	}

}
