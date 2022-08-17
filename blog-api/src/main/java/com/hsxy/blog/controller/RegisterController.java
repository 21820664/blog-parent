package com.hsxy.blog.controller;

import com.hsxy.blog.service.LoginService;
import com.hsxy.blog.vo.Result;
import com.hsxy.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @name RegisterController
 * @Description
 * @author WU
 * @Date 2022/8/11 18:09
 */
@RestController
@RequestMapping("/register")
public class RegisterController {
	
	@Autowired
	private LoginService loginService;
	
	//后端传递多个参数，前端只选用其需要的参数就可以了
	@PostMapping
	public Result register(@RequestBody LoginParam loginParam){
		//sso 单点登录，后期如果把登录注册功能 提出去（单独的服务，可以独立提供接口服务）
		return loginService.register(loginParam);
	}
}
