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
	<meta name="keywords" content="足球录像集锦,英超视频,西甲视频,德甲视频,意甲视频,法甲视频,中超视频,欧冠视频,足球视频在线观看" />
	<meta name="description" content="提供足球视频录像,足球全场录像,足球在线观看视频等,英超,西甲,德甲,意甲,法甲等五大联赛,欧洲杯,美洲杯,欧冠,中超等一些热点足球赛事录像，弥补球迷朋友们不能看直播的遗憾！" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="https://cdn.bootcss.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="/common/new/main.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - 足球录像集锦|英超视频|西甲视频|德甲视频|意甲视频|法甲视频|中超视频|欧冠视频|足球视频在线观看</title>
 	<style type="text/css">
 		.table-hover>tbody>tr:hover>td, .table-hover>tbody>tr:hover>th{color:#000;}
 		.tdsp{color:white;}
 	</style>
</head>

<body>
<!-- header start -->
<%@ include file="/common/new/menu.jsp"%>

<!-- pc content start -->
<div class="main hidden-sm hidden-xs">
	<div class="left_w930">
		<div class="bread tleft mb_10">
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;视频
		</div>
		<div class="video-type">  
				 <div class="video-inner">
				 	
					<div class="team-title video-type-each">
						<a href="videos-league-1.html" ><div class="${leagueId==1?'select-league':'common-league'}">英超</div></a>
					</div>
					<div class="team-title video-type-each" >
						<a href="videos-league-2.html" ><div class="${leagueId==2?'select-league':'common-league'}">西甲</div></a>
					</div>
					<div class="team-title video-type-each">
						<a href="videos-league-3.html" ><div class="${leagueId==3?'select-league':'common-league'}">德甲</div></a>
					</div>
					<div class="team-title video-type-each">
						<a href="videos-league-4.html" ><div class="${leagueId==4?'select-league':'common-league'}">意甲</div></a>
					</div>
					<div class="team-title video-type-each">
						<a href="videos-league-5.html" ><div class="${leagueId==5?'select-league':'common-league'}">法甲</div></a>
					</div>
					<div class="team-title video-type-each">
						<a href="videos-league-6.html" ><div class="${leagueId==6?'select-league':'common-league'}">欧冠</div></a>
					</div>
					<div class="team-title video-type-each">
						<a href="videos-league-7.html" ><div class="${leagueId==7?'select-league':'common-league'}">中超</div></a>
					</div>
					<div class="team-title video-type-each">
						<a href="videos-league-8.html" ><div class="${leagueId==8?'select-league':'common-league'}">其他</div></a>
					</div>
				</div>
			
				   <div class="table-responsive video-each">
						<table class="table table-condensed table-hover video-table">
						  <tbody>
						  	<c:forEach items="${videosPage.list}" var="videos">
						    <tr>
						      <td class="a-title lheight_30" ><a style="${videos.is_red=='1'?'color:red;':''}" href="/vdetail-<fmt:formatDate value="${videos.match_date}" pattern="yyyy-MM-dd"/>-${videos.match_en_title}-${videos.id}.html" target="_blank">${videos.match_title}</a></td>
						    </tr>
						    </c:forEach>
						  </tbody>
						</table>
					</div>
					
					<div class="video-page">
						<div class="scott pull-right" >
							<a href="/videos-league-${leagueId}-page-1.html" title="首页"> &lt;&lt; </a>
							
							<c:if test="${videosPage.pageNumber == 1}">
								<span class="disabled"> &lt; </span>
							</c:if>
							<c:if test="${videosPage.pageNumber != 1}">
								<a href="/videos-league-${leagueId}-page-${videosPage.pageNumber - 1}.html" > &lt; </a>
							</c:if>
							<c:if test="${videosPage.pageNumber > 8}">
								<a href="/videos-league-${leagueId}-page-1.html">1</a>
								<a href="/videos-league-${leagueId}-page-2.html">2</a>
								...
							</c:if>
							<c:if test="${!empty pageUI.list}">
								<c:forEach items="${pageUI.list}" var="pageNo">
									<c:if test="${videosPage.pageNumber == pageNo }">
										<span class="current">${pageNo}</span>
									</c:if>
									<c:if test="${videosPage.pageNumber != pageNo }">
										<a href="/videos-league-${leagueId}-page-${pageNo}.html">${pageNo}</a>
									</c:if>
								</c:forEach>
							</c:if>
							<c:if test="${(videosPage.totalPage - videosPage.pageNumber) >= 8 }">
								...
								<a href="/videos-league-${leagueId}-page-${videosPage.totalPage - 1}.html">${videosPage.totalPage - 1}</a>
								<a href="/videos-league-${leagueId}-page-${videosPage.totalPage}.html">${videosPage.totalPage}</a>
							</c:if>
							
							<c:if test="${videosPage.pageNumber == videosPage.totalPage}">
								<span class="disabled"> &gt; </span>
							</c:if>
							<c:if test="${videosPage.pageNumber != videosPage.totalPage}">
								<a href="/videos-league-${leagueId}-page-${videosPage.pageNumber + 1}.html"> &gt; </a>
							</c:if>
							
							<a href="/videos-league-${leagueId}-page-${videosPage.totalPage}.html" title="尾页" > &gt;&gt; </a>
						</div>
					</div>
					
			</div>
	</div>
</div>

<!-- pc content end -->
<%@ include file="/common/new/videos-mobile.jsp"%>	

<!-- footer start -->
<%@ include file="/common/new/footer.jsp"%>	

</body>	
</html>
