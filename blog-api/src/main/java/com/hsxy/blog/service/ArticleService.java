package com.hsxy.blog.service;

import com.hsxy.blog.vo.Result;
import com.hsxy.blog.vo.params.ArticleParam;
import com.hsxy.blog.vo.params.PageParams;

/**
 * @name ArticleService
 * @Description 业务层:首页
 * @author WU
 * @Date 2022/8/8 18:26
 */
public interface ArticleService {
	/**
	 * @Description 分页查询 文章列表
	 * @Param [pageParams]
	 * @return com.hsxy.blog.vo.Result
	 */
	Result listArticle(PageParams pageParams);
	
	/**
	 * @Description 最热文章
	 * @Param [limit]
	 * @return com.hsxy.blog.vo.Result
	 */
	Result hotArticle(int limit);
	
	/**
	 * @Description 最新文章
	 * @Param [limit]
	 * @return com.hsxy.blog.vo.Result
	 */
	Result newArticles(int limit);
	
	/**
	 * @Description 文章归档
	 * @Param []
	 * @return com.hsxy.blog.vo.Result
	 */
	Result listArchives();
	
	/**
	 * @Description 通过ID查看文章详情
	 * @Param [articleId]
	 * @return com.hsxy.blog.vo.Result
	 */
	Result findArticleById(Long articleId);
	
	/**
	 * @Description 文章发布
	 * @Param [articleParam]
	 * @return com.hsxy.blog.vo.Result
	 */
	Result publish(ArticleParam articleParam);
}
