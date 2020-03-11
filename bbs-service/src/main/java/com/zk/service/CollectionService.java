package com.zk.service;

import com.zk.exception.BussinessException;
import com.zk.po.model.Collection;
import com.zk.po.query.CollectionQuery;
import com.zk.po.utils.PageResult;

public interface CollectionService {
	
	public void addCollection(Collection collection) throws BussinessException;
	
	public Collection findCollectionByKey(CollectionQuery collectionQuery);
	
	public PageResult<Collection> findCollectionByPage(CollectionQuery collectionQuery);
	
	public void deleteCollection(Collection collection);
}