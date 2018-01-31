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
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/viewer/viewer.min.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
    	
    </style>
    <title>趣点足球网 - ${fun.title}|足球趣点|数据分析|新星挖掘|球员评价</title>
</head>

<body>
<div id="all_content">
	<div class="container">
		<%@ include file="/common/menu.jsp"%>
	    
	    <div class="row visible-sm visible-xs" style="margin-top:45px;">
			<div class="col-sm-12 col-xs-12 bread">
				当前位置：<a href="/" target="_self">首页</a>&nbsp;&gt;&nbsp;详情
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
				<div class="well well-lg" style="line-height:2;text-indent:20px;padding:10px;background:#fdfdfd;">
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
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;<a href="/say.html" target="_blank">说说</a>&nbsp;&gt;&nbsp;详情
			</div>
		</div>
	</div>
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="padding-bottom: 130px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-9 col-lg-offset-2 col-md-9 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-10 col-md-10 col-lg-12 col-md-12">
				<div class="row">
					<div class="col-lg-12 col-md-12"><h3 style="font-weight:bold;">${fun.title}</h3></div>
					<div class="col-lg-12 col-md-12">
						<div class="mob-author">
	                            <span class="mob-author-a" style="margin-left:0px;">
	                            	<span class="author-name">趣点足球网</span>
	                            </span>
	                            <span class="author-name">
	                            	<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>
	                            </span>
	                            <div class="bdsharebuttonbox" style="margin-top:6px;"><a href="#" class="bds_more" data-cmd="more">分享到：</a><a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博">新浪微博</a><a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间">QQ空间</a><a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信">微信</a><a href="#" class="bds_tieba" data-cmd="tieba" title="分享到百度贴吧">百度贴吧</a><a href="#" class="bds_ty" data-cmd="ty" title="分享到天涯社区">天涯社区</a></div>
	<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"16"},"share":{"bdSize":16}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
	                    </div>
					</div>
				
					<div class="col-lg-12 col-md-12" style="margin-top:20px;">
						<blockquote style="background-color:#f3f7f0;font-size:16px;">
							<img src="assets/image/page/quote.png">${fun.summary}
						</blockquote>
					</div>
					
					<div class="col-lg-12 col-md-12" style="margin-top:10px;font-size:14px;">
						<div class="well well-lg" style="line-height:2;text-indent:20px;">
							${fun.content}
						</div>
					</div>
				
					<c:if test="${!empty fun.source_name}">
						<div class="col-lg-12 col-md-12 bread" style="font-size:14px;">
							来源：<a href="${fun.source_url}" target="_blank">${fun.source_name}</a>
						</div>
					</c:if>
				</div>
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
		$('.well').viewer(
				{toolbar:false, 
				zIndex:20000,
				shown: function() {
					$(".viewer-canvas").attr("data-action","mix");
				 }
			 }
		);
	});
	</script>
		
</body>	

