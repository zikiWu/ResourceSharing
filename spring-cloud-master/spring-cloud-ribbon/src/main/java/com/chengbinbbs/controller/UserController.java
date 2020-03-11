package com.chengbinbbs.controller;

import com.chengbinbbs.module.User;
import com.chengbinbbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 控制器
 *
 * @author zhangcb
 * @created 2017-05-24 14:02.
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/user/add",method = RequestMethod.GET)
    public String addUser(@RequestParam(value = "name")String name,@RequestParam(value = "age")Integer age){
        userService.addUser(name,age);
        return "访问";
    }
    @RequestMapping(value = "/user/get")
    public User getUser(){
        return new User("1",1);
    }

}
