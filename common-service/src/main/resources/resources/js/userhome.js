fzqblog.tags=["file","topic","file-coll","topic-coll","fans","focus"]
fzqblog.centerUrl={
    loadShuoShuos:zuul.realpath+"/user/loadShuoShuos",
	loadFile:zuul.realpath+"/file/loadFile",
    loadTopic:zuul.realpath+"/bbs/loadTopic",
	loadFileColl:zuul.realpath+"/file/loadFileColl",
	loadTopicColl:zuul.realpath+"/bbs/loadTopicColl",
    loadFans:zuul.realpath+"/common/loadFans",
    loadFoucs:zuul.realpath+"/common/loadFocus",
	delFile:zuul.realpath+"/file/FileService/delFile",
	delTopic:zuul.realpath+"/bbs/topicServer/delTopic",
	delCollFile:zuul.realpath+"/file/cancelview",
	delCollTopic:zuul.realpath+"/bbs/delTopicColl"

}
$(function(){
    tag({
	id : "tag", contentClass : "tag-content", fun : function(index) {
	    var type = fzqblog.tags[index];
	    var url = window.location.href;
	    url = url.substring(0, url.lastIndexOf("#"));
	    window.location.href = url + "#" + type;
	    dispatchLoad(type);
	}
    });
    initTagContent();

});



function tag(config) {
		var id = config.id;
		var contentClass = config.contentClass;
		var event = config.event || "click";
		var fun = config.fun || function() {};
		var tag = $("#" + id);
		var lis = tag.find("li");
		$("." + contentClass).css({
			"marginTop": "10px"
		});
		console.log("tag");
		$("." + contentClass).hide();
		// $("." + contentClass).eq(1).show();
		lis.each(function(i, v) {
			$(this).attr("index", i);
		});
		lis.bind(event, function() {
			lis.removeClass("active");
			$(this).addClass("active");
			$("body").css('background-color', '#fff');
			$(this).css('border-bottom', '1px solid #fff');
			$("." + config.contentClass).hide();
			$("." + config.contentClass).eq($(this).attr("index")).show();
			fun($(this).attr("index"));
		});
	}

//初始化tag内容
function initTagContent(){
	$(".tag-content").hide();
	console.log("initTagContent");
    var url = window.location.href;
    var indexType = url.lastIndexOf("#");
    if (indexType == -1) {
    	type = "shuoshuo";
		$("body").css('background-color', '#e9f0f5');
		// $(".tag-content").eq(0).show();
    } else {
    	type = url.substring(url.lastIndexOf("#") + 1);
		$("body").css('background-color', '#fff');
		// $(".tag-content").eq(0).show();
	}
    $("ul.tag li").removeClass("active");
    $("ul.tag li[type='" + type + "']").addClass("active");
    dispatchLoad(type);
}

function dispatchLoad(type) {
	console.log("dispatchLoad");
	$(".tag-content").hide();
    switch (type) {
    case "file":// 文件
	$(".tag-content").eq(0).show();
	if($("#file-content").children().length==0){
	    loadFile(1);
	}
	break;
    case "topic":// 主题
	$(".tag-content").eq(1).show();
	if($("#topic-content").children().length==0){
	    loadTopic(1);
	}
	break;
    case "file-coll"://文件收藏
	$(".tag-content").eq(2).show();
	if($("#file-coll-content").children().length==0){
	    loadFileColl(1);
	}
	break;
    case "topic-coll"://帖子收藏
	$(".tag-content").eq(3).show();
	if($("#topic-coll-content").children().length==0){
	    loadTopicColl(1);
	}
	break;
    case "fans":// 粉丝
	$(".tag-content").eq(4).show();
	if($("#fans-content").children().length==0){
	    loadFans();
	}
	break;
    case "focus":// 关注的人
	$(".tag-content").eq(5).show();
	if($("#focus-content").children().length==0){
	    loadFocus();
	}
	break;
    default:
	break;
    }
}




	/************主题******************/

	$(document).on("click", "#shuoshuoload-more", function() {
		$(this).remove();
		loadTopic(fzqblog.topicPage + 1);

	});

function topicItem(data){
    var topic_item = $("<div class='topic-item' ></div>");
    var topic_item_info = $("<div class='topic-item-info'></div>").appendTo(topic_item);
    var topic_item_title = $("<div class='topic-item-title'></div>").appendTo(topic_item_info);

    console.log(data.topicId);

    $("<a href='http://localhost:4445/"+data.topicId+"' target='_blank' class='title'>"+data.title+"</a>").appendTo(topic_item_title);


	if($("#focus-btn").length == 0){
		$('<a href="javascript:void(0)" onclick="delTpoic(this,'+data.topicId+')" class="btn sub-new" id="post-btn" >删除</a>').appendTo(topic_item_title);
	}

    return topic_item;
}
function topicCollItem(data){
	var topic_item = $("<div class='topic-item' ></div>");
	var topic_item_info = $("<div class='topic-item-info'></div>").appendTo(topic_item);
	var topic_item_title = $("<div class='topic-item-title'></div>").appendTo(topic_item_info);

	console.log(data.topicId);

	$("<a href='http://localhost:4445/"+data.articleId+"' target='_blank' class='title'>"+data.title+"</a>").appendTo(topic_item_title);


	if($("#focus-btn").length == 0){
		$('<a href="javascript:void(0)" onclick="delCollTpoic(this,'+data.articleId+','+data.userId+')" class="btn sub-new" id="post-btn" >删除</a>').appendTo(topic_item_title);
	}
	return topic_item;
}



function loadTopic(page){
    var topic_content = $("#topic-content");
    // $('<div id="loading"> <div class="loading-con"><img src="' + fzqblog.realpath + '/images/loading.gif"/><span>正在加载.......</span></div></div>').appendTo(topic_content);
    fzqblog.topicPage= page;
    $.ajax({
    	url: fzqblog.centerUrl.loadTopic,
    	type: 'POST',
    	dataType: 'json',
    	data: { 
    	 pageNum : page,
	   	 userId: fzqblog.center.userId
	   	},
	   	success:function(res){
		   	// $("#loading").remove();
		    var list = res.data.list;
		    if (list.length == 0) {
			$("<span class='no-data'>没有帖子</span>").appendTo(topic_content);
		    }
		    var simplePage = res.data.page;
		    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
			new topicItem(d).appendTo(topic_content);
		    }
		   
		    if(simplePage.pageTotal>simplePage.pageNum){
			 	$('<div id="shuoshuoload-more" class="load-more"><a href="javascript:;">⇓加载更多</a></div>').appendTo(topic_content);
				}
		   	}
	
    });
}


function loadTopicColl(page){
	var topic_content = $("#topic-coll-content");
	// $('<div id="loading"> <div class="loading-con"><img src="' + fzqblog.realpath + '/images/loading.gif"/><span>正在加载.......</span></div></div>').appendTo(topic_content);
	fzqblog.topicPage= page;
	$.ajax({
		url: fzqblog.centerUrl.loadTopicColl,
		type: 'POST',
		dataType: 'json',
		data: {
			pageNum : page,
			userId: fzqblog.center.userId
		},
		success:function(res){
			// $("#loading").remove();
			var list = res.data.list;
			if (list.length == 0) {
				$("<span class='no-data'>没有帖子</span>").appendTo(topic_content);
			}
			var simplePage = res.data.page;
			for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
				new topicCollItem(d).appendTo(topic_content);
			}

			if(simplePage.pageTotal>simplePage.pageNum){
				$('<div id="shuoshuoload-more" class="load-more"><a href="javascript:;">⇓加载更多</a></div>').appendTo(topic_content);
			}
		}

	});
}

function fileItem(data){
	var file_item = $("<div class='topic-item'></div>");
	var file_item_info = $("<div class='topic-item-info'></div>").appendTo(file_item);
	var file_item_title = $("<div class='topic-item-title'></div>").appendTo(file_item_info);
	$("<a href='"+zuul.realpath+"/file/filecontent/?id="+data.id+"' target='_blank' class='title'>"+data.title.substring(13)+"</a>").appendTo(file_item_title);
	$('<span class="time">'+data.time+'</span>').appendTo(file_item_title);
	console.log($("#focus-btn").length);
	if($("#focus-btn").length == 0) {
		$('<a href="javascript:void(0)" onclick="delFile(this,'+data.id+')" class="btn sub-new" id="post-btn" >删除</a>').appendTo(file_item_title);
	}
	return file_item;
}
function fileCollItem(data){
	var file_item = $("<div class='topic-item'></div>");
	var file_item_info = $("<div class='topic-item-info'></div>").appendTo(file_item);
	var file_item_title = $("<div class='topic-item-title'></div>").appendTo(file_item_info);
	$("<a href='"+zuul.realpath+"/file/filecontent/?id="+data.id+"' target='_blank' class='title'>"+data.title.substring(13)+"</a>").appendTo(file_item_title);
	$('<span class="time">'+data.time+'</span>').appendTo(file_item_title);
	console.log($("#focus-btn").length);
	if($("#focus-btn").length == 0) {
		$('<a href="javascript:void(0)" onclick="delCollFile(this,'+data.id+')" class="btn sub-new" id="post-btn" >删除</a>').appendTo(file_item_title);
	}
	return file_item;
}


function loadFile(page){
	var file_content = $("#file-content");
	// $('<div id="loading"> <div class="loading-con"><img src="' + fzqblog.realpath + '/images/loading.gif"/><span>正在加载.......</span></div></div>').appendTo(topic_content);
	fzqblog.topicPage= page;
	$.ajax({
		url: fzqblog.centerUrl.loadFile,
		type: 'POST',
		dataType: 'json',
		data: {
			pageNum : page,
			userName: fzqblog.center.userName
		},
		success:function(res){
			// $("#loading").remove();
			var list = res.list;
			if (list.length == 0) {
				$("<span class='no-data'>没有资源</span>").appendTo(file_content);
			}
			var simplePage = res.page;
			for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
				new fileItem(d).appendTo(file_content);
			}

			if(simplePage.pageTotal>simplePage.pageNum){
				$('<div id="shuoshuoload-more" class="load-more"><a href="javascript:;">⇓加载更多</a></div>').appendTo(file_content);
			}
		}

	});
}


function loadFileColl(page){
	var file_content = $("#file-coll-content");
	// $('<div id="loading"> <div class="loading-con"><img src="' + fzqblog.realpath + '/images/loading.gif"/><span>正在加载.......</span></div></div>').appendTo(topic_content);
	fzqblog.topicPage= page;
	$.ajax({
		url: fzqblog.centerUrl.loadFileColl,
		type: 'POST',
		dataType: 'json',
		data: {
			pageNum : page,
			userName: fzqblog.center.userName
		},
		success:function(res){
			// $("#loading").remove();
			var list = res.list;
			if (list.length == 0) {
				$("<span class='no-data'>没有资源</span>").appendTo(file_content);
			}
			var simplePage = res.page;
			for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
				new fileCollItem(d).appendTo(file_content);
			}

			if(simplePage.pageTotal>simplePage.pageNum){
				$('<div id="shuoshuoload-more" class="load-more"><a href="javascript:;">⇓加载更多</a></div>').appendTo(file_content);
			}
		}

	});
}




$(document).on("click", ".showImage", function() {
	var bimg = $(this).find("img").attr("src");
		// var d = dialog({
		//     title: '图片查看',
		//     content: '<div style="text-align:center"><img src="' + bimg + '"style="max-width:1300px;"></div>'
		// });
		// d.showModal();
		layer.open({
		maxWidth:1300,
        content: '<div style="text-align:center"><a href="' + bimg  + '" target="_blank"><img src="' + bimg + '"style="max-width:1300px;"></a></div>'
    });

});

	$(document).on("click", "#askload-more", function() {
		$(this).remove();
		loadAsk(fzqblog.askPage + 1);

	});
	$(document).on("click", "#knowledgeload-more", function() {
		$(this).remove();
		loadKnowledge(fzqblog.knowledgePage + 1);

	});


/********粉丝，关注*********/

function loadFans(){
    var topic_content = $("#fans-content");
    $('<div id="loading"> <div class="loading-con"><img src="' + fzqblog.realpath + '/images/loading.gif"/><span>正在加载.......</span></div></div>').appendTo(topic_content);
    $.ajax({
	 url : fzqblog.centerUrl.loadFans, 
	 type: 'POST',
	 dataType: 'json',
		data : {
	    	friendUserId:fzqblog.center.userId
		}, 
		success:function(res) {
	    $("#loading").remove();
	    topic_content.empty();
	    var list = res.data.list;
	    if (list.length == 0) {
		$("<span class='no-data'>没有粉丝</span>").appendTo(topic_content);
	    }
	    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {
			console.log("没有关注的人"+d.userId+d.userIcon+d.userName);
		$("<li><a href='"+zuul.realpath+"/common/"+d.userId+"'><img  src='"+fzqblog.realpath+"/images/"+d.userIcon+"'><span>"+d.userName+"</span></a></li>").appendTo(topic_content);
	    }
	}
    });
}

function delTpoic(id,topicId){
	$(id).parent().parent().parent().hide();
	$.ajax({
		url: fzqblog.centerUrl.delTopic,
		type: 'POST',
		dataType: 'json',
		data: {
			topicId: topicId
		},
		success: function (res) {
			layer.msg('删除帖子成功', {icon: 6,time:1000});
		}
	});
}
function delCollTpoic(id,articleId,userId){
	$(id).parent().parent().parent().hide();
	$.ajax({
		url: fzqblog.centerUrl.delCollTopic,
		type: 'POST',
		dataType: 'json',
		data: {
			articleId: articleId,
			userId:userId
		},
		success: function (res) {
			layer.msg('删除帖子成功', {icon: 6,time:1000});
		}
	});
}
function delFile(id,fileId){
	$(id).parent().parent().parent().hide();
	$.ajax({
		url: fzqblog.centerUrl.delFile,
		type: 'POST',
		dataType: 'json',
		data: {
			id: fileId
		},
		success: function (res) {
			layer.msg('删除文件成功', {icon: 6,time:1000});
		}
	});
}
//删除收藏
function delCollFile(id,fileId){
	$(id).parent().parent().parent().hide();
	$.ajax({
		url: fzqblog.centerUrl.delCollFile,
		type: 'POST',
		dataType: 'json',
		data: {
			fileid: fileId
		},
		success: function (res) {
			layer.msg('删除收藏文件成功', {icon: 6,time:1000});
		}
	});
}


function loadFocus(){
    var topic_content = $("#focus-content");
    $('<div id="loading"><div class="loading-con"><img src="' + fzqblog.realpath + '/images/loading.gif"/><span>正在加载.......</span></div></div>').appendTo(topic_content);
    $.ajax({
    	url : fzqblog.centerUrl.loadFoucs, 
		type: 'POST',
		dataType: 'json',	
		data:{
		    userId:fzqblog.center.userId
		}, 
		success:function(res){
			 $("#loading").remove();
		    topic_content.empty();
		    var list = res.data.list;
		    if (list.length == 0) {
			$("<span class='no-data'>没有关注</span>").appendTo(topic_content);
		    }
		    for (var i = 0, _len = list.length, d; i < _len, d = list[i]; i++) {

				console.log("粉丝"+d.friendUserId);
		    	$("<li><a href='"+zuul.realpath+"/common/"+d.friendUserId+"'><img   src='"+fzqblog.realpath+"/images/"+d.friendUserIcon+"'><span>"+d.friendUserName+"</span></a></li>").appendTo(topic_content);
			// $("<li><a href='"+zuul.realpath+"/common/"+d.userId+"'><img  src='"+fzqblog.realpath+"/images/"+d.userIcon+"'><span>"+d.userName+"</span></a></li>").appendTo(topic_content);
		    }

		}
    });
}

