/**
 * 
 */
package com.csxh.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csxh.dao.AdminDao;
import com.csxh.entity.Admin;
import com.csxh.entity.Role;
import com.csxh.entity.Log;
import com.csxh.service.AdminService;
import com.csxh.tools.PasswordHelper;

/**
 * @author xxl
 *
 */
@Service
public class AdminServiceImpl implements AdminService {	//AdminService实现类
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
    private PasswordHelper passwordHelper;
	
	/**
	 * 创建管理员
	 */
	@Override
	public Integer createAdmin(Admin admin) {
		Admin retAdmin = adminDao.findAdminByAdminname("admin");
		if(retAdmin != null) {
			return -1;
		}
		String salt = admin.getCredentialsSalt();
		admin.setSalt(salt);//保存盐
		admin.setPassword(passwordHelper.encryptPassword(admin.getPassword(), salt));//加密密码
		return adminDao.createAdmin(admin);
	}
	/**
	 * 修改管理员信息
	 */
	@Override
	public Integer updateAdmin(Admin admin) {
		return adminDao.updateAdmin(admin);
	}
	/**
	 * 修改密码
	 */
	@Override
	public Integer changePassword(Admin admin, String oldPassword, String newPassword) {

		if(admin.getPassword().equals(passwordHelper.encryptPassword(oldPassword, admin.getSalt()))) {

			return adminDao.changePassword(admin.getAdminname(), passwordHelper.encryptPassword(newPassword, admin.getSalt()));
		}
		return -1;
	}
	/**
	 * 删除管理员
	 */
	@Override
	public Integer deleteAdmin(Admin admin) {
		return adminDao.deleteAdmin(admin);
	}
	/**
	 * 无条件查询所有管理员
	 */
	@Override
	public List<Admin> findAll() {
		return adminDao.findAll();
	}
	/**
	 * 根据管理员名字查询管理员
	 */
	@Override
	public Admin findAdminByAdminname(String adminname) {
		return adminDao.findAdminByAdminname(adminname);
	}
	/**
	 * 根据邮箱查询管理员
	 */
	@Override
	public Admin findAdminByEmail(String adminname) {
		return adminDao.findAdminByEmail(adminname);
	}
	/**
	 * 根据管理员名字查询管理员角色
	 */
	@Override
	public String findRoles(String adminname) {
		return adminDao.findRoles(adminname);
	}
	/**
	 * 根据管理员名字查询管理员权限
	 */
	@Override
	public Set<String> findPermissions(String adminname) {
		return adminDao.findPermissions(adminname);
	}
	
	/**
	 * 管理员操作日志
	 */
	@Override
	public void insertAdminLog(Log log) {
		// TODO Auto-generated method stub
		adminDao.insertAdminLog(log);
	}
	
	/**
	 * 修改管理员信息
	 */
	@Override
	public Integer changeAdmin(Admin admin) {
		return adminDao.changeAdmin(admin);
	}	
}
