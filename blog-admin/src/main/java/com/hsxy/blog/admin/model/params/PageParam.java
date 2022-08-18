package com.hsxy.blog.admin.model.params;

import lombok.Data;

/**
 * @name PageParam
 * @Description
 * @author WU
 * @Date 2022/8/18 18:48
 */
@Data
public class PageParam {
	
	private Integer currentPage;
	
	private Integer pageSize;
	
	private String queryString;
}
