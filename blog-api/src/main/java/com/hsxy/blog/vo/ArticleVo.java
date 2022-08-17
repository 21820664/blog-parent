package com.hsxy.blog.vo;

import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.List;

/**
 * @name ArticleVo
 * @Description 文章vo
 * @author WU
 * @Date 2022/8/8 18:50
 */
@Data
public class ArticleVo {
	//@JsonSerialize(using = ToStringSerializer.class) //序列化,com.fasterxml.jackson
	//private String id; //--> 导致url显示http://localhost:8080/#/view/null
	@JsonFormat(shape = JsonFormat.Shape.STRING) //<-- 前端JSON转化时精度发生了丢失,需要JSON序列化时转换回String
	private Long id;
	
	private String title;
	
	private String summary;
	
	private Integer commentCounts;
	
	private Integer viewCounts;
	
	private Integer weight;
	/**
	 * 创建时间
	 * ##!与Article不同,Article为Long类型
	 */
	private String createDate;
	private String author;
	//private UserVo author;
	
	private ArticleBodyVo body;
	
	private List<TagVo> tags;
	
	private CategoryVo category;
	
}
