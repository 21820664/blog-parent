package com.hsxy.blog.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hsxy.blog.admin.mapper.AdminMapper;
import com.hsxy.blog.admin.mapper.PermissionMapper;
import com.hsxy.blog.admin.pojo.Admin;
import com.hsxy.blog.admin.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @name AdminService
 * @Description
 * @author WU
 * @Date 2022/8/19 9:38
 */
@Service
public class AdminService {
	
	@Resource
	private AdminMapper adminMapper;
	@Resource
	private PermissionMapper permissionMapper;
	
	public Admin findAdminByUserName(String username){
		LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Admin::getUsername,username).last("limit 1");
		return adminMapper.selectOne(queryWrapper);
	}
	

	/**
	 * @Description 按管理员Id查找权限(多加了个s)
	 * @Param [adminId]
	 * @return java.util.List<com.hsxy.blog.admin.pojo.Permission>
	 */
	public List<Permission> findPermissionsByAdminId(Long adminId){
		return adminMapper.findPermissionsByAdminId(adminId);
	}
	
}