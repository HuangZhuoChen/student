<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	<!--导航 begin -->
	<nav class="navbar navbar-default">
		<div>
		 	<a href="${ctx}/login?method=logout">  <span>${user.name}</span> <span class="glyphicon glyphicon-off" aria-hidden="true"></span>&nbsp;&nbsp;退出</a>
		</div>
	<div class="container">
		<!-- 搜索表单end -->
		<table class="table table-hover">
			<thead>
				<tr>
					<th>ID</th>
					<th>用户名</th>
					<th>密码</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${onLineUserList}" var="user">
					<tr>
						<td>${user.id}</td>
						<td>${user.name}</td>
						<td>${user.password}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>
	</nav>
	<!-- 内容部分 end-->
</body>
</html>