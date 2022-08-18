package com.hsxy.blog.controller;

import com.hsxy.blog.common.aop.LogAnnotation;
import com.hsxy.blog.common.cache.Cache;
import com.hsxy.blog.service.ArticleService;
import com.hsxy.blog.vo.Result;
import com.hsxy.blog.vo.params.ArticleParam;
import com.hsxy.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

/**
 * @name ArticleController
 * @Description 控制器:首页 JSON数据交互
 * @author WU
 * @Date 2022/8/8 15:23
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	/**
	 * @Description 分页查询article数据库表
	 * @Param [pageParams]
	 * @return com.hsxy.blog.vo.Result
	 */
	@PostMapping
	@LogAnnotation(module = "文章",operation = "获取文章列表")//加上此注解，代表要对此接口记录日志
	//@Cacheable(cacheNames = "listArticleCache")	//TODO SpringCache报错序列化异常
	@Cache(expire = 5 * 60 * 1000, name = "list_article")
	public Result listArticle(@RequestBody PageParams pageParams) {//Result是统一结果返回
		//ArticleVo 页面接收的数据
		//List<ArticleVo> articles = articleService.listArticlesPage(pageParams);
		
		//return Result.success(articles);
		
		//int i=10/0;	//测试报错
		return articleService.listArticle(pageParams);
	}
	
	/**
	 * @Description 首页 最热文章
	 * @Param []
	 * @return com.hsxy.blog.vo.Result
	 */
	@PostMapping("/hot")
	@Cache(expire = 5 * 60 * 1000, name = "hot_article")
	public Result hotArticle(){//无参删除
		int limit = 5;
		return articleService.hotArticle(limit);
	}
	
	/**
	 * @Description 首页 最新文章
	 * @Param []
	 * @return com.hsxy.blog.vo.Result
	 */
	@PostMapping("/new")
	@Cache(expire = 5 * 60 * 1000, name = "new_article")
	public Result newArticles(){
		int limit = 5;
		return articleService.newArticles(limit);
	}
	
	/**
	 * @Description 首页 文章归档
	 * @Param []
	 * @return com.hsxy.blog.vo.Result
	 */
	@PostMapping("/listArchives")
	public Result listArchives(){//[档案列表]
		return articleService.listArchives();
	}
	
	/**
	 * @Description 文章详情
	 * @Param [articleId]
	 * @return com.hsxy.blog.vo.Result
	 */
	@PostMapping("/view/{id}")
	public Result findArticleById(@PathVariable("id") Long articleId){
		return articleService.findArticleById(articleId);
	}
	
	/**
	 * @Description 发布文章
	 * @Param [articleParam]
	 * @return com.hsxy.blog.vo.Result
	 */
	@PostMapping("/publish")
	public Result publish(@RequestBody ArticleParam articleParam){
		return articleService.publish(articleParam);
	}
}
