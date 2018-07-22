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
	<meta name="keywords" content="${fun.keyword},${fun.title},<c:if test="${empty fun.keyword}">足球趣点,数据分析,新星挖掘,球员评价</c:if>" />
	<meta name="description" content="${fun.summary}" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="https://cdn.bootcss.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="/common/new/main.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/viewer/viewer.min.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - ${fun.title}|足球趣点|足坛动态|数据分析|新星挖掘|球员评价</title>
    
</head>

<body>
<!-- header start -->
<%@ include file="/common/new/menu.jsp"%>

<!-- pc content start -->
<div class="main hidden-sm hidden-xs">
	<div class="left_w930">
		<div class="bread left">
			当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;趣点
		</div>
		<div class="w100left" >
			<div class="w100left h45"><h3 class="bold mt10">${fun.title}</h3></div>
			<div class="w100left">
				<div class="mob-author">
                           <span class="mob-author-a ml0" >
                           	<span class="author-name">趣点足球网</span>
                           </span>
                           <span class="author-name">
                           	<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>
                           </span>
                           <div class="bdsharebuttonbox mt6" ><a href="#" class="bds_more" data-cmd="more">分享到：</a><a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博">新浪微博</a><a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间">QQ空间</a><a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信">微信</a><a href="#" class="bds_tieba" data-cmd="tieba" title="分享到百度贴吧">百度贴吧</a><a href="#" class="bds_ty" data-cmd="ty" title="分享到天涯社区">天涯社区</a></div>
<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"16"},"share":{"bdSize":16}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
                   </div>
			</div>
		
			<div class="w100left mt20 tleft">
				<blockquote class="fun-quote">
					<img src="assets/image/page/quote.png">${fun.summary}
				</blockquote>
			</div>
			
			<div class="w100left mt10 fs14 tleft">
				<div class="well well-lg fun-content">
					${fun.content}
				</div>
			</div>
		
			<c:if test="${!empty fun.source_name}">
				<div class="bread w100left fs14 tleft">
					来源：<a href="${fun.source_url}" target="_blank">${fun.source_name}</a>
				</div>
			</c:if>
		</div>
		
	</div>
</div>

<!-- pc content end -->
<%@ include file="/common/new/fun-detail-mobile.jsp"%>	

<!-- footer start -->
<%@ include file="/common/new/footer.jsp"%>	

<script src="assets/global/plugins/viewer/viewer-jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		$('.well').viewer(
				{toolbar:false, 
				zIndex:20000,
				shown: function() {
					$(".viewer-canvas").attr("data-action","mix");
				 }
			 }
		);
	});
	
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
</html>
