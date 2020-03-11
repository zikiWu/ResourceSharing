package com.zk.controller;

import com.alibaba.fastjson.JSON;
import com.zk.mapper.BigclassifyMapper;
import com.zk.mapper.FilesMapper;
import com.zk.mapper.SmallclassifyMapper;
import com.zk.po.model.Bigclassify;
import com.zk.po.model.Files;
import com.zk.po.model.FirstCate;
import com.zk.po.model.Smallclassify;
import com.zk.po.utils.LayUiListResult;
import com.zk.po.vo.FileCategoryVO;
import com.zk.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class UtilControl {
    @Autowired
    private FilesMapper filesMapper;

    @Autowired
    private BigclassifyMapper bigclassifyMapper;

    @Autowired
    private SmallclassifyMapper smallclassifyMapper;


    @ResponseBody
    @RequestMapping("FileService/getFileList")
    public LayUiListResult<Files> getList(  Integer page, Integer limit){
        if (page == null){
            page = 1;
            limit = 10;
        }
        List<Files> files = filesMapper.selectList();
        files.stream().forEach(o->{
            o.setTitle(o.getTitle().substring(13));
        });
        LayUiListResult<Files> layUiListResult = new LayUiListResult<>(0,"",files.size(),page,limit);
        layUiListResult.setData(files);
        return layUiListResult;
    }

    @ResponseBody
    @RequestMapping("FileService/delFile")
    public void delFile(Integer id){
        filesMapper.deleteByPrimaryKey(id);
    }

    @ResponseBody
    @RequestMapping("FileService/getFirstCate")
    public LayUiListResult<FirstCate> getFirstCate(Integer page, Integer limit){
        if (page == null){
            page = 1;
            limit = 10;
        }
        List<FirstCate> cates = new ArrayList<>();

        FirstCate f;
        String[] cateNames = {"考试资料","作业课件","IT数码","论文读物","影音视频","创意图库"};
        for (int i = 1;i<6;i++){
            f = new FirstCate();
            f.setId(i);
            f.setName(cateNames[i-1]);
            cates.add(f);
        }
        LayUiListResult<FirstCate> layUiListResult = new LayUiListResult<>(0,"",cates.size(),page,limit);
        layUiListResult.setData(cates);
        return layUiListResult;
    }

    @ResponseBody
    @RequestMapping("FileService/getSecondCate")
    public LayUiListResult<Bigclassify> getSecondCate(Integer page, Integer limit){

        if (page == null){
            page = 1;
            limit = 10;
        }
        List<Bigclassify> cates = bigclassifyMapper.selectList();


        LayUiListResult<Bigclassify> layUiListResult = new LayUiListResult<>(0,"",cates.size(),page,limit);
        layUiListResult.setData(cates);
        return layUiListResult;
    }


    @ResponseBody
    @RequestMapping("FileService/getThirdCate")
    public LayUiListResult<FileCategoryVO> getThirdCate(Integer page, Integer limit){

        if (page == null){
            page = 1;
            limit = 10;
        }
        List<Smallclassify> Scates = smallclassifyMapper.selectList();
        List<Bigclassify> Bcates = bigclassifyMapper.selectList();

        Map<Integer,Bigclassify> bigclassifyMap = Bcates.stream().collect(Collectors.toMap(Bigclassify::getId,b->b));
        List<FileCategoryVO> fileCategoryVOS = new ArrayList<>();

        for (Smallclassify s : Scates) {
            FileCategoryVO fileCategoryVO = new FileCategoryVO();
            fileCategoryVO.setId(s.getId());
            if (bigclassifyMap.containsKey(s.getBelong())) {

                fileCategoryVO.setFirstCategory(bigclassifyMap.get(s.getBelong()).getFirstCategory());
                fileCategoryVO.setSecondCategory(bigclassifyMap.get(s.getBelong()).getName());
                fileCategoryVO.setName(s.getName());
                fileCategoryVOS.add(fileCategoryVO);

            }
        }




        LayUiListResult<FileCategoryVO> layUiListResult = new LayUiListResult<>(0,"",fileCategoryVOS.size(),page,limit);
        layUiListResult.setData(fileCategoryVOS);
        return layUiListResult;
    }


    @ResponseBody
    @RequestMapping("FileService/delSecondCate")
    public void delSecondCate(Integer id){

        bigclassifyMapper.deleteByPrimaryKey(id);
    }
    @ResponseBody
    @RequestMapping("FileService/delThirdCate")
    public void delThirdCate(Integer id){

        smallclassifyMapper.deleteByPrimaryKey(id);
    }

    @ResponseBody
    @RequestMapping("FileService/addThirdCate")
    public void addThirdCate(String belong,String name){
        Smallclassify s = new Smallclassify();
        s.setBelong(
                bigclassifyMapper.selectByName(belong).getId());
        s.setName(name);
        smallclassifyMapper.insert(s);
    }

    @ResponseBody
    @RequestMapping("FileService/addSecondCate")
    public void addSecondCate(String name,String firstCategory,Integer state){
        Bigclassify b = new Bigclassify();

        b.setName(name);
        b.setState(state);
        b.setFirstCategory(firstCategory);
        bigclassifyMapper.insert(b);
    }

    @ResponseBody
    @RequestMapping("FileService/updateThirdCate")
    public void updateThirdCate(Integer id,String name){
        Smallclassify s = new Smallclassify();
        s.setId(id);
        s.setName(name);
        smallclassifyMapper.updateByPrimaryKeySelective(s);
    }

    @ResponseBody
    @RequestMapping("FileService/updateSecondCate")
    public void updateSecondCate(Integer id,String name){
        Bigclassify b = new Bigclassify();
        b.setId(id);
        b.setName(name);
        bigclassifyMapper.updateByPrimaryKeySelective(b);
    }
    @ResponseBody
    @RequestMapping("FileService/getFileById")
    public String updateSecondCate(Integer id){
        Files filedetail = filesMapper.selectByPrimaryKey(id);
        return JSON.toJSONString(filedetail);
    }
    /**更新评论数
    * @author wzk
    * @date 2019/5/8
    * @param
    */
    @ResponseBody
    @RequestMapping("FileService/updateFileCommentCount")
    public String updateFileCommentCount(Integer id){
        Files filedetail = filesMapper.selectByPrimaryKey(id);
        filedetail.setEvaluate(filedetail.getEvaluate()+1);
        filesMapper.updateByPrimaryKey(filedetail);
        return JSON.toJSONString(filedetail);
    }

}
