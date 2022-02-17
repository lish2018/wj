package com.csxh.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.csxh.entity.Answer;
import com.csxh.entity.Question;

public interface AnswerService {
	/**
	 * 获取Anser列表 分页查询 通过班级ID、问卷ID查询
	 * 
	 * @param map
	 * @return
	 */
	public List<Answer> listAnswerByPageAndTeamId(Map<String, Object> map);

	/**
	 * 获取某班某问卷的Anser记录数
	 * 
	 * @return
	 */
	public Integer getTotalCount(Map<String, Object> map);

	public void addAnswer(Answer answer);

	public ArrayList<Question> getQuestionsListByQuestionnaireId(Integer questionnaireId);
}
