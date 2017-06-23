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
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/viewer/viewer.min.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - ${say.player_name}的近况|<fmt:formatDate value="${say.create_time}" pattern="yyyy-MM-dd HH:mm"/>，${say.player_name}：${say.content}</title>
</head>

<body>

<div id="all_content">
	<div class="container">
		<div class="row menu_bg clear_row_margin hidden-sm hidden-xs" >
			<div id="main_nav" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">	
				<div>
					<div class="logo_div">
						<a href="" title="首页">趣点足球网</a>
					</div>
					<ul style="float:left;" >
						<li class="menu_width"><a href="">首页</a></li>
						<li class="menu_width"><a href="fun.html">趣点</a></li>
						<li class="menu_width menu_sel"><a href="say.html">说说</a></li>
						<li class="menu_width"><a href="match.html">比赛</a></li>
						<li class="menu_width"><a href="data.html">数据</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="row menu_link navbar-fixed-top visible-sm visible-xs" style="background:#00A50D;min-height:35px;color:white;">
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/" target="_self"><span class="wwqk_menu">首页</span></a>
	       	</div>
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/fun.html" target="_self"><span class="wwqk_menu">趣点</span></a>
	       	</div>
	       	<div class="col-xs-3 col-sm-3 wwqk_menu_wh" >
	       		<a href="/say.html" target="_self"><span class="wwqk_menu dline">说说</span></a>
	       	</div>
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/match.html" target="_self"><span class="wwqk_menu">比赛</span></a>
	       	</div>
	       	<div class="col-xs-3 col-sm-3 wwqk_menu_wh" >
	       		<a href="/data.html" target="_self"><span class="wwqk_menu">数据</span></a>
	       	</div>
	    </div>
	    
	    <div class="row clear_row_margin visible-sm visible-xs" >
			<div id="main_content" style="min-height:10px;" class="col-sm-12 col-xs-12">		
				<div class="bread">
					当前位置：<a href="/" target="_self">首页</a>
						&nbsp;&gt;&nbsp;<a href="/say.html" target="_self">说说</a>
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
								&nbsp;<a href="player-${say.player_name_en}-${say.player_id}.html" target="_self" title="${say.player_name}的更多说说" style="color:#bbb;">查看更多</a>
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
					&nbsp;&gt;&nbsp;<a href="/say.html" target="_blank">说说</a>
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
								&nbsp;<a href="player-${say.player_name_en}-${say.player_id}.html" target="_blank" title="${say.player_name}的更多说说">查看更多</a>
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
						<div class="col-lg-12 col-md-12">
							<!-- JiaThis Button BEGIN -->
							<div class="jiathis_style" style="margin-top:10px;">
								<span class="jiathis_txt">分享到：</span>
								<a class="jiathis_button_qzone">QQ空间</a>
								<a class="jiathis_button_tsina">新浪微博</a>
								<a class="jiathis_button_tqq">腾讯微博</a>
								<a class="jiathis_button_weixin">微信</a>
								<a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jiathis_separator jtico jtico_jiathis" target="_blank">更多</a>
								<a class="jiathis_counter_style"></a>
							</div>
							<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>
							<!-- JiaThis Button END -->
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
		$('.image').viewer({toolbar:false, zIndex:20000});
	});
	</script>
		
</body>	

