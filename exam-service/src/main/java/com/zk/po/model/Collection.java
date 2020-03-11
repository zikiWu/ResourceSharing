package com.zk.po.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zk.po.enums.ArticleType;
import com.zk.po.enums.DateTimePatternEnum;
import com.zk.po.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Collection{
	private Integer articleId;

    private ArticleType articleType;

    private Integer userId;
    
    private Integer articleUserId;
	
    private String title;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date createTime;
    
    private String createTimeString;

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public ArticleType getArticleType() {
		return articleType;
	}

	public void setArticleType(ArticleType articleType) {
		this.articleType = articleType;
	}
	

	public Integer getArticleUserId() {
		return articleUserId;
	}

	public void setArticleUserId(Integer articleUserId) {
		this.articleUserId = articleUserId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getCreateTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat(DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern());
		return DateUtil.friendly_time(sdf.format(createTime));
	}

	public void setCreateTimeString(String createTimeString) {
		this.createTimeString = createTimeString;
	}

	


}