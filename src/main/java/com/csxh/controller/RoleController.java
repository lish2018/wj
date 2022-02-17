package com.csxh.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csxh.entity.Resource;
import com.csxh.entity.Role;
import com.csxh.service.RoleService;

@Controller
public class RoleController {
	@Autowired
	private RoleService roleService;
	/**
	 * 添加角色
	 * @return
	 */
	@PostMapping("Role/add")
	@ResponseBody
	public Integer addRole(String jsonobj) {
		
		JSONObject obj =  JSON.parseObject(jsonobj);
		
		Role ret = roleService.checkRepeat(obj.getString("rolename"));
		if(ret != null) {
			return -1;
		}
		
		Role role = new Role();
		role.setRoleName(obj.getString("rolename"));
		role.setRemark(obj.getString("description"));
		
		//新增角色
		Integer recordNum = roleService.createRole(role);
		
		
		if(recordNum < 1) {
			return -2;
		}
		
		JSONArray ids = obj.getJSONArray("ids");
		
		List<Map<String, Object>> rrlist = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> loginResource = new HashMap<String, Object>();
		loginResource.put("roleId", role.getId());
		loginResource.put("resourceId", 1);
		
		rrlist.add(loginResource);
		
		if(ids.size() > 0) {
			for(int i = 0; i < ids.size(); ++i) {
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("roleId", role.getId());
				map.put("resourceId", ids.getInteger(i));
				
				rrlist.add(map);
			}
		}
		
		Integer result = roleService.addResourcesRef(rrlist);
		
		return result;
	}
	
	
	/**
	 * 编辑角色
	 */
	@PostMapping("Role/update")
	@ResponseBody
	public Integer updateRole(String jsonobj) {
		
		JSONObject obj =  JSON.parseObject(jsonobj);
		
		Role role = new Role();
		role.setId(obj.getInteger("roleId"));
		role.setRoleName(obj.getString("rolename"));
		role.setRemark(obj.getString("description"));
		
		//修改角色信息
		Integer recordNum = roleService.updateRole(role);
		
		if(recordNum < 1) {
			return -2;
		}
		
		//删除已存在的关系
		roleService.deleteResoueceRefByRoleId(role.getId());
		
		//添加新关系
		JSONArray ids = obj.getJSONArray("ids");
		
		List<Map<String, Object>> rrlist = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> loginResource = new HashMap<String, Object>();
		loginResource.put("roleId", role.getId());
		loginResource.put("resourceId", 1);
		
		rrlist.add(loginResource);
		
		if(ids.size() > 0) {
			for(int i = 0; i < ids.size(); ++i) {
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("roleId", role.getId());
				map.put("resourceId", ids.getInteger(i));
				
				rrlist.add(map);
			}
		}
		
		Integer result = roleService.addResourcesRef(rrlist);
		
		return result;
	}
	/**
	 * 删除角色
	 */
	@PostMapping("Role/delete")
	@ResponseBody
	public Integer deleteRole(Integer roleId) {
		return roleService.deleteRole(roleId);
	}
	
	@PostMapping(value = "Role/get", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String get() {
		List<Role> roles = roleService.getRoleList();
		return JSON.toJSONString(roles);
	}
	

	@PostMapping(value = "Role/getResources", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String getResources() {
		List<Resource> resources = roleService.getResourceList();
		
		resources.remove(0);
		
		return JSON.toJSONString(resources);
	}
	

	@PostMapping(value = "Role/getRoleResources", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String getRoleResources(Integer roleId) {
		List<Resource> resources = roleService.getRoleResources(roleId);
		
		resources.remove(0);
		
		return JSON.toJSONString(resources);
	}
	

	
	@PostMapping(value = "Role/getbyname", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String getbyname(String roleName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("permName", roleName);
		
		List<Role> roles = roleService.findRoleByName(map);
		return JSON.toJSONString(roles);
	}
	
	
	
	
}
