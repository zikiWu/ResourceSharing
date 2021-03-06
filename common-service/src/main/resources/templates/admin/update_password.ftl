<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
		<#include "../include/top.ftl">
	<link rel="stylesheet" href="${realpath}/css/admin-public.css" />
	<link rel="stylesheet" type="text/css" href="${realpath}/webuploader/webuploader.css">
	<script type="text/javascript" src="${realpath}/webuploader/webuploader.min.js"></script>
	<script type="text/javascript" src="${realpath}/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="${realpath}/ueditor/ueditor.all.js"></script>
	<script type="text/javascript" charset="utf-8" src="${realpath}/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript" charset="utf-8" src="${realpath}/ueditor/MyActionUrl.js"></script>
	<link rel="stylesheet" type="text/css" href="${realpath}/webuploader/webuploader.css">
	<script language="javascript" type="text/javascript" src="${realpath}/datePicker/WdatePicker.js"></script>

	<title>后台管理</title>
</head>
<body>
<div>
	<nav class="leftNav navbar-default">
			<ul class="first-nav-level nav">
				<li class="nav-header">
					<span><a href="${realpath}/user/${user.userid?c}" style="padding: 0px;"><img alt="image" class="img-circle img" src="${realpath}/images/${user.userIcon}"></a></span>
					<span class="adminUser">${user.userName}</span>
				</li>
				<li>
					<a href="#" class="mycollapse">
          				<span class="glyphicon glyphicon-user"></span>
						<span>个人信息管理</span>
						<span class="fa arrow">-</span>
					</a>
					<ul class="second-nav-level nav in" id="collapse">
						<li><a href="${realpath}/admin" class="">修改个人资料</a></li>
						<li><a href="${realpath}/admin/preUpdatePassword" class="activeNav">修改登录密码</a></li>
						<li><a href="${realpath}/admin/preUpdateUserIcon">修改头像</a></li>
						<li><a href="${realpath}/admin/preUpdateUserBg">修改主页背景</a></li>
						<li><a href="${realpath}/admin/preUpdateUserPage">版面设置</a></li>
					</ul>
				</li>
				<li>
					<a href="#" class="mycollapse">
						<span class="glyphicon glyphicon-book"></span>
						<span>博客管理</span>
						<span class="fa arrow">+</span>
					</a>
					<ul class="second-nav-level nav collapse" id="collapse">
						<li><a href="${realpath}/admin/blogList" >我的博文</a></li>
						<li><a href="${realpath}/admin/preAddBlog">发表博文</a></li>
						<li><a href="${realpath}/admin/draftBlogList">博文草稿箱</a></li>
						<li><a href="${realpath}/admin/blog_category">博文分类管理</a></li>
					</ul>
				</li>
				<li>
					<a href="#" class="mycollapse collapse">
						<span class="glyphicon glyphicon-envelope"></span>
						<span>消息管理</span>
						<span class="fa arrow">+</span>
					</a>
					<ul class="second-nav-level nav collapse" id="collapse">
						<li><a href="${realpath}/admin/messageList">我的消息</a></li>
					</ul>
				</li>
				<li>
					<a href="#" class="mycollapse">
						<span class="glyphicon glyphicon-heart"></span>
						<span>收藏管理</span>
						<span class="fa arrow">+</span>
					</a>
					<ul class="second-nav-level nav collapse" id="collapse">
						<li><a href="${realpath}/admin/collection_list.action?articleType=T">收藏的帖子</a></li>
						<li><a href="${realpath}/admin/collection_list.action?articleType=K">收藏的知识库</a></li>
						<li><a href="${realpath}/admin/collection_list.action?articleType=A">收藏的问答</a></li>
						<li><a href="${realpath}/admin/collection_list.action?articleType=B">收藏的博文</a></li>
					</ul>
				</li>
			</ul>
	</nav>
	<div class="rightTopNav" style="margin-left: 200px;background: #18bc9c;">
		<nav class="navbar navbar-inverse navbar-static-top" style="">
				<div class="navbar-header">
			      <a class="navbar-brand">JAVABLOG</a>
			   </div>
		       	<ul class="nav navbar-top-links navbar-right">
		            <li class=""><a href="${realpath}">首页</a></li>
			        <li><a href="${realpath}/shuoshuo">说说</a></li>
			        <li><a href="${realpath}/exam">在线考试</a></li>
			        <li><a href="${realpath}/bbs">讨论区</a></li>
			        <li><a href="${realpath}/knowledge">知识库</a></li>
			        <li><a href="${realpath}/ask">问答</a></li>
		     	</ul>
		</nav>
	</div>
	<div class="mainFrame">
		<div class="container">
		<form id="updateUserForm"> 
			<div class="row">
				<div class="form-group" id="topicTitle">
					<label class="col-xs-1 control-label"> <em class="important">*</em>
						原密码
					</label>
					<div class="col-xs-4">
						<input name="oldPassword" id="oldPassword" class="form-control" type="password"  />	
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group" id="topicTitle">
					<label class="col-xs-1 control-label"> <em class="important">*</em>
						新密码
					</label>
					<div class="col-xs-4">
						<input  name="newPassword" id="newPassword" class="form-control" type="password" />	
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group" id="topicTitle">
					<label class="col-xs-1 control-label"> <em class="important">*</em>
						确认密码
					</label>
					<div class="col-xs-4">
						<input id="confirmNewPassword" class="form-control" type="password"  />	
					</div>
				</div>
			</div>
		</form>
			<div class="row">
				<div class="col-xs-4"></div>
				&nbsp;&nbsp;<a href="javascript:;" id="updatePWD" class="btn btn-sm btn-info">保存信息</a>
			</div>
		</div>
	</div>
</div>
	<script type="text/javascript" src="${realpath}/js/admin-public.js"></script>
</body>
</html>