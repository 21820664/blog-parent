package com.hsxy.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @name Result
 * @Description JSON统一结果返回
 * @author WU
 * @Date 2022/8/8 16:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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

