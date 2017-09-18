<%@ page contentType="text/html;charset=GB2312"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	response.setHeader("Pragma","No-cache");//HTTP     1.1
	response.setHeader("Cache-Control","no-cache");//HTTP     1.0
	response.setHeader("Expires","0");//��ֹ��proxy!
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
	<meta name="keywords" content="�������,����ʱ��,�������,��������,�������,��������,��ɭ�ɱ���,���Ǳ���,�����ֱ���,�ж�������,���ʱ���,����ͼ˹����,����ʥ�ն�������" />
	<meta name="description" content="Ȥ��������Ϊ�������ṩ����������µ��������Ԥ�棬���������" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
    <title>Ȥ�������� - �������|����ʱ��|�������|����|����|����|��ɭ��|����|������|�ж���|����|����ͼ˹|����ʥ�ն���</title>
    <style type="text/css">
    	.title2{line-height:30px;background:#F1F6FB;padding-left:5px;font-weight:bold;font-size:12px;text-indent:5px;border-bottom:1px dotted #CCC;}
    </style>
</head>

<body>
<div id="all_content">	
	<div class="container">
		<div class="row menu_bg clear_row_margin hidden-sm hidden-xs" >
			<div id="main_nav" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2">	
				<div>
					<div class="logo_div">
						<a href="" title="��ҳ">Ȥ��������</a>
					</div>
					<ul style="float:left;">
						<li class="menu_width"><a href="">��ҳ</a></li>
						<li class="menu_width"><a href="fun.html">Ȥ��</a></li>
						<li class="menu_width"><a href="live.html">ֱ��</a></li>
						<li class="menu_width menu_sel"><a href="bifen.html">�ȷ�</a></li>
						<li class="menu_width"><a href="data.html">����</a></li>
					</ul>
				</div>
			</div>
		</div>
		
		<div class="row menu_link navbar-fixed-top  visible-sm visible-xs" style="background:#00A50D;min-height:35px;color:white;">
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/" target="_self"><span class="wwqk_menu">��ҳ</span></a>
	       	</div>
	       	<div class="col-xs-3 col-sm-3 wwqk_menu_wh" >
	       		<a href="/fun.html" target="_self"><span class="wwqk_menu">Ȥ��</span></a>
	       	</div>
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/live.html" target="_self"><span class="wwqk_menu">ֱ��</span></a>
	       	</div>
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/bifen.html" target="_self"><span class="wwqk_menu dline">�ȷ�</span></a>
	       	</div>
	       	<div class="col-xs-3 col-sm-3 wwqk_menu_wh" >
	       		<a href="/data.html" target="_self"><span class="wwqk_menu">����</span></a>
	       	</div>
	    </div>
	</div>
	<!-- �ƶ������ݿ�ʼ -->
    <div id="mobile_div" class="row visible-sm visible-xs" style="padding-left:15px;padding-right:15px;margin-top:35px;padding-bottom: 130px;">
    	
    </div>
    <!-- �ƶ������ݽ��� -->
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:70px;">
		<div id="main_content" style="min-height:10px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2">		
			<div class="bread">
				��ǰλ�ã�<a href="/" target="_blank">��ҳ</a>&nbsp;&gt;&nbsp;����
			</div>
		</div>
	</div>
	
	<div class="row clear_row_margin hidden-sm hidden-xs" style="margin-top:8px;padding-bottom: 130px;">
		<div id="main_content" style="min-height:20px;" class="col-lg-10 col-lg-offset-2 col-md-10 col-md-offset-2">		
			<div class="col-lg-12 col-md-12" style="padding-left:0px;">
				<div id="pc_div" style="float:left;height:20000px;width:720px;">
					
				</div>
				<div id="chat_div" style="float:left;margin-left:5px;height:455px;width:305px;border:1px solid #c0c0c0;">
					
				</div>
			</div>
		</div>
	</div>
	
	
	<div class="row clear_row_margin footer">
		<div style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-7 col-md-7">
				<div class="row">
					<div class="col-lg-12 col-md-12" style="margin-top:15px;">
						Ȥ�������� - ������˼��������վ&nbsp;<a href="http://www.yutet.com/sitemap.html" target="_blank" style="color:white;">��վ��ͼ</a>
						<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1261131271'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s11.cnzz.com/z_stat.php%3Fid%3D1261131271%26show%3Dpic' type='text/javascript'%3E%3C/script%3E"));</script>
					</div>
					<div class="col-lg-12 col-md-12" style="margin-top:10px;">
						Copyright &copy; Ȥ��������   ��ICP��16048166��-4
					</div>
					<div class="col-lg-12 col-md-12 hidden-sm hidden-xs" style="margin-top:10px;color:#222;">
						��������������վ������Դ�ڻ�������������Ȩ������ϵQQ��920841228
					</div>
				</div>
			</div>
			<div class="col-lg-5 col-md-5 hidden-sm hidden-xs" style="margin-top:15px;">
				<div class="row">
					<div class="col-lg-12 col-md-12" >
						<img src="assets/pages/img/rich888.png" class="img-responsive img-rounded" />
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="scroll-to-top">
	    <i class="icon-arrow-up"></i>
	</div>
	
	<script src="https://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
	<script type="text/javascript">
	//Handles the go to top button at the footer
	var handleGoTop = function() {
	    var offset = 300;
	    var duration = 500;
	    if (navigator.userAgent.match(/iPhone|iPad|iPod/i)) { // ios supported
	        $(window).bind("touchend touchcancel touchleave", function(e) {
	            if ($(this).scrollTop() > offset) {
	                $('.scroll-to-top').fadeIn(duration);
	            } else {
	                $('.scroll-to-top').fadeOut(duration);
	            }
	        });
	    } else { // general 
	        $(window).scroll(function() {
	            if ($(this).scrollTop() > offset) {
	                $('.scroll-to-top').fadeIn(duration);
	            } else {
	                $('.scroll-to-top').fadeOut(duration);
	            }
	        });
	    }
	
	    $('.scroll-to-top').click(function(e) {
	        e.preventDefault();
	        $('html, body').animate({
	            scrollTop: 0
	        }, duration);
	        return false;
	    });
	};
	
	var pc_frame_url = '<iframe id="pc_iframe" name="pc_iframe" height="20000" src="http://live1.bet007.com/live.aspx?Edition=1&amp;lang=0&amp;ad=Ȥ��������&amp;adurl=http://www.yutet.com&amp;color=F7F3DE&amp;sound=0" frameborder="0" width="720"></iframe>';
	var chat_frame_url = '<div class="title2" style="text-align:left;">������</div><iframe id="pc_chat_iframe" name="pc_chat_iframe" src="http://chat.17jc.cc/bo360/chat?h=470" marginheight="0" marginwidth="0" frameborder="0" width="298" height="420" scrolling="no" allowtransparency="yes" style="margin-top:2px;"></iframe>';
	var m_bifen_url = '<iframe id="mobile_iframe" name="mobile_iframe" scrolling="no" frameborder="0" align="center" src="http://m.win007.com/" style="width:100%;height: 20000px;" rel="nofollow" border="0"></iframe>';
	
	function getNode(id) {	return document.getElementById(id);}
	function dw(str){	document.write(str);}
	
	$(function(){
		  handleGoTop();
		  setIframeContent();
	});
	
	window.onresize = function(){
		var winWidth = $(window).width();
		if (winWidth<992) {
			  $("#pc_div").html("");
			  $("#chat_div").html("");
			  $("#mobile_div").html(m_bifen_url);
		}else{
			 $("#pc_div").html(pc_frame_url);
		     $("#chat_div").html(chat_frame_url);
		     $("#mobile_div").html("");
		}
	};
	
	function setIframeContent(){
		  if(!(navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
			  $("#pc_div").html(pc_frame_url);
			  $("#chat_div").html(chat_frame_url);
			  $("#mobile_div").html("");
		  }else{
			  $("#pc_div").html("");
			  $("#chat_div").html("");
			  $("#mobile_div").html(m_bifen_url);
		  }
	}
	
	</script>
	
	</div>
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

