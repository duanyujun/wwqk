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
	<meta name="keywords" content="${videos.keywords}" />
	<meta name="description" content="${videos.description}" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="https://cdn.bootcss.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <link href="common/videos.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - ${videos.match_title}</title>
 	<style type="text/css">
 		
 	</style>
</head>

<body>

<div id="all_content">	
	<div class="container">
		
		<%@ include file="/common/menu.jsp"%>
		
	    <!-- 移动端内容开始 -->
	    <div class="row visible-sm visible-xs" style="margin-top:34px;padding-bottom: 130px;">
	    	<div class="col-xs-12 col-sm-12" style="padding:0;padding-bottom:10px;">
				 <iframe name="mytplayer" id="mytplayer" frameborder="0" width="100%" height="" marginheight="0" marginwidth="0" scrolling="no" src=""></iframe>
	    	</div>
	    	
	    	<c:forEach items="${lstLinks}" var="link" varStatus="status">
		    	<div class="col-xs-12 col-sm-12" >
		    		<c:if test="${link.player_type=='2'}">
		    			<a href="${link.m_real_url}" title="${link.title}" target="mytplayer">${link.title}</a>
		    		</c:if>
		    		<c:if test="${link.player_type!='2'}">
		    			<a href="/videos/mplay?id=${link.id}" title="${link.title}" target="mytplayer">${link.title}</a>
		    		</c:if>
				</div>
			</c:forEach>
	    </div>
	    <!-- 移动端内容结束 -->
	</div>
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:70px;padding-bottom: 130px;">
		<div id="main_content" style="min-height:10px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2">		
			<div class="bread">
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;视频&nbsp;&gt;&nbsp;${videos.match_title}
			</div>
			
			<div class="yt_play clearfix" style="margin-top:10px;margin-left:0px;">
			  <div class="player">
			    <h4><b>正在播放：</b><span id="playingSpan">${lstLinks[0].title}</span></h4> 
							    <div class="play"><iframe name="ytplayer" id="ytplayer" frameborder="0" width="650" height="488" marginheight="0" marginwidth="0" scrolling="no" src=""></iframe></div>
							    <div class="tags">
							    	<div class="bdsharebuttonbox"><a href="#" class="bds_more" data-cmd="more">分享到：</a><a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博">新浪微博</a><a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间">QQ空间</a><a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信">微信</a><a href="#" class="bds_tieba" data-cmd="tieba" title="分享到百度贴吧">百度贴吧</a><a href="#" class="bds_ty" data-cmd="ty" title="分享到天涯社区">天涯社区</a></div>
<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"16"},"share":{"bdSize":16}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
			    </div>
			  </div>
			  <div class="right">
			    <div></div>
			    
			    <div class="content" id="tab_content">
			      <div class="list" >
			      	<ul>
			      		<c:forEach items="${lstLinks}" var="link" varStatus="status">
			      			<li onclick="chooseVideo(this);" class="${status.index==0?'f':''}"><a href="/videos/play?id=${link.id}" title="${link.title}" target="ytplayer">${link.title}</a></li>
			      		</c:forEach>
			      	</ul>
			      </div>
			    </div>
			    <div></div>
			  </div>
			  <div class="clear"></div>
			</div>
			<div style="max-width:948px;">
				${videos.description}
				<c:if test="${(!empty videos.summary) && videos.summary!='0'}">
					<br>
					${videos.summary}
				</c:if>
			</div>
			
		</div>
	</div>
	
	
	
	<%@ include file="/common/footer.jsp"%>		
	</div>
	<script>
	
	var videoUrl = "/videos/play?id=${lstLinks[0].id}";
	var mvideoUrl = "/videos/mplay?id=${lstLinks[0].id}";
	var mplayerType = '${lstLinks[0].player_type}';
	var mpptvUrl = "${lstLinks[0].m_real_url}";
	$(function(){
		var winWidth = $(window).width();
		var playerHeight = (winWidth * 2)/3;
		$("#mytplayer").attr("height", playerHeight);
		 setIframeContent();
	});
	
	
	window.onresize = function(){
		/**
		var winWidth = $(window).width();
		if (winWidth<992) {
			$("#ytplayer").attr("src","");
			$("#mytplayer").attr("src",mvideoUrl);
		}else{
			$("#mytplayer").attr("src","");
		    $("#ytplayer").attr("src",videoUrl);
		}
		*/
	};
	
	
	function isFullscreen(){
		var isFullscreen = false;
		if(document.body.scrollHeight==window.screen.height&&document.body.scrollWidth==window.screen.width){ 
			isFullscreen = true;
		} 
		return isFullscreen;
	}
	
	function setIframeContent(){
		  if(!(navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
			  $("#mytplayer").attr("src","");
			  $("#ytplayer").attr("src",videoUrl);
		  }else{
			  $("#ytplayer").attr("src","");
			  if(mplayerType=='2'){
				  $("#mytplayer").attr("src",mpptvUrl);
			  }else{
				  $("#mytplayer").attr("src",mvideoUrl);
			  }
			  
		  }
	}
	
	function chooseVideo(obj){
		$(obj).siblings().removeClass("f");
		$(obj).addClass("f");
		$("#playingSpan").html($(obj).text());
	}
	
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

