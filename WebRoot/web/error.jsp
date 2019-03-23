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
	<meta name="keywords" content="趣点足球网,在线直播链接,球员动态,视频集锦,球队排名" />
	<meta name="description" content="趣点足球网为球迷们提供最新的球员资讯，直播链接以及足球数据" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="https://cdn.bootcss.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="/common/new/main.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - 足球趣闻|球员动态|直播预告|视频集锦|足球数据</title>
</head>

<body>
<!-- header start -->
<%@ include file="/common/new/menu.jsp"%>

<div class="visible-sm visible-xs" style="width:100%;float:left;">
	<!-- 移动端开始 -->
	    <div id="list_content" class="row visible-sm visible-xs" style="margin-top:45px;padding-bottom: 130px;">
	    	<div class="col-lg-12 col-md-12" style="margin-top:30px;">
				<span style="font-size:18px;"><img src="assets/image/page/cry.png" />对不起，服务器出小差了！</span>
			</div>
			
	    </div>
	    <!-- 移动端结束 -->
</div>


<!-- pc content start -->
<div class="main hidden-sm hidden-xs" >
	<div class="left_w930" style="height:min-height:900px;">
		<!-- PC端开始 -->
			<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:10px;padding-bottom: 300px;">
				<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2">		
						<div class="row" style="margin-top:100px;">
							<div class="col-lg-12 col-md-12">
								<h1><img src="assets/image/page/cry.png" />对不起，服务器开小差了！</h1>
							</div>
							
						</div>
				</div>
			</div>
			<!-- PC端结束 -->
			
	</div>
</div>

<!-- pc content end -->
<%@ include file="/common/new/index-mobile.jsp"%>	

<!-- footer start -->
<%@ include file="/common/new/footer.jsp"%>	

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