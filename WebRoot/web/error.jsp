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
	<meta name="keywords" content="趣点足球网,球员说说,球星资讯,球员动态,球星生活" />
	<meta name="description" content="趣点足球网为球迷们提供最新的球员生活、球星生活动态，球员资讯" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - 足球说说|球员说说|球员动态|球员资讯|球星生活</title>
</head>

<body>
<div id="all_content">
	<div class="container">
		<div class="row menu_bg clear_row_margin hidden-sm hidden-xs" >
			<div id="main_nav" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2">	
				<div>
					<div class="logo_div">
						<a href="" title="首页">趣点足球网</a>
					</div>
					<ul style="float:left;">
						<li class="menu_width"><a href="">首页</a></li>
						<li class="menu_width"><a href="fun.html">趣点</a></li>
						<li class="menu_width"><a href="live.html">直播</a></li>
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
	       	<div class="col-xs-3 col-sm-3 wwqk_menu_wh" >
	       		<a href="/fun.html" target="_self"><span class="wwqk_menu">趣点</span></a>
	       	</div>
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/live.html" target="_self"><span class="wwqk_menu">直播</span></a>
	       	</div>
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/match.html" target="_self"><span class="wwqk_menu">比赛</span></a>
	       	</div>
	       	<div class="col-xs-3 col-sm-3 wwqk_menu_wh" >
	       		<a href="/data.html" target="_self"><span class="wwqk_menu">数据</span></a>
	       	</div>
	    </div>
	    
	    <!-- 移动端开始 -->
	    <div id="list_content" class="row visible-sm visible-xs" style="margin-top:45px;padding-bottom: 130px;">
	    	<div class="col-lg-12 col-md-12" style="margin-top:30px;">
				<span style="font-size:18px;"><img src="assets/image/page/cry.png" />对不起，服务器出小差了！</span>
			</div>
			<div class="col-lg-12 col-md-12" style="font-size:14px;margin-top:10px;">
				<div onclick="javascript:history.go(-1);">返回到之前浏览的页面</div>
			</div>
			<div class="col-lg-12 col-md-12" style="font-size:14px;margin-top:10px;">
				<b>或者：</b><a href="/" target="_self" style="color:grey;">返回主页</a>
			</div>
	    </div>
	    <!-- 移动端结束 -->
	</div>
	
	
	<!-- PC端开始 -->
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:10px;padding-bottom: 130px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2">		
				<div class="row" style="margin-top:100px;">
					<div class="col-lg-12 col-md-12">
						<h1><img src="assets/image/page/cry.png" />对不起，服务器开小差了！</h1>
					</div>
					<div class="col-lg-12 col-md-12" style="font-size:16px;margin-top:10px;">
						<div onclick="javascript:history.go(-1);" style="cursor: pointer;width:200px;">返回到之前浏览的页面</div>
					</div>
					<div class="col-lg-12 col-md-12" style="font-size:16px;margin-top:10px;">
						<b>或者：</b><a href="/" target="_self" style="color:grey;">返回主页</a>
					</div>
				</div>
		</div>
	</div>
	<!-- PC端结束 -->
	
	
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

