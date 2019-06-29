package com.admin.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.admin.common.pojo.ResultData;
import com.admin.manager.service.UserLoginService;


@Controller
@RequestMapping("/blog_front_api")
public class UserLoginController {
	
	@Autowired
	private UserLoginService loginService;
	/**
	 * 登录接口
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	@ResponseBody
	public ResultData login(String username,String password) {
		ResultData result = loginService.login(username, password);
		return result;
	}
}
