package com.hsxy.blog.dao.pojo;


import lombok.Data;

/**
 * @name ArticleTag
 * @Description 文章标签
 * @author WU
 * @Date 2022/8/16 17:24
 */
@Data
public class ArticleTag {
	
	private Long id;
	
	private Long articleId;
	
	private Long tagId;
}
