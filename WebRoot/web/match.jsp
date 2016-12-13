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
					<a href="" title="首页">趣点足球网</a>
				</div>
				<ul style="float:left;" class="hidden-sm hidden-xs">
					<li class="menu_width"><a href="">首页</a></li>
					<li class="menu_width"><a href="fun">趣点</a></li>
					<li class="menu_width"><a href="say">说说</a></li>
					<li class="menu_width menu_sel"><a href="match">比赛</a></li>
					<li class="menu_width"><a href="data">数据</a></li>
				</ul>
				<div class="visible-sm visible-xs small-menu">
					<select id="menuSelect" class="form-control small-select">
						<option value="">首页</option>
						<option value="fun">趣点</option>
						<option value="say">说说</option>
						<option selected value="match">比赛</option>
						<option value="data">数据</option>
					</select>	
				</div>
			</div>
		</div>
	</div>
	
	
	<div class="row clear_row_margin" style="margin-top:70px;">
		<div id="main_content" style="min-height:10px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="bread">
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;比赛
			</div>
		</div>
	</div>
	
	<div class="row clear_row_margin" style="margin-top:1px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-10 col-lg-offset-2 col-md-10 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-9 col-md-9" style="padding-left:0px;padding-right:0px;">
				<c:forEach items="${lstGroup}" var="group">
					<div class="table-responsive hidden-sm hidden-xs" style="margin-top:10px;">
						<table class="table table-condensed table-hover" style="border-bottom:1px solid #dddddd;">
						  <caption style="text-align:center;"><img title="${group[0].league_name}" src="assets/image/page/league-logo${group[0].league_id}.jpg" style="width:80px;height:80px;"/></caption>
						  <thead>
						    <tr>
						      <th style="width:230px;">比赛时间（${group[0].league_name}）</th>
						      <th style="width:180px;"></th>
						      <th style="width:180px;"></th>
						      <th style="width:180px;"></th>
						      <th style="width:10px;"></th>	
						    </tr>
						  </thead>
						  <tbody>
						  	<c:forEach items="${group}" var="match">
						    <tr>
						      <td>${match.match_date} &nbsp;&nbsp;星期${match.match_weekday}</td>
						      <td class="team-title"><a href="team?id=${match.home_team_id}" target="_blank"><img src="assets/image/soccer/teams/150x150/${match.home_team_id}.png" style="width:25px;height:25px;"/>&nbsp;${match.home_team_name}</a></td>
						      <td>
						      	<c:if test="${fn:contains(match.result, '-')}">
						      		<b>${match.result}</b>
						      	</c:if>
						      	<c:if test="${!fn:contains(match.result, '-')}">
						      		${match.result}
						      	</c:if>
						      </td>
						      <td class="team-title"><a href="team?id=${match.away_team_id}" target="_blank"><img src="assets/image/soccer/teams/150x150/${match.away_team_id}.png" style="width:25px;height:25px;"/>&nbsp;${match.away_team_name}</a></td>
						      <td><span style="color:gray;"></span></td>
						    </tr>
						    </c:forEach>
						    
						  </tbody>
						</table>
					</div>
					
					
					<div class="table-responsive visible-sm visible-xs" style="margin-top:10px;">
						<table class="table table-condensed table-hover" style="border-bottom:1px solid #dddddd;">
						  <caption style="text-align:left;"><img title="${group[0].league_name}" src="assets/image/page/league-logo${group[0].league_id}.jpg" style="width:80px;height:80px;"/></caption>
						  <thead>
						    <tr>
						      <th>比赛时间</th>
						      <th>（${group[0].league_name}）</th>
						    </tr>
						  </thead>
						  <tbody>
						  	<c:forEach items="${group}" var="match">
						    <tr>
						      <td><fmt:formatDate value="${match.match_date}" pattern="yy/MM/dd"/></td>
						      <td class="team-title">
						      	<span style="display:block;min-width:106px;float:left;"><a href="team?id=${match.home_team_id}" ><img src="assets/image/soccer/teams/150x150/${match.home_team_id}.png" style="width:25px;height:25px;"/>&nbsp;${match.home_team_name}</a></span>
						      	<span style="display:block;min-width:40px;float:left;">
						      	<c:if test="${fn:contains(match.result, '-')}">
						      		<b>${match.result}</b>
						      	</c:if>
						      	<c:if test="${!fn:contains(match.result, '-')}">
						      		${match.result}
						      	</c:if>
						      	</span>
						        <a href="team?id=${match.away_team_id}" ><img src="assets/image/soccer/teams/150x150/${match.away_team_id}.png" style="width:25px;height:25px;"/>&nbsp;${match.away_team_name}</a>
						      </td>
						    </tr>
						    </c:forEach>
						    
						  </tbody>
						</table>
					</div>
				</c:forEach>
				
			</div>
			<div class="col-lg-3 col-md-3">
				
			</div>
		</div>
	</div>
	
	<%@ include file="/common/footer.jsp"%>		
</body>	

