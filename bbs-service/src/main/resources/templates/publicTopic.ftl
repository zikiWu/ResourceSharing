<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<#include "include/top.ftl">
	
	<link rel="stylesheet" type="text/css" href="${realpath}/webuploader/webuploader.css">
	<script type="text/javascript" src="${realpath}/webuploader/webuploader.min.js"></script>
	<script type="text/javascript" src="${realpath}/ueditor/ueditor.config.js"></script>
	<script src="${realpath}/ueditor/ueditor.all.js"></script>
	<script type="text/javascript" charset="utf-8" src="${realpath}/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript" charset="utf-8" src="${realpath}/ueditor/MyActionUrl.js"></script>
	<link rel="stylesheet" type="text/css" href="${realpath}/webuploader/webuploader.css">
	<link rel="stylesheet" type="text/css" href="${realpath}/css/publicTopic.css">
	<script language="javascript" type="text/javascript" src="${realpath}/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${realpath}/webuploader/webuploader.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${realpath}/js/bbs.js"></script>

	<title>发帖</title>
	</head>
<style type="text/css">
	
</style>
<body>
	<#include "include/header.ftl">
	<div class="main">
	<div class="container">
		<form id="postBbsForm">
		<input type="hidden" name="content" id="richContent" />
			<div class="row">
				<div class="form-group" id="topicTitle">
					<label class="col-xs-1 control-label"> <em class="important">*</em>
						标题:
					</label>
					<div class="col-xs-10">
						<input class="form-control"  name="title" id="title"></input>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group" id="category">
					<label class="col-xs-1 control-label"> <em class="important">*</em>
						板块:
					</label>
					<div class="col-xs-3">
						<select class="form-control" name="pCategoryId" id="pCategoryId">
							<option value="">请选择一级分类</option>
						</select>
					</div>
					<div class="col-xs-3">
						<select class="form-control" name="categoryId" id="categoryId">
							<option value="">请选择二级分类</option>
						</select>
					</div>
					<input id="categoryName" name="categoryName" type="hidden" />
			</div>
		</div>

		<div class="row">
			<#--<div class="form-group">-->
				<#--<label class="col-xs-1 control-label">-->
					<#--<em class="important">*</em>-->
					<#--类型:-->
				<#--</label>-->
				<#--<div class="col-xs-8">-->
					<#--<#list topicType as item>-->
					<#--<label class="checkbox-inline" style="padding-left: 0;">-->
						<#--<input type="radio"  <#if item_index==0>checked="checked"</#if>-->
					<#--name="topicType"  value="${item.getType()}" class="topicType">${item.getDesc()}-->
					<#--</label>-->
					<#--</#list>-->
				<#--</div>-->
			<#--</div>-->

			<input type="hidden" name="topicType" value="NORMAL" />
		</div>


	<div class="row">
				<div class="form-group" id="ueContent">
					<label class="col-xs-1 control-label"> <em class="important">*</em>
						内容:
					</label>
					<div class="col-xs-10">
						<script id="editor" type="text/plain" style="width:1024px;height:500px;"></script>
					</div>
				</div>
	</div>

			<input type="hidden" name="fileName" id="attached_file_name" />
			<input type="hidden" name="fileUrl" id="attached_file" />

			<div class="row">
				<div class="form-group" id="needMark">


					<div class="col-xs-1">
						<input class="form-control"  name="downloadMark" id="mark" value="0" type="hidden"></input>
					</div>
					<div class="col-xs-2">
					 </div>
				</div>
			</div>

</form>


		<div class="row">
			<div class="col-xs-10"></div>
			<button class="btn btn-info" id="postExamBtn">发布帖子</button>
		</div>
</div>
</div>
<script type="text/javascript" charset="utf-8" src="${realpath}/js/publicTopic.js"></script>

</html>