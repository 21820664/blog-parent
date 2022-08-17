package com.hsxy.blog.vo.params;

import lombok.Data;

/**
 * @name PageParams
 * @Description 请求参数:分页
 * @author WU
 * @Date 2022/8/8 16:16
 */
@Data
public class PageParams {
	
	private int page = 1;
	
	private int pageSize = 10;
	
/*	private Long categoryId;
	
	private Long tagId;
	
	private String year;
	
	private String month;
	
	public String getMonth(){
		if (this.month != null && this.month.length() == 1){
			return "0"+this.month;
		}
		return this.month;
	}*/
}