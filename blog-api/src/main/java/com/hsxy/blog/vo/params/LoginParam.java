package com.hsxy.blog.vo.params;

import lombok.Data;

/**
 * @name LoginParams
 * @Description 请求参数:登录
 * @author WU
 * @Date 2022/8/11 11:05
 */
@Data
public class LoginParam {
	
	private String account;
	
	private String password;
	
	/**
	 * @Description 用于注册
	 * @Param
	 * @return
	 */
	private String nickname;
}
