package com.zk.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper<T, Q> extends BaseMapper<T, Q> {
   public List<T> selectChildren(@Param("id") Integer id);
}