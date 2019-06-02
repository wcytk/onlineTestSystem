<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="loginHtml">
<head>
	<meta charset="utf-8">
	<title>登录</title>
	<link rel="icon" href="${pageContext.request.contextPath }/resources/car.jpg">
</head>
<body>
	<form method="post" action="${pageContext.request.contextPath }/loginRequest">
		<a href="${pageContext.request.contextPath }/index">首页</a><br/>
		<input type="text" name="strId"><br/>
		<input type="password" name="passwd"><br/>
		<input type="radio" name="remember" value="1">记住密码<br/>
		<input type="submit" name="提交" value="登录">
	</form>
</body>
</html>