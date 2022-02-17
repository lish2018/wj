package com.csxh.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 问卷表
 */
public class Questionnaire implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -303568473593356541L;

	/**
     * Default constructor
     */
    public Questionnaire() {
    }

    /**
     * 
     */
    private Integer id;

    /**
     * 创建人
     */
    private Admin admin;

    /**
     * 问卷标题
     */
    private String title;

    /**
     * 问卷类型{0:校园环境,1:教学质量}
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 截止时间
     */
    private Date endTime;

    /**
     * 问卷状态{0:未发布,1:已发布,2已截止}
     */
    private Integer status;

    /**
     * 问卷发布后的url
     */
    private String url;

    /**
     * 二维码路径
     */
    private String QRurl;

    /**
     * 问卷题目数
     */
    private Integer count;
    
    /**
     * 问题列表
     */
    private List<Question> questions;
    
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getQRurl() {
		return QRurl;
	}

	public void setQRurl(String qRurl) {
		QRurl = qRurl;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public Questionnaire(Integer id, Admin admin, String title, Integer type, Date createTime, Date endTime,
			Integer status, String url, String qRurl, Integer count, List<Question> questions, List<Answer> answers) {
		super();
		this.id = id;
		this.admin = admin;
		this.title = title;
		this.type = type;
		this.createTime = createTime;
		this.endTime = endTime;
		this.status = status;
		this.url = url;
		QRurl = qRurl;
		this.count = count;
		this.questions = questions;
		this.answers = answers;
	}

	@Override
	public String toString() {
		return "Questionnaire [id=" + id + ", admin=" + admin + ", title=" + title + ", type=" + type + ", createTime="
				+ createTime + ", endTime=" + endTime + ", status=" + status + ", url=" + url + ", QRurl=" + QRurl
				+ ", count=" + count + ", questions=" + questions + ", answers=" + answers + "]";
	}

}