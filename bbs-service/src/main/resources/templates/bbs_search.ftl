<!DOCTYPE html>
<ftl>
<head>
<meta charset="UTF-8">
<#include "include/top.ftl">
<link rel="stylesheet" type="text/css" href="${realpath}/css/bbs_list.css">
<script type="text/javascript" charset="utf-8" src="${realpath}/laypage/laypage.js"></script>
<script type="text/javascript" charset="utf-8" src="${realpath}/js/search.js"></script>
<script text="text/javascript">
	fzqblog.pageTotal = ${result.page.pageTotal}
</script>
</head>
<style>
	h3.list_h{
		width: 98%;
		margin: 0 auto;
		clear: both;
		line-height: 28px;
		height: 28px;
		overflow: hidden;
		text-align: left;
		margin-top: 10px;
	}
	h3.list_h a.on,h3.list_h a:hover{
		color: #3586f4;
		background: url("${realpath}/imgages/down_blue.gif") right no-repeat;
	}
	h3.list_h a{
		width: 105px;
		height: 28px;
		display: inline-block;
		font-size: 14px;
		color: #999;
		margin-right: 10px;
		text-align: center;
		background: url("${realpath}/imgages/down_gray.gif") right no-repeat;
		text-decoration: none;
	}
</style>
<body>
	<#include "include/header.ftl">
		<div class="main">
			<#assign subCategory="">
			<#if category??>
				<#assign subCategory="&nbsp;&gt;&nbsp;<span>${category.name}</span>">
			</#if>
			
			<div class="topic-list-con">


				<div class="topic-item-list">
				<#if (result.page.pageTotal>0)>
				<#list result.list as item>
					<div class="topic-item">
						<div class="user-icon">
							<a href="${zuulpath}/common/${(item.userId)?c}"><img class="img-circle" src="${realpath}/images/${item.userIcon}" /></a>
						</div>
						<div class="topic-item-info">
							<div class="topic-item-title">
							<#if (item.grade>0)>
	                		    <span class="ding">置顶</span>
	                		</#if>
	                		<#if (item.essence==1)>
	                		    <span class="jing">精华</span>
	                		 </#if>
	                		 <#if (item.topicType.getType()==1)>
	                		    <span class="vote">投票</span>
	                		 </#if>
	                			<a href="${zuulpath}/bbs/${item.topicId}" class="title">${item.title}</a>
	                			<span class="time">${item.createTimeString!''}</span>
	                			<a class="topic-cate-tag" href="${zuulpath}/bbs/sub_board/${item.categoryId}"><span>${item.categoryName!''}</span></a>
	                		</div>
							<div class="topic-item-summary">${item.summary}</div>
							<#if item.topicImageArray??>
							<div class="image-thum topic-item-images">
								<#list item.topicImageArray as img>
								<a class="showImage" href="javascript:;"><img class="img-thumbnail" src="${img}" /></a>
								<#if (img_index>=2)>
									<#break>
								</#if>
								</#list>
								<div class="clear"></div>
							</div>
							</#if>
							<div class="topic-count-info">
								<a href="${zuulpath}/user/${(item.userId)?c}" class="user-info"><i class="icon icon-user-name"></i><span>${item.userName}</span></a>
								<span class="count"><i class="icon icon-read"></i><span>阅读：${item.readCount!'0'}</span></span>
								<span class="count"><i class="icon icon-comment"></i><span>评论：${item.commentCount!'0'}</span></span>
								<span class="count"><i class="icon icon-like"></i><span>喜欢：${item.likeCount!'0'}</span></span>
								<span class="count"><i class="icon icon-collection"></i><span>收藏：${item.collectionCount!'0'}</span></span>
							</div>
						</div>
						<div class="clear"></div>
					</div>
					</#list>
					<#else>
						<div class="no-data">没有帖子，赶紧抢沙发吧</div>
					</#if>
					<div id="page" class="page"></div>
				<div class="clear"></div>
				</div>

			</div>

		</div>
	<script type="text/javascript" charset="utf-8" src="${realpath}/js/bbs_list.js"></script>
</body>
</ftl>