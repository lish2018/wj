package com.csxh.dao;

import java.util.List;

import com.csxh.entity.Resource;

public interface ResourceDao {
	 /**
     * 创建资源
     */
    Resource createResource(Resource resource);
    /**
     * 根据资源id更新资源信息
     */
    Resource updateResource(Resource resource);
    /**
     * 根据资源id删除资源数据
     */
    void deleteResource(Long resourceId);
    /**
     * 根据资源id查询资源数据
     */
    Resource findOne(Long resourceId);
    /**
     * 查询所有资源
     */
    List<Resource> findAll();
}
