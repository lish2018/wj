package com.csxh.tools;


/**
 * -问卷答案Json模板
 * @author 80463
 *
 */

public class TemplateJson {
	// 题目ID
	private Integer id;
    // 题目标题
    private String title;
    // 题目选项
    private String text;
    // 选项得分
    private String score;
    // 是否必填
    private Integer isRequired;
    // 题目类型
    private Integer type;
    // 题目序号
    private Integer qid;
    // 是否是关键项
    private Integer isimpt;
    
    
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public Integer getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(Integer isRequired) {
		this.isRequired = isRequired;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getQid() {
		return qid;
	}
	public void setQid(Integer qid) {
		this.qid = qid;
	}
	public Integer getIsimpt() {
		return isimpt;
	}
	public void setIsimpt(Integer isimpt) {
		this.isimpt = isimpt;
	}
    
    
    

    
}
