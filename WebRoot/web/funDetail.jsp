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
	
	<div class="row clear_row_margin" style="margin-top:70px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-9 col-md-9 bread">
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;<a href="/fun" target="_blank">趣点</a>&nbsp;&gt;&nbsp;明细
			</div>
		</div>
	</div>
	
	<div class="row clear_row_margin" style="margin-top:20px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-9 col-md-9">
				<div class="col-lg-12 col-md-12"><h2 style="font-weight:bold;">${fun.title}</h2></div>
				<div class="col-lg-12 col-md-12">
					<div class="mob-author">
                            <div class="author-face">
	                           <img src="assets/image/page/logo-small.png">
                            </div>
                            <span class="mob-author-a">
                            	<span class="author-name">趣点网</span>
                            </span>
                            <span class="author-name">
                            	<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>
                            </span>
                    </div>
				</div>
				
				<div class="col-lg-12 col-md-12" style="margin-top:20px;">
					<blockquote style="background-color:#f3f7f0;font-size:16px;">
						<img src="assets/image/page/quote.png">${fun.summary}
					</blockquote>
				</div>
				
				<div class="col-lg-12 col-md-12" style="margin-top:10px;font-size:16px;">
					<div class="well well-lg" style="line-height:2;text-indent:20px;">
						${fun.content}
					</div>
				</div>
				
				<c:if test="${!empty fun.source_name}">
					<div class="col-lg-12 col-md-12 bread" style="margin-top:10px;font-size:14px;">
						来源：<a href="${fun.source_url}" target="_blank">${fun.source_name}</a>
					</div>
				</c:if>
				
			</div>
		</div>
	</div>
	
	<%@ include file="/common/footer.jsp"%>		
</body>	

