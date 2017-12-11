<%@ page contentType="text/html;charset=UTF-8"%>
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
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<meta content="telephone=no" name="format-detection">
	<meta name="keywords" content="足球比分,球探比分,足球比分直播,即时比分,聊天室" />
	<meta name="description" content="趣点足球网为球迷们提供足球比分、即时比分和比赛结果，在线聊天室侃球" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css?v=2.0" rel="stylesheet" type="text/css" />
    <link href="${ctx}/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - 足球比分直播|球探比分|bet007足球比分|聊天侃球</title>
    <style type="text/css">
    	.title2{line-height:30px;background:#F1F6FB;padding-left:5px;font-weight:bold;font-size:12px;text-indent:5px;border-bottom:1px dotted #CCC;}
    </style>
</head>

<body>
<div id="all_content">	
	<div class="container">
		<%@ include file="/common/menu.jsp"%>
	</div>

		 <ul id="myTab" class="nav nav-tabs bread" style="margin-top:40px;">
					<li class="active"><a href="#m_bifen" data-toggle="tab" >比分</a></li>
					<li ><a href="#m_chatroom" data-toggle="tab" >侃球室</a></li>
				</ul>
		 <div id="myTabContent" class="tab-content">
				<div class="tab-pane fade in active" id="m_bifen" >
					 <table class="main" cellspacing="0" >
							<tbody id="bifenBody" >
								
						   </tbody>
					 </table>
				</div>
				<div class="tab-pane fade" id="m_chatroom" >
						
						<div id="chat_div" style="float:left;height:455px;width:100%;">
							
						</div>
						
				</div>
		</div>
		 
	
</div>

<table  style="display:none;">
	<tbody id="template">
		<tr class="match_box">
		      <td class="match_box_left">
		          <div class="team_up"><span class="match_league" >#league</span> <span class="match_start_time">#start_time</span></div>
		          <div class="team_down">
		          	#homeNameAndCards
		          </div>
		      </td>
		      <td class="match_box_middle">
		      	<div class="match_mins" style="color:#red;">#match_mins</div>
		                   <div class="match_bifen"><span>#bifen_a:#bifen_b</span></div>
		               </td>
		      <td class="match_box_right">
		          <div class="team_up"><span class="match_odds" >#odds</span><span class="match_half_bifen" >#half_bifen</span></div>
		          <div class="team_down">
		          	#awayNameAndCards
		          </div>
		      </td>
		</tr>
	</tbody>
</table>

<div class="scroll-to-top">
    <i class="icon-arrow-up"></i>
</div>
<script src="https://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="common/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript">
//Handles the go to top button at the footer
var handleGoTop = function() {
    var offset = 300;
    var duration = 300;
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

$(function(){
	  handleGoTop();	
	  updateBifen();
	  setInterval("updateBifen()",20000);
});

$(function(){
	var winWidth = $(window).width();
	var winHeight = $(window).height();
	if(!(navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
		if(winWidth>=992){
			window.location.href = "/bifen.html";
		}
	 }
	$("#chat_div").css("height", winHeight-90);
	chat_url = chat_url.replace("src_height", winHeight-90);
	chat_url = chat_url.replace("frame_height", winHeight-90);
	$("#chat_div").html(chat_url);
});

window.onresize = function(){
	var winWidth = $(window).width();
	var winHeight = $(window).height();
	if(winWidth>=992){
		window.location.href = "/bifen.html";
	}
	$("#chat_div").css("height", winHeight-90);
	chat_url = chat_url.replace("src_height", winHeight-90);
	chat_url = chat_url.replace("frame_height", winHeight-90);
	$("#chat_div").html(chat_url);
};

function updateBifen(){
	var newHtml='';
	var updateUrl = "/bifen/mobileJson?t="+Date.parse(new Date());
	$.ajax({url:updateUrl,success: function(data){
        for(var i=0; i<data.length; i++){
        	var oneMatch = $("#template").html();
        	oneMatch = oneMatch.replace("#league",data[i].league);
        	oneMatch = oneMatch.replace("#start_time",data[i].start_time);
        	var mins = data[i].match_mins;
        	if(mins.indexOf("'")!=-1){
        		mins = "&nbsp;&nbsp;"+mins.replace("'","<img src='assets/global/img/ticks.gif' />");
        	}
        	oneMatch = oneMatch.replace("#match_mins",mins);
        	if('未开'==data[i].match_mins){
        		oneMatch = oneMatch.replace("#red","#ccc");
        	}else{
        		oneMatch = oneMatch.replace("#red","red");
        	}
        	oneMatch = oneMatch.replace("#bifen_a",data[i].bifen_a);
        	oneMatch = oneMatch.replace("#bifen_b",data[i].bifen_b);
        	oneMatch = oneMatch.replace("#odds",data[i].odds);
        	oneMatch = oneMatch.replace("#half_bifen",data[i].half_bifen);
        	var homeNameAndCards = '';
        	if(data[i].yellow_a!=''){
        		homeNameAndCards+='<span class="yellow">'+data[i].yellow_a+'</span>';
        	}
        	if(data[i].red_a!=''){
        		homeNameAndCards+='<span class="red">'+data[i].red_a+'</span>';
        	}
        	homeNameAndCards +='<span >'+data[i].team_a+'</span>';
        	oneMatch = oneMatch.replace("#homeNameAndCards",homeNameAndCards);
        	
        	var awayNameAndCards = '';
        	awayNameAndCards +='<span >'+data[i].team_b+'</span>';
        	if(data[i].red_b!=''){
        		awayNameAndCards+='<span class="red">'+data[i].red_b+'</span>';
        	}
        	if(data[i].yellow_b!=''){
        		awayNameAndCards+='<span class="yellow">'+data[i].yellow_b+'</span>';
        	}
        	
        	
        	oneMatch = oneMatch.replace("#awayNameAndCards",awayNameAndCards);
        	newHtml += oneMatch;
        }
        $("#bifenBody").html(newHtml);
    }});
	
}

var chat_url = '<iframe id="pc_chat_iframe" name="pc_chat_iframe" src="http://chat.jcbao.org/bo360/chat?h=src_height" marginheight="0" marginwidth="0" frameborder="0" width="100%" height="frame_height" scrolling="no" allowtransparency="yes" style="margin-top:2px;"></iframe>';
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

</script>

		
</body>	

