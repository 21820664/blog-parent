package com.hsxy.blog.vo.params;

import com.hsxy.blog.vo.CategoryVo;
import com.hsxy.blog.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * @name ArticleParam
 * @Description
 * @author WU
 * @Date 2022/8/16 17:01
 */
@Data
public class ArticleParam {
	
	private Long id;
	
	private ArticleBodyParam body;
	
	/**
	 * @Description 文章类别
	 * @Param
	 * @return
	 */
	private CategoryVo category;
	
	/**
	 * @Description 文章概述
	 * @Param
	 * @return
	 */
	private String summary;
	
	private List<TagVo> tags;
	
	private String title;
}
