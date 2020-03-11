package com.zk.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageMapper<T, Q> extends BaseMapper<T, Q> {
	public void insertBatch(@Param("list") List<T> list);
	
	public void delete(@Param("userId") Integer userId, @Param("ids") Integer[] ids);
	
	public void update(@Param("userId") Integer userId, @Param("ids") Integer[] ids);
}