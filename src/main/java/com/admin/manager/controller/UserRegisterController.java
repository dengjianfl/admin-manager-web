package com.admin.manager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.admin.common.pojo.ResultData;
import com.admin.manager.pojo.User;
import com.admin.manager.service.UserRegisterService;

@Controller
@RequestMapping("/blog_front_api")
public class UserRegisterController {
	
	@Autowired
	private UserRegisterService registerservice;
	
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	@ResponseBody
	public ResultData login(User user) {
		ResultData result = registerservice.register(user);
		return result;
	}
}
