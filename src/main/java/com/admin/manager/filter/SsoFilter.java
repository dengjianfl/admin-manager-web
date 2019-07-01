package com.admin.manager.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.admin.common.pojo.ResultData;
import com.taotao.common.util.JsonUtils;

public class SsoFilter implements Filter {

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
                //uri中包含不过滤uri，则不进行拦截
                doFilter = false;
                break;
            }
        }
        
        // 不拦截
        if (doFilter) {
        	Object obj = request.getSession().getAttribute("user");
        	if (obj == null) {
        		response.setCharacterEncoding("UTF-8");
        	    response.setContentType("application/json; charset=utf-8");  
        		response.getWriter().append(JsonUtils.objectToJson(ResultData.build(400, "已经超时了")));
        		System.out.println("已经超时了");
        		return;
        	} else {
        		filterChain.doFilter(req, res);
        	}
        } else {
        	filterChain.doFilter(req, res);
        }
        
        

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
