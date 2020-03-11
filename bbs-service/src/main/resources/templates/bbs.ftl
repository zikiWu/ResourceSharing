<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<#include "include/top.ftl">
<script type="text/javascript" src="${realpath}/js/bbs.js"></script>
<link rel="stylesheet" type="text/css" href="${realpath}/css/bbs.css">
<title>论坛</title>
</head>
<style type="text/css">
	.list_sidebar_span{
		text-align: left;
		display: block;
		height: 30px;
		line-height: 30px;
		font-size: 14px;
		color: #333;
		margin: 0;
		padding: 0;
		font-weight: normal;
		font-family: "Microsoft YaHei",微软雅黑,"MicrosoftJhengHei",华文细黑,STHeiti,MingLiu;
		margin-top: 10px;
		font-weight: bold;
		border-bottom: 2px solid #ddd;
	}
	.public-btn {
		width: 225px;
		height: 50px;
		display: block;
		text-align: center;
		font-size: 20px;
		font-weight: bold;
		vertical-align: bottom;
		margin-top: 10px;
	}
	.img-circle {
		border-radius: 50%;
		width: 35px;
		height: 35px;
		margin-right: 5px;
	}
	.list_sidebardown ul li span.list_sidebar_left {
		color: #666;
		width: auto;
		float: left;
	}
	.list_sidebardown ul li span.list_sidebar_right {
		color: #999;
		width: auto;
		float: right;
	}
	.list_sidebardown ul li span.list_sidebar_right {
		color: #999;
		width: auto;
		float: right;
	}
	.list_sidebardown ul li {
		list-style: none;
		font-size: 12px;
		clear: both;
		text-align: left;
		line-height: 29px;
		height: 40px;
		padding: 0;
		margin: 0;
		text-indent: 0px;
	}
</style>
<body>
		<#include "include/header.ftl">
			<div class="main">
				<div class="left">
					<#list categories as category>
						<div class="cate-item">
								<div class="p-cate"><a href="${zuulpath}/bbs/board/${(category.categoryId)?c!}">${category.name!}</a></div>
									<#assign children = category.children>
										<#if (children?size>0)>
											<div class="sub-cate">
												<#list children as child>
													<div class="sub-cate-item">
														<div class="sub-cate-item-icon"><img src="${realpath}/images/topic.png"></div>
														<div class="sub-cate-item-info">
															<div >
																<div class="sub-cate-item-name">
																	<a href="${zuulpath}/bbs/sub_board/${child.categoryId!}">${child.name!}</a>
																</div>
															</div>
															<div >
																<div class="sub-cate-item-count">
																		<span class="topic-count">主题：${child.count!'0'}</span>
																		今日：<span>${child.todayCount!'0'}</span>
																</div>
															</div>
														</div>
														<div class="clear"></div>
													 </div>
												</#list>
												 <div class="clear"></div>
											</div>
										</#if>
						  </div>
					</#list>
				</div>
				
				
				<div class="right">
					<div class="count-info">
						<p><span class="day-count">今日帖子：${today} </span><span>昨日帖子：${yesterdayCount}</span></p>
						<p>总帖子：${count}</p>
					</div>
					<#--<div>-->
						<#--<a href="javascript:;" class="btn btn-info public-btn" id="publicTopicBtn">-->
							<#--<i class="icon icon-post-topic"></i>-->
							<#--<span>我要发帖</span>-->
						<#--</a>-->
					<#--</div>-->
					<#--<div class="active-user">-->
						<#--<div class="active-user-title"><i class="icon active-user-title-icon"></i>活跃用户(TOP20)</div>-->
						<#--<div class="active-user-icon">-->
							<#--<ul>-->
							<#--<#list activeUser as user>-->
								<#--<li>-->
									<#--<a href="${zuulpath}/user/${(user.userId)?c}">-->
										<#--<img class="img-circle" src="${realpath}/images/${user.userIcon}" style="display:inline;"/>-->
										<#--<span>${user.userName}</span>-->
										<#--<span class="post-count">发帖：${user.topicCount}</span>-->
									<#--</a>-->
								<#--</li>-->
							  <#--</#list>-->
							<#--</ul>-->
							<#--<div class="clear"></div>-->
						<#--</div>-->
					<#--</div>-->

					<div class="index_sidebar">
						<div>
							<a href="javascript:;" class="btn btn-info public-btn" id="publicTopicBtn">
								<i class="icon icon-post-topic"></i>
								<span>我要发帖</span>
							</a>
						</div>
						<div class="list_sidebardown">
							<span class="list_sidebar_span">用户发帖榜</span>
							<ul>
								<li><span class="list_sidebar_left">用户名</span><span class="list_sidebar_right">发帖数</span></li>
								<#list activeUser as user>
								 <#if user_index <= 10>
									<li class="weekexp" style="display: list-item;">
										<img class="img-circle" src="${realpath}/images/${user.userIcon}" style="display:inline;">
										<a class="list_sidebar_left" href="http://localhost:7777/common/${user.userId?c}">${user.userName}</a><span class="list_sidebar_right">${user.topicCount}</span></li>
								 </#if>
								</#list>

							</ul>
							</ul>
						</div>
					</div>


				</div>
				
				
				<div class="clear"></div>
			</div>

</body>
</html>