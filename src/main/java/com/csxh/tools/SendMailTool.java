package com.csxh.tools;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * @author Lish
 *
 */
public class SendMailTool {

	/*
	 * public static void main(String[] args) { SendMailTool sendMailTool = new
	 * SendMailTool(); POITool poiTool = new POITool(); String file =
	 * poiTool.creatExcel(); System.out.println(file);
	 * System.out.println(sendMailTool.QQSend("1347737228@qq.com", "测试", file,
	 * file)); poiTool.delExcel(file); }
	 */

	/**
	 * 通过qq邮箱发送邮件
	 * 
	 * @param email     收件人邮箱
	 * @param title     邮件标题
	 * @param excelName 文件名
	 * @param excelPath 文件路径
	 * @return true 发送成功 false 发送失败
	 */
	public boolean QQSend(String email, String title, String excelName, String excelPath) {
		try {
			// 创建一个配置文件保存并读取信息
			Properties properties = new Properties();
			// 设置qq邮件服务器
			properties.setProperty("mail.host", "smtp.qq.com");
			// 设置发送的协议
			properties.setProperty("mail.transport.protocol", "smtp");
			// 设置用户是否需要验证
			properties.setProperty("mail.smtp.auth", "true");
			// 设置SSL加密
			MailSSLSocketFactory sf;
			sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			properties.put("mail.smtp.ssl.enable", "true");
			properties.put("mail.smtp.ssl.socketFactory", sf);
			// 1.创建一个session会话对象
			Session session = Session.getDefaultInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("lish.email@foxmail.com", "semqqcnkxlrfheag");
				}
			});
			// 通过session开启Dubug模式，查看所有的过程
			session.setDebug(true);
			// 2.获取连接对象，通过session对象获得Transport
			Transport tp = session.getTransport();

			// 3.连接服务器,需要抛出异常；
			tp.connect("smtp.qq.com", "lish.email@foxmail.com", "semqqcnkxlrfheag");

			// 4.连接上之后我们需要发送邮件；
			MimeMessage mimeMessage = sendMail(session, email, title, excelName, excelPath);

			// 5.发送邮件
			tp.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

			// 6.关闭连接
			tp.close();
			return true;
		} catch (GeneralSecurityException | NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * @param session   邮件会话对象
	 * @param recipient 收件人地址
	 * @param title     邮件标题
	 * @param excelName 文件名
	 * @param excelPath 文件路径
	 * @return MimeMessage 会话消息
	 */
	public MimeMessage sendMail(Session session, String recipient, String title, String excelName, String excelPath) {
		MimeMessage mimeMessage = new MimeMessage(session);

		try {
			// 发件人
			mimeMessage.setFrom(new InternetAddress("lish.email@foxmail.com"));
			// 收件人
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			// 邮件主题
			mimeMessage.setSubject(title);

			// 正文
			Date date = new Date();
			String time = "" + (date.getYear() + 1900) + "年" + (date.getMonth() + 1) + "月" + date.getDate() + "日"
					+ date.getHours() + "时" + date.getMinutes() + "分";
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setContent("这是截止到" + time + "的" + title, "text/html;charset=utf-8");

			// excel附件
			MimeBodyPart excelBodyPart = new MimeBodyPart();
			excelBodyPart.setDataHandler(new DataHandler(new FileDataSource(excelPath)));
			System.out.println(excelName);
			excelBodyPart.setFileName(excelName + ".xls");

			// 邮件内容
			MimeMultipart email = new MimeMultipart();
			email.addBodyPart(excelBodyPart);
			email.addBodyPart(textBodyPart);

			// 邮件放入会话并保存
			mimeMessage.setContent(email);
			mimeMessage.saveChanges();

		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mimeMessage;
	}
}
