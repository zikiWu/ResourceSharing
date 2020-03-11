<#assign realpath = "http://localhost:4445">
<#assign zuulpath = "http://localhost:7777">
<script type="text/javascript">
		var fzqblog = {};
		fzqblog.realpath = "${realpath}";
		var zuul = {};
		zuul.realpath = "${zuulpath}";
		fzqblog.userId = "";
		fzqblog.userName = "";
		<#--<#if Session["session_user_key"]?exists>-->
			<#--fzqblog.userId = "${session_user_key.userid?c}";-->
			<#--<#else>-->
			<#--fzqblog.userId = 0;-->
		<#--</#if>-->
		fzqblog.curUrl = window.location.pathname;
	</script>
<link rel="stylesheet" type="text/css" href="${realpath}/webjars/bootstrap/css/bootstrap.min.css">
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
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
				$("#message-count").text(count);
        	   // if(count>0){
        		//    $("#message-count").text(count);
        		//    setInterval(function(){
        		// 	   if($("#message-count").hasClass("shake")){
        		// 		   $("#message-count").removeClass("shake");
        		// 	   }else{
        		// 		   $("#message-count").addClass("shake");
        		// 	   }
        		//    },1000);
        	   // }
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
    $('<div id="loading-message" class="loading"> <div class="loading-con"><img src="' + fzqblog.realpath + '/images/loading.gif"/><span>正在加载.......</span></div></div>').appendTo($("#message-load"));

    $.ajax({
			url :fzqblog.topUrl.loadMessage,
			type: 'POST',
			dataType: 'json',
			data:{status:UNREAD},
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
	console.log("搜索1");
	$("#searchBtn2").click(function(){
    	 var keyword = $.trim($("#keyWord").val());
		console.log("搜索1");
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
			searchData();


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
 function searchData(curr){
	 var articleType = $("#select").val();
	 var keyword = $.trim($("#keyWord").val());
	 if(articleType==""||keyword==""){
		 return;
	 }
	 $(".seartch-tit").eq(0).text($("#select").find("option:selected").text());
	 fzqblog.curr = curr;

	 var url = "";
	 var keyword = $.trim($("#keyWord").val());
	 if(articleType=="T"){
		 url = zuul.realpath+"/bbs/search?keyWord="+keyword;
	 }else if(articleType=="F"){
		 url = zuul.realpath+"/file/pageToShowFiles?keyWord="+keyword;
	 }
	 console.log(url)
	 window.location.assign(url);

	 // $.getJSON(fzqblog.realpath+"/searchArticle", {
	 //     pageNum: curr || 1 ,//向服务端传的参数，此处只是演示
	 //     articleType:articleType,
	 //     keyword:keyword,
	 //     countTotal:fzqblog.countTotal
	 // }, function(response){
	 //     //此处仅仅是为了演示变化的内容
	 // 		var data = response.data;
	 // 		var simplePage = data.page;
	 // 		fzqblog.countTotal=simplePage.count;
	 // 		var list = data.list;
	 // 		$("#data-list .search-item").remove();
	 // 		if (list.length > 0) {
	 // 			 for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
	 // 				  new SearchItem(d).appendTo($("#data-list"));
	 // 			 }
	 // 			 $('html,body').animate({
	 // 					scrollTop: 0
	 // 				},'fast');
	 // 		} else {
	 // 		    $('<div class="no-data search-item" >没有搜索到数据</div>').appendTo($("#data-list"));
	 // 		}
	 //显示分页
	 laypage({
		 cont: 'pager', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
		 skin: 'molv', //皮肤
		 pages: response.data.page.pageTotal, //通过后台拿到的总页数
		 curr: curr || 1, //当前页
		 jump: function(obj, first){ //触发分页后的回调
			 var keyword = $.trim($("#keyWord").val());
			 location.href = '?keyWord='+keyWord+'&pageNum='+e.curr;
		 }
	 });
	 // });
 };
</script>
<script type="text/javascript" src="${realpath}/js/fzqblog.js"></script>