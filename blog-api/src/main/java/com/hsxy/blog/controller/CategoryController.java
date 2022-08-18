package com.hsxy.blog.controller;

import com.hsxy.blog.service.CategoryService;
import com.hsxy.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @name CategoryController
 * @Description
 * @author WU
 * @Date 2022/8/16 15:32
 */
@RestController
@RequestMapping("/categorys")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public Result listCategory() {
		return categoryService.findAll();
	}
	
	@GetMapping("/detail")
	public Result listCategoryDetail(){
		return categoryService.findAllDetail();
	}
	
	@GetMapping("/detail/{id}")
	public Result listCategoryDetailById(@PathVariable("id") Long id){
		return categoryService.findAllDetailById(id);
	}
}
