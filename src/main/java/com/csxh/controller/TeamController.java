package com.csxh.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.csxh.entity.Admin;
import com.csxh.entity.Questionnaire;
import com.csxh.service.QuestionService;
import com.csxh.service.TeamService;
import com.csxh.tools.POITool;
import com.csxh.tools.SendMailTool;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ResponseHeader;
import springfox.documentation.spring.web.json.Json;

/**
 * @author Lish
 *
 */
@Controller
@RequestMapping("report")
@Api(value = "/report", tags = "report接口")
public class TeamController {
	@Autowired
	private TeamService TeamService;
	@Autowired
	private QuestionService QuestionService;

	/**
	 * -返回报表页面的所有数据
	 * 
	 * @param questionnaireId
	 * @param session
	 * @return 报表页面
	 */
	@RequiresPermissions("ques:select")//查看问卷数据权限
	@ApiOperation(value = "问卷数据", notes = "问卷数据", httpMethod = "get")
	@GetMapping("report")
	public String getReport(int questionnaireId, HttpSession session) {
		// 存问卷id
		session.setAttribute("questionnaireId", questionnaireId);

		// 存报告信息
		Map<String, Object> reportMap = TeamService.getTeamMap(questionnaireId);
		System.out.println(JSON.toJSON(reportMap));
		session.setAttribute("reportMap", reportMap);

		// 存admin邮箱
		Admin admin = (Admin) session.getAttribute("admin");
		String email = admin.getEmail();
		System.out.println("email:" + email);
		session.setAttribute("email", email);

		return "report";
	}

	/**
	 * -发送邮件
	 * 
	 * @param questionnaireId
	 * @param email
	 * @param request
	 */
	@ResponseBody
	@RequiresPermissions("email:send")//发邮件权限
	@ApiOperation(value = "发送邮件", notes = "发送邮件", httpMethod = "post")
	@PostMapping("sendEmail")
	public String sendEmail(int questionnaireId, String email, HttpSession request) {
		// 状态
		String status = "false";
		POITool poiTool = new POITool();
		Questionnaire questionnaire = QuestionService.getQuestionnaireById(questionnaireId);
		String path = request.getServletContext().getRealPath("/");
		String file = poiTool.creatExcel(TeamService.getTeamMap(questionnaireId), questionnaire.getTitle(), path);
		SendMailTool sendTool = new SendMailTool();
		System.out.println(file);
		if (sendTool.QQSend(email, questionnaire.getTitle() + "分析报告", questionnaire.getTitle() + "分析报告", file)) {
			status = "true";
		} else {
			status = "false";
		}
		poiTool.delExcel(file);
		return status;
	}
} 
