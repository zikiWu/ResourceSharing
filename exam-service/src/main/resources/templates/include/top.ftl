<#assign realpath = "http://localhost:4447">
<#assign zuulpath = "http://localhost:7777">
<script type="text/javascript">
		var fzqblog = {};
		fzqblog.realpath = "${realpath}";
		var zuul = {};
		zuul.realpath = "${zuulpath}";
		fzqblog.userId = "10044";
		fzqblog.userName = "123";
		<#--<#if Session["session_user_key"]?exists>-->
			<#--fzqblog.userId = "${session_user_key.userid?c}";-->
			<#--<#else>-->
			<#--fzqblog.userId = 0;-->
		<#--</#if>-->
		fzqblog.curUrl = window.location.pathname;
	</script>
<link rel="stylesheet" type="text/css" href="${realpath}/webjars/bootstrap/css/bootstrap.min.css">
<script type="text/javascript" src="https://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>F
<script type="text/javascript" src="${realpath}/webjars/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="${realpath}/artDialog/css/ui-dialog.css">
<script type="text/javascript" src="${realpath}/artDialog/dist/dialog-min.js"></script>
<link rel="stylesheet" type="text/css" href="${realpath}/css/fzqblog.css">
<script type="text/javascript" src="${realpath}/layer/layer.js"></script>
<link rel="stylesheet" type="text/css" href="${realpath}/animate/animate.css">

<script>
 fzqblog.topUrl = {
		    loadMessage:zuul.realpath+"/common/load_user_message_list.action",
		    loadMessageCount:zuul.realpath+"/common/load_user_message_count.action"
}
     

     if(fzqblog.userId!="0"){
    	  getMessageCount();
     }
 
//获取消息数量
function getMessageCount(){

		$.ajax({
			url : fzqblog.topUrl.loadMessageCount,
			type: 'POST',
			dataType: 'json',
			data:{status:'UNREAD'},
			success:function(response){
			   var count = response.data;
        	   if(count>0){
        		   $("#message-count").text(count);
        		   setInterval(function(){
        			   if($("#message-count").hasClass("shake")){
        				   $("#message-count").removeClass("shake");
        			   }else{
        				   $("#message-count").addClass("shake");
        			   }
        		   },1000);
        	   }
			}
		});
}
//显示消息框
function showMessageList(){
	$("#message-dialog").show();
	if($("#message-data-list").children().length==0&&!fzqblog.isLoading){
		loadMessage();
	}
}



function MessageItem(data){
	var item = $("<div class='message-item'></div>");
	$("<div>"+data.description+"</div>").appendTo(item);
	var op = $("<div class='message-op'></div>").appendTo(item);
	$("<span class='time'>发送："+data.createTime+"</span>").appendTo(op);
	if(data.url!=""){
	    $('<a href="'+fzqblog.realpath+'/admin/readMessage.action?id='+data.id+'" class="message-detail" class="read-message" url="'+data.url+'" messageId="'+data.id+'">查看详情</a>').appendTo(op);
	}
	return item;
}

function loadMessage(page){
	fzqblog.isLoading = true;
	fzqblog.messagePageNo = page;
    $("#load-more-message").remove();
    $('<div id="loading-message" class="loading"> <div class="loading-con"><img src="' + fzqblog.realpath + '/resources/images/loading.gif"/><span>正在加载.......</span></div></div>').appendTo($("#message-load"));

    $.ajax({
			url :fzqblog.topUrl.loadMessage,
			type: 'POST',
			dataType: 'json',
			data:{status:'UNREAD'},
			success:function(res){
			   fzqblog.isLoading = false;
        	$("#loading-message").remove();
            var list = res.data.list;
            var simplePage = res.data.page;
            if(simplePage.count==0){
            	$("<div class='no-data'>没有消息</div>").appendTo($("#message-data-list"));
            }else{
            	for (var i = 0, _len = list.length, data; i < _len, data = list[i]; i++) {
                    new MessageItem(data).appendTo($("#message-data-list"));
                }
	            if (simplePage.pageTotal > simplePage.page) {
	                 $('<div id="load-more-message" class="load-more"><a href="javascript:;">⇓加载更多</a></div>').appendTo($("#message-load"));
	            }
           	 }
			}
		});
}
$(document).ready(function() {
	$("#searchBtn").click(function(){
    	 var keyword = $.trim($("#keyWord").val());
    	 if(keyword == ""){
             layer.msg("搜索内容不能为空", {
                        icon: 5,
                        time: 1500 //2秒关闭（如果不配置，默认是3秒）
                    });
             return;
         }
         if(keyword.lenth > 20){
             layer.msg("搜索内容不能超过20个字符", {
                        icon: 5,
                        time: 1500 //2秒关闭（如果不配置，默认是3秒）
                    });
             return;
         }
    	 if(fzqblog.curUrl.indexOf("search")==-1){
    		 searchForm.submit();
    	 }else{
    		 searchData();
    	 }
     });
	$.ajax({
		url :zuul.realpath+"/common/getLoginUser",
		type: 'POST',
		dataType: 'json',
		data:{status:0},
		success:function(res){
			fzqblog.userId = res.userId;
			fzqblog.userName = res.userName;
			<#assign userId = "">
		}
	});

});

</script>
<script type="text/javascript" src="${realpath}/js/fzqblog.js"></script>