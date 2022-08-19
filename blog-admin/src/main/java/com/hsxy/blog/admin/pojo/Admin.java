package com.hsxy.blog.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @name Admin
 * @Description
 * @author WU
 * @Date 2022/8/19 9:32
 */
@Data
public class Admin {
	
	@TableId(type = IdType.AUTO)
	private Long id;
	
	private String username;
	
	private String password;
}