package com.atguigu.edu.controller;

import com.atguigu.base.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * @author HaiYu
 */
@Api(description = "模拟登录")
@RestController
@RequestMapping("/user")
public class LoginController {
    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login() {
        return Result.ok().data("token", "admin");
    }


    @ApiOperation("用户信息")
    @GetMapping("/info")
    public Result info() {
        return Result.ok()
                .data("roles", "admin")
                .data("name", "admin")
                .data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
