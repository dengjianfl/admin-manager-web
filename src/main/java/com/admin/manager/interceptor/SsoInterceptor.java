package com.admin.manager.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.admin.common.pojo.ResultData;
import com.admin.common.util.JsonUtils;
import com.admin.manager.service.UserLoginService;
import com.admin.manager.web.vo.ErrorCodeVO;

public class SsoInterceptor implements HandlerInterceptor {
	
	@Autowired
	private UserLoginService loginService;
	
	@Value("${DJ_TOKEN_KEY}")
	private String DJ_TOKEN_KEY;

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
        	if (arr != null) {
        		for(Cookie s: arr){
    				if (s.getName().equals(DJ_TOKEN_KEY)) {
    					token = s.getValue();
    					break;
    				}
    			    
    			}
        	}
			
			try {
				isIntercept = loginService.isTokenExist(token);
				
				// 已经超时了，跳转到登录页面
				if (!isIntercept) {
					ErrorCodeVO errorCodeVo = new ErrorCodeVO();
					errorCodeVo.setErrorCode("200");
	        		response.setCharacterEncoding("UTF-8");
	        	    response.setContentType("application/json; charset=utf-8");
	        		response.getWriter().append(JsonUtils.objectToJson(ResultData.build(false, "登录超时，请重新登录!",errorCodeVo)));
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
