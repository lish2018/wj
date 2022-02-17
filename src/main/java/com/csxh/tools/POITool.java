package com.csxh.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import com.alibaba.fastjson.JSON;
import com.csxh.entity.Answer;
import com.csxh.entity.Question;
import com.csxh.entity.Team;

/**
 * @author Lish
 *
 */
public class POITool {

	/**
	 * -生成excel报表
	 * 
	 * @param map
	 * @param name
	 * @return excel的临时地址
	 */
	public String creatExcel(Map<String, Object> map, String name, String path) {
		File file = new File(path + "\\" + name + "问卷分析报告.xls");
		HSSFWorkbook wb = null;
		// 班级List
		ArrayList<Team> teamList = (ArrayList<Team>) map.get("teamList");
		// 每个班的答卷列表
		ArrayList<ArrayList<Answer>> teamAnswers = (ArrayList<ArrayList<Answer>>) map.get("teamAnswers");
		// 满意度Array
		Map<String, Integer> satisfactionMap = (Map<String, Integer>) map.get("satisfactionMap");
		// 问题列表
		ArrayList<Question> questionList = (ArrayList<Question>) map.get("questionList");

		// 单选标题列表
		ArrayList<String> radioTitles = (ArrayList<String>) map.get("radioTitles");
		System.err.println(radioTitles.toString());
		ArrayList<Integer[]> scoreList = (ArrayList<Integer[]>) map.get("scoreList");
		System.err.println(JSON.toJSONString(scoreList));
		// 得分低的标题集
		ArrayList<ArrayList<ArrayList<String>>> radioArrayList = new ArrayList<ArrayList<ArrayList<String>>>();
		// 班
		for (int i = 0; i < teamAnswers.size(); i++) {
			ArrayList<ArrayList<String>> radioArrayList2 = new ArrayList<ArrayList<String>>();
			// 人
			for (int j = 0; j < teamAnswers.get(i).size(); j++) {
				ArrayList<String> raArrayList = new ArrayList<String>();
				if (teamAnswers.get(i).get(j).getRadio().indexOf("^") > 0) {
					String[] radioStrings = teamAnswers.get(i).get(j).getRadio().split("`");
					int[] options = new int[radioStrings.length];
					for (int k = 0; k < options.length; k++) {
						String[] radioStrings2 = radioStrings[k].split("\\^");
						options[k] = Integer.parseInt(radioStrings2[1]);
					}
					System.out.println(JSON.toJSONString(options));
					int max = 0;
					int sc = 0;
					for (int k = 0; k < options.length; k++) {
						for (int l = 0; l < scoreList.get(k).length; l++) {
							max = max > scoreList.get(k)[l] ? max : scoreList.get(k)[l];
						}
						sc = scoreList.get(k)[options[k]];
						if (max / 2 > sc) {
							raArrayList.add(radioTitles.get(k));
						}
					}
				}
				radioArrayList2.add(raArrayList);
			}
			radioArrayList.add(radioArrayList2);
		}
		System.err.println(radioArrayList.toString());
		// 每个人的打分
		ArrayList<ArrayList<Integer>> scores = new ArrayList<ArrayList<Integer>>();
		for (int item = 0; item < teamList.size(); item++) {
			ArrayList<Integer> scArrayList = new ArrayList<Integer>();
			for (int i = 0; i < teamAnswers.get(item).size(); i++) {
				System.out.println(teamAnswers.get(item).get(i).getScore());
				scArrayList.add(teamAnswers.get(item).get(i).getScore());
			}
			scores.add(scArrayList);
		} // #for

		// 生成excel
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			wb = new HSSFWorkbook();
			HSSFCellStyle style = wb.createCellStyle();
			HSSFCellStyle style1 = wb.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			style.setVerticalAlignment(VerticalAlignment.CENTER);
			style1.setWrapText(true);
			style1.setAlignment(HorizontalAlignment.CENTER);
			style1.setVerticalAlignment(VerticalAlignment.CENTER);
			HSSFSheet s1 = wb.createSheet("分析报告总表");
			HSSFRow root = s1.createRow(0);
			root.createCell(0).setCellValue("序号");
			root.createCell(1).setCellValue("班级名");
			root.createCell(2).setCellValue("总人数");
			root.createCell(3).setCellValue("完成人数");
			root.createCell(4).setCellValue("满意度");
			s1.setDefaultColumnStyle(0, style);
			s1.setDefaultColumnStyle(1, style);
			s1.setDefaultColumnStyle(2, style);
			s1.setDefaultColumnStyle(3, style);
			s1.setDefaultColumnStyle(4, style);
			for (int i = 0; i < teamList.size(); i++) {
				HSSFRow row1 = s1.createRow(i + 1);
				row1.createCell(0).setCellValue(i + 1);
				row1.createCell(1).setCellValue(teamList.get(i).getName());
				row1.createCell(2).setCellValue(teamList.get(i).getNumber());
				row1.createCell(3).setCellValue(teamAnswers.get(i).size());
				row1.createCell(4).setCellValue(satisfactionMap.get(teamList.get(i).getName()));
				HSSFSheet hssfSheet = wb.createSheet(teamList.get(i).getName() + "班答题明细");
				HSSFRow rootRow = hssfSheet.createRow(0);
				hssfSheet.setColumnWidth(2, 3840);
				hssfSheet.setColumnWidth(3, 4608);
				hssfSheet.setColumnWidth(5, 9150);
				hssfSheet.setColumnWidth(6, 9150);
				hssfSheet.setDefaultColumnStyle(0, style);
				hssfSheet.setDefaultColumnStyle(1, style);
				hssfSheet.setDefaultColumnStyle(2, style);
				hssfSheet.setDefaultColumnStyle(3, style);
				hssfSheet.setDefaultColumnStyle(4, style);
				hssfSheet.setDefaultColumnStyle(5, style1);
				hssfSheet.setDefaultColumnStyle(6, style1);
				rootRow.createCell(0).setCellValue("序号");
				rootRow.createCell(1).setCellValue("班级");
				rootRow.createCell(2).setCellValue("IP");
				rootRow.createCell(3).setCellValue("提交时间");
				rootRow.createCell(4).setCellValue("分数");
				rootRow.createCell(5).setCellValue("得分较低的题目");
				rootRow.createCell(6).setCellValue("建议");
				System.out.println(JSON.toJSON(scores));
				for (int j = 0; j < teamAnswers.get(i).size(); j++) {
					HSSFRow row2 = hssfSheet.createRow(j + 1);
					row2.createCell(0).setCellValue(j + 1);
					row2.createCell(1).setCellValue(teamList.get(i).getName());
					row2.createCell(2).setCellValue(teamAnswers.get(i).get(j).getIp());

					// 提交时间处理
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String rString = sdf.format(teamAnswers.get(i).get(j).getCommitTime());
					row2.createCell(3).setCellValue(rString);

					row2.createCell(4).setCellValue(scores.get(i).get(j));
					row2.createCell(5).setCellValue(radioArrayList.get(i).get(j).toString().substring(1,radioArrayList.get(i).get(j).toString().length()-1));

					// 建议字段处理
					String key = teamAnswers.get(i).get(j).getKey();
					if (teamAnswers.get(i).get(j).getText().indexOf("^") > 0) {
						System.out.println(teamAnswers.get(i).get(j).getText().indexOf("^"));
						String[] t = teamAnswers.get(i).get(j).getText().split("`");
						String[] e = new String[t.length];
						for (int k = 0; k < t.length; k++) {
							String[] x = (t[k].split("\\^"));
							e[k] = (x[1]);
						}

						for (int k = 0; k < e.length; k++) {
							key += e[k] + "\n";
						}
					}

					row2.createCell(6).setCellValue(key);
				}
			}
			wb.write(new FileOutputStream(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (wb != null)
					wb.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("报表生成成功");
		return file.toString();
	}

	/**
	 * 删除excel临时文件
	 * 
	 * @param filePath
	 */
	public void delExcel(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
			System.out.println("文件删除成功");
		}
	}
}
