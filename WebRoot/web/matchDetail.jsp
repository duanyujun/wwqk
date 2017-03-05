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
	<meta name="keywords" content="${homeTeam.name}vs${history.away_team_name}直播,${history.away_team_name}vs${homeTeam.name}免费直播,${history.away_team_name}vs${homeTeam.name}在线直播,${history.away_team_name}vs${homeTeam.name}视频直播,${homeTeam.name}直播,${history.away_team_name}直播" />
	<meta name="description" content='<fmt:formatDate value="${history.match_date}" pattern="yyyy年MM月dd日"/>${homeTeam.name}vs${history.away_team_name}直播,${homeTeam.name}vs${history.away_team_name}免费直播,${history.away_team_name}vs${homeTeam.name}直播,${homeTeam.name}直播,${history.away_team_name}直播,更多${leagueName}视频直播尽在趣点足球网' />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <script src="assets/global/plugins/jquery.min.js" type="text/javascript"></script>
    <script src="common/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <title>${leagueName}${homeTeam.name}vs${history.away_team_name}直播|${homeTeam.name}vs${history.away_team_name}免费直播|${homeTeam.name}vs${history.away_team_name}直播信号|趣点足球网直播</title>
</head>

<body>
	<div class="row menu_bg clear_row_margin" >
		<div id="main_nav" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">	
			<div>
				<div class="logo_div">
					<a style="margin-left:12px;">趣点足球网</a>
				</div>
				<ul style="float:left;" class="hidden-sm hidden-xs">
					<li class="menu_width"><a href="">首页</a></li>
					<li class="menu_width"><a href="fun.html">趣点</a></li>
					<li class="menu_width"><a href="say.html">说说</a></li>
					<li class="menu_width menu_sel"><a href="match.html">比赛</a></li>
					<li class="menu_width"><a href="data.html">数据</a></li>
				</ul>
				<div class="visible-sm visible-xs small-menu">
					<select id="menuSelect" class="form-control small-select">
						<option value="">首页</option>
						<option value="fun.html">趣点</option>
						<option value="say.html">说说</option>
						<option selected value="match.html">比赛</option>
						<option value="data.html">数据</option>
					</select>	
				</div>
			</div>
		</div>
		
	</div>
	
	<div class="row clear_row_margin" style="margin-top:70px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-9 col-md-9 col-sm-11 col-xs-11 bread">
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;<a href="/match" target="_blank">比赛</a>&nbsp;&gt;&nbsp;${homeTeam.name}vs${history.away_team_name}
			</div>
		</div>
	</div>
	
	<div class="row clear_row_margin" style="margin-top:5px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-12 col-md-12 col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="row">
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="margin-top:10px;font-size:16px;">
						<div class="well well-lg" style="line-height:2;text-indent:20px;">
							<div class="row">
								<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
									【所属联赛】：<span class="a-title" style="font-size:16px;"><a href="data-${leagueENName}-${history.league_id}.html" target="_blank">${leagueName}</a> （第${history.round}轮）</span>
								</div>
								<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
									【对阵球队】：<span class="a-title" style="font-size:16px;"><a href="team-${homeTeam.name_en}-${homeTeam.id}.html" target="_blank">${homeTeam.name}</a>vs<a href="team-${history.away_team_en_name}-${history.away_team_id}.html" target="_blank">${history.away_team_name}</a></span>
									<c:if test="${history.status=='完场'}">
									（完场：<span style="color:red;">${history.result}</span>）
									</c:if>
								</div>
								<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
									【赛事时间】：<fmt:formatDate value="${history.match_date}" pattern="yyyy年MM月dd日  HH:mm"/>
								</div>
								<c:if test="${history.status=='完场'}">
									<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
										【观看集锦】：
											<span class="a-title" style="font-size:16px;">
												<c:if test="${history.league_id==1}">
													<a href="http://www.52waha.com/mixtape/soccer/yingchao" target="_blank">集锦</a>
												</c:if>
												<c:if test="${history.league_id==2}">
													<a href="http://www.52waha.com/mixtape/soccer/xijia" target="_blank">集锦</a>
												</c:if>
												<c:if test="${history.league_id==3}">
													<a href="http://www.52waha.com/mixtape/soccer/dejia" target="_blank">集锦</a>
												</c:if>
												<c:if test="${history.league_id==4}">
													<a href="http://www.52waha.com/mixtape/soccer/yijia" target="_blank">集锦</a>
												</c:if>
												<c:if test="${history.league_id==5}">
													<a href="http://www.52waha.com/mixtape/soccer/fajia" target="_blank">集锦</a>
												</c:if>
											</span>
									</div>
								</c:if>
								<c:if test="${history.status!='完场'}">
									<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
										【直播地址】：
										<c:if test="${empty lstMatchLive}">
											暂无
										</c:if>
										<c:if test="${!empty lstMatchLive}">
											<span class="a-title" style="font-size:16px;">
											<c:forEach items="${lstMatchLive}" var="live">
												<a href="${live.live_url}" target="_blank">${live.live_name}</a>&nbsp;&nbsp;	
											</c:forEach>
											</span>
										</c:if>
									</div>
								</c:if>
								
								<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
									【主队球场】：${homeTeam.venue_name} &nbsp;&nbsp;<span style="color:#888;">容量：${homeTeam.venue_capacity}人</span>
									<img src="${homeTeam.venue_small_img_local}" style="margin-left:20px;" class="img-responsive img-rounded"  alt="${homeTeam.name}球场名称：${homeTeam.venue_name}" title="${homeTeam.name}球场名称：${homeTeam.venue_name}"/>
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
										</div>
									</div>
								</div>
							</div>
							<!-- odds end -->
				
			</div>
		</div>
	</div>
	
	<%@ include file="/common/footer.jsp"%>		
	
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

