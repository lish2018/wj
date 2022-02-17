package com.csxh.service;

import java.util.List;
import java.util.Set;

import com.csxh.entity.Admin;
import com.csxh.entity.Role;
import com.csxh.entity.Log;


public interface AdminService {
	/**
     * 创建管理员
     * @param admin
     */
	public Integer createAdmin(Admin admin);
    /**
     * 更新管理员信息
     * @param admin
     * @return
     */
	public Integer updateAdmin(Admin admin);
	/**
     * 修改密码
     * @param adminname
     * @param newPassword
     */
	public Integer changePassword(Admin admin, String old, String newPassword);
	/**
	 * 删除管理员
	 * @param admin
	 * @return
	 */
	public Integer deleteAdmin(Admin admin);
	/**
	 * 无条件查询所有管理员
	 * @return
	 */
	public List<Admin> findAll();
	/**
	 * 根据用户名查询管理员
	 * @param adminname
	 * @return
	 */
	public Admin findAdminByAdminname(String adminname);
	/**
	 * 根据邮箱查询管理员
	 * @param adminname
	 * @return
	 */
	public Admin findAdminByEmail(String adminname);
	/**
     * 根据管理员名查找其角色
     * @param adminname
     * @return
     */
	String findRoles(String adminname);
    
  /**
   * 根据管理员名查找权限
   * @param adminname
   * @return
   */
    Set<String> findPermissions(String adminname);
    
    /**
	 * 新增管理员操作日志
	 * @param log
	 */
	public void insertAdminLog(Log log);
	
	/**
	 * 修改管理员信息
	 * @param admin
	 * @return
	 */
	public Integer changeAdmin(Admin admin);
}
