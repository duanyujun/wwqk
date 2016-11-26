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
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    
</head>

<body>
	<div class="row menu_bg clear_row_margin" >
		<div id="main_nav" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div>
				<div class="logo_div">
					<a style="margin-left:12px;">趣点足球网</a>
				</div>
				<ul style="float:left;">
					<li class="menu_width"><a href="">首页</a></li>
					<li class="menu_width"><a href="fun">趣点</a></li>
					<li class="menu_sel menu_width"><a href="say">说说</a></li>
					<li class="menu_width"><a href="match">比赛</a></li>
					<li class="menu_width"><a href="data">数据</a></li>
				</ul>	
			</div>
		</div>
	</div>
	
	<div class="row clear_row_margin" style="margin-top:70px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-9 col-md-9 bread">
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;说说广场
			</div>
		</div>
	</div>
	
	<div class="row clear_row_margin" style="margin-top:20px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-9 col-md-9">
				<c:forEach items="${sayPage.list}" var="say" varStatus="status">
					<div class="col-lg-12 col-md-12" style="border:1px solid #E3E7EA;${status.index!=0?'border-top:0;':''}padding:20px;padding-left:0;padding-bottom:10px;">
						<div class="col-lg-1 col-md-1">
							<a href="say/list?id=${say.player_id}" style="color:#292f33;" target="_blank"><img src="${say.player_img_local}" style="width:48px;height:48px;" /></a>
						</div>
						<div class="col-lg-11 col-md-11" >
							<div class="col-lg-12 col-md-12 say-info">
								<span style="font-weight:bold;color:#292f33;"><a href="say/list?id=${say.player_id}" style="color:#292f33;" target="_blank">${say.player_name}</a></span>
								<span style="color:#8899a6;font-size:13px;"> - <fmt:formatDate value="${say.create_time}" pattern="MM月dd日"/> </span>
								&nbsp;<a href="say/list?id=${say.player_id}" target="_blank" title="${say.player_name}的更多说说">查看更多</a>
							</div>
							<div class="col-lg-12 col-md-12">
								<span style="color:#292f33;font-size:14px;">${say.content}</span>
							</div>
							<c:if test="${!empty say.image_big}">
								<div class="col-lg-12 col-md-12" style="margin-top:10px;">
									<img src="${say.image_big}" class="img-responsive img-rounded" />
								</div>
							</c:if>
						</div>
					</div>
				</c:forEach>
				
				
				<div class="col-lg-12 col-md-12 " style="margin-top:20px;">
					<div class="scott pull-right">
						<a href="/say?pageNumber=1" title="首页"> &lt;&lt; </a>
						
						<c:if test="${sayPage.pageNumber == 1}">
							<span class="disabled"> &lt; </span>
						</c:if>
						<c:if test="${sayPage.pageNumber != 1}">
							<a href="/say?pageNumber=${sayPage.pageNumber - 1}" > &lt; </a>
						</c:if>
						<c:if test="${sayPage.pageNumber > 8}">
							<a href="/say?pageNumber=1">1</a>
							<a href="/say?pageNumber=2">2</a>
							...
						</c:if>
						<c:if test="${!empty pageUI.list}">
							<c:forEach items="${pageUI.list}" var="pageNo">
								<c:if test="${sayPage.pageNumber == pageNo }">
									<span class="current">${pageNo}</span>
								</c:if>
								<c:if test="${sayPage.pageNumber != pageNo }">
									<a href="/say?pageNumber=${pageNo}">${pageNo}</a>
								</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${(sayPage.totalPage - sayPage.pageNumber) >= 8 }">
							...
							<a href="/say?pageNumber=${sayPage.totalPage - 1}">${sayPage.totalPage - 1}</a>
							<a href="/say?pageNumber=${sayPage.totalPage}">${sayPage.totalPage}</a>
						</c:if>
						
						<c:if test="${sayPage.pageNumber == sayPage.totalPage}">
							<span class="disabled"> &gt; </span>
						</c:if>
						<c:if test="${sayPage.pageNumber != sayPage.totalPage}">
							<a href="/say?pageNumber=${sayPage.pageNumber + 1}"> &gt; </a>
						</c:if>
						
						<a href="/say?pageNumber=${sayPage.totalPage}" title="尾页"> &gt;&gt; </a>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<%@ include file="/common/footer.jsp"%>	
</body>	

