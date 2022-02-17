package com.csxh.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义新华日志的注解
 * @author liliya
 *
 */
@Target(ElementType.METHOD) //该注解只能写在方法上
@Retention(RetentionPolicy.RUNTIME) //注解在运行时执行
@Documented //生成文档
public @interface XHLog {
	String value() default "";
	
	//操作的类型 这个是整数类型，但是这里只能写String,感觉用枚举更好，到时候再改吧
	String type();
}
