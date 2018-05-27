<%@ include file="/common/include-bifen.jsp"%>
<%@ page contentType="text/html;charset=GB2312"%>
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
	<meta name="keywords" content="英超直播,CCTV5在线直播,比赛直播,免费足球直播,直播网站,比赛结果" />
	<meta name="description" content="趣点足球网为球迷们提供五大联赛最新的足球比赛直播，比赛直播链接，比赛结果。" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="https://cdn.bootcss.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="/common/new/main.css" rel="stylesheet" type="text/css" />
    <link href="assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <title>趣点足球网 - 比赛直播|免费直播|直播网站</title>
    
</head>

<body>
<!-- header start -->
<%@ include file="/common/new/menu-bifen.jsp"%>

<!-- pc content start -->
<div class="main hidden-sm hidden-xs">
	<div style="width:930px;float:left;" class="live-all">
		<div class="bread" style="text-align:left;margin-bottom:10px;">
			当前位置：<a href="/" target="_blank">首页</a>&nbsp;&gt;&nbsp;直播
		</div>
		<ul id="infoTab" class="nav nav-tabs bread" >
				<li class="${bifen==1?'':'active'}" id="live_tab_li"><a href="#live_tab" data-toggle="tab">直播</a></li>
				<li class="${bifen==1?'active':''}" id="bifen_tab_li"><a href="#bifen_tab" data-toggle="tab">比分</a></li>
			</ul>
			<div id="infoTabContent" class="tab-content">
				<div class="tab-pane fade in ${bifen==1?'':'active'}" id="live_tab" style="border-top:none;">
					 <div style="width:100%;" >
								<table class="table table-condensed table-hover " >
									  <tbody>
										<c:forEach items="${lstMatch}" var="match">
											<c:if test="${empty match.home_team_name}">
												<tr>
													<td colspan="4" style="padding:0;"><span style="display:block;background:#5cb85c;height:35px;line-height:35px;font-size:16px;color:white;font-weight:bold;text-align:left;">&nbsp;${match.match_date_week}</span></td>
												</tr>
											</c:if>
											<c:if test="${!empty match.home_team_name}">
												<tr >
												  <td style="width:80px;height:35px;line-height:35px;"><span class="label label-default"><fmt:formatDate value="${match.match_datetime}" pattern="HH:mm"/></span></td>
											      <td style="width:110px;height:35px;line-height:35px;text-align:left;"><span class="league_${match.league_id}" title="${match.league_name}"><div class="text-cut" style="width:110px;line-height:35px;">${match.league_name}</div></span></td>
											      <td style="height:35px;line-height:35px;" class="a-title">
												      	<div style="width:100%;float:left;text-align:left;">
												      		<c:if test="${empty match.league_id}">
													      		<a  title="${match.game_id!='0'?'有分析':''} ${match.tips!='0'?'有推荐':''}" href="/live-<fmt:formatDate value="${match.match_datetime}" pattern="yyyy-MM-dd"/>-${match.home_team_enname}-vs-${match.away_team_enname}-${match.id}.html" target="_blank">
													      		<c:if test="${match.game_id!='0'}"><span class="label label-success">析</span></c:if> <c:if test="${match.tips!='0'}"><span class="label label-success" style="background-color:#03a4a2;">荐</span></c:if> ${match.home_team_name} VS ${match.away_team_name}
													      		</a>
													      	</c:if>
													      	<c:if test="${!empty match.league_id}">
													      		<b class="a-title" ><a title="${match.game_id!='0'?'有分析':''} ${match.tips!='0'?'有推荐':''}" href="match-${match.home_team_enname}-vs-${match.away_team_enname}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_blank">
													      		<c:if test="${match.game_id!='0'}"><span class="label label-success">析</span></c:if> <c:if test="${match.tips!='0'}"><span class="label label-success" style="background-color:#03a4a2;">荐</span></c:if> ${match.home_team_name} VS ${match.away_team_name}
													      		</a></b>
													      	</c:if>
												      	</div>
												      	<div style="width:100%;float:left;text-align:left;">
												      		<c:forEach items="${match.liveList}" var="live">
													      	  		 <nobr><i class="fa fa-tv"></i> <a href="${live.live_url}" target="_blank" style="color:red;">${live.live_name}</a></nobr>&nbsp;
													      	  </c:forEach>
													      	  <c:if test="${!empty match.liveList}">
													      	  	 &nbsp;
													      	  </c:if>
													      	  <nobr><img src="assets/pages/img/zq.gif" style="width:18px;"/> <span class="grey-title"><a class="live-a" >比分直播</a></span></nobr>
											      			
												      	</div>
											      </td>
											    </tr>
											    
											    
											</c:if>
									    </c:forEach>
									   </tbody>
									</table>
						</div>
				</div>
				<div class="tab-pane fade in ${bifen==1?'active':''}" id="bifen_tab" style="width:100%;border-top:none;float:left;">
					 <div style="width:65%;float:left;">
			 			<div id="pc_div" style="height:20000px;">
							<iframe id="pc_iframe" name="pc_iframe" height="20000" src="http://live1.bet007.com/live.aspx?Edition=1&amp;lang=0&amp;ad=豹步正品足球鞋男ag长钉成人训练比赛&amp;adurl=http://m.tb.cn/h.Z0dMcWl&amp;color=DCEFFF&amp;sound=0" frameborder="0" width="100%"></iframe>
						</div>
			 		</div>
			 		<div  style="width:34%;float:left;margin-left:1%;">
			 			<div id="chat_div" style="width:100%;border:1px solid #c0c0c0;height:490px;padding-left:0;padding-right:0;">
		 					<div class="title2" style="text-align:left;">侃球室</div>
		 					<iframe id="pc_chat_iframe" name="pc_chat_iframe"  src="http://chat.shoumi.org/bo360/chat?h=490" marginheight="0" marginwidth="0" frameborder="0" width="100%" height="450" scrolling="no" allowtransparency="yes" style="margin-top:2px;"></iframe>
		 				</div>
		 				<div id="cp_div" style="width:100%;margin-top:10px;border:1px solid #c0c0c0;min-height:70px;padding-left:0;padding-right:0;">
		 					<div class="title2" style="text-align:left;"><span class="headactions">最新彩票开奖结果 <a id="toggleA" href="javascript:;" onclick="toggleCp();">更多信息...</a></span></div>
							<div id="cp" style="min-height:70px;">
								<iframe frameBorder="0" scrolling="no" align="center" width="297" height="65" rel="nofollow" src="http://www.360zhibo.com/kjgd2013.html"></iframe>
							</div>
		 				</div>
			 		</div>
				</div>
			</div>
		
		
	</div>
</div>
<!-- pc content end -->

<%@ include file="/common/new/live-mobile.jsp"%>	

<!-- footer start -->
<%@ include file="/common/new/footer-bifen.jsp"%>	

<script src="https://cdn.bootcss.com/bootstrap/3.0.3/js/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript">
var pc_frame_url = '<iframe id="pc_iframe" name="pc_iframe" height="20000" src="http://live1.bet007.com/live.aspx?Edition=1&amp;lang=0&amp;ad=豹步正品足球鞋男ag长钉成人训练比赛&amp;adurl=http://m.tb.cn/h.Z0dMcWl&amp;color=DCEFFF&amp;sound=0" frameborder="0" width="100%"></iframe>';
var chat_frame_url = '<div class="title2" style="text-align:left;">侃球室</div><iframe id="pc_chat_iframe" name="pc_chat_iframe" src="http://www5.cbox.ws/box/?boxid=896554&boxtag=nner91&sec=main" marginheight="0" marginwidth="0" frameborder="0" width="100%" height="460" scrolling="no" allowtransparency="yes" style="margin-top:2px;"></iframe>';
var m_chat_url = '<iframe id="pc_chat_iframe" name="pc_chat_iframe" src="http://chat.shoumi.org/bo360/chat?h=460" marginheight="0" marginwidth="0" frameborder="0" width="100%" height="frame_height" scrolling="no" allowtransparency="yes" style="margin-top:2px;"></iframe>';
var m_bifen_url = '<iframe id="mobile_iframe" name="mobile_iframe" scrolling="no" frameborder="0" align="center" src="http://m.188bifen.com/" style="width:100%;height: 20000px;" rel="nofollow" border="0"></iframe>';
var cp_url = '<iframe frameBorder="0" scrolling="no" align="center" width="100%" height="65" rel="nofollow" src="http://www.360zhibo.com/kjgd2013.html"></iframe>';
var cp_more_url = '<iframe frameBorder="0" scrolling="no" align="center" width="100%" height="670" rel="nofollow" src="http://www.360zhibo.com/kjgd3013.html"></iframe>';

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
	changeMSize();
	updateBifen();
	setInterval("updateBifen()",20000);
	initBifen();
});

function initBifen(){
	$(".live-a").click(function(event){
		  event.preventDefault();
		  $("#live_tab").removeClass("active");
		  $("#bifen_tab").addClass("active");
		  $("#bifen_tab").addClass("in");
		  $("#live_tab_li").removeClass("active");
		  $("#bifen_tab_li").addClass("active");
		  
		  $("#m_live").removeClass("active");
		  $("#m_bifen").addClass("active");
		  $("#m_bifen").addClass("in");
		  $("#m_live_li").removeClass("active");
		  $("#m_bifen_li").addClass("active");
	  });
}

function changeMSize(){
	var winWidth = $(window).width();
	var winHeight = $(window).height();
	if(!(navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
		return;
	 }
	$("#m_chat_div").css("height", winHeight-215);
	m_chat_url = m_chat_url.replace("src_height", winHeight-103);
	m_chat_url = m_chat_url.replace("frame_height", winHeight-215);
	$("#m_chat_div").html(m_chat_url);
}

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
</html>
