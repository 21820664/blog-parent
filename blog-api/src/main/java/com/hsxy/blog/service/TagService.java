package com.hsxy.blog.service;

import com.hsxy.blog.vo.Result;
import com.hsxy.blog.vo.TagVo;

import java.util.List;

/**
 * @name TagService
 * @Description
 * @author WU
 * @Date 2022/8/9 11:40
 */
public interface TagService {
	List<TagVo> findTagsByArticleId(Long id);
	
	Result hots(int limit);
	
	Result findAll();
	
	Result findAllDetail();
}
