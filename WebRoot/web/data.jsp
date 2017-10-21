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
	<style type="text/css">
		ul{-webkit-padding-start:1%;}
		.table>thead>tr>th{border-bottom:none;}
	</style>
</head>

<body>
<div id="all_content">
	 <div class="container">
		<div class="row menu_bg clear_row_margin hidden-sm hidden-xs" >
			<div id="main_nav" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">	
				<div>
					<div class="logo_div">
						<a href="" title="首页">趣点足球网</a>
					</div>
					<ul style="float:left;">
						<li class="menu_width"><a href="">首页</a></li>
						<li class="menu_width"><a href="fun.html">趣点</a></li>
						<li class="menu_width"><a href="live.html">直播</a></li>
						<li class="menu_width"><a href="bifen.html">比分</a></li>
						<li class="menu_width menu_sel"><a href="data.html">数据</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="row menu_link navbar-fixed-top visible-sm visible-xs" style="background:#00A50D;min-height:35px;color:white;">
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/" target="_self"><span class="wwqk_menu">首页</span></a>
	       	</div>
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/fun.html" target="_self"><span class="wwqk_menu">趣点</span></a>
	       	</div>
	       	<div class="col-xs-3 col-sm-3 wwqk_menu_wh" >
	       		<a href="/live.html" target="_self"><span class="wwqk_menu">直播</span></a>
	       	</div>
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/bifen.html" target="_self"><span class="wwqk_menu">比分</span></a>
	       	</div>
	       	<div class="col-xs-3 col-sm-3 wwqk_menu_wh" >
	       		<a href="/data.html" target="_self"><span class="wwqk_menu dline">数据</span></a>
	       	</div>
	    </div>
	    
	    <!-- 移动端内容开始 -->
    	
    	<div class="row visible-sm visible-xs" style="margin-top:35px;">
			<div class="col-sm-2 col-xs-2" style="${leagueId==1?'padding-top:10px;':'padding-top:20px;'}">
				<a href="data-premier-league-1.html" class="link-img" style="${leagueId==1?'margin-left:2px;':'margin-left:10px;'}"><img src="assets/image/page/league-logo1.jpg" style="${leagueId==1?'width:55px;height:55px;':'width:40px;height:40px;'}"/></a>
			</div>
			<div class="col-sm-2 col-xs-2" style="${leagueId==2?'padding-top:10px;':'padding-top:20px;'}">
				<a href="data-primera-division-2.html" class="link-img" style="${leagueId==2?'margin-left:5px;':'margin-left:10px;'}"><img src="assets/image/page/league-logo2.jpg" style="${leagueId==2?'width:50px;height:50px;margin-top:-3px;':'width:40px;height:40px;'}"/></a>
			</div>
			<div class="col-sm-2 col-xs-2" style="${leagueId==3?'padding-top:10px;':'padding-top:20px;'}">
				<a href="data-bundesliga-3.html" class="link-img" style="${leagueId==3?'margin-left:5px;':'margin-left:10px;'}"><img src="assets/image/page/league-logo3.jpg" style="${leagueId==3?'width:50px;height:50px;':'width:40px;height:40px;'}"/></a>
			</div>
			<div class="col-sm-2 col-xs-2" style="${leagueId==4?'padding-top:10px;':'padding-top:20px;'}" >
				<a href="data-sesie-a-4.html" class="link-img" style="${leagueId==4?'margin-left:5px;':'margin-left:10px;'}"><img src="assets/image/page/league-logo4.jpg" style="${leagueId==4?'width:50px;height:50px;margin-top:-3px;':'width:40px;height:40px;'}"/></a>
			</div>
			<div class="col-sm-2 col-xs-2" style="${leagueId==5?'padding-top:10px;':'padding-top:20px;'}" >
				<a href="data-ligue-1-5.html" class="link-img" style="${leagueId==5?'margin-left:5px;':'margin-left:10px;'}"><img src="assets/image/page/league-logo5.jpg" style="${leagueId==5?'width:50px;height:50px;margin-top:-3px;':'width:40px;height:40px;'}"/></a>
			</div>
		</div>
		<div class="row visible-sm visible-xs">
			<div class="col-sm-2 col-xs-2  team-title" style="font-size:14px;">
				<a href="data-premier-league-1.html" target="_self"><div class="${leagueId==1?'select-league':'common-league'} xxs-font">英超</div></a>
			</div>
			<div class="col-sm-2 col-xs-2 team-title" style="font-size:14px;">
				<a href="data-primera-division-2.html" target="_self"><div class="${leagueId==2?'select-league':'common-league'} xxs-font">西甲</div></a>
			</div>
			<div class="col-sm-2 col-xs-2 team-title" style="font-size:14px;">
				<a href="data-bundesliga-3.html" target="_self"><div class="${leagueId==3?'select-league':'common-league'} xxs-font">德甲</div></a>
			</div>
			<div class="col-sm-2 col-xs-2 team-title" style="font-size:14px;">
				<a href="data-sesie-a-4.html" target="_self"><div class="${leagueId==4?'select-league':'common-league'} xxs-font">意甲</div></a>
			</div>
			<div class="col-sm-2 col-xs-2 team-title" style="font-size:14px;">
				<a href="data-ligue-1-5.html" target="_self" ><div class="${leagueId==5?'select-league':'common-league'} xxs-font">法甲</div></a>
			</div>
		</div>
	    
    	<div class="row visible-sm visible-xs" style="margin-top:10px;margin-bottom:130px;">
			<div class="col-sm-12 col-xs-12" style="padding-left:10px;padding-right:10px;">
				<ul id="myTab" class="nav nav-tabs bread" >
					<li class="active"><a href="#m_match_rank" data-toggle="tab" class="xxs-font">赛程</a></li>
					<li ><a href="#m_team_rank" data-toggle="tab" class="xxs-font">积分榜</a></li>
					<li><a href="#m_soccer_rank" data-toggle="tab" class="xxs-font">射手榜</a></li>
					<li><a href="#m_assist_rank" data-toggle="tab" class="xxs-font">助攻榜</a></li>
					
				</ul>
				<div id="myTabContent" class="tab-content">
						<div class="tab-pane fade in active" id="m_match_rank" style="border:1px solid #ddd;border-top:none;padding-bottom:8px;">
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
	
								<div id="roundCtr" style="margin-bottom:5px;margin-left:1%;">
									<div class="schedule-round">
									    <div class="sr-ctr">
									        <div class="sr-ctr-in udv-clearfix" style="padding-left:4%;margin-left:-15px;">
									        	<c:forEach items="${lstMatch}" var="match">
										            <div class="sr-box" style="width:45%;margin-left:4%;">
										                <div class="up">
										                    <p class="date"><fmt:formatDate value="${match.match_date}" pattern="MM-dd"/> ${match.match_weekday} <fmt:formatDate value="${match.match_date}" pattern="HH:mm"/></p>
										                    <p class="team a-title"><a href="team-${match.home_team_en_name}-${match.home_team_id}.html" target="_blank" class="link-333333 ml35"><img src="${match.home_team_img}" height="20" width="20" alt="${match.home_team_name}">&nbsp;${match.home_team_name}</a></p>
						 				                    <c:if test="${match.status!='完场'}">
							 				                    <p class="time a-title" title="直播"><a href="match-${match.home_team_en_name}-vs-${match.away_team_en_name}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_blank" ><img src="assets/image/page/vs.png" style="width:20px;"/></a></p>
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
				
						<div class="tab-pane fade" id="m_team_rank">
								<table class="table" style="border:1px solid #dddddd;border-top:none;">
								  <thead>
								  	
								    <tr style="color:black;font-size:12px;">
								      <th style="width:7px;"></th>
								      <th>球队</th>
								      <th><nobr>场次</nobr></th>
								      <th>胜</th>
								      <th>平</th>
								      <th>负</th>
								      <th>+/-</th>
								      <th><nobr>积分</nobr></th>
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
									  <td style="text-align:right;">${status.count}</td>
								      <td class="team-title" ><a href="team-${team.team_name_en}-${team.team_id}.html" target="_blank"><img class="xxs-no-show" src="assets/image/soccer/teams/25x25/${team.team_id}.png" style="width:25px;height:25px;" alt="${team.team_name}" title="${team.team_name}"/>&nbsp;${team.team_name}</a></td>
								      <td style="text-align:center;">${team.round_count}</td>
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
						<div class="tab-pane fade" id="m_soccer_rank">
											<table class="table " style="float:left;border:1px solid #dddddd;border-top:none;font-size:12px;">
											  <thead>
											      <th style="width:8px;"></th>
											      <th>球员</th>
											      <th>球队</th>
											      <th><center><nobr>进球</nobr></center></th>
											      <th ><center><nobr>点球</nobr></center></th>
											  </thead>
											  <tbody>
											  	<c:forEach items="${shooterList}"  var="shooter" varStatus="status">
												    <tr>
												      <td style="text-align:right;">${status.count}</td>
												      <td class="team-title" ><a href="player-${shooter.player_name_en}-${shooter.player_id}.html" target="_blank"><img src="${shooter.player_img}" style="width:25px;height:25px;" alt="${shooter.player_name}" title="${shooter.player_name}"/>&nbsp;${shooter.player_name}</a></td>
												      <td class="team-title" ><a href="team-${shooter.team_name_en}-${shooter.team_id}.html" target="_blank">${shooter.team_name}</a></td>
												      <td ><center>${shooter.goal_count}</center></td>
												      <td style="border-right:1px solid #dddddd;"><center>${shooter.penalty_count}</center></td>
												    </tr>
												</c:forEach>
											  </tbody>
											</table>
						</div>
						
						<div class="tab-pane fade" id="m_assist_rank">
											<table class="table" style="float:left;border:1px solid #dddddd;border-top:none;font-size:12px;">
											  <thead>
											  	  <th style="width:8px;"></th>
											      <th>球员</th>
											      <th>球队</th>
											      <th align="center">助攻数</th>
											  </thead>
											  <tbody>
											  	<c:forEach items="${assistsList}"  var="assists" varStatus="status">
												    <tr>
												      <td style="border-left:1px solid #dddddd;text-align:right;">${status.count}</td>
												      <td class="team-title" ><a href="player-${assists.player_name_en}-${assists.player_id}.html" target="_blank"><nobr><img src="${assists.player_img}" style="width:25px;height:25px;" alt="${assists.player_name}" title="${assists.player_name}" />&nbsp;${assists.player_name}</nobr></a></td>
												      <td class="team-title" ><a href="team-${assists.team_name_en}-${assists.team_id}.html" target="_blank"><nobr>${assists.team_name}</nobr></a></td>
												      <td ><center>${assists.assists_count}</center></td>
												    </tr>
											    </c:forEach>
											  </tbody>
											</table>
						</div>
						
				</div>
				
			</div>
		</div>
				
	    
	    <!-- 移动端内容结束 -->
	 </div>
		
		<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:70px;">
			<div id="main_content" style="min-height:10px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
				<div class="bread">
					当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;数据
				</div>
			</div>
		</div>
		
		<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:20px;padding-bottom: 130px;">
			<div id="main_content" class="col-lg-10 col-lg-offset-2 col-md-10 col-md-offset-2">		
				<div class="row">
					<div class="col-lg-2 col-md-2">
						<a href="data-premier-league-1.html" class="link-img"><img src="assets/image/page/league-logo1.jpg" style="width:60px;height:60px;"/></a>
					</div>
					<div class="col-lg-2 col-md-2">
						<a href="data-primera-division-2.html" class="link-img"><img src="assets/image/page/league-logo2.jpg" style="width:60px;height:60px;"/></a>
					</div>
					<div class="col-lg-2 col-md-2">
						<a href="data-bundesliga-3.html" class="link-img"><img src="assets/image/page/league-logo3.jpg" style="width:60px;height:60px;"/></a>
					</div>
					<div class="col-lg-2 col-md-2">
						<a href="data-sesie-a-4.html" class="link-img"><img src="assets/image/page/league-logo4.jpg" style="width:60px;height:60px;"/></a>
					</div>
					<div class="col-lg-2 col-md-2">
						<a href="data-ligue-1-5.html" class="link-img"><img src="assets/image/page/league-logo5.jpg" style="width:60px;height:60px;"/></a>
					</div>
				</div>
				<div class="row" style="margin-top:8px;">
					<div class="col-lg-2 col-md-2 team-title" style="font-size:14px;">
						<a href="data-premier-league-1.html" ><div class="${leagueId==1?'select-league':'common-league'}">英超</div></a>
					</div>
					<div class="col-lg-2 col-md-2 team-title" style="font-size:14px;">
						<a href="data-primera-division-2.html" ><div class="${leagueId==2?'select-league':'common-league'}">西甲</div></a>
					</div>
					<div class="col-lg-2 col-md-2 team-title" style="font-size:14px;">
						<a href="data-bundesliga-3.html" ><div class="${leagueId==3?'select-league':'common-league'}">德甲</div></a>
					</div>
					<div class="col-lg-2 col-md-2 team-title" style="font-size:14px;">
						<a href="data-sesie-a-4.html" ><div class="${leagueId==4?'select-league':'common-league'}">意甲</div></a>
					</div>
					<div class="col-lg-2 col-md-2 team-title" style="font-size:14px;">
						<a href="data-ligue-1-5.html" ><div class="${leagueId==5?'select-league':'common-league'}">法甲</div></a>
					</div>
				</div>
				
				<div class="row" style="margin-top:10px;">
					<div class="col-lg-9 col-md-9">
						<ul id="myTab" class="nav nav-tabs bread" >
							<li class="active"><a href="#match_rank" data-toggle="tab">赛程</a></li>
							<li ><a href="#team_rank" data-toggle="tab">球队排名</a></li>
							<li><a href="#player_rank" data-toggle="tab">球员排名</a></li>
						</ul>
						<div id="myTabContent" class="tab-content">
								<div class="tab-pane fade in active" id="match_rank" style="border:1px solid #ddd;border-top:none;padding-bottom:8px;">
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
		
										<div id="roundCtr" style="margin-bottom:5px;margin-left:1%;">
											<div class="schedule-round">
											    <div class="sr-ctr">
											        <div class="sr-ctr-in udv-clearfix" >
											        	<c:forEach items="${lstMatch}" var="match">
												            <div class="sr-box" >
												                <div class="up">
												                    <p class="date"><fmt:formatDate value="${match.match_date}" pattern="MM-dd"/> ${match.match_weekday} <fmt:formatDate value="${match.match_date}" pattern="HH:mm"/></p>
												                    <p class="team a-title"><a href="team-${match.home_team_en_name}-${match.home_team_id}.html" target="_blank" class="link-333333 ml35"><img src="${match.home_team_img}" height="20" width="20" alt="${match.home_team_name}">&nbsp;${match.home_team_name}</a></p>
								 				                    <c:if test="${match.status!='完场'}">
									 				                    <p class="time a-title" title="直播"><a href="match-${match.home_team_en_name}-vs-${match.away_team_en_name}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_blank" ><img src="assets/image/page/vs.png" style="width:20px;"/></a></p>
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
										<table class="table table-hover" style="border:1px solid #dddddd;border-top:none;">
										  <thead>
										  	<tr >
										  		<th colspan="10" style="border-right:1px solid #dddddd;border-bottom:2px solid #3CB371;"><center>积分榜</center></th>
										  	</tr>
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
										      <td class="team-title" ><a href="team-${team.team_name_en}-${team.team_id}.html" target="_blank"><img src="assets/image/soccer/teams/25x25/${team.team_id}.png" style="width:25px;height:25px;" alt="${team.team_name}" title="${team.team_name}"/>&nbsp;${team.team_name}</a></td>
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
													<table class="table table-striped table-hover " style="float:left;width:55%;border:1px solid #dddddd;border-top:none;border-right:none;">
													  <thead>
													  	<tr >
													  		<th colspan="5" style="border-bottom:2px solid #3CB371;"><center>射手榜</center></th>
													  	</tr>
													    <tr style="background:#3CB371;color:white;border:1px solid #3CB371;">
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
														      <td class="team-title" ><a href="player-${shooter.player_name_en}-${shooter.player_id}.html" target="_blank"><img src="${shooter.player_img}" style="width:25px;height:25px;" alt="${shooter.player_name}" title="${shooter.player_name}"/>&nbsp;${shooter.player_name}</a></td>
														      <td class="team-title" ><a href="team-${shooter.team_name_en}-${shooter.team_id}.html" target="_blank"><img src="assets/image/soccer/teams/25x25/${shooter.team_id}.png" style="width:25px;height:25px;" alt="${shooter.team_name}" title="${shooter.team_name}"/>&nbsp;${shooter.team_name}</a></td>
														      <td ><center>${shooter.goal_count}</center></td>
														      <td style="border-right:1px solid #dddddd;"><center>${shooter.penalty_count}</center></td>
														    </tr>
														</c:forEach>
													  </tbody>
													</table>
												
													<table class="table table-striped table-hover pull-right" style="float:left;width:40%;border:1px solid #dddddd;border-top:none;border-left:none;">
													  <thead>
													  	<tr>
													  		<th colspan="4" style="border-bottom:2px solid #3CB371;"><center>助攻榜</center></th>
													  	</tr>
													    <tr style="background:#3CB371;color:white;border-left:1px solid #3CB371;border-right:1px solid #3CB371;">
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
														      <td class="team-title" ><a href="player-${assists.player_name_en}-${assists.player_id}.html" target="_blank"><nobr><img src="${assists.player_img}" style="width:25px;height:25px;" alt="${assists.player_name}" title="${assists.player_name}" />&nbsp;${assists.player_name}</nobr></a></td>
														      <td class="team-title" ><a href="team-${assists.team_name_en}-${assists.team_id}.html" target="_blank"><nobr><img src="assets/image/soccer/teams/25x25/${assists.team_id}.png" style="width:25px;height:25px;" alt="${assists.team_name}" title="${assists.team_name}"/>&nbsp;${assists.team_name}</nobr></a></td>
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
		</div>
		
		<%@ include file="/common/footer.jsp"%>	
		</div>
		
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

