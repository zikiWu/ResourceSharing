package com.zk.mapper;

import com.zk.po.model.Bigclassify;
import com.zk.po.model.Files;

import java.util.List;

public interface BigclassifyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bigclassify record);

    int insertSelective(Bigclassify record);

    Bigclassify selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bigclassify record);


    int updateByPrimaryKey(Bigclassify record);

    List<Bigclassify> selectList();


    Bigclassify selectBy(Integer id);

    Bigclassify selectByName(String name);
}