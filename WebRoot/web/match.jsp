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
					<li class="menu_width"><a href="say">说说</a></li>
					<li class="menu_width menu_sel"><a href="match">比赛</a></li>
					<li class="menu_width"><a href="data">数据</a></li>
				</ul>	
			</div>
		</div>
		
	</div>
	
	<div class="row clear_row_margin" style="margin-top:20px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-9 col-md-9" style="padding-left:0px;">
				<div class="table-responsive">
					<table class="table table-condensed">
					  <caption><img src="assets/image/page/league-logo5.jpg" style="width:100px;height:100px;"/></caption>
					  <thead>
					    <tr>
					      <th>比赛时间（英超）</th>
					      <th></th>
					      <th></th>
					      <th></th>
					      <th></th>	
					    </tr>
					  </thead>
					  <tbody>
					    <tr>
					      <td>2016-09-18 星期二</td>
					      <td><img src="assets/image/page/flag-1.png" style="width:25px;height:25px;"/>&nbsp;利物浦</td>
					      <td>03 : 00</td>
					      <td><img src="assets/image/page/flag-2.png" style="width:25px;height:25px;"/>&nbsp;曼联</td>
					      <td></td>
					    </tr>
					  </tbody>
					</table>
				</div>
				
				
			</div>
			<div class="col-lg-3 col-md-3">
				
			</div>
		</div>
	</div>
	
		
</body>	

