package com.csxh.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.csxh.entity.Question;
import com.csxh.entity.Questionnaire;
import com.csxh.entity.Team;

import io.swagger.models.auth.In;

/**
 * -:问卷
 * @author 许文伟
 *
 */
public interface QuestionDao {
	
	
	/**
	 * -问卷问卷列表
	 * @param map 查询信息（分页，模糊搜索）
	 * @return
	 */
	public List<Questionnaire> getQuestionList(Map<String,Object> map);
	
	
	/**
	 * -模糊查询条数
	 * @param keyWord
	 * @return
	 */
	int queryCountLike(String keyWord);
	
	/**
	 * -添加指定问卷题目
	 * @param questionList 问卷集合
	 * @return
	 */
	int addQuestion(List<Question> questionList);
	
	
	/**
	 * -根据ID删除问卷和题目
	 * @param id 问卷ID
	 * @return
	 */
	int deleteQuestionById(Integer id);
	
	/**
	 * -添加问卷信息
	 * @param questionnaire 问卷信息
	 * @return 
	 */
	public int addQuestionnaire(Questionnaire questionnaire);

	/**
	 * - 通过问卷的id拿到这个问卷里的所有题目
	 * @param questionnaireId 问卷id
	 * @return questionsList 题目列表
	 */
	public ArrayList<Question> getQuestionsListByQuestionnaireId(Integer questionnaireId);
	
	/**
	 * -根据ID查找问卷
	 * @param id 问卷ID
	 * @return
	 */
	Questionnaire getQuestionnaireById(Integer id);
	
	 
	/**
	 * -获取问卷对应的所有题目
	 * @param questionnaireId 问卷ID
	 * @return
	 */
	public List<Question> getQuestionsList(Integer questionnaireId);
	
	/**
	 * -更新问卷的数据
	 * @param questionnaire
	 * @return
	 */
	int updateQuestionnaire(Questionnaire questionnaire);
	
	/**
	 * -更新问卷题目的数据
	 * @param questionList
	 * @return
	 */
	int updateQuestion(List<Question> list);
	
	
	
	/**
	 * -查询问卷的总数量
	 * @return
	 */
	int getQuestionnaireCount(Map<String, Object> map);
	
	/**
	 * -搜索问卷
	 * @param keyword
	 * @return
	 */
	List<Questionnaire> searchQuestionnaire(String keyword);
	
	
	
	/**
	 * -发布问卷生成url和二维码
	 * @param questionnaire
	 * @return
	 */
	int releaseQuestionnaire(Map<String,Object> map);
	
	
	/**
	 * -根据ID删除一条问卷题目
	 * @param id
	 * @return
	 */
	int deleteOneQuestionById(Integer id);
	
	/**
	 * 
	 * @param ip 需要验证的ip地址
	 * @param questionnaireId 哪张问卷
	 * @return 如果是返回 “0” 那就是未查找到ip，可以进行问卷填写
	 */
	String searchIpByQuestionnaireId(@Param("ip")String ip, @Param("questionnaireId")Integer questionnaireId);
	
	/**
	 * -手动截止问卷
	 * @param id
	 * @return
	 */
	int stopQuestionniare(Integer id);
	
	/**
	 * 这个是拿到班级id 和 名字
	 * @return
	 */
	List<Team> getTeamList();
	
	
	/**
	 * -获取问卷的答案数量
	 * @param id
	 * @return
	 */
	int getAnswersCount(Integer id);
	
	/**
	 * -修改问卷答案数量
	 * @param count
	 * @param id
	 * @return
	 */
	int updateQuestionniareCount(@Param("count")Integer count,@Param("id")Integer id);
	
}
