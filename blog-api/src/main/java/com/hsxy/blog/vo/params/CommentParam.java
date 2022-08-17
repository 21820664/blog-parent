package com.hsxy.blog.vo.params;

import lombok.Data;

/**
 * @name CommentParam
 * @Description
 * @author WU
 * @Date 2022/8/16 13:56
 */
@Data
public class CommentParam {
	private Long articleId;
	
	private String content;
	
	/**
	 * @Description 父评论id
	 * @Param
	 * @return
	 */
	private Long parent;
	
	/**
	 * @Description 被评论的用户id(@用户ID)
	 * @Param
	 * @return
	 */
	private Long toUserId;
}
