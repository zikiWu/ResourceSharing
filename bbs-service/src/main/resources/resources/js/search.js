$(function(){
	searchData(1);
});
fzqblog.countTotal = 0;
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
