package com.hsxy.blog.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @name SysUser
 * @Description 用户表
 * @author WU
 * @Date 2022/8/8 15:20
 */
@Data
public class SysUser {
	
	//@TableId(type = IdType.ASSIGN_ID) //默认ID类型
	private Long id;
	
	private String account;
	
	private Integer admin;
	
	private String avatar;
	
	private Long createDate;
	
	private Integer deleted;
	
	private String email;
	
	private Long lastLogin;
	
	private String mobilePhoneNumber;
	/**
	 * @Description 昵称
	 * @Param
	 * @return
	 */
	private String nickname;
	
	private String password;
	
	private String salt;
	
	private String status;
}