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
	<meta name="keywords" content="${fun.title},足球趣点,数据分析,新星挖掘,球员评价" />
	<meta name="description" content="${fun.summary}" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/viewer/viewer.min.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
    	
    </style>
    <title>趣点足球网 - ${fun.title}|足球趣点|数据分析|新星挖掘|球员评价</title>
</head>

<body>
<div id="all_content">
	<div class="container">
		<div class="row menu_bg clear_row_margin hidden-sm hidden-xs" >
			<div id="main_nav" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">	
				<div>
					<div class="logo_div">
						<a style="margin-left:12px;">趣点足球网</a>
					</div>
					<ul style="float:left;" class="hidden-sm hidden-xs">
						<li class="menu_width"><a href="">首页</a></li>
						<li class="menu_width menu_sel"><a href="fun.html">趣点</a></li>
						<li class="menu_width"><a href="say.html">说说</a></li>
						<li class="menu_width"><a href="match.html">比赛</a></li>
						<li class="menu_width"><a href="data.html">数据</a></li>
					</ul>
				</div>
			</div>
		</div>
		
		<div class="row menu_link navbar-fixed-top  visible-sm visible-xs" style="background:#00A50D;min-height:35px;color:white;">
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/" target="_self"><span class="wwqk_menu">首页</span></a>
	       	</div>
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/fun.html" target="_self"><span class="wwqk_menu dline">趣点</span></a>
	       	</div>
	       	<div class="col-xs-3 col-sm-3 wwqk_menu_wh" >
	       		<a href="/say.html" target="_self"><span class="wwqk_menu">说说</span></a>
	       	</div>
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/match.html" target="_self"><span class="wwqk_menu">比赛</span></a>
	       	</div>
	       	<div class="col-xs-3 col-sm-3 wwqk_menu_wh" >
	       		<a href="/data.html" target="_self"><span class="wwqk_menu">数据</span></a>
	       	</div>
	    </div>
	    
	    <div class="row visible-sm visible-xs" style="margin-top:45px;">
			<div class="col-sm-12 col-xs-12 bread">
				当前位置：<a href="/" target="_self">首页</a>&nbsp;&gt;&nbsp;<a href="/fun.html" target="_self">趣点</a>&nbsp;&gt;&nbsp;详情
			</div>
		</div>
		
		<!-- 移动端内容开始 -->
		<div class="row visible-sm visible-xs" style="padding-bottom: 130px;">
			<div class="col-sm-12 col-xs-12"><h3 style="font-weight:bold;">${fun.title}</h3></div>
			<div class="col-sm-12 col-xs-12">
				<div class="mob-author">
                           <div class="author-face">
                           <img src="assets/image/page/logo-small.png">
                           </div>
                           <span class="mob-author-a">
                           	<span class="author-name">趣点足球网</span>
                           </span>
                           <span class="author-name">
                           	<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>
                           </span>
                   </div>
			</div>
			<div class="col-sm-12 col-xs-12" style="margin-top:20px;">
				<blockquote style="background-color:#f3f7f0;font-size:14px;">
					<img src="assets/image/page/quote.png">${fun.summary}
				</blockquote>
			</div>
			<div class="col-sm-12 col-xs-12" style="margin-top:10px;font-size:14px;">
				<div class="well well-lg" style="line-height:2;text-indent:20px;padding:10px;">
					${fun.content}
				</div>
			</div>
			<c:if test="${!empty fun.source_name}">
				<div class="col-sm-12 col-xs-12 bread" style="margin-top:10px;font-size:14px;">
					来源：<a href="${fun.source_url}" target="_blank">${fun.source_name}</a>
				</div>
			</c:if>
		</div>
		<!-- 移动端内容结束 -->
		
	</div>
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:70px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-9 col-md-9 col-sm-11 col-xs-11 bread">
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;<a href="/fun.html" target="_blank">趣点</a>&nbsp;&gt;&nbsp;详情
			</div>
		</div>
	</div>
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:20px;padding-bottom: 130px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-9 col-md-9 col-lg-12 col-md-12">
				<div class="col-lg-12 col-md-12"><h2 style="font-weight:bold;">${fun.title}</h2></div>
				<div class="col-lg-12 col-md-12">
					<div class="mob-author">
                            <div class="author-face">
	                           <img src="assets/image/page/logo-small.png">
                            </div>
                            <span class="mob-author-a">
                            	<span class="author-name">趣点足球网</span>
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
	</div>
	
	<script src="assets/global/plugins/viewer/viewer-jquery.min.js" type="text/javascript"></script>
	
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
	
	$(function(){
		$('.well').viewer({toolbar:false});
	});
	</script>
		
</body>	

