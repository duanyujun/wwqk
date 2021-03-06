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
	<meta name="keywords" content="${match.home_team_name}vs${match.away_team_name}直播,${match.away_team_name}vs${match.home_team_name}免费直播,${match.away_team_name}vs${match.home_team_name}在线直播,${match.away_team_name}vs${match.home_team_name}视频直播,${match.home_team_name} ${match.away_team_name} 直播,<fmt:formatDate value="${match.match_datetime}" pattern="yyyy-MM-dd"/> ${match.home_team_name} vs ${match.away_team_name} 阵容 " />
	<meta name="description" content='<fmt:formatDate value="${match.match_datetime}" pattern="北京时间 - yyyy年MM月dd日 H点m分"/>，${match.league_name} ${match.home_team_name}vs${match.away_team_name}直播,趣点足球网还为您提供赛事分析，比赛结果，更有精彩视频录像集锦等着您！' />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="https://cdn.bootcss.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <script src="assets/global/plugins/jquery.min.js" type="text/javascript"></script>
    <script src="common/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <style type="text/css">
    	.nav>li>a{padding:5px; 10px;}
    	.alert{padding-top:5px;padding-bottom:5px;margin-bottom:5px;}
    	.label{font-size:14px;padding:.1em .4em .1em;}
	
    </style>
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/viewer/viewer.min.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <title>${match.leagueName}${match.home_team_name}vs${match.away_team_name}直播|${match.home_team_name}vs${match.away_team_name}免费直播|${match.home_team_name} ${match.away_team_name} 直播|趣点足球网直播|<fmt:formatDate value="${match.match_datetime}" pattern="yyyy-MM-dd"/> ${match.home_team_name} vs ${match.away_team_name} 阵容|<fmt:formatDate value="${match.match_datetime}" pattern="yyyy-MM-dd"/> ${match.home_team_name} vs ${match.away_team_name} 情报</title>
</head>

<body>
<div id="all_content">	
	<div class="container">
		<%@ include file="/common/menu.jsp"%>
		
	    <!-- 移动端内容开始 -->
	    <div class="row visible-sm visible-xs" style="margin-top:45px;">
			<div class="col-sm-12 col-xs-12 bread" style="padding-left:10px;">
				当前位置：<a href="/" target="_self">首页</a>&nbsp;&gt;&nbsp;<a href="/live" target="_self">直播</a>&nbsp;&gt;&nbsp;${match.home_team_name}vs${match.away_team_name}
			</div>
		</div>
		<div class="row visible-sm visible-xs" style="color:grey;padding-bottom: 130px;">
			<div class="col-sm-12 col-xs-12" style="margin-top:5px;padding-left:10px;">
				<table style="text-indent:0;">
					<tr>
						<td style="font-size:14px;font-weight:bold;color:black;">${match.league_name} / ${match.home_team_name} vs ${match.away_team_name}</td>
					</tr>
					<tr>
						<td style="padding-top:5px;"><fmt:formatDate value="${match.match_datetime}" pattern="yyyy年MM月dd日"/> ${match.weekday} <span class="label label-success"><img src="assets/pages/img/time.png" style="width:18px;margin-top:-5px;"/><fmt:formatDate value="${match.match_datetime}" pattern="HH:mm"/></span></td>
					</tr>
					<tr>
						<td style="padding-top:5px;">
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
						</td>
					</tr>
					<c:if test="${!empty lstTips}">
						<tr>
							<td style="padding-top:10px;">
								<ul id="minfoTab" class="nav nav-tabs bread" >
									<li class="active"><a href="#m_home_tab" data-toggle="tab">主队分析</a></li>
									<li ><a href="#m_away_tab" data-toggle="tab">客队分析</a></li>
								</ul>
								<div id="minfoTabContent" class="tab-content">
									<div class="tab-pane fade in active" id="m_home_tab" style="border:1px solid #ddd;border-top:none;padding:8px;">
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
									<div class="tab-pane fade in" id="m_away_tab" style="border:1px solid #ddd;border-top:none;padding:8px;">
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
							<td style="padding-top:10px;">
								<ul id="mguessTab" class="nav nav-tabs bread" >
									<c:forEach items="${lstGuess}" var="guess" varStatus="status">
										<li class="${guessId==guess.id?'active':''}"><a href="#mguess_tab_${status.index}" id="mgtab_${guess.id}" data-toggle="tab">${guess.tipster_name}</a></li>
									</c:forEach>
								</ul>
								<div id="mguessTabContent" class="tab-content">
									<c:forEach items="${lstGuess}" var="guess" varStatus="status">
										<div class="tab-pane fade in ${guessId==guess.id?'active':''}" id="mguess_tab_${status.index}" style="border:1px solid #ddd;border-top:none;padding:8px;">
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
					
					
				</table>
			</div>
			
		</div>
	    
		<!-- 移动端内容结束 -->
	</div>
	
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:70px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-9 col-md-9 col-sm-11 col-xs-11 bread">
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;<a href="/live" target="_blank">直播</a>&nbsp;&gt;&nbsp;<span style="font-size:18px;color:#000;">${match.leagueName}  ${match.home_team_name} VS ${match.away_team_name}</span>
			</div>
		</div>
	</div>
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:5px;padding-bottom: 130px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-12 col-md-12">
				<div class="row">
					<div class="col-lg-12 col-md-12 " style="margin-top:10px;font-size:14px;">
						<div class="well well-lg" style="line-height:2;text-indent:20px;">
							<div class="row">
								<div class="col-lg-12 col-md-12" >
									<table style="text-indent:0;">
										<tr>
											<td style="width:120px;">【所属赛事】：</td>
											<td>${match.league_name}</td>
										</tr>
										<tr>
											<td>【对阵球队】：</td>
											<td>${match.home_team_name} vs ${match.away_team_name}</td>
										</tr>
										<tr>
											<td>【赛事时间】：</td>
											<td><fmt:formatDate value="${match.match_datetime}" pattern="yyyy年MM月dd日"/> ${match.weekday} <fmt:formatDate value="${match.match_datetime}" pattern="HH:mm"/></td>
										</tr>
										<tr>
											<td>【直播地址】：</td>
											<td>
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
											</td>
										</tr>
										<c:if test="${!empty lstTips}">
											<tr>
												<td>【赛事分析】：</td>
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
															<li class="${guessId==guess.id?'active':''}"><a href="#guess_tab_${status.index}" id="gtab_${guess.id}" data-toggle="tab" title="网友${guess.tipster_name}推荐">${guess.tipster_name}</a></li>
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
										
										
									</table>
								</div>
						 </div>
							
						</div>
					</div>
				</div>
				
				
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
			$('#guessTab a[id="#gtab_'+guessId+'"]').tab('show');
			$('#mguessTab a[id="#mgtab_'+guessId+'"]').tab('show');
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

