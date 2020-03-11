package com.zk.service;

import java.util.List;

import com.zk.exception.BussinessException;
import com.zk.po.model.Ask;
import com.zk.po.query.AskQuery;
import com.zk.po.utils.PageResult;

public interface AskService {
	
	public PageResult<Ask> findAskByPage(AskQuery askQuery);
	
	public int findCount(AskQuery askQuery);
	
	public void addAsk(Ask ask) throws BussinessException;
	
	public void setBestAnswer(Integer bestAnswerId, Integer askId, Integer userId) throws BussinessException;
	
	public Ask getAskById(Integer askId);
	
	public Ask showAsk(Integer askId) throws BussinessException;
	
	public List<Ask> findTopUsers();
	
	public List<Ask> findAskList();
	
	public void deleteBatch(Integer[] ids) throws BussinessException;
	
}
