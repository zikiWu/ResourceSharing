<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<#include "include/top.ftl">
	<title></title>
    <style type="text/css">
    body{
      margin: 0;
      background: url(${realpath}/images/resginbf.jpg) no-repeat 0px 0px;
      background-size:cover;
      -webkit-background-size:cover;
      -moz-background-size:cover;
      -o-background-size:cover;
    }
  </style>
    <script>

        $(function(){
            $("#register2").click(function () {
                var username = $('#username').val();
                var email = $('#email').val();
                var password = $('#password').val();
                var confirmPassword = $('#confirmPassword').val();
                var emailreg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
                var passwordreg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/;

                console.log(username);
                if (username == null || $.trim(username) == '') {
                    checkForm('用户名不能是空串', 'username');
                    $("#username").parent().parent().addClass('has-error');
                } else if (username.indexOf(" ") > -1) {
                    checkForm('用户名不能含有空格', 'username');
                    $("#username").parent().parent().addClass('has-error');
                } else if (username.length < 1) {
                    checkForm('用户名长度不能小于1个字符', 'username');
                    $("#username").parent().parent().addClass('has-error');
                } else if (username.length > 20) {
                    checkForm('用户名长度不能大于20个字符', 'username');
                    $("#username").parent().parent().addClass('has-error');
                } else if (!emailreg.test(email)) {
                    $("#username").parent().parent().removeClass('has-error');
                    checkForm('请输入正确的常用邮箱', "email");
                    $("#email").parent().parent().addClass('has-error');
                } else if (password == null || $.trim(password) == '') {
                    $("#email").parent().parent().removeClass('has-error');
                    checkForm('密码不能是空串', 'password');
                    $("#password").parent().parent().addClass('has-error');
                } else if (password.indexOf(" ") > -1) {
                    checkForm('密码不能含有空格', 'password');
                    $("#password").parent().parent().addClass('has-error');
                } else if (password.length < 6) {
                    checkForm('密码长度不能小于6个字符', 'password');
                    $("#password").parent().parent().addClass('has-error');
                } else if (password.length > 16) {
                    checkForm('密码长度不能大于16个字符', 'password');
                    $("#password").parent().parent().addClass('has-error');
                } else if (!passwordreg.test(password)) {
                    checkForm('密码必须含有字母和数字', 'password');
                    $("#password").parent().parent().addClass('has-error');
                } else if (confirmPassword != password) {
                    $("#password").parent().parent().removeClass('has-error');
                    checkForm('两次输入密码必须一致', 'confirmPassword');
                    $("#confirmPassword").parent().parent().addClass('has-error');
                } else {
                    $("#confirmPassword").parent().parent().removeClass('has-error');
                    // captchaObj.show();
                    console.log(username);
                    var d = dialog({
                        content: "<div>注册成功.</div>",
                    });
                    d.showModal();
                    setTimeout(function () {
                        d.close().remove();
                    }, 1500);
                    window.open(zuul.realpath + "/common/login", "_self");
                    $.ajax({
                        url: zuul.realpath + 'common/register.do',
                        type: 'post',
                        dataType: 'json',
                        data: $('#registerform').serialize(),
                        success: function (data) {
                            console.log("success");
                            if (data.errorMsg == null) {
                                var d = dialog({
                                    content: "<div>注册成功.</div>",
                                });
                                d.showModal();
                                setTimeout(function () {
                                    d.close().remove();
                                }, 1500);
                                window.open(zuul.realpath + "/common/login", "_self");
                            } else {
                                var d = dialog({
                                    width: 200,
                                    content: data.errorMsg,
                                    quickClose: true // 点击空白处快速关闭
                                });
                                d.show();
                            }
                        }
                    });
                }
            });
        });
        function checkForm(content, doc) {
            var d = dialog({
                content: content,
                align: 'right',
                quickClose: true // 点击空白处快速关闭
            });
            d.show(document.getElementById(doc));
        }
    </script>
</head>
<body>
	<#include "include/header.ftl">
	<div class="main">
  <div class="container-fluid" style="min-height: 500px;">
	<div class="col-lg-6">
              <form class="form-horizontal" id="registerform">
                  <div class="form-group">
                    <label class="col-lg-2 control-label">用户名:</label>
                    <div class="col-lg-10">
                      <input type="text" class="form-control" name="userName" id="username" placeholder="长度1-20位字符，中文、数字、_ 、字母">
                    </div>
                  </div>
                
                  <div class="form-group">
                    <label  class="col-lg-2 control-label">邮箱:</label>
                    <div class="col-lg-10">
                      <input type="text" class="form-control" name="email" id="email" placeholder="用于取回密码,请填写正确的常用邮箱">
                    </div>
                  </div>

                  <div class="form-group">
                    <label class="col-lg-2 control-label">密码：</label>
                    <div class="col-lg-10">
                      <input type="password" class="form-control" name="password" id="password" placeholder="密码长度6-16位字符，由数字、字母组成">
                    </div>
                  </div>

                   <div class="form-group">
                    <label class="col-lg-2 control-label">确认密码:</label>
                    <div class="col-lg-10">
                      <input type="password" class="form-control" id="confirmPassword" placeholder="请再次输入登录密码">
                    </div>
                  </div>


                  <div class="form-group">
                    <div class="col-lg-10 col-lg-offset-2">
                      <button id="reset" type="reset" class="btn btn-info">重置</button>
                      <button id="register2" type="button" class="btn btn-success">注册</button>
                    </div>
                  </div>
                  
                  <div id="popup-captcha"></div>
              </form>
           

           
           
          </div>
          </div>
          
          </div>
</body>
</html>