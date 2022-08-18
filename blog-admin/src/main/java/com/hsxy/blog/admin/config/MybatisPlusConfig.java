package com.hsxy.blog.admin.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @name MybatisPlusConfig
 * @Description
 * @author WU
 * @Date 2022/8/18 18:29
 */
@Configuration
@MapperScan("com.hsxy.blog.admin.mapper")
public class MybatisPlusConfig {
	
	//分页插件
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor(){
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
		return interceptor;
	}
}