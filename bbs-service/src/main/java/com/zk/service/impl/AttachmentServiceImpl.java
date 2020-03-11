package com.zk.service.impl;

import com.zk.exception.BussinessException;
import com.zk.mapper.AttachmentMapper;
import com.zk.po.enums.FileTopicType;
import com.zk.po.model.Attachment;
import com.zk.po.model.SessionUser;
import com.zk.po.query.AttachmentQuery;
import com.zk.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AttachmentServiceImpl implements AttachmentService {

	@Autowired
	private AttachmentMapper attachmentMapper;

	@Override
	public void addAttachment(Attachment attachment) throws BussinessException {

	}

	@Override
	public Attachment getAttachmentByTopicIdAndFileType(Integer topicId, FileTopicType fileTopicType) {
		AttachmentQuery attachmentQuery = new AttachmentQuery();
		attachmentQuery.setTopicId(topicId);
		attachmentQuery.setFileTopicType(fileTopicType);
		List<Attachment> attachments = this.attachmentMapper.selectList(attachmentQuery);
		if(attachments.isEmpty()){
			return null;
		}
		return attachments.get(0);
	}

	@Override
	public Attachment getAttachmentById(Integer attachmentId) {
		return null;
	}

	@Override
	public Attachment downloadAttachment(SessionUser sessionUser, Integer attachmentId) throws BussinessException {
		return null;
	}

	@Override
	public void checkDownloadPermission(Integer topicUserId, Integer userId, Integer downloadMark, Integer attachmentId) throws BussinessException {

	}

	@Override
	public void checkDownload(Integer attachmentId, Integer topicId, SessionUser sessionUser) throws BussinessException {

	}

	@Override
	public void deleteFile(Integer attachmentId) {

	}
}
