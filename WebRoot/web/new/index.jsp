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
	<meta name="keywords" content="趣点足球网,在线直播,球员动态,视频集锦,球队排名" />
	<meta name="description" content="趣点足球网为球迷们提供最新的球员资讯，直播链接以及足球数据" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="https://cdn.bootcss.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="/common/new/main.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - 足球说说|球员说说|球员动态|球员资讯|球星生活</title>
    
</head>

<body>
<!-- header start -->
<%@ include file="/common/new/menu.jsp"%>

<!-- pc content start -->
<div class="main hidden-sm hidden-xs">
	<div style="width:930px;float:left;">
		<div style="width:500px;float:left;">
			<div style="width:500px;height:140px;float:left;">
				<div style="width:220px;float:left;">
					<!-- 图片 -->
					<img src="${say.image_small}" style="width:220px;height:140px;" />
				</div>
				<div style="width:272px;height:140px;float:left;">
					<div class="multi-line-cut" style="-webkit-line-clamp: 5;text-align:left;width:272px;height:110px;float:left;background:#f5f5f5;padding:4px;padding-top:10px;font-size:14px;">
						<span style="color:#666;"><i>${say.content}</i></span>
					</div>
					<div style="width:272px;height:30px;float:left;background:#f9f9f9;">
						<div style="width:30px;height:30px;float:left;margin-left:4px;"><img src="${say.player_img_local}" style="width:30px;height:30px;border-radius:50%;"/></div>
						<div style="width:230px;height:30px;line-height:30px;float:left;text-align:left;margin-left:4px;"><span class="grey-title" style="color:grey;"><a href="player-${say.player_name_en}-${say.player_id}.html" target="_blank" title="${say.player_name}的详细信息">${say.player_name}</a>&nbsp;&nbsp;<fmt:formatDate value="${say.create_time}" pattern="yyyy-MM-dd"/></span></div>
					</div>
				</div>
			</div>
			<div style="width:500px;height:296px;float:left;text-align:left;padding-left:3px;padding-top:5px;">
				<c:forEach items="${funList}" var="fun">
					<div class="text-cut a-title" title="${fun.title}" style="width:490px;height:29px;line-height:29px;font-size:14px;"><span style="font-size:16px;color:#ddd;">&bull;</span> <a href="/fdetail-<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>-${fun.title_en}-${fun.id}.html" target="_blank"><span >${fun.title}（<fmt:formatDate value="${fun.create_time}" pattern="yyyy/MM/dd"/>）</span></a></div>
				</c:forEach>
			</div>
		</div>
		<div style="width:430px;height:436px;float:left;border:1px #e5e5e5 solid; padding:3px;padding-top:5px;">
				<c:forEach items="${liveMatchList}" var="match" >
					<div style="width:424px;height:60px;float:left;">
						<div style="width:100%;height:30px;line-height:30px;float:left;text-align:left;padding-left:5px;">
							<span class="label label-primary">${match.league_name}</span> <span style="color:#aaa;font-size:12px;">${match.weekday}</span>
						</div>
						<div class="a-title" style="width:100%;height:30px;line-height:30px;text-align:left;padding-left:5px;">
							<span style="color:#888;"><fmt:formatDate value="${match.match_datetime}" pattern="M月d日 HH:mm"/></span> 
								<c:if test="${empty match.league_id}">
									<a href="/live-<fmt:formatDate value="${match.match_datetime}" pattern="yyyy-MM-dd"/>-${match.home_team_enname}-vs-${match.away_team_enname}-${match.id}.html" target="_blank" title="查看详情">${match.home_team_name} <img src="assets/image/page/team_default.png" style="width:20px;height:20px;" > <span style="font-size:12px;color:#777;">VS</span> <img src="assets/image/page/team_default.png" style="width:20px;height:20px;" > ${match.away_team_name }<c:if test="${match.game_id!=0}"> <span class="label label-success" title="分析">析</span></c:if></a>
								</c:if>
								<c:if test="${!empty match.league_id}">
									<a href="/match-${match.home_team_enname}-vs-${match.away_team_enname}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_blank" title="查看详情">${match.home_team_name} <img src="assets/image/page/team_default.png" style="width:20px;height:20px;" > <span style="font-size:12px;color:#777;">VS</span> <img src="assets/image/page/team_default.png" style="width:20px;height:20px;" > ${match.away_team_name }<c:if test="${match.game_id!=0}"> <span class="label label-success" title="分析">析</span></c:if></a>
								</c:if>
						</div>
					</div>
				</c:forEach>
		</div>
	</div>
	<div style="width:930px;margin-top:10px;float:left;">
		<c:forEach items="${videoList}" var="video" varStatus="status" >
			<div style="width:173px;height:155px;background:#eee;float:left;${status.count!=5?'margin-right:16px;':''}">
				<div style="width:173px;height:100px;float:left;" title="${video.match_title}">
					<a href="/vdetail-<fmt:formatDate value="${video.match_date}" pattern="yyyy-MM-dd"/>-${video.match_en_title}-${video.id}.html" target="_blank"><img src="assets/image/page/v-default.jpg" style="width:173px;height:100px;"/></a>
				</div>
				<div  style="width:173px;height:55px;text-align:left;float:left;font-size:12px;padding:4px;margin-top:6px;">
					<div class="multi-line-cut a-title" title="${video.match_title}" style="width:100%;height:40px;float:left;">
						<a href="/vdetail-<fmt:formatDate value="${video.match_date}" pattern="yyyy-MM-dd"/>-${video.match_en_title}-${video.id}.html" target="_blank">${video.match_title}</a>
					</div>
				</div>
			</div>
		</c:forEach>
		
	</div>
</div>

<!-- pc content end -->
<%@ include file="/common/new/index-mobile.jsp"%>	

<!-- footer start -->
<%@ include file="/common/new/footer.jsp"%>	

</body>	
</html>
