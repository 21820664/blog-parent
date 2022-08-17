package com.hsxy.blog.controller;

import com.hsxy.blog.service.LoginService;
import com.hsxy.blog.vo.Result;
import com.hsxy.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @name LoginController
 * @Description 控制器:退出登录
 * @author WU
 * @Date 2022/8/11 10:27
 */
@RestController
@RequestMapping("/logout")
public class LogoutController {
	
	@Autowired
	private LoginService loginService;
	
	/**
	 * @Description 退出与登录不同,使用的是GET --> 效仿users/currentUser
	 * @Param [loginParam]
	 * @return com.hsxy.blog.vo.Result
	 */
	@GetMapping
	public Result logout(@RequestHeader("Authorization") String token){
		
		return loginService.logout(token);
		
	}
	
}