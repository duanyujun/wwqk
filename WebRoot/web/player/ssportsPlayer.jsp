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
<object type="application/x-shockwave-flash" id="SSportsPlayer" name="SSportsPlayer" align="middle" data="http://image.ssports.com/images/resources/web/live/swf/SSportsPlayer20170825.swf" width="650" height="460"><param name="quality" value="high"><param name="bgcolor" value="#000000"><param name="allowscriptaccess" value="always"><param name="allowfullscreen" value="true"><param name="wmode" value="opaque"><param name="flashvars" value="${link}"></object>		
</BODY>
</HTML>