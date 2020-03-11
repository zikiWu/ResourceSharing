package com.zk.controller;

import com.zk.po.enums.OrderByEnum;
import com.zk.po.enums.ResponseCode;
import com.zk.po.model.Message;
import com.zk.po.query.MessageQuery;
import com.zk.po.utils.PageResult;
import com.zk.po.vo.AjaxResponse;
import com.zk.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class MessageControl {

    @Autowired
    private MessageService messageService;



}
