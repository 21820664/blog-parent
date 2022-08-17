package com.hsxy.blog.vo;

import lombok.Data;

/**
 * @name CategoryVo
 * @Description 类别vo
 * @author WU
 * @Date 2022/8/12 15:59
 */
@Data
public class CategoryVo {
	/**
	 * @Description 类别ID
	 * @Param
	 * @return
	 */
	private Long id;
	/**
	 * @Description 图标路径
	 * @Param
	 * @return
	 */
	private String avatar;
	/**
	 * @Description 图标名称
	 * @Param
	 * @return
	 */
	private String categoryName;
}
