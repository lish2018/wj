package com.csxh.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csxh.dao.AnswerDao;
import com.csxh.dao.QuestionDao;
import com.csxh.entity.Answer;
import com.csxh.entity.Question;
import com.csxh.service.AnswerService;

/**
 * 
 * @author Lish
 *
 */
@Service
public class AnswerServiceImpl implements AnswerService {
	@Autowired
	private AnswerDao answerDao;
	@Autowired
	private QuestionDao QuestionDao;

	@Override
	public void addAnswer(Answer answer) {
		answerDao.addAnswer(answer);
	}

	@Override
	public List<Answer> listAnswerByPageAndTeamId(Map<String, Object> map) {
		return answerDao.listAnswerByPageAndTeamId(map);
	}

	@Override
	public Integer getTotalCount(Map<String, Object> map) {
		return answerDao.getCount(map);
	}

	@Override
	public ArrayList<Question> getQuestionsListByQuestionnaireId(Integer questionnaireId) {
		return QuestionDao.getQuestionsListByQuestionnaireId(questionnaireId);
	}

}
