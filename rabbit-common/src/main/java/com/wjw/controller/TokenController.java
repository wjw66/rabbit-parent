package com.wjw.controller;

import com.wjw.annotation.AutoIdempotent;
import com.wjw.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : wjwjava01@163.com
 * @date : 23:19 2020/8/19
 * @description :
 */
@RequestMapping("/demo")
@RestController
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @GetMapping("/getToken")
    public String getToken(){
        return tokenService.createToken();
    }

    @PostMapping("/hello")
    @AutoIdempotent
    public String hello(){
        return "【通过】token获取到hello!";
    }

    @PostMapping("/hello1")
    public String hello1(){
        return "【不通过】token获取到hello!";
    }

}
