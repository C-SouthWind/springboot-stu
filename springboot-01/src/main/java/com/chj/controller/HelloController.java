package com.chj.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    //接口 ：http://localhost:8080/hello
    @RequestMapping("/hello")
    public String hello(){
        //调用前端业务，接受前端的参数
        return "hello world";
    }

}
