package com.zk.service;

import com.zk.exception.BussinessException;
import com.zk.po.model.Like;
import com.zk.po.query.LikeQuery;
import com.zk.po.utils.PageResult;

public interface LikeService {
	
	public void addLike(Like like) throws BussinessException;
	
	public Like findLikeByKey(LikeQuery likeQuery);
	
	public PageResult<Like> findLikeByPage(LikeQuery likeQuery);
}