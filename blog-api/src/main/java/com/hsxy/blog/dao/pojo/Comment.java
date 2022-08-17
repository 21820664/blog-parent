package com.hsxy.blog.dao.pojo;

import lombok.Data;

/**
 * @name Comment
 * @Description
 * @author WU
 * @Date 2022/8/15 9:38
 */
@Data
public class Comment {
	
	private Long id;
	
	private String content;
	
	private Long createDate;
	
	private Long articleId;
	
	private Long authorId;
	
	private Long parentId;
	
	private Long toUid;
	
	/**
	 * @Description 评论层级{1,2}
	 * @Param
	 * @return
	 */
	private Integer level;
}
