package com.csxh.controller;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.csxh.entity.Admin;
import com.csxh.service.AdminPageService;

@Controller
@RequestMapping("admin")
public class AdminPageController {
	
	@Autowired
	AdminPageService adminPageService;
 
	@RequestMapping(value = "page/get", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String get(Integer pageNum, String adminName, Integer pageCount) {

		Admin admin = SecurityUtils.getSubject().getPrincipals().oneByType(Admin.class);
		
		Map<String, Object> objs = adminPageService.showAdminsByPage(pageNum, adminName, pageCount, admin.getAdminname());
	 
		return JSON.toJSONString(objs);
	}
}
