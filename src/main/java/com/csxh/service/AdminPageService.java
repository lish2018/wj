package com.csxh.service;

import java.util.Map;

/**
	  * @param request 前端传过来的参数
	  *	@param model 向模板页面返回处理结果
	  *	@param username 管理员登录名
	  */

public interface AdminPageService {
	Map<String, Object> showAdminsByPage(Integer pageNum, String adminName, Integer pageCount, String myself);
}
