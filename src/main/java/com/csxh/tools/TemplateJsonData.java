package com.csxh.tools;

import java.util.List;

/**
 * -Question模板Json模板
 * @author xww
 */
public class TemplateJsonData {
	// 文章标题
    private String title;
    // 模板类型
    private Integer type;
    // 模板题目
    private List<TemplateJson> subject;

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

    
	public List<TemplateJson> getSubject() {
		return subject;
	}

	public void setSubject(List<TemplateJson> subject) {
		this.subject = subject;
	}

}
