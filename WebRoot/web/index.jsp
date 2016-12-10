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
					<li class="menu_sel menu_width"><a href="">首页</a></li>
					<li class="menu_width"><a href="fun">趣点</a></li>
					<li class="menu_width"><a href="say">说说</a></li>
					<li class="menu_width"><a href="match">比赛</a></li>
					<li class="menu_width"><a href="data">数据</a></li>
				</ul>	
			</div>
		</div>
		
	</div>
	
	<div class="row clear_row_margin" style="margin-top:80px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-9 col-md-9" style="padding-left:0px;">
				<c:forEach items="${funPage.list}" var="fun" varStatus="status">
					<c:if test="${status.index!=0}">
						<div class="col-lg-12 col-md-12" style="margin-top:19px;height:1px;"></div>
					</c:if>
				
					<div class="col-lg-4 col-md-4">
						<c:if test="${fun.type==1}">
							<a href="fun/detail?id=${fun.id}" target="_blank"><img src="${fun.image_small}" class="msg-img" /></a>
						</c:if>
						<c:if test="${fun.type==2}">
							<a href="say/detail?id=${fun.source_id}" target="_blank"><img src="${fun.image_small}" class="msg-img" /></a>
						</c:if>
					</div>
					<div class="col-lg-8 col-md-8" style="padding-left:0px;">
						<div class="col-lg-12 col-md-12">
							<span class="msg-title">
								<c:if test="${fun.type==1}">
									<a href="fun/detail?id=${fun.id}" target="_blank">${fun.title}</a>
								</c:if>
								<c:if test="${fun.type==2}">
									<a href="say/detail?id=${fun.source_id}" target="_blank">${fun.title}</a>
								</c:if>
							</span>
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:5px;">
							<div class="mob-author">
	                                <div class="author-face">
				                        <c:if test="${fun.type==1}">
											<img src="assets/image/page/logo-small.png">
										</c:if>
										<c:if test="${fun.type==2}">
											<a href="say/list?id=${fun.player_id}" target="_blank"><img src="${fun.player_image}"></a>
										</c:if>
		                            </div>
		                            
		                            <c:if test="${fun.type==1}">
										<a href="fun" target="_blank" class="mob-author-a">
			                                <span class="author-name">趣点网</span>
			                            </a>
									</c:if>
									<c:if test="${fun.type==2}">
										<a href="say/list?id=${fun.player_id}" target="_blank" class="mob-author-a">
			                                <span class="author-name">${fun.player_name}</span>
			                            </a>
									</c:if>
		                            
		                            <span class="author-name">
										&nbsp;<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd hh:mm:ss"/>
									</span>
		                    </div>
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:20px;padding-right:0;">
							<span class="summary">${fun.summary}</span>
						</div>
					</div>
					<div class="col-lg-12 col-md-12">
						<div class="index-line"></div>
					</div>
					
				</c:forEach>
				
				<div class="col-lg-12 col-md-12 " style="margin-top:20px;">
					<div class="scott pull-right">
						<a href="/?pageNumber=1" title="首页"> &lt;&lt; </a>
						
						<c:if test="${funPage.pageNumber == 1}">
							<span class="disabled"> &lt; </span>
						</c:if>
						<c:if test="${funPage.pageNumber != 1}">
							<a href="/?pageNumber=${funPage.pageNumber - 1}" > &lt; </a>
						</c:if>
						<c:if test="${funPage.pageNumber > 8}">
							<a href="/?pageNumber=1">1</a>
							<a href="/?pageNumber=2">2</a>
							...
						</c:if>
						<c:if test="${!empty pageUI.list}">
							<c:forEach items="${pageUI.list}" var="pageNo">
								<c:if test="${funPage.pageNumber == pageNo }">
									<span class="current">${pageNo}</span>
								</c:if>
								<c:if test="${funPage.pageNumber != pageNo }">
									<a href="/?pageNumber=${pageNo}">${pageNo}</a>
								</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${(funPage.totalPage - funPage.pageNumber) >= 8 }">
							...
							<a href="/?pageNumber=${funPage.totalPage - 1}">${funPage.totalPage - 1}</a>
							<a href="/?pageNumber=${funPage.totalPage}">${funPage.totalPage}</a>
						</c:if>
						
						<c:if test="${funPage.pageNumber == funPage.totalPage}">
							<span class="disabled"> &gt; </span>
						</c:if>
						<c:if test="${funPage.pageNumber != funPage.totalPage}">
							<a href="/?pageNumber=${funPage.pageNumber + 1}"> &gt; </a>
						</c:if>
						
						<a href="/?pageNumber=${funPage.totalPage}" title="尾页"> &gt;&gt; </a>
					</div>
				</div>
				
			</div>
		</div>
	</div>
	
	<%@ include file="/common/footer.jsp"%>		
</body>	

