<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../common/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/lib/bootstrap/css/bootstrap.min.css"/>
<script type="text/javascript">
$(function(){
	$("#nameId").blur(function() {
		var name = $(this).val();
		$.post(
			"${ctx}/student?method=checkName",//url
			{"name":name}, //data
			function(data) { // callback
				//alert(data.isExist);
			    if(data.isExist) {
			    	$("#nameInfo").html("用户名已经存在");
			        $("#nameInfo").css("color", "red");
			    } else {
			    	$("#nameInfo").html("此用户名可用");
			        $("#nameInfo").css("color", "green");
			    }
			},
			"json"
		);
	});
});
</script>
	</head>
	<body>
		<!--导航 begin -->
		<nav class="navbar navbar-default">
		  <div class="container">
		    <!-- Brand and toggle get grouped for better mobile display -->
		    <div class="navbar-header">
		      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
		        <span class="sr-only">Toggle navigation</span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		      </button>
		      <a class="navbar-brand" href="#">教务管理系统</a>
		    </div>
		
		    <!-- Collect the nav links, forms, and other content for toggling -->
		    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		      <ul class="nav navbar-nav">
		        <li class="active"><a href="student_list.html"><span class="glyphicon glyphicon-home" aria-hidden="true"></span>&nbsp;&nbsp;学生管理 <span class="sr-only">(current)</span></a></li>
		        <li><a href="banji_list.html"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>&nbsp;&nbsp;班级管理</a></li>
		        <li><a href="#"><span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>&nbsp;&nbsp;课程管理</a></li>
		        <li><a href="#"><span class="glyphicon glyphicon-tag" aria-hidden="true"></span>&nbsp;&nbsp;教务管理</a></li>
		      </ul>
		      
		      <ul class="nav navbar-nav navbar-right">
		        <li><a href="#"><span class="glyphicon glyphicon-off" aria-hidden="true"></span>&nbsp;&nbsp;退出</a></li>
		      </ul>
		    </div><!-- /.navbar-collapse -->
		  </div><!-- /.container-fluid -->
		</nav>
		<!--导航 end -->
		
		<!-- 内容部分 begin-->
		<div class="container">
			<div class="row">
				<!-- 左边部分 begin-->
				<div class="col-md-2">
					<div class="list-group">
					  <a href="${pageContext.request.contextPath}/findAll.do" class="list-group-item">
					    学生列表
					  </a>
					  <a href="#" class="list-group-item active">学生添加</a>
					</div>
				</div>
				<!-- 左边部分 end-->
				<!-- 右边部分 begin-->
				<div class="col-md-10">
					<form action="${ctx}/student?method=add" method="post">
					  <div class="form-group">
					    <label for="exampleInputEmail1">名字</label>
					    <input type="text" id="nameId" name="name" class="form-control" id="exampleInputEmail1" placeholder="Email">
					    <span id="nameInfo"></span>
					  </div>
					  <div class="form-group">
					    <label for="exampleInputPassword1">年龄</label>
					    <input type="text" name="age" class="form-control" id="exampleInputPassword1" placeholder="Password">
					  </div>
					  <select name="banjiId">
					  	<c:forEach items="${list}" var="banji">
						  	<option value="${banji.id}">${banji.name}</option>
					  	</c:forEach>
					  </select><br/>
					  <button type="submit" class="btn btn-primary">Submit</button>
					</form>
				</div>
				<!-- 右边部分 end-->
			</div>
			
		</div>
		<!-- 内容部分 end-->
		
		
	</body>
</html>
