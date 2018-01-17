package com.blgroup.jkl.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blgroup.jkl.bean.SocialAccount;
import com.blgroup.jkl.context.MallContext;
import com.blgroup.jkl.context.SystemContext;
import com.blgroup.jkl.context.SystemContextBuilder;
import com.blgroup.jkl.context.UserContext;
import com.blgroup.jkl.core.SystemConextConstans;
import com.blgroup.jkl.service.ITokenService;
import com.blgroup.jkl.servlet.WeiXinSystemContextServlet;
import com.blgroup.jkl.util.SystemUtil;
/**
 * 初始化上下文
 * @author 姜钟明
 *
 */
@SuppressWarnings("all")
public class ContextInitFilter implements Filter {
	private ITokenService iTokenService;
	private Logger logger = LoggerFactory.getLogger(ContextInitFilter.class);
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		logger.debug("!!!Filter: ContextInitFilter Start!!!");
	}
	
	@Override
	public void destroy() {
		logger.debug("!!!Filter: ContextInitFilter End!!!");
	}
	
	@Override
	public void doFilter(

			final ServletRequest request, 
			final ServletResponse response,
			FilterChain filter) throws IOException, ServletException {
		    
		// 从session中获取上下文信息
		Map weixinContext = (Map)((HttpServletRequest)request).getSession().getAttribute(SystemConextConstans.WEI_XIN_USER_CONTEXT_NAME);
		request.setAttribute(SystemConextConstans.WEI_XIN_USER_CONTEXT_NAME, weixinContext);
		//初始化用户上下文信息到request中去
		final UserContext weiXinUserContext = new UserContext() {
			@Override
			public String getMemberToken() {
				Map userContext = (Map)request.getAttribute(SystemConextConstans.WEI_XIN_USER_CONTEXT_NAME);
				if(userContext!=null){
					String memberToken =(String)userContext.get(SystemConextConstans.MEMBER_TOKEN_NAME);
					return memberToken;
				}
				 return null;
			}

			@Override
			public String getMemberName() {
				Map userContext = (Map)request.getAttribute(SystemConextConstans.WEI_XIN_USER_CONTEXT_NAME);
				if(userContext!=null){
					String memberName =(String)userContext.get(SystemConextConstans.MEMBER_NAME_NAME);
					return memberName;
				}
				return null;
			}

			@Override
			public String getMemberId() {
				Map userContext = (Map)request.getAttribute(SystemConextConstans.WEI_XIN_USER_CONTEXT_NAME);
				if(userContext!=null){
					String memberId =(String)userContext.get(SystemConextConstans.MEMBER_ID_NAME);
					return memberId;
				}
				return null;
			}

			@Override
			public Date expireTime() {
				Map userContext = (Map)request.getAttribute(SystemConextConstans.WEI_XIN_USER_CONTEXT_NAME);
				if(userContext!=null){
					String expireTime =(String)userContext.get(SystemConextConstans.EXPIR_TIME_NAME);
					return new Date(Long.valueOf(expireTime));
				}	
				return null;
			}

			@Override
			public String getOpenId() {
			  String openId	=(String) request.getAttribute(SystemConextConstans.OPEN_ID_NAME);
			  if(openId==null){
					Map userContext = (Map)request.getAttribute(SystemConextConstans.WEI_XIN_USER_CONTEXT_NAME);
					if(userContext!=null){
						 openId =(String)userContext.get(SystemConextConstans.OPEN_ID_NAME);
					}	
			  }
			  
			  // 若openId仍是为空 则用这种微信的方式获取openid
			  if(openId==null) openId = SystemUtil.getOpenIdByCookie((HttpServletRequest)request);
			  return openId;
			}

			@Override
			public String getBrrowserType() {
				String header = ((HttpServletRequest)request).getHeader(SystemConextConstans.HEADER_USER_AGENT);
				 if(header.matches(SystemConextConstans.WEIXIN_USER_AGENT)){
					 return SystemConextConstans.BRROWES_TYPE_WEIXIN;
				 }
				return SystemConextConstans.BRROWES_TYPE_OTHER;
			}

			@Override
			public String getMemberLevelCode() {
				Map userContext = (Map)request.getAttribute(SystemConextConstans.WEI_XIN_USER_CONTEXT_NAME);
				if(userContext!=null){
					String memberLevelCode =(String)userContext.get(SystemConextConstans.MEMBER_LEVEL_CODE);
					return memberLevelCode;
				}
				return null;
			}

			@Override
			public String getCpsCode() {
				return (String)request.getAttribute(SystemConextConstans.USER_CONTEXT_CPS_CODE_CONTEXT_NAME);
			}
			
			@Override
			public String getPin() {
				Map userContext = (Map)request.getAttribute(SystemConextConstans.WEI_XIN_USER_CONTEXT_NAME);
				if(userContext!=null){
					String pin =(String)userContext.get(SystemConextConstans.MEMBER_PIN);
					return pin;
				}
				 return null;
			}
			
			@Override
			public String getMobile() {
				Map userContext = (Map)request.getAttribute(SystemConextConstans.WEI_XIN_USER_CONTEXT_NAME);
				if(userContext!=null){
					String mobile =(String)userContext.get(SystemConextConstans.MEMBER_MOBILE);
					return mobile;
				}
				return null;
			}
			
		};
		
		// 获取当前请求的服务地址
		String currentHost = ((HttpServletRequest)request).getHeader("host");
		// 配置当前的服务地址
		final String systemHost = "http://"+currentHost.replaceAll(":.*$", "");
			
		// 初始化系统上下文
		final SystemContext systemContext = new SystemContext() {
			private  SocialAccount account = null;
			{
				String header = ((HttpServletRequest)request).getHeader(SystemConextConstans.HEADER_USER_AGENT);
				//只有微信环境才需要初始化，微信相关的系统信息
				if(header.matches(SystemConextConstans.WEIXIN_USER_AGENT)){
					this.account  = iTokenService.getSocialAccount();
				}else{
					account = new SocialAccount();
				}
			}
			
			@Override
			public String getResourcePath() {
				return systemHost+WeiXinSystemContextServlet.getResourcePath();
			}
			
			@Override
			public String getAppPath() {
				return systemHost+WeiXinSystemContextServlet.getAppPath();
			}
			
			
			// 固定是H5路径
			@Override
			public String getH5AppPath() {
				return systemHost+WeiXinSystemContextServlet.getAppPath();
			}

			@Override
			public boolean getStatusProduction() {
				// TODO Auto-generated method stub
				return false;
			}

		};
		// 初始化上下文
		final MallContext context = new MallContext() {
			
			@Override
			public UserContext getUserContext() {
				return weiXinUserContext;
			}
			
			@Override
			public SystemContext getSystemContext() {
				return systemContext;
			}
		};
		new  WeiXinContextBuilderImpl(context);
		request.setAttribute(SystemConextConstans.WEI_XIN_SYSTEM_CONTEXT_NAME, systemContext);
        
		logger.debug("开始业务");
		
		// debug
		logger.debug("["+weiXinUserContext.getOpenId()+"-"+weiXinUserContext.getMemberId()+"-"+weiXinUserContext.getMemberName()+"]");
		filter.doFilter(request, response);    
	}

	// 设置微信上下文
	private static class WeiXinContextBuilderImpl extends SystemContextBuilder{
		WeiXinContextBuilderImpl(MallContext weiXinContext){
			super.weixinContext.set(weiXinContext);
		}
	}

	public void setiTokenService(ITokenService iTokenService) {
		this.iTokenService = iTokenService;
	}

    private class MAPIHttpServletRequestWrapper extends HttpServletRequestWrapper {  
        
        private final byte[] body; // 报文  
      
        public MAPIHttpServletRequestWrapper(HttpServletRequest request) throws IOException {  
            super(request);  
           InputStream inputStream =  request.getInputStream();
           int length =  1024*1024;
           byte data [] = new byte[length]; 
           List<byte[]> dataList = new ArrayList<byte[]>();
           while(inputStream.read(data)>-1){
               dataList.add(data);
           }
           body = new byte[length*(dataList.size()+1)];
           int idx = 0;
           for(byte[] dataInfo:dataList){
        	   for(byte value:dataInfo){
        		   body[idx] = value;
        		   idx++;
        	   }
           }
        }  
        
        public BufferedReader getReader() throws IOException {  
            return new BufferedReader(new InputStreamReader(getInputStream()));  
        }  
          
        public ServletInputStream getInputStream() throws IOException {  
            final ByteArrayInputStream bais = new ByteArrayInputStream(body);  
            return new ServletInputStream() {  
                @Override  
                public int read() throws IOException {  
                    return bais.read();  
                }

				@Override
				public boolean isFinished() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean isReady() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public void setReadListener(ReadListener readListener) {
					// TODO Auto-generated method stub
					
				}  
            };  
        }  
      
    }
}
