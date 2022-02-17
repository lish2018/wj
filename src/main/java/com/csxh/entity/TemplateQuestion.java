package com.csxh.entity;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 模板问题
 */
public class TemplateQuestion implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5792324312006402037L;


	/**
     * Default constructor
     */
    public TemplateQuestion() {
    }

    /**
     * 
     */
    private Integer id;

    /**
     * 模板引用对象
     */
    private Template template;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private Integer type;

    /**
     * 
     */
    private Integer isRequired;

    /**
     * 
     */
    private String text;

    /**
     * 
     */
    private String score;
    
    /**
     * 
     */
    @JSONField(name = "isimpt")
    private Integer isKey;
    
    /**
     * 
     */
    @JSONField(name = "qid")
    private Integer number;
    

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getIsKey() {
		return isKey;
	}

	public void setIsKey(Integer isKey) {
		this.isKey = isKey;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
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

	public Integer getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(Integer isRequired) {
		this.isRequired = isRequired;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
}