package com.hsxy.blog.controller;

import com.hsxy.blog.dao.pojo.SysUser;
import com.hsxy.blog.utils.UserThreadLocal;
import com.hsxy.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @name TestController
 * @Description 控制器:测试接口
 * @author WU
 * @Date 2022/8/12 10:52
 */
@RestController
@RequestMapping("/test")
public class TestController {
	
	@RequestMapping
	public Result test(){
		//SysUser sysUser = UserThreadLocal.get();
		//System.out.println(sysUser);
		return Result.success("test拦截器测试");
	}
}
