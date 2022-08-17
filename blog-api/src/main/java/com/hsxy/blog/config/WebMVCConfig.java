package com.hsxy.blog.config;

import com.hsxy.blog.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @name WebMVCConfig
 * @Description MVC配置文件
 * @author WU
 * @Date 2022/8/8 10:22
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
	
	@Autowired
	private LoginInterceptor loginInterceptor;
	
	/**
	 * @Description 增加跨域配置
	 * @Param [registry]
	 * @return void
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		//跨域配置，不可设置为*，不安全, 前后端分离项目，可能域名不一致
		//本地测试 端口不一致 也算跨域
		registry.addMapping("/**").allowedOrigins("http://localhost:8080");
	}
	
	/**
	 * @Description 增加拦截器
	 * @Param [registry]
	 * @return void
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//假设拦截test接口后续实际遇到拦截的接口是时，再配置真正的拦截接口
		registry.addInterceptor(loginInterceptor)
				.addPathPatterns("/test")
				.addPathPatterns("/comments/create/change")//评论需要登录
				.addPathPatterns("/articles/publish");//发布文章要拿到SysUser需要加入拦截器
				//.excludePathPatterns("/login");
	}
}
