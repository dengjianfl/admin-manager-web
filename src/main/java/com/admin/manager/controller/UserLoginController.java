package com.admin.manager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.admin.common.pojo.ResultData;
import com.admin.common.util.CookieUtils;
import com.admin.manager.service.UserLoginService;


@Controller
@RequestMapping("/blog_front_api")
public class UserLoginController {
	
	@Autowired
	private UserLoginService loginService;
	
	@Value("${DJ_TOKEN_KEY}")
	private String DJ_TOKEN_KEY;
	
	/**
	 * 登录接口
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/user/login.do",method=RequestMethod.POST)
	@ResponseBody
	public ResultData login(HttpServletRequest request,HttpServletResponse response,String username,String password) {
		ResultData result = loginService.login(username, password);
		// 说明登录成功了,放到cookie中去
		if (result.getIsSuccess() == true) {
			CookieUtils.setCookie(request, response, DJ_TOKEN_KEY, result.getData().toString());
		}
		return result;
	}
	
	@RequestMapping(value="/user/test.do",method=RequestMethod.POST)
	@ResponseBody
	public ResultData test(HttpServletRequest request,HttpServletResponse response,String username,String password) {
		ResultData result = ResultData.build(true, "成功");
		return result;
	}
	
	
}
