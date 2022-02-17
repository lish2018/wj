package com.csxh.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csxh.util.plantask.QuestionnaireExpired;

@Controller
@RequestMapping("test")
public class TestController {
	
	@Resource
	private QuestionnaireExpired questionnaireExpired;
	
	@GetMapping(value="luanmatest", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String luanmatest() {
		return "11牛啊";
	}
}
