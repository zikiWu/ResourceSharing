package com.zk.service.impl;

import com.zk.exception.BussinessException;
import com.zk.mapper.CollectionMapper;
import com.zk.po.enums.ArticleType;
import com.zk.po.enums.PageSize;
import com.zk.po.enums.TextLengthEnum;
import com.zk.po.model.Ask;
import com.zk.po.model.Blog;
import com.zk.po.model.Collection;
import com.zk.po.query.CollectionQuery;
import com.zk.po.utils.Page;
import com.zk.po.utils.PageResult;
import com.zk.po.utils.StringUtils;
import com.zk.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {

	@Autowired
	private CollectionMapper<Collection, CollectionQuery> collectionMapper;

	@Autowired
	private RestTemplate restTemplate;

	
	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor= BussinessException.class)
	public void addCollection(Collection collection)
			throws BussinessException {
		if(collection.getArticleId() == null || collection.getArticleType() == null 
				|| StringUtils.isEmpty(collection.getTitle()) ||
				collection.getTitle().length() > TextLengthEnum.TEXT_300_LENGTH.getLength()){
			throw new BussinessException("参数错误");
		}
		
		CollectionQuery collectionQuery = new CollectionQuery(collection.getArticleId(), 
				collection.getArticleType(), collection.getUserId());
		Collection c = this.findCollectionByKey(collectionQuery);
		if(c != null){
			throw new BussinessException("您已经收藏过了");
		}
		collection.setCreateTime(new Date());
		this.collectionMapper.insert(collection);
		Integer articleId = collection.getArticleId();
		if(collection.getArticleType() == ArticleType.TOPIC){
			//更新帖子收藏
			MultiValueMap<String, Object> requestMap1 = new LinkedMultiValueMap<>();
			requestMap1.add("topicId", articleId);
			//更新帖子评论数
			restTemplate.postForObject("http://BBS-SERVICE/updateInfoCollCount",requestMap1, String.class);

		}
		else{
			throw new BussinessException("参数错误");
		}
	}

	public Collection findCollectionByKey(CollectionQuery collectionQuery) {
		List<Collection> collectionList = this.collectionMapper.selectList(collectionQuery);
		if(collectionList.isEmpty()){
			return null;
		}
		return collectionList.get(0);
	}

	public PageResult<Collection> findCollectionByPage(
			CollectionQuery collectionQuery) {
		int count = this.collectionMapper.selectCount(collectionQuery);
		int pageSize = PageSize.PAGE_SIZE20.getSize();
		int pageNum = collectionQuery.getPageNum() == 1? 1:collectionQuery.getPageNum();
		Page page = new Page(pageNum, count, pageSize);
		collectionQuery.setPage(page);
		List<Collection> list = this.collectionMapper.selectList(collectionQuery);
		return new PageResult<Collection>(page, list);
	}

	public void deleteCollection(Collection collection) {
		this.collectionMapper.delete(collection);
	}

}