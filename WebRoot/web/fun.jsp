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
					<li class="menu_sel menu_width"><a href="fun">趣点</a></li>
					<li class="menu_width"><a href="say">说说</a></li>
					<li class="menu_width"><a href="match">比赛</a></li>
					<li class="menu_width"><a href="data">数据</a></li>
				</ul>	
			</div>
		</div>
		
	</div>
	
	<div class="row clear_row_margin" style="margin-top:20px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-9 col-md-9" style="padding-left:0px;">
				<c:forEach items="${list}" var="l" varStatus="status">
					<c:if test="${status.index!=0}">
						<div class="col-lg-12 col-md-12" style="margin-top:19px;height:1px;"></div>
					</c:if>
				
					<div class="col-lg-4 col-md-4">
						<img src="assets/image/page/1.jpg" class="msg-img" />
					</div>
					<div class="col-lg-8 col-md-8" style="padding-left:0px;">
						<div class="col-lg-12 col-md-12">
							<span class="msg-title"><a href="fun/detail" target="_blank">早报：同城双雄，命运各异</a></span>
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:5px;">
							<div class="mob-author">
	                                <div class="author-face">
		                                <a href="/member/1450385.html" target="_blank"><img src="https://imgs.bipush.com/auth/data/avatar/001/45/03/85_1462766181.jpg!40x40?imageMogr2/strip/interlace/1/quality/85/format/jpg"></a>
		                            </div>
		                            <a href="/member/1450385.html" target="_blank" class="mob-author-a">
		                                <span class="author-name">话题小助手</span>
		                            </a>
		                            <span class="author-name">2016-10-17 07:30:00</span>
		                    </div>
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:20px;padding-right:0;">
							<span class="summary">AC米兰3-1客胜重回欧冠区；国际米兰主场遭升班马逆转绝杀。升班马逆转绝杀AC米兰3-1客胜重回欧冠区；国际米兰主场遭升班马逆转绝杀。</span>
						</div>
					</div>
					<div class="col-lg-12 col-md-12">
						<div class="index-line"></div>
					</div>
					
				</c:forEach>
			</div>
		</div>
	</div>
	
		
</body>	

