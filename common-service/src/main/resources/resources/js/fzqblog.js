$(document).ready(function() {
	var url = fzqblog.curUrl;
    if (url.indexOf("redirect") != -1) {
	fzqblog.redirect = url.substring(url.indexOf("redirect") + 9, url.length);
    }
	$("ul.tag li").click(function(event) {
		$('ul.tag li').removeClass('active');
		$(this).addClass('active');

	});

	// $("#dropdown").click(function(event) {
	// 	if(fzqblog.userId!=""){
    //     	 showMessageList();
    //      }
	// });
	$(document).click(function(event) {
		$("#dropdownmenu").slideUp("slow");
	});

	$('#reset').click(function(event) {
		$('#username').val('');
		$('#email').val('');
		$('#password').val('');
		$('#confirmPassword').val('');
	});

	function checkForm(content, doc) {
		var d = dialog({
			content: content,
			align: 'right',
			quickClose: true // 点击空白处快速关闭
		});
		d.show(document.getElementById(doc));
	}


	/*
		登录验证
	*/

	$('#login').click(function(event) {
		var account = $('#account').val();
		var password = $('#password').val();
		if (account == null || $.trim(account) == '') {
			checkForm('用户名不能为空', 'account');
			$("#account").parent().addClass('has-error');
		} else if (password == null || $.trim(password) == '') {
			$("#account").parent().removeClass('has-error');
			checkForm('密码不能为空', 'password');
			$("#password").parent().addClass('has-error');
		} else {
			$("#password").parent().removeClass('has-error');
			var loadingindex = layer.load(0, {
			  shade: [0.1,'#fff'] //0.1透明度的白色背景
			});
			$.ajax({
				url: zuul.realpath + '/common/login.do',
				type: 'POST',
				dataType: 'json',
				data: $('#loginform').serialize(),
				success: function(data) {
					layer.close(loadingindex);
					if (data.errorMsg == null) {
						var d = dialog({
							content: "<div><img src='" + fzqblog.realpath +"/images/loading.gif' />&nbsp;&nbsp;&nbsp;登录成功,跳转中...</div>",
						});
						document.cookie = 'session_user_key='+data.data+';domain=/;path=/';
						d.showModal();
						setTimeout(function() {
							d.close().remove();
							if (null == fzqblog.redirect || fzqblog.redirect == "") {
								fzqblog.redirect = zuul.realpath+"/bbs";
							    }
							    document.location.href = fzqblog.redirect;
						}, 1000);
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
//控制用户信息显示  加载用户签到信息
	$("#userheadicon").mouseover(function(){
	    $("#mypanel").show();   
	  });
	 $("#userheadicon").mouseleave(function(event) {
	 	 fzqblog.showdialog_timeout = setTimeout(function(){
			$("#mypanel").hide();
	    },100);	 	
	 });
	 $("#mypanel").mouseenter(function(){
	 	clearTimeout(fzqblog.showdialog_timeout);
	    $("#mypanel").show();   
	  });
	  $("#mypanel").mouseleave(function(){
	    $("#mypanel").hide();   
	  });

});

