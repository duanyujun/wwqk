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
	<meta name="keywords" content="足球比赛,比赛时间,比赛结果,巴萨比赛,皇马比赛,曼联比赛,阿森纳比赛,曼城比赛,利物浦比赛,切尔西比赛,拜仁比赛,尤文图斯比赛,巴黎圣日耳曼比赛" />
	<meta name="description" content="趣点足球网为球迷们提供五大联赛最新的足球比赛预告，比赛结果。获取免费足球直播，上趣点足球网。" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - 足球比赛|比赛时间|比赛结果|巴萨|皇马|曼联|阿森纳|曼城|利物浦|切尔西|拜仁|尤文图斯|巴黎圣日耳曼</title>
</head>

<body>
<div id="all_content">
	<div class="row menu_bg clear_row_margin" >
		<div id="main_nav" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">	
			<div>
				<div class="logo_div">
					<a href="" title="首页">趣点足球网</a>
				</div>
				<ul style="float:left;" class="hidden-sm hidden-xs">
					<li class="menu_width"><a href="">首页</a></li>
					<li class="menu_width"><a href="fun.html">趣点</a></li>
					<li class="menu_width"><a href="say.html">说说</a></li>
					<li class="menu_width menu_sel"><a href="match.html">比赛</a></li>
					<li class="menu_width"><a href="data.html">数据</a></li>
				</ul>
				<div class="visible-sm visible-xs small-menu">
					<select id="menuSelect" class="form-control small-select">
						<option value="">首页</option>
						<option value="fun.html">趣点</option>
						<option value="say.html">说说</option>
						<option selected value="match.html">比赛</option>
						<option value="data.html">数据</option>
					</select>	
				</div>
			</div>
		</div>
	</div>
	
	
	<div class="row clear_row_margin" style="margin-top:70px;">
		<div id="main_content" style="min-height:10px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="bread">
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;<a href="/match.html" target="_blank">比赛</a>&gt;&nbsp;${area}
			</div>
		</div>
	</div>
	
	<div class="row clear_row_margin" style="margin-top:1px;padding-bottom: 130px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-10 col-lg-offset-2 col-md-10 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-9 col-md-9" style="padding-left:0px;padding-right:0px;">
				<div class="table-responsive hidden-sm hidden-xs" style="margin-top:10px;">
						<table class="table table-condensed table-hover" style="border-bottom:1px solid #dddddd;">
						  <thead>
						    <tr>
						      <th style="width:160px;">比赛时间</th>
						      <th style="width:50px;"></th>
						      <th style="width:150px;"></th>
						      <th style="width:150px;"></th>
						      <th style="width:150px;"></th>
						      <th style="width:40px;"></th>	
						    </tr>
						  </thead>
						  <tbody>
						  	<c:forEach items="${matchPage.list}" var="match">
						    <tr>
						      <td><fmt:formatDate value="${match.match_date}" pattern="yyyy-MM-dd"/> &nbsp;&nbsp;<b>${match.match_weekday}</b>&nbsp;&nbsp;&nbsp;<span style="color:#888;">第${match.round}轮</span></td>
						      <td>${match.league_name}</td>
						      <td class="a-title"><a href="team-${match.home_team_en_name}-${match.home_team_id}.html" target="_blank"><img src="assets/image/soccer/teams/25x25/${match.home_team_id}.png" style="width:25px;height:25px;" alt="${match.home_team_name}" title="${match.home_team_name}"/>&nbsp;<span class="${match.home_team_name==area?'red_result':''}">${match.home_team_name}</span></a></td>
						      <td>
						      	<c:if test="${fn:contains(match.result, '-')}">
						      		<b>${match.result}</b>
						      	</c:if>
						      	<c:if test="${!fn:contains(match.result, '-')}">
						      		${match.result}
						      	</c:if>
						      </td>
						      <td class="a-title"><a href="team-${match.away_team_en_name}-${match.away_team_id}.html" target="_blank"><img src="assets/image/soccer/teams/25x25/${match.away_team_id}.png" style="width:25px;height:25px;" alt="${match.away_team_name}" title="${match.away_team_name}"/>&nbsp;<span class="${match.away_team_name==area?'red_result':''}">${match.away_team_name}</span></a></td>
						      <td>
						      		<c:if test="${match.status=='完场'}">
							      		<span class="grey-title"><a href="match-${match.home_team_en_name}-vs-${match.away_team_en_name}_<fmt:formatDate value="${match.match_date}" pattern="yyyy-MM-dd"/>-${match.home_team_id}vs${match.away_team_id}.html" target="_blank">集锦</a></span>
							      	</c:if>
							      	<c:if test="${match.status!='完场'}">
							      		<b class="a-title"><a href="match-${match.home_team_en_name}-vs-${match.away_team_en_name}_<fmt:formatDate value="${match.match_date}" pattern="yyyy-MM-dd"/>-${match.home_team_id}vs${match.away_team_id}.html" target="_blank">直播</a></b>
							      	</c:if>
						      </td>
						    </tr>
						    </c:forEach>
						    
						  </tbody>
						</table>
					</div>
					
					<div class="table-responsive visible-sm visible-xs" style="margin-top:10px;">
						<table class="table table-condensed table-hover" style="border-bottom:1px solid #dddddd;">
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
						      	<span style="display:block;min-width:106px;float:left;"><a href="team?id=${match.home_team_id}" ><img src="assets/image/soccer/teams/25x25/${match.home_team_id}.png" style="width:25px;height:25px;"/>&nbsp;${match.home_team_name}</a></span>
						      	<span style="display:block;min-width:40px;float:left;">
						      	<c:if test="${fn:contains(match.result, '-')}">
						      		<b>${match.result}</b>
						      	</c:if>
						      	<c:if test="${!fn:contains(match.result, '-')}">
						      		${match.result}
						      	</c:if>
						      	</span>
						        <a href="team?id=${match.away_team_id}" ><img src="assets/image/soccer/teams/25x25/${match.away_team_id}.png" style="width:25px;height:25px;"/>&nbsp;${match.away_team_name}</a>
						      </td>
						    </tr>
						    </c:forEach>
						    
						  </tbody>
						</table>
					</div>
					
					<div class="col-lg-9 col-md-9" style="margin-top:20px;padding-right:0px;">
						<div class="scott pull-right" >
							<a href="/history<c:if test="${!empty filter}">-${filter}</c:if>-page-1.html" title="首页"> &lt;&lt; </a>
							
							<c:if test="${matchPage.pageNumber == 1}">
								<span class="disabled"> &lt; </span>
							</c:if>
							<c:if test="${matchPage.pageNumber != 1}">
								<a href="/history<c:if test="${!empty filter}">-${filter}</c:if>-page-${matchPage.pageNumber - 1}.html" > &lt; </a>
							</c:if>
							<c:if test="${matchPage.pageNumber > 8}">
								<a href="/history<c:if test="${!empty filter}">-${filter}</c:if>-page-1.html">1</a>
								<a href="/history<c:if test="${!empty filter}">-${filter}</c:if>-page-2.html">2</a>
								...
							</c:if>
							<c:if test="${!empty pageUI.list}">
								<c:forEach items="${pageUI.list}" var="pageNo">
									<c:if test="${matchPage.pageNumber == pageNo }">
										<span class="current">${pageNo}</span>
									</c:if>
									<c:if test="${matchPage.pageNumber != pageNo }">
										<a href="/history<c:if test="${!empty filter}">-${filter}</c:if>-page-${pageNo}.html">${pageNo}</a>
									</c:if>
								</c:forEach>
							</c:if>
							<c:if test="${(matchPage.totalPage - matchPage.pageNumber) >= 8 }">
								...
								<a href="/history<c:if test="${!empty filter}">-${filter}</c:if>-page-${matchPage.totalPage - 1}.html">${matchPage.totalPage - 1}</a>
								<a href="/history<c:if test="${!empty filter}">-${filter}</c:if>-page-${matchPage.totalPage}.html">${matchPage.totalPage}</a>
							</c:if>
							
							<c:if test="${matchPage.pageNumber == matchPage.totalPage}">
								<span class="disabled"> &gt; </span>
							</c:if>
							<c:if test="${matchPage.pageNumber != matchPage.totalPage}">
								<a href="/history<c:if test="${!empty filter}">-${filter}</c:if>-page-${matchPage.pageNumber + 1}.html"> &gt; </a>
							</c:if>
							
							<a href="/history<c:if test="${!empty filter}">-${filter}</c:if>-page-${matchPage.totalPage}.html" title="尾页" > &gt;&gt; </a>
						</div>
					</div>
					
			</div>
			<div class="col-lg-3 col-md-3">
				
			</div>
		</div>
	</div>
	
	<%@ include file="/common/footer.jsp"%>		
	</div>
	<script>
	(function(){
	    var bp = document.createElement('script');
	    var curProtocol = window.location.protocol.split(':')[0];
	    if (curProtocol === 'https') {
	        bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';        
	    }
	    else {
	        bp.src = 'http://push.zhanzhang.baidu.com/push.js';
	    }
	    var s = document.getElementsByTagName("script")[0];
	    s.parentNode.insertBefore(bp, s);
	})();
	</script>
		
</body>	

