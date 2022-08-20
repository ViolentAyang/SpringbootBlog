package com.violentayang.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.violentayang.blog.dao.pojo.SysUser;
import com.violentayang.blog.service.LoginService;
import com.violentayang.blog.service.UserService;
import com.violentayang.blog.util.JWTUtils;
import com.violentayang.blog.vo.ErrorCode;
import com.violentayang.blog.vo.Result;
import com.violentayang.blog.vo.params.LoginParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    //加密盐用于加密
    private static final String salt = "mszlu!@#";

    @Autowired
    private UserService sysUserService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public Result login(LoginParam loginParam) {
        /**
         * 1.检查参数是否合法
         * 2.根据用户名和密码去user表中查询是否存在
         * 3.如果不存在那就登录失败
         * 4.如果存在，使用JWT生成token 返回给前端
         * 5.token放入redis中 reids token：user信息 设置过期时间
         * (登录认证的时候 先认证我们的token字符串是否合法，去redis认证是否存在)
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md5Hex(password + salt);
        SysUser sysUser = sysUserService.findUser(account,password);
        if (sysUser == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }
}
