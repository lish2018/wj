package com.csxh.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csxh.entity.Admin;
import com.csxh.entity.Template;
import com.csxh.entity.TemplateQuestion;
import com.csxh.service.impl.TemplateServiceImpl;
import com.csxh.tools.JsonTool;
import com.csxh.tools.PageTools;
import com.csxh.tools.TemplateJsonData;
import com.csxh.tools.TemplateTools;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/admin/template")
@Api(value = "/admin/template", tags = "TemplateQuestion接口")
public class TemplateController {
	
	@Autowired
	private TemplateServiceImpl templateService;
	
	/**
	 * -模板列表（首页）
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	@RequiresPermissions("template:list")//查看模板列表权限
	@ApiOperation(value = "模板列表", notes = "模板列表", httpMethod = "GET", response = Template.class)
	public String wj_list(Model model,Integer pageNow,Integer pageCount, String name, Integer isOwner) {
		if(pageNow == null || pageNow < 1) {
			pageNow = 1;
		}
		
		if(pageCount == null || pageCount < 1) {
			pageCount = 5;
		}
		
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		if(isOwner != null && isOwner == 1) {
			Admin admin = SecurityUtils.getSubject().getPrincipals().oneByType(Admin.class);
			map.put("adminId", admin.getId());
		}
		
		int pageSize = templateService.getTemplateCount(map);
		
		PageTools pageTools = new PageTools(pageSize, pageNow, pageCount);
		
		map.put("startIndex", pageTools.getStartPos());
		map.put("pageCount", pageCount); 
		
		List<Template> list = templateService.getTemplateList(map);
		for(Template q:list) {
			System.out.println("模板数据=》"+q);
		} 
		System.out.println("分页信息==》"+pageTools);
		// 模板集合
		model.addAttribute("templateList", list);
		// 分页资料
		model.addAttribute("pageObj", pageTools);
		return "templateList"; 
	}
	
	
	/**
	 * -模板跳转到添加题目
	 * @param template 模板信息
	 * @param session HttpSession
	 * @return
	 */
	@ResponseBody
	@PostMapping(value="/toEdit", produces = "text/plain;charset=utf-8")
	@RequiresPermissions("template:insert")//创建模板权限
	@ApiOperation(value = "模板信息存入Session", notes = "模板信息存入Session", httpMethod = "POST", response = Template.class)
	public String addTemplateQuestion(String title,Integer type,HttpSession session) {
		Template template = new Template();
		// 存入截止时间和发布时间
		template.setType(type);
		template.setTitle(title);


		Admin admin = SecurityUtils.getSubject().getPrincipals().oneByType(Admin.class);
		System.out.println(admin.getAdminname()+ "\t" + admin.getId());
		template.setAdmin(admin);
		System.out.println("template信息=>："+template);
		System.out.println("login=>"+(Admin)session.getAttribute("admin"));
		// 将模板信息存入session
		session.setAttribute("templateData", template);
		return "success";
	}
	
	
	/**
	 * -模板题目新增
	 * @param session HttpSession
	 * @return
	 */
	@GetMapping("/edit")
	public String edit(HttpSession session) {
		Template templateData = (Template)session.getAttribute("templateData");
		if(templateData==null) { // 没有创建模板则返回列表
			return "redirect:list";
		} 
		return "templateEdit";
	}
	
	
	/**
	 * -添加模板以及模板题目信息
	 * @param json 模板JSON数据
	 * @param session 
	 * @return
	 */
	@ResponseBody
	@PostMapping(value="/addTemplateQuestions", produces = "text/plain;charset=utf-8")
	public String addAllTemplateQuestion(String json,HttpSession session) {
		JSONObject parseObject = JSONObject.parseObject(json);
		// type  count  sessionQ=合并后的数据 
		Template sessionQ = (Template)session.getAttribute("templateData");
		System.out.println(sessionQ);
		
		// 新增模板
		int templateRes = templateService.addTemplate(sessionQ);
		JSONArray subject = parseObject.getJSONArray("subject");
		
		// 模板问题列表
		List<TemplateQuestion> templateQuestionList = TemplateTools.toList(subject,session,sessionQ);
		System.out.println(templateQuestionList);
		int templateQuestionRes = templateService.addTemplateQuestion(templateQuestionList);
		if(templateRes>=1 && templateQuestionRes>=1) {
			return JSONObject.toJSONString(JsonTool.buildSuccess(null, "添加成功"));
		} else {
			return JSONObject.toJSONString(JsonTool.buildError("添加失败"));
		}
	} 
	 
 
	/**
	 * -根据ID删除模板及模板的题目
	 * @param id 模板ID
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/deleteTemplateQuestion/{id}", produces = "text/plain;charset=utf-8")
	@RequiresPermissions("template:delete")//删除模板权限
	public String deleteById(@PathVariable("id") Integer id) {
		int res = templateService.deleteTemplateQuestionById(id);
		if(res>=1) {
			return JSONObject.toJSONString(JsonTool.buildSuccess(null, "删除成功"));
		}else {
			return JSONObject.toJSONString(JsonTool.buildError("删除失败"));
		}
	}
	
	/**
	 * -编辑查询数据
	 * @param id
	 * @param model
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/editTemplateQuestion",produces = "text/plain;charset=utf-8")
	public String editTemplateQuestion(Integer id,Model model) {
		// 根据ID查找模板
		Template q = templateService.getTemplateById(id);
		// 根据ID查找题目
		List<TemplateQuestion> list = templateService.getTemplateQuestionsList(id);
		// 模板Json模板
		TemplateJsonData jsonObject = new TemplateJsonData();
		jsonObject.setSubject(TemplateTools.TemplateQuestionToJson(list));
		jsonObject.setTitle(q.getTitle());
		jsonObject.setType(q.getType());
		return JSONObject.toJSONString(jsonObject);
	}
	
	/**
	 * -获取对应模板下的题目
	 * @param id 模板ID
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/toEditTemplateQuestion/{id}",produces = "text/plain;charset=utf-8")
	@RequiresPermissions("template:update")//编辑模板权限
	public String toEdit(@PathVariable("id") Integer id,Model model) {
		Template template = templateService.getTemplateById(id);
		model.addAttribute("template", template);
		return "templateQuestionEdit";
	}
	
	/**
	 * -编辑模板的题目
	 * @param json json数据
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/editTemplateQuestions",produces = "text/plain;charset=utf-8")
	public String editTemplateQuestions(String json) {
		JSONObject template = JSONObject.parseObject(json);
		Template templateQuestions = JSON.toJavaObject(template, Template.class);
		int updateTemplateRes = templateService.updateTemplate(templateQuestions);
		
		JSONArray subject = template.getJSONArray("subject");
		ArrayList<TemplateQuestion> list = new ArrayList<TemplateQuestion>();
		for(Object o :subject) {
			TemplateQuestion q = JSON.toJavaObject((JSONObject)o, TemplateQuestion.class);
			list.add(q);
		}
		List<TemplateQuestion> lists = TemplateTools.getEditTemplateQuestionList(list, templateQuestions);
		
		
		// 模板问题列表
		int updateTemplateQuestionRes = templateService.updateTemplateQuestion(list);
		// 需要添加的问题
		ArrayList<TemplateQuestion> addList = new ArrayList<TemplateQuestion>();
		for(TemplateQuestion q:lists) {
			if(q.getId()==null) {
				addList.add(q);
			}
		}
		// 添加新数据
		int addTemplateQuestionRes = 0;
		if(!addList.isEmpty()) {
			addTemplateQuestionRes = templateService.addTemplateQuestion(addList);
		}
		if(addTemplateQuestionRes>=0 && updateTemplateRes>=0 && updateTemplateQuestionRes >=0) {
			return JSONObject.toJSONString(JsonTool.buildSuccess(null, "修改成功"));
		} 
		
		return JSONObject.toJSONString(JsonTool.buildError("修改失败"));
	}
	
	
	/**
	 * -删除一条题目数据
	 * @param id
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/deleteOneTemplateQuestion",produces = "text/plain;charset=utf-8")
	public String deleteTemplateQuestionById(Integer id) {
		int res = templateService.deleteOneTemplateQuestionById(id);
		if(res>=1) {
			return JSONObject.toJSONString(JsonTool.buildSuccess(null, "删除成功"));
		}else {
			return JSONObject.toJSONString(JsonTool.buildError("删除失败"));
		}
	}
	
	 
	
}
