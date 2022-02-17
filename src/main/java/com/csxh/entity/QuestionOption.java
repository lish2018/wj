package com.csxh.entity;

/**
 * 问题的选项
 * @author liliya
 *
 */
public class QuestionOption {
	private Integer id;
	private String value;
	private String score;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "QuestionOption [id=" + id + ", value=" + value + "]";
	}
	public QuestionOption(Integer id, String value) {
		super();
		this.id = id;
		this.value = value;
	}
	
	public QuestionOption() {
	}
	public String getScore() {
		return score;
	}
	public QuestionOption(Integer id, String value, String score) {
		super();
		this.id = id;
		this.value = value;
		this.score = score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	
	
}
