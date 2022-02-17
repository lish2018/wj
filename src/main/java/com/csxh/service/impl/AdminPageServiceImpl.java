package com.csxh.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csxh.dao.AdminPageDao;
import com.csxh.entity.Admin;
import com.csxh.service.AdminPageService;
import com.csxh.tools.PageTools;

@Service
public class AdminPageServiceImpl implements AdminPageService{
	
	@Autowired
	AdminPageDao adminPageDao;
	/**
	 * 	@param request 用来获取前端传进来的参数
	 * 	@param model 用来向模板页面返回处理结果
	 */
	@Override
	public Map<String, Object> showAdminsByPage(Integer pageNum, String adminName, Integer pageCount, String myself) {

		if(pageNum == null || pageNum < 1) {
			pageNum = 1;
		}
		
		if(pageCount == null || pageCount < 1) {
			pageCount = 5;
		}
		
		//总的记录数
		int totalCount = (int)adminPageDao.getAdminsTotalCount(adminName, myself);
		
		if (pageNum > totalCount) {
			pageNum = totalCount;
		}
		
		PageTools page = new PageTools(totalCount, pageNum, pageCount);
		List<Admin> admins = this.adminPageDao.selectAdminsByPage(page.getStartPos(), page.getPageCount(), adminName, myself);
		
		System.out.println(admins);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("page", page);
		map.put("admins", admins);
		return map;
	}
	
}
