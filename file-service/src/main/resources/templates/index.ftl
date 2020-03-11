<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "include/top.ftl">
    <title>资料分享系统</title>

    <script type="text/javascript" src="${realpath}/jquery-easyui-1.5.5.2/jquery.min.js"></script>
    <script type="text/javascript" src="${realpath}/js/vue.min.js"></script>
    <style type="text/css">
        *{
            margin: 0;
            padding: 0;
        }
        .clear{
            clear: both;
        }
        .clearfix{
            *zoom:1;
        }
        .clearfix:after{
            visibility: hidden;
            display: block;
            content: " ";
            clear: both;
            height: 0;
        }
        #head{
            width: 100%;
            height: 30px;
            border-bottom: 1px solid #dddddd;
            font-size: 14px;
            background-color: #e6f3fd;
        }
        .left{
            float: left;
            color: gray;
            line-height: 30px;
        }
        .right li{
            float: right;
            list-style: none;
            line-height: 30px;
        }
        .right li a{
            text-decoration: none;
            color: gray;
        }
        .right li a:hover{
            color: blue;
        }
        .right_login{
            margin-right: 60px;
            margin-left: 6px;
        }
        .right_register{
            margin-right: 12px;
        }
        .right .un{
        margin-right: 60px;
        }
        .line{
            padding-top: 8px;
        }
        .line span{
            border-left: 2px solid gray;
            display: inline-block;
            height: 16px;
        }
        #title{
            width: 100%;
            height: 70px;
            background-color: #e6f3fd;
            margin: 0 auto;
        }
        .title_wrap{
            width: 950px;
            height: 70px;
            margin: 0 auto;
        }
        .title_wrap .title_img1{
            float: left;
            border: none;
        }
        .title_wrap .search_wrap{
            float: left;
            margin-left: 35px;
            margin-top: 15px;
            text-align: left;
        }
        .title_wrap .search1{
            border: 1px solid #bfc3c2;
            width: 387px;
            height: 28px;
            margin-right: 8px;
            vertical-align: middle;
            line-height: 28px;
        }
        .title_wrap .search2{
            width: 64px;
            height: 31px;
            background-color: #3586f4;
            border: none;
            vertical-align: middle;
            color: #ffffff;
            cursor: pointer;
        }
        .title_wrap .upload_a{
            float: right;
            margin-top: 5px;
            margin-right: 10px;
        }
        .nav2{
            width: 950px;
            margin: 0 auto;
            height: 40px;
            border: none;
            position: relative;
            margin-top: 10px;
            padding-top: 10px;
            margin-bottom: 10px;
        }
        .nav2 span{
            height: 40px;
            position: absolute;
            bottom: 0px;
            left: 0px;
        }
        .nav2 span a{
            height: 40px;
            line-height: 40px;
            padding: 0px 20px 0px 20px;
            display: block;
            float: left;
            margin-left: 10px;
            font-size: 14px;
            color: #333333;
            background-color: #eeeeee;
            text-decoration: none;
        }
        .nav span a.on,.nav span a:hover{
            height: 42px;
            border-bottom: none;
            background: url("${realpath}/img/jiantoutop.gif") bottom center no-repeat #3586f4;
            color: #ffffff;
        }
        .childnavbox{
            width: 950px;
            margin: 0 auto;
        }
        .childnavbox ul.child_nav{
            width: 946px;
            clear: both;
            background-color: #ffffff;
            padding-top: 10px;
            padding-bottom: 10px;
            border: 1px solid #3586f4;
            border-radius: 10px;
            margin-top: 0px;
        }
        .childnavbox ul.child_nav li{
            width: 100%;
            clear: both;
            text-align: left;
            height: auto;
            min-height: 20px;
            line-height: 20px;
            margin-left: 1px;
            margin-top: 1px;
            color: #dddddd;
            overflow: visible;
            list-style-type: none;
        }
        .childnavbox ul.child_nav li a.f_link{
            color: #3586f4;
            font-weight: bold;
            margin-right: 20px;
            float: left;
            width: 10%;
            text-align: center;
            text-decoration: none;
        }
        .childnavbox ul.child_nav li a{
            font-size: 12px;
            font-weight: normal;
            color: #666666;
            padding: 2px;
            text-decoration: none;
        }
        .childnavbox ul.child_nav li div.c_links{
            width: 85%;
            float: left;
        }
        .child_nav{
            display: none;
        }
        .index_content{
            border: none;
            width: 950px;
            clear: both;
            margin: 0 auto;
            height: auto;
            font-family: "宋体";
            padding: 0;
        }
        .index_main{
            float: left;
            border-right: 1px solid #ddd;
            width: 74%;
            height: auto;
            margin-left: 0;
            overflow: hidden;
            background-color: #fff;
        }
        h3.list_h{
            width: 98%;
            margin: 0 auto;
            clear: both;
            line-height: 28px;
            height: 28px;
            overflow: hidden;
            text-align: left;
            margin-top: 10px;
        }
        h3.list_h a.on,h3.list_h a:hover{
            color: #3586f4;
            background: url("${realpath}/img/down_blue.gif") right no-repeat;
        }
        h3.list_h a{
            width: 105px;
            height: 28px;
            display: inline-block;
            font-size: 14px;
            color: #999;
            margin-right: 10px;
            text-align: center;
            background: url("${realpath}/img/down_gray.gif") right no-repeat;
            text-decoration: none;
        }
        .list_main ul{
            width: 100%;
            height: auto;
            margin: 0 auto;
            padding: 0;
            margin-bottom: 20px;
        }
        .list_main ul li{
            clear: both;
            width: 98%;
            margin: 0 auto;
            margin-top: 10px;
            height: auto;
            list-style: none;
            border-bottom: 1px dashed #D5D5D5;
        }
        .icon {
            vertical-align: middle;
        }
        .icon-user-name {
            display: inline-block;
            width: 20px;
            height: 20px;
            background-position: 0px -430px;
        }
        .icon-comment {
            display: inline-block;
            width: 20px;
            height: 20px;
            background-position: 0px -360px;
        }
        .icon-collection {
            display: inline-block;
            width: 20px;
            height: 20px;
            background-position: 0px -760px;
        }
        .list_main ul li h2{
            width: 600px;
            clear: both;
            height: 30px;
            line-height: 30px;
            overflow: hidden;
            margin-left: 85px;
            margin-bottom: 5px;
        }
        .list_main ul li h2 a{
            font-size: 16px;
            color: #333333;
            float: left;
            margin-left: 10px;
            font-family: "Microsoft YaHei",微软雅黑,"MicrosoftJhengHei",华文细黑,STHeiti,MingLiu;
            font-weight: normal;
            text-decoration: none;
        }
        .list_main ul li h2 a.check_discuss{
            float: right;
            font-size: 12px;
            font-weight: normal;
            color: #858585;
            margin-right: 10px;
        }
        .list_main ul li h2 em{
            color: #fe3535;
            font-style: normal;
        }
        .list_main_inside{
            height: 88px;
        }
        .list_inside_left{
            margin-left: 20px;
        }
        .left_a{
            display: block;
            float: left;
            margin-right: 8px;
            margin-top: 5px;
            text-decoration: none;
        }
        .left_a img{
            border: none;
            width: 48px;
            height: 48px;
        }
        .left_div{
            float: left;
            font-size: 12px;
            color: #6e6e6e;
            text-align: left;
            line-height: 2em;
            margin-left: 20px;
        }
        .left_div span{
            margin-right: 20px;
        }
        .list_inside_right{
            float: right;
        }
        .star{
            width: 59px;
            height: 20px;
            background: url("${realpath}/img/star_0.gif") left no-repeat;
            position: relative;
            display: inline-block;
            vertical-align: middle;
            margin-right: 5px;
        }
        .star span{
            width: 59px;
            height: 20px;
            background: url("${realpath}/img/star_5.gif") left no-repeat;
            position: absolute;
            top: 0;
            left: 0;
        }
        a.right_a{
            float: right;
            font-size: 14px;
            background-color: #428bca;
            color: #fff;
            border: 1px solid #428bca;
            padding: 5px 10px 5px 10px;
            font-family: "Microsoft YaHei",微软雅黑,"MicrosoftJhengHei",华文细黑,STHeiti,MingLiu;
            text-decoration: none;
            margin-top: 10px;
        }
        a.right_a:hover{
            background-color: #fbf0df;
            color: #f60;
            border: 1px solid #f60;
        }
        ul.list_pages{
            width: 90%;
            margin: 0 auto;
            padding: 0;
        }
        .list_pages li{
            float: left;
            list-style: none;
        }
        .list_pages li a{
            display: block;
            text-decoration: none;
            padding: 5px 10px 5px 10px;
            margin-right: 5px;
            border: 1px solid #e4e4e4;
            color: #0b589e;
            font-size: 12px;
        }
        .list_pages li a.on,.list_pages li a:hover{
        	background: #9DCEFF;
        }
        .index_sidebar{
            float: right;
            width: 24%;
            height: auto;
            overflow: hidden;
            text-align: center;
            margin-right: 10px;
        }
        .list_sidebardown{
            width: 98%;
            margin: 0 auto;
            clear: both;
        }
        .list_sidebar_span{
            text-align: left;
            display: block;
            height: 30px;
            line-height: 30px;
            font-size: 14px;
            color: #333;
            margin: 0;
            padding: 0;
            font-weight: normal;
            font-family: "Microsoft YaHei",微软雅黑,"MicrosoftJhengHei",华文细黑,STHeiti,MingLiu;
            margin-top: 10px;
            font-weight: bold;
            border-bottom: 2px solid #ddd;
        }
        .list_sidebar_title{
            height: 40px;
            margin-bottom: 10px;
        }
        .list_sidebardown .a7{
            display: block;
            float: left;
            padding: 6px 10px 6px 10px;
            font-size: 12px;
            color: #333;
            text-decoration: none;
            border-top: 1px solid #ddd;
            border-left: 1px solid #ddd;
            border-right: 1px solid #ddd;
            margin-top: 10px;
        }
        .list_sidebardown .a8{
            display: block;
            float: left;
            padding: 6px 10px 6px 10px;
            font-size: 12px;
            color: #999;
            text-decoration: none;
            border-bottom: 1px solid #ddd;
            margin-top: 10px;
        }
        .list_sidebardown ul{
            width: 98%;
            margin: 0 auto;
            padding: 0;
            text-indent: 0px;
        }
        .list_sidebardown ul li {
            list-style: none;
            font-size: 12px;
            clear: both;
            text-align: left;
            line-height: 29px;
            height: 40px;
            padding: 0;
            margin: 0;
            text-indent: 0px;
        }
        .list_sidebardown ul li span.list_sidebar_left {
            color: #666;
            width: auto;
            float: left;
        }
        .list_sidebardown ul li span.list_sidebar_right {
            color: #999;
            width: auto;
            float: right;
        }
        .footer_wrap{
            width: 950px;
            margin: 0 auto;

        }
        .new_footer {
            width: 100%;
            height: auto;
            clear: both;
            line-height: 24px;
            font-size: 12px;
            font-family: "微软雅黑","黑体";
            color: #999;
            margin: 0 auto;
            padding: 5px 0 40px 0;
            text-align: left;
            border-top: 10px solid #9DCEFF;
            margin-top: 10px;
            font-weight: bold;
        }
        ul.rank_ul li {
            width: 100%;
            clear: both;
            text-align: left;
            line-height: 26px;
            font-size: 12px;
            list-style: none;
            color: #999;
            margin-bottom: 5px;
            overflow: hidden;
            text-overflow:ellipsis;
            white-space: nowrap;
        }
        ul.rank_ul li em{
            color: #555;
            font-family: Arial, Helvetica, sans-serif;
            font-weight: bold;
            width: 19px;
            height: 26px;
            display: inline-block;
            text-align: center;
        }
        ul.rank_ul li em.toprank{
            background: url("${realpath}/img/rank.gif") top left no-repeat;
            color: #ffffff;
        }
        ul.rank_ul li a{
            text-decoration: none;
            height: 26px;
            overflow: hidden;
            text-overflow:ellipsis;white-space:norwap;
            line-height: 26px;
            font-size: 12px;
            color: #666;
        }
        .public-btn {
            width: 225px;
            height: 50px;
            display: block;
            text-align: center;
            font-size: 20px;
            font-weight: bold;
            vertical-align: bottom;
            margin-top: 10px;
        }
        .img-circle {
            border-radius: 50%;
            width: 35px;
            height: 35px;
            margin-right: 5px;
        }
    </style>
</head>
<body>
<#include "include/header.ftl">
<#--<div id="head">-->
    <#--<script type="text/javascript">-->
        	<#--var temp = "${name}";-->
        	<#--if(temp==null || temp==''){-->
        		<#--$(".a_hide").css("display","inline-block");-->
        		<#--$(".un").css("display","none");-->
        	<#--}else{-->
        		<#--$(".a_hide").css("display","none");-->
        		<#--$(".un").css("display","inline-block");-->
        	<#--}	-->
        <#--</script>-->
<#--</div>-->
<#--<div id="title">-->
    <#--<div class="title_wrap">-->
        <#--<img class="title_img1" src="${realpath}/img/xuepan.jpg">-->
        <#--<div class="search_wrap">-->
            <#--<input class="search1" type="text">-->
            <#--<input class="search2" type="button" value="搜索" onclick="search_onclick()">-->
        <#--</div>-->
        <#--<a class="upload_a" href="${zuulpath}/uploadfile"><img src="${realpath}/img/upload.jpg"></a>-->
    <#--</div>-->
<#--</div>-->
<div class="nav2">
    <span>
        <a href="javascript:void(0);" class="show1" onclick="find1(0);return false;">全部资料</a>
        <a href="javascript:void(0);" class="show2" onclick="find1(1);return false;">考试资料</a>
        <a href="javascript:void(0);" class="show3" onclick="find1(2);return false;">作业课件</a>
        <a href="javascript:void(0);" class="show4" onclick="find1(3);return false;">IT数码</a>
        <a href="javascript:void(0);" class="show5" onclick="find1(4);return false;">论文读物</a>
        <a href="javascript:void(0);" class="show6" onclick="find1(5);return false;">影音视频</a>
        <a href="javascript:void(0);" class="show7" onclick="find1(6);return false;">创意图库</a>
    </span>
</div>
<div class="childnavbox" id="vue_classify">
    <ul class="child_nav show1"></ul>
    <ul v-for="(arr1,index1) in classify_arr" v-bind:class="'child_nav show'+(index1+2)">
        <li v-for="(arr2,index2) in arr1">
            <a class="f_link" href="javascript:void(0);" v-bind:onclick="'find2('+arr2[0].id+')'">{{arr2[0].name}}</a>
            <div class="c_links">
                <template v-for="(obj,index3) in arr2">
                	<template v-if="index3 > 0">
                    	<a href="javascript:void(0);" v-bind:onclick="'find3('+obj.id+')'">{{obj.name}}</a>&nbsp;|&nbsp;
                    </template>
                </template>
            </div>
            <div class="clear"></div>
        </li>
    </ul>
</div>
<script type="text/javascript">
    $(function () {
        var show_arr = ["show2","show3","show4","show5","show6","show7"];
        $(".nav2 span a").mouseover(function () {
            $(".nav2 span a").removeClass("on");
            $(".childnavbox .child_nav").hide();
            var obj = $(this);
            obj.addClass("on");
            $.each(show_arr,function (index,value) {
                if(obj.hasClass(value)){
                    $(".childnavbox ."+value).show();
                    return false;
                }
            });
        });
    });
</script>
<div class="index_content clearfix">
    <div class="index_main">
        <div class="list_main" id="vue_list">
            <h3 class="list_h">
                <a href="javascript:void(0);" class="on" onclick="timefunc(1)" id="time1">按上传时间</a>
                <a href="javascript:void(0);" onclick="timefunc(2)" id="time2">按人气排行</a>
            </h3>
            <ul>
                <li v-for="list_obj in list_obj_arr">
                    <h2>
                        <a v-bind:href="'${realpath}/filecontent?id='+list_obj.id" v-html="list_obj.title.substring(13)">{{list_obj.title.substring(13)}}</a>
                        <a v-bind:href="'${realpath}/filecontent?id='+list_obj.id" class="check_discuss">查看全部条评论</a>
                    </h2>
                    <div class="list_main_inside">
                        <div class="list_inside_left">
                            <a class="left_a" v-bind:href="'${realpath}/filecontent?id='+list_obj.id">
                                <img v-bind:src="'${realpath}/img/'+list_obj.type+'.gif'">
                            </a>
                            <div class="left_div">
                                <span>上传时间：{{list_obj.time}}</span>
                                <span>大小：{{list_obj.size}}kb</span>
                                <br>
                                <i class="icon icon-collection"></i>
                                <span>
                                    收藏：<em>{{list_obj.attention}}</em>
                                </span>
                                <i class="icon icon-user-name"></i>
                                上传者：{{list_obj.author}}
                                <span><i class="icon icon-comment"></i>评价：{{list_obj.evaluate}}</span>
                            </div>
                        </div>
                        <div class="list_inside_right">
                            <div class="star">
                                <span></span>
                            </div>
                            <div class="clear"></div>
                            <a class="right_a" href="javascript:void(0);" v-bind:onclick="'downloadfile('+list_obj.id+')'">立即下载</a>
                            <span style="display:none" v-bind:id="'sss'+(list_obj.id)">{{list_obj.money}}</span>
                        </div>
                    </div>
                </li>
            </ul>
            <a style="display:none" id="d_hide_a"  target="_blank" href="" download><span id="d_hide_span">&nbsp;</span></span></a>
        </div>
        <ul class="list_pages">
            <li><a class="on" href="#" onclick="pageclick(this,1);return false;">1</a></li>
            <li><a href="#" onclick="pageclick(this,2);return false;">2</a></li>
            <li><a href="#" onclick="pageclick(this,3);return false;">3</a></li>
            <li><a href="#" onclick="pageclick(this,4);return false;">4</a></li>
            <li><a href="#" onclick="pageclick(this,5);return false;">5</a></li>
            <li><a href="#" onclick="pageclick(this,6);return false;">6</a></li>
            <li><a href="#" onclick="pageclick(this,7);return false;">下一页</a></li>
        </ul>
    </div>
    <script type="text/javascript">
    function downloadfile(id) {
		var mtip = "下载需要消耗"+$("#sss"+id).html()+"个积分";
    	if(confirm(mtip)){
    		$.ajax({
         		type:"GET",
         		url:"${zuulpath}/file/downloadfile?id="+id,
         		async:true,
         		success:function(result){
         			if(result.flag==1){
         				window.location.href=result.fileurl;
         			}else if(result.flag==2){
         				alert(result.message);
         			}else{
         				//alert(result.fileurl);
         				//window.location.href = result.fileurl;
         				//document.getElementById("d_hide_a").href=result.fileurl;
         				//document.getElementById("d_hide_a").click();
         				$("#d_hide_a").prop('href',result.fileurl);
         				var d_filename = result.fileurl.substring(result.fileurl.lastIndexOf("/")+1).substring(13);
         				$("#d_hide_a").prop('download',d_filename);
         				$("#d_hide_span").click();
         			}
         		},
         		error:function(){
         				
         		}
         	});
    	}else{
    		return false;
    	}
	}
    </script>
    <div class="index_sidebar">
        <div>
            <a href="${zuulpath}/file/uploadfile" class="btn btn-info public-btn" id="publicFileBtn">
                <i class="icon icon-post-topic"></i>
                <span>我要上传</span>
            </a>
        </div>
        <div class="list_sidebardown">
            <span class="list_sidebar_span">用户贡献榜</span>
            <ul>
                <li><span class="list_sidebar_left">用户名</span><span class="list_sidebar_right">上传文件数</span></li>
                <#list upload_al as upload_obj>
                    <#if upload_obj_index <= 10>
                    <li class="weekexp" style="display: list-item;">
                        <img class="img-circle" src="http://localhost:4445/images/${upload_obj.userIcon}" style="display:inline;">
                        <a class="list_sidebar_left" href="${zuulpath}/common/${upload_obj.userid?c }">${upload_obj.userName }</a><span class="list_sidebar_right">${upload_obj.fileNum}</span></li>
                    </#if>
                </#list>
            </ul>
            <span class="list_sidebar_span">下载排行</span>
            <ul class="rank_ul">
            <#list download_al as download_obj>
                <li>
                    <#if download_obj_index <= 3>
                        <em class="toprank">${download_obj_index}</em>
                        <a href="${realpath}/filecontent?id=${download_obj.id}" title="${download_obj.title?substring(0,13)}">${download_obj.title?substring(13)}</a>
                    <#else>
                        <em class="">${download_obj_index}</em>
                        <a href="${realpath}/filecontent?id=${download_obj.id}" title="${download_obj.title?substring(0,13)}">${download_obj.title?substring(13)}</a>
                    </#if>
                </li>
            </#list>
            </ul>
        </div>
    </div>
</div>

</body>
<script type="text/javascript">
var nosearch = 2;//1搜索，2分类
var whichfind = 1;//1,2,3
var findid = 0;

	var timeflag = 1;
	function timefunc(flag) {
		pagenum=1;
		for(var i=0;i<6;i++){
           var j = i+1;
           $("ul.list_pages li a").eq(i).html(j);
           $("ul.list_pages li a").eq(i).removeClass("on");
       }
		$("ul.list_pages li a").eq(6).html("下一页");
		$("ul.list_pages li a").eq(0).addClass("on");
		
		if(flag==1){
			$("#time2").removeClass("on");
			$("#time1").addClass("on");
			timeflag = 1;
		}else if(flag==2){
			$("#time1").removeClass("on");
			$("#time2").addClass("on");
			timeflag = 2;
		}
		$.ajax({
     		type:"GET",
     		url:"${zuulpath}/file/showfiles?timeflag="+timeflag,
     		async:true,
     		success:function(result){
     			vue_list_obj.list_obj_arr = result;
     		},
     		error:function(){
     				
     		}
     	});
	}
	
    var vue_classify = new Vue({
        el:'#vue_classify',
        data:{
            classify_arr:[]
        }
    });
    $.ajax({
		type:"GET",
		url:"${zuulpath}/file/classifyData?timeflag="+timeflag,
		async:true,
		success:function(result){
			vue_classify.classify_arr = result;
		},
		error:function(){
				
		}
	});
    
    var pagenum = 1;
    var start = 1;
    function pageclick(e,eindex) {
    	for(var ei=0;ei<6;ei++){
    			$("ul.list_pages li a").eq(ei).removeClass("on");
    	}
        pagenum = parseInt(pagenum);
        var text = e.innerHTML;
        if(text=="上一页"){pagenum = pagenum-1;}
        else if(text=="下一页"){pagenum = pagenum+1;}
        else{pagenum = text;}
        if(pagenum==1){
            for(var i=0;i<6;i++){
                var j = i+1;
                $("ul.list_pages li a").eq(i).html(j);
            }
        }else{
            if(pagenum==2||pagenum==3||pagenum==4){start = 1;}
            else{start = pagenum-3;}
            $("ul.list_pages li a").eq(0).html("上一页");
            for(var p=1;p<6;p++){
                start = start+1;
                $("ul.list_pages li a").eq(p).html(start);
            }
        }
        for(var vei=0;vei<6;vei++){
        	if($("ul.list_pages li a").eq(vei).html()==pagenum){
        		$("ul.list_pages li a").eq(vei).addClass("on");
        	}
        }
        
        //alert(nosearch+" "+whichfind+" "+findid+" "+pagenum+" "+content_search1+" "+timeflag);
        $.ajax({
     		type:"GET",
     		url:"${zuulpath}/file/pageToShowFiles?nosearch="+nosearch+"&whichfind="+whichfind+"&findid="+findid+"&pagenum="+pagenum+"&content_search1="+content_search1+"&timeflag="+timeflag,
     		async:true,
     		success:function(result){
     			if(nosearch==1){
     				if(content_search1!=null&&content_search1!=undefined&&content_search1!=""){
         				for(index in result){
             				result[index]['title'] = result[index]['title'].replace(new RegExp(content_search1,'g'),"<span style='color:red'>"+content_search1+"</span>");
             			}
         			}
     			}
     			vue_list_obj.list_obj_arr = result;
     		},
     		error:function(){
     				
     		}
     	});
    }
    
    var list_data = [{title:'考研政治毛泽东思想概论复习指导.txt',author:'lost',time:'2013-01-23 03:57:11',size:'2.37Kb',attention:61,evaluate:0,score:5,type:'txt',discuss:10},
                     {title:'考研政治毛泽东思想概论复习指导.txt',author:'lost',time:'2013-01-23 03:57:11',size:'2.37Kb',attention:61,evaluate:0,score:5,type:'txt',discuss:10},
                     {title:'考研政治毛泽东思想概论复习指导.txt',author:'lost',time:'2013-01-23 03:57:11',size:'2.37Kb',attention:61,evaluate:0,score:5,type:'txt',discuss:10},
                     {title:'考研政治毛泽东思想概论复习指导.txt',author:'lost',time:'2013-01-23 03:57:11',size:'2.37Kb',attention:61,evaluate:0,score:5,type:'txt',discuss:10},
                     {title:'考研政治毛泽东思想概论复习指导.txt',author:'lost',time:'2013-01-23 03:57:11',size:'2.37Kb',attention:61,evaluate:0,score:5,type:'txt',discuss:10}];
                 var vue_list_obj = new Vue({
                     el:'#vue_list',
                     data:{
                         list_obj_arr:[]
                     }
                 });
                 $.ajax({
             		type:"GET",
             		url:"${zuulpath}/file/showfiles?timeflag=1",
             		async:true,
             		success:function(result){
             			vue_list_obj.list_obj_arr = result;
             		},
             		error:function(){
             				
             		}
             	});
   var content_search1 = "";
   function search_onclick(keyWord) {
	   nosearch = 1;
	   
		content_search1 = keyWord;
		pagenum=1;
		for(var i=0;i<6;i++){
            var j = i+1;
            $("ul.list_pages li a").eq(i).html(j);
            $("ul.list_pages li a").eq(i).removeClass("on");
        }
		$("ul.list_pages li a").eq(6).html("下一页");
		$("ul.list_pages li a").eq(0).addClass("on");
		$.ajax({
     		type:"GET",
     		url:"${zuulpath}/file/showfiles?timeflag="+timeflag+"&content="+content_search1+"&pagenumber="+pagenum,
     		async:true,
     		success:function(result){
     			if(content_search1!=null&&content_search1!=undefined&&content_search1!=""){
     				for(index in result){
         				result[index]['title'] = result[index]['title'].replace(new RegExp(content_search1,'g'),"<span style='color:red'>"+content_search1+"</span>");
         			}
     			}
     			vue_list_obj.list_obj_arr = result;
     		},
     		error:function(){
     				
     		}
     	});
	}
   
   function find1(id) {
	   nosearch = 2;
	   whichfind = 1;
	   findid = id;
	   
	   pagenum=1;
		for(var i=0;i<6;i++){
           var j = i+1;
           $("ul.list_pages li a").eq(i).html(j);
           $("ul.list_pages li a").eq(i).removeClass("on");
       }
		$("ul.list_pages li a").eq(6).html("下一页");
		$("ul.list_pages li a").eq(0).addClass("on");
	   $.ajax({
    		type:"GET",
    		url:"${zuulpath}/file/showfiles/find1?id="+id+"&timeflag="+timeflag+"&pagenum="+pagenum,
    		async:true,
    		success:function(result){
    			vue_list_obj.list_obj_arr = result;
    		},
    		error:function(){
    				
    		}
    	});
	}
   
   function find2(id) {
	   nosearch = 2;
	   whichfind = 2;
	   findid = id;
	   
	   pagenum=1;
		for(var i=0;i<6;i++){
           var j = i+1;
           $("ul.list_pages li a").eq(i).html(j);
           $("ul.list_pages li a").eq(i).removeClass("on");
       }
		$("ul.list_pages li a").eq(6).html("下一页");
		$("ul.list_pages li a").eq(0).addClass("on");
	   $.ajax({
   		type:"GET",
   		url:"${zuulpath}/file/showfiles/find2?id="+id+"&timeflag="+timeflag+"&pagenum="+pagenum,
   		async:true,
   		success:function(result){
   			vue_list_obj.list_obj_arr = result;
   		},
   		error:function(){
   				
   		}
   	});
	}
   function find3(id) {
	   nosearch = 2;
	   whichfind = 3;
	   findid = id;
	   
	   pagenum=1;
		for(var i=0;i<6;i++){
           var j = i+1;
           $("ul.list_pages li a").eq(i).html(j);
           $("ul.list_pages li a").eq(i).removeClass("on");
       }
		$("ul.list_pages li a").eq(6).html("下一页");
		$("ul.list_pages li a").eq(0).addClass("on");
	   $.ajax({
	   		type:"GET",
	   		url:"${zuulpath}/file/showfiles/find3?id="+id+"&timeflag="+timeflag+"&pagenum="+pagenum,
	   		async:true,
	   		success:function(result){
	   			vue_list_obj.list_obj_arr = result;
	   		},
	   		error:function(){
	   				
	   		}
	   	});
	}
</script>
</html>