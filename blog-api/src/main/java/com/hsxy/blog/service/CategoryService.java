package com.hsxy.blog.service;

import com.hsxy.blog.vo.CategoryVo;
import com.hsxy.blog.vo.Result;

/**
 * @name CategoryService
 * @Description
 * @author WU
 * @Date 2022/8/12 16:37
 */
public interface CategoryService {

	/**
	 * @Description 根据类别id查询类别信息
	 * @Param [id]
	 * @return com.hsxy.blog.vo.CategoryVo
	 */
	CategoryVo findCategoryById(Long id);
	
	/**
	 * @Description 查找全部类别信息
	 * @Param []
	 * @return com.hsxy.blog.vo.Result
	 */
	Result findAll();
}