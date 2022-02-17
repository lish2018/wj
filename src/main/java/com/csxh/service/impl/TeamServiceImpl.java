package com.csxh.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csxh.dao.QuestionDao;
import com.csxh.dao.TeamDao;
import com.csxh.entity.Answer;
import com.csxh.entity.Question;
import com.csxh.entity.Team;
import com.csxh.service.TeamService;

/**
 * @author Lish
 */
@Service
public class TeamServiceImpl implements TeamService {
	@Autowired
	private TeamDao TeamDao;
	@Autowired
	private QuestionDao QuestionDao;

	@Override
	public Map<String, Object> getTeamMap(int questionnaireId) {
		// TODO Auto-generated method stub
		Map<String, Object> reportMap = new HashMap<String, Object>();
		// 问题列表
		ArrayList<Question> questionList = QuestionDao.getQuestionsListByQuestionnaireId(questionnaireId);
		// 班级列表
		ArrayList<Team> teamList = getTeamList(questionnaireId);
		// 班级名字
		ArrayList<Map<String, Integer>> teamNameMap = new ArrayList<Map<String, Integer>>();
		// 每个班的总人数
		int[] teamNums = new int[teamList.size()];
		// 答卷列表
		ArrayList<Answer> AnswerList = new ArrayList<Answer>();

		// 每个题的每个选项的分数
		ArrayList<Integer[]> scoreList = new ArrayList<Integer[]>();
		for (Question q : questionList) {
			if (q.getType() == 0) {
				String[] string = q.getScore().split("`");
				Integer[] arr = new Integer[string.length];
				for (int i = 0; i < string.length; i++) {
					arr[i] = Integer.parseInt(string[i]);
				}
				scoreList.add(arr);
			}
		}

		// 试卷总分
		int totalScore = 0;
		for (int i = 0; i < scoreList.size(); i++) {
			int max = 0;
			for (int j = 0; j < scoreList.get(i).length; j++) {
				if (scoreList.get(i)[j] > max) {
					max = scoreList.get(i)[j];
				}
			}
			totalScore += max;
		}

		// 满意度Array
		Map<String, Integer> satisfactionMap = getSatisfactionArray(teamList, scoreList, totalScore);
		for (int i = 0; i < teamList.size(); i++) {
			teamNums[i] = (teamList.get(i).getNumber());
			AnswerList.addAll(teamList.get(i).getAnswers());
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put(teamList.get(i).getName(), teamList.get(i).getId());
			teamNameMap.add(map);

		}

		// 每个班的答卷列表
		ArrayList<ArrayList<Answer>> teamAnswers = getTeamAnswers(teamList, AnswerList);

		// 单选题目列表
		ArrayList<String> radioTitles = new ArrayList<String>();
		for (Question q : questionList) {
			if (q.getType() == 0) {
				radioTitles.add(q.getTitle().trim());
			}
		}

		reportMap.put("teamNameMap", teamNameMap);
		reportMap.put("teamNums", teamNums);
		reportMap.put("satisfactionMap", satisfactionMap);
		reportMap.put("teamAnswers", teamAnswers);
		reportMap.put("scoreList", scoreList);
		reportMap.put("totalScore", totalScore);
		reportMap.put("radioTitles", radioTitles);
		reportMap.put("teamList", teamList);
		return reportMap;
	}

	/**
	 * 返回所有参与班级
	 * 
	 * @param questionnaireId
	 * @return teamList
	 */
	public ArrayList<Team> getTeamList(int questionnaireId) {
		// TODO Auto-generated method stub
		return TeamDao.getTeamList(questionnaireId);
	}

	/**
	 * 计算各班的满意度
	 * 
	 * @param teamList     班级列表
	 * @param questionList 问题列表
	 * @return satisfactionArray 各班满意度的数组 放大100倍的结果
	 */
	public Map<String, Integer> getSatisfactionArray(ArrayList<Team> teamList, ArrayList<Integer[]> scoreList,
			int totalScore) {
		Map<String, Integer> satisfactionMap = new HashMap<String, Integer>();

		// 每个班的满意度
		for (int item = 0; item < teamList.size(); item++) {
			// 每个班的总分
			int score = 0;
			for (Answer a : teamList.get(item).getAnswers()) {
				if (a.getRadio().length() == 0) {

					System.out.println("kong");
					continue;
				}
				// 题
				String[] string = a.getRadio().split("`");

				// 每个题选的选项
				int[] option = new int[string.length];
				for (int i = 0; i < string.length; i++) {
					String[] strings = string[i].split("\\^");

					option[i] = Integer.parseInt(strings[1]);
				}

				// 计算每张答卷总分
				for (int j = 0; j < option.length; j++) {
					score += scoreList.get(j)[option[j]];
				}
			}
			// 班级总分数
			int totalTeamScore = totalScore * teamList.get(item).getAnswers().size();

			System.out.println("班级总分" + totalTeamScore);
			System.out.println(score);
			int total_score = 0;
			if (totalTeamScore > 0) {
				total_score = (int) score * 100 / totalTeamScore;
			}
			satisfactionMap.put(teamList.get(item).getName(), total_score);
		}
		return satisfactionMap;
	}

	/**
	 * 分出各班的答卷
	 * 
	 * @param teamList
	 * @param AnswerList
	 * @return
	 */
	public ArrayList<ArrayList<Answer>> getTeamAnswers(ArrayList<Team> teamList, ArrayList<Answer> AnswerList) {
		ArrayList<ArrayList<Answer>> teamAnswers = new ArrayList<ArrayList<Answer>>();
		for (Team t : teamList) {
			ArrayList<Answer> answers = (ArrayList<Answer>) t.getAnswers();
			teamAnswers.add(answers);

		}
		return teamAnswers;
	}
}
