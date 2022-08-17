package com.hsxy.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @name BlogApp
 * @Description Blog启动类
 * @author WU
 * @Date 2022/8/7 22:53
 */

@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class BlogApp {
	public static void main(String[] args) {
		SpringApplication.run(BlogApp.class,args);
	}
}
