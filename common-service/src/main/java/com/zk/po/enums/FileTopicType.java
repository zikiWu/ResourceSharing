package com.zk.po.enums;

import com.zk.po.utils.StringUtils;


public enum FileTopicType {
	TOPIC("T", "论坛附件"), KNOWLEDGE("K", "论坛附件"),  BLOG("B", "博客附件");
	private String type;
	private String desc;

	private FileTopicType(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static FileTopicType getFileTopicTypeByType(String type){
		if(StringUtils.isEmpty(type)){
			return null;
		}
		for(FileTopicType fileTopicType : FileTopicType.values()){
			if(fileTopicType.getType().equals(type)){
				return fileTopicType;
			}
		}
		return null;
	}
}
