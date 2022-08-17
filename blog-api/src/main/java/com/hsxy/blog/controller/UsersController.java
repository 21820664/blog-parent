package com.hsxy.blog.controller;

import com.hsxy.blog.service.SysUserService;
import com.hsxy.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @name UsersController
 * @Description 控制器:用户
 * @author WU
 * @Date 2022/8/11 16:48
 */
@RestController
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * @Description 验证Token --> 前端使用GET --> 退出登录也同
	 * @Param [token]
	 * @return com.hsxy.blog.vo.Result
	 */
	@GetMapping("/currentUser")
	public Result currentUser(@RequestHeader("Authorization") String token){
		
		return sysUserService.findUserByToken(token);
	}
}
