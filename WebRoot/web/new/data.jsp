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
    <meta name="apple-mobile-web-app-capable" content="yes">
    <link href="https://cdn.bootcss.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="/common/new/main.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - ${leagueName}足球数据|${leagueName}射手榜|${leagueName}助攻榜|${leagueName}积分榜|${leagueName}联赛排名</title>
	<style type="text/css">
		.sn-list>ul{-webkit-padding-start:1%;}
		.table>thead>tr>th{border-bottom:none;}
		.label{font-size:14px;}
	</style>
</head>

<body>
<!-- header start -->
<%@ include file="/common/new/menu.jsp"%>

<!-- pc content start -->
<div class="main hidden-sm hidden-xs">
	<div class="left_w930">
		<div class="bread left mb_5">
			当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;数据
		</div>
	
		<div class="w100left">
			<div class="w20left">
				<a href="data-premier-league-1.html" class="link-img"><img src="assets/image/page/league-logo1.jpg" class="w60h60"/></a>
			</div>
			<div class="w20left">
				<a href="data-primera-division-2.html" class="link-img"><img src="assets/image/page/league-logo2.jpg" class="w60h60"/></a>
			</div>
			<div class="w20left">
				<a href="data-bundesliga-3.html" class="link-img"><img src="assets/image/page/league-logo3.jpg" class="w60h60"/></a>
			</div>
			<div class="w20left">
				<a href="data-sesie-a-4.html" class="link-img"><img src="assets/image/page/league-logo4.jpg" class="w60h60"/></a>
			</div>
			<div class="w20left">
				<a href="data-ligue-1-5.html" class="link-img"><img src="assets/image/page/league-logo5.jpg" class="w60h60"/></a>
			</div>
		</div>
		<div class="w100left mt_8" >
			<div class="team-title w20left fs14_pl64">
				<a href="data-premier-league-1.html" ><div class="${leagueId==1?'select-league':'common-league'}">英超</div></a>
			</div>
			<div class="team-title w20left fs14_pl64">
				<a href="data-primera-division-2.html" ><div class="${leagueId==2?'select-league':'common-league'}">西甲</div></a>
			</div>
			<div class="team-title w20left fs14_pl64">
				<a href="data-bundesliga-3.html" ><div class="${leagueId==3?'select-league':'common-league'}">德甲</div></a>
			</div>
			<div class="team-title w20left fs14_pl64">
				<a href="data-sesie-a-4.html" ><div class="${leagueId==4?'select-league':'common-league'}">意甲</div></a>
			</div>
			<div class="team-title w20left fs14_pl64">
				<a href="data-ligue-1-5.html" ><div class="${leagueId==5?'select-league':'common-league'}">法甲</div></a>
			</div>
		</div>
		
		<div class="w100left mt_15">
			<ul id="myTab" class="nav nav-tabs bread" >
				<li class="active"><a href="#match_rank" data-toggle="tab">赛程</a></li>
				<li ><a href="#team_rank" data-toggle="tab">球队排名</a></li>
				<li><a href="#player_rank" data-toggle="tab">球员排名</a></li>
			</ul>
			<div id="myTabContent" class="tab-content">
					<div class="tab-pane fade in active data-rank" id="match_rank">
						<div class="schedule-nav" >
							    <div class="sn-list round">
							        <ul class="udv-clearfix" >
							            <c:forEach items="${lstRound}" var="round">
							            	<li title="第${round}轮" ><a href="data-${leagueENName}-r${round}-${leagueId}.html" target="_self" class="${round==currentRound?'current':''}">${round}</a></li>
							            </c:forEach>
							            
							            <li title="更多比赛"><a href="history-${leagueENName}-${leagueId}.html" target="_blank">...</a></li>
							        </ul>
							    </div>
							</div>

							<div id="roundCtr" class="mb5ml1">
								<div class="schedule-round">
								    <div class="sr-ctr">
								        <div class="sr-ctr-in udv-clearfix" >
								        	<c:forEach items="${lstMatch}" var="match" begin="0" end="4">
									            <div class="sr-box" >
									                <div class="up">
									                    <p class="date"><fmt:formatDate value="${match.match_date}" pattern="MM-dd"/> <span style="color:#444;">${match.match_weekday}</span> <fmt:formatDate value="${match.match_date}" pattern="HH:mm"/></p>
									                    <p class="team a-title"><a href="team-${match.home_team_en_name}-${match.home_team_id}.html" target="_blank" class="link-333333 ml35"><img src="${match.home_team_img}" height="20" width="20" alt="${match.home_team_name}">&nbsp;${match.home_team_name}</a></p>
					 				                    <c:if test="${match.status!='完场'}">
						 				                    <p class="time a-title" title="直播">
						 				                    	<c:if test="${match.game_id!='0'}">
						 				                    		<a href="match-${match.home_team_en_name}-vs-${match.away_team_en_name}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_blank" ><span class="label label-success">情报</span></a>
						 				                    	</c:if>
						 				                    	<c:if test="${match.game_id=='0'}">
						 				                    		<a href="match-${match.home_team_en_name}-vs-${match.away_team_en_name}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_blank" ><img src="assets/image/page/vs.png" style="width:20px;"/></a>
						 				                    	</c:if>
						 				                    </p>
					 				                    </c:if>
					 				                    <c:if test="${match.status=='完场'}">
					 				                    	<p class="time a-title" title="集锦"><a href="match-${match.home_team_en_name}-vs-${match.away_team_en_name}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_blank" style="color:red;">${match.result}</a></p>
					 				                    </c:if>
									                    
									                    <p class="team_away a-title"><a href="team-${match.away_team_en_name}-${match.away_team_id}.html" target="_blank" class="link-333333 ml35"><img src="${match.away_team_img}" height="20" width="20" alt="${match.away_team_name}">&nbsp;${match.away_team_name}</a></p>
									                </div>
									            </div>
								        	</c:forEach>
								        </div>
								        <div class="sr-ctr-in udv-clearfix" style="clear:both;">
								        	<c:forEach items="${lstMatch}" var="match" begin="5" >
									            <div class="sr-box" style="margin-top:10px;">
									                <div class="up">
									                    <p class="date"><fmt:formatDate value="${match.match_date}" pattern="MM-dd"/> <span style="color:#444;">${match.match_weekday}</span> <fmt:formatDate value="${match.match_date}" pattern="HH:mm"/></p>
									                    <p class="team a-title"><a href="team-${match.home_team_en_name}-${match.home_team_id}.html" target="_blank" class="link-333333 ml35"><img src="${match.home_team_img}" height="20" width="20" alt="${match.home_team_name}">&nbsp;${match.home_team_name}</a></p>
					 				                    <c:if test="${match.status!='完场'}">
						 				                    <p class="time a-title" title="直播">
						 				                    	<c:if test="${match.game_id!='0'}">
						 				                    		<a href="match-${match.home_team_en_name}-vs-${match.away_team_en_name}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_blank" ><span class="label label-success">情报</span></a>
						 				                    	</c:if>
						 				                    	<c:if test="${match.game_id=='0'}">
						 				                    		<a href="match-${match.home_team_en_name}-vs-${match.away_team_en_name}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_blank" ><img src="assets/image/page/vs.png" style="width:20px;"/></a>
						 				                    	</c:if>
						 				                    </p>
					 				                    </c:if>
					 				                    <c:if test="${match.status=='完场'}">
					 				                    	<p class="time a-title" title="集锦"><a href="match-${match.home_team_en_name}-vs-${match.away_team_en_name}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_blank" style="color:red;">${match.result}</a></p>
					 				                    </c:if>
									                    
									                    <p class="team_away a-title"><a href="team-${match.away_team_en_name}-${match.away_team_id}.html" target="_blank" class="link-333333 ml35"><img src="${match.away_team_img}" height="20" width="20" alt="${match.away_team_name}">&nbsp;${match.away_team_name}</a></p>
									                </div>
									            </div>
								        	</c:forEach>
								        </div>
								    </div>
								</div>
							</div>
					</div>
			
					<div class="tab-pane fade" id="team_rank">
							<table class="table table-hover data-team-rank" >
							  <thead>
							  	<tr >
							  		<th colspan="10" class="data-team-rank-trth"><center>积分榜</center></th>
							  	</tr>
							    <tr class="data-team-rank-tr">
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
							      <td class="team-title" ><a href="team-${team.team_name_en}-${team.team_id}.html" target="_blank"><img src="assets/image/soccer/teams/25x25/${team.team_id}.png" class="w25h25" alt="${team.team_name}" title="${team.team_name}"/>&nbsp;${team.team_name}</a></td>
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
					<div class="tab-pane fade" id="player_rank">
										<table class="table table-striped table-hover data-player-rank" >
										  <thead>
										  	<tr >
										  		<th colspan="5" class="data-player-rank-trth"><center>射手榜</center></th>
										  	</tr>
										    <tr class="data-player-rank-tr">
										      <th>排名</th>
										      <th>球员</th>
										      <th>球队</th>
										      <th><center>进球数</center></th>
										      <th ><center>点球数</center></th>
										    </tr>
										  </thead>
										  <tbody>
										  	<c:forEach items="${shooterList}"  var="shooter" varStatus="status">
											    <tr>
											      <td>${status.count}</td>
											      <td class="team-title" ><a href="player-${shooter.player_name_en}-${shooter.player_id}.html" target="_blank"><img src="${shooter.player_img}" class="w25h25" alt="${shooter.player_name}" title="${shooter.player_name}"/>&nbsp;${shooter.player_name}</a></td>
											      <td class="team-title" ><a href="team-${shooter.team_name_en}-${shooter.team_id}.html" target="_blank"><img src="assets/image/soccer/teams/25x25/${shooter.team_id}.png" class="w25h25" alt="${shooter.team_name}" title="${shooter.team_name}"/>&nbsp;${shooter.team_name}</a></td>
											      <td ><center>${shooter.goal_count}</center></td>
											      <td class="br1"><center>${shooter.penalty_count}</center></td>
											    </tr>
											</c:forEach>
										  </tbody>
										</table>
									
										<table class="table table-striped table-hover pull-right data-assist-rank" >
										  <thead>
										  	<tr>
										  		<th colspan="4" class="data-assist-trth"><center>助攻榜</center></th>
										  	</tr>
										    <tr class="data-assist-tr" >
										      <th><nobr>排名</nobr></th>
										      <th>球员</th>
										      <th>球队</th>
										      <th align="center"><nobr>助攻数</nobr></th>
										    </tr>
										  </thead>
										  <tbody>
										  	<c:forEach items="${assistsList}"  var="assists" varStatus="status">
											    <tr>
											      <td style="border-left:1px solid #dddddd;">${status.count}</td>
											      <td class="team-title" ><a href="player-${assists.player_name_en}-${assists.player_id}.html" target="_blank"><nobr><img src="${assists.player_img}" class="w25h25" alt="${assists.player_name}" title="${assists.player_name}" />&nbsp;${assists.player_name}</nobr></a></td>
											      <td class="team-title" ><a href="team-${assists.team_name_en}-${assists.team_id}.html" target="_blank"><nobr><img src="assets/image/soccer/teams/25x25/${assists.team_id}.png" class="w25h25" alt="${assists.team_name}" title="${assists.team_name}"/>&nbsp;${assists.team_name}</nobr></a></td>
											      <td ><center>${assists.assists_count}</center></td>
											    </tr>
										    </c:forEach>
										  </tbody>
										</table>
					</div>
					
			</div>
				
		</div>
		
	</div>
</div>
<!-- pc content end -->

<%@ include file="/common/new/data-mobile.jsp"%>	

<!-- footer start -->
<%@ include file="/common/new/footer.jsp"%>	

<script src="common/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>

<script>
(function(){
    var bp = document.createElement('script');
    var curProtocol = window.location.protocol.split(':')[0];
    if (curProtocol === 'https') {
        bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';        
    }
    else {
        bp.src = 'http://push.zhanzhang.baidu.com/push.js';
    }
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(bp, s);
})();
</script>

</body>	
</html>
