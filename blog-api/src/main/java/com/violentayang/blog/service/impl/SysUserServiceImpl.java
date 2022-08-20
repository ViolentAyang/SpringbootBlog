package com.violentayang.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.violentayang.blog.dao.mapper.SysUserMapper;
import com.violentayang.blog.dao.pojo.SysUser;
import com.violentayang.blog.service.LoginService;
import com.violentayang.blog.service.UserService;
import com.violentayang.blog.vo.ErrorCode;
import com.violentayang.blog.vo.LoginUserVo;
import com.violentayang.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserServiceImpl implements UserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private LoginService loginService;

    @Override
    public SysUser findUserById(Long id) {
        //根据id查询
        //为防止sysUser为空增加一个判断
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("admin");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        //去loginService处理密码加密
        queryWrapper.select(SysUser::getAccount,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {
        /**
         * 1.token合法性校验
         *  是否为空，解析是否成功，redis是否存在
         * 2.如果校验失败 返回错误
         * 3.如果成功 返回对应的结果 LoginUserVo
         */
        if (StringUtils.isBlank(token)){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null){
            Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setAccount(sysUser.getAccount());
        return Result.success(loginUserVo);
    }
}

