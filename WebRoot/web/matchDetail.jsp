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
	<meta name="keywords" content="${homeTeam.name}vs${history.away_team_name}直播,${history.away_team_name}vs${homeTeam.name}免费直播,${history.away_team_name}vs${homeTeam.name}在线直播,${history.away_team_name}vs${homeTeam.name}视频直播,${homeTeam.name}直播,${history.away_team_name}直播,<fmt:formatDate value="${history.match_date}" pattern="yyyy-MM-dd"/> ${homeTeam.name} vs ${history.away_team_name} 阵容 " />
	<meta name="description" content='${history.description}趣点足球网还为您提供赛事分析，比赛结果，更有精彩${leagueName}录像集锦等着您！' />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    
    <style type="text/css">
    	.nav>li>a{padding:5px; 10px;}
    	.alert{padding-top:5px;padding-bottom:5px;margin-bottom:5px;}
    	.label{font-size:14px;padding:.1em .4em .1em;}
    </style>
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/viewer/viewer.min.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <title>${leagueName}${homeTeam.name}vs${history.away_team_name}直播|${homeTeam.name}vs${history.away_team_name}免费直播|${homeTeam.name} ${history.away_team_name} 直播|趣点足球网直播|<fmt:formatDate value="${history.match_date}" pattern="yyyy-MM-dd"/> ${homeTeam.name} vs ${history.away_team_name} 阵容|<fmt:formatDate value="${history.match_date}" pattern="yyyy-MM-dd"/> ${homeTeam.name} vs ${history.away_team_name} 情报</title>
</head>

<body>
<div id="all_content">	
	<div class="container">
		<%@ include file="/common/menu.jsp"%>
		
	    <!-- 移动端内容开始 -->
	    <div class="row visible-sm visible-xs" style="margin-top:45px;">
			<div class="col-sm-12 col-xs-12 bread" style="padding-left:10px;">
				当前位置：<a href="/" target="_self">首页</a>&nbsp;&gt;&nbsp;<a href="/live.html" target="_self">直播</a>&nbsp;&gt;&nbsp;${homeTeam.name}vs${history.away_team_name}
			</div>
		</div>
		<div class="row visible-sm visible-xs" style="font-size:13px;color:grey;">
			<div class="col-sm-12 col-xs-12" style="margin-top:5px;padding-left:10px;">
				<table style="text-indent:0;">
					<tr>
						<td style="font-size:14px;font-weight:bold;color:black;">
							<span class="a-title" ><a href="data-${leagueENName}-${history.league_id}.html" target="_blank"><u>${leagueName}</u></a> （第${history.match_round}轮）</span>
						</td>
					</tr>
					<tr>
						<td style="padding-top:5px;">
							<span class="a-title" ><a href="team-${homeTeam.name_en}-${homeTeam.id}.html" target="_blank"><u>${homeTeam.name}</u></a><span style="color:#888" title="排名第${homeTeam.rank}">[ ${homeTeam.rank} ]</span> vs <a href="team-${history.away_team_en_name}-${history.away_team_id}.html" target="_blank"><u>${history.away_team_name}</u></a><span style="color:#888" title="排名第${awayTeam.rank}">[ ${awayTeam.rank} ]</span></span>
							<c:if test="${history.status=='完场'}">
							（完场：<span style="color:red;">${history.result}</span>）
							</c:if>
						</td>
					</tr>
					<tr>
						<td style="padding-top:5px;"><fmt:formatDate value="${history.match_date}" pattern="yyyy年MM月dd日"/> ${history.match_weekday} <span class="label label-success"><img src="assets/pages/img/time.png" style="width:18px;margin-top:-5px;"/><fmt:formatDate value="${history.match_date}" pattern="HH:mm"/></span></td>
					</tr>
					<tr>
						<td style="padding-top:5px;">
							<c:if test="${history.status=='完场'}">
										<span class="a-title" >
											<c:if test="${!empty videosUrl}">
												<u><a href="${videosUrl}" target="_blank"><i class="fa fa-tv"></i> 集锦</a></u>
											</c:if>
											<c:if test="${empty videosUrl}">
												<c:if test="${history.league_id==1}">
													<u><a href="videos.html" target="_blank"><i class="fa fa-tv"></i> 集锦</a></u>
												</c:if>
												<c:if test="${history.league_id==2}">
													<u><a href="videos-league-2.html" target="_blank"><i class="fa fa-tv"></i> 集锦</a></u>
												</c:if>
												<c:if test="${history.league_id==3}">
													<u><a href="videos-league-3.html" target="_blank"><i class="fa fa-tv"></i> 集锦</a></u>
												</c:if>
												<c:if test="${history.league_id==4}">
													<u><a href="videos-league-4.html" target="_blank"><i class="fa fa-tv"></i> 集锦</a></u>
												</c:if>
												<c:if test="${history.league_id==5}">
													<u><a href="videos-league-5.html" target="_blank"><i class="fa fa-tv"></i> 集锦</a></u>
												</c:if>
											</c:if>
										</span>
							</c:if>
							<c:if test="${history.status!='完场'}">
									<c:if test="${empty lstMatchLive}">
										<span class="a-title" ><img src="assets/pages/img/zq.gif" style="width:18px;"/> <a href="/bifen.html" target="_blank" style="color:red;">比分直播</a></span>
									</c:if>
									<c:if test="${!empty lstMatchLive}">
											<span class="a-title" >
												<c:forEach items="${lstMatchLive}" var="live">
													<nobr><i class="fa fa-tv"></i> <a href="${live.live_url}" target="_blank" style="color:red;">${live.live_name}</a>&nbsp;&nbsp;</nobr>
												</c:forEach>
												<nobr><img src="assets/pages/img/zq.gif" style="width:18px;"/> <a href="/bifen.html" target="_blank" style="color:red;">比分直播</a></nobr>
											</span>
									</c:if>
							</c:if>
						</td>
					</tr>
					<c:if test="${!empty lstTips}">
						<tr>
							<td style="padding-top:5px;">
								<c:forEach items="${lstTips}" var="tips">
									<c:if test="${tips.is_good_bad=='0'}">
										<div class="alert alert-success" title="有利情报"><c:if test="${tips.is_home_away==0}"><span class="label label-danger">主</span></c:if><c:if test="${tips.is_home_away==1}"><span class="label label-primary">客</span></c:if> ${tips.news}</div>
									</c:if>
									<c:if test="${tips.is_good_bad=='1'}">
										<div class="alert alert-danger" title="不利情报"><c:if test="${tips.is_home_away==0}"><span class="label label-danger">主</span></c:if><c:if test="${tips.is_home_away==1}"><span class="label label-primary">客</span></c:if> ${tips.news}</div>
									</c:if>
									<c:if test="${tips.is_good_bad!='0' && tips.is_good_bad!='1'}">
										<div class="alert alert-warning" title="中立情报"><c:if test="${tips.is_home_away==0}"><span class="label label-danger">主</span></c:if><c:if test="${tips.is_home_away==1}"><span class="label label-primary">客</span></c:if> ${tips.news}</div>
									</c:if>
									
								</c:forEach>
							</td>
						</tr>
					</c:if>
					<tr>
						<td style="padding-top:5px;">
							[${homeTeam.venue_name} &nbsp;&nbsp;<span style="color:#888;">容量：${homeTeam.venue_capacity}人</span>]
							<img src="${homeTeam.venue_small_img_local}" big="${homeTeam.venue_img_local}" style="cursor:pointer;" class="img-responsive img-rounded venue"  alt="${homeTeam.name}球场名称：${homeTeam.venue_name}" title="${homeTeam.name}球场名称：${homeTeam.venue_name}"/>
						</td>
					</tr>
					
					<c:if test="${!empty history.team and history.team!=''}">
						<tr>
							<td style="padding-top:5px;">
								${history.team}
							</td>
						</tr>
					</c:if>
					<c:if test="${!empty history.info and history.info!=''}">
						<tr>
							<td style="padding-top:5px;">
								${history.info}
							</td>
						</tr>
					</c:if>
					
					<c:if test="${!empty history.opta_id}">
						<tr>
							<td class="a-title" style="padding-top:5px;">
								<a href="http://match.sports.sina.com.cn/football/result.php?id=${history.opta_id}" target="_blank"><u>新浪统计</u></a>
							</td>
						</tr>
					</c:if>
					<c:if test="${empty history.opta_id}">
						<c:if test="${!empty history.analysis}">
							<tr>
								<td style="padding-top:5px;">
									${history.analysis}
								</td>
							</tr>
						</c:if>
					</c:if>
					
				</table>
			</div>
		</div>
	    
	    <!-- odds start -->
		<div class="row visible-sm visible-xs" style="margin-top:10px;padding-bottom: 130px;">
			<div class="col-sm-12 col-xs-12" style="margin-top:10px;">
				<ul id="myTab" class="nav nav-tabs bread" >
					<li class="active"><a href="#odds_wh_mobile" data-toggle="tab">威廉希尔</a></li>
					<li><a href="#odds_bet365_mobile" data-toggle="tab">Bet365</a></li>
					<li><a href="#odds_bwin_mobile" data-toggle="tab">Bwin</a></li>
					<li><a href="#odds_ml_mobile" data-toggle="tab">澳门彩票</a></li>
					<li><a href="#odds_lb_mobile" data-toggle="tab">立博</a></li>
				</ul>
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane fade in active" id="odds_wh_mobile">
						<c:if test="${!empty lstOddsWH}">
							<div class="table-responsive" style="border-top:none;">
								<table class="table table-hover table-bordered table-striped" style="border-top:none;">
									<thead>
										<tr>
											<th colspan="8">初始赔率：${odds_wh_start}
												<c:if test="${!empty odds_wh_end}">
												<br>最终赔率：${odds_wh_end}
												</c:if>
												<br>
												${calcStr_14}
											</th>
										</tr>
										<tr>
											<th><center>联赛</center></th>
											<th><center>比赛时间</center></th>
											<th><center>初始赔率</center></th>
											<th><center>最终赔率</center></th>
											<th>主队</th>
											<th>比分</th>
											<th>客队</th>
											<th><center>彩果</center></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${lstOddsWH}" var="odds">
											<tr title="${odds.league_name} <fmt:formatDate value="${odds.match_date_time}" pattern="yyyy-MM-dd HH:mm:ss"/> ${odds.home_team_name} vs ${odds.away_team_name} ${odds.result}">
												<td><center>${odds.league_name}</center></td>
												<td><center><fmt:formatDate value="${odds.match_date_time}" pattern="yyyy-MM-dd HH:mm:ss"/></center></td>
												<td><center>${odds.odds_home_start}&nbsp;&nbsp;${odds.odds_draw_start}&nbsp;&nbsp;${odds.odds_away_start}</center></td>
												<td><center>${odds.odds_home_end}&nbsp;&nbsp;${odds.odds_draw_end}&nbsp;&nbsp;${odds.odds_away_end}</center></td>
												<td>${odds.home_team_name}</td>
												<td>${odds.result}</td>
												<td>${odds.away_team_name}</td>
												<td><center>${odds.common_result}</center></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>  
						</c:if>
						<c:if test="${empty lstOddsWH}">
							<span style="color:grey;">暂无记录</span>
						</c:if>
					</div>
					<div class="tab-pane fade" id="odds_bet365_mobile">
						<c:if test="${!empty lstOddsBet365}">
							<div class="table-responsive" style="border-top:none;">
								<table class="table table-hover table-bordered table-striped" style="border-top:none;">
									<thead>
										<tr>
											<th colspan="8">初始赔率：${odds_bet365_start}
												<c:if test="${!empty odds_bet365_end}">
												<br>最终赔率：${odds_bet365_end}
												</c:if>
												<br>
												${calcStr_27}
											</th>
										</tr>
										<tr>
											<th><center>联赛</center></th>
											<th><center>比赛时间</center></th>
											<th><center>初始赔率</center></th>
											<th><center>最终赔率</center></th>
											<th>主队</th>
											<th>比分</th>
											<th>客队</th>
											<th><center>彩果</center></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${lstOddsBet365}" var="odds">
											<tr>
												<td><center>${odds.league_name}</center></td>
												<td><center><fmt:formatDate value="${odds.match_date_time}" pattern="yyyy-MM-dd HH:mm:ss"/></center></td>
												<td><center>${odds.odds_home_start}&nbsp;&nbsp;${odds.odds_draw_start}&nbsp;&nbsp;${odds.odds_away_start}</center></td>
												<td><center>${odds.odds_home_end}&nbsp;&nbsp;${odds.odds_draw_end}&nbsp;&nbsp;${odds.odds_away_end}</center></td>
												<td>${odds.home_team_name}</td>
												<td>${odds.result}</td>
												<td>${odds.away_team_name}</td>
												<td><center>${odds.common_result}</center></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>  
						</c:if>
						<c:if test="${empty lstOddsBet365}">
							<span style="color:grey;">暂无记录</span>
						</c:if>
					</div>
					<div class="tab-pane fade" id="odds_bwin_mobile">
						<c:if test="${!empty lstOddsBwin}">
							<div class="table-responsive" style="border-top:none;">
								<table class="table table-hover table-bordered table-striped" style="border-top:none;">
									<thead>
										<tr>
											<th colspan="8">初始赔率：${odds_bwin_start}
												<c:if test="${!empty odds_bwin_end}">
												<br>最终赔率：${odds_bwin_end}
												</c:if>
												<br>
												${calcStr_94}
											</th>
										</tr>
										<tr>
											<th><center>联赛</center></th>
											<th><center>比赛时间</center></th>
											<th><center>初始赔率</center></th>
											<th><center>最终赔率</center></th>
											<th>主队</th>
											<th>比分</th>
											<th>客队</th>
											<th><center>彩果</center></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${lstOddsBwin}" var="odds">
											<tr>
												<td><center>${odds.league_name}</center></td>
												<td><center><fmt:formatDate value="${odds.match_date_time}" pattern="yyyy-MM-dd HH:mm:ss"/></center></td>
												<td><center>${odds.odds_home_start}&nbsp;&nbsp;${odds.odds_draw_start}&nbsp;&nbsp;${odds.odds_away_start}</center></td>
												<td><center>${odds.odds_home_end}&nbsp;&nbsp;${odds.odds_draw_end}&nbsp;&nbsp;${odds.odds_away_end}</center></td>
												<td>${odds.home_team_name}</td>
												<td>${odds.result}</td>
												<td>${odds.away_team_name}</td>
												<td><center>${odds.common_result}</center></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>  
						</c:if>
						<c:if test="${empty lstOddsBwin}">
							<span style="color:grey;">暂无记录</span>
						</c:if>
					</div>
					<div class="tab-pane fade" id="odds_ml_mobile">
						<c:if test="${!empty lstOddsML}">
							<div class="table-responsive" style="border-top:none;">
								<table class="table table-hover table-bordered table-striped" style="border-top:none;">
									<thead>
										<tr>
											<th colspan="8">初始赔率：${odds_ml_start}
												<c:if test="${!empty odds_ml_end}">
												<br>最终赔率：${odds_ml_end}
												</c:if>
												<br>
												${calcStr_84}
											</th>
										</tr>
										<tr>
											<th><center>联赛</center></th>
											<th><center>比赛时间</center></th>
											<th><center>初始赔率</center></th>
											<th><center>最终赔率</center></th>
											<th>主队</th>
											<th>比分</th>
											<th>客队</th>
											<th><center>彩果</center></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${lstOddsML}" var="odds">
											<tr>
												<td><center>${odds.league_name}</center></td>
												<td><center><fmt:formatDate value="${odds.match_date_time}" pattern="yyyy-MM-dd HH:mm:ss"/></center></td>
												<td><center>${odds.odds_home_start}&nbsp;&nbsp;${odds.odds_draw_start}&nbsp;&nbsp;${odds.odds_away_start}</center></td>
												<td><center>${odds.odds_home_end}&nbsp;&nbsp;${odds.odds_draw_end}&nbsp;&nbsp;${odds.odds_away_end}</center></td>
												<td>${odds.home_team_name}</td>
												<td>${odds.result}</td>
												<td>${odds.away_team_name}</td>
												<td><center>${odds.common_result}</center></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>  
						</c:if>
						<c:if test="${empty lstOddsML}">
							<span style="color:grey;">暂无记录</span>
						</c:if>
					</div>
					<div class="tab-pane fade" id="odds_lb_mobile">
						<c:if test="${!empty lstOddsLB}">
							<div class="table-responsive" style="border-top:none;">
								<table class="table table-hover table-bordered table-striped" style="border-top:none;">
									<thead>
										<tr>
											<th colspan="8">初始赔率：${odds_lb_start}
												<c:if test="${!empty odds_lb_end}">
												<br>最终赔率：${odds_lb_end}
												</c:if>
												<br>
												${calcStr_82}
											</th>
										</tr>
										<tr>
											<th><center>联赛</center></th>
											<th><center>比赛时间</center></th>
											<th><center>初始赔率</center></th>
											<th><center>最终赔率</center></th>
											<th>主队</th>
											<th>比分</th>
											<th>客队</th>
											<th><center>彩果</center></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${lstOddsLB}" var="odds">
											<tr>
												<td><center>${odds.league_name}</center></td>
												<td><center><fmt:formatDate value="${odds.match_date_time}" pattern="yyyy-MM-dd HH:mm:ss"/></center></td>
												<td><center>${odds.odds_home_start}&nbsp;&nbsp;${odds.odds_draw_start}&nbsp;&nbsp;${odds.odds_away_start}</center></td>
												<td><center>${odds.odds_home_end}&nbsp;&nbsp;${odds.odds_draw_end}&nbsp;&nbsp;${odds.odds_away_end}</center></td>
												<td>${odds.home_team_name}</td>
												<td>${odds.result}</td>
												<td>${odds.away_team_name}</td>
												<td><center>${odds.common_result}</center></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>  
						</c:if>
						<c:if test="${empty lstOddsLB}">
							<span style="color:grey;">暂无记录</span>
						</c:if>
					</div>
				</div>
			</div>
		</div>
		<!-- odds end -->
	    
	    
	    
		<!-- 移动端内容结束 -->
	</div>
	
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:70px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-9 col-md-9 col-sm-11 col-xs-11 bread">
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;<a href="/live.html" target="_blank">直播</a>&nbsp;&gt;&nbsp;<span style="font-size:18px;color:#000;">${leagueName} ${history.year_show}赛季 第${history.match_round}轮 ${homeTeam.name} VS ${history.away_team_name}</span>
			</div>
		</div>
	</div>
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:5px;padding-bottom: 130px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-12 col-md-12 ">
				<div class="row">
					<div class="col-lg-12 col-md-12 " style="margin-top:10px;font-size:14px;">
						<div class="well well-lg" style="line-height:2;text-indent:20px;background:#fdfdfd;">
							<div class="row">
								<div class="col-lg-12 col-md-12" >
									<table style="text-indent:0;">
										<tr>
											<td style="width:120px;">【所属赛事】：</td>
											<td><span class="a-title" ><a href="data-${leagueENName}-${history.league_id}.html" target="_blank"><u>${leagueName}</u></a> （第${history.match_round}轮）</span></td>
										</tr>
										<tr>
											<td>【对阵球队】：</td>
											<td>
												<span class="a-title" ><a href="team-${homeTeam.name_en}-${homeTeam.id}.html" target="_blank"><u>${homeTeam.name}</u></a><span style="color:#888" title="排名第${homeTeam.rank}">[ ${homeTeam.rank} ]</span> vs <a href="team-${history.away_team_en_name}-${history.away_team_id}.html" target="_blank"><u>${history.away_team_name}</u></a><span style="color:#888" title="排名第${awayTeam.rank}">[ ${awayTeam.rank} ]</span></span>
												<c:if test="${history.status=='完场'}">
												（完场：<span style="color:red;">${history.result}</span>）
												</c:if>
											</td>
										</tr>
										<tr>
											<td>【赛事时间】：</td>
											<td>
												<fmt:formatDate value="${history.match_date}" pattern="yyyy年MM月dd日"/> ${history.match_weekday} <fmt:formatDate value="${history.match_date}" pattern="HH:mm"/>
											</td>
										</tr>
										<tr>
											<td>${history.status=='完场'?'【观看集锦】：':'【直播地址】：'}</td>
											<td>
												<c:if test="${history.status=='完场'}">
															<span class="a-title" >
																<c:if test="${!empty videosUrl}">
																	<u><a href="${videosUrl}" target="_blank"><i class="fa fa-tv"></i> 集锦</a></u>
																</c:if>
																<c:if test="${empty videosUrl}">
																	<c:if test="${history.league_id==1}">
																		<u><a href="videos.html" target="_blank"><i class="fa fa-tv"></i> 集锦</a></u>
																	</c:if>
																	<c:if test="${history.league_id==2}">
																		<u><a href="videos-league-2.html" target="_blank"><i class="fa fa-tv"></i> 集锦</a></u>
																	</c:if>
																	<c:if test="${history.league_id==3}">
																		<u><a href="videos-league-3.html" target="_blank"><i class="fa fa-tv"></i> 集锦</a></u>
																	</c:if>
																	<c:if test="${history.league_id==4}">
																		<u><a href="videos-league-4.html" target="_blank"><i class="fa fa-tv"></i> 集锦</a></u>
																	</c:if>
																	<c:if test="${history.league_id==5}">
																		<u><a href="videos-league-5.html" target="_blank"><i class="fa fa-tv"></i> 集锦</a></u>
																	</c:if>
																</c:if>
															</span>
												</c:if>
												<c:if test="${history.status!='完场'}">
														<c:if test="${empty lstMatchLive}">
															<span class="a-title" ><img src="assets/pages/img/zq.gif" style="width:18px;"/> <a href="/bifen.html" target="_blank" style="color:red;">比分直播</a></span>
														</c:if>
														<c:if test="${!empty lstMatchLive}">
																<span class="a-title" >
																	<c:forEach items="${lstMatchLive}" var="live">
																		<nobr><i class="fa fa-tv"></i> <a href="${live.live_url}" target="_blank" style="color:red;">${live.live_name}</a>&nbsp;&nbsp;</nobr>
																	</c:forEach>
																	<nobr><img src="assets/pages/img/zq.gif" style="width:18px;"/> <a href="/bifen.html" target="_blank" style="color:red;">比分直播</a></nobr>
																</span>
														</c:if>
												</c:if>
												
												
											</td>
										</tr>
										<c:if test="${!empty lstTips}">
											<tr>
												<td>【赛事情报】：</td>
												<td>
													<ul id="infoTab" class="nav nav-tabs bread" >
														<li class="active"><a href="#home_tab" data-toggle="tab">主队</a></li>
														<li ><a href="#away_tab" data-toggle="tab">客队</a></li>
													</ul>
													<div id="infoTabContent" class="tab-content">
														<div class="tab-pane fade in active" id="home_tab" style="border:1px solid #ddd;border-top:none;padding:8px;">
																<c:forEach items="${lstTips}" var="tips">
																	<c:if test="${tips.is_home_away==0}">
																		<c:if test="${tips.is_good_bad=='0'}">
																			<div class="alert alert-success" title="有利情报"><span class="label label-danger">主</span> ${tips.news}</div>
																		</c:if>
																		<c:if test="${tips.is_good_bad=='1'}">
																			<div class="alert alert-danger" title="不利情报"><span class="label label-danger">主</span> ${tips.news}</div>
																		</c:if>
																		<c:if test="${tips.is_good_bad!='0' && tips.is_good_bad!='1'}">
																			<div class="alert alert-warning" title="中立情报"><span class="label label-danger">主</span> ${tips.news}</div>
																		</c:if>
																	</c:if>
																</c:forEach>
														</div>
														<div class="tab-pane fade in" id="away_tab" style="border:1px solid #ddd;border-top:none;padding:8px;">
																<c:forEach items="${lstTips}" var="tips">
																	<c:if test="${tips.is_home_away==1}">
																		<c:if test="${tips.is_good_bad=='0'}">
																			<div class="alert alert-success" title="有利情报"><span class="label label-primary">客</span> ${tips.news}</div>
																		</c:if>
																		<c:if test="${tips.is_good_bad=='1'}">
																			<div class="alert alert-danger" title="不利情报"><span class="label label-primary">客</span> ${tips.news}</div>
																		</c:if>
																		<c:if test="${tips.is_good_bad!='0' && tips.is_good_bad!='1'}">
																			<div class="alert alert-warning" title="中立情报"><span class="label label-primary">客</span> ${tips.news}</div>
																		</c:if>
																	</c:if>
																</c:forEach>
														</div>
													</div>
												</td>
											</tr>
										</c:if>
										
										<c:if test="${!empty lstGuess}">
											<tr>
												<td>【网友推荐】：</td>
												<td>
												
													<ul id="guessTab" class="nav nav-tabs bread" >
														<c:forEach items="${lstGuess}" var="guess" varStatus="status">
															<li class="${guessId==guess.id?'active':''}"><a href="#guess_tab_${status.index}" id="gtab_${guess.id}"  data-toggle="tab">${guess.tipster_name}</a></li>
														</c:forEach>
													</ul>
													<div id="guessTabContent" class="tab-content">
														<c:forEach items="${lstGuess}" var="guess" varStatus="status">
															<div class="tab-pane fade in ${guessId==guess.id?'active':''}" id="guess_tab_${status.index}" style="border:1px solid #ddd;border-top:none;padding:8px;">
																	<div class="alert alert-success" style="background-color:#f5f5f5;color:#333;">
																		<span class="label label-danger">荐</span> <b>${guess.bet_title_cn}</b>
																	</div>
																	<div class="alert alert-success" style="background-color:#f5f5f5;color:#333;">
																		${guess.content_cn}
																	</div>
															</div>
														</c:forEach>
													</div>
													
													
												</td>
											</tr>
										</c:if>
										
										<tr>
											<td>【主队球场】：</td>
											<td>
												[${homeTeam.venue_name} &nbsp;&nbsp;<span style="color:#888;">容量：${homeTeam.venue_capacity}人</span>]
												<img src="${homeTeam.venue_small_img_local}" big="${homeTeam.venue_img_local}" style="cursor:pointer;" class="img-responsive img-rounded venue"  alt="${homeTeam.name}球场名称：${homeTeam.venue_name}" title="${homeTeam.name}球场名称：${homeTeam.venue_name}"/>
											</td>
										</tr>
										
										<c:if test="${!empty history.team and history.team!=''}">
											<tr>
												<td>【球队阵容】：</td>
												<td>
													${history.team}
												</td>
											</tr>
										</c:if>
										<c:if test="${!empty history.info and history.info!=''}">
											<tr>
												<td>【赛事情报】：</td>
												<td>
													${history.info}
												</td>
											</tr>
										</c:if>
										
										<c:if test="${!empty history.opta_id}">
											<tr>
												<td>【赛后统计】：</td>
												<td class="a-title">
													<a href="http://match.sports.sina.com.cn/football/result.php?id=${history.opta_id}" target="_blank"><u>新浪统计</u></a>
												</td>
											</tr>
										</c:if>
										<c:if test="${empty history.opta_id}">
											<c:if test="${!empty history.analysis}">
												<tr>
													<td>【赛后统计】：</td>
													<td>
														${history.analysis}
													</td>
												</tr>
											</c:if>
										</c:if>
										
									</table>
								</div>
								
							</div>
							
						</div>
					</div>
				</div>
				
				<!-- odds start -->
					<div class="row" style="margin-top:10px;">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<ul id="myTab" class="nav nav-tabs bread" >
								<li class="active"><a href="#odds_wh" data-toggle="tab">威廉希尔</a></li>
								<li><a href="#odds_bet365" data-toggle="tab">Bet365</a></li>
								<li><a href="#odds_bwin" data-toggle="tab">Bwin</a></li>
								<li><a href="#odds_ml" data-toggle="tab">澳门彩票</a></li>
								<li><a href="#odds_lb" data-toggle="tab">立博</a></li>
							</ul>
							<div id="myTabContent" class="tab-content">
								<div class="tab-pane fade in active" id="odds_wh">
									<c:if test="${!empty lstOddsWH}">
										<div class="table-responsive">
											<table class="table table-hover table-bordered table-striped" style="border-top:none;">
												<thead>
													<tr>
														<th colspan="8">初始赔率：${odds_wh_start}
															<c:if test="${!empty odds_wh_end}">
															&nbsp;&nbsp;最终赔率：${odds_wh_end}
															</c:if>
															<br>
															${calcStr_14}
														</th>
													</tr>
													<tr>
														<th><center>联赛</center></th>
														<th><center>比赛时间</center></th>
														<th><center>初始赔率</center></th>
														<th><center>最终赔率</center></th>
														<th>主队</th>
														<th>比分</th>
														<th>客队</th>
														<th><center>彩果</center></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${lstOddsWH}" var="odds">
														<tr>
															<td><center>${odds.league_name}</center></td>
															<td><center><fmt:formatDate value="${odds.match_date_time}" pattern="yyyy-MM-dd HH:mm:ss"/></center></td>
															<td><center>${odds.odds_home_start}&nbsp;&nbsp;${odds.odds_draw_start}&nbsp;&nbsp;${odds.odds_away_start}</center></td>
															<td><center>${odds.odds_home_end}&nbsp;&nbsp;${odds.odds_draw_end}&nbsp;&nbsp;${odds.odds_away_end}</center></td>
															<td>${odds.home_team_name}</td>
															<td>${odds.result}</td>
															<td>${odds.away_team_name}</td>
															<td><center>${odds.common_result}</center></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>  
									</c:if>
									<c:if test="${empty lstOddsWH}">
										<span style="color:grey;">暂无记录</span>
									</c:if>
								</div>
								<div class="tab-pane fade" id="odds_bet365">
									<c:if test="${!empty lstOddsBet365}">
										<div class="table-responsive">
											<table class="table table-hover table-bordered table-striped" style="border-top:none;">
												<thead>
													<tr>
														<th colspan="8">初始赔率：${odds_bet365_start}
															<c:if test="${!empty odds_bet365_end}">
															&nbsp;&nbsp;最终赔率：${odds_bet365_end}
															</c:if>
															<br>
															${calcStr_27}
														</th>
													</tr>
													<tr>
														<th><center>联赛</center></th>
														<th><center>比赛时间</center></th>
														<th><center>初始赔率</center></th>
														<th><center>最终赔率</center></th>
														<th>主队</th>
														<th>比分</th>
														<th>客队</th>
														<th><center>彩果</center></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${lstOddsBet365}" var="odds">
														<tr>
															<td><center>${odds.league_name}</center></td>
															<td><center><fmt:formatDate value="${odds.match_date_time}" pattern="yyyy-MM-dd HH:mm:ss"/></center></td>
															<td><center>${odds.odds_home_start}&nbsp;&nbsp;${odds.odds_draw_start}&nbsp;&nbsp;${odds.odds_away_start}</center></td>
															<td><center>${odds.odds_home_end}&nbsp;&nbsp;${odds.odds_draw_end}&nbsp;&nbsp;${odds.odds_away_end}</center></td>
															<td>${odds.home_team_name}</td>
															<td>${odds.result}</td>
															<td>${odds.away_team_name}</td>
															<td><center>${odds.common_result}</center></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>  
									</c:if>
									<c:if test="${empty lstOddsBet365}">
										<span style="color:grey;">暂无记录</span>
									</c:if>
								</div>
								<div class="tab-pane fade" id="odds_bwin">
									<c:if test="${!empty lstOddsBwin}">
										<div class="table-responsive">
											<table class="table table-hover table-bordered table-striped" style="border-top:none;">
												<thead>
													<tr>
														<th colspan="8">初始赔率：${odds_bwin_start}
															<c:if test="${!empty odds_bwin_end}">
															&nbsp;&nbsp;最终赔率：${odds_bwin_end}
															</c:if>
															<br>
															${calcStr_94}
														</th>
													</tr>
													<tr>
														<th><center>联赛</center></th>
														<th><center>比赛时间</center></th>
														<th><center>初始赔率</center></th>
														<th><center>最终赔率</center></th>
														<th>主队</th>
														<th>比分</th>
														<th>客队</th>
														<th><center>彩果</center></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${lstOddsBwin}" var="odds">
														<tr>
															<td><center>${odds.league_name}</center></td>
															<td><center><fmt:formatDate value="${odds.match_date_time}" pattern="yyyy-MM-dd HH:mm:ss"/></center></td>
															<td><center>${odds.odds_home_start}&nbsp;&nbsp;${odds.odds_draw_start}&nbsp;&nbsp;${odds.odds_away_start}</center></td>
															<td><center>${odds.odds_home_end}&nbsp;&nbsp;${odds.odds_draw_end}&nbsp;&nbsp;${odds.odds_away_end}</center></td>
															<td>${odds.home_team_name}</td>
															<td>${odds.result}</td>
															<td>${odds.away_team_name}</td>
															<td><center>${odds.common_result}</center></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>  
									</c:if>
									<c:if test="${empty lstOddsBwin}">
										<span style="color:grey;">暂无记录</span>
									</c:if>
								</div>
								<div class="tab-pane fade" id="odds_ml">
									<c:if test="${!empty lstOddsML}">
										<div class="table-responsive">
											<table class="table table-hover table-bordered table-striped" style="border-top:none;">
												<thead>
													<tr>
														<th colspan="8">初始赔率：${odds_ml_start}
															<c:if test="${!empty odds_ml_end}">
															&nbsp;&nbsp;最终赔率：${odds_ml_end}
															</c:if>
															<br>
															${calcStr_84}
														</th>
													</tr>
													<tr>
														<th><center>联赛</center></th>
														<th><center>比赛时间</center></th>
														<th><center>初始赔率</center></th>
														<th><center>最终赔率</center></th>
														<th>主队</th>
														<th>比分</th>
														<th>客队</th>
														<th><center>彩果</center></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${lstOddsML}" var="odds">
														<tr>
															<td><center>${odds.league_name}</center></td>
															<td><center><fmt:formatDate value="${odds.match_date_time}" pattern="yyyy-MM-dd HH:mm:ss"/></center></td>
															<td><center>${odds.odds_home_start}&nbsp;&nbsp;${odds.odds_draw_start}&nbsp;&nbsp;${odds.odds_away_start}</center></td>
															<td><center>${odds.odds_home_end}&nbsp;&nbsp;${odds.odds_draw_end}&nbsp;&nbsp;${odds.odds_away_end}</center></td>
															<td>${odds.home_team_name}</td>
															<td>${odds.result}</td>
															<td>${odds.away_team_name}</td>
															<td><center>${odds.common_result}</center></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>  
									</c:if>
									<c:if test="${empty lstOddsML}">
										<span style="color:grey;">暂无记录</span>
									</c:if>
								</div>
								<div class="tab-pane fade" id="odds_lb">
									<c:if test="${!empty lstOddsLB}">
										<div class="table-responsive">
											<table class="table table-hover table-bordered table-striped" style="border-top:none;">
												<thead>
													<tr>
														<th colspan="8">初始赔率：${odds_lb_start}
															<c:if test="${!empty odds_lb_end}">
															&nbsp;&nbsp;最终赔率：${odds_lb_end}
															</c:if>
															<br>
															${calcStr_82}
														</th>
													</tr>
													<tr>
														<th><center>联赛</center></th>
														<th><center>比赛时间</center></th>
														<th><center>初始赔率</center></th>
														<th><center>最终赔率</center></th>
														<th>主队</th>
														<th>比分</th>
														<th>客队</th>
														<th><center>彩果</center></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${lstOddsLB}" var="odds">
														<tr>
															<td><center>${odds.league_name}</center></td>
															<td><center><fmt:formatDate value="${odds.match_date_time}" pattern="yyyy-MM-dd HH:mm:ss"/></center></td>
															<td><center>${odds.odds_home_start}&nbsp;&nbsp;${odds.odds_draw_start}&nbsp;&nbsp;${odds.odds_away_start}</center></td>
															<td><center>${odds.odds_home_end}&nbsp;&nbsp;${odds.odds_draw_end}&nbsp;&nbsp;${odds.odds_away_end}</center></td>
															<td>${odds.home_team_name}</td>
															<td>${odds.result}</td>
															<td>${odds.away_team_name}</td>
															<td><center>${odds.common_result}</center></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>  
									</c:if>
									<c:if test="${empty lstOddsLB}">
										<span style="color:grey;">暂无记录</span>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				<!-- odds end -->
				
			</div>
		</div>
	</div>
	
	<%@ include file="/common/footer.jsp"%>		
</div>
	<script src="assets/global/plugins/viewer/viewer-jquery.min.js" type="text/javascript"></script>
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
	
	$(function(){
		var guessId = '${guessId}';
		if(guessId!=''){
			$('#myTab a[id="#gtab_'+guessId+'"]').tab('show');
		}
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

