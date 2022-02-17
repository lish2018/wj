package com.csxh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * -404自定义页面
 * @author Administrator
 *
 */
@Controller
@RequestMapping("*")
public class To404Controller {
	@RequestMapping("/**")
	public String to404() {
		
		return "404";
	}
	@RequestMapping("/unauthorized")
	public String toUnauthorized() {
		return "unauthorized";
	}

}
