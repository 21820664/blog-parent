package com.hsxy.blog.common.aop;

import java.lang.annotation.*;

/**
 * @author WU
 * @Description 日志注解
 * @Param
 * @return
 */
@Target(ElementType.METHOD)//ElementType.TYPE代表可以放在类上面,  method代表可以放在方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
	String module() default "";
	
	String operation() default "";
}
