package com.violentayang.blog.controller;

import com.violentayang.blog.service.LoginService;
import com.violentayang.blog.vo.Result;
import com.violentayang.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("logout")
public class LogoutController {

    @Autowired
    private LoginService loginService;

//获取头部信息这样一个参数
    @GetMapping
    public Result logout(@RequestHeader("Authorization") String token){

        return loginService.logout(token);

    }
}

