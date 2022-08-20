package com.violentayang.blog.service;

import com.violentayang.blog.dao.pojo.SysUser;
import com.violentayang.blog.vo.Result;

public interface UserService {

    SysUser findUserById(Long userId);

    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);
}
