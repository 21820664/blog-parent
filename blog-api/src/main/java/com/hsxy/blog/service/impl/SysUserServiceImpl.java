package com.hsxy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hsxy.blog.service.LoginService;
import com.hsxy.blog.service.SysUserService;
import com.hsxy.blog.dao.mapper.SysUserMapper;
import com.hsxy.blog.dao.pojo.SysUser;
import com.hsxy.blog.vo.ErrorCode;
import com.hsxy.blog.vo.LoginUserVo;
import com.hsxy.blog.vo.Result;
import com.hsxy.blog.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @name SysUserServiceImpl
 * @Description
 * @author WU
 * @Date 2022/8/9 14:29
 */
@Service
public class SysUserServiceImpl implements SysUserService {
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private LoginService loginService;
	
	//@Resource
	//private RedisTemplate<String,String> redisTemplate;
	
	@Override
	public SysUser findUser(String account, String password) {
		LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SysUser::getAccount,account);
		queryWrapper.eq(SysUser::getPassword,password);
		//account id 头像 名称
		queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAdmin,SysUser::getNickname);
		//增加查询效率，只查询一条	(和selectOne()搭配,不可少)
		queryWrapper.last("limit 1");
//selectOne的坑https://www.guangmuhua.com/articleDetail/2625
		return sysUserMapper.selectOne(queryWrapper);
	}
	
	@Override
	public UserVo findUserVoById(Long userId) {
		SysUser sysUser = sysUserMapper.selectById(userId);
		//未登录
		if (sysUser == null){
			sysUser = new SysUser();
			sysUser.setId(1L);
			sysUser.setAvatar("/static/img/logo.b3a48c0.png");
			sysUser.setNickname("红域");
		}
		UserVo userVo = new UserVo();
		/*userVo.setAvatar(sysUser.getAvatar());
		userVo.setNickname(sysUser.getNickname());
		userVo.setId(sysUser.getId());//↓↓↓*/
		BeanUtils.copyProperties(sysUser, userVo);
		return userVo;
	}
	
	@Override
	public Result findUserByToken(String token) {
		/**
		 * 1、token合法性校验
		 * 是否为空 ，解析是否成功，redis是否存在
		 * 2、如果校验失败，返回错误
		 *3、如果成功，返回对应结果 LoginUserVo
		 */
		//去loginService中去校验token
		SysUser sysUser = loginService.checkToken(token);
		if(sysUser == null){
			return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
		}
		LoginUserVo loginUserVo = new LoginUserVo();
		loginUserVo.setId(sysUser.getId());
		loginUserVo.setNickname(sysUser.getNickname());
		loginUserVo.setAvatar(sysUser.getAvatar());
		loginUserVo.setAccount(sysUser.getAccount());
		return Result.success(loginUserVo);
	}
	
	@Override
	public SysUser findUserByAccount(String account) {
		LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SysUser::getAccount,account);
		//确保只能查询一条
		queryWrapper.last("limit 1");
		return sysUserMapper.selectOne(queryWrapper);
	}
	
	@Override
	public void save(SysUser sysUser) {
		//保存用户这 id会自动生成
		//这个地方 默认生成的id是 分布式id 雪花算法
		//mybatis-plus
		this.sysUserMapper.insert(sysUser);
	}
}
