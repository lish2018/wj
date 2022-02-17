package com.csxh.config;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.csxh.annotation.XHLog;
import com.csxh.entity.Admin;
import com.csxh.entity.Log;
import com.csxh.service.AdminService;


@Aspect //定义一个切面
@Component
public class XHLogAspect {
	@Autowired
	private AdminService logService;
	
	//直接注入，就可以轻易拿到ip地址
	@Autowired
	private HttpServletRequest request;
	
	
	//定义切点
	@Pointcut("@annotation(com.csxh.annotation.XHLog)")
	public void logPointCut(){
		
	}
	
	/**
	 * 切面配置通知
	 */
	@AfterReturning("logPointCut()")
	public void saveXHLog(JoinPoint joinPoint){
		
		//保存日志对象
		Log log = new Log();
		
		//获取切入点所在的方法
		MethodSignature sinSignature = (MethodSignature) joinPoint.getSignature();
		Method method =  sinSignature.getMethod();
		
		String className =  joinPoint.getTarget().getClass().getName();
		String methodName = method.getName(); 
		
		//假装日志 有时间类名和方法名
		String logString = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
		System.out.println(logString+"=>>>"+className+">>>>"+methodName);
		
		XHLog xhLog = method.getAnnotation(XHLog.class);
	      	
		
		//本次操作干了啥，类型是什么
		if (xhLog!=null) {
			String value = xhLog.value();
			String type =  xhLog.type();
			log.setComment(value);
			log.setType(Integer.valueOf(type));
		}
		
		
		//通过Shiro来获取用户名
		String adminname = SecurityUtils.getSubject().getPrincipal().toString();
		Admin admin = logService.findAdminByAdminname(adminname);
		log.setAdmin(admin);
		
		//获取管理员ip地址
		log.setIp(request.getRemoteAddr());
		
		//存起来
		logService.insertAdminLog(log);
		
	}
}
