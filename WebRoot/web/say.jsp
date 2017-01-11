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
	<meta name="keywords" content="趣点足球网,球员说说,球星资讯,球员动态,球星生活" />
	<meta name="description" content="趣点足球网为球迷们提供最新的球员生活、球星生活动态，球员资讯" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - 足球说说|球员说说|球员动态|球员资讯|球星生活</title>
</head>

<body>
	<div class="row menu_bg clear_row_margin" >
		<div id="main_nav" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">	
			<div>
				<div class="logo_div">
					<a href="" title="首页">趣点足球网</a>
				</div>
				<ul style="float:left;" class="hidden-sm hidden-xs">
					<li class="menu_width"><a href="">首页</a></li>
					<li class="menu_width"><a href="fun.html">趣点</a></li>
					<li class="menu_width menu_sel"><a href="say.html">说说</a></li>
					<li class="menu_width"><a href="match.html">比赛</a></li>
					<li class="menu_width"><a href="data.html">数据</a></li>
				</ul>
				<div class="visible-sm visible-xs small-menu">
					<select id="menuSelect" class="form-control small-select">
						<option value="">首页</option>
						<option value="fun.html">趣点</option>
						<option selected value="say.html">说说</option>
						<option value="match.html">比赛</option>
						<option value="data.html">数据</option>
					</select>	
				</div>
			</div>
		</div>
		
	</div>
	
	
	<div class="row clear_row_margin" style="margin-top:70px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="bread">
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;说说广场
			</div>
		</div>
	</div>
	
	<div class="row clear_row_margin" style="margin-top:10px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
				<c:forEach items="${sayPage.list}" var="say" varStatus="status">
					<div class="col-lg-9 col-md-9 col-sm-12 col-xs-12" style="border:1px solid #E3E7EA;${status.index!=0?'border-top:0;':''}padding:20px;padding-left:0;padding-bottom:10px;">
						<div class="col-lg-1 col-md-1 hidden-sm hidden-xs">
							<a href="player-${say.player_name_en}-${say.player_id}.html" style="color:#292f33;" target="_blank"><img src="${say.player_img_local}" style="width:48px;height:48px;" alt="${say.player_name}" title="${say.player_name}"/></a>
						</div>
						<div class="col-lg-11 col-md-11 col-sm-12 col-xs-12" >
							<div class="col-lg-12 col-md-12 say-info hidden-sm hidden-xs">
								<span style="display:block;font-weight:bold;color:#292f33;float:left;"><a href="player-${say.player_name_en}-${say.player_id}.html" style="color:#292f33;" target="_blank">${say.player_name}</a></span>
								<span style="display:block;color:#8899a6;font-size:13px;float:left;"> - <fmt:formatDate value="${say.create_time}" pattern="MM月dd日"/> </span>
								&nbsp;<a href="player-${say.player_name_en}-${say.player_id}.html" target="_blank" title="${say.player_name}的更多说说">查看更多</a>
							</div>
							
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 say-info visible-sm visible-xs" style="line-height:30px;height:30px;padding-left:0px;padding-right:0px;">
								<span style="display:block;float:left;"><a class="visible-sm visible-xs" href="player-${say.player_name_en}-${say.player_id}.html" style="color:#292f33;" target="_blank"><img src="${say.player_img_local}" style="width:30px;height:30px;" /></a></span>
								<span style="display:block;font-weight:bold;color:#292f33;float:left;"><a href="player-${say.player_name_en}-${say.player_id}.html" style="color:#292f33;" target="_blank">${say.player_name}</a></span>
								<span style="display:block;color:#8899a6;font-size:13px;float:left;"> - <fmt:formatDate value="${say.create_time}" pattern="MM月dd日"/> </span>
								&nbsp;<a href="player-${say.player_name_en}-${say.player_id}.html" target="_blank" title="${say.player_name}的更多说说">查看更多</a>
							</div>
						
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 hidden-sm hidden-xs">
								<span style="color:#292f33;font-size:14px;">${say.content}</span>
							</div>
							
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 visible-sm visible-xs" style="padding-right:0px;margin-top:8px;">
								<span style="color:#292f33;font-size:14px;">${say.content}</span>
							</div>
							
							
							<c:if test="${!empty say.image_big}">
								<div class="col-lg-12 col-md-12" style="margin-top:10px;">
									<img src="${say.image_big}" class="img-responsive img-rounded" alt="${say.content}" title="${say.player_name} - ${say.content}"/>
								</div>
							</c:if>
						</div>
					</div>
				</c:forEach>
				
				
				<div class="col-lg-9 col-md-9" style="margin-top:20px;padding-right:0px;">
					<div class="scott pull-right" >
						<a href="/say-page-1.html" title="首页"> &lt;&lt; </a>
						
						<c:if test="${sayPage.pageNumber == 1}">
							<span class="disabled"> &lt; </span>
						</c:if>
						<c:if test="${sayPage.pageNumber != 1}">
							<a href="/say-page-${sayPage.pageNumber - 1}.html" > &lt; </a>
						</c:if>
						<c:if test="${sayPage.pageNumber > 8}">
							<a href="/say-page-1.html">1</a>
							<a href="/say-page-2.html">2</a>
							...
						</c:if>
						<c:if test="${!empty pageUI.list}">
							<c:forEach items="${pageUI.list}" var="pageNo">
								<c:if test="${sayPage.pageNumber == pageNo }">
									<span class="current">${pageNo}</span>
								</c:if>
								<c:if test="${sayPage.pageNumber != pageNo }">
									<a href="/say-page-${pageNo}.html">${pageNo}</a>
								</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${(sayPage.totalPage - sayPage.pageNumber) >= 8 }">
							...
							<a href="/say-page-${sayPage.totalPage - 1}.html">${sayPage.totalPage - 1}</a>
							<a href="/say-page-${sayPage.totalPage}.html">${sayPage.totalPage}</a>
						</c:if>
						
						<c:if test="${sayPage.pageNumber == sayPage.totalPage}">
							<span class="disabled"> &gt; </span>
						</c:if>
						<c:if test="${sayPage.pageNumber != sayPage.totalPage}">
							<a href="/say-page-${sayPage.pageNumber + 1}.html"> &gt; </a>
						</c:if>
						
						<a href="/say-page-${sayPage.totalPage}.html" title="尾页" > &gt;&gt; </a>
					</div>
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

