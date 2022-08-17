package com.hsxy.blog.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @name MybatisPlusConfig
 * @Description
 * @author WU
 * @Date 2022/8/7 23:17
 */
@Configuration
@MapperScan("com.hsxy.blog.dao.mapper")
public class MybatisPlusConfig {
	//分页插件
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor(){
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor());//增加分页内拦截器
		return interceptor;
	}
}
