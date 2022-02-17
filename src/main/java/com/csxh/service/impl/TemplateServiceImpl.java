package com.csxh.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csxh.dao.TemplateDao;
import com.csxh.entity.Team;
import com.csxh.entity.Template;
import com.csxh.entity.TemplateQuestion;
import com.csxh.service.TemplateService;
import com.csxh.util.plantask.JobUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Service
public class TemplateServiceImpl implements TemplateService{
	
	@Autowired
	private TemplateDao templateDao;

	@Override
	public List<Template> getTemplateList(Map<String, Object> map) {
		return templateDao.getTemplateList(map);
	}

	@Override
	public List<TemplateQuestion> getTemplateQuestionsList(Integer templateId) {
		return templateDao.getTemplateQuestionsList(templateId);
	}
	
	@Override
	public int addTemplateQuestion(List<TemplateQuestion> templateQuestionList) {
		return templateDao.addTemplateQuestion(templateQuestionList);
	}
	
	@Override
	public int addTemplate(Template template) {
		return templateDao.addTemplate(template);
	}

	@Override
	public int deleteTemplateQuestionById(Integer id) {
		return templateDao.deleteTemplateQuestionById(id);
	}

	@Override
	public Template getTemplateById(Integer id) {
		return templateDao.getTemplateById(id);
	}

	@Override
	public int updateTemplate(Template template) {
		return templateDao.updateTemplate(template);
	}

	@Override
	public int updateTemplateQuestion(List<TemplateQuestion> templateQuestionList) {
		return templateDao.updateTemplateQuestion(templateQuestionList);
	}

	@Override
	public int getTemplateCount(Map<String, Object> map) {
		return templateDao.getTemplateCount(map);
	}

	@Override
	public List<Template> searchTemplate(String keyword) {
		return templateDao.searchTemplate(keyword);
	}

	@Override
	public int deleteOneTemplateQuestionById(Integer id) {
		return templateDao.deleteOneTemplateQuestionById(id);
	}

	@Override
	public int releaseTemplate(Integer id, HttpServletRequest request) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String url  ="http://"+JobUtil.getLocalHostAddress()+":"+request.getLocalPort()+"/wj/selectClass/"+id;


        int width = 400; // 图像宽度
        int height = 400; // 图像高度
        String format = "gif";// 图像类型
        Map<EncodeHintType, Object> hints = new HashMap<>();
        //内容编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //设置二维码边的空度，非负数
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix;
        String path  = request.getServletContext().getRealPath("/");
        
        String fname = "q.gif";
        fname = System.currentTimeMillis()+fname;
        path +="\\images\\"+fname;
		try {
			
			System.out.println("1111111111111111111"+path);
			bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);
			MatrixToImageWriter.writeToPath(bitMatrix, format, new File(path).toPath());// 输出原图片
			
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("id",id);
		map.put("url",url);
		map.put("QRurl",fname);

		return templateDao.releaseTemplate(map);
	}
	
	
	

	@Override
	public String searchIpByTemplateId(String ip, Integer templateId) {
		// TODO Auto-generated method stub
		return templateDao.searchIpByTemplateId(ip, templateId);
	}

	@Override
	public int stopTemplate(Integer id) {
		System.out.println("截止问卷");
		return templateDao.stopTemplate(id);
	}

	
	
}
