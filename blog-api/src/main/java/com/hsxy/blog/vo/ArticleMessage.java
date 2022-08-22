package com.hsxy.blog.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @name ArticleMessage
 * @Description 文章消息队列
 * @author WU
 * @Date 2022/8/19 16:32
 */
@Data
public class ArticleMessage implements Serializable {
	
	private Long articleId;
	
}
