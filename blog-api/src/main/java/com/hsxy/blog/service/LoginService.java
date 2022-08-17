package com.hsxy.blog.service;

import com.hsxy.blog.dao.pojo.SysUser;
import com.hsxy.blog.vo.Result;
import com.hsxy.blog.vo.params.LoginParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * @name LoginService
 * @Description 业务层:登录
 * @author WU
 * @Date 2022/8/11 10:35
 */
@Transactional
public interface LoginService {
	Result login(LoginParam loginParam);
	
	/**
	 * @Description 检查Token
	 * @Param [token]
	 * @return com.hsxy.blog.dao.pojo.SysUser
	 */
	SysUser checkToken(String token);
	
	/**
	 * @Description 退出登录
	 * @Param [token]
	 * @return com.hsxy.blog.vo.Result
	 */
	Result logout(String token);
	
	/**
	 * @Description 注册 --> sso 单点登录，后期如果把登录注册功能 提出去（单独的服务，可以独立提供接口服务）
	 * @Param [loginParam]
	 * @return com.hsxy.blog.vo.Result
	 */
	Result register(LoginParam loginParam);
}
