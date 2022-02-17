package com.csxh.service.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.csxh.dao.QuestionDao;
import com.csxh.entity.Question;
import com.csxh.entity.Questionnaire;
import com.csxh.entity.Team;
import com.csxh.service.QuestionService;
import com.csxh.util.plantask.JobUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionDao questionDao;

	@Override
	public List<Questionnaire> getQuestionList(Map<String, Object> map) {
		return questionDao.getQuestionList(map);
	}

	@Override
	public List<Question> getQuestionsList(Integer questionnaireId) {
		return questionDao.getQuestionsList(questionnaireId);
	}

	@Override
	public int addQuestion(List<Question> questionList) {
		return questionDao.addQuestion(questionList);
	}

	@Override
	public int addQuestionnaire(Questionnaire questionnaire) {
		return questionDao.addQuestionnaire(questionnaire);
	}

	@Override
	public int deleteQuestionById(Integer id) {
		return questionDao.deleteQuestionById(id);
	}

	@Override
	public Questionnaire getQuestionnaireById(Integer id) {
		return questionDao.getQuestionnaireById(id);
	}

	@Override
	public int updateQuestionnaire(Questionnaire questionnaire) {
		return questionDao.updateQuestionnaire(questionnaire);
	}

	@Override
	public int updateQuestion(List<Question> questionList) {
		return questionDao.updateQuestion(questionList);
	}

	@Override
	public int getQuestionnaireCount(Map<String, Object> map) {
		return questionDao.getQuestionnaireCount(map);
	}

	@Override
	public List<Questionnaire> searchQuestionnaire(String keyword) {
		return questionDao.searchQuestionnaire(keyword);
	}

	@Override
	public int deleteOneQuestionById(Integer id) {
		return questionDao.deleteOneQuestionById(id);
	}

	@Override
	public int releaseQuestionnaire(Integer id, HttpServletRequest request) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String url = "http://" + JobUtil.getLocalHostAddress() + ":" + request.getLocalPort() + "/wj/selectClass/" + id;

		int width = 400; // 图像宽度
		int height = 400; // 图像高度
		String format = "gif";// 图像类型
		Map<EncodeHintType, Object> hints = new HashMap<>();
		// 内容编码格式
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		// 指定纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 设置二维码边的空度，非负数
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix;
		String path = request.getServletContext().getRealPath("/");
		File logo = new File(path+"\\img\\新华logo.png");
		System.out.println(logo);
		String fname = "q.gif";
		fname = System.currentTimeMillis() + fname;
		path += "images\\" + fname;
		try {

			System.out.println(path);
			bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);
//			MatrixToImageWriter.writeToPath(bitMatrix, format, new File(path).toPath());// 输出原图片
			BufferedImage combined = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = (Graphics2D) combined.getGraphics();
			BufferedImage qrcode = toBufferedImage(bitMatrix);
			BufferedImage logoImg = ImageIO.read(logo);
			int deltaHeight = height;
			int deltaWidth = width;
			g2.drawImage(qrcode, 0, 0, null);
			g2.drawImage(logoImg, deltaWidth / 5 * 2, deltaHeight / 5 * 2, deltaWidth / 5, deltaHeight / 5, null);// 绘制
			BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			g2.setStroke(stroke);// 设置笔画对象
			// 指定弧度的圆角矩形
			RoundRectangle2D.Float round = new RoundRectangle2D.Float(deltaWidth / 5 * 2, deltaHeight / 5 * 2,
					deltaWidth / 5, deltaHeight / 5, 20, 20);
			g2.setColor(Color.white);
			g2.draw(round);// 绘制圆弧矩形
			g2.dispose();
			ImageIO.write(combined, "gif", new File(path));
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("id", id);
		map.put("url", url);
		map.put("QRurl", fname);

		return questionDao.releaseQuestionnaire(map);
	}

	@Override
	public String searchIpByQuestionnaireId(String ip, Integer questionnaireId) {
		// TODO Auto-generated method stub
		return questionDao.searchIpByQuestionnaireId(ip, questionnaireId);
	}

	@Override
	public int stopQuestionniare(Integer id) {
		System.out.println("截止问卷");
		return questionDao.stopQuestionniare(id);
	}

	@Override
	public List<Team> getTeamList() {
		// TODO Auto-generated method stub
		return questionDao.getTeamList();
	}

	@Override
	public int getAnswersCount(Integer id) {

		return questionDao.getAnswersCount(id);
	}

	@Override
	public int updateQuestionniareCount(Integer count, Integer id) {
		return questionDao.updateQuestionniareCount(count, id);
	}

	public BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}
		return image;
	}

}
