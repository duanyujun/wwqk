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
			<div class="col-lg-7 col-md-7">
				<c:forEach items="${list}" var="l" varStatus="status">
					<div class="col-lg-12 col-md-12" style="border:1px solid #E3E7EA;${status.index!=0?'border-top:0;':''}padding:5px;padding-left:0;padding-bottom:10px;">
						<div class="col-lg-1 col-md-1">
							<img src="assets/image/page/14.png" style="width:48px;height:48px;" />
						</div>
						<div class="col-lg-11 col-md-11" >
							<div class="col-lg-12 col-md-12 say-info">
								<span style="font-weight:bold;color:#292f33;">拉姆</span>
								<span style="color:#8899a6;font-size:13px;"> - 10月14日</span>
								&nbsp;<a title="拉姆的更多说说">查看更多</a>
							</div>
							<div class="col-lg-12 col-md-12">
								<span style="color:#292f33;font-size:14px;">今晚的团队的伟大胜利。现在我可以享受周末庆祝我的生日，今晚的团队的伟大胜利。现在我可以享受周末庆祝我的生日:)</span>
							</div>
							<div class="col-lg-12 col-md-12" style="margin-top:10px;">
								<img src="assets/image/page/t-1.jpg" class="img-responsive img-rounded" />
							</div>
						</div>
					</div>
				</c:forEach>
				
			</div>
		</div>
	</div>
	
		
</body>	

