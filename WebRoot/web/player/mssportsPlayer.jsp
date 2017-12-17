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
</HEAD>
<BODY topmargin='0' leftmargin='0' oncontextmenu='return false' ondragstart='return false' onselectstart ='return false' onselect='document.selection.empty()' oncopy='document.selection.empty()' onbeforecopy='return false' onmouseup='document.selection.empty()'>
<video id="video" src="${link}" autobuffer="" loop="" controls="" style="width:100%;height:100%;object-fit:fill;"></video>
<script type="text/javascript">
function playVideo(){
	var videoes = document.getElementById("video");
    videoes.play();
}
playVideo();

</script>
</BODY>
</HTML>