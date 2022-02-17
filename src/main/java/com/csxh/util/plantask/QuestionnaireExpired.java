package com.csxh.util.plantask;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.csxh.service.QuestionService;

/**
 * 问卷截止
 * @author hcx
 */
public class QuestionnaireExpired implements Job {

	public QuestionnaireExpired() {
		
	}
	
	@Resource 
	JobUtil quartzManager;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		Map<String, Object> map = context.getMergedJobDataMap();
		
		
		Integer questionnaireId = (Integer)map.get("questionnaireId");
		QuestionService questionService = (QuestionService)map.get("questionService");
		
		System.out.println("执行自动截止：" + questionnaireId);
		questionService.stopQuestionniare(questionnaireId);
	}
	
	int idCounter = 0;
	
	static final String JOB_NAME = "job";
	static final String TRIGGER_NAME = "trigger";
	static final String GROUP_NAME = "group1";
	
	/**
	 * 计划任务业务部分
	 * @param questionnaireId
	 * @param date
	 * @return
	 */
	public Integer expiredPlan(Integer questionnaireId, Date date, QuestionService questionService) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("questionnaireId", questionnaireId);
		map.put("date", date);
		map.put("questionService", questionService);
		
		quartzManager.addJob(JOB_NAME + idCounter, GROUP_NAME, 
				TRIGGER_NAME + idCounter, GROUP_NAME, 
				this.getClass(), date, map);

		idCounter++;
		return idCounter - 1;
	}
	
	/**
	 * 更改计划时间
	 * @param id
	 * @param date
	 */
	public void modifyPlan(Integer id, Date date) {
		quartzManager.modifyJobTime(TRIGGER_NAME + id, GROUP_NAME, date);
	}
	
	/**
	 * 移除计划
	 * @param id
	 */
	public void removePlan(Integer id) {
		quartzManager.removeJob(JOB_NAME + id, GROUP_NAME, 
				TRIGGER_NAME + id, GROUP_NAME);
	}
}
