package com.csxh.service;

import java.util.Map;

/**
 * @author Lish
 *
 */
public interface TeamService {

	/**
	 * 返回报表所需的所有信息
	 * 
	 * @param questionnaireId
	 * @return teams
	 */
	public Map<String, Object> getTeamMap(int questionnaireId);

}
