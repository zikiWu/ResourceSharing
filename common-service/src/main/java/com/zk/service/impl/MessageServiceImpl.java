package com.zk.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.zk.exception.BussinessException;
import com.zk.mapper.MessageMapper;
import com.zk.po.enums.ArticleType;
import com.zk.po.enums.MessageType;
import com.zk.po.enums.PageSize;
import com.zk.po.model.Files;
import com.zk.po.model.Message;
import com.zk.po.model.MessageParams;
import com.zk.po.query.MessageQuery;
import com.zk.po.utils.Constants;
import com.zk.po.utils.Page;
import com.zk.po.utils.PageResult;
import com.zk.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageMapper<Message, MessageQuery> messageMapper;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void createMessage(MessageParams messageParams) {
		MessageType type = messageParams.getMessageType();
		switch (type) {
			case AT_ARTICLE_MESSAGE:
				atMessage(messageParams);
				break;
				//评论
			case COMMENT_MESSAGE:
				commentMessage(messageParams);
				break;
			default:
				break;
		}
	}

	@Override
	public Message getMessageById(Integer id, Integer userId) {

		MessageQuery messageQuery = new MessageQuery();
		messageQuery.setReceivedUserId(userId);
		messageQuery.setId(id);
		List<Message> list = this.messageMapper.selectList(messageQuery);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}

	@Override
	public PageResult<Message> findMessageByPage(MessageQuery messageQuery) {
		int pageSize = PageSize.PAGE_SIZE20.getSize();
		int count = findMessageCount(messageQuery);
		int pageNum = messageQuery.getPageNum() == 1 ? 1 : messageQuery.getPageNum();
		Page page = new Page(pageNum, count, pageSize);
		messageQuery.setPage(page);
		List<Message> list = this.messageMapper.selectList(messageQuery);
		return new PageResult<Message>(page, list);
	}

	@Override
	public int findMessageCount(MessageQuery messageQuery) {
		return this.messageMapper.selectCount(messageQuery).intValue();
	}

	@Override
	public void update(Integer[] ids, Integer userId) throws BussinessException {
		if(ids == null || userId == null || ids.length == 0){
			throw new BussinessException("参数错误");
		}
		this.messageMapper.update(userId, ids);
	}

	@Override
	public void delMessage(Integer userId, Integer[] ids) throws BussinessException {
		if(ids == null || userId == null || ids.length == 0){
			throw new BussinessException("参数错误");
		}
		this.messageMapper.delete(userId, ids);
	}

	private void removeUser(Set<Integer> receiveUserIds, Integer sendUserId) {
		Iterator<Integer> iterator = receiveUserIds.iterator();
		while(iterator.hasNext()){
			if(iterator.next().intValue() == sendUserId){
				iterator.remove();
			}
		}
	}
	private String getUrl4CommentAndAt(MessageParams params){
		String location = "";
		if(params.getPageNum() != null){
			location = "#" + params.getPageNum()+ "_" + params.getCommentId();
		}
		switch (params.getArticleType()) {
			case TOPIC:
				return Constants.DOMAIN + "/bbs/" + params.getArticleId() + location;
			case FILE:
				return  "http://localhost:4448/filecontent?id=" + params.getArticleId() ;
		}
		return null;
	}
	/**@人发送消息
	* @author wzk
	* @date 2019/3/19
	* @param
	*/
	private void atMessage(MessageParams messageParams) {
		List<Message> messageList = new ArrayList<Message>();
		Set<Integer> receiveUserIds = messageParams.getReceiveUserIds();
		Message message = null;
		removeUser(receiveUserIds, messageParams.getSendUserId());
		Date curDate = new Date();
		for(Integer receivedUserId : receiveUserIds){
			message = new Message();
			message.setReceivedUserId(receivedUserId);
			message.setUrl(getUrl4CommentAndAt(messageParams));
			message.setDescription("<span>" + messageParams.getSendUserName() + "</span>" +
					"在【" + messageParams.getArticleType().getDesc() + "】" + "中提到了你");
			message.setCreateTime(curDate);
			messageList.add(message);
		}
		if(!messageList.isEmpty()){
			this.messageMapper.insertBatch(messageList);
		}
	}
	/**提交评论发送消息
	* @author wzk
	* @date 2019/3/19
	* @param
	*/
	private void commentMessage(MessageParams messageParams) {
		List<Message> messageList = new ArrayList<Message>();
		Set<Integer> receiveUserIds = messageParams.getReceiveUserIds();
		ArticleType articleType = messageParams.getArticleType();
		Integer articleId = messageParams.getArticleId();
		String title = "";
		Integer articleUserId = null;

		if(articleType == ArticleType.TOPIC){

			MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
			requestMap.add("topicId", articleId);
			String topicStr = restTemplate.postForObject("http://BBS-SERVICE/getTopicById",requestMap, String.class);
			com.zk.po.model.Topic topic = JSON.parseObject(topicStr, com.zk.po.model.Topic.class);
			title = topic.getTitle();

		}else if(articleType == ArticleType.FILE){
			MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
			requestMap.add("id", articleId);
			String topicStr = restTemplate.postForObject("http://FILE-SERVICE/FileService/getFileById",requestMap, String.class);
			Files files = JSON.parseObject(topicStr, Files.class);
			title = files.getTitle();
			messageParams.setArticleUserId(files.getUserId());
			receiveUserIds.add(files.getUserId());
		}

		Message message = null;
//		removeUser(receiveUserIds, messageParams.getSendUserId());
		Date curDate = new Date();
		for(Integer receivedUserId : receiveUserIds){
			message = new Message();
			message.setReceivedUserId(receivedUserId);
			message.setUrl(getUrl4CommentAndAt(messageParams));
			message.setDescription("<span>" + messageParams.getSendUserName() + "</span>" +
					"在【" + messageParams.getArticleType().getDesc() + "】" + "&nbsp;&nbsp;("  + title + ")中回复了你");
			message.setCreateTime(curDate);
			messageList.add(message);
		}
		if(!messageList.isEmpty()){
			this.messageMapper.insertBatch(messageList);
		}
	}

}
