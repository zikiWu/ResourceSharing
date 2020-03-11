package com.zk.service.impl;

import com.zk.exception.BussinessException;
import com.zk.mapper.CommentMapper;
import com.zk.po.enums.*;
import com.zk.po.model.*;
import com.zk.po.query.CommentQuery;
import com.zk.po.query.UserFriendQuery;
import com.zk.po.utils.Constants;
import com.zk.po.utils.Page;
import com.zk.po.utils.PageResult;
import com.zk.po.utils.StringUtils;
import com.zk.po.vo.AjaxResponse;
import com.zk.service.CommentService;
import com.zk.service.FormateAtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {


	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private FormateAtService formateAtService;

	@Autowired
	private MessageServiceImpl messageService;

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public PageResult<Comment> findCommentByPage(CommentQuery commentQuery) {
		commentQuery.setPid(0);
		int pageNum = commentQuery.getPageNum() == 1 ? 1 : commentQuery.getPageNum();
		int pageSize = PageSize.PAGE_SIZE10.getSize();
		int count = commentMapper.selectCount(commentQuery);
		Page page = new Page(pageNum, count, pageSize);
		commentQuery.setPage(page);
		List<Comment> list = this.commentMapper.selectList(commentQuery);
		return new PageResult<Comment>(page, list);
	}

	@Override
	public Comment getCommentById(Integer commentId) {

		CommentQuery commentQuery = new CommentQuery();
		commentQuery.setCommentId(commentId);
		List<Comment> list = this.commentMapper.selectList(commentQuery);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void addComment(Comment comment) throws BussinessException {
		String content = comment.getContent();
		content = StringUtils.replaceLast(content.replaceFirst("<p>", ""), "</p>", "");
		if(StringUtils.isEmpty(content) || content.length() > TextLengthEnum.LONGTEXT.getLength()
				|| comment.getArticleId() == null || comment.getArticleType() == null){
			throw new BussinessException("参数错误");
		}
		Integer pid = comment.getPid();
		pid = pid == null ? 0 : pid;
		if(pid!=0){
			content = StringUtils.addLink(content);//给网页加链接
			content = HtmlUtils.htmlEscape(content);
		}
		if(pid != 0 && content.length() > TextLengthEnum.TEXT_500_LENGTH.getLength()){
			throw new BussinessException("参数错误");
		}

		Set<Integer> userIds = new HashSet<Integer>();
		String formatContent = this.formateAtService.generateRefererLinks(content, userIds);
		//TODO给用户发消息
		comment.setContent(formatContent);
		comment.setCreateTime(new Date());
		this.commentMapper.insert(comment);
		Integer articleUserId = null;
		if(comment.getArticleType() == ArticleType.TOPIC){

            MultiValueMap<String, Object> requestMap1 = new LinkedMultiValueMap<>();
            requestMap1.add("topicId", comment.getArticleId());
			//更新帖子评论数
			restTemplate.postForObject("http://BBS-SERVICE/updateInfoCommentCount",requestMap1, String.class);

            MultiValueMap<String, Object> requestMap2 = new LinkedMultiValueMap<>();
            requestMap2.add("articleId", comment.getArticleId());
            //帖子创建者
			articleUserId = restTemplate.postForObject("http://BBS-SERVICE/getTopic",requestMap2, Integer.class);
		}
		else if(comment.getArticleType() == ArticleType.FILE){
			//更新资源评论数
            MultiValueMap<String, Object> requestMap1 = new LinkedMultiValueMap<>();
            requestMap1.add("id", comment.getArticleId());
            restTemplate.postForObject("http://FILE-SERVICE/FileService/updateFileCommentCount",requestMap1, String.class);

//
//            MultiValueMap<String, Object> requestMap2 = new LinkedMultiValueMap<>();
//            requestMap2.add("articleId", comment.getArticleId());
//            //资源创建者
//            articleUserId = restTemplate.postForObject("http://BBS-SERVICE/getTopic",requestMap2, Integer.class);


		}
		else{
			throw new BussinessException("参数错误");
		}
		this.userService.changeMark(comment.getUserId(), MarkEnum.MARK_COMMENT.getMark());
		if(pid == 0){
			userIds.add(articleUserId);
		}
		else{
			Comment comment2 = this.getCommentById(pid);
			userIds.add(comment2.getUserId());
		}
		MessageParams messageParams = new MessageParams();
		messageParams.setArticleId(comment.getArticleId());
		messageParams.setArticleType(comment.getArticleType());
		messageParams.setArticleUserId(articleUserId);
		messageParams.setMessageType(MessageType.COMMENT_MESSAGE);
		messageParams.setSendUserName(comment.getUserName());
		messageParams.setSendUserId(comment.getUserId());
		messageParams.setReceiveUserIds(userIds);
		messageParams.setCommentId(comment.getId());
		messageParams.setPageNum(comment.getPageNum());
		messageService.createMessage(messageParams);
	}


}