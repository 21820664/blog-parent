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
		
		//如果配置了自定义拦截器,这种跨域配置会失效，所以采用第二种(403:Invalid CORS request)
		//因为使用了nginx代理，导致码神的那种方式会失效（个人觉得是配置发生了变化），所以全部放开就好啦。
		//registry.addMapping("/**").allowedOrigins("43.142.80.183","http://localhost:8080");
		
		registry.addMapping("/**")
				.allowedOriginPatterns("*")
				.allowedMethods("GET","HEAD","POST","PUT","DELETE","OPTIONS")
				.allowCredentials(true)
				.maxAge(3600)
				.allowedHeaders("*");
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
				//.excludePathPatterns("/login")
	}
}
