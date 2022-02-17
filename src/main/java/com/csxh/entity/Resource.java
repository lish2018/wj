package com.csxh.entity;

import java.io.Serializable;

public class Resource implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1366590299323059480L;
	/**
	 * id
	 */
	private int id;
	/**
	 * 资源名称
	 */
	private String resName;
	/**
	 * 权限名称
	 */
	private String permName;
	/**
	 * 权限
	 */
	private String perm;
	/**
	 * 是否禁用
	 */
	private int flag;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getPermName() {
		return permName;
	}
	public void setPermName(String permName) {
		this.permName = permName;
	}
	public String getPerm() {
		return perm;
	}
	public void setPerm(String perm) {
		this.perm = perm;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public Resource(int id, String resName, String permName, String perm, int flag) {
		super();
		this.id = id;
		this.resName = resName;
		this.permName = permName;
		this.perm = perm;
		this.flag = flag;
	}
	public Resource() {
		super();
	}
	@Override
	public String toString() {
		return "Resource [id=" + id + ", resName=" + resName + ", permName=" + permName + ", perm=" + perm + ", flag="
				+ flag + "]";
	}

	
}
