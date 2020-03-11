package com.zk.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zk.exception.BussinessException;
import com.zk.mapper.TopicMapper;
import com.zk.po.enums.*;
import com.zk.po.model.*;
import com.zk.po.query.TopicQuery;
import com.zk.po.query.UpdateQuery4ArticleCount;
import com.zk.po.utils.Constants;
import com.zk.po.utils.Page;
import com.zk.po.utils.PageResult;
import com.zk.po.utils.StringUtils;
import com.zk.service.AttachmentService;
import com.zk.service.TopicService;
import com.zk.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TopicServiceImpl implements TopicService {

//
	@Autowired
	private AttachmentService attachmentService;
//
//	@Autowired
//	private MessageService messageService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private TopicMapper<Topic, TopicQuery> topicMapper;

	@Override
	public PageResult<Topic> findTopicByPage(TopicQuery topicQuery) {
	 	int count = topicMapper.selectCount(topicQuery);
		int pageSize = PageSize.PAGE_SIZE20.getSize();
		int pageNum = 1;
		if (topicQuery.getPageNum() != 1) {
			pageNum = topicQuery.getPageNum();
		}
		Page page = new Page(pageNum, count, pageSize);
		topicQuery.setPage(page);
		topicQuery.setOrderBy(OrderByEnum.LAST_COMMENT_TIME_DESC_CREATE_TIME_DESC);
		List<Topic> list = this.topicMapper.selectList(topicQuery);
		PageResult<Topic> pageResult = new PageResult<Topic>(page, list);
		return pageResult;
	}



	@Override
	public void addTopic(Topic topic, TopicVote topicVote, String[] voteTitle, Attachment file,SessionUser user) throws BussinessException {
		if (topic.getTopicType() == null
				|| topic.getTitle() == null
				|| topic.getTitle().length() > TextLengthEnum.TEXT_200_LENGTH
				.getLength()
				|| topic.getCategoryId() == null
				|| StringUtils.isEmpty(topic.getContent())
				|| topic.getContent().length() > TextLengthEnum.LONGTEXT
				.getLength()) {
			throw new BussinessException("参数错误");
		}
		String title = topic.getTitle();
		topic.setTitle(HtmlUtils.htmlEscape(title));
		String content = topic.getContent();
		String summary = StringUtils.cleanHtmlTag(HtmlUtils.htmlUnescape(content));
		if (summary.length() > TextLengthEnum.TEXT_200_LENGTH.getLength()) {
			summary = summary.substring(0,
					(int) TextLengthEnum.TEXT_200_LENGTH.getLength())
					+ "......";
		}
		Set<Integer> userIds = new HashSet<Integer>();


		String formatContent = generateRefererLinks(content,userIds,user);


		topic.setUserId(user.getUserid());
		topic.setUserName(user.getUserName());
		topic.setUserIcon(user.getUserIcon());
		topic.setSummary(summary);
		topic.setContent(formatContent);
		String topicImage = ImageUtils.getImages(content);
		topic.setTopicImage(topicImage);
		String topicImageSmall = ImageUtils.createThumbnail(topicImage, true);
		topic.setTopicImageThum(topicImageSmall);
		Date curDate = new Date();
		topic.setCreateTime(curDate);
		topic.setLastCommentTime(curDate);
		this.topicMapper.insert(topic);


		//数量



		// TODO 给用户发消息
	}

	@Override
	public Topic showTopic(Integer topicId) throws BussinessException {
		Topic topic = getTopic(topicId);
		if(topic == null){
			throw new BussinessException("帖子不存在或已删除");
		}
		//附件
		topic.setAttachment(this.attachmentService.getAttachmentByTopicIdAndFileType(topic.getTopicId(), FileTopicType.TOPIC));
		//观看人数
		UpdateQuery4ArticleCount updateQuery4ArticleCount = new UpdateQuery4ArticleCount();
		updateQuery4ArticleCount.setAddReadCount(Boolean.TRUE);
		updateQuery4ArticleCount.setArticleId(topicId);
		this.topicMapper.updateInfoCount(updateQuery4ArticleCount);
		return topic;
	}



	@Override
	public Topic getTopic(Integer topicId) {
		if(topicId == null){
			return null;
		}
		TopicQuery topicQuery = new TopicQuery();
		topicQuery.setShowContent(Boolean.TRUE);
		topicQuery.setTopicId(topicId);
		List<Topic> list = this.topicMapper.selectList(topicQuery);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<Topic> findActiveUsers() {
		return this.topicMapper.selectActiveUser4Topic();
	}

	@Override
	public Integer findCount(TopicQuery topicQuery) {
		return this.topicMapper.selectCount(topicQuery);
	}

	@Override
	public List<Topic> findTopicList() {
		TopicQuery topicQuery = new TopicQuery();
		topicQuery.setOrderBy(OrderByEnum.LAST_COMMENT_TIME_DESC_CREATE_TIME_DESC);
		List<Topic> topics = topicMapper.selectList(topicQuery);
		return topics;
	}

	@Override
	public void updateTopicEssence(Integer[] topicId, int essence) throws BussinessException {

	}

	@Override
	public void updateTopicStick(Integer[] topicId, int stick) throws BussinessException {
		if(topicId == null){
			throw new BussinessException("参数错误");
		}

		for(int id : topicId){
			Topic topic = new Topic();
			topic.setTopicId(id);
			topic.setGrade(stick);
			topicMapper.update(topic);
		}
	}

	@Override
	public void deleteBatch(Integer[] topicIds) throws BussinessException {

	}


	public String generateRefererLinks(String msg, Set<Integer> userIds,SessionUser user){
		StringBuilder html = new StringBuilder();
		int lastIdx = 0;
		Matcher matcher = referer_pattern.matcher(msg);
		while (matcher.find()) {
			String origion_str = matcher.group();
			String userName = origion_str.substring(1, origion_str.length()).trim();
			html.append(msg.substring(lastIdx, matcher.start()));
			if(null != user){
				html.append("<a href=\"" + userUrl + user.getUserid() + "\"  class=\"referer\"  target=\"_blank\">@");
				html.append(userName.trim());
				html.append("</a> ");
				userIds.add(user.getUserid());
			}
			else{
				html.append(origion_str);
			}
			lastIdx = matcher.end();
		}
		html.append(msg.substring(lastIdx));
		return html.toString();
	}
	private static String userUrl = Constants.DOMAIN + "/common/";

	private static Pattern referer_pattern = Pattern.compile("@([^@\\s\\:\\;\\,\\\\.\\<\\?\\？\\{\\}\\&]{1,})");
}
