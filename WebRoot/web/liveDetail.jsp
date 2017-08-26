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
						<li class="menu_width"><a href="say.html">说说</a></li>
						<li class="menu_width menu_sel"><a href="live.html">直播</a></li>
						<li class="menu_width "><a href="match.html">比赛</a></li>
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
	       		<a href="/say.html" target="_self"><span class="wwqk_menu">说说</span></a>
	       	</div>
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/live.html" target="_self"><span class="wwqk_menu dline">直播</span></a>
	       	</div>
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/match.html" target="_self"><span class="wwqk_menu ">比赛</span></a>
	       	</div>
	       	<div class="col-xs-3 col-sm-3 wwqk_menu_wh" >
	       		<a href="/data.html" target="_self"><span class="wwqk_menu">数据</span></a>
	       	</div>
	    </div>
	    <!-- 移动端内容开始 -->
	    <div class="row visible-sm visible-xs" style="margin-top:45px;">
			<div class="col-sm-12 col-xs-12 bread">
				当前位置：<a href="/" target="_self">首页</a>&nbsp;&gt;&nbsp;<a href="/live" target="_self">直播</a>&nbsp;&gt;&nbsp;${match.home_team_name}vs${match.away_team_name}
			</div>
		</div>
		<div class="row visible-sm visible-xs" style="color:grey;">
			<div class="col-sm-12 col-xs-12" style="margin-top:10px;padding-left:10px;">
				【所属联赛】：${match.league_name}
			</div>
			<div class="col-sm-12 col-xs-12" style="margin-top:10px;padding-left:10px;">
				【对阵球队】：${match.home_team_name} vs ${match.away_team_name}
			</div>
			<div class="col-sm-12 col-xs-12" style="margin-top:10px;padding-left:10px;">
				【赛事时间】：<fmt:formatDate value="${match.match_datetime}" pattern="yyyy年MM月dd日  HH:mm"/>
			</div>
			
			<div class="col-sm-12 col-xs-12" style="margin-top:10px;padding-left:10px;">
					【直播地址】：
					<c:if test="${empty lstMatchLive}">
						暂无
					</c:if>
					<c:if test="${!empty lstMatchLive}">
						<span class="a-title" >
						<c:forEach items="${lstMatchLive}" var="live">
							<i class="fa fa-tv"></i> <a href="${live.live_url}" target="_blank" style="color:red;">${live.live_name}</a>&nbsp;&nbsp;	
						</c:forEach>
						</span>
					</c:if>
			</div>
			<div class="col-sm-12 col-xs-12" style="margin-top:10px;padding-left:10px;" title="<fmt:formatDate value="${match.match_datetime}" pattern="yyyy-MM-dd"/> ${match.home_team_name} vs ${match.away_team_name} 统计">
				【比分】：<span class="a-title"><a href="http://www.188bifen.com/" href="_blank">比分直播</a></span>
			</div>
			<c:if test="${!empty match.info and match.info!=''}">
				   <div class="col-sm-12 col-xs-12" style="margin-top:10px;padding-left:10px;" title="<fmt:formatDate value="${match.match_datetime}" pattern="yyyy-MM-dd"/> ${match.home_team_name} vs ${match.away_team_name} 情报">
						【情报】：${match.info}
					</div>
			</c:if>
			
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
								<div class="col-lg-12 col-md-12 ">
									【所属联赛】：${match.league_name}
								</div>
								<div class="col-lg-12 col-md-12 ">
									【对阵球队】：${match.home_team_name} vs ${match.away_team_name}
								</div>
								<div class="col-lg-12 col-md-12 ">
									【赛事时间】：<fmt:formatDate value="${match.match_datetime}" pattern="yyyy年MM月dd日  HH:mm"/> ${match.weekday}
								</div>
								
								<div class="col-lg-12 col-md-12 ">
									【直播地址】：
									<c:if test="${empty lstMatchLive}">
										暂无
									</c:if>
									<c:if test="${!empty lstMatchLive}">
										<span class="a-title" >
											<c:forEach items="${lstMatchLive}" var="live"><i class="fa fa-tv"></i> <a href="${live.live_url}" target="_blank" style="color:red;">${live.live_name}</a>&nbsp;&nbsp;	
										</c:forEach>
										</span>
									</c:if>
								</div>
								<div class="col-lg-12 col-md-12 "  title="<fmt:formatDate value="${match.match_datetime}" pattern="yyyy-MM-dd"/> ${match.home_team_name} vs ${match.away_team_name} 情报">
									【比分】：<span class="a-title"><a href="http://www.188bifen.com/" href="_blank">比分直播</a></span>
								</div>
								<c:if test="${!empty match.info and match.info!=''}">
									   <div class="col-lg-12 col-md-12 " style="" title="<fmt:formatDate value="${match.match_datetime}" pattern="yyyy-MM-dd"/> ${match.home_team_name} vs ${match.away_team_name} 情报">
											【情报】：${match.info}
										</div>
								</c:if>
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
		$('.image').viewer({toolbar:false, zIndex:20000});
		$('.venue').viewer({toolbar:false, zIndex:20000, url:"big"});
	});
	</script>
		
</body>	

