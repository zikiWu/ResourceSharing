package com.zk.po.query;


import com.zk.po.utils.Page;

public class BaseQuery {
	private Page page;
	private int pageNum = 1;
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	
}
