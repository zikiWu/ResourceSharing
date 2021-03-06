<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<#include "include/top.ftl">
<link rel="stylesheet" type="text/css" href="${realpath}/css/bbs_detail.css">
<script type="text/javascript" src="${realpath}/ueditor/third-party/SyntaxHighlighter/shCore.js"></script>
<link rel="stylesheet" href="${realpath}/ueditor/third-party/SyntaxHighlighter/shCoreDefault.css">
<script type="text/javascript">
    SyntaxHighlighter.all();
</script>
<script text="text/javascript">
	fzqblog.topicType=${topic.topicType.getType()};
	fzqblog.topicId = ${topic.topicId};
	fzqblog.articleType="TOPIC";
	fzqblog.articleTitle="${topic.title}";
	fzqblog.articleUserId="${topic.userId?c}";
</script>
<title></title>
</head>
<body>
	<#include "include/header.ftl">
		<div class="main">
		<div class="navigation">
		<a href="${zuulpath}/bbs">讨论区</a>&nbsp;&gt;&nbsp;
		<a href="${zuulpath}/bbs/board/${topic.pCategoryId}">${topic.pCategoryName}</a>&nbsp;&gt;&nbsp;
		<a href="${zuulpath}/bbs/sub_board/${topic.categoryId}">${topic.categoryName}</a>&nbsp;&gt;&nbsp;
		<span>${topic.title}</span>
		</div>
			<div class="arc-shadow">
				<div class="topic-info-con">
					<div class="topic-icon"><a href="${realpath}/user/${(topic.userId)?c}"><img class="img-circle" src="${realpath}/images/${topic.userIcon}"></a></div>
					<div class="topic-info">
						<div class="topic-title">
							<a href="${realpath}/${(topic.topicId)?c}" class="title">${topic.title}</a>
							<span class="time">${topic.createTimeString!''}</span>
				            <a class="topic-cate-tag" href="${realpath}/bbs/sub_board/${(topic.categoryId)?c}"><span>${topic.categoryName}</span></a>
							<button class="like">喜欢</button>
							<button class="collection">收藏</button>
						</div>
						<div class="topic-info-d topic-count-info">
							<a href="${zuulpath}/common/${(topic.userId)?c}" class="user-info"><i class="icon icon-user-name"></i><span>${topic.userName}</span></a>
			       			<span class="count"><i class="icon icon-read"></i><span>阅读：${topic.readCount!'0'}</span></span>
			       			<span class="count"><i class="icon icon-comment" data-garbage="true"></i><span>评论：<span id="comment-count" >${topic.commentCount!'0'}</span></span></span>
			       			<span class="count"><i class="icon icon-like"></i><span>喜欢：<span id="like-count">${topic.likeCount!'0'}</span></span></span>
			       			<span class="count"><i class="icon icon-collection"></i><span>收藏：<span id="collection-count">${topic.collectionCount!'0'}</span></span></span>
						</div>
					</div>
					<div class="clear"></div>
				</div>
				<div id="vote-con" class="vote-con">
				</div>
				<div class="topic-detail">
					${topic.content}
				</div>
				<#if topic.attachment??>
				<div class="attachment">
					<div class="attachment-title">附件</div>
					<div class="attachment-info">
						<span class="filename"><i class="icon icon-package"></i>${topic.attachment.fileName!''}</span>&nbsp;&nbsp;
				        <span >所需积分：${topic.attachment.downloadMark!'0'}</span>&nbsp;&nbsp;
				        <span class="dccount">下载：<a href="javascript:void(0)" attachmentid="${topic.attachment.attachmentId!''}" id="dcount">${topic.attachment.downloadCount!'0'}</a></span>&nbsp;&nbsp;
				        <a href="javascript:void(0)" class="btn btn-info" name="144" topicid="${topic.topicId!''}" attachmentid="${topic.attachment.attachmentId!''}" id="downloadFile">点击下载</a>
				        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				        <a href="${realpath}/donate" target="_blank">没有积分？</a>
					</div>
					<div class="clear"></div>
				</div>
				</#if>
				
			<#include "include/comment.ftl">
					
			</div>
		</div>
	<script type="text/javascript" charset="utf-8" src="${realpath}/js/bbs_detail.js"></script>
</body>
</html>