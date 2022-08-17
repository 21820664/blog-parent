package com.hsxy.blog.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @name Article
 * @Description 文章类
 * @author WU
 * @Date 2022/8/8 10:47
 */
@Data
public class Article {
	
	/**
	 * @Description 文章是否置顶(1,0)
	 * @Param
	 * @return
	 */
	public static final int Article_TOP = 1;
	
	public static final int Article_Common = 0;
	
	
	@Id//不确定是否需要
	@TableId(type = IdType.AUTO)	//数据库自增
	private Long id;
	
	private String title;
	
	private String summary;
	
	private Integer commentCounts;
	
	private Integer viewCounts;
	
	/**
	 * 作者id
	 */
	private Long authorId;
	/**
	 * 内容id
	 */
	private Long bodyId;
	/**
	 *类别id
	 */
	private Long categoryId;
	
	/**
	 * 置顶
	 */
	private Integer weight;
	
	
	/**
	 * 创建时间
	 */
	private Long createDate;
}
