package com.csxh.dao;

import java.util.ArrayList;

import com.csxh.entity.Team;

/**
 * @author Lish
 *
 */
public interface TeamDao {

	/**
	 * 查询所有参与指定问卷的班级信息
	 * 
	 * @param questionnaireId
	 * @return teamList
	 */
	public ArrayList<Team> getTeamList(int questionnaireId);

	

}
