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
	<meta name="keywords" content="趣点足球网,<fmt:formatDate value="${say.create_time}" pattern="yyyy-MM-dd HH:mm"/>，${say.player_name}的动态,${say.player_name}的近况,${say.content}" />
	<meta name="description" content="<fmt:formatDate value="${say.create_time}" pattern="yyyy-MM-dd HH:mm"/>，${say.player_name}：${say.content}" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="https://cdn.bootcss.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/viewer/viewer.min.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - ${say.player_name}的近况|<fmt:formatDate value="${say.create_time}" pattern="yyyy-MM-dd HH:mm"/>，${say.player_name}：${say.content}</title>
</head>

<body>

<div id="all_content">
	<div class="container">
		<%@ include file="/common/menu.jsp"%>
	    
	    <div class="row clear_row_margin visible-sm visible-xs" >
			<div id="main_content" style="min-height:10px;" class="col-sm-12 col-xs-12">		
				<div class="bread">
					当前位置：<a href="/" target="_self">首页</a>
						&nbsp;&gt;&nbsp;详情
				</div>
			</div>
			<div class="col-sm-12 col-xs-12" style="margin-top:25px;">
   					<div class="mob-author">
                            <div class="author-face">
		                        <a href="player-${say.player_name_en}-${say.player_id}.html" target="_self"><img src="${say.player_img_local}"></a>
                            </div>
                            
                            <a href="player-${say.player_name_en}-${say.player_id}.html" target="_self" class="mob-author-a">
                                <span class="author-name">${say.player_name}</span>
                            </a>
                            
                            <span class="author-name say-info">
								&nbsp;<fmt:formatDate value="${say.create_time}" pattern="yyyy-MM-dd HH:mm"/>
								&nbsp;<a href="player-${say.player_name_en}-${say.player_id}.html" target="_self" title="${say.player_name}的更多信息" style="color:#bbb;">查看更多</a>
							</span>
                    </div>
	    	</div>
		</div>
		
		<div class="row clear_row_margin visible-sm visible-xs" style="margin-top:5px;padding-bottom: 130px;">
			
			<div class="col-lg-12 col-md-12">
				<span style="color:#292f33;font-size:14px;">${say.content}</span>
			</div>
			<c:if test="${!empty say.image_big}">
				<div class="col-lg-12 col-md-12" style="margin-top:10px;">
					<img src="${say.image_big}" class="img-responsive img-rounded image" alt="${say.content}" title="${say.player_name} - ${say.content}"/>
				</div>
			</c:if>
		</div>
	</div>
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:70px;">
		<div id="main_content" style="min-height:10px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="bread">
				当前位置：<a href="/" target="_blank">首页</a>
					&nbsp;&gt;&nbsp;详情
			</div>
		</div>
	</div>
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:5px;padding-bottom: 130px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
				<div class="col-lg-9 col-md-9" style="border:1px solid #E3E7EA;padding:20px;padding-left:0;padding-bottom:10px;">
						<div class="col-lg-1 col-md-1">
							<a href="player-${say.player_name_en}-${say.player_id}.html" style="color:#292f33;" target="_blank"><img src="${say.player_img_local}" style="width:48px;height:48px;" /></a>
						</div>
						<div class="col-lg-11 col-md-11" >
							<div class="col-lg-12 col-md-12 say-info">
								<span style="font-weight:bold;color:#292f33;"><a href="player-${say.player_name_en}-${say.player_id}.html" style="color:#292f33;" target="_blank">${say.player_name}</a></span>
								<span style="color:#8899a6;font-size:13px;"> - <fmt:formatDate value="${say.create_time}" pattern="MM月dd日"/> </span>
								&nbsp;<a href="player-${say.player_name_en}-${say.player_id}.html" target="_blank" title="${say.player_name}的更多信息">查看更多</a>
							</div>
							<div class="col-lg-12 col-md-12">
								<span style="color:#292f33;font-size:14px;">${say.content}</span>
							</div>
							<c:if test="${!empty say.image_big}">
								<div class="col-lg-12 col-md-12" style="margin-top:10px;">
									<img src="${say.image_big}" class="img-responsive img-rounded image" style="cursor:pointer;" alt="${say.content}" title="${say.player_name} - ${say.content}"/>
								</div>
							</c:if>
						</div>
						<div class="col-lg-11 col-md-11 col-md-offset-1 col-lg-offset-1" style="margin-top:10px;">
							<div class="bdsharebuttonbox" style="margin-left:15px;"><a href="#" class="bds_more" data-cmd="more">分享到：</a><a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博">新浪微博</a><a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间">QQ空间</a><a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信">微信</a><a href="#" class="bds_tieba" data-cmd="tieba" title="分享到百度贴吧">百度贴吧</a><a href="#" class="bds_ty" data-cmd="ty" title="分享到天涯社区">天涯社区</a></div>
<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"16"},"share":{"bdSize":16}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
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
		$('.image').viewer(
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

