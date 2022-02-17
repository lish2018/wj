package com.csxh.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.csxh.entity.Resource;
import com.csxh.entity.Role;

public interface RoleService {
	/**
     * 创建角色
     * @return
     */
	Integer createRole(Role role);

    /**
     * 修改角色
     * @return
     */
	Integer updateRole(Role role);

    /**
     * 根据角色id删除角色信息
     * @return
     */
	Integer deleteRole(Integer roleId);

    /**
     * 根据角色id删除角色资源关系信息
     * @return
     */
    Integer deleteResoueceRefByRoleId(Integer roleId);

    /**
     * 查询所有角色
     * @return
     */
    List<Role> findAll();
    /**
     * 根据角色id查到权限
     * @param roleIds
     * @return
     */
    Set<String> findPermissions(Integer[] roleIds);
    
    /**
     * 根据角色名查询角色信息
     * @return
     */
    Role checkRepeat(String roleName);

    /**
     * 查询所有的角色
     * @return
     */
	List<Role> getRoleList();

	/**
	 * 查询所有的权限
	 * @return
	 */
	List<Resource> getResourceList();
	
	/**
	 * 查询所有角色
	 * 
	 * @param map
	 * @return
	 */
	List<Role> findRoleByName(Map<String, Object> map);
	
	/**
	 * 为角色添加权限
	 * @param list
	 * @return
	 */
	Integer addResourcesRef(List<Map<String, Object>> list);

	/**
	 * 获取某角色拥有的权限
	 * @param roleId
	 * @return
	 */
	List<Resource> getRoleResources(Integer roleId);
}
