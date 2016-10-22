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
					<div class="col-lg-12 col-md-12">
						<span>托特纳姆热刺</span>
					</div>
					<div class="col-lg-12 col-md-12">
						<img src="assets/image/page/team-logo.png" />
					</div>
				</div>
				
				<div class="row" style="margin-top:20px;">
					<div class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
						<table class="table " >
						  <caption style="min-height:30px;">前锋</caption>
						  <tbody >
						  	<c:set var="i" value="1"/>
						  	<tr style="border-top:1px solid #dddddd; ${2==playerlist.size()?'border-bottom:1px solid #dddddd;':''}">
							<c:forEach items="${playerlist}" var="player">
								<td style="width:50px;border:none;"><img src="assets/image/page/14.png" /></td>
						      	<td colspan="${i==playerlist.size()?3:1}" style="border:none;">孙兴慜&nbsp;24岁</td>
						      	<c:if test="${i%2==0}">
								</tr>
								<tr style="${i+2 ge playerlist.size()?'border-bottom:1px solid #dddddd;':''}">
								</c:if>
								<c:set var="i" value="${i+1}"></c:set>
							</c:forEach>
							</tr>
							
						  </tbody>
						</table>
					</div>
				</div>
				
			</div>
		</div>
		
		
</body>	

