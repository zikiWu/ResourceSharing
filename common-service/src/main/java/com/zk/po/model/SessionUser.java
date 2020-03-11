package com.zk.po.model;

import java.io.Serializable;

public class SessionUser implements Serializable {
	private Integer userid;
	private String userName;
	private String userIcon;

	public SessionUser() {
	}

	public SessionUser(Integer userid, String userName, String userIcon) {
		this.userid = userid;
		this.userName = userName;
		this.userIcon = userIcon;
	}

	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserIcon() {
		return userIcon;
	}
	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}
	
	
}
