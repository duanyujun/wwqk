<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>视频播放</title>
</head>
<body>
<video id="video" src="${link}" autobuffer="" loop="" controls="" style="width:100%;height:100%;object-fit:fill;"></video>

<script type="text/javascript">
var videoes = document.getElementById("video");
videoes.play();
</script>

</body>
</HTML>
