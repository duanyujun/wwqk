<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">

<head>
	<base href="<%=basePath%>">
    <link href="common/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
</head>

<div id="main" class="container">
	<div class="row">
		<div class="col-lg-12 col-sm-12 col-md-12">
			<div class="pull-right"><a href="/user/edit" class="btn btn-primary">新增用户</a></div>
			<table class="table table-hover table-bordered" >
				<thead ><th>ID</th><th>姓名</th><th>年龄</th><th>性别</th><th>备注</th><th>操作</th></thead>
				<tbody >
					<c:forEach items="${users}" var="user">
						<tr><td>${user.id}</td><td>${user.name}</td><td>${user.age}</td><td>${user.sex}</td><td>${user.remark}</td><td><a href="/user/edit/${user.id}" class="btn btn-primary btn-sm">修改</a> <a href="/user/del/${user.id}" class="btn btn-danger btn-sm">删除</a></td></tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>