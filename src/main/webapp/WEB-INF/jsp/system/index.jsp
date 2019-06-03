<%@ page import="com.wcytk.entity.User" %>
<%@ page import="com.wcytk.entity.Teacher" %>
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
    String name = "";
    User user = new User();
    Teacher teacher = new Teacher();
    boolean isTeacher = false;
    boolean flag = false;
    if(session.getAttribute("user") != null) {
        user = (User)session.getAttribute("user");
        name = user.getName();
        flag = true;
    }
    if (session.getAttribute("teacher") != null) {
        teacher = (Teacher)session.getAttribute("teacher");
        isTeacher = true;
        name = teacher.getName();
        flag = true;
    }
%>
<c:choose>
    <c:when test="<%=flag %>">
        欢迎，<%=name%>
        <c:choose>
            <c:when test="<%=isTeacher %>">
                教师 <br/>
                <a href="${pageContext.request.contextPath}/teacher/class">课程管理</a>
            </c:when>
            <c:otherwise>
                学生 <br/>
                <a href="${pageContext.request.contextPath}/student/class">我的课程</a>
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