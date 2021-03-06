<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
		<#include "../include/top.ftl">
	<link rel="stylesheet" href="${realpath}/css/admin-public.css" />
	<script type="text/javascript" charset="utf-8" src="${realpath}/laypage/laypage.js"></script>
	<script language="javascript" type="text/javascript" src="${realpath}/datePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		fzqblog.background = "${user.userBg}";
	</script>
	<title>后台管理</title>
</head>
<body>
<#include "../include/header.ftl">
<div>
	<div class="mainFrame">

		<div class="search">
		    <form id="searchform" onsubmit="return false">
			  <table class="table">
			    <tbody>
				  <tr>
			        <td class="tit-left">创建时间</td>
			        <td class="tit">
						<input class="form-control input input-small" onclick="WdatePicker()" type="text" id="startDate" name="startDate" value="" readonly="readonly">
					</td>
					<td>&nbsp;至&nbsp;</td>
					<td><input class="form-control input input-small" onclick="WdatePicker()" type="text" id="endDate" name="endDate" value="" readonly="readonly">
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
				    <th width="25"><input type="checkbox" class="not-input" id="check-all"/></th>
				    <th width="50">状态</th>
					<th>消息</th>
					<th width="200">创建时间</th>
					<th width="100">操作</th>
				</thead>
				<tbody id="data-list">
					
				</tbody>
			</table>
			<div class="btn-group" style="float: left;">
			     <a href="javascript:;" class="btn btn-info" id="mark-batch-btn">标记为已读</a>
			     <a href="javascript:;" class="btn btn-info" id="del-batch-btn">删除</a>
			</div>
			<div class="clear"></div>
			<div id="pager" class="page">
				
			</div>
			
		</div>
      

	</div>
</div>
	<script type="text/javascript" src="${realpath}/js/admin-public.js"></script>
	<script type="text/javascript" src="${realpath}/js/message_list.js"></script>
	
</body>
</html>