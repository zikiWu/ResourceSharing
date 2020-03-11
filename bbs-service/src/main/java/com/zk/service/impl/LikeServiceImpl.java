package com.zk.service.impl;

import com.zk.po.utils.PageResult;
import com.zk.po.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.zk.exception.BussinessException;
import com.zk.mapper.*;
import com.zk.po.enums.ArticleType;
import com.zk.po.enums.TextLengthEnum;
import com.zk.po.model.*;
import com.zk.po.query.*;
import com.zk.service.LikeService;
import java.util.Date;
import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {
	
	@Autowired
	private LikeMapper<Like, LikeQuery> likeMapper;

	@Autowired
	private TopicMapper<Topic, TopicQuery> topicMapper;
	

	
	@Autowired
	private AskMapper<Ask, AskQuery> askMapper;
	

	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor=BussinessException.class)
	public void addLike(Like like) throws BussinessException {
		if(like.getArticleId() == null || like.getArticleType() == null
				|| StringUtils.isEmpty(like.getTitle()) ||
				like.getTitle().length() > TextLengthEnum.TEXT_300_LENGTH.getLength()){
			throw new BussinessException("参数错误");
		}

		LikeQuery likeQuery = new LikeQuery(like.getArticleId(),
				like.getArticleType(), like.getUserId());
		Like l = this.findLikeByKey(likeQuery);
		if(l != null){
			throw new BussinessException("您已经赞过了");
		}
		like.setCreateTime(new Date());
		this.likeMapper.insert(like);

		UpdateQuery4ArticleCount updateQuery4ArticleCount = new UpdateQuery4ArticleCount();
		updateQuery4ArticleCount.setAddLikeCount(Boolean.TRUE);
		updateQuery4ArticleCount.setArticleId(like.getArticleId());
		if(like.getArticleType() == ArticleType.TOPIC){
			this.topicMapper.updateInfoCount(updateQuery4ArticleCount);
		}
		else if(like.getArticleType() == ArticleType.Ask){
			this.askMapper.updateInfoCount(updateQuery4ArticleCount);
		}
		else{
			throw new BussinessException("参数错误");
		}
	}

	public Like findLikeByKey(LikeQuery likeQuery) {
		List<Like> likes = this.likeMapper.selectList(likeQuery);
		if(likes.isEmpty()){
			return null;
		}
		return likes.get(0);
	}

	public PageResult<Like> findLikeByPage(LikeQuery likeQuery) {
		// TODO Auto-generated method stub
		return null;
	}

}
