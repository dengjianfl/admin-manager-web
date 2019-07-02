package com.admin.manager.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.admin.common.pojo.ResultData;
import com.admin.common.util.JsonUtils;
import com.admin.manager.service.UserLoginService;

@Component
public class SsoFilter implements Filter {
	
//	@Autowired
	private UserLoginService loginService;
	
//	@Value("${DJ_TOKEN_KEY}")
//	private String DJ_TOKEN_KEY;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		//不过滤的uri
        String[] notFilter = new String[]{"/login"};

        //请求的uri
        String uri = request.getRequestURI();
        System.out.println("filter>>>uri>>>"+uri);
        
        //是否过滤
        boolean doFilter = true;
        for(String s : notFilter){
            if(uri.indexOf(s) != -1){
                //uri中包含不过滤uri，则不进行过滤
                doFilter = false;
                break;
            }
        }
        
        // 判断是否需要过滤
        if (doFilter) {
        	Cookie[] arr = request.getCookies();
        	String token = "";
//        	System.out.println("DJ_TOKEN_KEY的值是===>"+DJ_TOKEN_KEY);
			for(Cookie s: arr){
//				System.out.println("cookie的name====>"+s.getName());
				if (s.getName().equalsIgnoreCase("DJ_TOKEN")) {
					System.out.println("进去了====>"+token);
					token = s.getValue();
					break;
				}
			    
			}
			System.out.println("token====>"+token);
			
			try {
				Boolean isFilter = loginService.isTokenExist(token);
				if (!isFilter) {
	        		response.setCharacterEncoding("UTF-8");
	        	    response.setContentType("application/json; charset=utf-8");  
	        		response.getWriter().append(JsonUtils.objectToJson(ResultData.build(false, "已经超时了")));
	        		System.out.println("已经超时了");
	        	} else {
	        		filterChain.doFilter(req, res);
	        	}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        } else {
        	filterChain.doFilter(req, res);
        }
        
        

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		if (loginService == null) {
			
			WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
			loginService= (UserLoginService) wac.getBean("UserLoginService");
			
//			loginService = (UserLoginService) SpringUtils.getBean("UserLoginService");
		}

	}

}
