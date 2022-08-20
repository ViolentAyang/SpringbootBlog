package com.violentayang.blog.controller;

import com.violentayang.blog.service.LoginService;
import com.violentayang.blog.vo.Result;
import com.violentayang.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("login")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){
        //登录要验证用户 访问用户表，但是
        return loginService.login(loginParam);
    }
}
