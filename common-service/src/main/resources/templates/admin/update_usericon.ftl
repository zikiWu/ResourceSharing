<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
		<#include "../include/top.ftl">
	<link rel="stylesheet" href="${realpath}/css/admin-public.css" />
	<link rel="stylesheet" href="${realpath}/css/changeUserIcon.css" />
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
<#include "../include/header.ftl">
<link rel="stylesheet" href="${realpath}/Jcrop/css/jquery.Jcrop.min.css">
<script src="${realpath}/Jcrop/js/jquery.Jcrop.min.js"></script>
<script src="${realpath}/Jcrop/js/jQuery.UtrialAvatarCutter.js"></script>

<div>
	<div class="mainFrame">

		<form id="updateUserForm">
			<div class="row">
				<div class="form-group" id="topicTitle">
					<label class="col-xs-1 control-label">
						唯一ID:
					</label>
					<div class="col-xs-10">
						${user.userid?c}
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group" id="topicTitle">
					<label class="col-xs-1 control-label">
						邮箱:
					</label>
					<div class="col-xs-10">
						${user.email!''}
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group" id="topicTitle">
					<label class="col-xs-1 control-label">
						用户名:
					</label>
					<div class="col-xs-10">
						${user.userName}
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group">
					<label class="col-xs-1 control-label">性别:</label>
					<div class="col-xs-8">
						<#assign sex="${user.sex!''}">
						<label class="checkbox-inline" style="padding-left: 0;">
							<input type="radio" <#if sex=="男">checked="checked"</#if> name="sex" value="男">男</label>
						<label class="checkbox-inline" style="padding-left: 0;">
							<input type="radio" <#if sex=="女">checked="checked"</#if> name="sex" value="女">女</label>
					</div>
				</div>
			</div>
			<div class="row" id="enddate">
				<div class="form-group">
					<label class="col-xs-1 control-label">出生日期:</label>
					<div class="col-xs-2">
						<input  id="birthday" value="${(user.birthday?date)!''}" name="birthday" class="form-control" type="text" onFocus="WdatePicker({isShowClear:false,readOnly:true})"/>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group" id="topicTitle">
					<label class="col-xs-1 control-label">
						居住地:
					</label>
					<div class="col-xs-10">
						<input name="address" class="form-control" type="text" value="${user.address!''}" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group" id="topicTitle">
					<label class="col-xs-1 control-label">
						学校:
					</label>
					<div class="col-xs-10">
						<input  name="work" class="form-control" type="text" value="${user.work!''}"/>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group" id="topicTitle">
					<label class="col-xs-1 control-label">
						个性签名:
					</label>
					<div class="col-xs-10">
						<input name="characters" class="form-control" type="text" value="${user.characters!''}" />
					</div>
				</div>
			</div>
		</form>
		<div class="row">
			<div class="col-xs-10"></div>
			<a href="javascript:;" id="saveUserInfo" class="btn btn-info">保存信息</a>
		</div>
	</div>

        <div id="user-avatar">
			<img src="${realpath}/images/${user.userIcon}" style="width: 180px;height:180px;">        
        </div>
        <div class="save-btn">
            <a href="javascript:;" class="btn btn-sm btn-info" id="save-btn">保存头像</a>
        </div>
        <#--<ul class="tag"  id="tag">-->
            <#--<li class="active"><a href="javascript:void(0)">自定义头像</a></li>-->
            <#--&lt;#&ndash;<li><a href="javascript:void(0)">系统头像</a></li>&ndash;&gt;-->
        <#--</ul>-->
        <div class="tag-content">
            <div class="file_upload" id="file_upload">
                   <div class="file-uploader">
                       <div id="filePicker">上传图片</div>
                       <div id="loading-upload"><img src="${realpath}/images/loading.gif"></div>
                   </div>
                   <div class="file-info"><span class="info">图片不能超过2M，高宽不能超过1000px，不能低于180px</span></div>
                   <div class="clear"></div>
             </div>
             <div id="imgarea">
				<div id="picture_original">
					<img src="" id="target-img"/>
				</div>
				<div id="preview">
					<div id="picture_200"></div>
				</div>
				<div style="clear:left;"></div>
			</div>
        </div>
        <div class="tag-content">
            <div id="sys-user-icon">
            
            </div>
        </div>

	</div>
</div>
	<script type="text/javascript" src="${realpath}/js/admin-public.js"></script>
	<script type="text/javascript" src="${realpath}/js/changeUserIcon.js"></script>
</body>
</html>