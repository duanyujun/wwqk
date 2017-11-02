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
	<meta name="keywords" content="����ȷ�,��̽�ȷ�,����ȷ�ֱ��,��ʱ�ȷ�,������" />
	<meta name="description" content="Ȥ��������Ϊ�������ṩ����ȷ֡���ʱ�ȷֺͱ������������������٩��" />
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="common/main.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
    <title>Ȥ�������� - ����ȷ�ֱ��|��̽�ȷ�|bet007����ȷ�|����٩��</title>
    <style type="text/css">
    	.title2{line-height:30px;background:#F1F6FB;padding-left:5px;font-weight:bold;font-size:12px;text-indent:5px;border-bottom:1px dotted #CCC;}
    </style>
</head>

<body>
	<div class="container">	
		<div class="row">
			<div class="col-sm-5 col-xs-5">
				<div class="col-sm-12 col-xs-12 pull-right">
					����U19 18:00
				</div>
				<div class="col-sm-12 col-xs-12 pull-right">
					�������U19(��)
				</div>
			</div>
			<div class="col-sm-2 col-xs-2">
				<div class="col-sm-12 col-xs-12">
					13'
				</div>
				<div class="col-sm-12 col-xs-12">
					0:0
				</div>
			</div>
			<div class="col-sm-5 col-xs-5">
				<div class="col-sm-12 col-xs-12 pull-right">
					��ƽ��
				</div>
				<div class="col-sm-12 col-xs-12 pull-right">
					�������U19(��)
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-5 col-xs-5">
				<div class="col-sm-12 col-xs-12 pull-right">
					����U19 18:00
				</div>
				<div class="col-sm-12 col-xs-12 pull-right">
					�������U19(��)
				</div>
			</div>
			<div class="col-sm-2 col-xs-2">
				<div class="col-sm-12 col-xs-12">
					13'
				</div>
				<div class="col-sm-12 col-xs-12">
					0:0
				</div>
			</div>
			<div class="col-sm-5 col-xs-5">
				<div class="col-sm-12 col-xs-12 pull-right">
					��ƽ��
				</div>
				<div class="col-sm-12 col-xs-12 pull-right">
					�������U19(��)
				</div>
			</div>
		</div>
	
	<%@ include file="/common/footer-bifen.jsp"%>		
	
	</div>
	
	<script>
	
	
	
	</script>
		
</body>	

