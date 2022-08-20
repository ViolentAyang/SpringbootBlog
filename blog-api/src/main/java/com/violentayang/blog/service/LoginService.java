package com.violentayang.blog.service;

import com.violentayang.blog.dao.pojo.SysUser;
import com.violentayang.blog.vo.Result;
import com.violentayang.blog.vo.params.LoginParam;

public interface LoginService {
    /**
     * 登录功能
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    SysUser checkToken(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Result logout(String token);
}
