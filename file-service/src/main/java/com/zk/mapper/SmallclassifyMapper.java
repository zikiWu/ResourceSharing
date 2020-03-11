package com.zk.mapper;

import com.zk.po.model.Smallclassify;

import java.util.List;

public interface SmallclassifyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Smallclassify record);

    int insertSelective(Smallclassify record);

    Smallclassify selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Smallclassify record);

    int updateByPrimaryKey(Smallclassify record);

    List<Smallclassify> selectList();

    Smallclassify selectByName(String name);
}