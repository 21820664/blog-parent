package com.hsxy.blog.common.aop;

import com.alibaba.fastjson.JSON;
import com.hsxy.blog.utils.HttpContextUtils;
import com.hsxy.blog.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @name LogAspect
 * @Description 日志切面
 * @author WU
 * @Date 2022/8/16 20:01
 */
@Component
@Aspect //切面 定义了通知和切点的关系
@Slf4j
public class LogAspect {
	
	/**
	 * @Description 切入点
	 * @Param []
	 * @return void
	 */
	@Pointcut("@annotation(com.hsxy.blog.common.aop.LogAnnotation)")
	public void pt(){
		//每个地方都是切入点
	}
	
	//环绕通知
	@Around("pt()")
	public Object log(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		//保存日志
		recordLog(point,time);
		return result;
		
	}
	
	private void recordLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
		log.info("=====================log start================================");
		log.info("module:{}",logAnnotation.module());
		log.info("operation:{}",logAnnotation.operation());
		
		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		log.info("request method:{}",className + "." + methodName + "()");

		//请求的参数(报错,已解决<改动后不显示>)
		Object[] args = joinPoint.getArgs();
		String params = JSON.toJSONString(args[0]);
		log.info("params:{}",params);
		/*if (Objects.nonNull(args)){//不用加
			List<Object> argsList = Arrays.asList(args);
			// 将 HttpServletResponse 和 HttpServletRequest 参数移除，不然会报异常
			List<Object> collect = argsList.stream().filter(o -> !(o instanceof HttpServletResponse || o instanceof HttpServletRequest)).collect(Collectors.toList());
			collect.toArray(args);
		}
		try {
			String params = JSON.toJSONString(args[0]);
			log.info("params:{}",params);
		} catch (Exception e) {
		}*/
		
		//获取request 设置IP地址
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		log.info("ip:{}", IpUtils.getIpAddr(request));
		
		
		log.info("execute time : {} ms",time);
		log.info("=====================log end================================");
	}
	
}
