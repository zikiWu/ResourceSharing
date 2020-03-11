
<div class="user-home-user-name" id="userHomeName">${otherUser.userName}</div>
<div class="user-home-op-btn">

	<#if focusType==0>
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
	<span class="user-home-info">${otherUser.mark!'0'}</span>
</div>
<div class="user-home-info-item">
	<span class="user-home-info-tit">性别:</span>
	<span class="user-home-info">
				${otherUser.sex!'未知'}
	</span>
</div>
<div class="user-home-info-item">
	<span class="user-home-info-tit">出生日期:</span>
	<span class="user-home-info">${otherUser.birthdayString!'未知'}</span>
</div>
<div class="user-home-info-item">
	<span class="user-home-info-tit">学校:</span>
	<span class="user-home-info">${otherUser.work!'未知'}</span>
</div>
<div class="user-home-info-item">
	<span class="user-home-info-tit">地区:</span>
	<span class="user-home-info">${otherUser.address!'未知'}</span>
</div>
<div class="user-home-info-item">
	<span class="user-home-info-tit">加入时间:</span>
	<span class="user-home-info">${otherUser.registerTimeString!'未知'}</span>
</div>
<div class="user-home-info-item">
	<span class="user-home-info-tit">上次登录:</span>
	<span class="user-home-info">${otherUser.lastLoginTimeString!'未知'}</span>
</div>




<script>
$(function(){
    $("#focus-btn").click(function(){
		var focusType = $(this).attr("focusType");
		var url="";
		if(focusType=="1"){//关注
		    url=zuul.realpath+"/common/focus.action";
		}else if(focusType=="2"){
		    url=zuul.realpath+"/common/cancel_focus.action";
		}
		var that = $(this);

		$.ajax({
			url: url,
			type: 'POST',
			dataType: 'json',
			data: {friendUserId:"${otherUser.userid?c}"},
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
