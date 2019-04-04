 <%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../common/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript">
	function refreshCode() {
		$("#codeImage").attr("src", "${ctx}/checkImg?" + Math.random());
	}
</script>
</head>
<body>
	<form action="${ctx}/login?method=login" method="post">
		用户名：<input type="text" name="name"></br>
		密码：<input type="text" name="password"></br>
		验证码：<input type="text" name="checkCode"/>
		<img id="codeImage" alt="" src="${ctx}/checkImg" onclick="refreshCode()"><br/>
		<input type="submit" value="登录"/>
	</form>
</body>
</html>