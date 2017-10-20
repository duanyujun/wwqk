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
	<meta name="description" content='<fmt:formatDate value="${match.match_datetime}" pattern="yyyy年MM月dd日"/>${match.home_team_name}vs${match.away_team_name}直播,${match.home_team_name}vs${match.away_team_name}免费直播,${match.away_team_name}vs${match.home_team_name}直播,${match.home_team_name}直播,${match.away_team_name}直播,更多${match.leagueName}视频直播尽在趣点足球网,<fmt:formatDate value="${match.match_datetime}" pattern="yyyy-MM-dd"/> ${match.home_team_name} vs ${match.away_team_name} 阵容' />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
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
		<div class="row menu_bg clear_row_margin hidden-sm hidden-xs" >
			<div id="main_nav" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">	
				<div>
					<div class="logo_div">
						<a style="margin-left:12px;">趣点足球网</a>
					</div>
					<ul style="float:left;" class="hidden-sm hidden-xs">
						<li class="menu_width"><a href="">首页</a></li>
						<li class="menu_width"><a href="fun.html">趣点</a></li>
						<li class="menu_width menu_sel"><a href="live.html">直播</a></li>
						<li class="menu_width "><a href="bifen.html">比分</a></li>
						<li class="menu_width"><a href="data.html">数据</a></li>
					</ul>
				</div>
			</div>
		</div>
		
		<div class="row menu_link navbar-fixed-top  visible-sm visible-xs" style="background:#00A50D;min-height:35px;color:white;">
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/" target="_self"><span class="wwqk_menu">首页</span></a>
	       	</div>
	       	<div class="col-xs-3 col-sm-3 wwqk_menu_wh" >
	       		<a href="/fun.html" target="_self"><span class="wwqk_menu">趣点</span></a>
	       	</div>
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/live.html" target="_self"><span class="wwqk_menu dline">直播</span></a>
	       	</div>
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/bifen.html" target="_self"><span class="wwqk_menu ">比分</span></a>
	       	</div>
	       	<div class="col-xs-3 col-sm-3 wwqk_menu_wh" >
	       		<a href="/data.html" target="_self"><span class="wwqk_menu">数据</span></a>
	       	</div>
	    </div>
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
											<nobr><img src="assets/pages/img/tv.png" style="margin-top:-5px;"/> <a href="${live.live_url}" target="_blank" style="color:red;">${live.live_name}</a>&nbsp;&nbsp;</nobr>
										</c:forEach>
										<nobr><img src="assets/pages/img/zq.gif" style="width:18px;"/> <a href="/bifen.html" target="_blank" style="color:red;">比分直播</a></nobr>
									</span>
							</c:if>
						</td>
					</tr>
					<c:if test="${!empty lstTips}">
						<tr>
							<td style="padding-top:10px;">
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
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:5px;padding-bottom: 130px;margin-bottom:20px;">
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
																<nobr><img src="assets/pages/img/tv.png" style="margin-top:-5px;"/> <a href="${live.live_url}" target="_blank" style="color:red;">${live.live_name}</a>&nbsp;&nbsp;</nobr>
															</c:forEach>
															<nobr><img src="assets/pages/img/zq.gif" style="width:18px;"/> <a href="/bifen.html" target="_blank" style="color:red;">比分直播</a></nobr>
														</span>
												</c:if>
											</td>
										</tr>
										<c:if test="${!empty lstTips}">
											<tr>
												<td>【赛事情报】：</td>
												<td>
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

