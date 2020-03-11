
<!DOCTYPE html>
<html lang="en">
<head>

    <#include "include/top.ftl">
    <meta charset="UTF-8">
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
        .upload_wrap{
            width: 950px;
            margin: 0 auto;
        }
        .upload_main{
            width: 98%;
            height: 240px;
            margin: 0 auto;
            border: 1px dashed #dcdcdc;
            text-align: center;
            background-color: #fafafa;
        }
        .upload_main .main_p1{
            margin-top: 86px;
            text-align: center;
            color: #999;
            line-height: 26px;
        }
        .upload_main .main_p2{
            text-align: center;
            line-height: 32px;
        }
        .main_p2 input{
            width: 70px;
            height: 30px;
            border: none;
            color: #666;
        }
        .main_p2 input:hover{
            background-color: #ccc;
        }
        .content_tip{
            width: 950px;
            margin: 0 auto;
            font-size: 14px;
            line-height: 26px;
            margin-top: 30px;
            color: #555;
        }
        .choose_classify{
            width: 950px;
            margin: 0 auto;
            margin-bottom: 10px;
            margin-top: 5px;
        }
        .choose_classify span{
            margin-left: 16px;
            color: #666;
        }
        .choose_classify select{
            margin-left: 10px;
        }
        
        
        .aaa{
        	width:300px;
        	height:30px;
        	border:1px solid #00bb00;
        	margin:0 auto;
        	margin-top: 86px;
        }
        .bbb{
        	width:0%;
        	height:30px;
        	background:#00bb00;
        }
        .control-label {
            float: left;
            width: 200px;
            padding-top: 5px;
            text-align: right;
        }
        #input_numberid{
            width:30%;
        }
        .row{
            padding-top: 50px;
        }
        #input_shuoming{
            width:70%;
        }
    </style>
</head>
<body>
<#include "include/header.ftl">
<div class="choose_classify">
    <form id="uploadfile_form" enctype="multipart/form-data" method="post">


        <label class="col-xs-1 control-label"> <em class="important">*</em>
            文件类型：
        </label>
        <div class="col-xs-3">
            <select name="c1" id="c1" class="form-control">
                <option value="1">考试资料</option>
                <option value="2">作业课件</option>
                <option value="3">IT数码</option>
                <option value="4">考试资料</option>
                <option value="5">影音视频</option>
                <option value="6">创意图库</option>
            </select>
        </div>
        <div class="col-xs-3">
            <select name="c2" id="c2" class="form-control">
            <#list al_big as b>
                <option value="${b[0] }">${b[1] }</option>
            </#list>
            </select>
        </div>
        <div class="col-xs-3">
            <select name="c3" id="c3" class="form-control">
            <#list al_small as s>
                <option value="${s[0] }">${s[1] }</option>
            </#list>
            </select>
        </div>
        <div class="row">
            <label class="col-xs-1 control-label"> <em class="important">*</em>
                文件积分：
            </label>
            <input class="form-control" id="input_numberid" type='text' onkeyup="input_number(this)" onblur="input_number(this)" placeholder="输入正整数，默认为0"/>
        </div>
        <div class="row">
            <label class="col-xs-1 control-label"> <em class="important">*</em>
                资源说明：
            </label>
            <textarea class="form-control" id="input_shuoming" rows="3"  ></textarea>
        </div>
        <#--<input class="form-control"  name="title" id="title"></input>-->
        <p style="display: none"><input type="file" id="file_hidden" name="hidden_file"></p>
        <script type="text/javascript">
        	function input_number(obj) {
				$("#input_numberid").val($("#input_numberid").val().replace(/[^0-9-]+/,''));
			}
        </script>
    </form>
</div>
<div class="upload_wrap">
    <div class="upload_main">
    <div class="aaa" style="display:none"><div class="bbb"></div></div>
        <p class="main_p1">拖拉文件到虚线框上传</p>
        <p class="main_p2">
        <input class="btn btn-info" id="choosefile" type="button" onclick="alertfile()" value="选择文件&nbsp;">
        <input class="btn btn-info" id="fileupload" type="button" onclick="uploadfile()" value="上传文件&nbsp;"  style="display:none">
        </p>
    </div>
    <script type="text/javascript">
    var uploadMethon = 1;//1-拖拉文件上传，2-点击选择文件上传
        $(function(){
            $("#c1").change(function(){
                $.ajax({
                    type:"post",
                    url:"${zuulpath}/file/classify/data1",
                    async:true,
                    data:{val:$("#c1 option:selected").val()},
                    dataType:"json",
                    success:showSelect1
                });
            });
            $("#c2").change(function(){
                $.ajax({
                    type:"post",
                    url:"${zuulpath}/file/classify/data2",
                    async:true,
                    data:{val:$("#c2 option:selected").val()},
                    dataType:"json",
                    success:showSelect2
                });
            });
            function showSelect1(dataObj){
                $("#c2").empty();
                for(var i=0;i<dataObj.a.length;i++){
                    $("#c2").append($("<option value="+dataObj.a[i][0]+">"+dataObj.a[i][1]+"</option>"));
                }
                $("#c3").empty();
                for(var i=0;i<dataObj.b.length;i++){
                    $("#c3").append($("<option value="+dataObj.b[i][0]+">"+dataObj.b[i][1]+"</option>"));
                }
            }
            function showSelect2(dataObj){
                $("#c3").empty();
                for(var i=0;i<dataObj.b.length;i++){
                    $("#c3").append($("<option value="+dataObj.b[i][0]+">"+dataObj.b[i][1]+"</option>"));
                }
            }
            //阻止浏览器默认行为
            $(".upload_main").on({
                dragleave:function(e){    //拖离
                    e.preventDefault();
                    $(".main_p1").html("拖拉文件到虚线框上传");
                },
                drop:function(e){  //拖后放
                    e.preventDefault();
                    $(".main_p1").html("拖拉文件到虚线框上传");
                    uploadMethon = 1;
                    dt = e.originalEvent.dataTransfer.files[0];
                    var filename = dt.name;
                    $(".main_p1").html(filename);
        			$("#choosefile").hide();
        			$("#fileupload").show();
                },
                dragenter:function(e){    //拖进
                    e.preventDefault();
                    $(".main_p1").html("松开鼠标上传文件");
                },
                dragover:function(e){    //拖来拖去
                    e.preventDefault();
                    $(".main_p1").html("松开鼠标上传文件");
                }
            });
        });
        function alertfile() {
        	uploadMethon = 2;
            $("#file_hidden").click();
        }
        $("#file_hidden").change(function () {
			var filepath = $("#file_hidden").val();
			var index = filepath.lastIndexOf("\\");
			var filename = filepath.substring(index+1);
			$(".main_p1").html(filename);
			$("#choosefile").hide();
			$("#fileupload").show();
		});
        function uploadfile() {
        	$(".main_p1").hide();
        	$(".main_p2").hide();
        	$(".aaa").show();
        	var myform = new FormData();
        	var c1 = $("#c1").val();
        	var c2 = $("#c2").val();
        	var c3 = $("#c3").val();
        	myform.append('c1',c1);
        	myform.append('c2',c2);
        	myform.append('c3',c3);
        	var number_value = $("#input_numberid").val();
            var input_shuoming = $("#input_shuoming").val();
        	myform.append('money',number_value);
            myform.append('shuoming',input_shuoming);
        	var f = $("#file_hidden")[0].files[0];
        	if(uploadMethon==1||f==null||f.length==0){
        		myform.append("files",dt);
        	}else{
        		myform.append("files",f);
        	}
        	$.ajax({
                type:"post",
                async:true,  //这里要设置异步上传，才能成功调用myXhr.upload.addEventListener('progress',function(e){}),progress的回掉函数
                Accept:'text/html;charset=UTF-8',
                data:myform,
                contentType:"multipart/form-data",
                url: "${zuulpath}/zuul/file/uploadfile/all",
                processData: false, // 告诉jQuery不要去处理发送的数据
                contentType: false, // 告诉jQuery不要去设置Content-Type请求头
                xhr:function(){                        
                    myXhr = $.ajaxSettings.xhr();
                    if(myXhr.upload){ // check if upload property exists
                        myXhr.upload.addEventListener('progress',function(e){                            
                            var loaded = e.loaded;                  //已经上传大小情况 
                            var total = e.total;                      //附件总大小 
                            var percent = Math.floor((100*loaded)/total)+"%";     //已经上传的百分比  
                            $(".bbb").css("width",percent);                                                                
                        }, false); // for handling the progress of the upload
                    }
                    return myXhr;
                },                    
                success:function(data){
                	if(data.url!=1){
                		window.location.href = data.url;
                	}else{
                		alert(data.result);
                        $(".main_p1").show();
                    	$(".main_p2").show();
                    	$(".aaa").hide();
                    	$(".main_p1").html("拖拉文件到虚线框上传");
                    	$("#choosefile").show();
            			$("#fileupload").hide();
                	}
                },
                error:function(){
                    alert("上传文件失败！");
                    $(".main_p1").show();
                	$(".main_p2").show();
                	$(".aaa").hide();
                	$(".main_p1").html("拖拉文件到虚线框上传");
                	$("#choosefile").show();
        			$("#fileupload").hide();
                }
            });
		}
    </script>
</div>
<div class="content_tip">
    <h2>温馨提示</h2>
    <p>1、支持格式为：TXT, DOC, PPT, XLS,PDF的资料上传；</p>
    <p>2、禁止上传反对宪法所确定的基本原则的资料；</p>
    <p>3、 禁止发布、散布、宣扬、危害中国主权、攻击党和国家领导人、故意破坏社会稳定局势、邪教、反动、封建迷信、淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；</p>
    <p>4、禁止发布内容低级不健康、过分的性话题或太过隐私，不宜在公开场合讨论的话题。肮脏的、色情话题及黄色小段子、含病毒连接或恶意代码的；</p>
    <p>5、禁止发布恐怖、凶杀以及给人视觉反感的图片、人体暴露照片、色情的以及打擦边球的图片（人体艺术图片视情况而定）；</p>
    <p>6、禁止发布包含个人隐私内容的资料，比如：包含您或他人的电话号码、邮箱、住址等联系方式，以及个人身份证件号码、银行卡号，等等；</p>
    <p>7、不得发表包含种族、肤色、性别、性取向、宗教、民族、地域、残疾、社会经济状况歧视内容的言论；</p>
    <p>8、不得发表对他人构成伤害的言论，不得发表宣扬对青少年成长不利的言论。禁止进行人身攻击、侮辱或者诽谤他人、侵害他人合法权益的、含有法律、行政法规禁止的其他内容的。网络上传信息及其后果由上传者本人负责，违反各种法律法规及《保守国家秘密法》等严重行为者，将承担法律责任；</p>
    <p>9、资料内容、简介、标题涉及广告宣传不得发表；</p>
    <p>10、盗版内容，非法转载以及侵权内容。爱问共享资料对他人在网站上实施的此类侵权行为不承担法律责任，侵权的法律责任概由本人承担；</p>
    <p>11、用户利用不同ID，进行自我交易实现获利的，视为作弊行为，爱问共享资料保留一切解释和处理权利；</p>
    <p>12、禁止用户在爱问共享资料上传已存在的资料；</p>
    <p>13、禁止上传无效或无价值资料；</p>
    <p>14、如严重违反上述规定，爱问共享资料将对于违规用户会采取封闭账号、禁止访问的措施</p>
</div>
<div class="footer_wrap">
</div>
</body>
</html>