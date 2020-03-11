package com.zk.service;

import com.zk.exception.BussinessException;
import com.zk.po.model.Comment;
import com.zk.po.query.CommentQuery;
import com.zk.po.utils.PageResult;

public interface CommentService {
	
	public PageResult<Comment> findCommentByPage(CommentQuery commentQuery);
	
	public Comment getCommentById(Integer commentId);
	
	public void addComment(Comment comment) throws BussinessException;
	
}