package com.csxh.entity;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 问卷题目表
 */
public class Question implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5687741238273777163L;

	/**
     * Default constructor
     */
    public Question() {}

    /**
     * 
     */
    private Integer id;

    /**
     * 题目所属问卷引用对象
     */
    private Questionnaire questionnaire;

    /**
     * 题目标题
     */
    private String title;

    /**
     * 题目类型{0:单选,1:多选,2填空}
     */
    private Integer type;

    /**
     * 是否必填
     */
    private Integer isRequired;

    /**
     * 题目序号
     */
    private Integer number;

    /**
     * 选项文本，存储多个选项，选项与选项之间用`分割
     */
    private String text;

    /**
     * 选项分数，存储多个选项，选项与选项之间用`分割
     */
    private String score;
    
    private Integer isKey;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
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

	public Integer getNumber() {
		return number;
	}
	
	@JSONField(name = "qid")
	public void setNumber(Integer number) {
		this.number = number;
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

	public Integer getIsKey() {
		return isKey;
	}
	
	
	@JSONField(name = "isimpt")
	public void setIsKey(Integer isKey) {
		this.isKey = isKey;
	}

	public Question(Integer id, Questionnaire questionnaire, String title, Integer type, Integer isRequired,
			Integer number, String text, String score, Integer isKey) {
		super();
		this.id = id;
		this.questionnaire = questionnaire;
		this.title = title;
		this.type = type;
		this.isRequired = isRequired;
		this.number = number;
		this.text = text;
		this.score = score;
		this.isKey = isKey;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", questionnaire=" + questionnaire + ", title=" + title + ", type=" + type
				+ ", isRequired=" + isRequired + ", number=" + number + ", text=" + text + ", score=" + score
				+ ", isKey=" + isKey + "]";
	}

	
	
	

}