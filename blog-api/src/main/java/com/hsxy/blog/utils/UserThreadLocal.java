package com.hsxy.blog.utils;

import com.hsxy.blog.dao.pojo.SysUser;

/**
 * @name UserThreadLocal
 * @Description 用户本地线程(用户登录后储存SysUser)
 * @author WU
 * @Date 2022/8/12 13:55
 */
public class UserThreadLocal {
	private UserThreadLocal(){}//从下面默认创建
	//线程变量隔离
	private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();
	
	public static void put(SysUser sysUser){
		LOCAL.set(sysUser);
	}
	public static SysUser get(){
		return LOCAL.get();
	}
	public static void remove(){
		LOCAL.remove();
	}
}
