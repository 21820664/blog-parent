package com.hsxy.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

import java.util.TimeZone;

/**
 * @name BlogApp
 * @Description Blog启动类
 * @author WU
 * @Date 2022/8/7 22:53
 */


//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication(exclude= {SecurityAutoConfiguration.class })//关闭SpringSecurity,防止无法连接前端(401)
@EnableCaching	//开启基于注解的缓存
public class BlogApp {
	public static void main(String[] args) {
		//修复Linux启动后的log日志时区不对
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
		SpringApplication.run(BlogApp.class,args);
	}
}
