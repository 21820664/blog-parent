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
	
	/**
	 * @Description 通过类别ID分页
	 * @Param
	 * @return
	 */
	private Long categoryId;
	/**
	 * @Description 通过标签ID分页
	 * @Param
	 * @return
	 */
	private Long tagId;
	
	private String year;
	
	private String month;
	
	/**
	 * @Description 月份为两位数
	 * @Param []
	 * @return java.lang.String
	 */
	public String getMonth(){
		if (this.month != null && this.month.length() == 1){
			return "0"+this.month;
		}
		return this.month;
	}
}