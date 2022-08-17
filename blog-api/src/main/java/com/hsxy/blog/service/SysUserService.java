package com.hsxy.blog.service;

import com.hsxy.blog.dao.pojo.SysUser;
import com.hsxy.blog.vo.Result;
import com.hsxy.blog.vo.UserVo;

/**
 * @name SysUserService
 * @Description 业务层:用户
 * @author WU
 * @Date 2022/8/9 14:27
 */
public interface SysUserService {
	SysUser findUser(String account, String password);
	
	/**
	 * @Description 根据id查询用户信息
	 * @Param [id]
	 * @return com.hsxy.blog.dao.pojo.SysUser
	 */
	//SysUser findUserById(Long id);
	
	/**
	 * @Description 根据id查询用户信息VO
	 * @Param [authorId]
	 * @return com.hsxy.blog.vo.UserVo
	 */
	UserVo findUserVoById(Long authorId);
	
	/**
	 * @Description 根据Token查询用户信息
	 * @Param [token]
	 * @return com.hsxy.blog.vo.Result
	 */
	Result findUserByToken(String token);
	
	/**
	 * @Description 根据账户查找用户
	 * @Param [account]
	 * @return com.hsxy.blog.dao.pojo.SysUser
	 */
	SysUser findUserByAccount(String account);
	
	/**
	 * @Description 保存用户
	 * @Param [sysUser]
	 * @return void
	 */
	void save(SysUser sysUser);
	
	
}
