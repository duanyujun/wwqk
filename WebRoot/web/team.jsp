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
	<meta name="keywords" content="${team.name},${team.name}球员,${team.name}直播,${team.name}数据,${team.name}比赛,${team.name}排名" />
	<meta name="description" content="趣点足球网为球迷们提供${team.name}球队相关的趣闻、${team.name}阵容数据、${team.name}比赛，${team.name}排名以及免费的足球直播。了解${team.name}，上趣点足球网。" />
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - ${team.name}|${team.name}球员|${team.name}直播|${team.name}数据|${team.name}比赛|${team.name}排名</title>
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
			<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
				<div class="bread">
					当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;<a href="/data" target="_blank">数据</a>&nbsp;&gt;&nbsp;${team.name}
				</div>
			</div>
		</div>
		
		<div class="row clear_row_margin" style="margin-top:10px;">
			<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
				
				<div class="row">
					<div class="col-lg-4 col-md-4 hidden-sm hidden-xs">
						<div class="col-lg-12 col-md-12">						
							<a href="${team.offical_site}" target="_blank" title="查看${team.name}官网"><img src="assets/image/soccer/teams/150x150/${team.id}.png"  style="width:150px;height:150px;" alt="${team.name}" /></a>
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:10px;">
							<span style="font-size:24px;font-weight:bold;">${team.name}</span>
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:10px;">
							成立时间：${team.setup_time}
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:10px;">
							地址：${team.address}
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:10px;">
							国家：${team.country}
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:10px;">
							电话：${team.telphone}
						</div>
					</div>
					<div class="col-sm-12 col-xs-12 visible-sm visible-xs">
						<div class="row">
							<div class="col-sm-6 col-xs-6">						
								<img src="assets/image/soccer/teams/150x150/${team.id}.png"  style="width:150px;height:150px;" alt="${team.name}" title="${team.name}"/>
							</div>
							<div class="col-sm-6 col-xs-6" style="margin-top:10px;">
								<span style="font-size:18px;font-weight:bold;">${team.name}</span>
							</div>
							<div class="col-sm-6 col-xs-6" style="margin-top:10px;">
								成立时间：${team.setup_time}年
							</div>
							<div class="col-sm-6 col-xs-6" style="margin-top:10px;">
								国家：${team.country}
							</div>
						</div>
						<div class="row" style="margin-top:10px;">
							<div class="col-sm-12 col-xs-12">
								地址：${team.address}
							</div>
						</div>
						<div class="row" style="margin-top:10px;">
							<div class="col-sm-12 col-xs-12">
									电话：${team.telphone}
							</div>
						</div>
					</div>
					
					<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
						<div class="row" style="margin-top:10px;">
							<div class="col-lg-12 col-md-12">
								<img src="${team.venue_small_img_local}" class="img-responsive img-rounded" style="width:300px;height:225px;" alt="${team.name}球场名称：${team.venue_name}" title="${team.name}球场名称：${team.venue_name}"/>
							</div>
							<div class="col-lg-12 col-md-12" style="margin-top:10px;">
								球场名称：${team.venue_name}
							</div>
							<div class="col-lg-12 col-md-12" style="margin-top:10px;">
								所在城市：${team.venue_address}
							</div>
							<div class="col-lg-12 col-md-12" style="margin-top:10px;">
								观众容量：${team.venue_capacity}人
							</div>
						</div>
					</div>
					
					<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 hidden-sm hidden-xs">
						<div class="row" style="margin-top:10px;">
							<div class="col-lg-12 col-md-12">
								<table class="table small-table" >
									  <caption style="min-height:30px;text-align:left;"><b style="margin-left:15px;">最近五场比赛</b></caption>
									  <tbody>
									  		<c:forEach items="${lstMatchHistory}" var="history">
									  			<tr>
									  				<td class="a-title"><a href="team-${history.home_team_en_name}-${history.home_team_id}.html" target="_blank"><img src="assets/image/soccer/teams/25x25/${history.home_team_id}.png" style="width:25px;height:25px;" alt="${history.home_team_name}" title="${history.home_team_name}"/>&nbsp;<span style="font-size:12px;">${history.home_team_name}</span></a></td>
									  				<td class="a-title" style="text-align:center;min-width:100px;">
									  					<c:if test="${fn:contains(history.result, '-')}">
												      		<b><a title="观看集锦" href="/match/detail?matchKey=<fmt:formatDate value="${history.match_date}" pattern="yyyy-MM-dd"/>-${history.home_team_id}vs${history.away_team_id}" target="_blank">${history.result}</a></b>
												      	</c:if>
												      	<c:if test="${!fn:contains(history.result, '-')}">
												      		<a title="直播地址" href="/match/detail?matchKey=<fmt:formatDate value="${history.match_date}" pattern="yyyy-MM-dd"/>-${history.home_team_id}vs${history.away_team_id}" target="_blank"><fmt:formatDate value="${history.match_date}" pattern="yy/MM/dd hh:mm"/></a>
												      	</c:if>
									  				</td>
									  				<td class="a-title"><a href="team-${history.away_team_en_name}-${history.away_team_id}.html" target="_blank"><img src="assets/image/soccer/teams/25x25/${history.away_team_id}.png" style="width:25px;height:25px;" alt="${history.away_team_name}" title="${history.away_team_name}"/>&nbsp;<span style="font-size:12px;">${history.away_team_name}</span></a></td>
									  				
									  			</tr>
									  		</c:forEach>
									  </tbody>
								</table>
							</div>
						</div>
						
					</div>
					
				</div>
				
				<div class="row">
					<div class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
							<c:forEach items="${lstGroup}" var="group">
								<div class="row hidden-sm hidden-xs" style="margin-top:20px;">
									<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
										<table class="table" >
										  <caption style="min-height:30px;text-align:left;"><b style="margin-left:15px;">${group[0].position}</b></caption>
										  <tbody >
										  	<c:set var="i" value="1"/>
										  	<tr style="border-top:1px solid #dddddd; ${2==group.size()?'border-bottom:1px solid #dddddd;':''}">
											<c:forEach items="${group}" var="player">
												<td style="width:50px;border:none;"><img src="${player.img_small_local}" title="${player.name}" alt="${player.name}"/></td>
										      	<td colspan="${i==group.size()?3:1}" class="team-title" style="border:none;width:250px;font-size:13px;">
										      		<p>
										      		<a href="player-${player.en_url}-${player.id}.html" target="_blank">${player.name}</a>&nbsp;&nbsp;
										      		<c:if test="${!empty player.number}">
										      			<c:if test="${!empty team.cloth}">
										      				<nobr><span title="球衣：${player.number}号"><span style="<c:if test="${!empty clothBg }">display:line-block;background:#ddd;</c:if>"><img src="${team.cloth}" style="margin-top:-3px;" /></span> ${player.number}号</span></nobr>
										      			</c:if>
										      			<c:if test="${empty team.cloth}">
												      		<nobr><span title="球衣：${player.number}号"><img src="assets/pages/img/cloth.png" style="margin-top:-3px;" /> ${player.number}号</span></nobr>
										      			</c:if>
										      		</c:if>
										      		</p>
										      		<p style="line-height:20px;height:20px;">
										      		${player.age}岁 &nbsp;
										      		<c:if test="${player.goal_count!=0}">
										      			<nobr><span title="进球数：${player.goal_count}"><img src="assets/pages/img/goal-small.png" style="margin-top:-5px;" /> <b>${player.goal_count}</b></span></nobr>
										      		</c:if>
										      		<c:if test="${player.goal_count!=0 && player.assists_count!=0}">
										      		&nbsp;
										      		</c:if>
										      		<c:if test="${player.assists_count!=0}">
										      			<nobr><span title="助攻数：${player.assists_count}"><img src="assets/pages/img/goal-assists.png" style="margin-top:-5px;" /> <b>${player.assists_count}</b></span></nobr>
										      		</c:if>
										      		</p>
										      	</td>
										      	<c:if test="${i%2==0}">
												</tr>
												<tr style="${i+2 ge group.size()?'border-bottom:1px solid #dddddd;':''};width:250px;">
												</c:if>
												<c:set var="i" value="${i+1}"></c:set>
											</c:forEach>
											</tr>
											
										  </tbody>
										</table>
									</div>
									
								</div>
								
								
								<div class="row visible-sm visible-xs" style="margin-top:20px;">
									<div class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
										<table class="table small-table" >
										  <caption style="min-height:30px;text-align:left;"><b style="margin-left:15px;">${group[0].position}</b></caption>
										  <tbody >
										  	<c:set var="i" value="1"/>
										  	<tr style="border-top:1px solid #dddddd; ${2==group.size()?'border-bottom:1px solid #dddddd;':''}">
											<c:forEach items="${group}" var="player">
												<td style="width:50px;border:none;"><img src="${player.img_small_local}" title="${player.name}" alt="${player.name}" title="${player.name}"/></td>
										      	<td colspan="${i==group.size()?3:1}" class="team-title" style="border:none;width:250px;font-size:13px;">
										      		<p>
										      		<a href="say/list?id=${player.id}" target="_blank">${player.name}</a>
										      		<c:if test="${!empty player.number}">
											      		<nobr><span title="球衣：${player.number}号"><img src="assets/pages/img/cloth.png" style="margin-top:-3px;" /> ${player.number}号</span></nobr>
										      		</c:if>
										      		</p>
										      		<p style="line-height:20px;height:20px;margin-top:-7px;">
										      		${player.age}岁 
										      		<c:if test="${player.goal_count!=0}">
										      			<nobr><span title="进球数：${player.goal_count}"><img src="assets/pages/img/goal-small.png" style="margin-top:-5px;" /> <b>${player.goal_count}</b></span></nobr>
										      		</c:if>
										      		<c:if test="${player.assists_count!=0}">
										      			<nobr><span title="助攻数：${player.assists_count}"><img src="assets/pages/img/goal-assists.png" style="margin-top:-5px;" /> <b>${player.assists_count}</b></span></nobr>
										      		</c:if>
										      		</p>
										      	</td>
										      	<c:if test="${i%2==0}">
												</tr>
												<tr style="${i+2 ge group.size()?'border-bottom:1px solid #dddddd;':''};width:250px;">
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
					<div class="col-lg-4 col-md-4 hidden-sm hidden-xs">
						<div class="row " style="margin-top:10px;">
								<div class="col-lg-12 col-md-12">
									<table class="table" style="border:1px solid #dddddd;">
									  <caption style="min-height:30px;text-align:left;"><b style="margin-left:15px;">联赛排名</b></caption>
									  <thead>
									    <tr style="background:#3CB371;color:white;border-left:1px solid #3CB371;border-right:1px solid #3CB371;">
									      <th>排名</th>
									      <th>球队</th>
									      <th>场次</th>
									      <th>净胜球</th>
									      <th>积分</th>
									    </tr>
									  </thead>
									  <tbody>
									  <c:forEach items="${positionList}"  var="position" varStatus="status">
									  	<c:if test="${position.team_id!=team.id}">
									  		<tr>
											  <td>${status.count}</td>
										      <td class="team-title" ><a href="team-${position.team_name_en}-${position.team_id}.html" target="_blank"><img src="assets/image/soccer/teams/25x25/${position.team_id}.png" style="width:25px;height:25px;" alt="${position.team_name}" title="${position.team_name}"/>&nbsp;${position.team_name}</a></td>
										      <td>${position.round_count}</td>
										      <td>${position.goal_count}</td>
										      <td>${position.points}</td>
										    </tr>
									  	</c:if>
									  	<c:if test="${position.team_id==team.id}">
									  		<tr>
											  <td style="border:1px solid #00A50D;border-right:0;">${status.count}</td>
										      <td class="team-title" style="border:1px solid #00A50D;border-right:0;border-left:0;"><a href="team?id=${position.team_id}" target="_blank"><img src="assets/image/soccer/teams/25x25/${position.team_id}.png" style="width:25px;height:25px;" alt="${position.team_name}" title="${position.team_name}"/>&nbsp;${position.team_name}</a></td>
										      <td style="border:1px solid #00A50D;border-right:0;border-left:0;">${position.round_count}</td>
										      <td style="border:1px solid #00A50D;border-right:0;border-left:0;">${position.goal_count}</td>
										      <td style="border:1px solid #00A50D;border-left:0;">${position.points}</td>
										    </tr>
									  	</c:if>
									   </c:forEach>
									  </tbody>
									</table>
								</div>
						</div>
					</div>
				</div>
				
				
				
				
			</div>
			
			
			<div class="col-sm-12 col-xs-12 visible-sm visible-xs">
				<div class="row" style="margin-top:10px;">
					<div class="col-lg-12 col-md-12">
						<table class="table small-table" >
							  <caption style="min-height:30px;text-align:left;"><b style="margin-left:15px;">最近五场比赛</b></caption>
							  <tbody>
							  		<c:forEach items="${lstMatchHistory}" var="history">
							  			<tr>
							  				<td class="a-title"><a href="team?id=${history.home_team_id}" target="_blank"><img src="assets/image/soccer/teams/25x25/${history.home_team_id}.png" style="width:25px;height:25px;" alt="${history.home_team_name}" title="${history.home_team_name}"/>&nbsp;${history.home_team_name}</a></td>
							  				<td class="a-title" style="text-align:center;">
							  					<c:if test="${fn:contains(history.result, '-')}">
										      		<b><a title="观看集锦" href="/match/detail?matchKey=<fmt:formatDate value="${history.match_date}" pattern="yyyy-MM-dd"/>-${history.home_team_id}vs${history.away_team_id}" target="_blank">${history.result}</a></b>
										      	</c:if>
										      	<c:if test="${!fn:contains(history.result, '-')}">
										      		<a title="直播地址" href="/match/detail?matchKey=<fmt:formatDate value="${history.match_date}" pattern="yyyy-MM-dd"/>-${history.home_team_id}vs${history.away_team_id}" target="_blank"><fmt:formatDate value="${history.match_date}" pattern="yy/MM/dd hh:mm"/></a>
										      	</c:if>
							  				</td>
							  				<td class="a-title"><a href="team?id=${history.away_team_id}" target="_blank"><img src="assets/image/soccer/teams/25x25/${history.away_team_id}.png" style="width:25px;height:25px;" alt="${history.away_team_name}" title="${history.away_team_name}"/>&nbsp;${history.away_team_name}</a></td>
							  				
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

