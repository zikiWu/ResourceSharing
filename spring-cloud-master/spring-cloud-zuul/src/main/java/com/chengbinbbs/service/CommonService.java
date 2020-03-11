package com.chengbinbbs.service;

import com.chengbinbbs.po.vo.AjaxResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@FeignClient(name = "common-service")
public interface CommonService {

//    @RequestMapping("login.do")
//    public AjaxResponse<Object> logindo(HttpServletRequest request, HttpServletResponse response,
//                                        String account, String password, String rememberMe);
}
