<nav class="navbar navbar-inverse">
  <div class="container-fluid">
      <ul class="nav navbar-nav">
        <li class=""><a href="${realpath}">首页</a></li>
        <li><a href="${realpath}/shuoshuo">说说</a></li>
        <li><a href="${realpath}/exam">在线考试</a></li>
        <li><a href="${realpath}/bbs">讨论区</a></li>
        <li><a href="${realpath}/knowledge">知识库</a></li>
        <li><a href="${realpath}/ask">问答</a></li>
        <li><a href="${realpath}/manage">管理员后台</a></li>
        <form class="navbar-form navbar-left" role="search" id="searchForm" action="${realpath}/search" method="POST">
		<select class="form-control" id="select" name="articleType">
		  <option value="K" <#if articleType??&&articleType=='K'>selected="selected"</#if>>知识库</option>
          <option value="T" <#if articleType??&&articleType=='T'>selected="selected"</#if>>帖子</option>
          <option value="B" <#if articleType??&&articleType=='B'>selected="selected"</#if>>博客</option>
          <option value="A" <#if articleType??&&articleType=='A'>selected="selected"</#if>>问答</option>
        </select>
        <div class="form-group">
          <input type="text" class="form-control" name="keyword" id="keyWord" value="${keyword!''}" style="width: 300px;">
        </div>
        <a class="btn btn-info" id="searchBtn" href="javascript:;">搜索</a>
      </form>
      </ul>

	<ul class="nav navbar-nav navbar-right">
			<#if !user.userid?? >
			 	<li><a href="${zuulpath}/common/login"> 登录</a></li>
        		<li><a href="register">加入我们</a></li>
        		<#else>
				<li class="message" style="margin-right: 40px;">
			      <a href="#" class="dropdown-toggle" id="dropdown" data-toggle="dropdown" role="button" aria-expanded="true">
			      	 您有
			         <span class="badge animated" id="message-count" style="color: red;font-size:15px;">0</span>
			         条消息
			         <span class="caret"></span>
			      </a>
			      <ul class="dropdown-menu animated rotateIn" role="menu" id="dropdownmenu">
					  <div id="message-data-list">
					  </div>
					  <div id="message-load"></div>
					  <div id="message-op" style="margin-right: 5px;">
					      <a href="${zuulpath}/common/messageList" class="btn btn-info">进入消息中心</a>
					  </div>
		          </ul>
			   </li>

				<li id="userheadicon"><img id="headicon" src="${realpath}/resources/images/${user.userIcon}" class="img-circle" id="headicon" width="60px" height="60px">
				<div style="width:260px;" id="mypanel" class="animated rotateInUpRight">
					<div class="panel panel-success">
					  <div class="panel-body">
					    <div class="panel-user-info">
					    <img src="${realpath}/images/${user.userIcon}" class="img-rounded" width="70px" height="70px" />
					    	&nbsp;&nbsp;
					    	<div class="main-user-info">
						    	<div>&nbsp;&nbsp;<span class="panel-username">${user.userName}</span></div>
								<div>&nbsp;&nbsp;积分：<span class="usermark"></span></div>
							</div>
							<div class="clear"> </div>
					    </div>
					    <div class="panel-user-signin">
					    	<a class="unsigninimage" id="signinimage"></a>
					    	<div class="panel-user-signin-info">
						    	<span class="signindate"></span>
						    	<span class="usertotalsignin">总共签到<em></em>天</span>
					    	</div>
					    	<div class="clear"> </div>
					    </div>
					    <div style="color: #fff;margin-top:10px;"></div>
					    <div class="user-action">
					    	<div class="user-action-center">
					    		<a href="${zuulpath}/common/${user.userid?c}">个人中心</a>
					    	</div>
					    	<div class="usre-action-panel">
					    		<a href="${realpath}/logout">退出</a>
					    	</div>
					    </div>
					  </div>
					</div>					
				</div>
				</li>
			</#if>
		</ul>
</nav>