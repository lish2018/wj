package com.csxh.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.csxh.entity.Question;
import com.csxh.entity.QuestionOption;
import com.csxh.service.impl.QuestionServiceImpl;
import com.csxh.tools.JsonTool;
import com.csxh.util.plantask.JobUtil;

@Controller
@RequestMapping("/questions")
public class QuestionGenerateController {

	@Autowired
	private QuestionServiceImpl questionService;

	/**
	 * 该方法用于第二张问卷
	 * @param model 向前端发送数据
	 * @param id 这个是问卷的id，用于确定是填写哪张问卷
	 * @param request 这个用于拿到用户的ip地址
	 * @return
	 */
	@GetMapping("/qaq/{id}")
	public String getQuestionsListByQuestionnaireId(Model model,@PathVariable Integer id,HttpServletRequest request) {
		System.out.println("发送请求的ip地址是"+request.getRemoteAddr());
		
		//题目
		List<Question> questionsList = questionService.getQuestionsList(id);
		
		System.out.println("qlist===>" + questionsList);
		//选项
		List<List<QuestionOption>> questionOptions = new ArrayList<List<QuestionOption>>();
		//题目是否必填,里面存储的是必填的题目的name
		List<String> questionIsRequired = new ArrayList<String>();
		
		
		
		for (int i = 0; i < questionsList.size(); i++) {
			if (questionsList.get(i).getType()<2 && questionsList.get(i).getIsRequired()==0) {
				questionIsRequired.add(questionsList.get(i).getTitle()+questionsList.get(i).getNumber());
			}
//			System.out.println(questionsList.get(i));
			System.out.println(questionsList.get(i).getText());
			String optionTextString = questionsList.get(i).getText();
			ArrayList<QuestionOption> options = new ArrayList<QuestionOption>();
			if (optionTextString==null) {
				System.out.println("出现了空");
				options.add(new QuestionOption(0,""));
			}else {
				if(questionsList.get(i).getType() == 0) {
					String optString[] = questionsList.get(i).getText().split("`");
					String optScore[] = questionsList.get(i).getScore().split("`");
					for(int j = 0; j < optString.length; j++){
						options.add(new QuestionOption(j,optString[j], optScore[j]));
					}
				}else if(questionsList.get(i).getType() == 1) {
					String optString[] = questionsList.get(i).getText().split("`");
					for(int j = 0; j < optString.length; j++){
						options.add(new QuestionOption(j,optString[j]));
					}
				}else if(questionsList.get(i).getType() == 2) {
					options.add(new QuestionOption(0,""));
				}
			}
			
			
			questionOptions.add(options);
			
		}

		
		model.addAttribute("options", questionOptions);
		model.addAttribute("lists", questionsList);
		model.addAttribute("queIsRe", questionIsRequired);
		model.addAttribute("ip", request.getRemoteAddr());
		return "doQuestionnaire";
	}

}
