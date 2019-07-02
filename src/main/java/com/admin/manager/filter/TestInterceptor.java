package com.admin.manager.filter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.admin.common.pojo.ResultData;
import com.admin.common.util.JsonUtils;
import com.admin.manager.service.UserLoginService;

public class TestInterceptor implements HandlerInterceptor {
	
	@Autowired
	private UserLoginService loginService;

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		
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
        
        // 是否拦截，默认不拦截
        Boolean isIntercept = true;
        
     // 判断是否需要过滤
        if (doFilter) {
        	Cookie[] arr = request.getCookies();
        	String token = "";
        	// System.out.println("DJ_TOKEN_KEY的值是===>"+DJ_TOKEN_KEY);
			for(Cookie s: arr){
				// System.out.println("cookie的name====>"+s.getName());
				if (s.getName().equalsIgnoreCase("DJ_TOKEN")) {
					// System.out.println("进去了====>"+token);
					token = s.getValue();
					break;
				}
			    
			}
			// System.out.println("token====>"+token);
			
			try {
				isIntercept = loginService.isTokenExist(token);
				
				if (!isIntercept) {
	        		response.setCharacterEncoding("UTF-8");
	        	    response.setContentType("application/json; charset=utf-8");  
	        		response.getWriter().append(JsonUtils.objectToJson(ResultData.build(false, "登录超时，请重新登录!")));
	        	}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        } else {
        	isIntercept = true;
        }
		return isIntercept;
	}

}
