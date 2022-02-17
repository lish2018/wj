package com.csxh.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志表
 */
public class Log implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 309928424296002787L;

	/**
     * Default constructor
     */
    public Log() {
    }

    /**
     * 
     */
    private Integer id;

    /**
     * 用户
     */
    private Admin admin;

    /**
     * 执行操作的IP
     */
    private String ip;

    /**
     * 操作类型
     */
    private Integer type;

    /**
     * 操作时间
     */
    private Date time;

    /**
     * 操作备注
     */
    private String comment;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Log(Integer id, Admin admin, String ip, Integer type, Date time, String comment) {
		super();
		this.id = id;
		this.admin = admin;
		this.ip = ip;
		this.type = type;
		this.time = time;
		this.comment = comment;
	}

}