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
	<meta name="keywords" content="英超直播,CCTV5在线直播,比赛直播,免费足球直播,直播网站,比赛结果" />
	<meta name="description" content="趣点足球网为球迷们提供五大联赛最新的足球比赛直播，比赛直播链接，比赛结果。" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - 比赛直播|免费直播|直播网站</title>
	<style type="text/css">
		.label{font-size:14px;}
		.label-default{background:#ddd;color:#333;}
		.text_cut{text-overflow:ellipsis;white-space:nowrap;overflow:hidden;}
	</style>    
</head>

<body>
<div id="all_content">	
	<div class="container">
		
		<%@ include file="/common/menu.jsp"%>
		
	    <!-- 移动端内容开始 -->
	    <div class="row visible-sm visible-xs" style="margin-top:34px;padding-bottom: 130px;">
	    	<div class="col-xs-12 col-sm-12" style="padding:0;">
						<table class="table small-table" style="text-indent:0;">
						  	<tbody>
							<c:forEach items="${lstMatch}" var="match">
								<c:if test="${empty match.home_team_name}">
									<tr>
										<td colspan="3" style="padding:0;"><span style="display:block;background:#5cb85c;height:35px;line-height:35px;font-size:16px;color:white;">&nbsp;${match.match_date_week}</span></td>
									</tr>
								</c:if>
								<c:if test="${!empty match.home_team_name}">
									<tr >
									  <td style="width:60px;height:35px;line-height:35px;padding-left:5px;">
									  		<span class="label label-default"><fmt:formatDate value="${match.match_datetime}" pattern="HH:mm"/></span>
									  </td>
								      <td style="width:60px;height:35px;line-height:35px;"><span class="league_${match.league_id}"><div class="text_cut" style="width:60px;line-height:35px;">${match.league_name}</div></span></td>
								      <td style="height:35px;line-height:35px;" class="a-title">
								      	<c:if test="${empty match.league_id}">
								      		<a title="${match.game_id!='0'?'有情报':''}" href="/live-<fmt:formatDate value="${match.match_datetime}" pattern="yyyy-MM-dd"/>-${match.home_team_enname}-vs-${match.away_team_enname}-${match.id}.html" target="_self">
								      		<c:if test="${match.game_id!='0'}"><span class="label label-success">情报</span></c:if> ${match.home_team_name} VS ${match.away_team_name}
								      		</a>
								      	</c:if>
								      	<c:if test="${!empty match.league_id}">
								      		<b class="a-title" ><a title="${match.game_id!='0'?'有情报':''}" href="match-${match.home_team_enname}-vs-${match.away_team_enname}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_self">
								      			<c:if test="${match.game_id!='0'}"><span class="label label-success">情报</span></c:if> ${match.home_team_name} VS ${match.away_team_name}
								      		</a></b>
								      	</c:if>
								      	
								      </td>
								    </tr>
								    <tr>
								    	<td colspan="3" class="a-title" style="height:35px;line-height:35px;border-top:none;padding-left:5px;">
								      	  	<c:forEach items="${match.liveList}" var="live">
								      	  		<nobr><i class="fa fa-tv"></i> <a href="${live.live_url}" target="_blank" style="color:red;">${live.live_name}</a></nobr>
								      	   </c:forEach>
								      	   &nbsp;<nobr><span class="a-title"><img src="assets/pages/img/zq.gif" style="width:18px;"/> <a href="/bifen.html" target="_self" style="color:red;">比分直播</a></span></nobr>
								       </td>
								    </tr>
								</c:if>
						    </c:forEach>
						   </tbody>
						</table>
	    		</div>
	    		
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
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:8px;padding-bottom: 130px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-10 col-lg-offset-2 col-md-10 col-md-offset-2">		
			<div class="col-lg-10 col-md-10" style="padding-left:0px;padding-right:0px;">
					<table class="table table-condensed table-hover " >
						  <tbody>
							<c:forEach items="${lstMatch}" var="match">
								<c:if test="${empty match.home_team_name}">
									<tr>
										<td colspan="4" style="padding:0;"><span style="display:block;background:#5cb85c;height:35px;line-height:35px;font-size:16px;color:white;font-weight:bold;">&nbsp;${match.match_date_week}</span></td>
									</tr>
								</c:if>
								<c:if test="${!empty match.home_team_name}">
									<tr >
									  <td style="width:60px;height:35px;line-height:35px;"><span class="label label-default"><fmt:formatDate value="${match.match_datetime}" pattern="HH:mm"/></span></td>
								      <td style="width:70px;height:35px;line-height:35px;"><span class="league_${match.league_id}" title="${match.league_name}"><div class="text_cut" style="width:70px;line-height:35px;">${match.league_name}</div></span></td>
								      <td style="width:270px;height:35px;line-height:35px;" class="a-title">
								      	<c:if test="${empty match.league_id}">
								      		<a  title="${match.game_id!='0'?'有情报':''}"href="/live-<fmt:formatDate value="${match.match_datetime}" pattern="yyyy-MM-dd"/>-${match.home_team_enname}-vs-${match.away_team_enname}-${match.id}.html" target="_blank">
								      		<c:if test="${match.game_id!='0'}"><span class="label label-success">情报</span></c:if> ${match.home_team_name} VS ${match.away_team_name}
								      		</a>
								      	</c:if>
								      	<c:if test="${!empty match.league_id}">
								      		<b class="a-title" ><a title="${match.game_id!='0'?'有情报':''}" href="match-${match.home_team_enname}-vs-${match.away_team_enname}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_blank">
								      		<c:if test="${match.game_id!='0'}"><span class="label label-success">情报</span></c:if> ${match.home_team_name} VS ${match.away_team_name}
								      		</a></b>
								      	</c:if>
								      	
								      </td>
								      <td class="a-title" style="height:35px;line-height:35px;">
								      	  <c:forEach items="${match.liveList}" var="live">
								      	  		 <nobr><i class="fa fa-tv"></i> <a href="${live.live_url}" target="_blank" style="color:red;">${live.live_name}</a></nobr>
								      	  </c:forEach>
								      	  &nbsp;<nobr><img src="assets/pages/img/zq.gif" style="width:18px;"/> <a href="/bifen.html" target="_blank">比分直播</a></nobr>
								      </td>
								      
								    </tr>
								</c:if>
						    </c:forEach>
						   </tbody>
						</table>
			</div>
			<div class="col-lg-2 col-md-2">
				
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

