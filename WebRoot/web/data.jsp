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
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    
</head>

<body>
	<div class="row menu_bg" >
		<div id="main_nav" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="logo_div">
				<a >趣点足球网</a>
			</div>
			<ul style="float:left;">
				<li class="menu_width"><a href="#">首页</a></li>
				<li class="menu_width"><a href="match">比赛</a></li>
				<li class="menu_width menu_sel"><a href="data">数据</a></li>
			</ul>	
		</div>
	</div>
	
	
	
</body>	

