package com.csxh.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.csxh.entity.Team;
import com.csxh.entity.Template;
import com.csxh.entity.TemplateQuestion;


public interface TemplateService {
	//获取所有模板 
	public List<Template> getTemplateList(Map<String,Object> map);
	
	// 添加模板题目
	int addTemplateQuestion(List<TemplateQuestion> questionList);
	
	// 添加模板
	int addTemplate(Template questionnaire);
	
	// 删除模板和指定模板的题目
	int deleteTemplateQuestionById(Integer id);
	
	// 拿到某个模板的题目
	public List<TemplateQuestion> getTemplateQuestionsList(Integer questionnaireId);
	
	// 根据ID查找模板
	Template getTemplateById(Integer id);
	
	// 更新模板的数据
	int updateTemplate(Template questionnaire);
	
	// 更新模板题目的数据
	int updateTemplateQuestion(List<TemplateQuestion> questionList);
	
	// 查询模板的总数量
	int getTemplateCount(Map<String, Object> map);
	
	// 搜索模板
	List<Template> searchTemplate(String keyword);
	
	// 根据ID删除一条模板的题目
	int deleteOneTemplateQuestionById(Integer id);
	
	// 发布模板
	int releaseTemplate(Integer id, HttpServletRequest request);
	
	//查找某一张模板里面是否存在这个ip地址
	String searchIpByTemplateId(String ip,Integer questionnaireId);

	//手动截止模板
	int stopTemplate(Integer id);
	
}
