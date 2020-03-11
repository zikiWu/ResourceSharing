<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<#include "../include/top.ftl">
	<link rel="stylesheet" type="text/css" href="${realpath}/webuploader/webuploader.css">
	<script type="text/javascript" src="${realpath}/webuploader/webuploader.min.js"></script>
	<link rel="stylesheet" href="${realpath}/css/shuoshuo.css" />
	<script type="text/javascript" src="${realpath}/js/shuoshuo.js"></script>
	<link rel="stylesheet" type="text/css" href="${realpath}/css/userhome.css">
	<#if otherUser.userPage==1>
		<link rel="stylesheet" type="text/css" href="${realpath}/css/userfullhome.css">
		<#else>
		<style type="text/css">
		.comment-form textarea {
		  width: 320px;
		}
			.main{
				height: auto;
				border: #eee 1px solid;
				 background: #fff;
				color: #333;
				margin-top: 40px;
				filter: progid:DXImageTransform.Microsoft.Shadow(color=#909090,direction=120,strength=3);
				-moz-box-shadow: 2px 2px 10px #909090;
				-webkit-box-shadow: 2px 2px 10px #909090;
				box-shadow: 2px 2px 10px #909090;
				-webkit-border-radius: 10px;
				border-radius: 10px;
			}
			.btn{
				display: inline-block;
				text-decoration: none;
				background: #4590CE;
				color: #fff;
				float: right;
				border-radius: 5px;
			}
		</style>
	</#if>
	
	<script type="text/javascript" charset="utf-8" src="${realpath}/laypage/laypage.js"></script>
	<script type="text/javascript">
		fzqblog.center = {
			userId: "${otherUser.userid?c}",
			userName: "${otherUser.userName}"
		};
	</script>
	<title></title>
</head>
<body>
	<#include "../include/header.ftl">
	<div class="main">

		<div class="left">
			<#include "../include/left.ftl">
		</div>


		<div class="right">
			<div class="user-home-tag">
				<ul class="tag" id="tag">
					<li type="file" index="1" ><a href="javascript:void(0)">资源</a>
					<li type="topic" index="2"><a href="javascript:void(0)">讨论</a></li>
					<li type="file-coll" index="3"><a href="javascript:void(0)">资源收藏</a></li>
					<li type="topic-coll" index="4"><a href="javascript:void(0)">帖子收藏</a></li>
					<li type="fans" index="5"><a href="javascript:void(0)">Ta的粉丝</a></li>
					<li type="focus" index="6"><a href="javascript:void(0)">Ta的关注</a></li>
				</ul>
			</div>

			<!-- 资源 -->
			<div class="tag-content">
				<div id="file-pager-top"></div>
				<div id="file-content"></div>
				<div id="file-page" class="page"></div>
			</div>
			<!-- 讨论 -->
			<div class="tag-content">
				<div id="topic-pager-top"></div>
				<div id="topic-content"></div>
				<div id="topic-page" class="page"></div>
			</div>
			<!-- 资源收藏 -->
			<div class="tag-content">
				<div id="file-coll-pager-top"></div>
				<div id="file-coll-content"></div>
				<div id="file-coll-pager-footer"></div>
			</div>
			<!-- 帖子收藏 -->
			<div class="tag-content">
				<div id="topic-coll-pager-top"></div>
				<div id="topic-coll-content"></div>
				<div id="topic-coll-pager-footer"></div>
			</div>
			<!-- Ta的粉丝 -->
			<div class="tag-content">
				<div class="user-con-s-f">
					<ul id="fans-content" >
					
					</ul>
				</div>
			</div>
			<!-- Ta关注的人 -->
			<div class="tag-content">
				<div class="user-con-s-f">
					<ul id="focus-content" >
					
					</ul>
				</div>
			</div>
		</div>
		
		<div class="clear"></div>

	</div>
<script type="text/javascript" src="${realpath}/js/userhome.js"></script>
</body>
</html>