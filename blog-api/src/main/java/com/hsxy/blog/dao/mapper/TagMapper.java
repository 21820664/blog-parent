package com.hsxy.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hsxy.blog.dao.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @name TagMapper
 * @Description
 * @author WU
 * @Date 2022/8/8 15:18
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
	
	
	
	/**
	 * @Description 根据文章ID查询标签列表
	 * @Param [articleId]
	 * @return java.util.List<com.hsxy.blog.dao.pojo.Tag>
	 */
	List<Tag> findTagsByArticleId(Long articleId);
	
	/**
	 * @Description 查询最热的标签 前n条
	 * @Param [limit]
	 * @return java.util.List<java.lang.Long>
	 */
	List<Long> findHotsTagIds(int limit);
	
	List<Tag> findTagsByTagIds(List<Long> tagIds);
}
