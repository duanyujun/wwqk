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
	<meta name="keywords" content="趣点足球网,球员说说,球星资讯,球员动态,球星生活" />
	<meta name="description" content="趣点足球网为球迷们提供最新的球员生活、球星生活动态，球员资讯" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/dropload/dropload.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - 足球说说|球员说说|球员动态|球员资讯|球星生活</title>
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
						<li class="menu_width"><a href="fun.html">趣点</a></li>
						<li class="menu_width menu_sel"><a href="say.html">说说</a></li>
						<li class="menu_width"><a href="match.html">比赛</a></li>
						<li class="menu_width"><a href="data.html">数据</a></li>
					</ul>
				</div>
			</div>
		</div>
		
		<div class="row menu_link navbar-fixed-top  visible-sm visible-xs" style="background:#00A50D;min-height:35px;color:white;">
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
	    
	    <!-- 移动端开始 -->
	    <div id="list_content" class="row visible-sm visible-xs" style="margin-top:45px;">
	    <c:forEach items="${sayPage.list}" var="say" varStatus="status">
    			<c:if test="${status.index!=0}">
					<div class="col-sm-12 col-xs-12" style="margin-top:10px;height:1px;"></div>
				</c:if>
		    	<div class="col-sm-12 col-xs-12">
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
		    	<!-- 内容 -->
		    	<div class="col-sm-12 col-xs-12 content-title" style="padding-left:45px;">
					<span class="summary">${say.content}</span>
				</div>
		    	<!-- 图片 -->
		    	<c:if test="${!empty say.image_big}">
			    	<div class="col-sm-12 col-xs-12 content-title" style="margin-top:8px;padding-left:45px;">
						<img src="${say.image_big}" class="img-responsive" style="height:220px;" alt="${say.content}" title="${say.content}"/>
					</div>
				</c:if>
				<div class="col-sm-12 col-xs-12" style="padding-left:0px;padding-right:0px;">
					<div class="index-line"></div>
				</div>
	    </c:forEach>
	    </div>
	    <!-- 移动端结束 -->
	</div>
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:70px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2">		
			<div class="bread">
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;说说广场
			</div>
		</div>
	</div>
	
	
	<!-- PC端开始 -->
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:10px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2">		
				<c:forEach items="${sayPage.list}" var="say" varStatus="status">
					<div class="col-lg-9 col-md-9" style="border:1px solid #E3E7EA;${status.index!=0?'border-top:0;':''}padding:20px;padding-left:0;padding-bottom:10px;">
						<div class="col-lg-1 col-md-1">
							<a href="player-${say.player_name_en}-${say.player_id}.html" style="color:#292f33;" target="_blank"><img src="${say.player_img_local}" style="width:48px;height:48px;" alt="${say.player_name}" title="${say.player_name}"/></a>
						</div>
						<div class="col-lg-11 col-md-11" >
							<div class="col-lg-12 col-md-12 say-info">
								<span style="display:block;font-weight:bold;color:#292f33;float:left;"><a href="player-${say.player_name_en}-${say.player_id}.html" style="color:#292f33;" target="_blank">${say.player_name}</a></span>
								<span style="display:block;color:#8899a6;font-size:13px;float:left;"> - <fmt:formatDate value="${say.create_time}" pattern="MM月dd日"/> </span>
								&nbsp;<a href="player-${say.player_name_en}-${say.player_id}.html" target="_blank" title="${say.player_name}的更多说说">查看更多</a>
							</div>
						
							<div class="col-lg-12 col-md-12">
								<span style="color:#292f33;font-size:14px;">${say.content}</span>
							</div>
							
							<c:if test="${!empty say.image_big}">
								<div class="col-lg-12 col-md-12" style="margin-top:10px;">
									<img src="${say.image_big}" class="img-responsive img-rounded" alt="${say.content}" title="${say.player_name} - ${say.content}"/>
								</div>
							</c:if>
						</div>
					</div>
				</c:forEach>
				
				
				<div class="col-lg-9 col-md-9" style="margin-top:20px;padding-right:0px;">
					<div class="scott pull-right" >
						<a href="/say-page-1.html" title="首页"> &lt;&lt; </a>
						
						<c:if test="${sayPage.pageNumber == 1}">
							<span class="disabled"> &lt; </span>
						</c:if>
						<c:if test="${sayPage.pageNumber != 1}">
							<a href="/say-page-${sayPage.pageNumber - 1}.html" > &lt; </a>
						</c:if>
						<c:if test="${sayPage.pageNumber > 8}">
							<a href="/say-page-1.html">1</a>
							<a href="/say-page-2.html">2</a>
							...
						</c:if>
						<c:if test="${!empty pageUI.list}">
							<c:forEach items="${pageUI.list}" var="pageNo">
								<c:if test="${sayPage.pageNumber == pageNo }">
									<span class="current">${pageNo}</span>
								</c:if>
								<c:if test="${sayPage.pageNumber != pageNo }">
									<a href="/say-page-${pageNo}.html">${pageNo}</a>
								</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${(sayPage.totalPage - sayPage.pageNumber) >= 8 }">
							...
							<a href="/say-page-${sayPage.totalPage - 1}.html">${sayPage.totalPage - 1}</a>
							<a href="/say-page-${sayPage.totalPage}.html">${sayPage.totalPage}</a>
						</c:if>
						
						<c:if test="${sayPage.pageNumber == sayPage.totalPage}">
							<span class="disabled"> &gt; </span>
						</c:if>
						<c:if test="${sayPage.pageNumber != sayPage.totalPage}">
							<a href="/say-page-${sayPage.pageNumber + 1}.html"> &gt; </a>
						</c:if>
						
						<a href="/say-page-${sayPage.totalPage}.html" title="尾页" > &gt;&gt; </a>
					</div>
				</div>
		</div>
	</div>
	<!-- PC端结束 -->
	
	
	<%@ include file="/common/footer.jsp"%>	
	
	<script src="assets/global/plugins/dropload/dropload.min.js" type="text/javascript"></script>
	
	<script>
	
	$(function(){
		if (!(navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
			return;
		}
		
	    var noDataStr = '<div class="dropload-noData">暂无数据</div>';
	    if(parseInt('${initCount}')<5){
	    	if(parseInt('${initCount}')!=0){
		    	noDataStr = '<div class="dropload-noData">&nbsp;</div>';
	    	}
	    }
	    var pageNo = 1;
	    // dropload
	    $('.container').dropload({
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
	                url: 'say/listMore',
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
	                url: 'say/listMore',
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


	function addRecode(say){
		var create_time = '';
		var date_str = '';
		if(say.create_time){
			create_time = say.create_time.substring(0,16);
			date_str = say.create_time.substring(0,10);
		}
		var strhtml = "<div class=\"col-sm-12 col-xs-12\">";
		strhtml+="	   					<div class=\"mob-author\">";
		strhtml+="	                            <div class=\"author-face\">";
		strhtml+="			                        <a href=\"player-"+say.player_name_en+"-"+say.player_id+".html\" target=\"_self\"><img src=\""+say.player_img_local+"\"></a>";
		strhtml+="	                            </div>";
		strhtml+="	                            <a href=\"player-"+say.player_name_en+"-"+say.player_id+".html\" target=\"_self\" class=\"mob-author-a\">";
		strhtml+="	                                <span class=\"author-name\">"+say.player_name+"</span>";
		strhtml+="	                            </a>";
		strhtml+="	                            <span class=\"author-name say-info\">";
		strhtml+="									&nbsp;"+create_time;
		strhtml+="									&nbsp;<a href=\"player-"+say.player_name_en+"-"+say.player_id+".html\" target=\"_self\" title=\""+say.player_name+"的更多说说\" style=\"color:#bbb;\">查看更多</a>";
		strhtml+="								</span>";
		strhtml+="	                    </div>";
		strhtml+="		    	</div>";
		strhtml+="		    	<div class=\"col-sm-12 col-xs-12 content-title\" style=\"padding-left:45px;\">";
		strhtml+="					<span class=\"summary\">"+say.content+"</span>";
		strhtml+="				</div>";
		if(say.image_big && say.image_big!=''){
			strhtml+="			    	<div class=\"col-sm-12 col-xs-12 content-title\" style=\"margin-top:8px;padding-left:45px;\">";
			strhtml+="						<img src=\""+say.image_big+"\" class=\"img-responsive\" style=\"height:220px;\" alt=\""+say.content+"\" title=\""+say.content+"\"/>";
			strhtml+="					</div>";
		}
		strhtml+="				<div class=\"col-sm-12 col-xs-12\" style=\"padding-left:0px;padding-right:0px;\">";
		strhtml+="					<div class=\"index-line\"></div>";
		strhtml+="				</div>";
			
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

