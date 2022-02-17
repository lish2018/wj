package com.csxh.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.csxh.entity.Resource;
import com.csxh.entity.Role;

public interface RoleDao {
	/**
     * 创建角色
     * @return
     */
	Integer createRole(@Param("role")Role role);
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
     * 根据角色id查询角色信息
     * @return
     */
    Role findRole(Integer roleId);
    
    /**
     * 根据角色名查询角色信息
     * @return
     */
    Role checkRepeat(String roleName);

    /**
     * 查询所有角色
     * @return
     */
    List<Role> findAll();
    
    /**
     * 查询所有的权限
     * @return
     */
	List<Resource> getAllResources();
	
	/**
	 * 通过名字查询角色
	 */
	List<Role> findRoleByName(Map<String, Object> map);
	
	/**
	 * 为角色添加权限
	 * @param list
	 * @return
	 */
	Integer addResourcesRef(List<Map<String, Object>> list);
	
	/**
	 * 通过角色ID查询用户数
	 * @param roleId
	 * @return
	 */
	Integer getAdminCountByRoleId(Integer roleId);

	/**
	 * 获取某角色拥有的权限
	 * @param roleId
	 * @return
	 */
	List<Resource> getRoleResources(Integer roleId);
}
