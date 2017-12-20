<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>视频播放</title>
<style type="text/css">
	body { margin: 0 auto; background-color:#000000;}
	a{text-decoration:none;color: #00FF00;cursor:hand;}
	a:hover{text-decoration:underline;color:#db2c30;}
	a.noline{text-decoration:none}
	p{ color: #FFF; font-size:14px ; margin-top:8px; text-align:center}
</style>
</HEAD>
<BODY topmargin='0' leftmargin='0' oncontextmenu='return false' ondragstart='return false' onselectstart ='return false' onselect='document.selection.empty()' oncopy='document.selection.empty()' onbeforecopy='return false' onmouseup='document.selection.empty()'>

<script type="text/javascript">
function dw(str){document.write(str);}
function getParam(name, url)
{
	url  = url?url:self.window.document.location.href;
	var js_get  = url.split('#')[0];
	var start	= js_get.indexOf(name + '=');
	if (start == -1) return '';
	var len = start + name.length + 1;
	var end = js_get.indexOf('&',len);
  	if (end == -1) end = js_get.length;
  	return unescape(js_get.substring(len,end));
}

var type = '${type}';
var link = '${link}';
var id;
if(type=='1'){
	dw('<iframe width="100%" height="460" frameborder="0" scrolling="no" src="https://v.qq.com/iframe/player.html?vid='+link+'&tiny=0&auto=0"></iframe>	');
	var url = 'https://v.qq.com/iframe/player.html?vid='+link+'&tiny=0&auto=0'; 
}else if(type=='2'){
	id = link.indexOf('cid')>-1?getParam('cid', link):link;
	if(id.indexOf('http://')>-1){
		dw('<embed src="'+id+'" quality="high" width="650" height="450" align="middle" allowScriptAccess="always" allownetworking="all" allowfullscreen="true" type="application/x-shockwave-flash" wmode="window"></embed>'); 
		var url = id;
	}else{
		dw('<embed width="100%" height="460" wmode="transparent" type="application/x-shockwave-flash" pluginspage="http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash" allownetworking="all" allowscriptaccess="always" allowfullscreen="true" quality="high" src="http://player.pptv.com/v/'+id+'.swf">');
		var url = 'http://player.pptv.com/v/'+id+'.swf';
	}
}else if(type=='4'){
	id = link.indexOf('http://')>-1?getParam('id', link):link;
	dw('<iframe scrolling="no" src="http://minisite.letv.com/tuiguang/index.shtml?vid='+id+'&autoPlay=none&typeFrom=letv360cp&ark=372&isAutoPlay=1&wmode=transparent?jump=browser&login=0" width="100%" height="430" frameborder="0"></iframe>'); 
	var url = 'http://i7.imgs.letv.com/player/swfPlayer.swf?autoPlay=1&id='+id;  
}else if(type=='5'){
	id = link.indexOf('http://')>-1?getParam('pid', link):link;
	dw('<iframe width="100%" height="460" frameborder="0" scrolling="no" src="http://player.cntv.cn/flashplayer/players/htmls/smallwindow.html?pid='+id+'&time=0"></iframe>');
	var url = 'http://player.cntv.cn/flashplayer/players/htmls/smallwindow.html?pid='+id; 
}else if(type=='6'){
	dw('<embed src="'+link+'" quality="high" bgcolor="#ffffff" width="100%" height="460" quality="high" allowfullscreen="true" allowscriptaccess="always" allownetworking="all" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />');
	var url = '<embed src="'+link+'" quality="high" bgcolor="#ffffff" width="100%" height="460" quality="high" allowfullscreen="true" allowscriptaccess="always" allownetworking="all" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />';
}else if(type=='7'){
	id = link.indexOf('http://')>-1?getParam('vid', link):link;
	dw('<embed src="http://video.sina.com.cn/share/video/'+id+'.swf" quality="high" allowfullscreen="true" allowscriptaccess="always" allownetworking="all" pluginspage="http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash" type="application/x-shockwave-flash" width="100%" height="460" wmode="transparent" ></embed>');
	var url = 'http://video.sina.com.cn/share/video/'+id+'.swf';
}else if(type=='8'){
	dw('<div style="width:650px;height:460px;background-color:#FFF;"><div style="padding-top:80px;text-align:center;font-size:18px;color:red;">请点击链接进入【官方网站】播放视频，<a href="'+link+'" target="_blank" style="font-size:18px;color:blue;">点此打开播放器观看</a> <br><br><br>或者到首页选择其他视频 <a href="http://www.yutet.com" style="font-size:18px;color:blue;" target="_blank">点此进入首页</a><br><br><br></div></div>');
	var url = link;
}else if(type=='9'){
	id = link.indexOf('http://')>-1?getParam('iid', link):link;
	dw('<embed id="videoplayer" width="100%" height="460" align="absmiddle" allowfullscreen="true" allowscriptaccess="always" wmode="opaque" quality="HIGH" play="TRUE"  loop="TRUE" src="http://js.tudouui.com/bin/player2/olc_8.swf?iid='+id+'&swfPath=http://js.tudouui.com/bin/lingtong/SocialPlayer_54.swf&tvcCode=-1&autoPlay=true"/>'); 
	var url = 'http://js.tudouui.com/bin/player2/olc_8.swf?iid='+id+'&swfPath=http://js.tudouui.com/bin/lingtong/SocialPlayer_54.swf&tvcCode=-1&autoPlay=true';  
}else if(type=='10'){
	id = link.indexOf('http://')>-1?getParam('vid', link):link;
	dw('<iframe width="650" height="460" frameborder="0" scrolling="no" src="http://player.duo.la/qqvideo.html?id='+id+'"></iframe>');
	var url = 'https://v.qq.com/x/page/'+id+'.html';   
}else if(type=='11'){
	if(link.indexOf('http://')>-1){
		dw('<embed src="'+link+'" id="object_flash_player" allowScriptAccess = "always" wmode="opaque"  pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" allowfullscreen="true" height="460" width="100%">'); 
		var url = link;
	}
	else{
		dw('<embed src="http://www.56.com/flashApp/player_open.14.09.05.d.swf?vid='+link+'&ref=www.qqzhbo.net&player_key=open&appid=3000001677" id="object_flash_player" allowScriptAccess = "always" wmode="opaque"  pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" allowfullscreen="true" height="460" width="100%">'); 
		var url = 'http://player.56.com/v_'+link+'.swf';
	}
}else if(type=='12'){
	id = link.indexOf('http://')>-1?getParam('vid', link):link;
	dw('<embed src="http://tv.sohu.com/upload/swf/20101021/Main.swf?autoplay=true&vid='+id+'" quality="high" allowfullscreen="true" allowscriptaccess="always" allownetworking="all" pluginspage="http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash" type="application/x-shockwave-flash" width="100%" height="460" wmode="transparent" ></embed>'); 
	var url = 'http://tv.sohu.com/upload/swf/20101021/Main.swf?autoplay=true&vid='+id; 
}else if(type=='13'){
	id = link.indexOf('http://')>-1?getParam('epgId', link):link;
	dw('<embed id="null" width="100%" height="460" type="application/x-shockwave-flash" src="http://img3.t.sinajs.cn/t4/apps/tv/static/flash/mainPlayer20130201_V5_0_1_12.swf?epgId='+id+'" allowfullscreeninteractive="true" allowfullscreen="true" allowscriptaccess="always" quality="high" cachebusting="true" wmode="window" bgcolor="#000000" />'); 
	var url = 'http://img3.t.sinajs.cn/t4/apps/tv/static/flash/mainPlayer20130201_V5_0_1_12.swf?epgId='+id; 
}else{
	location.href = link;
}
/**
if(top==self){
	top.location= url;
}   
*/

</script>
</BODY>
</HTML>
