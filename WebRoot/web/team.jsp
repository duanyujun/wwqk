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
				<div>
					<div class="logo_div">
						<a href="" title="首页">趣点足球网</a>
					</div>
					<ul style="float:left;" class="hidden-sm hidden-xs">
						<li class="menu_width"><a href="">首页</a></li>
						<li class="menu_width"><a href="fun">趣点</a></li>
						<li class="menu_width"><a href="say">说说</a></li>
						<li class="menu_width"><a href="match">比赛</a></li>
						<li class="menu_width menu_sel"><a href="data">数据</a></li>
					</ul>
					<div class="visible-sm visible-xs small-menu">
						<select id="menuSelect" class="form-control small-select">
							<option value="">首页</option>
							<option value="fun">趣点</option>
							<option value="say">说说</option>
							<option value="match">比赛</option>
							<option selected value="data">数据</option>
						</select>	
					</div>
				</div>
			</div>
		</div>
		
		
		
		<div class="row clear_row_margin" style="margin-top:70px;">
			<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
				
				<div class="row">
					<div class="col-lg-4 col-md-4 hidden-sm hidden-xs">
						<div class="col-lg-12 col-md-12">						
							<img src="assets/image/soccer/teams/150x150/${team.id}.png"  style="width:150px;height:150px;"/>
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:10px;">
							<span style="font-size:24px;font-weight:bold;">${team.name}</span>
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:10px;">
							成立时间：${team.setup_time}
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:10px;">
							地址：${team.address}
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:10px;">
							国家：${team.country}
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:10px;">
							电话：${team.telphone}
						</div>
					</div>
					<div class="col-sm-12 col-xs-12 visible-sm visible-xs">
						<div class="row">
							<div class="col-sm-6 col-xs-6">						
								<img src="assets/image/soccer/teams/150x150/${team.id}.png"  style="width:150px;height:150px;"/>
							</div>
							<div class="col-sm-6 col-xs-6" style="margin-top:10px;">
								<span style="font-size:18px;font-weight:bold;">${team.name}</span>
							</div>
							<div class="col-sm-6 col-xs-6" style="margin-top:10px;">
								成立时间：${team.setup_time}年
							</div>
							<div class="col-sm-6 col-xs-6" style="margin-top:10px;">
								国家：${team.country}
							</div>
						</div>
						<div class="row" style="margin-top:10px;">
							<div class="col-sm-12 col-xs-12">
								地址：${team.address}
							</div>
						</div>
						<div class="row" style="margin-top:10px;">
							<div class="col-sm-12 col-xs-12">
									电话：${team.telphone}
							</div>
						</div>
					</div>
					
					<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
						<div class="row" style="margin-top:10px;">
							<div class="col-lg-12 col-md-12">
								<img src="${team.venue_small_img_local}" class="img-responsive img-rounded" style="width:300px;height:225px;"/>
							</div>
							<div class="col-lg-12 col-md-12" style="margin-top:10px;">
								球场名称：${team.venue_name}
							</div>
							<div class="col-lg-12 col-md-12" style="margin-top:10px;">
								所在城市：${team.venue_address}
							</div>
							<div class="col-lg-12 col-md-12" style="margin-top:10px;">
								观众容量：${team.venue_capacity}
							</div>
						</div>
					</div>
				</div>
				
				<c:forEach items="${lstGroup}" var="group">
					<div class="row hidden-sm hidden-xs" style="margin-top:20px;">
						<div class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
							<table class="table" >
							  <caption style="min-height:30px;text-align:left;"><b style="margin-left:15px;">${group[0].position}</b></caption>
							  <tbody >
							  	<c:set var="i" value="1"/>
							  	<tr style="border-top:1px solid #dddddd; ${2==group.size()?'border-bottom:1px solid #dddddd;':''}">
								<c:forEach items="${group}" var="player">
									<td style="width:50px;border:none;"><img src="${player.img_small_local}" title="${player.name}" /></td>
							      	<td colspan="${i==group.size()?3:1}" class="team-title" style="border:none;width:250px;font-size:13px;">
							      		<p>
							      		<a href="say/list?id=${player.id}" target="_blank">${player.name}</a>&nbsp;&nbsp;
							      		<c:if test="${!empty player.number}">
								      		<nobr><span title="球衣：${player.number}号"><img src="assets/pages/img/cloth.png" style="margin-top:-3px;" /> ${player.number}号</span></nobr>
							      		</c:if>
							      		</p>
							      		<p style="line-height:20px;height:20px;">
							      		${player.age}岁 &nbsp;
							      		<c:if test="${player.goal_count!=0}">
							      			<nobr><span title="进球数：${player.goal_count}"><img src="assets/pages/img/goal-small.png" style="margin-top:-5px;" /> <b>${player.goal_count}</b></span></nobr>
							      		</c:if>
							      		<c:if test="${player.goal_count!=0 && player.assists_count!=0}">
							      		&nbsp;
							      		</c:if>
							      		<c:if test="${player.assists_count!=0}">
							      			<nobr><span title="助攻数：${player.assists_count}"><img src="assets/pages/img/goal-assists.png" style="margin-top:-5px;" /> <b>${player.assists_count}</b></span></nobr>
							      		</c:if>
							      		</p>
							      	</td>
							      	<c:if test="${i%2==0}">
									</tr>
									<tr style="${i+2 ge group.size()?'border-bottom:1px solid #dddddd;':''};width:250px;">
									</c:if>
									<c:set var="i" value="${i+1}"></c:set>
								</c:forEach>
								</tr>
								
							  </tbody>
							</table>
						</div>
					</div>
					
					
					<div class="row visible-sm visible-xs" style="margin-top:20px;">
						<div class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
							<table class="table small-table" >
							  <caption style="min-height:30px;text-align:left;"><b style="margin-left:15px;">${group[0].position}</b></caption>
							  <tbody >
							  	<c:set var="i" value="1"/>
							  	<tr style="border-top:1px solid #dddddd; ${2==group.size()?'border-bottom:1px solid #dddddd;':''}">
								<c:forEach items="${group}" var="player">
									<td style="width:50px;border:none;"><img src="${player.img_small_local}" title="${player.name}" /></td>
							      	<td colspan="${i==group.size()?3:1}" class="team-title" style="border:none;width:250px;font-size:13px;">
							      		<p>
							      		<a href="say/list?id=${player.id}" target="_blank">${player.name}</a>
							      		<c:if test="${!empty player.number}">
								      		<nobr><span title="球衣：${player.number}号"><img src="assets/pages/img/cloth.png" style="margin-top:-3px;" /> ${player.number}号</span></nobr>
							      		</c:if>
							      		</p>
							      		<p style="line-height:20px;height:20px;margin-top:-7px;">
							      		${player.age}岁 
							      		<c:if test="${player.goal_count!=0}">
							      			<nobr><span title="进球数：${player.goal_count}"><img src="assets/pages/img/goal-small.png" style="margin-top:-5px;" /> <b>${player.goal_count}</b></span></nobr>
							      		</c:if>
							      		<c:if test="${player.assists_count!=0}">
							      			<nobr><span title="助攻数：${player.assists_count}"><img src="assets/pages/img/goal-assists.png" style="margin-top:-5px;" /> <b>${player.assists_count}</b></span></nobr>
							      		</c:if>
							      		</p>
							      	</td>
							      	<c:if test="${i%2==0}">
									</tr>
									<tr style="${i+2 ge group.size()?'border-bottom:1px solid #dddddd;':''};width:250px;">
									</c:if>
									<c:set var="i" value="${i+1}"></c:set>
								</c:forEach>
								</tr>
								
							  </tbody>
							</table>
						</div>
					</div>
				</c:forEach>
				
			</div>
		</div>
		
	<%@ include file="/common/footer.jsp"%>		
</body>	

