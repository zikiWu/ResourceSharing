package com.zk.mapper;

import com.zk.po.query.UpdateQuery4ArticleCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TopicMapper<T, Q> extends BaseMapper<T, Q> {
   public void updateInfoCount(UpdateQuery4ArticleCount updateQuery4ArticleCount);
   
   public List<T> selectActiveUser4Topic();
   
   public void delete(@Param("ids") Integer[] ids);

   public List<T> selectListByTitle(String keyWord);
}