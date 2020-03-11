package com.zk.service;

import com.zk.exception.BussinessException;
import com.zk.po.model.UserFriend;
import com.zk.po.query.UserFriendQuery;
import com.zk.po.utils.PageResult;

public interface UserFriendService {
	/**
	 * 
	 * @param userFriendQuery
	 * @return关注的用户
	 */
	public PageResult<UserFriend> findFriendList(UserFriendQuery userFriendQuery);
	
	public int findCount(UserFriendQuery userFriendQuery);
	
	public PageResult<UserFriend> findFansList(UserFriendQuery userFriendQuery);
	
	public void addFocus(UserFriend userFriend) throws BussinessException;
	
	public void cancelFocus(UserFriend userFriend) throws BussinessException;
	
	public int findFocusType4UserHome(UserFriendQuery userFriendQuery);
}