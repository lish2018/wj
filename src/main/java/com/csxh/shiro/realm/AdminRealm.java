package com.csxh.shiro.realm;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.csxh.entity.Admin;
import com.csxh.entity.Role;
import com.csxh.service.AdminService;

/**
 * 自定义Realm
 * 过adminService获取帐号及角色/权限信息。
 */
public class AdminRealm extends AuthorizingRealm {
	
	@Autowired
    private AdminService adminService;
	/**
	 * 自定义Realm的名字
	 */
	@Override
	public String getName() {
		return "AdminRrealm";
	}
	
	/**
	 * 认证方法
	 * @param token 
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		//加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (token.getPrincipal() == null) {
            return null;
        }
		
		String adminName = (String) token.getPrincipal();//获取管理员名字
		Admin admin = adminService.findAdminByAdminname(adminName);//从数据库查找用户
		if(admin == null) {
			throw new UnknownAccountException("用户名不存在！");//没找到帐号
		}
		if(1 == admin.getFlag()) {
	        throw new LockedAccountException("账号被锁定！"); //帐号禁用
	    }
		
		/**
		 * 存入用户信息
		 */
		List<Object> principals = new ArrayList<Object>();
		principals.add(admin.getAdminname());
		principals.add(admin);
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				 	principals, //用户信息
	                admin.getPassword(), //密码
	                ByteSource.Util.bytes(admin.getSalt()),//加密后的盐值
	                getName()//realm name
	        );
	        return authenticationInfo;//返回认证信息
	}
	
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		/**
		 *1.	从PrincipalCollection 中获取用户登录的信息
		 *2.	利用登录的用户信息来获取角色或权限
		 *3.	创建SimpleAuthorizationInfo，并设置roles属性
		 */
			Object admin = principals.getPrimaryPrincipal();//获取管理员信息
			System.out.println("欢迎来到授权方法！");
//			String roles = adminService.findRoles((String)admin);//根据管理员名字查找管理员角色
//			System.out.println("授权打印的信息："+roles);
			Set<String> permissions = adminService.findPermissions((String)admin);//根据管理员名字查找管理员权限
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			info.setStringPermissions(permissions);//将权限名称提供给授权信息
	        return info;//返回授权信息
	}
	

}
