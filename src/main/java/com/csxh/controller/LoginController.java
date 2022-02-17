package com.csxh.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.csxh.entity.Admin;
import com.csxh.tools.PasswordHelper;
@Controller
public class LoginController {
	
	@PostMapping("/login")
	public String login(String username,String password, HttpSession session,Model model) {

		try {
			username = new String(username.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			
			e1.printStackTrace();
		}
		
//		String pwd = passwordHelper.encryptPassword(password, "admin");//加密
//		System.out.println("自定义加密算法的密码是："+pwd);
		
		//使用 shiro 登录验证
		//1 认证的核心组件：获取 Subject 对象
		Subject subject = SecurityUtils.getSubject();
		//2 将登陆表单封装成 token 对象
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			//3 让 shiro 框架进行登录验证：
			subject.login(token);
			System.out.println("登陆成功!");
			String currentAdmin = (String) SecurityUtils.getSubject().getPrincipal();
			System.out.println("当前用户是："+currentAdmin);
			
			Admin admin = SecurityUtils.getSubject().getPrincipals().oneByType(Admin.class);
			
			System.out.println(admin);
			
			session.setAttribute("admin", admin);
			
		} catch (UnknownAccountException e) {
			e.printStackTrace();
			System.out.println("登录失败!");
			model.addAttribute("message", "您的账户不存在！");
			model.addAttribute("url", "/index");
			return "info";
		}catch (IncorrectCredentialsException e) {
			e.printStackTrace();
			model.addAttribute("message", "用户名或密码错误！");
			model.addAttribute("url", "/index");
			return "info";
		}catch (LockedAccountException e) {
			e.printStackTrace();
			model.addAttribute("message", "您的账户已被禁用！");
			model.addAttribute("url", "/index");
			return "info";
		}catch (ExcessiveAttemptsException e) {
			e.printStackTrace();
			model.addAttribute("message", "登录失败次数过多！");
			model.addAttribute("url", "/index");
			return "info";
		}
		
		return "redirect:/admin/question/list";
	}
	
	@PostMapping("admin/logout")
	public String logout(HttpSession session) {

		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		
		session.removeAttribute("admin");
		
		return "index";
	}
}