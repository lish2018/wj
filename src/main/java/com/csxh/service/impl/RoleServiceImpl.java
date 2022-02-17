package com.csxh.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csxh.dao.RoleDao;
import com.csxh.entity.Resource;
import com.csxh.entity.Role;
import com.csxh.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService{
	@Autowired 
	private RoleDao roleDao; 
	
	/**
	 * 创建角色
	 */
	@Override
	public Integer createRole(Role role) {
		
		return roleDao.createRole(role);
	}
	/**
	 * 更新角色
	 */
	@Override
	public Integer updateRole(Role role) {
		
		return roleDao.updateRole(role);
	}
	
	/**
	 * 删除角色
	 */
	@Override
	public Integer deleteRole(Integer roleId) {
		
		Integer ret = roleDao.getAdminCountByRoleId(roleId);
		
		if(ret > 0) {
			return -1;
		}
		
		int count = 0;
		count += roleDao.deleteRole(roleId);
		count += roleDao.deleteResoueceRefByRoleId(roleId);
		return count;
	}
	/**
	 * 无条件查找所有角色
	 */
	@Override
	public List<Role> findAll() {
		
		return roleDao.findAll();
	}
	/**
	 * 根据用户名查找其权限
	 */
	@Override
	public Set<String> findPermissions(Integer[] roleIds) {
		return null;
	}
	@Override
	public List<Role> getRoleList() {
		return roleDao.findAll();
	}
	
	/**
	 * 查询所有权限
	 */
	@Override
	public List<Resource> getResourceList() {
		return roleDao.getAllResources();
	}
	
	/**
	 * 查询所有角色
	 */
	@Override
	public List<Role> findRoleByName(Map<String, Object> map) {
		return roleDao.findRoleByName(map);
	}

	
	/**
	 * 为角色添加权限
	 * @param list
	 * @return
	 */
	public Integer addResourcesRef(List<Map<String, Object>> list){
		return roleDao.addResourcesRef(list);
	}

    
    /**
     * 根据角色名查询角色信息
     * @return
     */
	@Override
	public Role checkRepeat(String roleName) {
		return roleDao.checkRepeat(roleName);
	}


    /**
     * 根据角色id删除角色资源关系信息
     * @return
     */
	@Override
	public Integer deleteResoueceRefByRoleId(Integer roleId) {
		return roleDao.deleteResoueceRefByRoleId(roleId);
	}

	/**
	 * 获取某角色拥有的权限
	 * @param roleId
	 * @return
	 */
	@Override
	public List<Resource> getRoleResources(Integer roleId) {
		return roleDao.getRoleResources(roleId);
	}
}
