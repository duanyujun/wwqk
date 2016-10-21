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
					<li class="menu_width"><a href="say">说说</a></li>
					<li class="menu_width"><a href="match">比赛</a></li>
					<li class="menu_width menu_sel"><a href="data">数据</a></li>
				</ul>	
			</div>
		</div>
		
		<div class="row clear_row_margin" style="margin-top:20px;">
			<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
				<div class="row">
					<div class="col-lg-2 col-md-2">
						<a href="data" class="link-img"><img src="assets/image/page/league-logo1.jpg" style="width:60px;height:60px;"/></a>
					</div>
					<div class="col-lg-2 col-md-2">
						<a href="data?league=2" class="link-img"><img src="assets/image/page/league-logo2.jpg" style="width:60px;height:60px;"/></a>
					</div>
					<div class="col-lg-2 col-md-2">
						<a href="data?league=3" class="link-img"><img src="assets/image/page/league-logo3.jpg" style="width:60px;height:60px;"/></a>
					</div>
					<div class="col-lg-2 col-md-2">
						<a href="data?league=4" class="link-img"><img src="assets/image/page/league-logo4.jpg" style="width:60px;height:60px;"/></a>
					</div>
					<div class="col-lg-2 col-md-2">
						<a href="data?league=5" class="link-img"><img src="assets/image/page/league-logo5.jpg" style="width:60px;height:60px;"/></a>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-2 col-md-2">
						<a href="data" ><div class="select-league">英超</div></a>
					</div>
					<div class="col-lg-2 col-md-2">
						<a href="data?league=2" ><div class="common-league">德甲</div></a>
					</div>
					<div class="col-lg-2 col-md-2">
						<a href="data?league=3" ><div class="common-league">西甲</div></a>
					</div>
					<div class="col-lg-2 col-md-2">
						<a href="data?league=4" ><div class="common-league">意甲</div></a>
					</div>
					<div class="col-lg-2 col-md-2">
						<a href="data?league=5" ><div class="common-league">法甲</div></a>
					</div>
				</div>
				
				<div class="row" style="margin-top:20px;">
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<table class="table">
						  <caption>联赛积分榜</caption>
						  <thead>
						    <tr style="background:#2EB398;color:white;">
						      <th>排名</th>
						      <th>球队</th>
						      <th>场次</th>
						      <th>胜</th>
						      <th>平</th>
						      <th>负</th>
						      <th>进球</th>
						      <th>失球</th>
						      <th>净胜球</th>
						      <th>积分</th>
						    </tr>
						  </thead>
						  <tbody>
						  <c:forEach items="${list}" var="l" varStatus="status">
						    <tr class="success">
						      <td>1</td>
						      <td class="team-title" ><a href="" target="_blank"><img src="assets/image/page/flag-1.png" style="width:25px;height:25px;"/>&nbsp;曼城</a></td>
						      <td>8</td>
						      <td>6</td>
						      <td>1</td>
						      <td>1</td>
						      <td>19</td>
						      <td>8</td>
						      <td>+11</td>
						      <td>19</td>
						    </tr>
						   </c:forEach>
						  </tbody>
						</table>
					</div>
				</div>
				
				<div class="row" style="margin-top:20px;">
					<div class="col-lg-7 col-md-7 col-sm-12 col-xs-12">
						<table class="table">
						  <caption>射手榜</caption>
						  <thead>
						    <tr style="background:#2EB398;color:white;">
						      <th>排名</th>
						      <th>球员</th>
						      <th>球队</th>
						      <th align="center">进球数</th>
						      <th align="center">点球数</th>
						      <th align="center">第一进球</th>
						    </tr>
						  </thead>
						  <tbody>
						    <tr class="active">
						      <td>1</td>
						      <td class="team-title" ><a href="" target="_blank"><img src="assets/image/page/14.png" style="width:25px;height:25px;" />&nbsp;迭戈-科斯塔</a></td>
						      <td class="team-title" ><a href="" target="_blank"><img src="assets/image/page/flag-1.png" style="width:25px;height:25px;"/>&nbsp;切尔西</a></td>
						      <td align="center">7</td>
						      <td align="center">0</td>
						      <td align="center">3</td>
						    </tr>
						  </tbody>
						</table>
					</div>
					
					<div class="col-lg-5 col-md-5 col-sm-12 col-xs-12">
						<table class="table">
						  <caption>助攻榜</caption>
						  <thead>
						    <tr style="background:#2EB398;color:white;">
						      <th>排名</th>
						      <th>球员</th>
						      <th>球队</th>
						      <th align="center">助攻数</th>
						    </tr>
						  </thead>
						  <tbody>
						    <tr class="active">
						      <td>1</td>
						      <td class="team-title" ><a href="" target="_blank"><img src="assets/image/page/14.png" style="width:25px;height:25px;" />&nbsp;迭戈-科斯塔</a></td>
						      <td class="team-title" ><a href="" target="_blank"><img src="assets/image/page/flag-1.png" style="width:25px;height:25px;"/>&nbsp;切尔西</a></td>
						      <td align="center">7</td>
						    </tr>
						  </tbody>
						</table>
					</div>
				</div>
				
				
			</div>
		</div>
		
		
</body>	

