package com.hsxy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.hsxy.blog.dao.mapper.CategoryMapper;
import com.hsxy.blog.dao.pojo.Category;
import com.hsxy.blog.service.CategoryService;
import com.hsxy.blog.vo.CategoryVo;
import com.hsxy.blog.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @name CategoryServiceImpl
 * @Description
 * @author WU
 * @Date 2022/8/12 16:43
 */
@Service
public class CategoryServiceImpl implements CategoryService {
	@Resource
	private CategoryMapper categoryMapper;
	
	@Override
	public CategoryVo findCategoryById(Long id){
		Category category = categoryMapper.selectById(id);
		CategoryVo categoryVo = new CategoryVo();
		//因为category,categoryVo属性一样所以可以使用 BeanUtils.copyProperties
		BeanUtils.copyProperties(category,categoryVo);
		return categoryVo;
	}
	
	@Override
	public Result findAll() {
		LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();//因为空参,所以new一个空LambdaQueryWrapper
		queryWrapper.select(Category::getId,Category::getCategoryName);
		List<Category> categories = categoryMapper.selectList(queryWrapper);
		//页面交互的对象
		return Result.success(copyList(categories));
	}
	
	@Override
	public Result findAllDetail() {
		List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<>());//因为空参,所以new一个空QueryWrapper
		//页面交互的对象
		return Result.success(copyList(categories));
	}
	
	@Override
	public Result findAllDetailById(Long id) {
		Category category = categoryMapper.selectById(id);
		return Result.success(copy(category));
	}
	
	
	public CategoryVo copy(Category category){
		CategoryVo categoryVo = new CategoryVo();
		BeanUtils.copyProperties(category,categoryVo);
		//id不一致要重新设立
		//categoryVo.setId(String.valueOf(category.getId()));//没看出异常
		return categoryVo;
	}
	public List<CategoryVo> copyList(List<Category> categoryList){
		List<CategoryVo> categoryVoList = new ArrayList<>();
		for (Category category : categoryList) {
			categoryVoList.add(copy(category));
		}
		return categoryVoList;
	}
}
