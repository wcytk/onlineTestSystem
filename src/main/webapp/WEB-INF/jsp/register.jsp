<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="loginHtml">
<head>
    <meta charset="utf-8">
    <title>注册</title>
    <link rel="icon" href="${pageContext.request.contextPath }/resources/car.jpg">
</head>
<body>
<form method="post" action="${pageContext.request.contextPath }/registerRequest">
    <a href="${pageContext.request.contextPath }/index">首页</a><br/>
    <input type="text" name="name">
    <input type="password" name="passwd"><br/>
    <input type="radio" name="checkTeacher" value="teacher">教师
    <input type="radio" name="checkTeacher" value="student" checked="checked">学生<br/>
    <input type="submit" name="提交" value="注册">
</form>
</body>
</html>