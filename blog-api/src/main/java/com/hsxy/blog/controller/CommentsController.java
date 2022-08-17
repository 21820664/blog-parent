package com.hsxy.blog.controller;

import com.hsxy.blog.service.CommentsService;
import com.hsxy.blog.vo.Result;
import com.hsxy.blog.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @name CommentsController
 * @Description 控制器:评论
 * @author WU
 * @Date 2022/8/15 9:44
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {
	
	@Autowired
	private CommentsService commentsService;
	
	@GetMapping("/article/{id}")
	public Result comments(@PathVariable("id") Long articleId){
		
		return commentsService.commentsByArticleId(articleId);
		
	}
	
	@PostMapping("/create/change")
	public Result comment(@RequestBody CommentParam commentParam){
		return commentsService.comment(commentParam);
	}
}