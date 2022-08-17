package com.hsxy.blog.vo;

import lombok.Data;

/**
 * @name LoginUserVo
 * @Description
 * @author WU
 * @Date 2022/8/11 17:16
 */
@Data
public class LoginUserVo {
	
	private Long id;
	
	private String account;
	
	private String nickname;
	
	/**
	 * @Description 头像
	 * @Param
	 * @return
	 */
	private String avatar;
}
