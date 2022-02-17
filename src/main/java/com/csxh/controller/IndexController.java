package com.csxh.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.csxh.entity.Questionnaire;
import com.csxh.entity.Team;
import com.csxh.service.impl.QuestionServiceImpl;

@Controller
public class IndexController {
	
	@Autowired
	private QuestionServiceImpl questionService;
	
	
	/**
	 * 直接跳转到登录页面
	 * @return
	 */
	@GetMapping("/index")
	public String index() {
		return "login";
	}

	/**
	 * 直接跳转到登录页面
	 * @return
	 */
	@GetMapping("/")
	public String index2() {
		return "login";
	}
	
	/**
	 * 直接跳转到选择班级页面
	 * @param id 问卷tid
	 * @param model
	 * @param request
	 * @return
	 */
	@GetMapping("selectClass/{id}")
	public String toSelectClass(@PathVariable("id")Integer id,Model model,HttpServletRequest request) {
		Questionnaire questionnaire = questionService.getQuestionnaireById(id);
		if (questionnaire==null) {
			return "404";
		}
		List<Team> teams = questionService.getTeamList();
		model.addAttribute("questionInfo", questionnaire);
		model.addAttribute("teams", teams);
		model.addAttribute("ip", request.getRemoteAddr());
		
		return "selectClass";
	}
}
