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
	<meta name="keywords" content="趣点足球网,在线直播,球员动态,视频集锦,球队排名" />
	<meta name="description" content="趣点足球网为球迷们提供最新的球员资讯，直播链接以及足球数据" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="https://cdn.bootcss.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="/common/new/main.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - 足球说说|球员说说|球员动态|球员资讯|球星生活</title>
    <style type="text/css">
    	.goods-link a{font-size:14px;color:#404040;}
		.goods-link a:focus, a:hover{text-decoration: underline; color:#f40;}
		.goods-div{min-height:220px;float:left;border:1px solid #ededed;padding-left:0;padding-right:0;padding-bottom:15px;}
		.goods-div:hover{border:1px solid #f40;}
    </style>
</head>

<body>
<!-- header start -->
<%@ include file="/common/new/menu.jsp"%>

<!-- pc content start -->
<div class="main hidden-sm hidden-xs">
	<div style="width:930px;float:left;">
		<div style="width:100%;float:left;padding-left:3%;">
			<c:forEach items="${goodsPage.list}" var="goods" varStatus="status">
				<div style="float:left;width:25%; margin-bottom:20px;">
					<div style="float:left;width:100%;text-align:center;">
						<div style="width:94%;max-height:340px;min-height:285px;height:285px;">
							<div style="width:100%;">
								<div style="float:left;width:100%;max-height:240px;">
									<a href="${goods.tbk_short_url}" target="_blank"><img src="${goods.product_img}" style="width:100%;height:240px;" /></a>
								</div>
								<div style="float:left;width:90%;margin-left:5%;margin-right:5%;height:36px;line-height:40px;">
									<div style="font-size:18px;color:#F40;">
								       <span>¥</span><strong>${goods.price}</strong>
								    </div>
								</div>
								<div style="float:left;width:90%;margin-left:5%;margin-right:5%;height:40px;">
									<div class="more_text goods-link" style="height:40px;">
								       <a href="${goods.tbk_short_url}" href="_blank" title="${goods.product_name}">${goods.product_name}</a>
								    </div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		<div style="width:100%;float:left;margin-top:20px;">
				${pageContent}
		</div>
	</div>
</div>
<!-- pc content end -->

<%@ include file="/common/new/goods-mobile.jsp"%>	

<!-- footer start -->
<%@ include file="/common/new/footer.jsp"%>	

<script type="text/javascript" src="assets/pages/scripts/jquery.dotdotdot.js"></script>
<script>
	$(function() {
	     $(".more_text").dotdotdot(); 
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
