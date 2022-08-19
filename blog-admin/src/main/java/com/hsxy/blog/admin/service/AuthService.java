package com.hsxy.blog.admin.service;

import com.hsxy.blog.admin.pojo.Admin;
import com.hsxy.blog.admin.pojo.Permission;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @name authService
 * @Description Spring Security:权限认证
 * @author WU
 * @Date 2022/8/19 9:53
 */
@Service
@Slf4j
public class AuthService {
	
	@Autowired
	private AdminService adminService;
	
	
	public boolean auth(HttpServletRequest request, Authentication authentication){
		//权限认证，请求路径
		String requestURI = request.getRequestURI();
		log.info("--------request url:{}", requestURI);
		//true代表放行 false 代表拦截
		Object principal = authentication.getPrincipal();
		if (principal == null || "anonymousUser".equals(principal)){
			//未登录 || [匿名用户]
			return false;
		}
		UserDetails userDetails = (UserDetails) principal;
		String username = userDetails.getUsername();
		Admin admin = adminService.findAdminByUserName(username);
		if (admin == null){
			return false;
		}
		if (admin.getId() == 1){
			//认为是超级管理员
			return true;
		}
		List<Permission> permissions = adminService.findPermissionsByAdminId(admin.getId());
		requestURI = StringUtils.split(requestURI,'?')[0];//获取不带参数的URI
		for (Permission permission : permissions) {
			if (requestURI.equals(permission.getPath())){
				log.info("权限通过");
				return true;
			}
		}
		return false;
	}
}