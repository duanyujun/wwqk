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
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    
</head>

<body>
		<div class="row menu_bg clear_row_margin" >
			<div id="main_nav" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
				<div class="logo_div">
					<a >趣点足球网</a>
				</div>
				<ul style="float:left;">
					<li class="menu_width"><a href="">首页</a></li>
					<li class="menu_width"><a href="say">说说</a></li>
					<li class="menu_width"><a href="match">比赛</a></li>
					<li class="menu_width menu_sel"><a href="data">数据</a></li>
				</ul>	
			</div>
		</div>
		
		<div class="row clear_row_margin" style="margin-top:20px;">
			<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
				<div class="row">
					<div class="col-lg-2 col-md-2">
						<a href="" class="link-img"><img src="assets/image/page/league-logo1.jpg" style="width:60px;height:60px;"/></a>
					</div>
					<div class="col-lg-2 col-md-2">
						<a href="" class="link-img"><img src="assets/image/page/league-logo2.jpg" style="width:60px;height:60px;"/></a>
					</div>
					<div class="col-lg-2 col-md-2">
						<a href="" class="link-img"><img src="assets/image/page/league-logo3.jpg" style="width:60px;height:60px;"/></a>
					</div>
					<div class="col-lg-2 col-md-2">
						<a href="" class="link-img"><img src="assets/image/page/league-logo4.jpg" style="width:60px;height:60px;"/></a>
					</div>
					<div class="col-lg-2 col-md-2">
						<a href="" class="link-img"><img src="assets/image/page/league-logo5.jpg" style="width:60px;height:60px;"/></a>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-2 col-md-2">
						<div class="select-league">英超</div>
					</div>
					<div class="col-lg-2 col-md-2">
						<div class="common-league">德甲</div>
					</div>
					<div class="col-lg-2 col-md-2">
						<div class="common-league">西甲</div>
					</div>
					<div class="col-lg-2 col-md-2">
						<div class="common-league">意甲</div>
					</div>
					<div class="col-lg-2 col-md-2">
						<div class="common-league">法甲</div>
					</div>
				</div>
			</div>
		</div>
</body>	

