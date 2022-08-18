package com.hsxy.blog.admin.controller;

import com.hsxy.blog.admin.model.params.PageParam;
import com.hsxy.blog.admin.pojo.Permission;
import com.hsxy.blog.admin.service.PermissionService;
import com.hsxy.blog.admin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @name AdminController
 * @Description
 * @author WU
 * @Date 2022/8/18 18:47
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private PermissionService permissionService;
	
	@PostMapping("/permission/permissionList")
	public Result permissionList(@RequestBody PageParam pageParam){
		return permissionService.listPermission(pageParam);
	}
	
	@PostMapping("/permission/add")
	public Result add(@RequestBody Permission permission){
		return permissionService.add(permission);
	}
	
	@PostMapping("/permission/update")
	public Result update(@RequestBody Permission permission){
		return permissionService.update(permission);
	}
	
	@GetMapping("/permission/delete/{id}")
	public Result delete(@PathVariable("id") Long id){
		return permissionService.delete(id);
	}
}
