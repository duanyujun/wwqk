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
	<meta name="keywords" content="趣点足球网,足球趣点,数据分析,新星挖掘,球员评价,数据统计" />
	<meta name="description" content="趣点足球网为球迷们提供最新有趣的足球资讯，挖掘潜力新星，五大联赛数据分析统计。" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/dropload/dropload.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - 足球趣点|数据分析|新星挖掘|球员评价</title>
    
</head>

<body>
	<div class="container">
		<div class="row menu_bg clear_row_margin hidden-sm hidden-xs" >
			<div id="main_nav" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2">	
				<div>
					<div class="logo_div">
						<a href="" title="首页">趣点足球网</a>
					</div>
					<ul style="float:left;">
						<li class="menu_width"><a href="">首页</a></li>
						<li class="menu_width menu_sel"><a href="fun.html">趣点</a></li>
						<li class="menu_width"><a href="say.html">说说</a></li>
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
	       		<a href="/fun.html" target="_self"><span class="wwqk_menu dline">趣点</span></a>
	       	</div>
	       	<div class="col-xs-3 col-sm-3 wwqk_menu_wh" >
	       		<a href="/say.html" target="_self"><span class="wwqk_menu">说说</span></a>
	       	</div>
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/match.html" target="_self"><span class="wwqk_menu">比赛</span></a>
	       	</div>
	       	<div class="col-xs-3 col-sm-3 wwqk_menu_wh" >
	       		<a href="/data.html" target="_self"><span class="wwqk_menu">数据</span></a>
	       	</div>
	    </div>
	    
	    <!-- 移动端内容开始 -->
	    <div id="list_content" class="row visible-sm visible-xs" style="margin-top:45px;">
	    	<c:forEach items="${funPage.list}" var="fun" varStatus="status">
					<c:if test="${status.index!=0}">
						<div class="col-sm-12 col-xs-12" style="margin-top:10px;height:1px;"></div>
					</c:if>
			    	<div class="col-sm-12 col-xs-12">
		   					<div class="mob-author">
		                            <div class="author-face">
				                        <img src="assets/image/page/logo-small.png">
		                            </div>
		                            <span class="mob-author-a">
		                            	<span class="author-name">趣点足球网</span>
		                            </span>
		                            
		                            <span class="author-name">
										&nbsp;<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd HH:mm"/>
									</span>
		                    </div>
			    	</div>
			    	
			    	<!-- 内容 -->
			    	<div class="col-sm-12 col-xs-12 content-title" style="padding-left:45px;">
						<span class="msg-title">
							<a href="fdetail-<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>-${fun.id}.html" target="_blank" title="${fun.title}" style="font-size:16px;">${fun.title}</a>
						</span><br>
						<a href="fdetail-<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>-${fun.id}.html" target="_self" ><span class="summary">${fun.summary}</span></a>
					</div>
			    	<!-- 图片 -->
			    	<div class="col-sm-12 col-xs-12 content-title" style="margin-top:8px;padding-left:45px;">
						<a href="fdetail-<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>-${fun.id}.html" target="_self" ><img src="${fun.image_small}" class="img-responsive" alt="${fun.title}" title="${fun.title}"/></a>
					</div>
					<div class="col-sm-12 col-xs-12" style="padding-left:0px;padding-right:0px;">
						<div class="index-line"></div>
					</div>
			    	
			  </c:forEach>
	    </div>
	    <!-- 移动端内容结束 -->
	    
	    
	</div>
	
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:70px;">
		<div id="main_content" style="min-height:10px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="bread">
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;趣点
			</div>
		</div>
	</div>
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:20px;">
		<div id="main_content" style="min-height:10px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2">		
			<div class="col-lg-9 col-md-9" style="padding-left:0px;">
				<c:forEach items="${funPage.list}" var="fun" varStatus="status">
					<c:if test="${status.index!=0}">
						<div class="col-lg-12 col-md-12" style="margin-top:19px;height:1px;"></div>
					</c:if>
				
					<div class="col-lg-4 col-md-4 content-title" style="padding-left:0px;">
							<a href="fdetail-<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>-${fun.id}.html" target="_blank"><img src="${fun.image_small}" class="msg-img" alt="${fun.title}" title="${fun.title}"/></a>
					</div>
					<div class="col-lg-8 col-md-8" style="padding-left:0px;">
						<div class="col-lg-12 col-md-12">
							<span class="msg-title">
								<a href="fdetail-<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>-${fun.id}.html" target="_blank" title="${fun.title}">${fun.title}</a>
							</span>
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:5px;">
							<div class="mob-author">
	                                <div class="author-face">
				                       	<img src="assets/image/page/logo-small.png">	
		                            </div>
		                            
		                            <span class="mob-author-a">
		                                <span class="author-name">趣点足球网</span>
		                            </span>
		                            <span class="author-name">
										&nbsp;<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>
									</span>
		                    </div>
						</div>
						
						<div class="col-lg-12 col-md-12" style="margin-top:20px;padding-right:0;">
							<span class="summary">${fun.summary}</span>
						</div>
					</div>
					<div class="col-lg-12 col-md-12" style="padding-left:0px;">
						<div class="index-line"></div>
					</div>
					
				</c:forEach>
				
				<div class="col-lg-12 col-md-12 " style="margin-top:20px;">
					<div class="scott pull-right">
						<a href="/fun-page-1.html" title="首页"> &lt;&lt; </a>
						
						<c:if test="${funPage.pageNumber == 1}">
							<span class="disabled"> &lt; </span>
						</c:if>
						<c:if test="${funPage.pageNumber != 1}">
							<a href="/fun-page-${funPage.pageNumber - 1}.html" > &lt; </a>
						</c:if>
						<c:if test="${funPage.pageNumber > 8}">
							<a href="/fun-page-1.html">1</a>
							<a href="/fun-page-2.html">2</a>
							...
						</c:if>
						<c:if test="${!empty pageUI.list}">
							<c:forEach items="${pageUI.list}" var="pageNo">
								<c:if test="${funPage.pageNumber == pageNo }">
									<span class="current">${pageNo}</span>
								</c:if>
								<c:if test="${funPage.pageNumber != pageNo }">
									<a href="/fun-page-${pageNo}.html">${pageNo}</a>
								</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${(funPage.totalPage - funPage.pageNumber) >= 8 }">
							...
							<a href="/fun-page-${funPage.totalPage - 1}.html">${funPage.totalPage - 1}</a>
							<a href="/fun-page-${funPage.totalPage}.html">${funPage.totalPage}</a>
						</c:if>
						
						<c:if test="${funPage.pageNumber == funPage.totalPage}">
							<span class="disabled"> &gt; </span>
						</c:if>
						<c:if test="${funPage.pageNumber != funPage.totalPage}">
							<a href="/fun-page-${funPage.pageNumber + 1}.html"> &gt; </a>
						</c:if>
						
						<a href="/fun-page-${funPage.totalPage}.html" title="尾页"> &gt;&gt; </a>
					</div>
				</div>
				
			</div>
		</div>
	</div>
	
	<%@ include file="/common/footer.jsp"%>		
	
	<script src="assets/global/plugins/dropload/dropload.min.js" type="text/javascript"></script>
	
	<script>
	
	$(function(){
		
	    var noDataStr = '<div class="dropload-noData">暂无数据</div>';
	    if(parseInt('${initCount}')<5){
	    	if(parseInt('${initCount}')!=0){
		    	noDataStr = '<div class="dropload-noData">&nbsp;</div>';
	    	}
	    }
	    var pageNo = 1;
	    // dropload
	    $('body').dropload({
	        scrollArea : window,
	        domUp : {
	            domClass   : 'dropload-up',
	            domRefresh : '<div class="dropload-refresh">↓下拉刷新</div>',
	            domUpdate  : '<div class="dropload-update">↑释放更新</div>',
	            domLoad    : '<div class="dropload-load"><span class="loading"></span>加载中...</div>'
	        },
	        domDown : {
	            domClass   : 'dropload-down',
	            domRefresh : '<div class="dropload-refresh">↑上拉加载更多</div>',
	            domLoad    : '<div class="dropload-load"><span class="loading"></span>加载中...</div>',
	            domNoData  : noDataStr
	        },
	        loadUpFn : function(me){
	            $.ajax({
	                type: 'GET',
	                data: {pageNo:1},
	                url: 'fun/listMore',
	                dataType: 'json',
	                success: function(data){
	                    var strhtml = '';
	                    for(var i = 0; i < data.length; i++){
	                    	if(i!=0){
	                    		strhtml += "<div class=\"col-sm-12 col-xs-12\" style=\"margin-top:10px;height:1px;\"></div>";
	                    	}
	                    	strhtml += addRecode(data[i]);
	                    }
	                    $('#list_content').html(strhtml);
	                    // 每次数据加载完，必须重置
	                    me.resetload();
	                    // 重置页数，重新获取loadDownFn的数据
	                    pageNo = 1;
	                    // 解锁loadDownFn里锁定的情况
	                    me.unlock();
	                    me.noData(false);
	                },
	                error: function(xhr, type){
	                    // 即使加载出错，也得重置
	                    me.resetload();
	                }
	            });
	        },
	        loadDownFn : function(me){
	        	pageNo++;
	            // 拼接HTML
	            var strhtml = '';
	            $.ajax({
	            	type: 'GET',
	                data: {pageNo:pageNo},
	                url: 'fun/listMore',
	                dataType: 'json',
	                success: function(data){
	                    var arrLen = data.length;
	                    if(arrLen > 0){
	                    	strhtml += "<div class=\"col-sm-12 col-xs-12\" style=\"margin-top:10px;height:1px;\"></div>";
	                        for(var i=0; i<arrLen; i++){
	                        	if(i!=0){
		                    		strhtml += "<div class=\"col-sm-12 col-xs-12\" style=\"margin-top:10px;height:1px;\"></div>";
		                    	}
	                        	strhtml += addRecode(data[i]);
	                        }
	                    // 如果没有数据
	                    }else{
	                        // 锁定
	                        me.lock();
	                        // 无数据
	                        me.noData();
	                    }
	                 	// 插入数据到页面，放到最后面
	                    $('#list_content').append(strhtml);
	                    // 每次数据插入，必须重置
	                    me.resetload();
	                },
	                error: function(xhr, type){
	                    // 即使加载出错，也得重置
	                    me.resetload();
	                }
	            });
	        },
	        threshold : 50
	    });
	});


	function addRecode(fun){
		var date_str = '';
		if(fun.create_time){
			date_str = fun.create_time.substring(0,10);
		}
		var strhtml = "<div class=\"col-sm-12 col-xs-12\">";
		strhtml+="		   					<div class=\"mob-author\">";
		strhtml+="		                            <div class=\"author-face\">";
		strhtml+="				                        <img src=\"assets/image/page/logo-small.png\">";
		strhtml+="		                            </div>";
		strhtml+="		                            <span class=\"mob-author-a\">";
		strhtml+="		                            	<span class=\"author-name\">趣点足球网</span>";
		strhtml+="		                            </span>";
		strhtml+="		                            <span class=\"author-name\">";
		strhtml+="										&nbsp;"+date_str;
		strhtml+="									</span>";
		strhtml+="		                    </div>";
		strhtml+="			    	</div>";
		strhtml+="			    	<div class=\"col-sm-12 col-xs-12 content-title\" style=\"padding-left:45px;\">";
		strhtml+="						<span class=\"msg-title\">";
		strhtml+="							<a href=\"fdetail-"+date_str+"-"+fun.id+".html\" target=\"_self\" title=\""+fun.title+"\" style=\"font-size:16px;\">"+fun.title+"</a>";
		strhtml+="						</span><br>";
		strhtml+="						<a href=\"fdetail-"+date_str+"-"+fun.id+".html\" target=\"_self\" ><span class=\"summary\">"+fun.summary+"</span></a>";
		strhtml+="					</div>";
		strhtml+="			    	<div class=\"col-sm-12 col-xs-12 content-title\" style=\"margin-top:8px;padding-left:45px;\">";
		strhtml+="						<a href=\"fdetail-"+date_str+"-"+fun.id+".html\" target=\"_self\" ><img src=\""+fun.image_small+"\" class=\"img-responsive\" alt=\""+fun.title+"\" title=\""+fun.title+"\"/></a>";
		strhtml+="					</div>";
		strhtml+="					<div class=\"col-sm-12 col-xs-12\" style=\"padding-left:0px;padding-right:0px;\">";
		strhtml+="						<div class=\"index-line\"></div>";
		strhtml+="					</div>";
			
		return strhtml;
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


