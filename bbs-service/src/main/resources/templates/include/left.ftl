
<div class="user-home-user-name">${user.userName}</div>
<div class="user-home-op-btn">

	<#if focusType==0>
		<a href="${realpath}/admin" class="btn btn-sm btn-info">后台管理</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="${realpath}/admin/preAddBlog" class="btn btn-sm btn-info">写写博客</a>
		<#elseif focusType==1>
		<a href="javascript:;" focustype="${focusType}" id="focus-btn" class="btn  btn-success">关注</a>
		<#else>
		<a href="javascript:;" focustype="${focusType}" id="focus-btn" class="btn  btn-warning">取消关注</a>	
	</#if>
</div>
<div class="user-home-friend">
	<span id="left-fans-count">${fansCount!'0'}</span>粉丝&nbsp;&nbsp;/&nbsp;&nbsp;<span id="left-focus-count">${focusCount!'0'}</span>关注
</div>
<div class="user-home-info-item">
	<span class="user-home-info-tit">积分:</span>
	<span class="user-home-info">${user.mark!'0'}</span>
</div>
<div class="user-home-info-item">
	<span class="user-home-info-tit">性别:</span>
	<span class="user-home-info">
				${user.sex!'未知'}
	</span>
</div>
<div class="user-home-info-item">
	<span class="user-home-info-tit">出生日期:</span>
	<span class="user-home-info">${user.birthdayString!'未知'}</span>
</div>
<div class="user-home-info-item">
	<span class="user-home-info-tit">工作:</span>
	<span class="user-home-info">${user.work!'未知'}</span>
</div>
<div class="user-home-info-item">
	<span class="user-home-info-tit">地区:</span>
	<span class="user-home-info">${user.address!'未知'}</span>
</div>
<div class="user-home-info-item">
	<span class="user-home-info-tit">加入时间:</span>
	<span class="user-home-info">${user.registerTimeString!'未知'}</span>
</div>
<div class="user-home-info-item">
	<span class="user-home-info-tit">上次登录:</span>
	<span class="user-home-info">${user.lastLoginTimeString!'未知'}</span>
</div>




<script>
$(function(){
    $("#focus-btn").click(function(){
		if(fzqblog.userId==""){
		    fzqblog.goLogin();
		    return;
		}
		var focusType = $(this).attr("focusType");
		var url="";
		if(focusType=="1"){//关注
		    url=fzqblog.realpath+"/user/focus.action";
		}else if(focusType=="2"){
		    url=fzqblog.realpath+"/user/cancel_focus.action";
		}
		var that = $(this);

		$.ajax({
			url: url,
			type: 'POST',
			dataType: 'json',
			data: {friendUserId:"${user.userid?c}"},
			success:function(res){
			   if(focusType=="1"){
			       that.attr("focusType",2);
			       that.html("取消关注");
			       that.removeClass('btn-success');
			       that.addClass('btn-warning');
			       $("#left-fans-count").text(parseInt($("#left-fans-count").text())+1);
			   }
			   else if(focusType=="2"){
			       that.attr("focusType",1);
			       that.removeClass('btn-warning');
			       that.addClass('btn-success');
			       that.html("&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;注&nbsp;&nbsp;");
			       $("#left-fans-count").text(parseInt($("#left-fans-count").text())-1);
			   }
			}

		});
    });
})
</script>		
