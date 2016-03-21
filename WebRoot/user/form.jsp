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
		<div class="col-lg-6 col-sm-6 col-md-6">
			<form action="/user/submit" method="post">
				<input type="hidden" name="user.id" value="${user.id}" />
				<div class="form-group">
					<label>姓名</label>
					<input type="text" name="user.name" value="${user.name}" class="form-control" placeholder="请输入姓名" />
				</div>
				<div class="form-group">
					<label>年龄</label>
					<input type="text" name="user.age" value="${user.age}" class="form-control" placeholder="请输入年龄" />
				</div>
				<div class="form-group">
					<label>性别</label>
					<div class="radio-inline">
						<input type="radio" name="user.sex" ${(user==null || user.sex=='1')?'checked':'' } value="1" />男
					</div>
					<div class="radio-inline">
						<input type="radio" name="user.sex" ${user.sex=='2'?'checked':'' } value="2" />女
					</div>
				</div>
				<div class="form-group">
					<label>备注</label>
					<textarea class="form-control" name="user.remark" style="height:70px;" placeholder="备注">${user.remark}</textarea>
				</div>
				<div style="text-align:center;">
					<button type="submit" class="btn btn-primary" style="width:120px;">提交</button>
				</div>
			</form>
		</div>
	</div>
</div>