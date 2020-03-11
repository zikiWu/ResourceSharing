package com.zk.service;

import com.zk.exception.BussinessException;
import com.zk.po.model.Message;
import com.zk.po.model.MessageParams;
import com.zk.po.query.MessageQuery;
import com.zk.po.utils.PageResult;
import org.springframework.scheduling.annotation.Async;

public interface MessageService {
	
	@Async
	public void createMessage(MessageParams messageParams);
	
	public Message getMessageById(Integer id, Integer userId);
	
	public PageResult<Message> findMessageByPage(MessageQuery messageQuery);
	
	public int findMessageCount(MessageQuery messageQuery);
	
	public void update(Integer[] ids, Integer userId)throws BussinessException;
	
	public void delMessage(Integer userId, Integer[] ids)throws BussinessException;
	
}