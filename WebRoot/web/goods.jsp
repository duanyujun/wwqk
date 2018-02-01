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
	<meta name="keywords" content="运动装备,足球装备,足球鞋,球衣,球迷,耐克,阿迪达斯" />
	<meta name="description" content="趣点足球网为您精选运动装备，为您对足球的热爱保驾护航。" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="https://cdn.bootcss.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - 运动装备，为您精选</title>
</head>

<body>

<div id="all_content">	
	<div class="container">
		
		<%@ include file="/common/menu.jsp"%>
	    <!-- 移动端内容开始 -->
	    <div class="row visible-sm visible-xs" style="margin-top:10px;padding-bottom: 30px;">
	    	<div class="col-xs-12 col-sm-12" style="padding:0;">
						<table class="table small-table" style="text-indent:0;">
						  	<tbody>
							<c:forEach items="${videosPage.list}" var="videos">
								<tr>
							      <td class="a-title" style="line-height:30px;padding-left:5px;">
							      	<div class="text_cut" style="width:350px;max-width:95%;line-height:30px;"><a style="${videos.is_red=='1'?'color:red;':''}" href="/vdetail-<fmt:formatDate value="${videos.match_date}" pattern="yyyy-MM-dd"/>-${videos.match_en_title}-${videos.id}.html" target="_blank">${videos.match_title}</a></div>
							      </td>
							    </tr>
						    </c:forEach>
						   </tbody>
						</table>
	    	</div>
	    	<div class="col-xs-12 col-sm-12" style="padding:0;">
	    		
	    	</div>
	    </div>
	    <!-- 移动端内容结束 -->
	</div>
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:70px;">
		<div id="main_content" style="min-height:10px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2">		
			<div class="bread">
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;精选
			</div>
		</div>
	</div>
	
	<div class="row clear_row_margin" style="padding-bottom: 130px;margin-top:15px;">
		<div id="main_content" style="min-height:20px;padding-bottom:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="row">
				<c:forEach items="${goodsPage.list}" var="goods" varStatus="status">
					<div class="col-lg-3 col-md-3" style="margin-bottom:25px;">
						<div class="row">
							<div class="col-lg-11 col-md-11 goods-div">
								<div style="float:left;width:100%;">
									<a href="${goods.tbk_short_url}" target="_blank"><img src="${goods.product_img}" style="width:100%;" /></a>
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
					
				</c:forEach>
			</div>
			<div class="row">
				<div class="col-lg-12 col-md-12 margin-top-20">
					${pageContent}
				</div>
			</div>
			
			
		</div>
	</div>
	
	<%@ include file="/common/footer.jsp"%>		
	</div>
	<script type="text/javascript" src="assets/pages/scripts/jquery.dotdotdot.js"></script>
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
	
	$(function() {
	     $(".more_text").dotdotdot(); 
	});
	</script>
		
</body>	

