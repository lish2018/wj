package com.csxh.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 提交数据表
 */
public class Answer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5050871159209756743L;

	/**
	 * Default constructor
	 */
	public Answer() {
	}

	/**
	 * 
	 */
	private Integer Id;

	/**
	 * 所属班级
	 */
	private Team team;

	/**
	 * 答卷者IP
	 */
	private String ip;

	private int score;

	@Override
	public String toString() {
		return "Answer [Id=" + Id + ", team=" + team + ", ip=" + ip + ", questionnaire=" + questionnaire
				+ ", commitTime=" + commitTime + ", radio=" + radio + ", checkbox=" + checkbox + ", text=" + text
				+ ", key=" + key + ",score=" + score + "]";
	}

	/**
	 * 所属问卷
	 */
	private Questionnaire questionnaire;

	/**
	 * 提交时间
	 */
	private Date commitTime;

	/**
	 * 单选答案 表示方法:tid1^select1` tid2^select2` tid3^select3 `分割每道题 ^分割选择
	 */
	public String radio;

	/**
	 * 多选答案 表示方法:tid1^select1^select2^select3`tid2^select1` tid2^select1`
	 * tid3^select1^select2 `分割每道题 ^分割选择
	 */
	public String checkbox;

	/**
	 * 填空答案 表示方法：tid1^answer1`tid2^answer2`tid3^answer3
	 * 
	 * `分割每道题 ^分割回答
	 */
	private String text;

	/**
	 * 关键填空答案 表示方法：tid1^answer1`tid2^answer2`tid3^answer3
	 * 
	 * `分割每道题 ^分割回答
	 */
	private String key;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public Date getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public String getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(String checkbox) {
		this.checkbox = checkbox;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	public Answer(Integer id, Team team, String ip, Questionnaire questionnaire, Date commitTime, String radio,
			String checkbox, String text, String key) {
		super();
		Id = id;
		this.team = team;
		this.ip = ip;
		this.questionnaire = questionnaire;
		this.commitTime = commitTime;
		this.radio = radio;
		this.checkbox = checkbox;
		this.text = text;
		this.key = key;
	}

}