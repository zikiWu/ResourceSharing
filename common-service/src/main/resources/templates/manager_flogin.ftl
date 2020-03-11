<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<#include "include/top.ftl">
<link rel="stylesheet" type="text/css"
	href="${realpath}/css/lrtk.css">
<script type="text/javascript" src="${realpath}/js/jquery.js"></script>
<script>
$(function(){
	$("#account").focus();
	
	$("#account").blur(function(){
		$.ajax({
			url: zuul.realpath + '/common/findHeadImage',
			type: 'post',
			dataType: 'json',
			data: {account: $(this).val()},
			success:function(result){
				var headIcon = result.data==null ? "headIcon.jpg" : result.data;
				$("#headicon").attr({
					src: fzqblog.realpath + "/images/" + headIcon
				});
				$("#headicon2").attr({
					src: fzqblog.realpath + "/images/" + headIcon
				});
			}
		});
		
	});
});
</script>
<title>登录</title>
<style type="text/css">
body {
	margin: 0;
	/*background: url(${realpath}/images/bg.jpg) no-repeat 0px 0px;
			background-size:cover;
			-webkit-background-size:cover;
			-moz-background-size:cover;
			-o-background-size:cover;*/
}
</style>
</head>
<body style="background-color: #F4F6F8;">
<#include "include/header.ftl">
	<div class="Upper">

		<table style="width: 100%" border="0" cellspacing="0" cellpadding="0"
			align="center">

			<tr>

				<td>

					<table style="width: 600px" border="0" cellspacing="0"
						cellpadding="0" align="center">
						<tr>
							<td style="padding: 70px 0 0" align="left" width="385">

								<h1 style="color: #f4f2ed"></h1>

								<p style="color: #d3f1a0"></p>

							</td>
						</tr>

						<tr>

							<td style="padding: 10px 0" colspan="2" align="right"></td>

						</tr>

					</table>

					<table style="width: 600px" border="0" cellspacing="0"
						cellpadding="0" align="center">

						<tr>

							<td class="content" style align="left" valign="top" width="600">

								<table border="0" cellspacing="0" cellpadding="0">

									<tr>

										<td style="padding: 20px 0 25px 95px" align="left"></td>

									</tr>
								</table>

							</td>

						</tr>

					</table>



				</td>

			</tr>

		</table>

	</div>

	<div class="logincontainer">
	<div class="login">
		<h3>管理员登录</h3>
		<form id="loginform">
			<div class="form-group">
				<input class="form-control" placeholder="请输入账号或者邮箱" name="account"
					id="account"></input>
			</div>
			<div class="form-group">
				<input type="password" class="form-control" placeholder="请输入密码"
					name="password" id="password"></input>
			</div>
			<#--<div class="checkbox">-->
				<#--<label> <input type="checkbox" name="rememberMe" value="1" style="">-->
				<#--<span class="text-danger" ><strong>记住我的登录信息(网吧公共场所请勿使用)</strong></span>-->
				<#--</label>-->
			<#--</div>-->
		</form>
		<button type="button" class="btn btn-info block full-width" id="login2">登录</button>
	</div>
	</div>

</body>
</html>