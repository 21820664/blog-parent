package com.hsxy.blog.controller;

import com.hsxy.blog.utils.QiniuUtils;
import com.hsxy.blog.vo.ErrorCode;
import com.hsxy.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @name UploadController
 * @Description 控制器:文件上传
 * @author WU
 * @Date 2022/8/17 14:01
 */
@RestController
@RequestMapping("/upload")
public class UploadController {
	@Autowired
	private QiniuUtils qiniuUtils;
	
	//@Value("${spring.servlet.multipart.max-file-size}")
	//String maxFileSize;
	
	//https://blog.csdn.net/justry_deng/article/details/80855235 MultipartFile介绍
	/**
	 * @Description 图片上传
	 * @Param [file] --> image
	 * @return com.hsxy.blog.vo.Result
	 */
	@PostMapping
	public Result upload(@RequestParam("image") MultipartFile file) {
		
		//TODO 判断失败:系统提前报错
		//错误:org.springframework.web.multipart.MaxUploadSizeExceededException: Maximum upload size exceeded; nested exception is java.lang.IllegalStateException: org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException: The field image exceeds its maximum permitted size of 20971520 bytes.
		/*// 判断单个文件大于1M
		long fileSize = file.getSize();
		String byteUnit = maxFileSize.substring(maxFileSize.length() - 1);
		//最大文件容量(忽略单位)
		int maxFileSizeNoUnit = 0;
		try {
			maxFileSizeNoUnit = Integer.parseInt(maxFileSize.substring(0,maxFileSize.length() - 2));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if(byteUnit.compareToIgnoreCase("K") == 0){//文件单位相同(为KB)
			maxFileSizeNoUnit *= 1024;
		} else if (byteUnit.compareToIgnoreCase("M") == 0) {
			maxFileSizeNoUnit *= 1024*1024;
		} else if (byteUnit.compareToIgnoreCase("G") == 0) {
			maxFileSizeNoUnit *= 1024*1024*1024;
		} else {
			return Result.fail(ErrorCode.UPLOAD_FAIL.getCode(), ErrorCode.UPLOAD_FAIL.getMsg());
		}
		
		if (fileSize > maxFileSizeNoUnit) {
			return Result.fail(ErrorCode.UPLOAD_FAIL.getCode(), ErrorCode.UPLOAD_FAIL.getMsg());
		}*/
		
		//原始文件名称 比如说aa.png
		String originalFilename = file.getOriginalFilename();
		//唯一的文件名称(UUID随机数+后缀名)<有字符串不必强转>
		String fileName = UUID.randomUUID() + "." + StringUtils.substringAfterLast(originalFilename, ".");
		//上传文件上传到那里呢？　七牛云　云服务器
		//降低我们自身应用服务器的带宽消耗
		boolean upload = qiniuUtils.upload(file, fileName);
		if (upload) {//	测试:[/test%2F]无效,只能放在根目录下,原因未知
			return Result.success(QiniuUtils.URL + "/" + fileName);
		}
		return Result.fail(ErrorCode.UPLOAD_FAIL.getCode(), ErrorCode.UPLOAD_FAIL.getMsg());
		
	}
}
