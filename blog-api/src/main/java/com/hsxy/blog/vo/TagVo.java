package com.hsxy.blog.vo;

import lombok.Data;

/**
 * @name TagVo
 * @Description
 * @author WU
 * @Date 2022/8/9 10:27
 */
@Data
public class TagVo {
	
	//private String id;
	private Long id;
	
	private String tagName;
	
	private String avatar;//头像
}
