package com.hsxy.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @name ThreadPoolConfig
 * @Description 线程池配置
 * @author WU
 * @Date 2022/8/12 23:02
 */
@Configuration
@EnableAsync //开启多线程
public class ThreadPoolConfig {
	
	/**
	 * @Description 异步服务执行器
	 * @Param []
	 * @return java.util.concurrent.Executor
	 */
	@Bean("taskExecutor")//引号只是命名,便于线程服务确认
	public Executor asyncServiceExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 设置核心线程数
		executor.setCorePoolSize(5);
		// 设置最大线程数
		executor.setMaxPoolSize(20);
		//配置队列大小
		executor.setQueueCapacity(Integer.MAX_VALUE);
		// 设置线程活跃时间（秒）
		executor.setKeepAliveSeconds(60);
		// 设置默认线程名称
		executor.setThreadNamePrefix("码神之路博客项目");
		// 等待所有任务结束后再关闭线程池
		executor.setWaitForTasksToCompleteOnShutdown(true);
		//执行初始化
		executor.initialize();
		return executor;
	}
}