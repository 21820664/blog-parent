package com.hsxy.blog.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @name SecurityConfig
 * @Description SpringSecurity配置
 * @author WU
 * @Date 2022/8/18 20:13
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/**
	 * @Description BCrypt密码加密策略(安全)<包含时间戳:每次生成都不同>
	 * @Param []
	 * @return org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * @Description 手动生成密码加入数据库, 不另做注册
	 * @Param [args]
	 * @return void
	 */
	public static void main(String[] args) {
		//加密策略 MD5:不安全		 彩虹表  MD5 加盐
		System.out.println(new BCryptPasswordEncoder().encode("123456"));
		System.out.println(new BCryptPasswordEncoder().encode("hsxy"));
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests() //开启登录认证
//                .antMatchers("/user/findAll").hasRole("admin") //访问接口需要admin的角色
				.antMatchers("/css/**").permitAll()
				.antMatchers("/img/**").permitAll()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/plugins/**").permitAll()
				.antMatchers("/admin/**").access("@authService.auth(request,authentication)") //自定义service 来去实现实时的权限认证
				.antMatchers("/pages/**").authenticated()
				.and().formLogin()
				.loginPage("/login.html") //自定义的登录页面
				.loginProcessingUrl("/login") //登录处理接口
				.usernameParameter("username") //定义登录时的用户名的key 默认为username
				.passwordParameter("password") //定义登录时的密码key，默认是password
				.defaultSuccessUrl("/pages/main.html")
				.failureUrl("/login.html")
				.permitAll() //通过 不拦截，更加前面配的路径决定，这是指和登录表单相关的接口 都通过
				.and().logout() //退出登录配置
				.logoutUrl("/admin/logout") //退出登录接口(要和前端匹配)
				.logoutSuccessUrl("/login.html")
				.permitAll() //退出登录的接口放行
				.and()
				.httpBasic()
				.and()
				.csrf().disable() //csrf关闭:跨站请求伪造,默认只能通过post方式提交logout请求 如果自定义登录 需要关闭
				.headers().frameOptions().sameOrigin();//支持iframe嵌套
	}
}
