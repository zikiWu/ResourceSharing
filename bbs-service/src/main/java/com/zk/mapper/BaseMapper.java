package com.zk.mapper;

import java.io.Serializable;
import java.util.List;

public interface BaseMapper<T, Q> extends Serializable {
	public void insert(T t);
	
	public List<T> selectList(Q q);
	
	public Integer selectCount(Q q);
	
	public void update(T t);

	public void delete(T t);	
}
