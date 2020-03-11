package com.zk.controller;

import com.alibaba.fastjson.JSON;
import com.zk.exception.BussinessException;
import com.zk.po.model.User;
import com.zk.service.FormateAtService;
import com.zk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;


@Controller
public class UtilControl {

    @Autowired
    private FormateAtService formateAtService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/FormateAtService/generateRefererLinks")
    public String index(String msg, Set<Integer> userIds){
       return   formateAtService.generateRefererLinks(msg,userIds);
    }
    @RequestMapping(value = "/UserService/changeMark")
    public void changeMark(int userid, int mark){
        userService.changeMark(userid,mark);
    }

//    @RequestMapping(value = "/UserService/findAllUser")
//    public String findAllUser(){
//
//        return  JSON.toJSONString(userService.findAllUsers());
//    }

    @ResponseBody
    @RequestMapping("/UserService/getUsers")
    public Object getUsers() {
        return this.userService.findUserVoList();
    }



}
