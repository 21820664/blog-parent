package com.hsxy.blog.vo;

import lombok.Data;

/**
 * @name UserVo
 * @Description
 * @author WU
 * @Date 2022/8/15 10:21
 */
@Data
public class UserVo {
	
	private String nickname;
	
	/**
	 * @Description 头像
	 * @Param
	 * @return
	 */
	private String avatar;
	
	private Long id;
}
