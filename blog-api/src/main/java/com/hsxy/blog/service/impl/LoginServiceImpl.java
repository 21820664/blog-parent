package com.hsxy.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hsxy.blog.dao.pojo.SysUser;
import com.hsxy.blog.service.LoginService;
import com.hsxy.blog.service.SysUserService;
import com.hsxy.blog.utils.JWTUtils;
import com.hsxy.blog.vo.ErrorCode;
import com.hsxy.blog.vo.Result;
import com.hsxy.blog.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @name LoginServceImpl
 * @Description
 * @author WU
 * @Date 2022/8/11 10:35
 */
@Service
@Transactional	//DO 新增:为什么要加事务，因为我们在添加用户的时候，如果他的数据有问题，还有就是其他一些的问题的时候，数据库里面是不能这样添加用户的，但是不加事务的话，可能会自动加上。--->所以要添加事务
public class LoginServiceImpl implements LoginService {
	//加密盐用于加密
	private static final String SALT = "mszlu!@#";
	
	@Autowired
	private SysUserService sysUserService;
	
	@Resource
	private RedisTemplate<String,String> redisTemplate;
	
	@Override
	public Result login(LoginParam loginParam) {
		/**
		 * 1. 检查参数是否合法
		 * 2. 根据用户名和密码去user表中查询 是否存在
		 * 3. 如果不存在 登录失败
		 * 4. 如果存在 ，使用jwt 生成token 返回给前端
		 * 5. token放入redis当中，redis  token：user信息 设置过期时间（相比来说session会给服务器产生压力，这么做也是为了实现jwt的续签）
		 *  (登录认证的时候 先认证token字符串是否合法，去redis认证是否存在)
		 */
		String account = loginParam.getAccount();
		String password = loginParam.getPassword();
		if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
			return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
		}
		String pwd = DigestUtils.md5Hex(password + SALT);//MD5+盐 加密
		SysUser sysUser = sysUserService.findUser(account,pwd);
		if (sysUser == null){
			return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
		}
		//登录成功，使用JWT生成token，返回token和redis中
		String token = JWTUtils.createToken(sysUser.getId());
		// JSON.toJSONString 用法    https://blog.csdn.net/antony9118/article/details/71023009
		//过期时间是一百天
		//redisTemplate用法  https://blog.csdn.net/lydms/article/details/105224210
		redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),100, TimeUnit.DAYS);
		return Result.success(token);
	}
	
	@Override
	public SysUser checkToken(String token) {
		//token为空返回null
		if(StringUtils.isBlank(token)){
			return null;
		}
		Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
		//解析失败
		if(stringObjectMap ==null){
			return null;
		}
		//如果成功
		String userJson =  redisTemplate.opsForValue().get("TOKEN_"+token);
		if (StringUtils.isBlank(userJson)) {
			return null;
		}
		//解析回sysUser对象
		SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
		return sysUser;
	}
	
	@Override
	public Result logout(String token) {
		redisTemplate.delete("TOKEN_"+token);
		return Result.success(null);
	}
	
	@Override
	public Result register(LoginParam loginParam) {
		/**
		 * 1. 判断参数 是否合法
		 * 2. 判断账户是否存在，存在 返回账户已经被注册
		 * 3. 不存在，注册用户
		 * 4. 生成token
		 * 5. 存入redis 并返回
		 * 6. 注意 加上事务，一旦中间的任何过程出现问题，注册的用户 需要回滚
		 */
		String account = loginParam.getAccount();
		String password = loginParam.getPassword();
		String nickname = loginParam.getNickname();
		if (StringUtils.isBlank(account)//实际操作中交给前端处理
				|| StringUtils.isBlank(password)
				|| StringUtils.isBlank(nickname)
		){
			return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
		}
		SysUser sysUser = sysUserService.findUserByAccount(account);
		if (sysUser != null){
			return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),ErrorCode.ACCOUNT_EXIST.getMsg());
		}
		//填充注册属性
		sysUser = new SysUser();
		sysUser.setNickname(nickname);
		sysUser.setAccount(account);
		sysUser.setPassword(DigestUtils.md5Hex(password + SALT));
		sysUser.setCreateDate(System.currentTimeMillis());
		sysUser.setLastLogin(System.currentTimeMillis());
		sysUser.setAvatar("/static/img/logo.b3a48c0.png");
		sysUser.setAdmin(1); //1 为true
		sysUser.setDeleted(0); // 0 为false
		sysUser.setSalt("");
		sysUser.setStatus("");
		sysUser.setEmail("");
		this.sysUserService.save(sysUser);
		
		//token 登录重复代码
		String token = JWTUtils.createToken(sysUser.getId());
		
		redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
		return Result.success(token);
	}
	
	/*//?未解决
	public static void main(String[] args) {
		String account = "admin";
		String password = "admin";
		
		String pwd = DigestUtils.md5Hex(password + SALT);//MD5+盐 加密
		System.out.println("pwd="+pwd);//pwd=15f08f86435b060236fa9ccea751e9e5(同)
		
	}*/
}
