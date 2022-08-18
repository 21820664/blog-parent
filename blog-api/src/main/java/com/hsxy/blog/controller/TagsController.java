package com.hsxy.blog.controller;

import com.hsxy.blog.service.TagService;
import com.hsxy.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @name TagsController
 * @Description 控制器:标签
 * @author WU
 * @Date 2022/8/10 14:09
 */

@RestController
@RequestMapping("/tags")
public class TagsController {
	@Autowired
	private TagService tagService;
	
	/**
	 * @Description 最热标签 :/tags/hot
	 * @Param []
	 * @return com.hsxy.blog.vo.Result
	 */
	@GetMapping("/hot")
	public Result hot(){
		int limit = 6;//最热的六个标签
		return tagService.hots(limit);
	}
	
	/**
	 * @Description 查找全部标签
	 * @Param []
	 * @return com.hsxy.blog.vo.Result
	 */
	@GetMapping
	public Result findAll(){
		return tagService.findAll();
	}
	
	/**
	 * @Description 查看全部标签详情
	 * @Param []
	 * @return com.hsxy.blog.vo.Result
	 */
	@GetMapping("/detail")
	public Result findAllDetail(){
		return tagService.findAllDetail();
	}
	
	/**
	 * @Description 查询文章标签下所有的文章
	 * @Param [id]
	 * @return com.hsxy.blog.vo.Result
	 */
	@GetMapping("detail/{id}")
	public Result findDetailById(@PathVariable("id") Long id){
		return tagService.findDetailById(id);
	}
	
}
