package com.zk.mapper;

import com.zk.po.model.Files;

import java.util.List;

public interface FilesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Files record);

    int insertSelective(Files record);

    Files selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Files record);

    int updateByPrimaryKey(Files record);

     List<Files> selectList();
}