package com.hsxy.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hsxy.blog.dao.dos.Archives;
import com.hsxy.blog.dao.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @name ArticleMapper
 * @Description
 * @author WU
 * @Date 2022/8/8 14:00
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
	
	/**
	 * @Description 文章归档
	 * @Param []
	 * @return java.util.List<com.hsxy.blog.dao.dos.Archives>
	 */
	List<Archives> listArchives();
}
