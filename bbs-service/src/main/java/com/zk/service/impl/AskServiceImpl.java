package com.zk.service.impl;

import com.zk.po.utils.Page;
import com.zk.po.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;
import com.zk.exception.BussinessException;
import com.zk.mapper.AskMapper;
import com.zk.po.enums.*;
import com.zk.po.model.Ask;
import com.zk.po.model.Comment;
import com.zk.po.model.MessageParams;
import com.zk.po.model.User;
import com.zk.po.query.AskQuery;
import com.zk.po.query.UpdateQuery4ArticleCount;
import com.zk.service.AskService;
import com.zk.utils.ImageUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AskServiceImpl implements AskService {
	
	@Autowired
	private AskMapper<Ask, AskQuery> askMapper;
	
	public PageResult<Ask> findAskByPage(AskQuery askQuery) {
		int count = this.findCount(askQuery);
		int pageNum = askQuery.getPageNum() == 1 ? 1 : askQuery.getPageNum();
		int pageSize = PageSize.PAGE_SIZE20.getSize();
		askQuery.setOrderBy(OrderByEnum.CREATE_TIME_DESC);
		Page page = new Page(pageNum, count, pageSize);
		List<Ask> list = this.askMapper.selectList(askQuery);
		return new PageResult<Ask>(page, list);
	}

	public int findCount(AskQuery askQuery) {
		return this.askMapper.selectCount(askQuery);
	}

	@Override
	public void addAsk(Ask ask) throws BussinessException {

	}

	@Override
	public void setBestAnswer(Integer bestAnswerId, Integer askId, Integer userId) throws BussinessException {

	}

	@Override
	public Ask getAskById(Integer askId) {
		return null;
	}

	@Override
	public Ask showAsk(Integer askId) throws BussinessException {
		return null;
	}

	@Override
	public List<Ask> findTopUsers() {
		return null;
	}

	@Override
	public List<Ask> findAskList() {
		return null;
	}

	@Override
	public void deleteBatch(Integer[] ids) throws BussinessException {

	}


}
