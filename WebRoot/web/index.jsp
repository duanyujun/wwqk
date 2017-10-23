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
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/dropload/dropload.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/viewer/viewer.min.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/lobibox/css/lobibox.min.css" rel="stylesheet" type="text/css" />
    
    
    <title>趣点足球网 - 一个有意思的足球网站|足球说说|足球趣闻|足球数据|球星生活|免费直播</title>
</head>

<body>
<div id="all_content">
	<div class="container">
		<div class="row menu_bg clear_row_margin hidden-sm hidden-xs" >
			<div id="main_nav" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2">	
				<div>
					<div class="logo_div">
						<a href="">趣点足球网</a>
					</div>
					<ul style="float:left;">
						<li class="menu_sel menu_width"><a href="">首页</a></li>
						<li class="menu_width"><a href="fun.html">趣点</a></li>
						<li class="menu_width"><a href="live.html">直播</a></li>
						<li class="menu_width"><a href="bifen.html">比分</a></li>
						<li class="menu_width"><a href="data.html">数据</a></li>
					</ul>
				</div>
			</div>
		</div>
		
		<div class="row menu_link navbar-fixed-top visible-sm visible-xs" style="background:#00A50D;min-height:35px;color:white;">
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/" target="_self"><span class="wwqk_menu dline">首页</span></a>
	       	</div>
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/fun.html" target="_self"><span class="wwqk_menu">趣点</span></a>
	       	</div>
	       	<div class="col-xs-3 col-sm-3 wwqk_menu_wh" >
	       		<a href="/live.html" target="_self"><span class="wwqk_menu">直播</span></a>
	       	</div>
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/bifen.html" target="_self"><span class="wwqk_menu">比分</span></a>
	       	</div>
	       	<div class="col-xs-3 col-sm-3 wwqk_menu_wh" >
	       		<a href="/data.html" target="_self"><span class="wwqk_menu">数据</span></a>
	       	</div>
	    </div>
	    
	    <!-- 移动端内容开始 -->
	    <div id="list_content" class="row visible-sm visible-xs" style="margin-top:45px;padding-bottom: 130px;">
	    	<c:forEach items="${funPage.list}" var="fun" varStatus="status">
					<c:if test="${status.index!=0}">
						<div class="col-sm-12 col-xs-12" style="margin-top:10px;height:1px;"></div>
					</c:if>
			    	<div class="col-sm-12 col-xs-12" style="padding-left:8px;">
		   					<div class="mob-author">
		   					
		   							<c:if test="${fun.type==2}">
			                            <div class="author-face">
											<a href="player-${fun.player_name_en}-${fun.player_id}.html" target="_self"><img src="${fun.player_image}"></a>
			                            </div>
		                            </c:if>
		                            
		                            <c:if test="${fun.type==1}">
										<a href="${(empty fun.source_name)?'fun.html':fun.source_url}" target="_blank" class="mob-author-a" >
			                                <span class="author-name" style="margin-left:0;">${(empty fun.source_name)?'趣点足球网':fun.source_name}</span>
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
			    	<div class="col-sm-12 col-xs-12 content-title" style="margin-top:10px;padding-left:38px;">
						<c:if test="${fun.type==1}">
							<a href="fdetail-<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>-${fun.title_en}-${fun.id}.html" target="_self" ><span class="summary-mobile">${fun.summary}</span></a>
						</c:if>
						<c:if test="${fun.type==2}">
							<span class="summary-mobile line-cut">${fun.summary}</span>
						</c:if>
					</div>
			    	<!-- 图片 -->
			    	<div class="col-sm-12 col-xs-12 content-title" style="margin-top:8px;padding-left:38px;">
						<c:if test="${fun.type==1}">
							<a href="fdetail-<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>-${fun.title_en}-${fun.id}.html" target="_self" ><img src="${fun.image_small}" class="img-responsive" alt="${fun.title}" title="${fun.title}"/></a>
						</c:if>
						<c:if test="${fun.type==2}">
							<c:if test="${!empty fun.image_big}">
								<div class="mobile-div-img" data-bg-sm="${fun.image_big}" width="${fun.image_big_width }" height="${fun.image_big_height}" style="min-height:220px;">
								
								</div>
							</c:if>
						</c:if>
					</div>
					<div class="col-sm-12 col-xs-12" style="padding-left:0px;padding-right:0px;">
						<div class="index-line"></div>
					</div>
			    	
			  </c:forEach>
	    </div>
	    <!-- 移动端内容结束 -->
	</div>
	
	<!-- PC内容开始 -->
    <div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:80px;padding-bottom: 130px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-6 col-lg-offset-2 col-md-6 col-md-offset-2">		
			<div class="col-lg-11 col-md-11" style="padding-left:0px;">
				<c:forEach items="${funPage.list}" var="fun" varStatus="status">
					<c:if test="${status.index!=0}">
						<div class="col-lg-12 col-md-12" style="margin-top:19px;height:1px;"></div>
					</c:if>
				
					<div class="col-lg-4 col-md-4 content-title" style="padding-left:0px;">
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
									<a href="sdetail-<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>-${fun.player_name_en}-${fun.source_id}.html" target="_blank"><div class="text_cut" style="width:410px;line-height:32px;" title="${fun.summary}">${fun.summary}</div></a>
								</c:if>
							</span>
						</div>
						<div class="col-lg-12 col-md-12" style="margin-top:5px;">
							<div class="mob-author">
									<c:if test="${fun.type==2}">
		                                <div class="author-face">
					                        <a href="player-${fun.player_name_en}-${fun.player_id}.html" target="_blank"><img src="${fun.player_image}"></a>
			                            </div>
		                            </c:if>
		                            
		                            <c:if test="${fun.type==1}">
			                            <a href="${(empty fun.source_name)?'fun.html':fun.source_url}" target="_blank" class="mob-author-a" style="margin-left:0;">
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
						
						<div class="col-lg-12 col-md-12" style="margin-top:20px;padding-right:0;" title="${fun.summary}">
							<span class="summary line-cut">${fun.summary}</span>
						</div>
					</div>
					<div class="col-lg-12 col-md-12" style="padding-left:0px;">
						<div class="index-line"></div>
					</div>
					
				</c:forEach>
				
				<div class="col-lg-12 col-md-12 " style="margin-top:20px;">
					<div class="scott pull-right">
						<a href="/index-page-1.html" title="首页"> &lt;&lt; </a>
						
						<c:if test="${funPage.pageNumber == 1}">
							<span class="disabled"> &lt; </span>
						</c:if>
						<c:if test="${funPage.pageNumber != 1}">
							<a href="/index-page-${funPage.pageNumber - 1}.html" > &lt; </a>
						</c:if>
						<c:if test="${funPage.pageNumber > 8}">
							<a href="/index-page-1.html">1</a>
							<a href="/index-page-2.html">2</a>
							...
						</c:if>
						<c:if test="${!empty pageUI.list}">
							<c:forEach items="${pageUI.list}" var="pageNo">
								<c:if test="${funPage.pageNumber == pageNo }">
									<span class="current">${pageNo}</span>
								</c:if>
								<c:if test="${funPage.pageNumber != pageNo }">
									<a href="/index-page-${pageNo}.html">${pageNo}</a>
								</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${(funPage.totalPage - funPage.pageNumber) >= 8 }">
							...
							<a href="/index-page-${funPage.totalPage - 1}.html">${funPage.totalPage - 1}</a>
							<a href="/index-page-${funPage.totalPage}.html">${funPage.totalPage}</a>
						</c:if>
						
						<c:if test="${funPage.pageNumber == funPage.totalPage}">
							<span class="disabled"> &gt; </span>
						</c:if>
						<c:if test="${funPage.pageNumber != funPage.totalPage}">
							<a href="/index-page-${funPage.pageNumber + 1}.html"> &gt; </a>
						</c:if>
						
						<a href="/index-page-${funPage.totalPage}.html" title="尾页"> &gt;&gt; </a>
					</div>
				</div>
				
			</div>
		</div>
		<div style="min-height:20px;padding-right:0px;" class="col-lg-3 col-md-3">	
			<div class="row">
				<div class="col-lg-12 col-md-12">
					<table class="table">
					  <tbody>
					  	<c:forEach items="${lstRecomMatches}" var="match">
						    <tr style="font-size:12px;">
						      <td class="team-title">
						      		<div class="row">
						      			<div class="col-lg-12 col-md-12"><center><a href="team-${match.home_team_en_name}-${match.home_team_id}.html" target="_blank"><img src="assets/image/soccer/teams/150x150/${match.home_team_id}.png" style="width:40px;height:40px;"/></a></center></div>
						      			<div class="col-lg-12 col-md-12"><center><a href="team-${match.home_team_en_name}-${match.home_team_id}.html" target="_blank">${match.home_team_name}</a></center></div>
						      		</div>
						      </td>
						      <td class="team-title">
							      	<fmt:formatDate value="${match.match_date}" pattern="yy/MM/dd"/><br>&nbsp;
							      		<b>${match.result}</b><br>&nbsp;
							      		<c:if test="${match.status=='完场'}">
								      		<span class="grey-title" style="font-size:12px;"><a href="match-${match.home_team_en_name}-vs-${match.away_team_en_name}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_blank">集锦</a></span>
								      	</c:if>
								      	<c:if test="${match.status!='完场'}">
								      		<b class="a-title" style="font-size:12px;"><a href="match-${match.home_team_en_name}-vs-${match.away_team_en_name}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_blank">直播</a></b>
								      	</c:if>
						      </td>
						      <td class="team-title">
						      		<div class="col-lg-12 col-md-12"><center><a href="team-${match.away_team_en_name}-${match.away_team_id}.html" target="_blank"><img src="assets/image/soccer/teams/150x150/${match.away_team_id}.png" style="width:40px;height:40px;"/></a></center></div>
						      		<div class="col-lg-12 col-md-12"><center><a href="team-${match.away_team_en_name}-${match.away_team_id}.html" target="_blank">${match.away_team_name}</a></center></div>
						      </td>
						    </tr>
					    </c:forEach>
					  </tbody>
					</table>
					
				</div>
			</div>
		</div>
	</div>
    <!-- PC内容结束 -->
	
	
	
	<%@ include file="/common/footer.jsp"%>		
	</div>
	
	<script src="assets/global/plugins/dropload/dropload.min.js" type="text/javascript"></script>
	<script src="assets/global/plugins/viewer/viewer-jquery.min.js" type="text/javascript"></script>
	<script src="assets/global/plugins/lobibox/js/notifications.min.js" type="text/javascript"></script>
	
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
	
	function resizeBackground(){
	  var winWidth = $(window).width();
	  if (winWidth<992) {
		  $(".mobile-div-img").each(function(){
			  var percent = (parseFloat($(this).attr("height"))/parseFloat($(this).attr("width"))).toFixed(2);
			  var width = winWidth-70;
			  var height = width * percent;
			  $(this).css("width",width+"px").css("height",height+"px");
			  var imageHtml = "<img class='image' src='"+$(this).attr("data-bg-sm")+"' style='width:"+width+"px;height:"+height+"px;' />";
			  $(this).html(imageHtml);
		  });
		  $('.image').viewer(
					{toolbar:false, 
					zIndex:20000,
					shown: function() {
						$(".viewer-canvas").attr("data-action","mix");
					 }
				 }
			);
	  } 
	}
	
	var jsonObj;
	$(document).ready(function(){
		$(window).resize(function() {
			resizeBackground();
		});
		jsonObj = JSON.parse('${jsonStr}');
		if(jsonObj.length!=0){
			//消息提示
			showMsg();
			setInterval("showMsg()",35000); 
		}
	});
	
	var i = 0;
	function showMsg(){
		var leagueName = jsonObj[i].attrs.league_name; 
		var homeName = jsonObj[i].attrs.home_name; 
		var awayName = jsonObj[i].attrs.away_name; 
		var predictionDesc = jsonObj[i].attrs.prediction_desc; 
		var predictionAll = jsonObj[i].attrs.prediction_all;
		var live_match_id = jsonObj[i].attrs.live_match_id;
		var match_key = jsonObj[i].attrs.match_key;
		var match_time = jsonObj[i].attrs.match_time;
		match_time = match_time.substring(0,10);
		var home_team_enname = jsonObj[i].attrs.home_team_enname;
		var away_team_enname = jsonObj[i].attrs.away_team_enname;
		var home_team_id = jsonObj[i].attrs.home_team_id;
		var away_team_id = jsonObj[i].attrs.away_team_id;
		var year_show = jsonObj[i].attrs.year_show;
		
		var urlLink = '';
		if(live_match_id!=0){
			if(home_team_id==undefined || home_team_id==''){
				urlLink = '&nbsp;<a style="color:#ddd;" target="_blank" href="live-'+match_time+'-'+home_team_enname+'-vs-'+away_team_enname+'-'+live_match_id+'.html">详情...</a>';
			}else{
				urlLink = '&nbsp;<a style="color:#ddd;" target="_blank" href="match-'+home_team_enname+'-vs-'+away_team_enname+'_'+match_key+'.html">详情...</a>';
			}
		}
		
		Lobibox.notify(
				  'success',
				  {
					  title: false,   
					  msg: leagueName+' '+homeName+' vs '+awayName+urlLink+'<br><span title="'+predictionAll+'">'+predictionDesc+'</span>',  
					  icon: false, 
					  delay: 12000, 
					  position: "bottom right"
				  }
		); 
		i++;
		if(i==jsonObj.length){
			i=0;
		}
	}
	
	$(function(){
		resizeBackground();
		
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
	                url: 'listMore',
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
	                    $('.image').viewer(
	        					{toolbar:false, 
	        					zIndex:20000,
	        					shown: function() {
	        						$(".viewer-canvas").attr("data-action","mix");
	        					 }
	        				 }
	        			);
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
	                url: 'listMore',
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
	                    $('.image').viewer(
	        					{toolbar:false, 
	        					zIndex:20000,
	        					shown: function() {
	        						$(".viewer-canvas").attr("data-action","mix");
	        					 }
	        				 }
	        			);
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
		var winWidth = $(window).width();
		var width;
		var height;
		if (winWidth<992) {
			  var percent = (parseFloat($(this).attr("height"))/parseFloat($(this).attr("width"))).toFixed(2);
			  width = winWidth-70;
			  height = width * percent;
		} 
		
		var create_time = '';
		var date_str = '';
		if(fun.create_time){
			create_time = fun.create_time.substring(0,10);
			date_str = create_time.substring(0,10);
		}
		var strhtml = "<div class=\"col-sm-12 col-xs-12\">";
		strhtml+="		   					<div class=\"mob-author\">";
		strhtml+="		                               <div class=\"author-face\">";
		if(fun.type==1){
			strhtml+="											<img src=\"assets/image/page/logo-small.png\">";
		}else{
			strhtml+="											<a href=\"player-"+fun.player_name_en+"-"+fun.player_id+".html\" target=\"_self\"><img src=\""+fun.player_image+"\"></a>";
		}
		strhtml+="		                            </div>";
		if(fun.type==1){
			strhtml+="										<a href=\"fun.html\" target=\"_blank\" class=\"mob-author-a\">";
			strhtml+="			                                <span class=\"author-name\">趣点足球网</span>";
			strhtml+="			                            </a>";
		}else{
			strhtml+="										<a href=\"player-"+fun.player_name_en+"-"+fun.player_id+".html\" target=\"_self\" class=\"mob-author-a\">";
			strhtml+="			                                <span class=\"author-name\">"+fun.player_name+"</span>";
			strhtml+="			                            </a>";
		}
		strhtml+="		                            <span class=\"author-name\">";
		strhtml+="										&nbsp;"+create_time;
		strhtml+="									</span>";
		strhtml+="		                    </div>";
		strhtml+="			    	</div>";
		strhtml+="			    	<div class=\"col-sm-12 col-xs-12 content-title\" style=\"margin-top:10px;padding-left:45px;\">";
		if(fun.type==1){
			strhtml+="							<a href=\"fdetail-"+date_str+"-"+fun.title_en+"-"+fun.id+".html\" target=\"_self\" ><span class=\"summary-mobile\">"+fun.summary+"</span></a>";
		}else{
			strhtml+="							<span class=\"summary-mobile\">"+fun.summary+"</span>";
		}
		strhtml+="					</div>";
		strhtml+="			    	<div class=\"col-sm-12 col-xs-12 content-title\" style=\"margin-top:8px;padding-left:45px;\">";
		if(fun.type==1){
			strhtml+="							<a href=\"fdetail-"+date_str+"-"+fun.title_en+"-"+fun.id+".html\" target=\"_self\" ><img src=\""+fun.image_small+"\" class=\"img-responsive\" alt=\""+fun.title+"\" /></a>";
		}else{
			if(fun.image_big && fun.image_big!=''){
				strhtml+="<div class='mobile-div-img' data-bg-sm='"+fun.image_big+"' width='"+fun.image_big_width+"' height='"+fun.image_big_height+"' style='width:"+width+"px;height:"+height+"px;'><img class='image' src='"+fun.image_big+"' style='width:"+width+"px;height:"+height+"px;' /></div>";
			}
		}
		strhtml+="					</div>";
		strhtml+="					<div class=\"col-sm-12 col-xs-12\" style=\"padding-left:0px;padding-right:0px;\">";
		strhtml+="						<div class=\"index-line\"></div>";
		strhtml+="					</div>";
			
		return strhtml;
	}
	
	
	
	</script>
		
	
</body>	

