package com.chj.controller;

import com.chj.mapper.UserMapper;
import com.chj.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/queryUsers")
    public List<UserPojo> queryUsers(){
       return userMapper.queryUsers();
    }
}
