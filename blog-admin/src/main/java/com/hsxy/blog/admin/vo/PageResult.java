package com.hsxy.blog.admin.vo;

import lombok.Data;

import java.util.List;

/**
 * @name PageResult
 * @Description
 * @author WU
 * @Date 2022/8/18 18:54
 */
@Data
public class PageResult<T> {
	
	private List<T> list;
	
	private Long total;
}