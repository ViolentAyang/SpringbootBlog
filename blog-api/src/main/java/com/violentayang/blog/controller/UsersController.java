package com.violentayang.blog.controller;

import com.violentayang.blog.service.UserService;
import com.violentayang.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return userService.findUserByToken(token);
    }
}
