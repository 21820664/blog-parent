package com.hsxy.blog.controller;

import com.hsxy.blog.service.LoginService;
import com.hsxy.blog.vo.Result;
import com.hsxy.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @name LoginController
 * @Description 控制器:登录
 * @author WU
 * @Date 2022/8/11 10:27
 */
@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@PostMapping
	public Result login(@RequestBody LoginParam loginParam){
		//登陆 验证用户 访问用户表
		return loginService.login(loginParam);
		
	}
}
