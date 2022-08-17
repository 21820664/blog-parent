package com.hsxy.blog.utils;

import com.alibaba.fastjson.JSON;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @name QiniuUtils
 * @Description 七牛云(图床)
 * @author WU
 * @Date 2022/8/17 15:01
 */
@Component
@Slf4j
public class QiniuUtils {
	//七牛云测试域名(30天)
	public static final String URL = "rgqys1n0s.hn-bkt.clouddn.com";
	
	//修改以下两个值放到proprietarties中，在密钥管理中获取
	@Value("${qiniu.accessKey}")
	private String accessKey;
	@Value("${qiniu.accessSecretKey}")
	private String accessSecretKey;
	
	/**
	 * @Description 七牛云-服务器直传-数据流上传(图片上传)
	 * (<a href="https://developer.qiniu.com/kodo/1239/java#server-upload">...</a>)
	 * @Param [file, fileName]
	 * @return boolean
	 */
	public boolean upload(MultipartFile file, String fileName){
		
		//构造一个带指定 Region 对象的配置类(华南地区)
		Configuration cfg = new Configuration(Region.huanan());
		//...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		//...生成上传凭证，然后准备上传，修改上传名称为自己创立空间的空间名称（是你自己的）
		String bucket = "blog-hsxy";
		//默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = null;
		try {
			byte[] uploadBytes = file.getBytes();
			Auth auth = Auth.create(accessKey, accessSecretKey);
			String upToken = auth.uploadToken(bucket);
			Response response = uploadManager.put(uploadBytes, fileName, upToken);
			//解析上传成功的结果
			DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
			//按官网另加
			//代替下方
			log.info("=================七牛云 start===========================");
			log.info("putRet.key:    {}",putRet.key);
			log.info("putRet.hash:   {}",putRet.hash);
			log.info("=================七牛云 end===========================");
			/*System.out.println(putRet.key);
			System.out.println(putRet.hash);*/
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
}