package com.zk.controller;

import com.alibaba.fastjson.JSON;
import com.zk.cache.CategoryCache;
import com.zk.exception.BussinessException;
import com.zk.mapper.CategoryMapper;
import com.zk.mapper.TopicMapper;
import com.zk.po.enums.AjaxResponse;
import com.zk.po.enums.DateTimePatternEnum;
import com.zk.po.model.Category;
import com.zk.po.model.Topic;
import com.zk.po.model.User;
import com.zk.po.query.CategoryQuery;
import com.zk.po.query.TopicQuery;
import com.zk.po.utils.DateUtil;
import com.zk.po.utils.LayUiListResult;
import com.zk.po.vo.TopicChildrenCate;
import com.zk.po.vo.UeditorResponse;
import com.zk.service.CategoryService;
import com.zk.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@Controller
public class ServerController {

    @Autowired
    private TopicMapper<Topic, TopicQuery> topicMapper;

    @Autowired
    private TopicService topicService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryCache categoryCache;

    @Value(value = "${resources_path}")
    String resources_path;//资源文件绝对地址目录

    @Value(value = "${server_path}")
    String server_path;//文件服务器地址目录

    @RequestMapping("/getTopic")
    @ResponseBody
    public Integer publicTopic(Integer articleId){
        TopicQuery topicQuery = new TopicQuery();
        topicQuery.setTopicId(articleId);
        Topic t = this.topicMapper.selectList(topicQuery).get(0);
        return t.getUserId();
    }
    @RequestMapping("/topicServer/getTopic")
    @ResponseBody
    public  LayUiListResult<Topic> getTopicList( Integer page, Integer limit){
        TopicQuery topicQuery = new TopicQuery();
        topicQuery.setShowContent(true);
        List<Topic> topicList = this.topicMapper.selectList(topicQuery);
        LayUiListResult<Topic> layUiListResult = new LayUiListResult<>(0,"",topicList.size(),page,limit);
        layUiListResult.setData(topicList);
        return layUiListResult;
    }

    @RequestMapping("/topicServer/getFirstCates")
    @ResponseBody
    public    LayUiListResult<Category> getFirstCates( Integer page, Integer limit){
        if (page == null){
            page = 1;
            limit = 10;
        }
        List<Category> category = categoryService.findCategoryList(new CategoryQuery());
        LayUiListResult<Category> layUiListResult = new LayUiListResult<>(0,"",category.size(),page,limit);
        layUiListResult.setData(category);
        return layUiListResult;
    }


    @RequestMapping("/topicServer/getSecondCates")
    @ResponseBody
    public  LayUiListResult<TopicChildrenCate>  getSecondCates( Integer page, Integer limit){
        if (page == null){
            page = 1;
            limit = 10;
        }
        List<Category> categorys = categoryService.findCategoryList(new CategoryQuery());
        List<TopicChildrenCate> children = new ArrayList<>();

        for (Category c : categorys){
            List<Category> ccs = c.getChildren();
            ccs.forEach(cs->{
                TopicChildrenCate childrenCate = new TopicChildrenCate();
                childrenCate.setCategoryId(cs.getCategoryId());
                childrenCate.setName(cs.getName());
                childrenCate.setDesc(cs.getDesc());
                childrenCate.setPid(cs.getPid());
                childrenCate.setPname(c.getName());
                children.add(childrenCate);
            });
        }
        LayUiListResult<TopicChildrenCate> layUiListResult = new LayUiListResult<>(0,"",children.size(),page,limit);
        layUiListResult.setData(children);
        return layUiListResult;
    }


    @RequestMapping("/topicServer/delCates")
    public  void  getSecondCates( Integer id){
        categoryMapper.delete(id);
        categoryCache.refreshCategoryCache();
    }
    @RequestMapping("/topicServer/updateSecondCates")
    public  void  updateSecondCates(Integer id,String name){
        TopicQuery topicQuery = new TopicQuery();
        topicQuery.setTopicId(id);
        Category category = (Category) categoryMapper.selectCategory4TopicCount(topicQuery).get(0);
        category.setCategoryId(id);
        category.setName(name);
        categoryMapper.update(category);
        categoryCache.refreshCategoryCache();
    }

    @RequestMapping("/topicServer/addSecondCates")
    public  void  addSecondCates(Integer pid,String name){
        Category category = new Category();
        category.setPid(pid);
        category.setName(name);
        category.setRank(0);
        category.setAllowPost(0);
        category.setShowInBbs("Y");
        category.setShowInQuestion("Y");
        category.setShowInKnowledge("Y");
        category.setShowInExam("Y");
        categoryMapper.insert(category);
        categoryCache.refreshCategoryCache();
    }

    /**置顶
    * @author wzk
    * @date 2019/4/20
    * @param
    */
    @ResponseBody
    @RequestMapping("/topic/stick")
    public AjaxResponse<Object> topicstick(Integer topicId){
        AjaxResponse<Object> ajaxResponse = new AjaxResponse<>();
        try {
            topicService.updateTopicStick(new Integer[]{topicId}, 1);
        } catch (BussinessException e) {
            ajaxResponse.setErrorMsg(e.getLocalizedMessage());
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ajaxResponse;
    }

    /**取消置顶
    * @author wzk
    * @date 2019/4/20
    * @param
    */
    @ResponseBody
    @RequestMapping("/topic/unstick")
    public AjaxResponse<Object> topicunstick(Integer topicId){
        AjaxResponse<Object> ajaxResponse = new AjaxResponse<>();
        try {
            topicService.updateTopicStick(new Integer[]{topicId}, 0);
        } catch (BussinessException e) {
            ajaxResponse.setErrorMsg(e.getLocalizedMessage());
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ajaxResponse;
    }


    @ResponseBody
    @RequestMapping("ueditorImageUpload.action")
    public UeditorResponse ueditorImageUpload(HttpSession session, MultipartHttpServletRequest multirequest,
                                              HttpServletResponse response){
        String realPath = resources_path + "/upload";
        UeditorResponse ueditorResponse = new UeditorResponse();
        Iterator<String> itr = multirequest.getFileNames();
        if(itr.hasNext()){
            MultipartFile multipartFile = multirequest.getFile(itr.next());
            long size = multipartFile.getSize();
            String fileName = multipartFile.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf(".")  + 1);
            if(!"JPG".equalsIgnoreCase(suffix) && !"BMP".equalsIgnoreCase(suffix) &&
                    !"gif".equalsIgnoreCase(suffix) && !"PNG".equalsIgnoreCase(suffix)){
                ueditorResponse.setState("只能是图片");
                return ueditorResponse;
            }
            String current = String.valueOf(System.currentTimeMillis());
            fileName = current + "." + suffix;
            String saveDir = DateUtil.format(new Date(), DateTimePatternEnum.YYYYMM.getPattern());
            String savePath = saveDir + "/" + fileName;
            String fileDir = realPath + "/" + saveDir;
            File dir = new File(fileDir);
            if(!dir.exists()){
                dir.mkdirs();
            }
            String filePath = fileDir + "/" + fileName;
            File file = new File(filePath);
            try {
                multipartFile.transferTo(file);
                ueditorResponse.setState("SUCCESS");
                ueditorResponse.setUrl(server_path + "upload/" + savePath);
                return ueditorResponse;
            } catch (Exception e) {
                ueditorResponse.setState("上传参数出错");
                return ueditorResponse;
            }
        }
        return ueditorResponse;
    }

    @RequestMapping("/getTopicById")
    @ResponseBody
    public String getTopicById(Integer topicId){
        TopicQuery topicQuery = new TopicQuery();
        topicQuery.setTopicId(topicId);
        Topic t = this.topicMapper.selectList(topicQuery).get(0);
        return JSON.toJSONString(t);
    }


    @RequestMapping("/topicServer/delTopic")
    @ResponseBody
    public  void delTopic( Integer topicId){
        topicMapper.delete(new Integer[]{topicId});
    }


    @RequestMapping("/refreshCategoryCache")
    public void refreshCategoryCache(){
        categoryCache.refreshCategoryCache();
    }
}

