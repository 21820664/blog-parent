package com.hsxy.blog.dao.dos;

import lombok.Data;

/**
 * @name Archives
 * @Description
 * @author WU
 * @Date 2022/8/10 17:00
 */
@Data
public class Archives {
	
	private Integer year;
	
	private Integer month;
	
	private Long count;
}
