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
	if(searchType=="T"){
		url = zuul.realpath+"/bbs/search?keyWord="+keyword;
	}else if(searchType=="F"){
		url = zuul.realpath+"/file/pageToShowFiles?keyWord="+keyword;
		search_onclick();
	}

};
