package com.hsxy.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hsxy.blog.dao.mapper.ArticleMapper;
import com.hsxy.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


/**
 * @name ThreadService
 * @Description 自定义线程池
 * @author WU
 * @Date 2022/8/12 18:48
 */
@Service
public class ThreadService  {
	
	/**
	 * @Description 更新文章浏览次数 --> 期望此操作在线程池执行不会影响原有主线程
	 * @Param [articleMapper, article]
	 * @return void
	 */
	@Async("taskExecutor")
	public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
		
		int viewCounts = article.getViewCounts();
		Article articleUpdate = new Article();
		articleUpdate.setViewCounts(viewCounts++);
		LambdaQueryWrapper<Article> updateWrapper = new LambdaQueryWrapper<>();
		//根据id更新
		updateWrapper.eq(Article::getId ,article.getId());
		//设置一个为了在多线程的环境下线程安全
		//改之前再确认这个值有没有被其他线程抢先修改，类似于CAS操作 cas加自旋，加个循环就是cas
		//查看次数只增不减,可看做乐观锁的时间戳（timestamp）
		updateWrapper.eq(Article ::getViewCounts,viewCounts );
		// update article set view_count=100 where view_count=99 and id =111
		//实体类加更新条件
		articleMapper.update(articleUpdate,updateWrapper);
		try {
			Thread.sleep(5000);
			System.out.println("更新完成了");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
