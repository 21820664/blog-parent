package com.hsxy.blog.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @name Permission
 * @Description
 * @author WU
 * @Date 2022/8/18 18:49
 */
@Data
public class Permission {
	
	@TableId(type = IdType.AUTO)
	private Long id;
	
	private String name;
	
	private String path;
	
	private String description;
}
