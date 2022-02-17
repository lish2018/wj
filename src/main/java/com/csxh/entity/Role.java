package com.csxh.entity;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5822003754184496963L;
	/**
	 * 角色id
	 */
	private int id;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 角色备注
	 */
	private String remark;
	/**
	 * 角色权限集合
	 */
	private List<Integer> permissionIds;
	
	/**
	 * 角色是否禁用
	 */
	private int flag;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Integer> getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(List<Integer> permissionIds) {
		this.permissionIds = permissionIds;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Role(int id, String roleName, String remark, List<Integer> permissionIds, int flag) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.remark = remark;
		this.permissionIds = permissionIds;
		this.flag = flag;
	}

	public Role() {
		super();
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", remark=" + remark + ", permissionIds=" + permissionIds
				+ ", flag=" + flag + "]";
	}
	
	
}
