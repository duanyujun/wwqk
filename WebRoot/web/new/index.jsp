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
	<meta name="keywords" content="趣点足球网,在线直播链接,球员动态,视频集锦,球队排名" />
	<meta name="description" content="趣点足球网为球迷们提供最新的球员资讯，直播链接以及足球数据" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="https://cdn.bootcss.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="/common/new/main.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/viewer/viewer.min.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/css/slideBox.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - 足球趣闻|球员动态|直播预告|视频集锦|足球数据</title>
    
</head>

<body>
<!-- header start -->
<%@ include file="/common/new/menu.jsp"%>

<!-- pc content start -->
<div class="main hidden-sm hidden-xs">
	<div class="left_w930" >
		<div class="left_w500">
			<div class="left_w500 h_140">
				<div class="left w220">
					<!-- 图片 -->
					<img src="${say.image_small}" big="${say.image_big}" class="image w220 h_140 pointer" />
				</div>
				<div class="h_140 left w272" >
					<div class="multi-line-cut idx-content" >
						<span style="color:#666;"><i>${say.content}</i></span>
					</div>
					<div class="idx-player">
						<div class="idx-player-div"><img src="${say.player_img_local}" class="idx-player-img" /></div>
						<div class="idx-player-name"><span class="grey-title" style="color:grey;"><a href="player-${say.player_name_en}-${say.player_id}.html" target="_blank" title="${say.player_name}的详细信息">${say.player_name}</a>&nbsp;&nbsp;<fmt:formatDate value="${say.create_time}" pattern="yyyy-MM-dd"/></span></div>
					</div>
				</div>
			</div>
			<div class="idx-fun-div">
				<c:forEach items="${funList}" var="fun">
					<div class="text-cut a-title idx-fun-title" title="${fun.title}" ><span class="idx-fun-dot" >&bull;</span> <a href="/fdetail-<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>-${fun.title_en}-${fun.id}.html" target="_blank"><span >${fun.title}（<fmt:formatDate value="${fun.create_time}" pattern="yyyy/MM/dd"/>）</span></a></div>
				</c:forEach>
			</div>
		</div>
		<div class="idx-match">
				<c:forEach items="${liveMatchList}" var="match" >
					<div class="idx-match-each" >
						<div class="idx-match-title">
							<span class="label label-primary">${match.league_name}</span> <span class="idx-match-wk" >${match.weekday}</span>
						</div>
						<div class="a-title idx-match-team" >
							<span class="color_888"><fmt:formatDate value="${match.match_datetime}" pattern="M月d日 HH:mm"/></span> 
								<c:if test="${empty match.league_id}">
									<a href="/live-<fmt:formatDate value="${match.match_datetime}" pattern="yyyy-MM-dd"/>-${match.home_team_enname}-vs-${match.away_team_enname}-${match.id}.html" target="_blank" title="查看详情">${match.home_team_name} <img src="assets/image/page/team_default.png" class="idx-match-team-img" > <span class="idx-match-vs">VS</span> <img src="assets/image/page/team_default.png" class="idx-match-team-img" > ${match.away_team_name }<c:if test="${match.game_id!=0}"> <span class="label label-success" title="分析">析</span></c:if></a>
								</c:if>
								<c:if test="${!empty match.league_id}">
									<a href="/match-${match.home_team_enname}-vs-${match.away_team_enname}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_blank" title="查看详情">${match.home_team_name} <img src="assets/image/page/team_default.png" class="idx-match-team-img" > <span class="idx-match-vs">VS</span> <img src="assets/image/page/team_default.png" class="idx-match-team-img" > ${match.away_team_name }<c:if test="${match.game_id!=0}"> <span class="label label-success" title="分析">析</span></c:if></a>
								</c:if>
						</div>
					</div>
				</c:forEach>
		</div>
	</div>
	<div class="idx-video" >
		<c:forEach items="${videoList}" var="video" varStatus="status" >
			<div class="idx-video-each" style="${(status.count!=5 && status.count!=10)?'margin-right:16px;':''};${status.count>5?'margin-top:15px;':''}">
				<div class="idx-video-img left"  title="${video.match_title}">
					<a href="/vdetail-<fmt:formatDate value="${video.match_date}" pattern="yyyy-MM-dd"/>-${video.match_en_title}-${video.id}.html" target="_blank"><img src="${(empty video.video_img)?'assets/image/page/v-default.jpg':video.video_img}" class="idx-video-img"/></a>
				</div>
				<div  class="idx-video-title">
					<div class="multi-line-cut a-title idx-video-t" title="${video.match_title}">
						<a href="/vdetail-<fmt:formatDate value="${video.match_date}" pattern="yyyy-MM-dd"/>-${video.match_en_title}-${video.id}.html" target="_blank">${video.match_title}</a>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
	
	<div class="idx-tip-outter">
	  <div id="message" class="idx-tip" >
	    <ul>
	      <c:forEach items="${lstTipsMatch}" var="tips">
	      		<li >
	      			<div  class="idx-tip-each" >
	      				<div class="a-title idx-tip-inner" >
		      				<span class="label label-primary">${tips.league_name}</span>
		      				<c:if test="${tips.live_match_id!=0}">
		      					<c:if test="${!empty tips.home_team_id }">
		      						&nbsp;<a target="_blank" href="match-<fmt:formatDate value="${tips.match_time}" pattern="yyyy-MM-dd-HHmm"/>-${tips.home_team_enname}-vs-${tips.away_team_enname}_${tips.match_key}.html"><u>${tips.home_name} vs ${tips.away_name}</u><i>（<fmt:formatDate value="${tips.match_time}" pattern="M月d日 HH:mm"/>）</i></a>
		      					</c:if>
		      					<c:if test="${empty tips.home_team_id }">
		      						&nbsp;<a target="_blank" href="live-<fmt:formatDate value="${tips.match_time}" pattern="yyyy-MM-dd-HHmm"/>-${tips.home_team_enname}-vs-${tips.away_team_enname}-${tips.live_match_id}.html"><u>${tips.home_name} vs ${tips.away_name}</u><i>（<fmt:formatDate value="${tips.match_time}" pattern="M月d日 HH:mm"/>）</i></a>
		      					</c:if>
		      				</c:if>
		      				<c:if test="${tips.live_match_id==0}">
		      					&nbsp;${tips.home_name} vs ${tips.away_name}<i>（<fmt:formatDate value="${tips.match_time}" pattern="M月d日 HH:mm"/>）</i>
		      				</c:if>
		      			</div>
	      				<div class="idx-tip-desc" title="${tips.prediction_desc}">
		      				${tips.prediction_desc}
		      			</div>
	      			</div>
	      		</li>
	      </c:forEach>
	    </ul>
	  </div>
	</div>
	
	<!-- 首页推广 -->
	<div id="slideBox" class="slideBox">
	  <ul class="items" >
	  	<c:forEach items="${lstAlliance}" var="alliance">
	  		<li class="h_200"><a href="${alliance.tbk_short_url}" title="${alliance.product_name}"   target="_blank"><img src="${alliance.product_img}" title="${alliance.product_name}" class="idx-tip-pimg" ></a></li>
	  	</c:forEach>
	  </ul>
	</div>
	
</div>

<!-- pc content end -->
<%@ include file="/common/new/index-mobile.jsp"%>	

<!-- footer start -->
<%@ include file="/common/new/footer.jsp"%>	

<script src="assets/global/plugins/viewer/viewer-jquery.min.js" type="text/javascript"></script>
<script src="assets/global/plugins/jquery.slideBox.min.js" type="text/javascript"></script>

<script type="text/javascript">
	function AutoScroll(obj) {
	    $(obj).find("ul:first").animate({
	        marginTop: "-22px"
	    },
	    500,
	    function() {
	        $(this).css({
	            marginTop: "0px"
	        }).find("li:first").appendTo(this);
	    });
	}

	$(function(){
		setInterval('AutoScroll("#message")', 15000);
		$('.image').viewer(
				{toolbar:false, 
				zIndex:20000,
				url:"big",
				shown: function() {
					$(".viewer-canvas").attr("data-action","mix");
				 }
			 }
		);
		
		//首页广告
		$('#slideBox').slideBox({
			duration : 0.3,//滚动持续时间，单位：秒
			easing : 'linear',//swing,linear//滚动特效
			hideClickBar : false,//不自动隐藏点选按键
		});
	});
	
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
</html>
