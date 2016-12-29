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
	<meta name="keywords" content="${leagueName}足球数据,${leagueName}射手榜,${leagueName}助攻榜,${leagueName}积分榜,${leagueName}联赛排名" />
	<meta name="description" content="趣点足球网为球迷们提供${leagueName}足球数据，${leagueName}射手榜，${leagueName}助攻榜，${leagueName}积分榜，${leagueName}联赛排名。了解五大联赛，上趣点足球网。" />
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - ${leagueName}足球数据|${leagueName}射手榜|${leagueName}助攻榜|${leagueName}积分榜|${leagueName}联赛排名</title>
</head>

<body>
		<div class="row menu_bg clear_row_margin" >
			<div id="main_nav" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">	
				<div>
					<div class="logo_div">
						<a href="" title="首页">趣点足球网</a>
					</div>
					<ul style="float:left;" class="hidden-sm hidden-xs">
						<li class="menu_width"><a href="">首页</a></li>
						<li class="menu_width"><a href="fun.html">趣点</a></li>
						<li class="menu_width"><a href="say.html">说说</a></li>
						<li class="menu_width"><a href="match.html">比赛</a></li>
						<li class="menu_width menu_sel"><a href="data.html">数据</a></li>
					</ul>
					<div class="visible-sm visible-xs small-menu">
						<select id="menuSelect" class="form-control small-select">
							<option value="">首页</option>
							<option value="fun.html">趣点</option>
							<option value="say.html">说说</option>
							<option value="match.html">比赛</option>
							<option selected value="data.html">数据</option>
						</select>	
					</div>
				</div>
			</div>
		</div>
		
		<div class="row clear_row_margin" style="margin-top:70px;">
			<div id="main_content" style="min-height:10px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
				<div class="bread">
					当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;数据
				</div>
			</div>
		</div>
		
		<div class="row clear_row_margin" style="margin-top:20px;">
			<div id="main_content" class="col-lg-10 col-lg-offset-2 col-md-10 col-md-offset-2 col-sm-12 col-xs-12">		
				<div class="row hidden-sm hidden-xs">
					<div class="col-lg-2 col-md-2">
						<a href="data.html" class="link-img"><img src="assets/image/page/league-logo1.jpg" style="width:60px;height:60px;"/></a>
					</div>
					<div class="col-lg-2 col-md-2">
						<a href="data?leagueId=2" class="link-img"><img src="assets/image/page/league-logo2.jpg" style="width:60px;height:60px;"/></a>
					</div>
					<div class="col-lg-2 col-md-2">
						<a href="data?leagueId=3" class="link-img"><img src="assets/image/page/league-logo3.jpg" style="width:60px;height:60px;"/></a>
					</div>
					<div class="col-lg-2 col-md-2">
						<a href="data?leagueId=4" class="link-img"><img src="assets/image/page/league-logo4.jpg" style="width:60px;height:60px;"/></a>
					</div>
					<div class="col-lg-2 col-md-2">
						<a href="data?leagueId=5" class="link-img"><img src="assets/image/page/league-logo5.jpg" style="width:60px;height:60px;"/></a>
					</div>
				</div>
				<div class="row" style="margin-top:8px;">
					<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2  team-title" style="font-size:14px;">
						<a href="data.html" ><div class="${leagueId==1?'select-league':'common-league'}">英超</div></a>
					</div>
					<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 team-title" style="font-size:14px;">
						<a href="data?leagueId=2" ><div class="${leagueId==2?'select-league':'common-league'}">西甲</div></a>
					</div>
					<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 team-title" style="font-size:14px;">
						<a href="data?leagueId=3" ><div class="${leagueId==3?'select-league':'common-league'}">德甲</div></a>
					</div>
					<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 team-title" style="font-size:14px;">
						<a href="data?leagueId=4" ><div class="${leagueId==4?'select-league':'common-league'}">意甲</div></a>
					</div>
					<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 team-title" style="font-size:14px;">
						<a href="data?leagueId=5" ><div class="${leagueId==5?'select-league':'common-league'}">法甲</div></a>
					</div>
				</div>
				
				<div class="row hidden-sm hidden-xs" style="margin-top:10px;">
					<div class="col-lg-9 col-md-9 col-sm-12 col-xs-12">
						<table class="table table-hover" style="border:1px solid #dddddd;">
						  <caption style="min-height:60px;"><img src="assets/image/page/cup-${leagueId}.jpg" />&nbsp;<span style="font-size:18px;">${leagueName}联赛积分榜</span></caption>
						  <thead>
						    <tr style="background:#3CB371;color:white;border-left:1px solid #3CB371;border-right:1px solid #3CB371;">
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
						  <c:forEach items="${positionList}"  var="team" varStatus="status">
						  	<c:if test="${leagueId==4}">
						  		<c:choose> 
								  <c:when test="${status.index<3}">   
								    <tr style="background:#E0F4F0;">
								  </c:when> 
								  <c:when test="${status.index>16}">   
								    <tr style="background:#EEEEEE;">
								  </c:when> 
								  <c:otherwise>   
								    <tr> 
								  </c:otherwise> 
								</c:choose> 
						  	</c:if>
						  	<c:if test="${leagueId==5}">
						  		<c:choose> 
								  <c:when test="${status.index<2}">   
								    <tr style="background:#E0F4F0;">
								  </c:when> 
								  <c:when test="${status.index>17}">   
								    <tr style="background:#EEEEEE;">
								  </c:when> 
								  <c:otherwise>   
								    <tr> 
								  </c:otherwise> 
								</c:choose> 
						  	</c:if>
						  	<c:if test="${leagueId!=4 && leagueId!=5}">
						  		<c:choose> 
								  <c:when test="${status.index<4}">   
								    <tr style="background:#E0F4F0;">
								  </c:when> 
								  <c:when test="${status.index>16}">   
								    <tr style="background:#EEEEEE;">
								  </c:when> 
								  <c:otherwise>   
								    <tr> 
								  </c:otherwise> 
								</c:choose> 
						  	</c:if>
							  <td>${status.count}</td>
						      <td class="team-title" ><a href="team?id=${team.team_id}" target="_blank"><img src="assets/image/soccer/teams/25x25/${team.team_id}.png" style="width:25px;height:25px;" alt="${team.team_name}" title="${team.team_name}"/>&nbsp;${team.team_name}</a></td>
						      <td>${team.round_count}</td>
						      <td>${team.win_count}</td>
						      <td>${team.even_count}</td>
						      <td>${team.lose_count}</td>
						      <td>${team.win_goal_count}</td>
						      <td>${team.lose_goal_count}</td>
						      <td>${team.goal_count}</td>
						      <td>${team.points}</td>
						    </tr>
						    
						   </c:forEach>
						  </tbody>
						</table>
					</div>
				</div>
				
				
				<div class="row visible-sm visible-xs" style="margin-top:10px;">
					<div class="col-lg-9 col-md-9 col-sm-12 col-xs-12">
						<table class="table small-table" style="border:1px solid #dddddd;">
						  <caption ><img src="assets/image/page/cup-${leagueId}.jpg" style="height:40px;"/>&nbsp;<span style="font-size:15px;">${leagueName}联赛积分榜</span></caption>
						  <thead>
						    <tr style="background:#3CB371;color:white;border-left:1px solid #3CB371;border-right:1px solid #3CB371;">
						      <th style="max-width:20px;">排<br/>名</th>
						      <th style="min-width:106px;" >球队</th>
						      <th style="width:35px;">场次</th>
						      <th style="width:35px;">胜</th>
						      <th style="width:35px;">平</th>
						      <th style="width:35px;">负</th>
						      <th style="width:35px;">净胜球</th>
						      <th >积分</th>
						    </tr>
						  </thead>
						  <tbody>
						  <c:forEach items="${positionList}"  var="team" varStatus="status">
						  	<c:if test="${leagueId==4}">
						  		<c:choose> 
								  <c:when test="${status.index<3}">   
								    <tr style="background:#E0F4F0;">
								  </c:when> 
								  <c:when test="${status.index>16}">   
								    <tr style="background:#EEEEEE;">
								  </c:when> 
								  <c:otherwise>   
								    <tr> 
								  </c:otherwise> 
								</c:choose> 
						  	</c:if>
						  	<c:if test="${leagueId==5}">
						  		<c:choose> 
								  <c:when test="${status.index<2}">   
								    <tr style="background:#E0F4F0;">
								  </c:when> 
								  <c:when test="${status.index>17}">   
								    <tr style="background:#EEEEEE;">
								  </c:when> 
								  <c:otherwise>   
								    <tr> 
								  </c:otherwise> 
								</c:choose> 
						  	</c:if>
						  	<c:if test="${leagueId!=4 && leagueId!=5}">
						  		<c:choose> 
								  <c:when test="${status.index<4}">   
								    <tr style="background:#E0F4F0;">
								  </c:when> 
								  <c:when test="${status.index>16}">   
								    <tr style="background:#EEEEEE;">
								  </c:when> 
								  <c:otherwise>   
								    <tr> 
								  </c:otherwise> 
								</c:choose> 
						  	</c:if>
							  <td>${status.count}</td>
						      <td class="team-title" style=""><a href="team?id=${team.team_id}" target="_blank"><img src="assets/image/soccer/teams/25x25/${team.team_id}.png" style="width:25px;height:25px;" title="${team.team_name}" alt="${team.team_name}"/>&nbsp;${team.team_name}</a></td>
						      <td>${team.round_count}</td>
						      <td>${team.win_count}</td>
						      <td>${team.even_count}</td>
						      <td>${team.lose_count}</td>
						      <td>${team.goal_count}</td>
						      <td>${team.points}</td>
						    </tr>
						    
						   </c:forEach>
						  </tbody>
						</table>
					</div>
				</div>
				
				
				<div class="row hidden-sm hidden-xs" style="margin-top:20px;">
					<div class="col-lg-5 col-md-5 col-sm-12 col-xs-12">
						<table class="table table-striped table-hover " style="border:1px solid #dddddd;">
						  <caption><span style="font-size:18px;">射手榜</span></caption>
						  <thead>
						    <tr style="background:#3CB371;color:white;border-left:1px solid #3CB371;border-right:1px solid #3CB371;">
						      <th>排名</th>
						      <th>球员</th>
						      <th>球队</th>
						      <th><center>进球数</center></th>
						      <th><center>点球数</center></th>
						    </tr>
						  </thead>
						  <tbody>
						  	<c:forEach items="${shooterList}"  var="shooter" varStatus="status">
							    <tr>
							      <td>${status.count}</td>
							      <td class="team-title" ><a href="player-${shooter.player_name_en}-${shooter.player_id}.html" target="_blank"><img src="${shooter.player_img}" style="width:25px;height:25px;" alt="${shooter.player_name}" title="${shooter.player_name}"/>&nbsp;${shooter.player_name}</a></td>
							      <td class="team-title" ><a href="team-${shooter.team_name_en}-${shooter.team_id}.html" target="_blank"><img src="assets/image/soccer/teams/25x25/${shooter.team_id}.png" style="width:25px;height:25px;" alt="${shooter.team_name}" title="${shooter.team_name}"/>&nbsp;${shooter.team_name}</a></td>
							      <td ><center>${shooter.goal_count}</center></td>
							      <td ><center>${shooter.penalty_count}</center></td>
							    </tr>
							</c:forEach>
						  </tbody>
						</table>
					</div>
					
					<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12" >
						<table class="table table-striped table-hover " style="border:1px solid #dddddd;">
						  <caption><span style="font-size:18px;">助攻榜</span></caption>
						  <thead>
						    <tr style="background:#3CB371;color:white;border-left:1px solid #3CB371;border-right:1px solid #3CB371;">
						      <th>排名</th>
						      <th>球员</th>
						      <th>球队</th>
						      <th align="center">助攻数</th>
						    </tr>
						  </thead>
						  <tbody>
						  	<c:forEach items="${assistsList}"  var="assists" varStatus="status">
							    <tr>
							      <td>${status.count}</td>
							      <td class="team-title" ><a href="player-${assists.player_name_en}-${assists.player_id}.html" target="_blank"><img src="${assists.player_img}" style="width:25px;height:25px;" alt="${assists.player_name}" title="${assists.player_name}" />&nbsp;${assists.player_name}</a></td>
							      <td class="team-title" ><a href="team-${assists.team_name_en}-${assists.team_id}.html" target="_blank"><img src="assets/image/soccer/teams/25x25/${assists.team_id}.png" style="width:25px;height:25px;" alt="${assists.team_name}" title="${assists.team_name}"/>&nbsp;${assists.team_name}</a></td>
							      <td ><center>${assists.assists_count}</center></td>
							    </tr>
						    </c:forEach>
						  </tbody>
						</table>
					</div>
				</div>
				
				
				<div class="row visible-sm visible-xs" style="margin-top:20px;">
					<div class="col-lg-5 col-md-5 col-sm-12 col-xs-12">
						<table class="table table-striped small-table " style="border:1px solid #dddddd;">
						  <caption><span style="font-size:16px;">射手榜</span></caption>
						  <thead>
						    <tr style="background:#3CB371;color:white;border-left:1px solid #3CB371;border-right:1px solid #3CB371;">
						      <th>排名</th>
						      <th>球员</th>
						      <th style="min-width:110px;">球队</th>
						      <th><center>进球数</center></th>
						      <th><center>点球数</center></th>
						    </tr>
						  </thead>
						  <tbody>
						  	<c:forEach items="${shooterList}"  var="shooter" varStatus="status">
							    <tr>
							      <td>${status.count}</td>
							      <td class="team-title" ><a href="say/list?id=${shooter.player_id}" target="_blank"><img src="${shooter.player_img}" style="width:25px;height:25px;" alt="${shooter.player_name}" title="${shooter.player_name}"/>&nbsp;${shooter.player_name}</a></td>
							      <td class="team-title" ><a href="team?id=${shooter.team_id}" target="_blank"><img src="assets/image/soccer/teams/25x25/${shooter.team_id}.png" style="width:25px;height:25px;" alt="${shooter.team_name}" title="${shooter.team_name}"/>&nbsp;${shooter.team_name}</a></td>
							      <td ><center>${shooter.goal_count}</center></td>
							      <td ><center>${shooter.penalty_count}</center></td>
							    </tr>
							</c:forEach>
						  </tbody>
						</table>
					</div>
					
					<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12" >
						<table class="table table-striped small-table " style="border:1px solid #dddddd;">
						  <caption><span style="font-size:16px;">助攻榜</span></caption>
						  <thead>
						    <tr style="background:#3CB371;color:white;border-left:1px solid #3CB371;border-right:1px solid #3CB371;">
						      <th>排名</th>
						      <th>球员</th>
						      <th>球队</th>
						      <th align="center">助攻数</th>
						    </tr>
						  </thead>
						  <tbody>
						  	<c:forEach items="${assistsList}"  var="assists" varStatus="status">
							    <tr>
							      <td>${status.count}</td>
							      <td class="team-title" ><a href="say/list?id=${assists.player_id}" target="_blank"><img src="${assists.player_img}" style="width:25px;height:25px;" alt="${assists.player_name}" title="${assists.player_name}" />&nbsp;${assists.player_name}</a></td>
							      <td class="team-title" ><a href="team?id=${assists.team_id}" target="_blank"><img src="assets/image/soccer/teams/25x25/${assists.team_id}.png" style="width:25px;height:25px;" alt="${assists.team_name}" title="${assists.team_name}"/>&nbsp;${assists.team_name}</a></td>
							      <td ><center>${assists.assists_count}</center></td>
							    </tr>
						    </c:forEach>
						  </tbody>
						</table>
					</div>
				</div>
				
				
			</div>
		</div>
		
		<%@ include file="/common/footer.jsp"%>	
</body>	

