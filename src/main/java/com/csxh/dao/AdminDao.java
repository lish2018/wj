package com.csxh.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.csxh.entity.Admin;
import com.csxh.entity.Role;
import com.csxh.entity.Log;
public interface AdminDao {
	/**
	 * 新增管理员
	 * @param admin
	 * @return
	 */
	public Integer createAdmin(Admin admin);
	/**
	 * 修改管理员信息
	 * @param admin
	 * @return
	 */
	public Integer updateAdmin(Admin admin);
	/**
     * 修改密码
     * @param adminname
     * @param newPassword
     */
	public Integer changePassword(@Param(value="adminname") String adminname,@Param(value="newPassword") String newPassword);
	/**
	 * 删除管理员
	 * @param admin
	 * @return
	 */
	public Integer deleteAdmin(Admin admin);
	/**
	 * 根据管理员id查询查询管理员信息
	 * @param adminId
	 * @return
	 */
	public Admin findAdmin(Integer adminId);
	/**
	 * 无条件查询所有管理员信息
	 * @return
	 */
	public List<Admin> findAll();
	/**
	 * 根据用户名查询管理员信息
	 * @param adminname
	 * @return
	 */
	public Admin findAdminByAdminname(String adminname);
	/**
	 * 根据邮箱查询管理员信息
	 * @param username
	 * @return
	 */
	public Admin findAdminByEmail(String email);
	
	/**
	 * 根据管理员名字查询管理员角色
	 */
	public String findRoles(String adminname);
	/**
	 * 根据管理员名字查询管理员权限
	 */
	public Set<String> findPermissions(String adminname);
		
	/**
	 * 新增管理员操作日志
	 * @param log
	 */
	public void insertAdminLog(Log log);
	
	/**
	 * 修改管理员
	 * @param admin
	 * @return
	 */
	public Integer changeAdmin(Admin admin);

}
