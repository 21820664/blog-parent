package com.hsxy.blog.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @name JacksonConfig
 * @Description jackson2序列化统一解决Long精度丢失问题
 * @author WU
 * @Date 2022/8/18 17:33
 */
@Configuration
public class JacksonConfig {
	
	/**
	 * Jackson全局转化long类型为String，解决jackson序列化时long类型缺失精度问题
	 * @return Jackson2ObjectMapperBuilderCustomizer 注入的对象
	 */
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
		return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder
				.serializerByType(Long.class, ToStringSerializer.instance)
				.serializerByType(Long.TYPE, ToStringSerializer.instance);
	}
}