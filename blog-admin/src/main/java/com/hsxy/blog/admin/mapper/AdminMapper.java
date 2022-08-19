package com.hsxy.blog.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hsxy.blog.admin.pojo.Admin;
import com.hsxy.blog.admin.pojo.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @name AdminMapper
 * @Description
 * @author WU
 * @Date 2022/8/19 9:38
 */
public interface AdminMapper extends BaseMapper<Admin> {
	
	/**
	 * @Description 按管理员Id查找权限(多加了个s)
	 * @Param [adminId]
	 * @return java.util.List<com.hsxy.blog.admin.pojo.Permission>
	 */
	@Select("SELECT * FROM ms_permission where id in (select permission_id from ms_admin_permission where admin_id=#{adminId})")
	List<Permission> findPermissionsByAdminId(Long adminId);
}
