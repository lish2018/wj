package com.csxh.dao;

import java.util.List;
import java.util.Map;

import com.csxh.entity.Answer;

/**
 * 
 * @author Lish
 *
 */
public interface AnswerDao {
	//public List<Answer> getAnswerList();
	
	/**
	 * 获取Anser列表 分页查询 通过 班级ID、问卷ID 查询 
	 * @param map
	 * @return
	 */
	public List<Answer> listAnswerByPageAndTeamId(Map<String, Object> map);

	/**
	 * 获取Anser记录数 通过 班级ID、问卷ID 查询
	 * @return
	 */
	public Integer getCount(Map<String, Object> map);
	
	/**
	 * 保存用户填写的答卷数据
	 * @param answer 
	 */
	public void addAnswer(Answer answer);
}
