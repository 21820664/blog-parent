package com.hsxy.blog.service;

import com.hsxy.blog.vo.Result;
import com.hsxy.blog.vo.params.CommentParam;

/**
 * @name CommentsService
 * @Description
 * @author WU
 * @Date 2022/8/15 9:47
 */
public interface CommentsService {
	
	/**
	 * @Description 根据文章id查询所有的评论列表
	 * @Param [articleId]
	 * @return com.hsxy.blog.vo.Result
	 */
	Result commentsByArticleId(Long articleId);
	
	/**
	 * @Description 评论
	 * @Param [commentParam]
	 * @return com.hsxy.blog.vo.Result
	 */
	Result comment(CommentParam commentParam);
}
