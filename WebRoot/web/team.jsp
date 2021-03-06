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
    <link href="https://cdn.bootcss.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/viewer/viewer.min.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/map/map.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - ${team.name}|${leagueName}${team.name}球员|${team.name}直播|${team.name}数据|${team.name}比赛|${team.name}排名</title>
	<style type="text/css">
		.text_cut{text-overflow:ellipsis;white-space:nowrap;overflow:hidden;}
	</style>
</head>

<body>
<div id="all_content">
	    <div class="container">
			<%@ include file="/common/menu.jsp"%>
		    
		    <div class="row visible-sm visible-xs" style="margin-top:45px;">
				<div id="main_content" style="min-height:20px;" class="col-sm-12 col-xs-12">		
					<div class="bread">
						当前位置：<a href="/" target="_self">首页</a>&nbsp;&gt;&nbsp;<a href="/data-${leagueENName}-${team.league_id}.html" title="${leagueName}" target="_self">数据</a>&nbsp;&gt;&nbsp;${team.name}
					</div>
				</div>
				<div class="col-sm-5 col-xs-5" style="margin-top:10px;">						
					<img src="assets/image/soccer/teams/150x150/${team.id}.png"  class="img-responsive image" alt="${team.name}" title="${team.name}"/>
				</div>
				<div class="col-sm-7 col-xs-7" style="color:grey;margin-top:10px;">	
					${team.country}&nbsp;·&nbsp;${team.setup_time}年&nbsp;·&nbsp;<a href="${team.offical_site}" target="_blank"  title="查看${team.name}官网" style="color:grey;">官网</a>
				</div>
				<div class="col-sm-7 col-xs-7" style="margin-top:10px;color:grey;">	
					地址：${team.address}
				</div>
				<div class="col-sm-7 col-xs-7" style="margin-top:10px;color:grey;">	
					${team.telphone}
				</div>
				<c:if test="${!empty postion}">
					<div class="col-sm-7 col-xs-7" style="margin-top:10px;color:grey;">	
						联赛排名：第<span style="color:black;"> ${postion} </span>名
					</div>
				</c:if>
				<div class="col-sm-12 col-xs-12" style="margin-top:10px;color:grey;">
					${team.venue_address}&nbsp;·&nbsp;${team.venue_name}&nbsp;·&nbsp;容量：${team.venue_capacity}人
				</div>
				<div class="col-sm-12 col-xs-12">
					<img src="${team.venue_small_img_local}" class="img-responsive img-rounded" alt="${team.name}球场名称：${team.venue_name}" title="${team.name}球场名称：${team.venue_name}"/>
				</div>
				
				<div class="col-sm-12 col-xs-12" style="margin-top:10px;"></div>
				<c:forEach items="${lstGroup}" var="group">
					<div class="col-sm-12 col-xs-12">
						<table class="table small-table" >
						  <caption style="min-height:30px;text-align:left;"><b style="margin-left:15px;">${group[0].position}</b></caption>
						  <tbody >
						  	<c:set var="i" value="1"/>
						  	<tr style="border-top:1px solid #dddddd; ${2==group.size()?'border-bottom:1px solid #dddddd;':''}">
							<c:forEach items="${group}" var="player">
								<td style="width:50px;border:none;"><a href="player-${player.en_url}-${player.id}.html" target="_self"><img src="${player.img_small_local}" alt="${player.name}" title="${player.name}" style="width:50px;"/></a></td>
						      	<td colspan="${i==group.size()?(group.size()==2?1:3):1}" class="team-title" style="border:none;width:250px;font-size:12px;color:grey;">
						      		<div>
												<div style="height:17px;width:17px;margin-top:2px;float:left;" class="${player.national_flag}" title="${player.nationality}" >&nbsp;</div> 
												<div style="height:17px;float:left;">
													&nbsp;<div class="text_cut" style="width:55px;line-height:17px;float:left;"><a href="player-${player.en_url}-${player.id}.html" target="_blank" title="${player.name}更多信息">${player.name}</a></div>
														<div style="height:17px;float:left;">
																<c:if test="${!empty player.number}">
																	<span title="球衣：${player.number}号">[${player.number}号]</span>
																</c:if>
														</div>
												</div>
									</div>
									<div style="line-height:20px;height:20px;clear:both;">
										${player.age}岁 &nbsp;
										<c:if test="${player.goal_count!=0}">
											<nobr><span title="进球数：${player.goal_count}" style="color:black;"><img src="assets/pages/img/goal-small.png" style="margin-top:-5px;" /> <b>${player.goal_count}</b></span></nobr>
										</c:if>
										<c:if test="${player.goal_count!=0 && player.assists_count!=0}">
										&nbsp;
										</c:if>
										<c:if test="${player.assists_count!=0}">
											<nobr><span title="助攻数：${player.assists_count}" style="color:black;"><img src="assets/pages/img/goal-assists.png" style="margin-top:-5px;" /> <b>${player.assists_count}</b></span></nobr>
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
				</c:forEach>
			</div>
			<!-- 移动端内容开始 -->
			
				<div class="row visible-sm visible-xs" style="margin-top:10px;color:grey;padding-bottom: 130px;">
					<c:if test="${!empty lstMatchHistory}">
					<div class="col-lg-12 col-md-12">
						<table class="table small-table" >
							  <caption style="min-height:30px;text-align:left;"><b style="margin-left:15px;">最近五场比赛</b></caption>
							  <tbody>
							  		<c:forEach items="${lstMatchHistory}" var="history">
							  			<tr >
							  				<td class="a-title" style="font-size:13px;"><a href="team-${history.home_team_en_name}-${history.home_team_id}.html" target="_self"><img src="assets/image/soccer/teams/25x25/${history.home_team_id}.png" style="width:25px;height:25px;" alt="${history.home_team_name}" title="${history.home_team_name}"/>&nbsp;${history.home_team_name}</a></td>
							  				<td class="a-title" style="text-align:center;font-size:13px;">
							  					<c:if test="${fn:contains(history.result, '-')}">
										      		<b>${history.result}</b>
										      	</c:if>
										      	<c:if test="${!fn:contains(history.result, '-')}">
										      		<fmt:formatDate value="${history.match_date}" pattern="yy/MM/dd"/><br><fmt:formatDate value="${history.match_date}" pattern="HH:mm"/>
										      	</c:if>
							  				</td>
							  				<td class="a-title" style="font-size:13px;"><a href="team-${history.away_team_en_name}-${history.away_team_id}.html" target="_self"><img src="assets/image/soccer/teams/25x25/${history.away_team_id}.png" style="width:25px;height:25px;" alt="${history.away_team_name}" title="${history.away_team_name}"/>&nbsp;${history.away_team_name}</a></td>
							  				<td class="a-title" style="text-align:center;font-size:13px;">
							  					<c:if test="${fn:contains(history.result, '-')}">
										      		<a title="观看集锦" href="match-${history.home_team_en_name}-vs-${history.away_team_en_name}_${history.year_show}-${history.home_team_id}vs${history.away_team_id}.html" target="_self" style="color:grey;">集锦</a>
										      	</c:if>
										      	<c:if test="${!fn:contains(history.result, '-')}">
										      		<b><a title="直播地址" href="match-${history.home_team_en_name}-vs-${history.away_team_en_name}_${history.year_show}-${history.home_team_id}vs${history.away_team_id}.html" target="_self">直播</a></b>
										      	</c:if>
							  				</td>
							  			</tr>
							  		</c:forEach>
							  </tbody>
						</table>
					</div>
					</c:if>
				</div>
			
			
			<!-- 移动端内容结束 -->
		</div>
		
		<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:70px;">
			<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
				<div class="bread">
					当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;<a href="/data-${leagueENName}-${team.league_id}.html" title="${leagueName}" target="_blank">数据</a>&nbsp;&gt;&nbsp;${team.name}
				</div>
			</div>
		</div>
		
		<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:10px;padding-bottom: 130px;">
			<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
				
				<div class="row">
					<div class="col-lg-4 col-md-4">
						<div class="col-lg-12 col-md-12">						
							<a href="${team.offical_site}" target="_blank" title="查看${team.name}官网"><img src="assets/image/soccer/teams/150x150/${team.id}.png"  style="width:150px;height:150px;" alt="${team.name}" /></a>
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:10px;">
							<span style="font-size:24px;font-weight:bold;">${team.name}</span> <span class="a-title"><a href="${team.offical_site}" target="_blank" title="查看${team.name}官网">官网</a></span>
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
					
					
					<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
						<div class="row" style="margin-top:10px;">
							<div class="col-lg-12 col-md-12">
								<img src="${team.venue_small_img_local}" big="${team.venue_img_local}" class="img-responsive img-rounded venue" style="width:300px;height:225px;cursor:pointer;" alt="${team.name}球场名称：${team.venue_name}" title="${team.name}球场名称：${team.venue_name}"/>
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
					
						<c:if test="${!empty lstMatchHistory}">
							<div class="row" style="margin-top:10px;">
								<div class="col-lg-12 col-md-12">
									<table class="table small-table" >
										  <caption style="min-height:30px;text-align:left;"><b style="margin-left:10px;">最近五场比赛</b><span class="say-info pull-right"><a href="/history-${team.name_en}-${team.id}.html" target="_blank" title="更多${team.name}的比赛">更多&gt;&gt;</a></span></caption>
										  <tbody>
										  		<c:forEach items="${lstMatchHistory}" var="history">
										  			<tr>
										  				<td class="a-title"><a href="team-${history.home_team_en_name}-${history.home_team_id}.html" target="_blank"><nobr><img src="assets/image/soccer/teams/25x25/${history.home_team_id}.png" style="width:25px;height:25px;" alt="${history.home_team_name}" title="${history.home_team_name}"/>&nbsp;<span style="font-size:12px;">${history.home_team_name}</span></nobr></a></td>
										  				<td class="a-title" style="text-align:center;min-width:100px;">
										  					<c:if test="${fn:contains(history.result, '-')}">
													      		<b><a title="观看集锦" href="match-${history.home_team_en_name}-vs-${history.away_team_en_name}_${history.year_show}-${history.home_team_id}vs${history.away_team_id}.html" target="_blank">${history.result}</a></b>
													      	</c:if>
													      	<c:if test="${!fn:contains(history.result, '-')}">
													      		<a title="直播地址" href="match-${history.home_team_en_name}-vs-${history.away_team_en_name}_${history.year_show}-${history.home_team_id}vs${history.away_team_id}.html" target="_blank"><fmt:formatDate value="${history.match_date}" pattern="yy/MM/dd HH:mm"/></a>
													      	</c:if>
										  				</td>
										  				<td class="a-title"><a href="team-${history.away_team_en_name}-${history.away_team_id}.html" target="_blank"><nobr><img src="assets/image/soccer/teams/25x25/${history.away_team_id}.png" style="width:25px;height:25px;" alt="${history.away_team_name}" title="${history.away_team_name}"/>&nbsp;<span style="font-size:12px;">${history.away_team_name}</span></nobr></a></td>
										  				
										  			</tr>
										  		</c:forEach>
										  </tbody>
									</table>
								</div>
							</div>
						</c:if>
						
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
												<td style="width:50px;border:none;"><a href="player-${player.en_url}-${player.id}.html" target="_blank" title="${player.name}更多信息"><img src="${player.img_small_local}" alt="${player.name}" style="width:50px;"/></a></td>
										      	<td colspan="${i==group.size()?(group.size()==2?1:3):1}" class="team-title" style="border:none;width:250px;font-size:13px;color:grey;">
										      		<div>
											      				<div style="height:17px;width:17px;margin-top:2px;float:left;" class="${player.national_flag}" title="${player.nationality}" >&nbsp;</div> 
											      				<div style="height:20px;float:left;">
												      				&nbsp;<a href="player-${player.en_url}-${player.id}.html" target="_blank" title="${player.name}更多信息">${player.name}</a>
													      			<c:if test="${!empty player.number}">
														      			<span title="球衣：${player.number}号">[${player.number}号]</span>
														      		</c:if>
													      		</div>
											      	</div>
											      	<div style="line-height:20px;height:20px;clear:both;">
											      		${player.age}岁 &nbsp;
											      		<c:if test="${player.goal_count!=0}">
											      			<nobr><span title="进球数：${player.goal_count}" style="color:black;"><img src="assets/pages/img/goal-small.png" style="margin-top:-5px;" /> <b>${player.goal_count}</b></span></nobr>
											      		</c:if>
											      		<c:if test="${player.goal_count!=0 && player.assists_count!=0}">
											      		&nbsp;
											      		</c:if>
											      		<c:if test="${player.assists_count!=0}">
											      			<nobr><span title="助攻数：${player.assists_count}" style="color:black;"><img src="assets/pages/img/goal-assists.png" style="margin-top:-5px;" /> <b>${player.assists_count}</b></span></nobr>
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
					<div class="col-lg-4 col-md-4 hidden-sm hidden-xs">
						<c:if test="${!empty lstMatchHistory}">
							<div class="row " style="margin-top:10px;">
									<div class="col-lg-12 col-md-12">
										<table class="table" style="border:1px solid #dddddd;">
										  <caption style="min-height:30px;text-align:left;"><b style="margin-left:10px;">联赛排名</b></caption>
										  <thead>
										    <tr style="background:#3CB371;color:white;border-left:1px solid #3CB371;border-right:1px solid #3CB371;font-size:12px;">
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
											      <td class="team-title" ><a href="team-${position.team_name_en}-${position.team_id}.html" target="_blank"><nobr><img src="assets/image/soccer/teams/25x25/${position.team_id}.png" style="width:25px;height:25px;" alt="${position.team_name}" title="${position.team_name}"/>&nbsp;${position.team_name}</nobr></a></td>
											      <td>${position.round_count}</td>
											      <td>${position.goal_count}</td>
											      <td>${position.points}</td>
											    </tr>
										  	</c:if>
										  	<c:if test="${position.team_id==team.id}">
										  		<tr>
												  <td style="border:1px solid #00A50D;border-right:0;">${status.count}</td>
											      <td class="team-title" style="border:1px solid #00A50D;border-right:0;border-left:0;"><a href="team-${position.team_name_en}-${position.team_id}.html" target="_blank"><nobr><img src="assets/image/soccer/teams/25x25/${position.team_id}.png" style="width:25px;height:25px;" alt="${position.team_name}" title="${position.team_name}"/>&nbsp;${position.team_name}</nobr></a></td>
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
						</c:if>
						
						
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
							  				<td class="a-title"><a href="team-${history.home_team_en_name}-${history.home_team_id}.html" target="_blank"><img src="assets/image/soccer/teams/25x25/${history.home_team_id}.png" style="width:25px;height:25px;" alt="${history.home_team_name}" title="${history.home_team_name}"/>&nbsp;${history.home_team_name}</a></td>
							  				<td class="a-title" style="text-align:center;">
							  					<c:if test="${fn:contains(history.result, '-')}">
										      		<b><a title="观看集锦" href="match-${history.home_team_en_name}-vs-${history.away_team_en_name}_<fmt:formatDate value="${history.match_date}" pattern="yyyy-MM-dd"/>-${history.home_team_id}vs${history.away_team_id}.html" target="_blank">${history.result}</a></b>
										      	</c:if>
										      	<c:if test="${!fn:contains(history.result, '-')}">
										      		<a title="直播地址" href="match-${history.home_team_en_name}-vs-${history.away_team_en_name}_<fmt:formatDate value="${history.match_date}" pattern="yyyy-MM-dd"/>-${history.home_team_id}vs${history.away_team_id}.html" target="_blank"><fmt:formatDate value="${history.match_date}" pattern="yy/MM/dd HH:mm"/></a>
										      	</c:if>
							  				</td>
							  				<td class="a-title"><a href="team-${history.away_team_en_name}-${history.away_team_id}.html" target="_blank"><img src="assets/image/soccer/teams/25x25/${history.away_team_id}.png" style="width:25px;height:25px;" alt="${history.away_team_name}" title="${history.away_team_name}"/>&nbsp;${history.away_team_name}</a></td>
							  				
							  			</tr>
							  		</c:forEach>
							  </tbody>
						</table>
					</div>
				</div>
			</div>
			
		</div>
		
	<%@ include file="/common/footer.jsp"%>		
	</div>
	
	<script src="assets/global/plugins/viewer/viewer-jquery.min.js" type="text/javascript"></script>
	
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
	
	$(function(){
		$('.image').viewer(
				{toolbar:false, 
				zIndex:20000,
				shown: function() {
					$(".viewer-canvas").attr("data-action","mix");
				 }
			 }
		);
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
	</script>
	
	
		
</body>	

