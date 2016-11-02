<%@page contentType= "text/html;charset=UTF-8" %>
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
