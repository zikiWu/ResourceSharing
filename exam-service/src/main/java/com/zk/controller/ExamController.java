package com.zk.controller;

import com.alibaba.fastjson.JSONObject;
import com.zk.po.enums.ExamChooseType;
import com.zk.po.model.Category;
import com.zk.po.model.SessionUser;
import com.zk.po.query.CategoryQuery;
import com.zk.po.utils.Constants;
import com.zk.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ExamController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired

    @RequestMapping
    public ModelAndView exam(HttpServletRequest request){
        CategoryQuery categoryQuery = new CategoryQuery();
        categoryQuery.setShowInExam(Constants.Y);
        List<Category> categoryList = this.categoryService.findCategoryList(categoryQuery);
        ModelAndView view = new ModelAndView("/exam/exam");
        view.addObject("categoryList", categoryList);
//        String userJson = restTemplate.getForObject("http://COMMON-SERVICE/getLoginUserForServer",String.class,"");
//        SessionUser user =	JSONObject.parseObject(userJson, SessionUser.class);
        SessionUser user =  null;
        if(user == null){
            user = new SessionUser();
            user.setUserid(10043);
            user.setUserName("shyer");
            user.setUserIcon("user_bg/bg10.jpg");
        }
        view.addObject("user",  user);
        return view;
    }

    @RequestMapping("preDoExam")
    public ModelAndView preDoExam(HttpSession session,Integer categoryId){
        ModelAndView view = new ModelAndView("/exam/doExam");
        view.addObject("categoryId", categoryId);
        SessionUser user =  null;
        if(user == null){
            user = new SessionUser();
            user.setUserid(10043);
            user.setUserName("shyer");
            user.setUserIcon("user_bg/bg10.jpg");
        }
        view.addObject("user",  user);
        return view;
    }

    @RequestMapping("addExam")
    public ModelAndView addExam(HttpSession session){
        CategoryQuery categoryQuery = new CategoryQuery();
        categoryQuery.setShowInExam(Constants.Y);
        List<Category> categoryList = this.categoryService.findCategoryList(categoryQuery);
        ModelAndView view = new ModelAndView("/exam/addExam");
        view.addObject("categoryList", categoryList);
        view.addObject("examChooseType", ExamChooseType.values());
        return view;
    }
}
