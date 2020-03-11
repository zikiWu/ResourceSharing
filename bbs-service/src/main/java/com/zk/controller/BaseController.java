package com.zk.controller;



import com.zk.po.model.SessionUser;
import com.zk.po.utils.Constants;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;


public class BaseController {
	
	public void setUserBaseInfo(Class<?> clazz, Object obj, SessionUser sessionUser){
		Integer userId = sessionUser.getUserid();
		String userName = sessionUser.getUserName();
		String userIcon = sessionUser.getUserIcon();
		try {
			Method UserIdMethod = clazz.getDeclaredMethod("setUserId", Integer.class);
			UserIdMethod.invoke(obj, userId);
			Method UserNameMethod = clazz.getDeclaredMethod("setUserName", String.class);
			UserNameMethod.invoke(obj, userName);
			Method UserIconMethod = clazz.getDeclaredMethod("setUserIcon", String.class);
			UserIconMethod.invoke(obj, userIcon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	public Integer getUserid(HttpSession session){

		return null;
	}
}
