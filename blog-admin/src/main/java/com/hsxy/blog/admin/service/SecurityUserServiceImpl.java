package com.hsxy.blog.admin.service;

import com.hsxy.blog.admin.pojo.Admin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @name SecurityUserService
 * @Description Spring Security:登录认证
 * @author WU
 * @Date 2022/8/19 9:34
 */
@Service
@Slf4j
public class SecurityUserServiceImpl implements UserDetailsService {
	@Autowired
	private AdminService adminService;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("username:{}",username);
		//当用户登录的时候，springSecurity 就会将请求 转发到此,会把username 传递到这里
		//根据用户名查询 admin表 查找用户，不存在 抛出异常，存在 将用户名，密码，授权列表 组装成springSecurity的User对象 并返回
		Admin adminUser = adminService.findAdminByUserName(username);
		if (adminUser == null){
			throw new UsernameNotFoundException("用户名不存在");
		}
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		//剩下的认证 就由框架帮我们完成
		return new User(username,adminUser.getPassword(), authorities);
	}
	
}