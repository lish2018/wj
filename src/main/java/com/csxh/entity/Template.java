package com.csxh.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 问卷模板
 */
public class Template implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7767562489095561154L;

	/**
     * Default constructor
     */
    public Template() {
    }

    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private Admin admin;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private Integer type;

    
    private Date createTime;
    
    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
     * 题目列表
     */
    private List<TemplateQuestion> questions;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}


	public List<TemplateQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<TemplateQuestion> questions) {
		this.questions = questions;
	}

}