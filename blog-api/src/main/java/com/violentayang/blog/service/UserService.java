package com.violentayang.blog.service;

import com.violentayang.blog.dao.pojo.SysUser;

public interface UserService {

    SysUser findUserById(Long userId);

    SysUser findUser(String account, String password);
}
