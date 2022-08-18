package com.hsxy.blog.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @name Result
 * @Description
 * @author WU
 * @Date 2022/8/18 18:50
 */
@Data
@AllArgsConstructor
public class Result {
	
	private boolean success;
	
	private int code;
	
	private String msg;
	
	private Object data;
	
	
	public static Result success(Object data){
		return new Result(true,200,"success",data);
	}
	
	public static Result fail(int code, String msg){
		return new Result(false,code,msg,null);
	}
}