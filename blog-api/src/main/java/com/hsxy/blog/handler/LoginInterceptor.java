package com.hsxy.blog.handler;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hsxy.blog.dao.pojo.SysUser;
import com.hsxy.blog.service.LoginService;
import com.hsxy.blog.utils.UserThreadLocal;
import com.hsxy.blog.vo.ErrorCode;
import com.hsxy.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @name LoginInterceptor
 * @Description 登录拦截器
 * @author WU
 * @Date 2022/8/12 9:18
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private LoginService loginService;
	
	/**
	 * 该方法是在执行执行servlet的 service方法之前执行的
	 * 即在进入controller之前调用
	 * @return 如果返回true表示继续执行下一个拦截器的PreHandle方法；如果没有拦截器了,则执行controller
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//在执行controller方法(Handler)之前进行执行
		/**
		 * 1. 需要判断 请求的接口路径 是否为 HandlerMethod (controller方法)
		 * 2. 判断 token是否为空，如果为空 未登录
		 * 3. 如果token 不为空，登录验证 loginService checkToken
		 * 4. 如果认证成功 放行即可
		 */
		//1. 如果不是我们的方法进行放行
		if (!(handler instanceof HandlerMethod)){
			//handler 可能是 RequestResourceHandler springboot 程序 访问静态资源 默认去classpath下的static目录去查询
			return true;
		}
		String token = request.getHeader("Authorization");//users/currentUser
		
		log.info("=================request start===========================");
		String requestURI = request.getRequestURI();
		log.info("request uri:{}",requestURI);
		log.info("request method:{}",request.getMethod());
		log.info("token:{}", token);
		log.info("=================request end===========================");
		//未登录
		if(StringUtils.isBlank(token)){
			Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
			//设置浏览器识别返回的是json
			response.setContentType("application/json;charset=utf-8");
			//https://www.cnblogs.com/qlqwjy/p/7455706.html response.getWriter().print()
			//SON.toJSONString则是将对象转化为Json字符串
			response.getWriter().print(JSON.toJSONString(result));
			return false;
		}
		//Token不为空,认证
		SysUser sysUser = loginService.checkToken(token);
		if (sysUser == null){
			//同上
			Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(JSON.toJSONString(result));
			return false;
		}
		//是登录状态，放行
		//登录验证成功，放行
		//我希望在controller中 直接获取用户的信息 怎么获取?
		UserThreadLocal.put(sysUser);
		
		return true;
	}
	/**
	 * @Description 完成controller后执行
	 * @Param [request, response, handler, ex]
	 * @return void
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
		//如果不删除 ThreadLocal中用完的信息 会有内存泄漏的风险
		UserThreadLocal.remove();
	}
	
}
