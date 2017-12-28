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
	<meta name="keywords" content="${enname},soccer player ${enname},football player ${enname},球员${player.name},${player.name}的说说,${player.name}的动态,${player.name}的资讯,${player.name}的生活,${player.name}的图片" />
	<meta name="description" content="趣点足球网为球迷们提供${player.name}（${enname}）最新的生活动态，资讯近况。" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/dropload/dropload.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/viewer/viewer.min.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/map/map.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - ${player.nationality} - ${player.name}|${enname}|${player.name}转会|${player.name}的近况|${player.name}职业生涯</title>
    <style>
    	#article_div ul{list-style:none;count-reset:count; }
    	#article_div li{line-height:30px;height:30px;}
    	#article_div li:before{content:"• "; color:#ccc;}
    </style>
   
</head>

<body>
<div id="all_content">
	<div class="container">
		<%@ include file="/common/menu.jsp"%>
	    
	    <!-- 导航 -->
	    <div class="row visible-sm visible-xs" style="margin-top:40px;">
			<div id="main_content" style="min-height:10px;" class="col-sm-12 col-xs-12">		
				<div class="bread">
					<a href="/" target="_self">首页</a>&nbsp;&gt;&nbsp;<a href="/data.html" target="_self">数据</a>&nbsp;&gt;&nbsp;${player.name}
				</div>
			</div>
		</div>
		
		<!-- 移动端内容开始 -->
		<div class="row visible-sm visible-xs" style="font-size:12px;color:grey;padding-bottom: 130px;">
			<div class="col-sm-5 col-xs-5">
				<img src="${player.img_big_local}" class="img-responsive" alt="${player.name}" title="${player.name}" />
			</div>
		
			<div class="col-sm-7 col-xs-7" style="margin-top:10px;">${player.nationality} ${player.birthday}（${player.age}岁）</div>
			<div class="col-sm-7 col-xs-7" style="margin-top:10px;">${player.height}&nbsp;·&nbsp;${player.weight}&nbsp;<c:if test="${!empty player.foot}">·&nbsp;惯用${player.foot}</c:if></div>
			<c:if test="${!empty player.team_id}">
				<div class="col-sm-7 col-xs-7" style="margin-top:10px;"><a href="team-${player.team_name_en}-${player.team_id}.html" target="_self" title="${player.team_name}" style="color:grey;">${player.team_name}</a>&nbsp;·&nbsp;${player.position}&nbsp;·&nbsp;${player.number}号</div>
			</c:if>
			
			<div class="col-sm-7 col-xs-7" style="margin-top:10px;">赛季数据：
				<c:if test="${player.goal_count!=0}">
	      			<span title="进球数：${player.goal_count}"><img src="assets/pages/img/goal-small.png" style="margin-top:-5px;" /> <b>${player.goal_count}</b></span>
	      			&nbsp;
	      		</c:if>
	      		<c:if test="${player.assists_count!=0}">
	      			<span title="助攻数：${player.assists_count}"><img src="assets/pages/img/goal-assists.png" style="margin-top:-5px;" /> <b>${player.assists_count}</b></span>
	      		</c:if>
			</div>
			
			<c:if test="${!empty NO_SAY}">
				<div class="col-sm-12 col-xs-12" style="margin-top:15px;">
					<div class="row visible-sm visible-xs">
						<div class="col-sm-12 col-xs-12" >
						<nobr><span style="font-weight:bold;">${player.name}</span>目前还没说说，去瞅瞅<b>${leagueName}</b>其他人 <img src="assets/image/page/smile.png"  style="width:32px;height:32px;"/></nobr>
						</div>
					</div>
				</div>
			</c:if>
			<div class="col-sm-12 col-xs-12" style="padding-left:0px;padding-right:0px;">
				<div class="index-line"></div>
			</div>
			<div class="col-sm-12 col-xs-12">
				<div id="list_content" class="row">
				    <c:forEach items="${sayPage.list}" var="say" varStatus="status">
					    	<div class="col-sm-12 col-xs-12" style="margin-top:10px;">
				   					<div class="mob-author">
				                            <div class="author-face">
						                        <a href="player-${say.player_name_en}-${say.player_id}.html" target="_self"><img src="${say.player_img_local}"></a>
				                            </div>
				                            
				                            <a href="player-${say.player_name_en}-${say.player_id}.html" target="_self" class="mob-author-a">
				                                <span class="author-name">${say.player_name}</span>
				                            </a>
				                            
				                            <span class="author-name say-info">
												&nbsp;<fmt:formatDate value="${say.create_time}" pattern="yyyy-MM-dd HH:mm"/>
											</span>
				                    </div>
					    	</div>
					    	<!-- 内容 -->
					    	<div class="col-sm-12 col-xs-12 content-title" style="padding-left:45px;">
								<span class="summary" style="color:#292f33;">${say.content}</span>
							</div>
					    	<!-- 图片 -->
					    	<c:if test="${!empty say.image_big}">
						    	<div class="col-sm-12 col-xs-12 content-title" style="margin-top:8px;padding-left:45px;">
									<img src="${say.image_big}" class="img-responsive image" style="height:220px;" alt="${say.content}" title="${say.content}"/>
								</div>
							</c:if>
							<div class="col-sm-12 col-xs-12" style="padding-left:0px;padding-right:0px;">
								<div class="index-line"></div>
							</div>
				    </c:forEach>
			    </div>
		    </div>
			
		</div>
		<!-- 移动端内容结束 -->
	</div>
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:70px;">
		<div id="main_content" style="min-height:10px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="bread">
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;<a href="/data.html" target="_blank">数据</a>&nbsp;&gt;&nbsp;${player.name}
			</div>
		</div>
	</div>
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:20px;padding-bottom: 130px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-6 col-lg-offset-2 col-md-6 col-md-offset-2">		
			<div class="row">
				<div class="col-lg-12 col-md-12" >
					<div class="col-lg-3 col-md-3">
						<img src="${player.img_big_local}" style="width:150px;height:150px;" alt="${player.name}" title="${player.name}"/>
					</div>
					<div class="col-lg-9 col-md-9">
						<div class="row">
							<div class="col-lg-6 col-md-6">
								<div class="row">
									<div class="col-lg-12 col-md-12" style="line-height:32px;">${player.first_name}·${player.last_name}</div>
								</div>
								<div class="row">
									<div class="col-lg-12 col-md-12" style="line-height:32px;">
										${player.nationality} ${player.birthday}（${player.age}岁）
									</div>
								</div>
								<div class="row">
									<div class="col-lg-12 col-md-12" style="line-height:32px;">
										${player.height}&nbsp;·&nbsp;${player.weight}&nbsp;<c:if test="${!empty player.foot}">·&nbsp;惯用${player.foot}</c:if>
									</div>
								</div>
								<div class="row">
									<div class="col-lg-12 col-md-12" style="line-height:32px;">
										<c:if test="${!empty player.team_id}">
											<span class="grey-title"><a href="team-${player.team_name_en}-${player.team_id}.html" target="_blank" title="${player.team_name}"><img src="assets/image/soccer/teams/150x150/${player.team_id}.png" style="width:25px;height:25px;"/>&nbsp;${player.team_name}</a></span>
										</c:if>
										&nbsp;·&nbsp;${player.position}<c:if test="${!empty player.number}">&nbsp;·&nbsp;${player.number}号</c:if>
									</div>
								</div>
								<div class="row">
									<div class="col-lg-12 col-md-12" style="line-height:32px;">
										<c:if test="${!empty fifa}">
										周薪：${fifa.wage}&nbsp;
										</c:if>
										赛季数据：
										<c:if test="${player.goal_count!=0}">
							      			<span title="进球数：${player.goal_count}"><img src="assets/pages/img/goal-small.png" style="margin-top:-5px;" /> <b>${player.goal_count}</b></span>
							      		</c:if>
							      		<c:if test="${player.goal_count!=0 && player.assists_count!=0}">
							      		&nbsp;
							      		</c:if>
							      		<c:if test="${player.assists_count!=0}">
							      			<span title="助攻数：${player.assists_count}"><img src="assets/pages/img/goal-assists.png" style="margin-top:-5px;" /> <b>${player.assists_count}</b></span>
							      		</c:if>
									</div>
								</div>
								
							</div>
							<div class="col-lg-6 col-md-6">
								<div id="radardiv" style="width: 220px; height: 160px; -webkit-tap-highlight-color: transparent; user-select: none; background: transparent;"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-lg-12 col-md-12">
						<div class="col-lg-4 col-md-4" style="margin-top:10px;">国际声誉： <c:forEach var="i" begin="1" end="${fifa.inter_rep}"><i class="fa fa-star gold"></i></c:forEach></div>
						<div class="col-lg-4 col-md-4" style="margin-top:10px;">逆足能力：<c:forEach var="i" begin="1" end="${fifa.unuse_foot}"><i class="fa fa-star gold"></i></c:forEach></div>
						<div class="col-lg-4 col-md-4" style="margin-top:10px;">花式技巧：<c:forEach var="i" begin="1" end="${fifa.trick}"><i class="fa fa-star gold"></i></c:forEach></div>
						<div class="col-lg-4 col-md-4" style="margin-top:10px;">积极性：${fifa.work_rate}</div>
						<div class="col-lg-4 col-md-4" style="margin-top:10px;">身价：${fifa.market_value}</div>
						<div class="col-lg-4 col-md-4" style="margin-top:10px;">违约金：${fifa.release_clause}</div>
						<div class="col-lg-4 col-md-4" style="margin-top:10px;">合同到期：${fifa.contract}</div>
						<div class="col-lg-4 col-md-4" style="margin-top:10px;">综合能力：<span class="label label-success">${fifa.overall_rate}</span></div>
						<div class="col-lg-4 col-md-4" style="margin-top:10px;">潜力：<span class="label label-success">${fifa.potential}</span></div>
				</div>
				
			</div>
			
			
			
			<c:if test="${!empty NO_SAY}">
			<div class="row">
				<div class="col-lg-12 col-md-12" style="margin-top:15px;">
					<div class="row">
						<div class="col-lg-12 col-md-12" style="font-size:16px;">
						<nobr><span style="font-weight:bold;">${player.name}</span>目前还没发表说说，去瞅瞅<b>${leagueName}</b>其他人的吧 <img src="assets/image/page/smile.png"  style="width:32px;height:32px;"/></nobr>
						</div>
					</div>
				</div>
			</div>
			</c:if>
			
			<div class="row">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"  style="margin-top:15px;">
					<c:forEach items="${sayPage.list}" var="say" varStatus="status">
						<div class="row" style="padding-left:15px;padding-right:15px;">
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="border:1px solid #E3E7EA;${status.index!=0?'border-top:0;':''}padding:10px;padding-bottom:10px;">
								<div class="row">
									<div class="col-lg-1 col-md-1 hidden-sm hidden-xs">
										<a href="player-${say.player_name_en}-${say.player_id}.html" style="color:#292f33;" target="_blank"><img src="${say.player_img_local}" style="width:48px;height:48px;" alt="${say.player_name}" title="${say.player_name}"/></a>
									</div>
									<div class="col-lg-11 col-md-11 col-sm-12 col-xs-12" >
										<div class="row">
											<div class="col-lg-12 col-md-12 say-info">
												<span style="display:block;font-weight:bold;color:#292f33;float:left;"><a href="player-${say.player_name_en}-${say.player_id}.html" style="color:#292f33;" target="_blank">${say.player_name}</a></span>
												<span style="display:block;color:#8899a6;font-size:13px;float:left;cursor:pointer;" title="<fmt:formatDate value="${say.create_time}" pattern="yyyy-MM-dd HH:mm"/>"> - <fmt:formatDate value="${say.create_time}" pattern="MM月dd日"/></span>
											</div>
										</div>
										
										<div class="row">
											<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 ">
												<span style="color:#292f33;font-size:14px;">${say.content}</span>
											</div>
										</div>
										
										<c:if test="${!empty say.image_big}">
											<div class="row">
												<div class="col-lg-12 col-md-12" style="margin-top:10px;">
													<img src="${say.image_big}" class="img-responsive img-rounded image" style="height:300px;cursor:pointer;" alt="${say.content}" title="${say.player_name} - ${say.content}" />
												</div>
											</div>
										</c:if>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
					
					<c:if test="${empty NO_SAY}">
						<c:if test="${sayPage.totalPage>1}">
								<div class="col-lg-12 col-md-12 " style="margin-top:20px;">
									<div class="scott pull-right">
										<a href="player-${player.en_url}-${player.id}_1.html" title="首页"> &lt;&lt; </a>
										
										<c:if test="${sayPage.pageNumber == 1}">
											<span class="disabled"> &lt; </span>
										</c:if>
										<c:if test="${sayPage.pageNumber != 1}">
											<a href="player-${player.en_url}-${player.id}_${sayPage.pageNumber - 1}.html" > &lt; </a>
										</c:if>
										<c:if test="${sayPage.pageNumber > 8}">
											<a href="player-${player.en_url}-${player.id}_1.html">1</a>
											<a href="player-${player.en_url}-${player.id}_2.html">2</a>
											...
										</c:if>
										<c:if test="${!empty pageUI.list}">
											<c:forEach items="${pageUI.list}" var="pageNo">
												<c:if test="${sayPage.pageNumber == pageNo }">
													<span class="current">${pageNo}</span>
												</c:if>
												<c:if test="${sayPage.pageNumber != pageNo }">
													<a href="player-${player.en_url}-${player.id}_${pageNo}.html">${pageNo}</a>
												</c:if>
											</c:forEach>
										</c:if>
										<c:if test="${(sayPage.totalPage - sayPage.pageNumber) >= 8 }">
											...
											<a href="player-${player.en_url}-${player.id}_${sayPage.totalPage - 1}.html">${sayPage.totalPage - 1}</a>
											<a href="player-${player.en_url}-${player.id}_${sayPage.totalPage}.html">${sayPage.totalPage}</a>
										</c:if>
										
										<c:if test="${sayPage.pageNumber == sayPage.totalPage}">
											<span class="disabled"> &gt; </span>
										</c:if>
										<c:if test="${sayPage.pageNumber != sayPage.totalPage}">
											<a href="player-${player.en_url}-${player.id}_${sayPage.pageNumber + 1}.html"> &gt; </a>
										</c:if>
										
										<a href="player-${player.en_url}-${player.id}_${sayPage.totalPage}.html" title="尾页"> &gt;&gt; </a>
									</div>
								</div>
						</c:if>
					</c:if>
					
					<c:if test="${!empty NO_SAY}">
						<div class="pull-right say-info" style="margin-top:10px;">
							<a href="/" target="_blank" title="去首页看看">去首页看看&gt;&gt;</a>
						</div>
					</c:if>
				</div>
				
			</div>
		</div>
		<div  style="min-height:20px;" class="col-lg-4 col-md-4">		
			<div id="article_div" class="col-lg-12 col-md-12" >
					
					<c:if test="${!empty lstTransfer}">
						<p>转会情况<span style="color:#aaa;">（M：百万欧元）</span></p>
						<table >
						  <tbody>
						  	<c:forEach items="${lstTransfer}" var="transfer">
							    <tr>
							      	<td class="grey-title" style="height:25px;line-height:25px;"><fmt:formatDate value="${transfer.date}" pattern="yy/MM/dd"/> ${transfer.from_team} → ${transfer.to_team} ${transfer.value}${transfer.extra}<c:if test="${(empty transfer.extra) && transfer.value!='租借' &&  transfer.value!='免签' &&  transfer.value!='无信息'}">万欧元</c:if></td>
							    </tr>
						     </c:forEach>
						  </tbody>
						</table>
					</c:if>
					
					<c:if test="${!empty lstNews}">
						<p style="margin-top:10px;">相关新闻</p>
						<table >
						  <tbody>
						  	<c:forEach items="${lstNews}" var="news">
							    <tr>
							      	<td class="text_cut grey-title" style="width:285px;height:25px;line-height:25px;" title="<fmt:formatDate value="${news.create_time}" pattern="yyyy-MM-dd"/> ${news.title}"><a target="_blank" href="/fdetail-<fmt:formatDate value="${news.create_time}" pattern="yyyy-MM-dd"/>-${news.title_en}-${news.id}.html"><fmt:formatDate value="${news.create_time}" pattern="MM/dd"/> ${news.title}</a></td>
							    </tr>
						     </c:forEach>
						  </tbody>
						</table>
					</c:if>
					
				</div>
		</div>
		
		
	</div>
	
	<%@ include file="/common/footer.jsp"%>		
	</div>
	
	<script src="assets/global/plugins/dropload/dropload.min.js" type="text/javascript"></script>
	<script src="assets/global/plugins/viewer/viewer-jquery.min.js" type="text/javascript"></script>
	
	//https://cdn.bootcss.com/echarts/3.8.5/echarts.common.min.js
	<script src="assets/global/scripts/echarts.js"></script>
	
	<script>
	
	$(function(){
		loadRadar();
		
		$('.image').viewer({toolbar:false,
			zIndex:20000,
			shown: function() {
				$(".viewer-canvas").attr("data-action","mix");
			 }});
		
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
	                data: {pageNo:1,id:'${player.id}'},
	                url: 'say/listPlayerMore',
	                dataType: 'json',
	                success: function(data){
	                    var strhtml = '<div class=\"col-sm-12 col-xs-12\" style=\"margin-top:10px;height:1px;\"></div>';
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
	                    $('.image').viewer({toolbar:false,
	            			zIndex:20000,
	            			shown: function() {
	            				$(".viewer-canvas").attr("data-action","mix");
	            			 }});
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
	            	data: {pageNo:pageNo,id:'${player.id}'},
	                url: 'say/listPlayerMore',
	                dataType: 'json',
	                success: function(data){
	                    var arrLen = data.length;
	                    if(arrLen > 0){
	                    	var strhtml = '<div class=\"col-sm-12 col-xs-12\" style=\"margin-top:10px;height:1px;\"></div>';
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
	                    $('.image').viewer({toolbar:false});
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
		strhtml+="								</span>";
		strhtml+="	                    </div>";
		strhtml+="		    	</div>";
		strhtml+="		    	<div class=\"col-sm-12 col-xs-12 content-title\" style=\"padding-left:45px;\">";
		strhtml+="					<span class=\"summary\" style=\"color:#292f33;\">"+say.content+"</span>";
		strhtml+="				</div>";
		if(say.image_big && say.image_big!=''){
			strhtml+="			    	<div class=\"col-sm-12 col-xs-12 content-title\" style=\"margin-top:8px;padding-left:45px;padding-right:20px;\">";
			strhtml+="						<img src=\""+say.image_big+"\" class=\"img-responsive image\" style=\"height:220px;\" alt=\""+say.content+"\" title=\""+say.content+"\"/>";
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
	
	var overallRating = '综合能力';
	var potential = '潜力';
	var labelPAC = '速度';
	var labelSHO = '射门';
	var labelPAS = '传球';
	var labelDRI = '盘带';
	var labelDEF = '防守';
	var labelPHY = '力量';
	var pointPAC = '${fifa.pac}';
	var pointSHO = '${fifa.sho}';
	var pointPAS = '${fifa.pas}';
	var pointDRI = '${fifa.dri}';
	var pointDEF = '${fifa.def}';
	var pointPHY = '${fifa.phy}';
	function loadRadar(){
		var myRadar = echarts.init(document.getElementById("radardiv"));
		myRadar.setOption({
		    radar: {
		        indicator: [{
		            name: labelSHO + '\n\r' + pointSHO,
		            max: 99
		        },
		        {
		            name: labelPAC + '\n\r' + pointPAC,
		            max: 99
		        },
		        {
		            name: labelPHY + '\n\r' + pointPHY,
		            max: 99
		        },
		        {
		            name: labelDEF + '\n\r' + pointDEF,
		            max: 99
		        },
		        {
		            name: labelDRI + '\n\r' + pointDRI,
		            max: 99
		        },
		        {
		            name: labelPAS + '\n\r' + pointPAS,
		            max: 99
		        }],
		        startAngle: 60,
		        nameGap: 10
		    },
		    series: [{
		        type: 'radar',
		        symbol: 'emptyCircle',
		        symbolSize: 5,
		        data: [[pointSHO, pointPAC, pointPHY, pointDEF, pointDRI, pointPAS]],
		        itemStyle: {
		            normal: {
		                color: '#239454',
		                borderWidth: 2
		            }
		        },
		        areaStyle: {
		            normal: {
		                opacity: 0.1
		            }
		        }
		    }]
		},
		true);
	}
	
	</script>
		
	
</body>	

