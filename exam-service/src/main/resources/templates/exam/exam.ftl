<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<#include "../include/top.ftl">
<link rel="stylesheet" type="text/css" href="${realpath}/css/exam.css">
<script type="text/javascript" src="${realpath}/js/exam.js"></script>
<title>在线考试</title>
</head>
<body>
		<#include "../include/header.ftl">
		<form action="${realpath}/exam/preDoExam" method="post">
		<div class="main">
		<label class="checkbox-inline">
			<input type="radio" name="categoryId"  
		         value="0" checked>全部考题</label>
		<#list categoryList as item>
		<label class="checkbox-inline">
			<input type="radio" name="categoryId"  
		         value="${item.categoryId!''}">${item.name!''}</label>
		</#list>
		</form>
		<button type="submit" class="btn btn-info" id="loadExam">进入考试</button>
		<a href="${realpath}/exam/addExam" class="btn btn-success">我要出题</a>
		<div class="examiers">发题人</div>
		<div class="examiers-detail" id="examiers-detail">
			
		</div>
		</div>
		<#include "../include/footer.ftl">
</body>
</html>