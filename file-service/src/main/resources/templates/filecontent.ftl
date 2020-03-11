<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "include/top.ftl">
    <script text="text/javascript">
        var file = {};
        file.articleId = ${filedetail.id};
        file.articleType="FILE";

    </script>

    <title>显示文件内容</title>
    <script type="text/javascript" src="${realpath}/jquery-easyui-1.5.5.2/jquery.min.js"></script>
    <script type="text/javascript" src="${realpath}/js/vue.min.js"></script>
    <script type="text/javascript">
        <#--function downloadfile(id) {-->
            <#--if(fzqblog.userId == null){-->
                <#--var d = dialog({-->
                    <#--content: "请先登录",-->
                    <#--quickClose: true// 点击空白处快速关闭-->
                <#--});-->
                <#--d.showModal();-->
                <#--return false;-->
            <#--}-->

            <#--var mtip = "下载需要消耗"+$(".right_need em").html()+"个积分";-->
            <#--if(confirm(mtip)){-->
                <#--$.ajax({-->
                    <#--type:"GET",-->
                    <#--url:"${zuulpath}/file/downloadfile?id="+id,-->
                    <#--async:false,-->
                    <#--success:function(result){-->
                        <#--if(result.flag==1){-->
                            <#--window.location.href=result.fileurl;-->
                        <#--}else if(result.flag==2){-->
                            <#--alert(result.message);-->
                        <#--}else{-->
                            <#--//alert(result.fileurl);-->
                            <#--//window.location.href = result.fileurl;-->
                            <#--//document.getElementById("d_hide_a").href=result.fileurl;-->
                            <#--//document.getElementById("d_hide_a").click();-->
                            <#--$("#d_hide_a").prop('href',result.fileurl);-->
                            <#--var d_filename = result.fileurl.substring(result.fileurl.lastIndexOf("/")+1).substring(13);-->
                            <#--$("#d_hide_a").prop('download',d_filename);-->
                            <#--$("#d_hide_span").click();-->
                            <#--if(null == result && "" == result){-->
                                <#--var d = dialog({-->
                                    <#--content: "积分不足",-->
                                    <#--quickClose: true// 点击空白处快速关闭-->
                                <#--});-->
                                <#--d.showModal();-->
                            <#--} else {-->
                                <#--// 下载失败-->
                            <#--}-->

                        <#--},-->
                        <#--error:function(){-->

                        <#--}-->
                    <#--});-->

                <#--// var form=$("<form>");//定义一个form表单-->
                <#--// form.attr("style","display:none");-->
                <#--// form.attr("target","");-->
                <#--// form.attr("method","post");-->
                <#--// form.attr("action","/httpMessageDown?id="+id);-->
                <#--//-->
                <#--// var input2=$("<input>");-->
                <#--// input2.attr("type","hidden");-->
                <#--// input2.attr("name","reTestCaseId");-->
                <#--// input2.attr("value", id);-->
                <#--// form.append(input2);-->
                <#--// $(document.body).append(form);-->
            <#--}else{-->
                <#--return false;-->
            <#--}-->
        <#--}-->
    </script>
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
        .right .un{
        margin-right: 60px;
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
        .nav span{
            height: 40px;
            position: absolute;
            bottom: 0px;
            left: 0px;
        }
        .nav span a{
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
        .index_content{
            border: none;
            /*width: 950px;*/
            clear: both;
            margin: 0 auto;
            height: auto;
            /*background-color: #f5f5f5;*/
            font-family: "宋体";
            /*border: 1px solid #3586f4;*/
            /*border-radius: 10px;*/

            padding-bottom: 10px;
        }
        .index_main{
            float: right;
            /*border-right: 1px solid #ddd;*/
            width: 100%;
            height: auto;
            margin-left: 0;
            overflow: hidden;
            /*border-left: 1px solid #ddd;*/
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
            border-bottom: 1px solid #ddd;
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
        .list_main ul li h2 span{
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
        .left_a img{
            border: none;
            width: 48px;
            height: 48px;
        }
        .left_div span{
            margin-right: 20px;
        }
        .star span{
            width: 59px;
            height: 20px;
            background: url("${realpath}/img/star_5.gif") left no-repeat;
            position: absolute;
            top: 0;
            left: 0;
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
        .index_sidebar{
            float: left;
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
            height: 29px;
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
            line-height: 26px;
            font-size: 12px;
            color: #666;
        }

        .index_main1{
            clear: both;
            width: 97%;
            height: auto;
            margin: 0 auto;
            padding: 0;
            text-align: left;
        }
        .main1_down1{
            width: 100%;
            height: auto;
            margin: 0 auto;
            padding: 0;
            overflow: hidden;
        }
        .main1_down1 h1{
            width: 100%;
            height: auto;
            line-height: 40px;
            margin: 0 auto;
            font-size: 18px;
            color: #333;
            border-bottom: 2px solid #dddddd;
            font-weight: normal;
            font-family: "Microsoft YaHei",微软雅黑,"MicrosoftJhengHei",华文细黑,STHeiti,MingLiu;
            background: #fff;
        }
        .main1_down1 h1 img{
            margin-right: 8px;
            margin-left: 10px;
            border: none;
            width: 22px;
            height: 22px;
        }
        .down1_left{
            float: left;
            width: 55%;
            height: auto;
            border-right: 1px solid #e4e4e4;
            min-height: 150px;
            font-size: 12px;
            color: #666;
            margin-top: 10px;
            margin-bottom: 10px;
        }
        .left_message1{
            margin-left: 20px;
            margin-top: 15px;
            line-height: 200%;
            margin-bottom: 10px;
        }
        .left_message1 span{
            font-weight: normal;
            margin-right: 15px;
            line-height: 1.8em;
        }
        .left_message2{
            height: 41px;
            margin-left: 20px;
        }
        a.downbtn{
            width: 150px;
            height: 40px;
            font-size: 16px;
            font-family: "微软雅黑","黑体";
            background: #4590CE;
            color: #ffff;
            line-height: 40px;
            text-align: center;
            float: left;
            text-decoration: none;
        }
        .left_message2 .message2_right{
            width: 150px;
            height: 33px;
            float: left;
            margin-left: 20px;
        }
        .right_star{
            width: 59px;
            position: relative;
            height: 20px;
            background: url("${realpath}/img/star_0.gif") left no-repeat;
            position: relative;
            display: inline-block;
            vertical-align: middle;
            margin-right: 5px;
        }
        .right_star a{
            height: 13px;
            padding: 0px;
            margin-top: 4px;
            left: 0px;
            top: 0px;
            position: absolute;
        }
        .right_star .star_rank1{
            width: 20%;
            z-index: 5;
        }
        .right_star .star_rank2{
            width: 40%;
            z-index: 4;
        }
        .right_star .star_rank3{
            width: 60%;
            z-index: 3;
        }
        .right_star .star_rank4{
            width: 80%;
            z-index: 2;
        }
        .right_star .star_rank5{
            width: 100%;
            z-index: 1;
        }
        .right_star a:hover{
            background: url("${realpath}/img/star_5.gif") left no-repeat;
        }
        .right_need{
            display: block;
            float: left;
            font-weight: normal;
            margin-right: 15px;
            line-height: 1.8em;
        }
        .right_need img{
            border: none;
            width: 13px;
            height: 14px;
        }
        .right_need em{
            font-style: normal;
            padding: 0 0 0 5px;
        }
        .down1_right{
            float: left;
            width: 42%;
            height: auto;
            min-height: 150px;
            font-size: 12px;
            color: #666;
            margin-top: 25px;
            margin-left: 15px;
            margin-bottom: 10px;
        }
        .right_span span{
            font-weight: normal;
            margin-right: 15px;
            line-height: 1.8em;
        }
        .index_tip{
            width: 97%;
            height: auto;
            margin: 0 auto;
            padding: 0;
            border: 1px solid #ddd;
            background-color: #f9f9f9;
            margin-top: 8px;
            text-align: center;
        }
        .index_tip h4{
            width: 90%;
            margin: 0 auto;
            text-align: left;
            font-size: 14px;
            font-weight: normal;
            color: #333333;
            padding: 8px 0 8px 0;
        }
        .index_tip p{
            width: 90%;
            margin: 0 auto;
            text-align: left;
            font-size: 12px;
            line-height: 1.8em;
            color: #8f9297;
        }
        .index_main2{
            margin: 20px 20px 20px 10px;
        }
        .index_main3{
            width: 97%;
            height: auto;
            margin: 0 auto;
            padding: 0;
            border: 1px solid #ddd;
            background-color: #f9f9f9;
            margin-top: 8px;
            text-align: center;
        }
        .index_main3 ul{
            margin-bottom: 8px;
            float: left;
            width: 40%;
            padding-left: 30px;
        }
        .index_main3 ul li{
            text-align: left;
            padding: 3px 0 3px 0;
            list-style: none;
        }
        .main3_title{
            font-size: 14px;
            color: #333333;
            margin-bottom: 10px;
            margin-top: 10px;
        }
        .index_main3 ul li img{
            margin-right: 10px;
            border: none;
            width: 16px;
            height: 16px;
        }
        .index_main3 ul li a{
            text-decoration: none;
            font-size: 12px;
            color: #8f9297;
        }
        .index_main h3{
            width: 95%;
            height: 30px;
            line-height: 30px;
            margin: 0 auto;
            margin-top: 10px;
        }
        span.documentreview{
            display: block;
            float: left;
            margin-left: 0;
            color: #666;
            font-size: 14px;
            font-weight: bold;
        }
        span.documentreview em{
            font-style: normal;
            color: #e55026;
        }
        span.comment{
            display: block;
            float: right;
            color: #e55026;
            font-size: 12px;
            font-weight: normal;
        }
        .index_main4{
            width: 98%;
            height:auto;
            margin: 0 auto;
        }
        .responseContent{
            width: 98%;
        }
        .responseContent li{
            margin-left: 10px;
            font-size: 14px;
            color: #555;
            margin-bottom: 5px;
            border-bottom: 1px solid #eee;
            line-height: 20px;
            list-style: none;
        }
        .editContent{
            width: 98%;
            height: 80px;
        }
        #edit_btn{
            float: right;
            margin-right: 10px;
            width: 50px;
            height: 26px;
            border-radius: 1px;
        }
        .addview,.cancelview{
        	display:inline-block;
        	border:1px solid #999;
            border-radius: 1px;
        	width:66px;
        	line-height:20px;
        	text-align:center;
        	margin-top:8px
        }
        .addview:hover,.cancelview:hover{
        	background:#aaa;
        	cursor:pointer;
        }
        .leftspan{
        	float:left;
        	color:#aaa;
        	font-size:12px;
        	line-height:16px;
        }
        .rightspan{
        	float:right;
        	color:#888;
        	font-size:13px;
        	line-height:16px;
        }
        .rightspan a{
        	text-decoration:none;
        	color:#888;
        }
        .main1{

            width: 1100px;
            margin: 0px auto;
            padding: 20px 0px;
            min-height: 500px;
        }
        .navigation {
            padding-bottom: 10px;
            min-height: 20px;
        }
        .main2 {
            height: auto;
            border: #eee 1px solid;
            background: #fff;
            color: #333;
            filter: progid:DXImageTransform.Microsoft.Shadow(color=#909090,direction=120,strength=3);
            -moz-box-shadow: 2px 2px 10px #909090;
            -webkit-box-shadow: 2px 2px 10px #909090;
            box-shadow: 2px 2px 10px #909090;
            -webkit-border-radius: 10px;
            border-radius: 10px;
        }
    </style>
</head>
<body>
<#include "include/header.ftl">


<div class="main1">
    <div class="navigation">
        <a href="${zuulpath}/file">资源区</a>&nbsp;&gt;&nbsp;
        <span>${filedetail.title}</span>
    </div>
<div class="main2">
<div class="index_content clearfix">
    <div class="index_main">
        <div class="index_main1">
            <div class="main1_down1">
                <h1>
                    <img src="${realpath}/img/${filedetail.type }.gif">${filedetail.title}
                </h1>
                <div class="down1_left">
                    <div class="left_message1">
                        <span>被收藏：${filedetail.attention}次</span>
                        <span>下载：${filedetail.download}次</span>
                        <br>
                        <span>大小：${filedetail.size}kb</span>
                        <span>资料格式：${filedetail.type }</span>
                        <span>上传时间：${filedetail.time}</span>
                    </div>
                    <div class="left_message2">
                        <a class="downbtn" href="javascript:void(0);" onclick="downloadfile(${filedetail.id})">下载文件</a>
                        <a></a>
                        <div class="message2_right">
                            <div class="right_star">
                                <a class="star_rank1" title="很差" onclick="giveGrade(1)"></a>
                                <a class="star_rank2" title="较差" onclick="giveGrade(2)"></a>
                                <a class="star_rank3" title="一般" onclick="giveGrade(3)"></a>
                                <a class="star_rank4" title="推荐" onclick="giveGrade(4)"></a>
                                <a class="star_rank5" title="非常好" onclick="giveGrade(5)"></a>
                                <script type="text/javascript">
                                    function downloadfile(id) {
                                        if(fzqblog.userId == null){
                                            var d = dialog({
                                                content: "请先登录",
                                                quickClose: true// 点击空白处快速关闭
                                            });
                                            d.showModal();
                                            return false;
                                        }

                                        var mtip = "下载需要消耗"+$(".right_need em").html()+"个积分";
                                        if(confirm(mtip)){
                                            $.ajax({
                                                type:"GET",
                                                url:"${zuulpath}/file/downloadfile?id="+id,
                                                async:false,
                                                success:function(result) {
                                                    if (result.flag == 1) {
                                                        window.location.href = result.fileurl;
                                                    } else if (result.flag == 2) {
                                                        alert(result.message);
                                                    } else {
                                                        //alert(result.fileurl);
                                                        //window.location.href = result.fileurl;
                                                        //document.getElementById("d_hide_a").href=result.fileurl;
                                                        //document.getElementById("d_hide_a").click();
                                                        $("#d_hide_a").prop('href', result.fileurl);
                                                        var d_filename = result.fileurl.substring(result.fileurl.lastIndexOf("/") + 1).substring(13);
                                                        $("#d_hide_a").prop('download', d_filename);
                                                        $("#d_hide_span").click();
                                                        if (null == result && "" == result) {
                                                            var d = dialog({
                                                                content: "积分不足",
                                                                quickClose: true// 点击空白处快速关闭
                                                            });
                                                            d.showModal();
                                                        } else {
                                                            // 下载失败
                                                        }

                                                    }
                                                },
                                                    error:function(){

                                                    }
                                                });

                                            // var form=$("<form>");//定义一个form表单
                                            // form.attr("style","display:none");
                                            // form.attr("target","");
                                            // form.attr("method","post");
                                            // form.attr("action","/httpMessageDown?id="+id);
                                            //
                                            // var input2=$("<input>");
                                            // input2.attr("type","hidden");
                                            // input2.attr("name","reTestCaseId");
                                            // input2.attr("value", id);
                                            // form.append(input2);
                                            // $(document.body).append(form);
                                        }else{
                                            return false;
                                        }
                                    }

                                	function giveGrade(grade) {
                                		var fileid = "${filedetail.id}";
                                    		$.ajax({
                                         		type:"GET",
                                         		url:"${realpath}/giveGrade?grade="+grade+"&fileid="+fileid,
                                         		async:true,
                                         		success:function(result){
                                         			if(result.state=="1"){
                                                        layer.msg(result.message, {icon: 5,time:1000});
                                         			}else if(result.state=="2"){
                                                        layer.msg(result.message, {icon: 6,time:1000});
                                         			}
                                         		},
                                         		error:function(){
                                                    // layer.msg("评分成功", {icon: 6,time:1000});
                                         		}
                                         	});

									}
                                </script>
                            </div>
                            <br>
                            <span class="right_need">
                                所需学分：<img src="${realpath}/img/wealth.gif"><em>${filedetail.money}</em>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="down1_right">
                    <div class="right_span">
                        <span>分享者：<a href="${zuulpath}/common/${filedetail.userId?c }">${filedetail.author}</a></span><br>
                        <span>评分：${filedetail.score}</span><br>
                        <span class="addview" onclick="addview()">收藏</span>
                        <span style="display:none" class="cancelview" onclick="cancelview()">取消收藏</span>
                        <script type="text/javascript">
                        	var ab = ${ab};
                        	if(ab=="1"){
                        		$(".cancelview").show();
                        		$(".addview").hide();
                        	}else{
                        		$(".addview").show();
                        		$(".cancelview").hide();
                        	}
                        </script>
                    </div>
                    <script type="text/javascript">
                                	function addview() {
                                        var fileid = "${filedetail.id}";
                                    		$.ajax({
                                         		type:"GET",
                                         		url:"${zuulpath}/file/addview?fileid="+fileid,
                                         		async:true,
                                         		success:function(result){
                                         			if(result.state=="1"){
                                                        layer.msg(result.message, {icon: 5,time:1000});
                                         			}else if(result.state=="2"){
                                                        layer.msg(result.message, {icon: 6,time:1000});
                                         				if(result.message=="收藏成功"){
                                         					$(".cancelview").show();
                                                    		$(".addview").hide();
                                         				}
                                         			}
                                         		},
                                         		error:function(){
                                         				
                                         		}
                                         	});

									}
                                	function cancelview() {
                                		var gtip = "是否取消关注";
                                		var fileid = "${filedetail.id}";
                                		if(confirm(gtip)){
                                    		$.ajax({
                                         		type:"GET",
                                         		url:"${realpath}/cancelview?fileid="+fileid,
                                         		async:true,
                                         		success:function(result){
                                         			if(result.state=="1"){
                                                        layer.msg(result.message, {icon: 6,time:1000});
                                         			}else if(result.state=="2"){
                                                        layer.msg(result.message, {icon: 6,time:1000});
                                         				if(result.message=="取消收藏"){
                                         					$(".addview").show();
                                                    		$(".cancelview").hide();
                                         				}
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
                </div>
                <div class="clear"></div>
            </div>
            <a style="display:none"  target="_blank"  id="d_hide_a" href="" download><span id="d_hide_span">&nbsp;</span></span></a>

        </div>
        <div class="index_tip">
            <h4>资源说明</h4>
            <p>${(filedetail.shuoMing)!""}</p>
            <div class="clear"></div>
        </div>
        <div class="index_main2"></div>

        <script type="text/javascript">

        </script>
    </div>
</div>

<#include "include/comment.ftl">
</div>
</div>
<div class="footer_wrap">
</div>
</body>
</html>