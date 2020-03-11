<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
		<#include "../include/top.ftl">
	<link rel="stylesheet" href="${realpath}/css/admin-public.css" />
	<script language="javascript" type="text/javascript" src="${realpath}/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" charset="utf-8" src="${realpath}/laypage/laypage.js"></script>
	<script type="text/javascript">
		fzqblog.background = "${user.userBg}";
	</script>
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
						<span class="fa arrow">+</span>
					</a>
					<ul class="second-nav-level nav collapse" id="collapse">
						<li><a href="${realpath}/admin" >修改个人资料</a></li>
						<li><a href="${realpath}/admin/preUpdatePassword">修改登录密码</a></li>
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
						<li><a href="${realpath}/admin/blogList">我的博文</a></li>
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
						<li><a href="${zuulpath}/common/messageList">我的消息</a></li>
					</ul>
				</li>
				<li>
					<a href="#" class="mycollapse">
						<span class="glyphicon glyphicon-heart"></span>
						<span>收藏管理</span>
						<span class="fa arrow">-</span>
					</a>
					<ul class="second-nav-level nav in" id="collapse">
						<li><a href="${zuulpath}/common/collection_list.action?articleType=TOPIC">收藏的帖子</a></li>
						<li><a href="${zuulpath}/common/collection_list.action?articleType=K">收藏的知识库</a></li>
					</ul>
				</li>
			</ul>
	</nav>


	<div class="rightTopNav" style="margin-left: 200px;background: #18bc9c;">
		<nav class="navbar navbar-inverse navbar-static-top" style="">
				<div class="navbar-header">
			      <a class="navbar-brand">ZK-FILE</a>
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

      	<div class="search">
		    <form id="searchform" onsubmit="return false">
			  <table class="table">
			  <input type="hidden" name="articleType" id="articleType" value="${articleType}"></input>
			    <tbody>
				  <tr>
				  	<td class="tit-left">标题</td>
					<td><input type="text" style="width: 400px;"  class="form-control input l-length" id="title" name="title" placeholder="标题支持模糊搜索"></td>
			        <td class="tit-left">创建时间</td>
			        <td class="tit">
						<input class="form-control input input-small" onclick="WdatePicker()" type="text" id="startDate" name="startDate" value="" readonly="readonly">
					</td>
					<td>至</td>
					<td>
						<input class="form-control input input-small" onclick="WdatePicker()" type="text" id="endDate" name="endDate" value="" readonly="readonly">
					</td>
			        <td class="tit"><a href="javascript:search()" class="btn btn-info">查询</a></td>
			      </tr>
			  </tbody>
			  </table>
			</form>
		</div>

		<div class="grid-list">
			<table class="table table-list hover">
				<thead>
					<th>标题</th>
					<th width="200">收藏时间</th>
					<th width="50">操作</th>
				</thead>

				<tbody id="data-list">
					
				</tbody>
			</table>
			<div id="pager" class="page"></div>
		</div>

	</div>
</div>
	<script type="text/javascript" src="${realpath}/js/admin-public.js"></script>
	<script type="text/javascript" src="${realpath}/js/collection_list.js"></script>
</body>
</html>