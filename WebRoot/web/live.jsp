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
	       		<a href="/bifen.html" target="_self"><span class="wwqk_menu">比分</span></a>
	       	</div>
	       	<div class="col-xs-3 col-sm-3 wwqk_menu_wh" >
	       		<a href="/data.html" target="_self"><span class="wwqk_menu">数据</span></a>
	       	</div>
	    </div>
	    <!-- 移动端内容开始 -->
	    <div class="row visible-sm visible-xs" style="margin-top:34px;padding-bottom: 130px;">
	    	<div class="col-xs-12 col-sm-12" style="padding:0;">
						<table class="table small-table">
						  	<tbody>
							<c:forEach items="${lstMatch}" var="match">
								<c:if test="${empty match.home_team_name}">
									<tr>
										<td colspan="2" style="padding:0;"><span style="display:block;background:#CD2626;height:35px;line-height:35px;font-size:16px;color:white;">&nbsp;${match.match_date_week}</span></td>
									</tr>
								</c:if>
								<c:if test="${!empty match.home_team_name}">
									<tr style="background:#ffe4e4;">
								      <td style="width:130px;height:35px;line-height:35px;"><fmt:formatDate value="${match.match_datetime}" pattern="HH:mm"/>&nbsp;&nbsp;<span class="league_${match.league_id}">${match.league_name}</span></td>
								      <td style="width:210px;height:35px;line-height:35px;" class="a-title">
								      	<c:if test="${empty match.league_id}">
								      		<a title="${!empty match.info?'有情报':''}" href="/live-<fmt:formatDate value="${match.match_datetime}" pattern="yyyy-MM-dd"/>-${match.home_team_enname}-vs-${match.away_team_enname}-${match.id}.html" target="_self">${match.home_team_name} VS ${match.away_team_name}
								      		<c:if test="${!empty match.info}">
								      		 	<img src="assets/image/page/spy.png" style="width:13px;margin-top:-3px;"  title="有情报"/>
								      		 </c:if>
								      		</a>
								      	</c:if>
								      	<c:if test="${!empty match.league_id}">
								      		<b class="a-title" ><a title="${!empty match.info?'有情报':''}" href="match-${match.home_team_enname}-vs-${match.away_team_enname}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_self">${match.home_team_name} VS ${match.away_team_name}
								      		<c:if test="${!empty match.info}">
								      		 	<img src="assets/image/page/spy.png" style="width:13px;margin-top:-3px;"  title="有情报"/>
								      		 </c:if>
								      		</a></b>
								      	</c:if>
								      	
								      </td>
								    </tr>
								    <tr>
								    	<td colspan="2" class="a-title" style="height:35px;line-height:35px;">
								      	  	<c:forEach items="${match.liveList}" var="live">
								      	  		 &nbsp;<i class="fa fa-tv"></i> <a href="${live.live_url}" target="_blank" style="color:red;">${live.live_name}</a>
								      	   </c:forEach>
								      	   &nbsp;<nobr><span class="a-title"><a href="http://www.shoumi.org/" target="_blank">比分直播</a></span></nobr>
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
					<table class="table table-condensed table-hover table-striped" >
						  <tbody>
							<c:forEach items="${lstMatch}" var="match">
								<c:if test="${empty match.home_team_name}">
									<tr>
										<td colspan="3" style="padding:0;"><span style="display:block;background:#00A50D;height:35px;line-height:35px;font-size:16px;color:white;font-weight:bold;">&nbsp;${match.match_date_week}</span></td>
									</tr>
								</c:if>
								<c:if test="${!empty match.home_team_name}">
									<tr >
								      <td style="width:160px;height:35px;line-height:35px;"><b><fmt:formatDate value="${match.match_datetime}" pattern="HH:mm"/></b>&nbsp;&nbsp;<span class="league_${match.league_id}" title="${match.league_name}">${match.league_name}</span></td>
								      <td style="width:210px;height:35px;line-height:35px;" class="a-title">
								      	<c:if test="${empty match.league_id}">
								      		<a  title="${!empty match.info?'有情报':''}"href="/live-<fmt:formatDate value="${match.match_datetime}" pattern="yyyy-MM-dd"/>-${match.home_team_enname}-vs-${match.away_team_enname}-${match.id}.html" target="_blank">${match.home_team_name} VS ${match.away_team_name}
								      		<c:if test="${!empty match.info}">
								      		 	<img src="assets/image/page/spy.png" style="width:13px;margin-top:-2px;"  title="有情报"/>
								      		 </c:if>
								      		</a>
								      	</c:if>
								      	<c:if test="${!empty match.league_id}">
								      		<b class="a-title" ><a title="${!empty match.info?'有情报':''}" href="match-${match.home_team_enname}-vs-${match.away_team_enname}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_blank">${match.home_team_name} VS ${match.away_team_name}
								      		<c:if test="${!empty match.info}">
								      		 	<img src="assets/image/page/spy.png" style="width:13px;margin-top:-2px;"  title="有情报"/>
								      		 </c:if>
								      		</a></b>
								      	</c:if>
								      	
								      </td>
								      <td class="a-title" style="height:35px;line-height:35px;">
								      	  <c:forEach items="${match.liveList}" var="live">
								      	  		 &nbsp;<nobr><i class="fa fa-tv"></i> <a href="${live.live_url}" target="_blank" style="color:red;">${live.live_name}</a></nobr>
								      	  </c:forEach>
								      	  &nbsp;<nobr><a href="http://www.shoumi.org/" target="_blank">比分直播</a></nobr>
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

