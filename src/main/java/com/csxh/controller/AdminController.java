package com.csxh.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csxh.entity.Admin;
import com.csxh.entity.Role;
import com.csxh.service.AdminService;


@Controller
public class AdminController {
	@Autowired
    private AdminService adminService;
	
	/**
	 * 管理员中心界面
	 * @return
	 */
	@RequiresPermissions("admin:list")//查看管理员中心权限
	@GetMapping("admin/center")
	public String center() {
		return "adminCenter";
	}
	/**
	 * 添加管理员
	 * @param admin
	 * @return
	 */
	@PostMapping("admin/add")
	@RequiresPermissions("admin:user:insert")//添加管理员权限
	@ResponseBody
	public String addUser(String adminname, String password, String phone, String email, Integer roleId) {
		Admin admin = new Admin();
		admin.setAdminname(adminname);
		admin.setPassword(password);
		admin.setPhone(phone);
		admin.setEmail(email);
		
		Role role = new Role();
		role.setId(roleId);
		
		admin.setRole(role);
		
		return adminService.createAdmin(admin).toString();
	}

	/**
	 * 编辑管理员
	 * @param admin
	 * @return
	 */
	@PostMapping("admin/update")
	@RequiresPermissions("admin:user:update")//编辑管理员权限
	@ResponseBody
	public String update(String adminname, String password, String phone, String email, Integer roleId, HttpSession session) {
		Admin admin = new Admin();
		admin.setAdminname(adminname);
		admin.setPassword(password);
		admin.setPhone(phone);
		admin.setEmail(email);
		
		Role role = new Role();
		role.setId(roleId);
		
		admin.setRole(role);
		

		Integer ret = adminService.changeAdmin(admin);
		
		return ret.toString();
	}

	/**
	 * 删除管理员
	 * @param admin
	 * @return
	 */
	@PostMapping("admin/del")
	@RequiresPermissions("admin:user:delete")//删除管理员权限
	@ResponseBody
	public String del(Admin admin) {
		return adminService.deleteAdmin(admin).toString();
	}

	
	/**
	 * 密码修改
	 * @param admin
	 * @return
	 */
	@PostMapping("admin/changepass")
	@RequiresPermissions("admin:changepwd")//修改密码权限
	@ResponseBody
	public Integer changePassword(String oldpw, String newpw) {
		Admin admin = SecurityUtils.getSubject().getPrincipals().oneByType(Admin.class);
		
		return adminService.changePassword(admin, oldpw, newpw);
	}
	
	/**
	 * 个人信息修改
	 * @param admin
	 * @return
	 */
	@PostMapping("admin/changeAdmin")
	@RequiresPermissions("admin:update")//修改个人信息权限
	@ResponseBody
	public void changeAdmin(String phone,String email, HttpSession sestion) {
		Admin admin = SecurityUtils.getSubject().getPrincipals().oneByType(Admin.class);
		admin.setPhone(phone);
		admin.setEmail(email);
		Integer ret = adminService.updateAdmin(admin);
		
		if(ret > 0) {
			Admin sessionadmin = (Admin)sestion.getAttribute("admin");
			sessionadmin.setPhone(admin.getPhone());
			sessionadmin.setEmail(admin.getEmail());
			sestion.setAttribute("admin", sessionadmin);
		}
	}

}
