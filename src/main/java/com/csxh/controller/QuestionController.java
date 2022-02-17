package com.csxh.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
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
import com.csxh.entity.Question;
import com.csxh.entity.Questionnaire;
import com.csxh.service.impl.QuestionServiceImpl;
import com.csxh.tools.JsonTool;
import com.csxh.tools.PageTools;
import com.csxh.tools.QuestionJsonData;
import com.csxh.tools.QuestionTools;
import com.csxh.util.plantask.QuestionnaireExpired;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/admin/question")
@Api(value = "/admin/question", tags = "Question接口")
public class QuestionController {
	
	@Autowired
	private QuestionServiceImpl questionService;
	
	/**
	 * -问卷列表（首页）
	 * @param model
	 * @return
	 */
	
	@GetMapping("/list")
	@RequiresPermissions("ques:list")//查看问卷列表权限
	@ApiOperation(value = "问卷列表", notes = "问卷列表", httpMethod = "GET", response = Questionnaire.class)
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
		
		
		int pageSize = questionService.getQuestionnaireCount(map);
		
		PageTools pageTools = new PageTools(pageSize, pageNow, pageCount);
		
		map.put("startIndex", pageTools.getStartPos());
		map.put("pageCount", pageCount); 
		
		List<Questionnaire> list = questionService.getQuestionList(map);
		for(Questionnaire q:list) {
			System.out.println("问卷数据=》"+q);
		} 
		System.out.println("分页信息==》"+pageTools);
		// 问卷集合
		model.addAttribute("questionList", list);
		// 分页资料
		model.addAttribute("pageObj", pageTools);
		return "wjList"; 
	}
	
	
//	@ResponseBody
//	@GetMapping(value = "/cs",produces = "text/plain;charset=utf-8")
//	public String ceshi(Integer pageNow,Integer pageCount) {
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		int pageSize = questionService.getQuestionnaireCount(null);
//		PageTools pageTools = null;
//		if(pageNow !=null && pageCount != null) {
//			pageTools = new PageTools(pageSize, pageNow, pageCount);
//			map.put("startIndex", pageTools.getStartPos());
//			map.put("pageCount",pageCount); 
//		}
//		if(pageNow ==null && pageCount == null) {
//			map.put("startIndex", 1);
//			map.put("pageCount",pageSize); 
//			pageTools = new PageTools(pageSize, 1, pageSize);
//		}
//		List<Questionnaire> list = questionService.getQuestionList(map);
//	
//		return JSONObject.toJSONString(pageTools);
//	} 
	
	/**
	 * -问卷跳转到添加题目
	 * @param questionnaire 问卷信息
	 * @param session HttpSession
	 * @return
	 */
	@ResponseBody
	@PostMapping(value="/toEdit", produces = "text/plain;charset=utf-8")
	@ApiOperation(value = "问卷信息存入Session", notes = "问卷信息存入Session", httpMethod = "POST", response = Questionnaire.class)
	public String addQuestion(String title,Integer type,Long createTime,HttpSession session) {
		Questionnaire questionnaire = new Questionnaire();
		// 存入截止时间和发布时间
		questionnaire.setType(type);
		questionnaire.setTitle(title);
		questionnaire.setCreateTime(new Date());
		questionnaire.setEndTime(new Date(createTime));
		
		// 修改计划任务...
		//System.out.println("修改截止计划:id:" + questionnaire.getId() + ", date:" + questionnaire.getEndTime());
		//questionnaireExpired.modifyPlan(questionnaire.getId(), questionnaire.getEndTime());
		
		/* questionnaire.setEndTime(new Date()); */


		Admin admin = SecurityUtils.getSubject().getPrincipals().oneByType(Admin.class);
		System.out.println(admin.getAdminname()+ "\t" + admin.getId());
		questionnaire.setAdmin(admin);
		questionnaire.setStatus(0);
		System.out.println("questionnaire信息=>："+questionnaire);
		System.out.println("login=>"+(Admin)session.getAttribute("admin"));
		// 将问卷信息存入session
		session.setAttribute("questionnaireData", questionnaire);
		return "success";
	}
	
	
	/**
	 * -问卷题目新增
	 * @param session HttpSession
	 * @return
	 */
	@GetMapping("/edit")
	@RequiresPermissions("ques:create")//创建问卷权限
	public String edit(HttpSession session) {
		Questionnaire questionnaireData = (Questionnaire)session.getAttribute("questionnaireData");
		if(questionnaireData==null) { // 没有创建问卷则返回列表
			return "redirect:list";
		} 
		return "edit";
	}
	
	
	/**
	 * -添加问卷以及问卷题目信息
	 * @param json 问卷JSON数据
	 * @param session 
	 * @return
	 */
	@ResponseBody
	@PostMapping(value="/addQuestions", produces = "text/plain;charset=utf-8")
	public String addAllQuestion(String json,HttpSession session) {
		JSONObject parseObject = JSONObject.parseObject(json);
		// Questionnaire类
		Questionnaire questions = JSON.toJavaObject(parseObject, Questionnaire.class);
		// type  count  sessionQ=合并后的数据 
		Questionnaire sessionQ = (Questionnaire)session.getAttribute("questionnaireData");
		sessionQ.setCount(0);
		System.out.println(sessionQ);
		
		// 新增问卷
		int questionnaireRes = questionService.addQuestionnaire(sessionQ);
		JSONArray subject = parseObject.getJSONArray("subject");
		
		// 开始计划任务...
		System.out.println("截止计划已开始:id:" + sessionQ.getId() + ",date:" + sessionQ.getEndTime());
		questionnaireExpired.expiredPlan(sessionQ.getId(), sessionQ.getEndTime(), questionService);
		
		// 问卷问题列表
		List<Question> questionList = QuestionTools.toList(subject,session,sessionQ);
		System.out.println(questionList);
		int questionRes = questionService.addQuestion(questionList);
		if(questionnaireRes>=1 && questionRes>=1) {
			return JSONObject.toJSONString(JsonTool.buildSuccess(null, "添加成功"));
		} else {
			return JSONObject.toJSONString(JsonTool.buildError("添加失败"));
		}
	} 
	 
 
	/**
	 * -根据ID删除问卷及问卷的题目
	 * @param id 问卷ID
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/deleteQuestion/{id}", produces = "text/plain;charset=utf-8")
	@RequiresPermissions("ques:delete")//删除问卷权限
	public String deleteById(@PathVariable("id") Integer id) {
		System.out.println("删除权限进来了没？");
		int res = questionService.deleteQuestionById(id);
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
	@PostMapping(value = "/editQuestion",produces = "text/plain;charset=utf-8")
	
	public String editQuestion(Integer id,Model model) {
		// 根据ID查找问卷
		Questionnaire q = questionService.getQuestionnaireById(id);
		// 根据ID查找题目
		List<Question> list = questionService.getQuestionsList(id);
		// 问卷Json模板
		QuestionJsonData jsonObject = new QuestionJsonData();
		jsonObject.setSubject(QuestionTools.QuestionToJson(list));
		jsonObject.setTitle(q.getTitle());
		jsonObject.setCount(0);
		jsonObject.setType(q.getType());
		return JSONObject.toJSONString(jsonObject);
	}
	
	/**
	 * -获取对应问卷下的题目
	 * @param id 问卷ID
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/toEditQuestion/{id}",produces = "text/plain;charset=utf-8")
	@RequiresPermissions("ques:update")//编辑问卷权限
	public String toEdit(@PathVariable("id") Integer id,Model model) {
		Questionnaire questionnaire = questionService.getQuestionnaireById(id);
		model.addAttribute("questionnaire", questionnaire);
		return "questionEdit";
	}
	
	/**
	 * -编辑问卷的题目
	 * @param json json数据
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/editQuestions",produces = "text/plain;charset=utf-8")
	public String editQuestions(String json) {
		JSONObject questionnaire = JSONObject.parseObject(json);
		Questionnaire questions = JSON.toJavaObject(questionnaire, Questionnaire.class);
		int updateQuestionnaireRes = questionService.updateQuestionnaire(questions);
		
		JSONArray subject = questionnaire.getJSONArray("subject");
		ArrayList<Question> list = new ArrayList<Question>();
		for(Object o :subject) {
			Question q = JSON.toJavaObject((JSONObject)o, Question.class);
			list.add(q);
		}
		List<Question> lists = QuestionTools.getEditQuestionList(list, questions);
		// 问卷问题列表
		int updateQuestionRes = questionService.updateQuestion(list);
		// 需要添加的问题
		ArrayList<Question> addList = new ArrayList<Question>();
		for(Question q:lists) {
			if(q.getId()==null) {
				addList.add(q);
			}
		}
		// 添加新数据
		int addQuestionRes = 0;
		if(!addList.isEmpty()) {
			addQuestionRes = questionService.addQuestion(addList);
		}
		if(addQuestionRes>=0 && updateQuestionnaireRes>=0 && updateQuestionRes >=0) {
			return JSONObject.toJSONString(JsonTool.buildSuccess(null, "修改成功"));
		} 
		
		return JSONObject.toJSONString(JsonTool.buildError("修改失败"));
	}
	
	
	/** 
	 * -发布问卷生成url和二维码
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("ques:release")//发布问卷权限
	@PostMapping(value = "/releaseQuestionnaire",produces = "text/plain;charset=utf-8")
	public String releaseQuestionnaire(Integer id, HttpServletRequest request) {
		int res = questionService.releaseQuestionnaire(id, request);
		if(res>=1) {
			return JSONObject.toJSONString(JsonTool.buildSuccess(null, "发布成功"));
		}else {
			return JSONObject.toJSONString(JsonTool.buildError("发布失败"));
		}  
	}
	
	
	/**
	 * -进入答题页面
	 * @param id
	 * @return
	 */
	@ResponseBody
	@GetMapping(value = "/toAnswer/{id}",produces = "text/plain;charset=utf-8")
	public String  toAnswer(@PathVariable("id") Integer id) {
		
		
		return "";
	}
	
	/**
	 * -删除一条题目数据
	 * @param id
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/deleteOneQuestion",produces = "text/plain;charset=utf-8")
	public String deleteQuestionById(Integer id) {
		int res = questionService.deleteOneQuestionById(id);
		if(res>=1) {
			return JSONObject.toJSONString(JsonTool.buildSuccess(null, "删除成功"));
		}else {
			return JSONObject.toJSONString(JsonTool.buildError("删除失败"));
		}
	}
	
	
	/**
	 * 
	 * @param ip 从前端传过来的ip地址
	 * @param questionnaireId 这个是从前端传过来的班级id
	 * @return 返回一个json对象，告诉页面到底是可以进去下一个页面还是不可以。
	 */
	@ResponseBody
	@PostMapping(value = "/ipsearch",produces = "text/plain;charset=utf-8")
	public String searchIpByQuestionnaireId(String ip,Integer questionnaireId) {
		String string = questionService.searchIpByQuestionnaireId(ip, questionnaireId);
		
		System.out.println(ip + " " + questionnaireId);
		
		JSONObject result = new JSONObject();
		if (string.equals("0")) {
			result.put("status", "yes");
		}else {
			result.put("status", "no");
		}
		 
		return result.toJSONString();
	}
	
	/**
	 * -手动截止问卷
	 * @param id
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/stopQuestionniare",produces = "text/plain;charset=utf-8")
	public String stopQuestionniare(Integer id ) {
		int res = questionService.stopQuestionniare(id);
		if(res>=1) {
			return JSONObject.toJSONString(JsonTool.buildSuccess(null, "截止成功"));
		}else {
			return JSONObject.toJSONString(JsonTool.buildError("截止失败"));
		}
	} 
	 
	/**
	 * -获取问卷的答卷数量
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/getAnswersCount" ,produces = "text/plain;charset=utf-8")
	public String getAnswersCount(Integer id) {
		int res = questionService.getAnswersCount(id);
		questionService.updateQuestionniareCount(res, id);
		return JSONObject.toJSONString(JsonTool.buildSuccess(res, "刷新成功"));
	}
	
	

	//问卷截止计划任务工具类
	@Resource
	private QuestionnaireExpired questionnaireExpired;
	
	

	/**
	 * -获取对应模板下的题目
	 * @param id 问卷ID
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/toCreateByTemplate",produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String toEdit(Integer id, Integer type,String title,Long createTime,Model model,HttpSession session) {
		

		Questionnaire questionnaire = new Questionnaire();
		// 存入截止时间和发布时间
		questionnaire.setType(type);
		questionnaire.setTitle(title);
		questionnaire.setCreateTime(new Date());
		questionnaire.setEndTime(new Date(createTime));

		Admin admin = SecurityUtils.getSubject().getPrincipals().oneByType(Admin.class);
		questionnaire.setAdmin(admin);
		questionnaire.setStatus(0);
		System.out.println("questionnaire信息=>："+questionnaire);
		System.out.println("login=>"+(Admin)session.getAttribute("admin"));
		// 将问卷信息存入session
		session.setAttribute("questionnaireData", questionnaire);
		session.setAttribute("templateid", id);
		return "success";
	}
	
	/**
	 * -导出为问卷
	 * @param session HttpSession
	 * @return
	 */
	@GetMapping("/createByTemplate")
	@RequiresPermissions("template:export")//导出为问卷权限
	public String createByTemplate(HttpSession session) {
		Questionnaire questionnaireData = (Questionnaire)session.getAttribute("questionnaireData");
		if(questionnaireData==null) { // 没有创建问卷则返回列表
			return "redirect:list";
		} 
		return "tqEdit";
	}
	
}






