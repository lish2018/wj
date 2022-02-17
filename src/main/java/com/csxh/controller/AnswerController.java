package com.csxh.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.csxh.entity.Answer;
import com.csxh.entity.Question;
import com.csxh.entity.Questionnaire;
import com.csxh.entity.Team;
import com.csxh.service.AnswerService;
import com.csxh.tools.PageTools;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Lish
 *
 */

@Controller
@Api(value = "", tags = "Answer接口")
public class AnswerController {
	@Autowired
	private AnswerService answerService;

	/**
	 * 拿到问卷数据，然后放到数据库
	 * 
	 * @param jsonstr  ip地址和班级或者其他数据，用json对象传递
	 * @param formData fromData数据 是一个字符串，要特殊处理
	 * @return 这里的返回结果可有和无
	 */
	@PostMapping("/json")
	@ResponseBody
	public String addAnswer(String jsonstr, String formData, HttpServletResponse response) {
		System.out.println(jsonstr);
		Answer answer = new Answer();
		// 提交时间
		Date commitTime = new Date();
		Questionnaire questionnaire = new Questionnaire();

		JSONObject jsonObject = JSON.parseObject(jsonstr);
		String banjiString = jsonObject.getString("banjiId");
		String questionnaireId = "0";
		if (jsonObject.getString("questionnaireId") != null) {
			questionnaireId = jsonObject.getString("questionnaireId");
			System.out.println(questionnaireId);
		}
		questionnaire.setId(Integer.valueOf(questionnaireId));
		// 获取班级
		Team team = new Team();
		team.setId(Integer.valueOf(banjiString));

		// 获取ip地址
		String ipAdress = jsonObject.getString("ip");

		String strData[];
		try {
			strData = URLDecoder.decode(formData, "UTF-8").split("&");
			System.out.println(strData);

			// 拼接redio字符串和checkbox字符串
			StringBuilder radioStringBuilder = new StringBuilder();
			StringBuilder checkboxStringBuilder = new StringBuilder();
			StringBuilder textStringBuilder = new StringBuilder();
			StringBuilder keyStringBuilder = new StringBuilder();
			// 判断checkbox的是不是同一个
			String headString = "";

			for (String s : strData) {
				System.out.println(s);
				// 单选题
				if (s.contains("_0")) {
					jsonStringByKeyWord(s, radioStringBuilder, "_0=");
				}

				// 如果这个是文本域
				if (s.contains("_2")) {
					jsonStringByKeyWord(s, textStringBuilder, "_2=");
				}

				// checkbox 有点不同
				if (s.contains("_1")) {
					String checkbox_string[] = s.split("_1=");

					String chackboxString = String.join("^", checkbox_string);
					// 如果是第一个题目的话，那就直接传入就好了，从第二个起就开始加 `
					if (checkboxStringBuilder.length() < 1) {
						checkboxStringBuilder.append(chackboxString);
					} else {
						if (headString.equals(checkbox_string[0])) {
							checkboxStringBuilder.append("^").append(checkbox_string[1]);
						} else {
							checkboxStringBuilder.append("`").append(chackboxString);
						}
					}
					headString = checkbox_string[0];
				}

			}

			// 单选字符串
			String radioString = radioStringBuilder.toString();
			// 分数处理
			int score = 0;
			{
				// 每个选项的分数
				ArrayList<Integer[]> scoreList = new ArrayList<Integer[]>();
				// 问题list
				ArrayList<Question> questionList = answerService
						.getQuestionsListByQuestionnaireId(Integer.parseInt(questionnaireId));
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
				if (radioString.indexOf("^") > 0) {
					// 题
					String[] string = radioString.split("`");

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

			}

			answer.setCheckbox(checkboxStringBuilder.toString());
			answer.setCommitTime(commitTime);
			answer.setIp(ipAdress);
			answer.setKey(keyStringBuilder.toString());
			answer.setRadio(radioString);
			answer.setTeam(team);
			answer.setText(textStringBuilder.toString());
			answer.setCheckbox(checkboxStringBuilder.toString());
			answer.setQuestionnaire(questionnaire);
			answer.setScore(score);
			System.out.println(answer);
			answerService.addAnswer(answer);
			System.out.println("插入数据成功！");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSON.toJSONString("success");
	}

	/**
	 * 将字符串以特殊方式处理，题目用`分隔，值用^分隔
	 * 
	 * @param s 传入的字符串
	 * @return 貌似什么都不需要返回，因为java是值传递 jsonstr:
	 *         {"id":"1111","class":"java","ip":"58.20.235.214","formData":"6_2=&7_2=&8_2="}
	 *         formData:
	 *         1_0=3&2_0=1&3_1=0&3_1=1&3_1=5&4_0=1&5_0=0&6_2=%E5%95%8A%E8%BF%99&7_2=%E5%95%8A%E8%BF%99&8_2=%E5%95%8A%E8%BF%99
	 */
	private void jsonStringByKeyWord(String s, StringBuilder sBuilder, String splitString) {
		String radio_string[] = s.split(splitString);
		String radioString = String.join("^", radio_string);
		if (sBuilder.length() < 1) {
			sBuilder.append(radioString);
		} else {
			sBuilder.append("`").append(radioString);
		}
	}

	/**
	 * 分页查询 通过班级ID和问卷ID筛选后的答卷列表
	 * 
	 * @param pageNum
	 * @param teamId
	 * @param questionnaireId
	 * @return
	 */
	@PostMapping(value = "/admin/answer/get", produces = "text/json;charset=utf-8")
	@ApiOperation(value = "分页查询通过班级ID和问卷ID筛选后的答卷列表", notes = "分页查询通过班级ID和问卷ID筛选后的答卷列表", httpMethod = "post")
	@ResponseBody
	public String getAnswerList(Integer pageNum, Integer teamId, Integer questionnaireId, String beginTime,
			String endTime, String start, String end) {

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date realbeginTime = null;
		Date realEndTime = null;
		int scoreStart = -1;
		int scoreEnd = 101;
		if (!(beginTime == null || beginTime.equals(""))) {
			try {
				realbeginTime = sdf.parse(beginTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (!(endTime == null || endTime.equals(""))) {
			try {
				realEndTime = sdf.parse(endTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (!(start == null)) {
			scoreStart = Integer.parseInt(start) - 1;
		}
		if (!(end == null)) {
			scoreEnd = Integer.parseInt(end) + 1;
		}

		System.out.println("realbeginTime:" + realbeginTime);
		System.out.println("realEndTime:" + realEndTime);

		// 页数不能低于1
		if (pageNum == null || pageNum < 1) {
			pageNum = 1;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("teamId", teamId);
		map.put("questionnaireId", questionnaireId);
		map.put("beginTime", realbeginTime);
		map.put("endTime", realEndTime);
		map.put("scoreStart", scoreStart);
		map.put("scoreEnd", scoreEnd);

		Integer totalCount = answerService.getTotalCount(map);

		PageTools page = new PageTools(totalCount, pageNum, 5);

		map.put("start", page.getStartPos());
		map.put("number", page.getPageCount());

		// System.out.println("asdasdasd");

		List<Answer> answers = answerService.listAnswerByPageAndTeamId(map);

		Map<String, Object> map2 = new HashMap<String, Object>();

		map2.put("page", page);
		map2.put("answers", answers);

		return JSON.toJSONString(map2);
	}
}
