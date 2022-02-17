package com.csxh.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.csxh.entity.Admin;

public interface AdminPageDao{
	/**
	 * 	使用注解方式传入多个参数，管理员界面分页，通过登录管理员名字查询
	 * @param startPos 每页记录开始的位置
	 * @param pageCount 每页的记录条数
	 * @param username 管理员登录名
	 * @return admin集合
	 */
	public List<Admin> selectAdminsByPage(@Param(value="startPos") Integer startPos,@Param(value="pageCount") Integer pageCount,@Param(value="username") String username, @Param(value="myself") String myself);
	
	/**
     * 	获取页面的总记录信息，通过username来获取
     * @param username 管理员登录名
     * @return 总的记录数
     */
	public Integer getAdminsTotalCount(@Param(value="username") String username, @Param(value="myself") String myself);
}
