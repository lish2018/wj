package com.csxh.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.csxh.entity.Team;
import com.csxh.entity.Template;
import com.csxh.entity.TemplateQuestion;

/**
 * -:问卷
 * @author 许文伟
 *
 */
public interface TemplateDao {
	
	
	/**
	 * -问卷问卷列表
	 * @param map 查询信息（分页，模糊搜索）
	 * @return
	 */
	public List<Template> getTemplateList(Map<String,Object> map);
	
	
	/**
	 * -模糊查询条数
	 * @param keyWord
	 * @return
	 */
	int queryCountLike(String keyWord);
	
	/**
	 * -添加指定问卷题目
	 * @param templateQuestionList 问卷集合
	 * @return
	 */
	int addTemplateQuestion(List<TemplateQuestion> templateQuestionList);
	
	
	/**
	 * -根据ID删除问卷和题目
	 * @param id 问卷ID
	 * @return
	 */
	int deleteTemplateQuestionById(Integer id);
	
	/**
	 * -添加问卷信息
	 * @param template 问卷信息
	 * @return 
	 */
	public int addTemplate(Template template);

	/**
	 * - 通过问卷的id拿到这个问卷里的所有题目
	 * @param templateId 问卷id
	 * @return templateQuestionsList 题目列表
	 */
	public ArrayList<TemplateQuestion> getTemplateQuestionsListByTemplateId(Integer templateId);
	
	/**
	 * -根据ID查找问卷
	 * @param id 问卷ID
	 * @return
	 */
	Template getTemplateById(Integer id);
	
	 
	/**
	 * -获取问卷对应的所有题目
	 * @param templateId 问卷ID
	 * @return
	 */
	public List<TemplateQuestion> getTemplateQuestionsList(Integer templateId);
	
	/**
	 * -更新问卷的数据
	 * @param template
	 * @return
	 */
	int updateTemplate(Template template);
	
	/**
	 * -更新问卷题目的数据
	 * @param templateQuestionList
	 * @return
	 */
	int updateTemplateQuestion(List<TemplateQuestion> list);
	
	
	
	/**
	 * -查询问卷的总数量
	 * @return
	 */
	Integer getTemplateCount(Map<String, Object> map);
	
	/**
	 * -搜索问卷
	 * @param keyword
	 * @return
	 */
	List<Template> searchTemplate(String keyword);
	
	
	
	/**
	 * -发布问卷生成url和二维码
	 * @param template
	 * @return
	 */
	int releaseTemplate(Map<String,Object> map);
	
	
	/**
	 * -根据ID删除一条问卷题目
	 * @param id
	 * @return
	 */
	int deleteOneTemplateQuestionById(Integer id);
	
	/**
	 * 
	 * @param ip 需要验证的ip地址
	 * @param templateId 哪张问卷
	 * @return 如果是返回 “0” 那就是未查找到ip，可以进行问卷填写
	 */
	String searchIpByTemplateId(@Param("ip")String ip, @Param("templateId")Integer templateId);
	
	/**
	 * -手动截止问卷
	 * @param id
	 * @return
	 */
	int stopTemplate(Integer id);
	
}
