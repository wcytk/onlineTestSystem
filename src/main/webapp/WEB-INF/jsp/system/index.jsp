<%@ page import="com.wcytk.entity.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html class="loginHtml">
<head>
    <meta charset="utf-8">
    <title>首页</title>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/car.jpg">
</head>
<body>
<%
    User user = new User();
    boolean flag = false;
    if(session.getAttribute("user") != null) {
        user = (User)session.getAttribute("user");
        flag = true;
    }
    String name = user.getName();
    boolean teacher = false;
    if(user.getTeacher() == 1) {
        teacher = true;
    }
%>
<c:choose>
    <c:when test="<%=flag %>">
        欢迎，<%=name%>
        <c:choose>
            <c:when test="<%=teacher %>">
                教师 <br/>
                <a href="#">考试管理</a>
            </c:when>
            <c:otherwise>
                学生 <br/>
                <a href="#">考试界面</a>
            </c:otherwise>
        </c:choose> <br/>
        <a href="${pageContext.request.contextPath}/logout">注销</a>
    </c:when>
    <c:otherwise>
        <a href="${pageContext.request.contextPath}/login">登录</a>
        <a href="${pageContext.request.contextPath}/register">注册</a>
    </c:otherwise>
</c:choose>
</body>
</html>