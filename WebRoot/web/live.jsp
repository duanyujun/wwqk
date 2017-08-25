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
	<meta name="keywords" content="比赛直播,免费直播,直播网站,比赛结果" />
	<meta name="description" content="趣点足球网为球迷们提供五大联赛最新的足球比赛预告，比赛直播链接，比赛结果。" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - 比赛直播|免费直播|直播网站</title>
    
</head>

<body>
<div id="all_content">	
	<div class="container">
		<div class="row menu_bg clear_row_margin hidden-sm hidden-xs" >
			<div id="main_nav" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2">	
				<div>
					<div class="logo_div">
						<a href="" title="首页">趣点足球网</a>
					</div>
					<ul style="float:left;">
						<li class="menu_width"><a href="">首页</a></li>
						<li class="menu_width"><a href="fun.html">趣点</a></li>
						<li class="menu_width"><a href="say.html">说说</a></li>
						<li class="menu_width menu_sel"><a href="match.html">比赛</a></li>
						<li class="menu_width"><a href="data.html">数据</a></li>
					</ul>
				</div>
			</div>
		</div>
		
		<div class="row menu_link navbar-fixed-top  visible-sm visible-xs" style="background:#00A50D;min-height:35px;color:white;">
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/" target="_self"><span class="wwqk_menu">首页</span></a>
	       	</div>
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/fun.html" target="_self"><span class="wwqk_menu">趣点</span></a>
	       	</div>
	       	<div class="col-xs-3 col-sm-3 wwqk_menu_wh" >
	       		<a href="/say.html" target="_self"><span class="wwqk_menu">说说</span></a>
	       	</div>
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/match.html" target="_self"><span class="wwqk_menu dline">比赛</span></a>
	       	</div>
	       	<div class="col-xs-3 col-sm-3 wwqk_menu_wh" >
	       		<a href="/data.html" target="_self"><span class="wwqk_menu">数据</span></a>
	       	</div>
	    </div>
	    <!-- 移动端内容开始 -->
	    <div class="row visible-sm visible-xs" style="margin-top:30px;padding-bottom: 130px;">
	    	<c:forEach items="${lstGroup}" var="group">
	    		<div class="col-xs-12 col-sm-12"><img title="${group[0].league_name}" src="assets/image/page/league-logo${group[0].league_id}.jpg" style="width:80px;height:80px;"/></div>
	    		<div class="col-xs-12 col-sm-12"><b>比赛时间（${group[0].league_name}）</b></div>
						<table class="table small-table">
						  <tbody>
						  	<c:forEach items="${group}" var="match">
						    <tr style="font-size:12px;">
						      <td class="team-title">
						      		<span style="display:block;min-width:106px;float:left;"><a href="team-${match.home_team_en_name}-${match.home_team_id}.html" target="_self"><img src="assets/image/soccer/teams/150x150/${match.home_team_id}.png" style="width:25px;height:25px;"/>&nbsp;${match.home_team_name}</a></span>
						      </td>
						      <td class="team-title">
							      	<fmt:formatDate value="${match.match_date}" pattern="yy/MM/dd"/><br>&nbsp;
							      	<c:if test="${fn:contains(match.result, '-')}">
							      		<b>${match.result}</b>
							      	</c:if>
							      	<c:if test="${!fn:contains(match.result, '-')}">
							      		<b>${match.result}</b>
							      	</c:if>
						      </td>
						      <td class="team-title">
						      		<a href="team-${match.away_team_en_name}-${match.away_team_id}.html" style="margin-left:20px;" target="_self"><img src="assets/image/soccer/teams/150x150/${match.away_team_id}.png" style="width:25px;height:25px;"/>&nbsp;${match.away_team_name}</a>
						      </td>
						      
						      <td>
						      		<c:if test="${match.status=='完场'}">
							      		<span class="grey-title" style="font-size:12px;"><a href="match-${match.home_team_en_name}-vs-${match.away_team_en_name}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_self">集锦</a></span>
							      	</c:if>
							      	<c:if test="${match.status!='完场'}">
							      		<b class="a-title" style="font-size:12px;"><a href="match-${match.home_team_en_name}-vs-${match.away_team_en_name}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_self">直播</a></b>
							      	</c:if>
						      </td>
						    </tr>
						    </c:forEach>
						    
						  </tbody>
						</table>
	    		</c:forEach>
	    </div>
	    <!-- 移动端内容结束 -->
	</div>
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:70px;">
		<div id="main_content" style="min-height:10px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2">		
			<div class="bread">
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;直播
			</div>
		</div>
	</div>
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:1px;padding-bottom: 130px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-10 col-lg-offset-2 col-md-10 col-md-offset-2">		
			<div class="col-lg-9 col-md-9" style="padding-left:0px;padding-right:0px;">
					<table class="table table-condensed table-hover" >
						  <tbody>
							<c:forEach items="${lstMatch}" var="match">
								<c:if test="${empty match.home_team_name}">
									<tr>
										<td colspan="5">${match.match_date_week}</td>
									</tr>
								</c:if>
								<c:if test="${!empty match.home_team_name}">
									<tr>
								      <td><fmt:formatDate value="${match.match_datetime}" pattern="HH:mm"/></td>
								      <td>${match.league_name}</td>
								      <td class="a-title">${match.home_team_name}</td>
								      <td class="a-title">${match.away_team_name}</td>
								      <td>
								      		直播
								      </td>
								    </tr>
								</c:if>
						    </c:forEach>
						   </tbody>
						</table>
			</div>
			<div class="col-lg-3 col-md-3">
				
			</div>
		</div>
	</div>
	
	<%@ include file="/common/footer.jsp"%>		
	</div>
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

