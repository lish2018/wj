package com.csxh.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 
 */
public class Team implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8693701966808846677L;

	/**
     * Default constructor
     */
    public Team() {
    }

    /**
     * 
     */
    private Integer id;

    /**
     * 班级名
     */
    private String name;

    /**
     * 班级人数
     */
    private Integer number;
    
    /**
     * 答卷列表
     */
    private List<Answer> answers;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public Team(Integer id, String name, Integer number, List<Answer> answers) {
		super();
		this.id = id;
		this.name = name;
		this.number = number;
		this.answers = answers;
	}

}