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
					<li class="menu_width"><a href="say">说说</a></li>
					<li class="menu_width menu_sel"><a href="match">比赛</a></li>
					<li class="menu_width"><a href="data">数据</a></li>
				</ul>	
			</div>
		</div>
		
	</div>
	
		<div class="row clear_row_margin" style="margin-top:20px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-9 col-md-9 bread">
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;比赛
			</div>
		</div>
	</div>
	
	<div class="row clear_row_margin" style="margin-top:1px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-10 col-lg-offset-2 col-md-10 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-9 col-md-9" style="padding-left:0px;">
				
				<c:forEach items="${lstGroup}" var="group">
					<div class="table-responsive" style="margin-top:10px;">
						<table class="table table-condensed table-hover" style="border-bottom:1px solid #dddddd;">
						  <caption><img src="assets/image/page/league-logo${group[0].league_id}.jpg" style="width:80px;height:80px;"/></caption>
						  <thead>
						    <tr>
						      <th>比赛时间（${group[0].league_name}）</th>
						      <th></th>
						      <th></th>
						      <th></th>
						      <th></th>	
						    </tr>
						  </thead>
						  <tbody>
						  	<c:forEach items="${group}" var="match">
						    <tr>
						      <td>${match.match_date} 星期${match.match_weekday}</td>
						      <td><img src="assets/image/soccer/teams/150x150/${match.home_team_id}.png" style="width:25px;height:25px;"/>&nbsp;${match.home_team_name}</td>
						      <td>${match.result}</td>
						      <td><img src="assets/image/soccer/teams/150x150/${match.away_team_id}.png" style="width:25px;height:25px;"/>&nbsp;${match.away_team_name}</td>
						      <td></td>
						    </tr>
						    </c:forEach>
						    
						  </tbody>
						</table>
					</div>
				</c:forEach>
				
			</div>
			<div class="col-lg-3 col-md-3">
				
			</div>
		</div>
	</div>
	
		
</body>	

