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
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;<a href="/say" target="_blank">说说</a>&nbsp;&gt;&nbsp;${player.name}
			</div>
		</div>
	</div>
	
	<div class="row clear_row_margin" style="margin-top:20px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-9 col-md-9">
				<div class="col-lg-12 col-md-12" >
					<div class="col-lg-3 col-md-3">
						<img src="${player.img_big_local}" style="width:150px;height:150px;" />
					</div>
					<div class="col-lg-9 col-md-9">
						<div class="col-lg-6 col-md-6" style="margin-top:10px;">名字：${player.first_name}</div>
						<div class="col-lg-6 col-md-6" style="margin-top:10px;">姓氏：${player.last_name}</div>
						<div class="col-lg-6 col-md-6" style="margin-top:10px;">生日：${player.birthday}</div>
						<div class="col-lg-6 col-md-6" style="margin-top:10px;">国籍：${player.nationality}</div>
						<div class="col-lg-6 col-md-6" style="margin-top:10px;">身高：${player.height}</div>
						<div class="col-lg-6 col-md-6" style="margin-top:10px;">体重：${player.weight}</div>
						<div class="col-lg-6 col-md-6" style="margin-top:10px;">位置：${player.position}</div>
						<div class="col-lg-6 col-md-6" style="margin-top:10px;">用脚：${player.foot}</div>
						<div class="col-lg-6 team-title" style="margin-top:10px;font-size:14px;">目前效力球队：
							<a href="team?id=${player.team_id}" target="_blank" title="${player.team_name}"><img src="assets/image/soccer/teams/150x150/${player.team_id}.png" style="width:25px;height:25px;"/>&nbsp;${player.team_name}</a>
						</div>
						<div class="col-lg-6 col-md-6" style="margin-top:10px;">
							<c:if test="${player.goal_count!=0}">
				      			<span title="进球数：${player.goal_count}"><img src="assets/pages/img/goal-small.png" style="margin-top:-5px;" /> <b>${player.goal_count}</b></span>
				      		</c:if>
				      		<c:if test="${player.goal_count!=0 && player.assists_count!=0}">
				      		&nbsp;
				      		</c:if>
				      		<c:if test="${player.assists_count!=0}">
				      			<span title="助攻数：${player.assists_count}"><img src="assets/pages/img/goal-assists.png" style="margin-top:-5px;" /> <b>${player.assists_count}</b></span>
				      		</c:if>
				      		<c:if test="${player.goal_count!=0 || player.assists_count!=0}">
				      		&nbsp;
				      		</c:if>
				      		<c:if test="${!empty player.number}">
					      		<span title="球衣：${player.number}号"><img src="assets/pages/img/cloth.png" style="margin-top:-3px;" /> ${player.number}号</span>
				      		</c:if>
						</div>
					</div>
				</div>
			</div>
			
			<div class="col-lg-9 col-md-9" style="margin-top:15px;">
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
				
				<c:if test="${sayPage.totalPage>1}">
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
				</c:if>
				
			</div>
		</div>
	</div>
	
	<%@ include file="/common/footer.jsp"%>		
</body>	

