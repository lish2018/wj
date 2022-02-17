package com.csxh.tools;

import java.util.List;
import com.csxh.entity.Question;

/**
 * -Question问卷Json模板
 * @author xww
 */
public class QuestionJsonData {
	// 文章标题
    private String title;
    // 问卷题目数量
    private Integer count;
    // 问卷类型
    private Integer type;
    // 问卷题目
    private List<QuestionJson> subject;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    
	public List<QuestionJson> getSubject() {
		return subject;
	}

	public void setSubject(List<QuestionJson> subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "QuestionJsonData [title=" + title + ", count=" + count + ", type=" + type + ", subject=" + subject
				+ "]";
	}

	

}
