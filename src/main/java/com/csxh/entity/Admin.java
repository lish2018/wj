package com.csxh.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 用户表
 */
public class Admin implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3091591390687097876L;

	/**
     * Default constructor
     */
    public Admin() {
    }

    /**
     * 
     */
    private Integer id;

    /**
     * 登录名
     */
    private String adminname;

    /**
     * 密码
     */
    private String password;
    /**
     *角色
     */
    private Role role;
    
	/**
     *盐值
     */
    private String salt;

	/**
     * 邮箱
     */
    private String email;

    /**
     * 用户状态 {0:正常,1:禁用}
     */
    private Integer flag;

    /**
     * 手机号码
     */
    private String phone;
    
    /**
     * 日志列表
     */
    private List<Log> logs;
    
    /**
     * 模板列表
     */
    private List<Template> templates;
    
    /**
     * 问卷列表
     */
    private List<Questionnaire> Questionnaires;

	public Admin(Integer id, String adminname, String password, Role role, String salt, String email, Integer flag,
			String phone, List<Log> logs, List<Template> templates, List<Questionnaire> questionnaires) {
		super();
		this.id = id;
		this.adminname = adminname;
		this.password = password;
		this.role = role;
		this.salt = salt;
		this.email = email;
		this.flag = flag;
		this.phone = phone;
		this.logs = logs;
		this.templates = templates;
		Questionnaires = questionnaires;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAdminname() {
		return adminname;
	}

	public void setAdminname(String adminname) {
		this.adminname = adminname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Log> getLogs() {
		return logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

	public List<Template> getTemplates() {
		return templates;
	}

	public void setTemplates(List<Template> templates) {
		this.templates = templates;
	}

	public List<Questionnaire> getQuestionnaires() {
		return Questionnaires;
	}

	public void setQuestionnaires(List<Questionnaire> questionnaires) {
		Questionnaires = questionnaires;
	}

	public String getCredentialsSalt() {
		int num = (int)(Math.random() * 100000);
		
		return adminname + email + num;
	}

	@Override
	public String toString() {
		return "Admin [id=" + id + ", adminname=" + adminname + ", password=" + password + ", role=" + role + ", salt="
				+ salt + ", email=" + email + ", flag=" + flag + ", phone=" + phone + ", logs=" + logs + ", templates="
				+ templates + ", Questionnaires=" + Questionnaires + "]";
	}


	
	
}