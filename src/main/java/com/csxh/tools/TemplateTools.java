package com.csxh.tools;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csxh.entity.Template;
import com.csxh.entity.TemplateQuestion;

/**
 * TemplateQuestion问卷工具类
 * @author xww
 *
 */
public class TemplateTools {
	
	
	/**
	 * -生成TemplateQuestion数据集合
	 * @param arr
	 * @return
	 */
	public static List<TemplateQuestion> toList(JSONArray arr,HttpSession session,Template qusetionnaire) {
		ArrayList<TemplateQuestion> questionList = new ArrayList<TemplateQuestion>();
		Template questionnaire = (Template)session.getAttribute("templateData");
		for(Object o:arr) {
			TemplateQuestion q = JSON.toJavaObject((JSONObject)o, TemplateQuestion.class);
			// 总问卷
			q.setTemplate(questionnaire);
			// 问卷题目遍历
			if(q.getType() == 0 || q.getType() == 1) {
				JSONArray JSONArrayText = JSON.parseArray(q.getText());
				ArrayList<String> strArrText = new ArrayList<String>();
				for(Object str:JSONArrayText) {
					strArrText.add((String)str);
				}
				q.setText(join(strArrText,"`"));
			}
			if(q.getType() == 0) {
				// 问卷分数遍历
				JSONArray JSONArrayScore = JSON.parseArray(q.getScore());
				ArrayList<String> strArrScore = new ArrayList<String>();
				for(Object str:JSONArrayScore) {
					strArrScore.add((String)str);
				}
				q.setScore(join(strArrScore, "`"));
			}
			questionList.add(q);
		}
		
		return questionList;
		
	}

	
    /**
     * -拼接字符串
     * @param strList 字符串集合
     * @param str 拼接字符
     * @return
     */
	public static String join(List<String> strList, String str){
	    if(strList.isEmpty()){
	        return "";
	    }
	    StringBuffer sb = new StringBuffer();
	    
	    for(int i=0; i<strList.size(); i++){
	        sb.append(strList.get(i)).append(str);
	    }
	    
	    return sb.substring(0, sb.length() - str.length());
	}
	
	
	/**
	 * -问题转Json模板
	 * @param list 问卷题目集合
	 * @return
	 */
	public static List<TemplateJson> TemplateQuestionToJson(List<TemplateQuestion> list) {
		ArrayList<TemplateJson> qJsonList = new ArrayList<TemplateJson>();
		for(TemplateQuestion q:list) {
			System.out.println(q);
			TemplateJson question = new TemplateJson();
			question.setId(q.getId()); 
			if(q.getText()!=null) {
				question.setText(q.getText());
			}
			if(q.getType()!=null) {
				question.setType(q.getType());
			}
			if(q.getNumber()!=null) {
				question.setQid(q.getNumber());
			}
			if(q.getIsKey()!=null && q.getType()==2) {
				question.setIsimpt(q.getIsKey()==0?0:1);
			}
			if(q.getScore()!=null) {
				question.setScore(q.getScore());
			}
			if(q.getTitle()!=null) {
				question.setTitle(q.getTitle());
			} 
			if(q.getIsRequired()!=null) {
				question.setIsRequired(q.getIsRequired());
			}
			qJsonList.add(question);
		}
		return qJsonList;
	}
	
	/**
	 * -格式化问卷题目数据
	 * @param list
	 * @param questionnaire
	 * @return
	 */
	public static List<TemplateQuestion> getEditTemplateQuestionList(List<TemplateQuestion> list,Template questionnaire) {
		for(int i=0;i<list.size();i++) {
			list.get(i).setTemplate(questionnaire);
			list.get(i).setNumber(i);
			if(list.get(i).getType() == 0 || list.get(i).getType() == 1) {
				JSONArray JSONArrayText = JSON.parseArray(list.get(i).getText());
				ArrayList<String> strArrText = new ArrayList<String>();
				for(Object str:JSONArrayText) {
					strArrText.add((String)str);
				}
				list.get(i).setText(join(strArrText,"`"));
			}
			if(list.get(i).getType() == 0) {
				JSONArray JSONArrayScore = JSON.parseArray(list.get(i).getScore());
				ArrayList<String> strArrScore = new ArrayList<String>();
				for(Object str:JSONArrayScore) {
					strArrScore.add((String)str);
				}
				list.get(i).setScore(join(strArrScore, "`"));
			}
		}
		return list;
	}
	
}
