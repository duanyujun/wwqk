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
	<meta name="keywords" content="足球比赛,比赛时间,比赛结果,巴萨比赛,皇马比赛,曼联比赛,阿森纳比赛,曼城比赛,利物浦比赛,切尔西比赛,拜仁比赛,尤文图斯比赛,巴黎圣日耳曼比赛" />
	<meta name="description" content="趣点足球网为球迷们提供五大联赛最新的足球比赛预告，比赛结果。" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - 足球比赛|比赛时间|比赛结果|巴萨|皇马|曼联|阿森纳|曼城|利物浦|切尔西|拜仁|尤文图斯|巴黎圣日耳曼</title>
    
</head>

<body>
	
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
	    <div class="row visible-sm visible-xs" style="margin-top:25px;">
	    	<c:forEach items="${lstGroup}" var="group">
		    	<div class="table-responsive" style="margin-top:10px;border:none;">
						<table class="table table-condensed table-hover" >
						  <caption style="text-align:left;"><img title="${group[0].league_name}" src="assets/image/page/league-logo${group[0].league_id}.jpg" style="width:80px;height:80px;"/></caption>
						  <thead>
						    <tr>
						      <th>比赛时间（${group[0].league_name}）</th>
						      <th></th>
						    </tr>
						  </thead>
						  <tbody>
						  	<c:forEach items="${group}" var="match">
						    <tr>
						      <td class="team-title" style="font-size:13px;">
						      	<span style="display:block;min-width:106px;float:left;"><a href="team-${match.home_team_en_name}-${match.home_team_id}.html" target="_self"><img src="assets/image/soccer/teams/150x150/${match.home_team_id}.png" style="width:25px;height:25px;"/>&nbsp;${match.home_team_name}</a></span>
						      	<span style="display:block;min-width:40px;float:left;font-size:12px;">
						      	<fmt:formatDate value="${match.match_date}" pattern="yy/MM/dd"/><br>&nbsp;
						      	<c:if test="${fn:contains(match.result, '-')}">
						      		<b>${match.result}</b>
						      	</c:if>
						      	<c:if test="${!fn:contains(match.result, '-')}">
						      		${match.result}
						      	</c:if>
						      	</span>
						        <a href="team-${match.away_team_en_name}-${match.away_team_id}.html" style="margin-left:20px;" target="_self"><img src="assets/image/soccer/teams/150x150/${match.away_team_id}.png" style="width:25px;height:25px;"/>&nbsp;${match.away_team_name}</a>
						      </td>
						      <td>
						      		<c:if test="${match.status=='完场'}">
							      		<span class="grey-title" style="font-size:13px;"><a href="match-${match.home_team_en_name}-vs-${match.away_team_en_name}_<fmt:formatDate value="${match.match_date}" pattern="yyyy-MM-dd"/>-${match.home_team_id}vs${match.away_team_id}.html" target="_self">集锦</a></span>
							      	</c:if>
							      	<c:if test="${match.status!='完场'}">
							      		<b class="a-title" style="font-size:13px;"><a href="match-${match.home_team_en_name}-vs-${match.away_team_en_name}_<fmt:formatDate value="${match.match_date}" pattern="yyyy-MM-dd"/>-${match.home_team_id}vs${match.away_team_id}.html" target="_self">直播</a></b>
							      	</c:if>
						      </td>
						    </tr>
						    </c:forEach>
						    
						  </tbody>
						</table>
					</div>
	    		</c:forEach>
	    </div>
	    <!-- 移动端内容结束 -->
	</div>
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:70px;">
		<div id="main_content" style="min-height:10px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2">		
			<div class="bread">
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;比赛
			</div>
		</div>
	</div>
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:1px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-10 col-lg-offset-2 col-md-10 col-md-offset-2">		
			<div class="col-lg-9 col-md-9" style="padding-left:0px;padding-right:0px;">
				<c:forEach items="${lstGroup}" var="group">
					<div class="table-responsive" style="margin-top:10px;">
						<table class="table table-condensed table-hover" style="border-bottom:1px solid #dddddd;">
						  <caption style="text-align:center;"><img title="${group[0].league_name}" src="assets/image/page/league-logo${group[0].league_id}.jpg" style="width:80px;height:80px;"/></caption>
						  <thead>
						    <tr>
						      <th style="width:230px;">比赛时间（${group[0].league_name}）</th>
						      <th style="width:160px;"></th>
						      <th style="width:160px;"></th>
						      <th style="width:160px;"></th>
						      <th style="width:60px;" class="say-info"><a href="/history-${group[0].league_name_en}-${group[0].league_id}.html" target="_blank" title="更多${group[0].league_name}比赛">更多&gt;&gt;</a></th>	
						    </tr>
						  </thead>
						  <tbody>
						  	<c:forEach items="${group}" var="match">
						    <tr>
						      <td><fmt:formatDate value="${match.match_date}" pattern="yyyy-MM-dd"/>&nbsp;&nbsp;<b>${match.match_weekday}</b>&nbsp;&nbsp;&nbsp;<span style="color:#888;">第${match.round}轮</span></td>
						      <td class="a-title"><a href="team-${match.home_team_en_name}-${match.home_team_id}.html" target="_blank"><img src="assets/image/soccer/teams/25x25/${match.home_team_id}.png" style="width:25px;height:25px;" alt="${match.home_team_name}" title="${match.home_team_name}"/>&nbsp;${match.home_team_name}</a></td>
						      <td>
						      	<c:if test="${fn:contains(match.result, '-')}">
						      		<b>${match.result}</b>
						      	</c:if>
						      	<c:if test="${!fn:contains(match.result, '-')}">
						      		${match.result}
						      	</c:if>
						      </td>
						      <td class="a-title"><a href="team-${match.away_team_en_name}-${match.away_team_id}.html" target="_blank"><img src="assets/image/soccer/teams/25x25/${match.away_team_id}.png" style="width:25px;height:25px;" alt="${match.away_team_name}" title="${match.away_team_name}"/>&nbsp;${match.away_team_name}</a></td>
						      <td>
						      		<c:if test="${match.status=='完场'}">
							      		<span class="grey-title"><a href="match-${match.home_team_en_name}-vs-${match.away_team_en_name}_<fmt:formatDate value="${match.match_date}" pattern="yyyy-MM-dd"/>-${match.home_team_id}vs${match.away_team_id}.html" target="_blank">集锦</a></span>
							      	</c:if>
							      	<c:if test="${match.status!='完场'}">
							      		<b class="a-title"><a href="match-${match.home_team_en_name}-vs-${match.away_team_en_name}_<fmt:formatDate value="${match.match_date}" pattern="yyyy-MM-dd"/>-${match.home_team_id}vs${match.away_team_id}.html" target="_blank">直播</a></b>
							      	</c:if>
						      </td>
						    </tr>
						    </c:forEach>
						    
						  </tbody>
						</table>
					</div>
					
				</c:forEach>
				<div class="pull-right say-info" style="margin-top:10px;">
					<a href="/history.html" target="_blank" title="查看全部比赛">查看全部比赛&gt;&gt;</a>
				</div>
			</div>
			<div class="col-lg-3 col-md-3">
				
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

