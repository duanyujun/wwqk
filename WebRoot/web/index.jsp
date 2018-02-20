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
	<meta name="keywords" content="足球网,趣点足球网,足球趣闻,五大联赛,球队阵容,免费足球直播" />
	<meta name="description" content="趣点足球网为球迷们提供足球相关的趣闻、球队阵容数据、免费的足球直播。让足球生活更有意义，上趣点足球网。" />
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="360-site-verification" content="50124c80a9d6c3f6e8b6886a57c2d0eb" />
    <link href="https://cdn.bootcss.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/dropload/dropload.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/viewer/viewer.min.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/lobibox/css/lobibox.min.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - 一个有意思的足球网站|足球说说|足球趣闻|足球数据|球星生活|免费直播</title>
</head>

<body>
<div id="all_content">
	<div class="container">
		<%@ include file="/common/menu.jsp"%>
	    
	    <!-- 移动端内容开始 -->
	    <div id="list_content" class="row visible-sm visible-xs m-idx-list" >
	    	<c:forEach items="${funPage.list}" var="fun" varStatus="status">
					<c:if test="${status.index!=0}">
						<div class="col-sm-12 col-xs-12 m-idx-line"></div>
					</c:if>
			    	<div class="col-sm-12 col-xs-12 padding-left-8">
		   					<div class="mob-author">
		   					
		   							<c:if test="${fun.type==2}">
			                            <div class="author-face">
											<a href="player-${fun.player_name_en}-${fun.player_id}.html" target="_self"><img src="${fun.player_image}"></a>
			                            </div>
		                            </c:if>
		                            
		                            <c:if test="${fun.type==1}">
										<a href="${(empty fun.source_name)?'fun.html':fun.source_url}" target="_blank" class="mob-author-a" >
			                                <span class="author-name margin-left-0">${(empty fun.source_name)?'趣点足球网':fun.source_name}</span>
			                            </a>
			                            <span class="author-name">
											&nbsp;<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>
										</span>
									</c:if>
									<c:if test="${fun.type==2}">
										<a href="player-${fun.player_name_en}-${fun.player_id}.html" target="_self" class="mob-author-a">
			                                <span class="author-name">${fun.player_name}</span>
			                            </a>
			                            <span class="author-name">
											&nbsp;<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>&nbsp;
										</span>
									</c:if>
		                    </div>
			    	</div>
			    	<!-- 内容 -->
			    	<div class="col-sm-12 col-xs-12 content-title m-idx-title" >
						<c:if test="${fun.type==1}">
							<a href="fdetail-<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>-${fun.title_en}-${fun.id}.html" target="_self" ><span class="summary-mobile">${fun.summary}</span></a>
						</c:if>
						<c:if test="${fun.type==2}">
							<span class="summary-mobile line-cut">${fun.summary}</span>
						</c:if>
					</div>
			    	<!-- 图片 -->
			    	<div class="col-sm-12 col-xs-12 content-title m-idx-title" >
						<c:if test="${fun.type==1}">
							<a href="fdetail-<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>-${fun.title_en}-${fun.id}.html" target="_self" ><img src="${fun.image_small}" class="img-responsive" alt="${fun.title}" title="${fun.title}"/></a>
						</c:if>
						<c:if test="${fun.type==2}">
							<c:if test="${!empty fun.image_big}">
								<div class="mobile-div-img min-height-220" data-bg-sm="${fun.image_big}" width="${fun.image_big_width }" height="${fun.image_big_height}" >
								
								</div>
							</c:if>
						</c:if>
					</div>
					<div class="col-sm-12 col-xs-12 padding-zero">
						<div class="index-line"></div>
					</div>
			    	
			  </c:forEach>
	    </div>
	    <!-- 移动端内容结束 -->
	</div>
	
	<!-- PC内容开始 -->
    <div class="row clear_row_margin hidden-sm hidden-xs pc-idx-list">
    	<div class="col-lg-2 col-md-2">
    		<c:if test="${!empty lstAlliance}">
    			<div id="slideBox" class="slideBox" >
				  <ul class="items" >
				  	<c:forEach items="${lstAlliance}" var="alliance">
				  		<li><a href="${alliance.tbk_short_url}" title="${alliance.product_name}" target="_blank"><img src="${alliance.product_img}"></a></li>
				  	</c:forEach>
				  </ul>
				</div>
    		</c:if>
    	</div>
		<div id="main_content" class="col-lg-6 col-md-6 min-height-20">		
			<div class="col-lg-11 col-md-11 padding-left-0">
				<c:forEach items="${funPage.list}" var="fun" varStatus="status">
					<c:if test="${status.index!=0}">
						<div class="col-lg-12 col-md-12 pc-idx-cont"></div>
					</c:if>
				
					<div class="col-lg-4 col-md-4 content-title padding-left-0">
						<c:if test="${fun.type==1}">
							<a href="fdetail-<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>-${fun.title_en}-${fun.id}.html" target="_blank"><img src="${fun.image_small}" class="msg-img" alt="${fun.title}" title="${fun.title}"/></a>
						</c:if>
						<c:if test="${fun.type==2}">
							<a href="sdetail-<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>-${fun.player_name_en}-${fun.source_id}.html" target="_blank"><img src="${fun.image_small}" class="msg-img" alt="${fun.player_name_en}" title="${fun.player_name_en}"/></a>
						</c:if>
					</div>
					<div class="col-lg-8 col-md-8 content_pleft" >
						<div class="col-lg-12 col-md-12">
							<span class="msg-title">
								<c:if test="${fun.type==1}">
									<a href="fdetail-<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>-${fun.title_en}-${fun.id}.html" target="_blank" title="${fun.title}">${fun.title}</a>
								</c:if>
								<c:if test="${fun.type==2}">
									<a href="sdetail-<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>-${fun.player_name_en}-${fun.source_id}.html" target="_blank"><div class="text_cut pc-cut-title" title="${fun.summary}">${fun.summary}</div></a>
								</c:if>
							</span>
						</div>
						<div class="col-lg-12 col-md-12 margin-top-5">
							<div class="mob-author">
									<c:if test="${fun.type==2}">
		                                <div class="author-face">
					                        <a href="player-${fun.player_name_en}-${fun.player_id}.html" target="_blank"><img src="${fun.player_image}"></a>
			                            </div>
		                            </c:if>
		                            
		                            <c:if test="${fun.type==1}">
			                            <a href="${(empty fun.source_name)?'fun.html':fun.source_url}" target="_blank" class="mob-author-a margin-left-0">
			                                <span class="author-name" style="margin-left:0;">${(empty fun.source_name)?'趣点足球网':fun.source_name}</span>
			                            </a>
			                            <span class="author-name">
											&nbsp;<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>
										</span>
									</c:if>
									<c:if test="${fun.type==2}">
										<a href="player-${fun.player_name_en}-${fun.player_id}.html" target="_blank" class="mob-author-a">
			                                <span class="author-name">${fun.player_name}</span>
			                            </a>
			                            <span class="author-name">
											&nbsp;<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>&nbsp;
										</span>
									</c:if>
		                    </div>
						</div>
						
						<div class="col-lg-12 col-md-12 pc-summary" title="${fun.summary}">
							<span class="summary line-cut">${fun.summary}</span>
						</div>
					</div>
					<div class="col-lg-12 col-md-12 padding-left-0">
						<div class="index-line"></div>
					</div>
					
				</c:forEach>
				
				<div class="col-lg-12 col-md-12 margin-top-20">
					${pageContent}
				</div>
				
			</div>
		</div>
		<div style="min-height:20px;padding-right:0px;" class="col-lg-3 col-md-3">	
			<div class="row">
				<div class="col-lg-12 col-md-12">
					<%@ include file="/common/recom.jsp"%>							
				</div>
			</div>
			
		</div>
	</div>
    <!-- PC内容结束 -->
	
	
	
	<%@ include file="/common/footer.jsp"%>		
	</div>
	
	<script src="assets/global/plugins/dropload/dropload.min.js" type="text/javascript"></script>
	<script src="assets/global/plugins/viewer/viewer-jquery.min.js" type="text/javascript"></script>
	<script src="assets/global/plugins/lobibox/js/notifications.min.js?v=1.0" type="text/javascript"></script>
	<script src="assets/global/plugins/jquery.slideBox.min.js" type="text/javascript"></script>
	<script type="text/javascript">
	var guessCount = parseInt('${guessCount}');
	var jsonStr = '${jsonStr}';
	var initCount = '${initCount}';
	</script>
	
	<script src="assets/pages/scripts/index.js" type="text/javascript"></script>
	
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

