package com.hsxy.blog.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @name CommentVo
 * @Description 评论vo
 * @author WU
 * @Date 2022/8/15 10:21
 */
@Data
public class CommentVo  {
	/**
	 * @Description 评论ID
	 * @Param
	 * @return
	 */
	//@JsonFormat(shape = JsonFormat.Shape.STRING) //<-- 前端JSON转化时精度发生了丢失,需要JSON序列化时转换回String
	@JsonSerialize(using = ToStringSerializer.class)	//序列化
	private Long id;
	
	private UserVo author;
	
	private String content;
	
	private List<CommentVo> childrens;
	
	private String createDate;
	
	private Integer level;
	
	private UserVo toUser;
}
