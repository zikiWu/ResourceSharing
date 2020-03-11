package com.zk.service;

import com.zk.exception.BussinessException;
import com.zk.po.enums.FileTopicType;
import com.zk.po.model.Attachment;
import com.zk.po.model.SessionUser;

public interface AttachmentService {
	
	public void addAttachment(Attachment attachment) throws BussinessException;
	
	public Attachment getAttachmentByTopicIdAndFileType(Integer topicId, FileTopicType fileTopicType);
	
	public Attachment getAttachmentById(Integer attachmentId);
	
	public Attachment downloadAttachment(SessionUser sessionUser, Integer attachmentId) throws BussinessException;
	
	public void checkDownloadPermission(Integer topicUserId, Integer userId, Integer downloadMark,
                                        Integer attachmentId) throws BussinessException;
	
	public void checkDownload(Integer attachmentId, Integer topicId, SessionUser sessionUser) throws BussinessException;
	
	public void deleteFile(Integer attachmentId);
	
}
