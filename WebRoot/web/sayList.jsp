<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">

<head>
	<base href="<%=basePath%>">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<meta content="telephone=no" name="format-detection">
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    
</head>

<body>
	<div class="row menu_bg clear_row_margin" >
		<div id="main_nav" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div>
				<div class="logo_div">
					<a style="margin-left:12px;">趣点足球网</a>
				</div>
				<ul style="float:left;">
					<li class="menu_width"><a href="">首页</a></li>
					<li class="menu_width"><a href="fun">趣点</a></li>
					<li class="menu_sel menu_width"><a href="say">说说</a></li>
					<li class="menu_width"><a href="match">比赛</a></li>
					<li class="menu_width"><a href="data">数据</a></li>
				</ul>	
			</div>
		</div>
		
	</div>
	
	<div class="row clear_row_margin" style="margin-top:20px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-9 col-md-9 bread">
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;<a href="/" target="_blank">说说</a>&nbsp;&gt;&nbsp;
			</div>
		</div>
	</div>
	
	<div class="row clear_row_margin" style="margin-top:20px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-9 col-md-9">
				<div class="col-lg-12 col-md-12" >
					<div class="col-lg-3 col-md-3">
						<img src="assets/image/soccer/players/150x150/${player.id}.png" style="width:150px;height:150px;" />
					</div>
					<div class="col-lg-9 col-md-9">
						<div class="col-lg-6 col-md-6" style="margin-top:10px;">名字：${player.first_name}</div>
						<div class="col-lg-6 col-md-6" style="margin-top:10px;">姓氏：${player.last_name}</div>
						<div class="col-lg-6 col-md-6" style="margin-top:10px;">生日：${player.birthday}</div>
						<div class="col-lg-6 col-md-6" style="margin-top:10px;">国籍：${player.nationality}</div>
						<div class="col-lg-6 col-md-6" style="margin-top:10px;">身高：${player.height}</div>
						<div class="col-lg-6 col-md-6" style="margin-top:10px;">体重：${player.weight}</div>
						<div class="col-lg-6 col-md-6" style="margin-top:10px;">位置：${player.position}</div>
						<div class="col-lg-6 col-md-6" style="margin-top:10px;">用脚：${player.foot}</div>
						<div class="col-lg-12 team-title" style="margin-top:10px;font-size:14px;">目前效力球队：
							<a href="team?id=${player.team_id}" target="_blank"><img src="assets/image/soccer/teams/150x150/${player.team_id}.png" style="width:25px;height:25px;"/>&nbsp;${player.team_name}</a>
						</div>
					</div>
				</div>
			</div>
			
			<div class="col-lg-9 col-md-9" style="margin-top:15px;">
				<c:forEach items="${list}" var="l" varStatus="status">
					<div class="col-lg-12 col-md-12" style="border:1px solid #E3E7EA;${status.index!=0?'border-top:0;':''}padding:20px;padding-left:0;padding-bottom:10px;">
						<div class="col-lg-1 col-md-1">
							<img src="assets/image/page/14.png" style="width:48px;height:48px;" />
						</div>
						<div class="col-lg-11 col-md-11" >
							<div class="col-lg-12 col-md-12 say-info">
								<span style="font-weight:bold;color:#292f33;">拉姆</span>
								<span style="color:#8899a6;font-size:13px;"> - 10月14日</span>
							</div>
							<div class="col-lg-12 col-md-12">
								<span style="color:#292f33;font-size:14px;">今晚的团队的伟大胜利。现在我可以享受周末庆祝我的生日，今晚的团队的伟大胜利。现在我可以享受周末庆祝我的生日:)</span>
							</div>
							<div class="col-lg-12 col-md-12" style="margin-top:10px;">
								<img src="assets/image/page/t-1.jpg" class="img-responsive img-rounded" />
							</div>
						</div>
					</div>
				</c:forEach>
				
			</div>
		</div>
	</div>
	
		
</body>	

