<%@ page contentType="text/html;charset=GB2312"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	response.setHeader("Pragma","No-cache");//HTTP     1.1
	response.setHeader("Cache-Control","no-cache");//HTTP     1.0
	response.setHeader("Expires","0");//防止被proxy!
%>
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
	<meta name="keywords" content="足球比分,球探比分,足球比分直播,即时比分,聊天室" />
	<meta name="description" content="趣点足球网为球迷们提供足球比分、即时比分和比赛结果，在线聊天室侃球" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="https://cdn.bootcss.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - 足球比分直播|球探比分|bet007足球比分|聊天侃球</title>
    <style type="text/css">
    	.title2{line-height:30px;background:#F1F6FB;padding-left:5px;font-weight:bold;font-size:12px;text-indent:5px;border-bottom:1px dotted #CCC;}
    </style>
</head>

<body>
<div id="all_content">	
	<div class="container">
		<%@ include file="/common/menu-bifen.jsp"%>
	</div>
	<!-- 移动端内容开始 -->
    <div id="mobile_div" style="width:100%; margin-top:35px;padding-bottom:30px;">
    	
    </div>
    <!-- 移动端内容结束 -->
	
	<div class="row clear_row_margin hidden-sm hidden-xs" >
		<div id="main_content" style="min-height:10px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2">		
			<div class="bread">
				当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;比分
			</div>
		</div>
	</div>
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:8px;padding-bottom: 130px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-10 col-lg-offset-2 col-md-10 col-md-offset-2">		
			<div class="col-lg-12 col-md-12" style="padding-left:0px;">
				<div id="pc_div" style="float:left;height:20000px;width:690px;">
					
				</div>
				<div id="chat_div" style="float:left;margin-left:5px;height:455px;width:300px;border:1px solid #c0c0c0;">
					
				</div>
				<div id="cp_div" style="float:left;width:300px;margin-left:5px;margin-top:10px;border:1px solid #c0c0c0;min-height:70px;">
					<div class="title2" style="text-align:left;"><span class="headactions">最新彩票开奖结果 <a id="toggleA" href="javascript:;" onclick="toggleCp();">更多信息...</a></span></div>
					<div id="cp" style="min-height:70px;"></div>
				</div>
			</div>
		</div>
	</div>
	
	<%@ include file="/common/footer-bifen.jsp"%>		
	
	</div>
	
	<script>
	
	var pc_frame_url = '<iframe id="pc_iframe" name="pc_iframe" height="20000" src="http://live1.bet007.com/live.aspx?Edition=1&amp;lang=0&amp;ad=豹步正品足球鞋男ag长钉成人训练比赛&amp;adurl=http://m.tb.cn/h.Z0dMcWl&amp;color=DCEFFF&amp;sound=0" frameborder="0" width="690"></iframe>';
	var chat_frame_url = '<div class="title2" style="text-align:left;">侃球室</div><iframe id="pc_chat_iframe" name="pc_chat_iframe" src="http://www5.cbox.ws/box/?boxid=896554&boxtag=nner91&sec=main" marginheight="0" marginwidth="0" frameborder="0" width="297" height="420" scrolling="no" allowtransparency="yes" style="margin-top:2px;"></iframe>';
	//http://m.310win.com/Analysis/Football.aspx?TypeID=101
	var m_bifen_url = '<iframe id="mobile_iframe" name="mobile_iframe" scrolling="no" frameborder="0" align="center" src="http://m.188bifen.com/" style="width:100%;height: 20000px;" rel="nofollow" border="0"></iframe>';
	var cp_url = '<iframe frameBorder="0" scrolling="no" align="center" width="297" height="65" rel="nofollow" src="http://www.360zhibo.com/kjgd2013.html"></iframe>';
	var cp_more_url = '<iframe frameBorder="0" scrolling="no" align="center" width="297" height="670" rel="nofollow" src="http://www.360zhibo.com/kjgd3013.html"></iframe>';
	
	var isAll = false;
	function toggleCp(){
		if(isAll){
			isAll = false;
			document.getElementById('cp').innerHTML = cp_url;
			$("#toggleA").html("更多信息...");
		}else{
			isAll = true;
			document.getElementById('cp').innerHTML = cp_more_url;
			$("#toggleA").html("折叠...");
		}
	}
	
	$(function(){
		  setIframeContent();
	});
	
	window.onresize = function(){
		isAll = false;
		var winWidth = $(window).width();
		if (winWidth<992) {
			window.location.href = "/mbifen.html";
			  /**
			  $("#pc_div").html("");
			  $("#chat_div").html("");
			  $("#cp").html("");
			  $("#mobile_div").html(m_bifen_url);
			  */
		}else{
			 $("#pc_div").html(pc_frame_url);
		     $("#chat_div").html(chat_frame_url);
		     $("#cp").html(cp_url);
		     $("#mobile_div").html("");
		}
	};
	
	function setIframeContent(){
		  isAll = false;
		  if(!(navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
			  $("#pc_div").html(pc_frame_url);
			  $("#chat_div").html(chat_frame_url);
			  $("#cp").html(cp_url);
			  $("#mobile_div").html("");
		  }else{
			  window.location.href = "/mbifen.html";
			  /**
			  $("#pc_div").html("");
			  $("#chat_div").html("");
			  $("#cp").html("");
			  $("#mobile_div").html(m_bifen_url);
			  */
		  }
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

