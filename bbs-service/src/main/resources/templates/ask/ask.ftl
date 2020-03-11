<!DOCTYPE html>
<html lang="en">
<head>
<#include "../include/top.ftl">
<link rel="stylesheet" type="text/css" href="${realpath}/css/ask.css">
<script type="text/javascript" charset="utf-8" src="${realpath}/laypage/laypage.js"></script>
<script text="text/javascript">
	fzqblog.pageTotal = ${result.page.pageTotal}
</script>
	<meta charset="UTF-8">
	<title>问答</title>
</head>
<body>
<#include "../include/header.ftl">

	<div class="main">
		<div class="left">
			<div class="tag">
				<a href="${realpath}/ask" <#if haveSolved == 0>class="active"</#if>>待解决问题</a>
				<a href="${realpath}/ask?solveType=1" <#if haveSolved == 1>class="active"</#if>>已解决问题</a>
			</div>
			<div class="left-main">
			<#if (result.page.pageTotal>0)>
			<#list result.list as item>
				<div class="title-item">
		                	<div class="title">
		                		<a href="${realpath}/ask/${(item.askId)?c}">${item.title}</a>
		                		<span class="mark"><i class="icon icon-mark"></i>&nbsp;赏金&nbsp;${item.mark}</span>
		                		<span class="time">${item.createTimeString}</span>
		                		<span class="answer">${item.commentCount!'0'}人回答</span>
		                		<div class="clear"></div>
		                	</div>
		                	<div class="summary">
		                		${item.summary}
		                	</div>
		        </div>
		    </#list>
		    <#else>
				<div class="no-data">没有帖子，赶紧抢沙发吧</div>
		    </#if>
		    <div id="page" class="page"></div>
			</div>
		</div>
		
		<div class="right">
			<div class="count-info">
				<p><span class="day-count">今日问题：${todayAskCount!'0'} </span><span>今日解决：${todaySolvedCount!'0'}</span></p>
				<p><span class="day-count">所有问题：${totalAsk!'0'}</span> <span>总共解决：${totalSolved!'0'} </span></p>
				<button class="btn btn-info toAsk" id="toAsk">我要提问</button>
			</div>
			<div class="rang">
			<div class="rang-title">
				<span class="rang-bang">封神榜</span>
				<span class="rang-count">采纳数</span>
			</div>
			<div class="rang-list">
				<#if (topUsers?size>0)>
				<#list topUsers as topUser>
					<div class="rang-item">
						<div class="rang-user"><span class="rang-num rang-num1">1</span><a href="${realpath}/user/${(topUser.userId?c)}">${topUser.userName}</a></div>
						<span class="rang-item-count">${topUser.solveCount}</span>
						<div class="clear"></div>
					</div>
				</#list>
				<#else>
				<div class="no-data">没有人被采纳，赶紧抢沙发吧</div>
				</#if>
			</div>
		</div>
		</div>
		<div class="clear"></div>
	</div>
<script type="text/javascript" src="${realpath}/js/ask.js"></script>
<#include "../include/footer.ftl">	
</body>
</html>