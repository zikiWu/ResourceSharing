package com.zk.mapper;

import com.zk.po.query.UpdateQuery4ArticleCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AskMapper<T, Q> extends BaseMapper<T, Q> {
	public void updateInfoCount(UpdateQuery4ArticleCount updateQuery4ArticleCount);
	
	public List<T> selectTopUser(Q q);
	
	public void updateBestAnswer(T t);
	
	public void delete(@Param("askId") Integer askId);
}