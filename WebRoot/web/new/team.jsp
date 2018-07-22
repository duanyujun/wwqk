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
    <meta name="apple-mobile-web-app-capable" content="yes">
    <link href="https://cdn.bootcss.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="/common/new/main.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/map/map.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/viewer/viewer.min.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - ${team.name}|${leagueName}${team.name}球员|${team.name}直播|${team.name}数据|${team.name}比赛|${team.name}排名</title>
	
</head>

<body>
<!-- header start -->
<%@ include file="/common/new/menu.jsp"%>

<!-- pc content start -->
<div class="main hidden-sm hidden-xs">
	<div class="left_w930">
			<div class="bread mb_10 tleft">
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;<a href="/data-${leagueENName}-${team.league_id}.html" title="${leagueName}" target="_blank">数据</a>&nbsp;&gt;&nbsp;${team.name}
			</div>
			<div class="left tleft w66">
				<div class="w50 left mt10">
					<div class="w100left">						
						<a href="${team.offical_site}" target="_blank" title="查看${team.name}官网"><img src="assets/image/soccer/teams/150x150/${team.id}.png" class="w150h150" alt="${team.name}" /></a>
					</div>
					<div class="w100left mt10">
						<span style="font-size:24px;font-weight:bold;">${team.name}</span> <span class="a-title"><a href="${team.offical_site}" target="_blank" title="查看${team.name}官网">官网</a></span>
					</div>
					<div class="w100left mt10">
						成立时间：${team.setup_time}
					</div>
					<div class="w100left mt10">
						地址：${team.address}
					</div>
					<div class="w100left mt10">
						国家：${team.country}
					</div>
					<div class="w100left mt10">
						电话：${team.telphone}
					</div>
				</div>
				
				<div class="mt10 left w50">
					<div class="w100left">
						<img src="${team.venue_small_img_local}" big="${team.venue_img_local}" class="img-responsive venue w300h225 pointer"  alt="${team.name}球场名称：${team.venue_name}" title="${team.name}球场名称：${team.venue_name}"/>
					</div>
					<div class="w100left mt10">
						球场名称：${team.venue_name}
					</div>
					<div class="w100left mt10">
						所在城市：${team.venue_address}
					</div>
					<div class="w100left mt10">
						观众容量：${team.venue_capacity}人
					</div>
				</div>
				
				<div class="w100left">
						<c:forEach items="${lstGroup}" var="group">
							<div class="mt20" >
								<div class="w100" >
									<table class="table" >
									  <caption class="tleft mh30"><b class="ml15">${group[0].position}</b></caption>
									  <tbody >
									  	<c:set var="i" value="1"/>
									  	<tr style="border-top:1px solid #dddddd; ${2==group.size()?'border-bottom:1px solid #dddddd;':''}">
										<c:forEach items="${group}" var="player">
											<td style="width:50px;border:none;"><a href="player-${player.en_url}-${player.id}.html" target="_blank" title="${player.name}更多信息"><img src="${player.img_small_local}" alt="${player.name}" style="width:50px;"/></a></td>
									      	<td colspan="${i==group.size()?(group.size()==2?1:3):1}" class="team-title" style="border:none;width:250px;font-size:13px;color:grey;">
									      		<div style="text-align:left;">
										      				<div class="nation-flag ${player.national_flag}" title="${player.nationality}" >&nbsp;</div> 
										      				<div class="left h20">
											      				&nbsp;<a href="player-${player.en_url}-${player.id}.html" target="_blank" title="${player.name}更多信息">${player.name}</a>
												      			<c:if test="${!empty player.number}">
													      			<span title="球衣：${player.number}号">[${player.number}号]</span>
													      		</c:if>
												      		</div>
										      	</div>
										      	<div class="team-p-info">
										      		${player.age}岁 &nbsp;
										      		<c:if test="${player.goal_count!=0}">
										      			<nobr><span title="进球数：${player.goal_count}" style="color:black;"><img src="assets/pages/img/goal-small.png" class="mtn5" /> <b>${player.goal_count}</b></span></nobr>
										      		</c:if>
										      		<c:if test="${player.goal_count!=0 && player.assists_count!=0}">
										      		&nbsp;
										      		</c:if>
										      		<c:if test="${player.assists_count!=0}">
										      			<nobr><span title="助攻数：${player.assists_count}" style="color:black;"><img src="assets/pages/img/goal-assists.png" class="mtn5" /> <b>${player.assists_count}</b></span></nobr>
										      		</c:if>
										      	</div>
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
			</div>
			<!-- 右侧 -->
			<div class="team-match">
				<div class="w100left" >
					<table class="table small-table" >
							  <caption class="mh30 tleft"><b class="ml15">最近五场比赛</b></caption>
							  <tbody>
							  		<c:forEach items="${lstMatchHistory}" var="history">
							  			<tr>
							  				<td class="a-title tleft" ><a href="team-${history.home_team_en_name}-${history.home_team_id}.html" target="_blank"><img src="assets/image/soccer/teams/25x25/${history.home_team_id}.png" class="w25h25" alt="${history.home_team_name}" title="${history.home_team_name}"/>&nbsp;${history.home_team_name}</a></td>
							  				<td class="a-title tcenter">
							  					<c:if test="${fn:contains(history.result, '-')}">
										      		<b><a title="观看集锦" href="match-${history.home_team_en_name}-vs-${history.away_team_en_name}_<fmt:formatDate value="${history.match_date}" pattern="yyyy-MM-dd"/>-${history.home_team_id}vs${history.away_team_id}.html" target="_blank">${history.result}</a></b>
										      	</c:if>
										      	<c:if test="${!fn:contains(history.result, '-')}">
										      		<a title="直播地址" href="match-${history.home_team_en_name}-vs-${history.away_team_en_name}_<fmt:formatDate value="${history.match_date}" pattern="yyyy-MM-dd"/>-${history.home_team_id}vs${history.away_team_id}.html" target="_blank"><fmt:formatDate value="${history.match_date}" pattern="yy/MM/dd HH:mm"/></a>
										      	</c:if>
							  				</td>
							  				<td class="a-title tleft" >&nbsp;&nbsp;&nbsp;<a href="team-${history.away_team_en_name}-${history.away_team_id}.html" target="_blank"><img src="assets/image/soccer/teams/25x25/${history.away_team_id}.png" class="w25h25" alt="${history.away_team_name}" title="${history.away_team_name}"/>&nbsp;${history.away_team_name}</a></td>
							  				
							  			</tr>
							  		</c:forEach>
							  </tbody>
						</table>
				</div>
			</div>
			
			<div class="team-rank">
					<c:if test="${!empty lstMatchHistory}">
						<div class="w100left">
								<table class="table team-rank-table">
								  <caption class="mh30 tleft" ><b class="ml10">联赛排名</b></caption>
								  <thead>
								    <tr class="team-rank-title">
								      <th><nobr>排名</nobr></th>
								      <th><nobr>球队</nobr></th>
								      <th><nobr>场次</nobr></th>
								      <th><nobr>净胜球</nobr></th>
								      <th><nobr>积分</nobr></th>
								    </tr>
								  </thead>
								  <tbody>
								  <c:forEach items="${positionList}"  var="position" varStatus="status">
								  	<c:if test="${position.team_id!=team.id}">
								  		<tr>
										  <td>${status.count}</td>
									      <td class="team-title" ><a href="team-${position.team_name_en}-${position.team_id}.html" target="_blank"><nobr><img src="assets/image/soccer/teams/25x25/${position.team_id}.png" class="w25h25" title="${position.team_name}"/>&nbsp;${position.team_name}</nobr></a></td>
									      <td>${position.round_count}</td>
									      <td>${position.goal_count}</td>
									      <td>${position.points}</td>
									    </tr>
								  	</c:if>
								  	<c:if test="${position.team_id==team.id}">
								  		<tr>
										  <td class="team-border border-right0" style="border-top:1px solid #00A50D;">${status.count}</td>
									      <td class="team-title team-border border-right0 border-left0" style="border-top:1px solid #00A50D;"><a href="team-${position.team_name_en}-${position.team_id}.html" target="_blank"><nobr><img src="assets/image/soccer/teams/25x25/${position.team_id}.png" class="w25h25"  title="${position.team_name}"/>&nbsp;${position.team_name}</nobr></a></td>
									      <td class="team-border border-right0 border-left0" style="border-top:1px solid #00A50D;">${position.round_count}</td>
									      <td class="team-border border-right0 border-left0" style="border-top:1px solid #00A50D;">${position.goal_count}</td>
									      <td class="team-border border-left0" style="border-top:1px solid #00A50D;">${position.points}</td>
									    </tr>
								  	</c:if>
								   </c:forEach>
								  </tbody>
								</table>
							</div>
					</c:if>
			</div>
			
	</div>
</div>
<!-- pc content end -->

<%@ include file="/common/new/team-mobile.jsp"%>	

<!-- footer start -->
<%@ include file="/common/new/footer.jsp"%>	

<script src="assets/global/plugins/viewer/viewer-jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		$('.venue').viewer(
				{toolbar:false, 
				zIndex:20000,
				url:"big",
				shown: function() {
					$(".viewer-canvas").attr("data-action","mix");
				 }
			 }
		);
	});
	
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
