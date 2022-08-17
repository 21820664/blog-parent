package com.hsxy.blog.dao.pojo;

import lombok.Data;

/**
 * @name Category
 * @Description 文章类别 --> 文章正文下
 * @author WU
 * @Date 2022/8/12 15:46
 */
@Data
public class Category {
	
	private Long id;
	
	private String avatar;
	
	private String categoryName;
	
	private String description;
}
