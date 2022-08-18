package com.hsxy.blog.common.cache;

import java.lang.annotation.*;

/**
 * @name Cache
 * @Description 缓存注解(建议使用Spring Cache)
 * @author WU
 * @Date 2022/8/18 15:49
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {
	/**
	 * @Description 过期时间
	 * @Param []
	 * @return long
	 */
	long expire() default 1 * 60 * 1000;
	/**
	 * @Description 缓存标识Key
	 * @Param []
	 * @return java.lang.String
	 */
	String name() default "";
	
}
