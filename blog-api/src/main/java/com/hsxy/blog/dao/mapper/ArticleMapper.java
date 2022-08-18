package com.hsxy.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsxy.blog.dao.dos.Archives;
import com.hsxy.blog.dao.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
	
	/**
	 * @Description 自定义sql(?需要在每个参数前加@Param())
	 * @Param [page, categoryId, tagId, year, month]
	 * @return com.baomidou.mybatisplus.core.metadata.IPage<com.hsxy.blog.dao.pojo.Article>
	 */
	IPage<Article> listArticle(Page<Article> page,
								Long categoryId,
								Long tagId,
								String year,
								String month);
}
