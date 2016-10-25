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
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    
</head>

<body>
		<div class="row menu_bg clear_row_margin" >
			<div id="main_nav" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
				<div class="logo_div">
					<a >趣点足球网</a>
				</div>
				<ul style="float:left;">
					<li class="menu_width"><a href="">首页</a></li>
					<li class="menu_width"><a href="fun">趣点</a></li>
					<li class="menu_width"><a href="say">说说</a></li>
					<li class="menu_width"><a href="match">比赛</a></li>
					<li class="menu_width menu_sel"><a href="data">数据</a></li>
				</ul>	
			</div>
		</div>
		
		<div class="row clear_row_margin" style="margin-top:20px;">
			<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
				
				<div class="row">
					<div class="col-lg-4 col-md-4">
						<div class="col-lg-12 col-md-12">						
							<img src="assets/image/page/team-logo.png" style="width:150px;height:150px;"/>
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:10px;">
							<span style="font-size:24px;font-weight:bold;">托特纳姆热刺</span>
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:10px;">
							成立时间：1892
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:10px;">
							地址：Anfield Road L4 0TH Liverpool
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:10px;">
							国家：英格兰
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:10px;">
							电话：+44 (151) 263 2361
						</div>
					</div>
					<div class="col-lg-4 col-md-4">
						<div class="col-lg-12 col-md-12">
							<img src="assets/image/page/venue.jpg" style="width:300px;height:225px;"/>
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:10px;">
							球场名称：Anfield
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:10px;">
							所在城市：Liverpool
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:10px;">
							观众容量：54074
						</div>
					</div>
				</div>
				
				<c:forEach items="${lstGroup}" var="group">
					<div class="row" style="margin-top:20px;">
						<div class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
							<table class="table " >
							  <caption style="min-height:30px;"><b>${group[0].position}</b></caption>
							  <tbody >
							  	<c:set var="i" value="1"/>
							  	<tr style="border-top:1px solid #dddddd; ${2==group.size()?'border-bottom:1px solid #dddddd;':''}">
								<c:forEach items="${group}" var="player">
									<td style="width:50px;border:none;"><img src="assets/image/soccer/players/50x50/${player.id}.png" /></td>
							      	<td colspan="${i==group.size()?3:1}" style="border:none;">${player.name}<br>${player.age}岁</td>
							      	<c:if test="${i%2==0}">
									</tr>
									<tr style="${i+2 ge group.size()?'border-bottom:1px solid #dddddd;':''}">
									</c:if>
									<c:set var="i" value="${i+1}"></c:set>
								</c:forEach>
								</tr>
								
							  </tbody>
							</table>
						</div>
					</div>
				</c:forEach>
				
			</div>
		</div>
		
		
</body>	

