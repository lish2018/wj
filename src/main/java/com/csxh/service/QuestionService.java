package com.csxh.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.csxh.entity.Question;
import com.csxh.entity.Questionnaire;
import com.csxh.entity.Team;


public interface QuestionService {
	//获取所有问卷 
	public List<Questionnaire> getQuestionList(Map<String,Object> map);
	
	// 添加问卷题目
	int addQuestion(List<Question> questionList);
	
	// 添加问卷
	int addQuestionnaire(Questionnaire questionnaire);
	
	// 删除问卷和指定问卷的题目
	int deleteQuestionById(Integer id);
	
	// 拿到某个问卷的题目
	public List<Question> getQuestionsList(Integer questionnaireId);
	
	// 根据ID查找问卷
	Questionnaire getQuestionnaireById(Integer id);
	
	// 更新问卷的数据
	int updateQuestionnaire(Questionnaire questionnaire);
	
	// 更新问卷题目的数据
	int updateQuestion(List<Question> questionList);
	
	// 查询问卷的总数量
	int getQuestionnaireCount(Map<String, Object> map);
	
	// 搜索问卷
	List<Questionnaire> searchQuestionnaire(String keyword);
	
	// 根据ID删除一条问卷的题目
	int deleteOneQuestionById(Integer id);
	
	// 发布问卷
	int releaseQuestionnaire(Integer id, HttpServletRequest request);
	
	//查找某一张问卷里面是否存在这个ip地址
	String searchIpByQuestionnaireId(String ip,Integer questionnaireId);

	//手动截止问卷
	int stopQuestionniare(Integer id);
	
	//查找所有的班级
	List<Team> getTeamList();
	
	// 获取问卷的答案数量
	int getAnswersCount(Integer id);
	
	// 修改问卷答案数量
	int updateQuestionniareCount(Integer count,Integer id);
	
}
