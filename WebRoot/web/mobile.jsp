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
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - 足球比分直播|球探比分|bet007足球比分|聊天侃球</title>
    <style type="text/css">
    	.title2{line-height:30px;background:#F1F6FB;padding-left:5px;font-weight:bold;font-size:12px;text-indent:5px;border-bottom:1px dotted #CCC;}
    	.main{
		    width: 100%; margin: 0 auto 10px; padding: 0;
		}
		.match_box{
		    margin: 0 auto 10px auto; padding: 0; width: 100%; /*background: #EBF5FA; */ background:#f3faff; border-bottom-width: 1px;border-bottom-style: solid;border-bottom-color: #CAD9E6;
		}
		.match_box_left{
		    margin: 0 auto; padding: 0;  text-align: right;
		}
		.match_box_right{
		    margin: 0 auto; padding: 0;  text-align: left;
		}
		.match_box_middle{
		    margin: 0 auto; padding: 0;  width: 60px;  text-align:center;
		}
		
		.team_up{
		    line-height: 20px; font-size: 12px;
		}
		.team_down{
		    line-height: 26px; font-weight: bold;
		}
		.match_league{
		    color:#FCA92D;
		}
		.match_start_time{
		    color:#666;
		}
		.match_odds{
		    color:#666;
		}
		.match_half_bifen{
		    color:#666;
		}
		.match_mins{
		    line-height: 20px; font-size: 12px;
		}
		.match_bifen{
		    color:red; font-size: 16px; font-weight: bolder;
		}
		.red{
		    background: red; color: #fff; font-size: 12px; padding: 3px; margin: 0 2px; font-weight: normal;
		}
		.yellow{
		    background: yellow; color: #222; font-size: 12px; padding: 3px; margin: 0 2px; font-weight: normal;
		}
		
		.top_bar{
		    width: 100%; height: 30px; color:#fff; background:rgb(64, 178, 241); display:block; position: fixed; top:0; z-index: 9; padding: 0 10px;
		}
		
		
		.match_box td{
		    border-bottom: white solid 10px;
		}
		
		.a_info_box td{
		    padding: 0;
		}
    </style>
</head>

<body>
<div id="all_content">	
	<div class="container">
		<div class="row menu_link navbar-fixed-top  visible-sm visible-xs" style="background:#00A50D;min-height:35px;color:white;">
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/" target="_self"><span class="wwqk_menu">首页</span></a>
	       	</div>
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/fun.html" target="_self"><span class="wwqk_menu">趣点</span></a>
	       	</div>
	       	<div class="col-xs-3 col-sm-3 wwqk_menu_wh" >
	       		<a href="/live.html" target="_self"><span class="wwqk_menu">直播</span></a>
	       	</div>
	       	<div class="col-xs-2 col-sm-2 wwqk_menu_wh" >
	       		<a href="/bifen.html" target="_self"><span class="wwqk_menu dline">比分</span></a>
	       	</div>
	       	<div class="col-xs-3 col-sm-3 wwqk_menu_wh" >
	       		<a href="/data.html" target="_self"><span class="wwqk_menu">数据</span></a>
	       	</div>
	    </div>
	</div>

			<table class="main" cellspacing="0" style="margin-top:45px;">
				<tbody>
					<c:forEach items="${lstBifen}" var="bifen">
						<tr class="match_box ng-scope">
					        <td class="match_box_left">
					            <div class="team_up"><span class="match_league" >${bifen.leagueName}</span> <span class="match_start_time">${bifen.startTimeStr}</span></div>
					            <div class="team_down">
					            	<c:if test="${!empty bifen.homeYellow}"><span class="yellow">${bifen.homeYellow}</span></c:if><c:if test="${!empty bifen.homeRed}"><span class="red">${bifen.homeRed}</span></c:if><span >${bifen.homeName}</span>
					            </div>
					        </td>
					        <td class="match_box_middle">
					        	<div class="match_mins" style="color:red;">${bifen.liveTimeStr}</div>
		                        <div class="match_bifen"><span>${bifen.homeBifen}:${bifen.awayBifen}</span></div>
		                    </td>
					        <td class="match_box_right">
					            <div class="team_up"><span class="match_odds" >${bifen.handicapStr}</span><span class="match_half_bifen" >${bifen.halfBifen}</span></div>
					            <div class="team_down">
					            	<span>${bifen.awayName}</span><c:if test="${!empty bifen.awayRed}"><span class="red">${bifen.awayRed}</span></c:if><c:if test="${!empty bifen.awayYellow}"><span class="yellow">${bifen.awayYellow}</span></c:if>
					            </div>
					        </td>
					    </tr>
					</c:forEach>
			   </tbody>
		 </table>
	
</div>

<div class="scroll-to-top">
    <i class="icon-arrow-up"></i>
</div>
<script src="https://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
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
});

$(function(){
	if(!(navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
		if(winWidth>=992){
			window.location.href = "/bifen";
		}
	 }
});

window.onresize = function(){
	var winWidth = $(window).width();
	if(winWidth>=992){
		window.location.href = "/bifen";
	}
};



</script>

		
</body>	

