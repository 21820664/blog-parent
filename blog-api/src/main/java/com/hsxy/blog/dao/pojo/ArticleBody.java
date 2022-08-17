package com.hsxy.blog.dao.pojo;

import lombok.Data;

/**
 * @name ArticleBody
 * @Description 文章内容
 * @author WU
 * @Date 2022/8/12 15:44
 */
@Data
public class ArticleBody {
	
	private Long id;
	private String content;
	private String contentHtml;
	private Long articleId;
}
