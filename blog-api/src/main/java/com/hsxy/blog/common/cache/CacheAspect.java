package com.hsxy.blog.common.cache;

import com.alibaba.fastjson.JSON;
import com.hsxy.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * @name CacheAspect
 * @Description 缓存切面
 * 有BUG,废案(id在JSON中会出现精度损失,但返回为Object,无法使用序列化注解{要大改Long id为String id})
 * @author WU
 * @Date 2022/8/18 15:52
 */
@Aspect
@Component
@Slf4j
public class CacheAspect {
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Pointcut("@annotation(com.hsxy.blog.common.cache.Cache)")
	public void pt(){
		//每个地方都是切入点
	}
	
	@Around("pt()")
	public Object around(ProceedingJoinPoint pjp){
		try {
			Signature signature = pjp.getSignature();
			//类名
			String className = pjp.getTarget().getClass().getSimpleName();
			//调用的方法名
			String methodName = signature.getName();
			
			
			Class[] parameterTypes = new Class[pjp.getArgs().length];
			Object[] args = pjp.getArgs();
			//参数
			//String params = "";
			StringBuilder params = new StringBuilder();
			for(int i=0; i<args.length; i++) {
				if(args[i] != null) {
					//params += JSON.toJSONString(args[i]);
					params.append(JSON.toJSONString(args[i]));
					parameterTypes[i] = args[i].getClass();
				}else {
					parameterTypes[i] = null;
				}
			}
			if (StringUtils.isNotEmpty(params.toString())) {
				//加密 以防出现key过长以及字符转义获取不到的情况
				params = new StringBuilder(DigestUtils.md5Hex(params.toString()));
			}
			Method method = pjp.getSignature().getDeclaringType().getMethod(methodName, parameterTypes);
			//获取Cache注解
			Cache annotation = method.getAnnotation(Cache.class);
			//缓存过期时间
			long expire = annotation.expire();
			//缓存名称
			String name = annotation.name();
			//先从redis获取(可用Spring Cache代替)
			String redisKey = name + "::" + className+"::"+methodName+"::"+params;
			String redisValue = redisTemplate.opsForValue().get(redisKey);
			if (StringUtils.isNotEmpty(redisValue)){
				log.info("走了缓存~~~,{},{}",className,methodName);
				//id在JSON中会出现精度损失,但返回为Object,无法使用序列化注解
				return JSON.parseObject(redisValue, Result.class);
			}
			Object proceed = pjp.proceed();
			redisTemplate.opsForValue().set(redisKey,JSON.toJSONString(proceed), Duration.ofMillis(expire));
			log.info("存入缓存~~~ {},{}",className,methodName);
			return proceed;
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		return Result.fail(-999,"系统错误");
	}
	
}
