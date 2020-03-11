package com.zk.po.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zk.po.enums.DateTimePatternEnum;
import com.zk.po.enums.MessageStatus;
import com.zk.po.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private Integer id;

    private Integer receivedUserId;
    
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date createTime;
    
    private String createTimeString;
    
    private MessageStatus status;

    private String description;

    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReceivedUserId() {
        return receivedUserId;
    }

    public void setReceivedUserId(Integer receivedUserId) {
        this.receivedUserId = receivedUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public MessageStatus getStatus() {
		return status;
	}

	public void setStatus(MessageStatus status) {
		this.status = status;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
    
    public String getCreateTimeString() {
    	SimpleDateFormat sdf = new SimpleDateFormat(DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern());
		return DateUtil.friendly_time(sdf.format(createTime));	
	}

	public void setCreateTimeString(String createTimeString) {
		this.createTimeString = createTimeString;
	}
}