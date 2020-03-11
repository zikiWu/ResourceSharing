package com.zk.service;

import com.zk.exception.BussinessException;
import com.zk.po.model.Attachment;
import com.zk.po.model.SessionUser;
import com.zk.po.model.Topic;
import com.zk.po.model.TopicVote;
import com.zk.po.query.TopicQuery;
import com.zk.po.utils.PageResult;
import java.util.List;

public interface TopicService {
	
	public PageResult<Topic> findTopicByPage(TopicQuery topicQuery);
	
	
	public void addTopic(Topic topic, TopicVote topicVote, String[] voteTitle, Attachment file ,SessionUser user)throws BussinessException;
	
	
	public Topic showTopic(Integer topicId) throws BussinessException;
	
	
	public Topic getTopic(Integer topicId);
	
	public List<Topic> findActiveUsers();
	
	public Integer findCount(TopicQuery topicQuery);
	
	public List<Topic> findTopicList();
	
	public void updateTopicEssence(Integer[] topicId, int essence)throws BussinessException;
	
	public void updateTopicStick(Integer[] topicId, int stick)throws BussinessException;
	
	public void deleteBatch(Integer[] topicIds) throws BussinessException;
	
}
