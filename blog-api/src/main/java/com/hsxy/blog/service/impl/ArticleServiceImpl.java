package com.hsxy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsxy.blog.dao.dos.Archives;
import com.hsxy.blog.dao.mapper.ArticleBodyMapper;
import com.hsxy.blog.dao.mapper.ArticleTagMapper;
import com.hsxy.blog.dao.pojo.ArticleBody;
import com.hsxy.blog.dao.pojo.ArticleTag;
import com.hsxy.blog.dao.pojo.SysUser;
import com.hsxy.blog.service.*;
import com.hsxy.blog.dao.mapper.ArticleMapper;
import com.hsxy.blog.dao.pojo.Article;
import com.hsxy.blog.utils.UserThreadLocal;
import com.hsxy.blog.vo.*;
import com.hsxy.blog.vo.params.ArticleParam;
import com.hsxy.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @name ArticleServiceImpl
 * @Description
 * @author WU
 * @Date 2022/8/8 18:27
 */
@Service
public class ArticleServiceImpl implements ArticleService {
	
	@Resource//等同于@Autowired,但是不爆红
	private ArticleMapper articleMapper;
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public Result listArticle(PageParams pageParams) {
		/**
		 * 1. 分页查询 article数据库表
		 */
		Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
		
		IPage<Article> articleIPage = this.articleMapper.listArticle(
				page,
				pageParams.getCategoryId(),
				pageParams.getTagId(),
				pageParams.getYear(),
				pageParams.getMonth());
		return Result.success(copyList(articleIPage.getRecords(),true,true));
	}
	
	/*@Override
    public Result listArticle(PageParams pageParams) {
        *//**
         * 1. 分页查询 article数据库表
         *//*
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
		//查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
		//查询文章的参数 加上分类id，判断不为空 加上分类条件
        if (pageParams.getCategoryId() != null){
            // and category_id=#{categoryId}
            queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
        }
        List<Long> articleIdList = new ArrayList<>();
        if (pageParams.getTagId() != null){
            //加入标签 条件查询
            //article表中 并没有tag字段 一篇文章 有多个标签
            //article_tag  article_id 1 : n tag_id
	
			//我们需要利用一个全新的属于文章标签的queryWrapper将这篇文章的article_Tag查出来，保存到一个list当中。
			// 然后再根据queryWrapper的in方法选择我们需要的标签即可。
            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId,pageParams.getTagId());
            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
            for (ArticleTag articleTag : articleTags) {
                articleIdList.add(articleTag.getArticleId());
            }
            if (!articleIdList.isEmpty()){
                // and id in(1,2,3)
                queryWrapper.in(Article::getId,articleIdList);
            }
        }
        //是否置顶进行排序
        //order by create_date desc
        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        //能直接返回吗？ 很明显不能
        List<ArticleVo> articleVoList = copyList(records,true,true);
        return Result.success(articleVoList);
    }*/
	
	@Override
	public Result hotArticle(int limit) {
		//查询条件
		LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
		//与sql 执行顺序有出入:7select->9order by->10limit
		queryWrapper.select(Article::getId,Article::getTitle);
		queryWrapper.orderByDesc(Article::getViewCounts);
		queryWrapper.last("limit "+limit);
		//select id,title from article order by view_counts desc limit 5
		List<Article> articles = articleMapper.selectList(queryWrapper);
		
		return Result.success(copyList(articles,false,false));
	}
	
	@Override
	public Result newArticles(int limit) {
		//查询条件
		LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
		//与sql 执行顺序有出入:7select->9order by->10limit
		queryWrapper.select(Article::getId,Article::getTitle);
		queryWrapper.orderByDesc(Article::getCreateDate);
		queryWrapper.last("limit "+limit);
		//select id,title from article order by creat_date desc limit 5
		List<Article> articles = articleMapper.selectList(queryWrapper);
		
		return Result.success(copyList(articles,false,false));
	}
	
	@Override
	public Result listArchives() {
		List<Archives> archivesList = articleMapper.listArchives();
		return Result.success(archivesList);
	}
	
	@Autowired
	private ThreadService threadService;
	
	@Override
	public Result findArticleById(Long articleId) {
		/**
		 * 1. 根据id查询 文章信息
		 * 2. 根据bodyId和categoryid 去做关联查询
		 */
		Article article = this.articleMapper.selectById(articleId);
		ArticleVo articleVo = copy(article, true, true,true,true);
		//查看完文章了，新增阅读数，有没有问题呢？
		//查看完文章之后，本应该直接返回数据了，这时候做了一个更新操作，更新时加写锁，阻塞其他的读操作，性能就会比较低
		// 更新 增加了此次接口的 耗时 如果一旦更新出问题，不能影响 查看文章的操作
		//线程池  可以把更新操作 扔到线程池中去执行，和主线程就不相关了
		threadService.updateArticleViewCount(articleMapper,article);
		return Result.success(articleVo);
	}
	
	/**
	 * @Description
	 * @Param [records, isTag, isAuthor] //采用重载方式
	 * @return java.util.List<com.hsxy.blog.vo.ArticleVo>
	 */
	private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor) {
		List<ArticleVo> articleVoList = new ArrayList<>();
		for (Article article : records) {
			ArticleVo articleVo = copy(article,isTag,isAuthor,false,false);
			articleVoList.add(articleVo);
		}
		return articleVoList;
	
	}
	private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory) {
		List<ArticleVo> articleVoList = new ArrayList<>();
		for (Article article : records) {
			ArticleVo articleVo = copy(article,isTag,isAuthor,isBody,isCategory);
			articleVoList.add(articleVo);
		}
		return articleVoList;
		
	}
	
	//"eop的作用是对应copyList，集合之间的copy分解成集合元素之间的copy
	private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory){
		ArticleVo articleVo = new ArticleVo();
		//BeanUtils.copyProperties用法   https://blog.csdn.net/Mr_linjw/article/details/50236279
		BeanUtils.copyProperties(article, articleVo);
		articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
		//并不是所有的接口都需要标签和作者信息
		if(isTag){
			Long articleId = article.getId();
			articleVo.setTags(tagService.findTagsByArticleId(articleId));
		}
		if (isAuthor) {
			//拿到作者id
			Long authorId = article.getAuthorId();
			articleVo.setAuthor(sysUserService.findUserVoById(authorId).getNickname());
		}
		if (isBody){
			Long bodyId = article.getBodyId();
			articleVo.setBody(findArticleBodyById(bodyId));
		}
		if (isCategory){
			Long categoryId = article.getCategoryId();
			articleVo.setCategory(categoryService.findCategoryById(categoryId));
		}
		return articleVo;
		
	}
	
	@Resource
	private CategoryService categoryService;
	
	private CategoryVo findCategory(Long categoryId) {
		return categoryService.findCategoryById(categoryId);
	}
	
	
	@Resource
	private ArticleBodyMapper articleBodyMapper;
	
	private ArticleBodyVo findArticleBodyById(Long bodyId) {
		ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
		ArticleBodyVo articleBodyVo = new ArticleBodyVo();
		articleBodyVo.setContent(articleBody.getContent());
		return articleBodyVo;
	}
	
	@Resource
	private ArticleTagMapper articleTagMapper;
	
	@Override
	@Transactional
	public Result publish(ArticleParam articleParam) {
		//注意想要拿到数据必须将接口加入拦截器
		SysUser sysUser = UserThreadLocal.get();
		
		/**
		 * 1. 发布文章 目的 构建Article对象
		 * 2. 作者id  当前的登录用户
		 * 3. 标签  要将标签加入到 关联列表当中
		 * 4. body 内容存储 article bodyId
		 */
		Article article = new Article();
		article.setAuthorId(sysUser.getId());
		article.setCategoryId(articleParam.getCategory().getId());
		article.setCreateDate(System.currentTimeMillis());
		article.setCommentCounts(0);
		article.setSummary(articleParam.getSummary());
		article.setTitle(articleParam.getTitle());
		article.setViewCounts(0);
		article.setWeight(Article.Article_Common);
		article.setBodyId(-1L);
		//插入之后 会生成一个文章id（因为新建的文章没有文章id所以要insert一下
		//官网解释："insert后主键会自动'set到实体的ID字段。所以你只需要"getid()就好
		//利用主键自增，mp的insert操作后id值会回到参数对象中
		//https://blog.csdn.net/HSJ0170/article/details/107982866
		this.articleMapper.insert(article);
		
		//tags
		List<TagVo> tags = articleParam.getTags();
		if (tags != null) {
			for (TagVo tag : tags) {
				ArticleTag articleTag = new ArticleTag();
				articleTag.setArticleId(article.getId());
				articleTag.setTagId(tag.getId());
				this.articleTagMapper.insert(articleTag);
			}
		}
		//body
		ArticleBody articleBody = new ArticleBody();
		articleBody.setContent(articleParam.getBody().getContent());
		articleBody.setContentHtml(articleParam.getBody().getContentHtml());
		articleBody.setArticleId(article.getId());
		articleBodyMapper.insert(articleBody);
		//插入完之后生成BodyId
		article.setBodyId(articleBody.getId());
		//MybatisPlus中的save方法什么时候执行insert，什么时候执行update
		// https://www.cxyzjd.com/article/Horse7/103868144
		//只有当更改数据库时才插入或者更新，一般查询就可以了
		articleMapper.updateById(article);
		
		ArticleVo articleVo = new ArticleVo();
		articleVo.setId(article.getId());
		return Result.success(articleVo);//只设置了ID值,只返回ID
		
		/*//第二种返回方法
		Map<String,String> map = new HashMap<>();
		map.put("id",article.getId().toString());
		return Result.success(map);*/
	}
}
