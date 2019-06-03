<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.wcytk.entity.User" %><%--
  Created by IntelliJ IDEA.
  User: Wcytk
  Date: 2019/5/30
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>课程管理界面</title>
    <link rel="stylesheet" href="../../../css/bootstrap.css"/>
    <link rel="stylesheet" href="../../../css/bootstrap-switch.css"/>
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.js"></script>
    <script src="../../../js/bootstrap.js"></script>
    <script src="../../../js/bootstrap-switch.js"></script>
    <script src="../../../js/bootstrap-table.min.js"></script>
</head>
<body>
<%
    String name = "";
    User user = new User();
    boolean flag = false;
    if (session.getAttribute("user") != null) {
        user = (User) session.getAttribute("user");
        name = user.getName();
        flag = true;
    }
    if (!flag) {
        response.sendRedirect("/index");
    }
%>
<script type="text/javascript">
    window.onload = function () {
        showClass();
        showMyClass();
    };

    function deleteClass(id) {
        if (confirm("确定要删除此课程?")) {
            $.ajax({
                url: "/teacher/deleteClass",
                type: "POST",
                dataType: "json",
                data: {
                    id: id
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.code === 10 || data.code === 11) {
                        window.location.href = "/index";
                    } else if (data.code === 0) {
                        showClass();
                    }
                },
                error: function (data) {
                    alert("请求失败~");
                }
            });
        }
    }

    function deleteTest(classId, testId) {
        if (confirm("确定要删除此测试?")) {
            $.ajax({
                url: "/teacher/deleteTest",
                type: "POST",
                dataType: "json",
                data: {
                    class_id: classId,
                    test_id: testId
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.code === 10 || data.code === 11) {
                        window.location.href = "/index";
                    } else if (data.code === 0) {
                        showTests(classId);
                    }
                },
                error: function (data) {
                    alert("请求失败~");
                }
            });
        }
    }

    function deleteQuestion(id) {
        if (confirm("确定要删除此试题?")) {
            $.ajax({
                url: "/teacher/deleteQuestion",
                type: "POST",
                dataType: "json",
                data: {
                    question_id: id
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.code === 10 || data.code === 11) {
                        window.location.href = "/index";
                    } else if (data.code === 0) {
                        showAllQuestions();
                    }
                },
                error: function (data) {
                    alert("请求失败~");
                }
            });
        }
    }

    function deleteQuestionFromTest(test_id, question_id) {
        var question_grade = $('#question' + id).find('input[name=\"grade\"]').val();
        if (confirm("确定要从测试中删除此试题?")) {
            $.ajax({
                url: "/teacher/deleteQuestionFromTest",
                type: "POST",
                dataType: "json",
                data: {
                    question_id: question_id,
                    test_id: test_id,
                    question_grade: question_grade
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.code === 10 || data.code === 11) {
                        window.location.href = "/index";
                    } else if (data.code === 0) {
                        showAllQuestions();
                    }
                },
                error: function (data) {
                    alert("请求失败~");
                }
            });
        }
    }

    function addTest(classId) {
        if (confirm("确定要添加这个测试?")) {
            $.ajax({
                url: "/teacher/addTest",
                type: "POST",
                dataType: "json",
                data: {
                    class_id: classId,
                    name: $("#addTestName").val(),
                    student_num: $("#addTestStudentNum").val(),
                    duration: $("#addTestDuration").val()
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.code === 10 || data.code === 11) {
                        window.location.href = "/index";
                    } else if (data.code === 0) {
                        showTests(classId);
                    }
                },
                error: function (data) {
                    alert("请求失败~");
                }
            });
        }
    }

    function addQuestion() {
        if (confirm("确定要添加这个测试?")) {
            $.ajax({
                url: "/teacher/addQuestion",
                type: "POST",
                dataType: "json",
                data: {
                    question: $("#addNewQuestion").val(),
                    a_option: $("#addAOption").val(),
                    b_option: $("#addBOption").val(),
                    c_option: $("#addCOption").val(),
                    d_option: $("#addDOption").val(),
                    e_option: $("#addEOption").val(),
                    f_option: $("#addFOption").val(),
                    answer: $("#addAnswer").val(),
                    type: $("#addType").val(),
                    grade: $("#addGrade").val()
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.code === 10 || data.code === 11) {
                        window.location.href = "/index";
                    } else if (data.code === 0) {
                        showAllQuestions();
                    }
                },
                error: function (data) {
                    alert("请求失败~");
                }
            });
        }
    }

    function addQuestionToTest(id) {
        if (confirm("确定要添加到测试?")) {
            var test_id = $('#question' + id).find('input[name=\"addToTest\"]').val();
            var question_grade = $('#question' + id).find('input[name=\"grade\"]').val();
            $.ajax({
                url: "/teacher/addQuestionToTest",
                type: "POST",
                dataType: "json",
                data: {
                    question_id: id,
                    test_id: test_id,
                    question_grade: question_grade
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.code === 10 || data.code === 11) {
                        window.location.href = "/index";
                    } else if (data.code === 0) {
                        showAllQuestions();
                    }
                },
                error: function (data) {
                    alert("请求失败~");
                }
            });
        }
    }

    function submitAnswer(length,testId) {
        var list = [];
        for(var i = 0; i < length; i++) {
            var question = {};
            var question_id = $('#question' + i).find('.question_id').text();;
            var answer = '';
            if ($('#question' + i).find('.question_type').text() == '0') {
                answer = $('#question' + i).find('input[type^=radio]:checked').val();
                question = {
                    question_id: question_id,
                    answer: answer
                };
            } else if ($('#question' + i).find('.question_type').text() == '1') {
                var values = $('#question' + i).find('input[type^=checkbox]:checked');
                for (var j = 0; j < values.length; j++) {
                    answer = answer + $(values[j]).val();
                }
                question = {
                    question_id: question_id,
                    answer: answer
                };
            } else {
                answer = $('#question' + i).find('input[name=\'student_answer\']').val();
                question = {
                    question_id: question_id,
                    answer: answer
                };
            }
            list.push(question);
        }
        $.ajax({
            url: "/student/submitAnswer",
            type: "POST",
            dataType: "json",
            data: {
                test_id: testId,
                answers: JSON.stringify(list)
            },
            success: function (data) {
                alert(data.msg);
                // window.location.href = "/index";
            },
            error: function (data) {
                alert("请求失败");
            }
        });
    }

    function showQuestions(testId) {
        $.ajax({
            url: "/student/showQuestions",
            type: "POST",
            dataType: "json",
            data: {
                test_id: testId
            },
            success: function (data) {
                if (data.code === 10 || data.code === 11) {
                    alert(data.msg);
                    window.location.href = "/index";
                } else if (data.code === 0) {
                    var set = new Set([]);
                    $('#question').empty();
                    $('#addQuestions').empty();
                    var testName = $($('#test' + testId).find('th')[1]).text();
                    $('#question').append(testName + "的试题");
                    $("#question").append("<tr><th>编号</th><th>题目类型</th><th>题干</th><th>选项A</th><th>选项B</th><th>选项C</th><th>选项D</th><th>选项E</th><th>选项F</th></tr>");
                    for (var i = 0; i < data.data.list.length; i++) {
                        var id = data.data.list[i].id;
                        if (!set.has(id)) {
                            set.add(id);
                            var typed;
                            if (data.data.list[i].type === 2 || data.data.list[i].type === 3) {
                                if (data.data.list[i].type === 2) {
                                    typed = "<tr id=\"question" + i + "\"><th>"+ (i+1) +"</th><th>判断题（输入对、错）</th>";
                                } else {
                                    typed = "<tr id=\"question" + i + "\"><th>"+ (i+1) +"</th><th>填空题（输入答案）</th>";
                                }
                                var list = ""+ typed + "<th>" +
                                    data.data.list[i].question + "</th><th><input placeholder='你的答案' name=\"student_answer\"><th><th class='question_type' hidden>" + data.data.list[i].type + "</th><th class='question_id' hidden>"+ id +"</th></tr><br/>";
                                $("#question").append(list);
                            } else {
                                var typed;
                                if (data.data.list[i].type === 0) {
                                    typed = "<tr id=\"question" + i + "\"><th>"+ (i+1) +"</th><th>单项选择题</th>";
                                } else {
                                    typed = "<tr id=\"question" + i + "\"><th>"+ (i+1) +"</th><th>多项选择题</th>";
                                }
                                var types;
                                if (data.data.list[i].type === 0) {
                                    types = "radio";
                                    var list = ""+ typed +"<th>" +
                                        data.data.list[i].question + "</th><th><input type=\""+ types +"\" name=\"option\" value=\"a\">" + data.data.list[i].a_option + "</th>" +
                                        "<th><input type=\""+ types +"\" name=\"option\" value=\"b\">" + data.data.list[i].b_option + "</th>" +
                                        "<th><input type=\""+ types +"\" name=\"option\" value=\"c\">" + data.data.list[i].c_option + "</th>" +
                                        "<th><input type=\""+ types +"\" name=\"option\" value=\"d\">" + data.data.list[i].d_option + "</th>" +
                                        "<th><input type=\""+ types +"\" name=\"option\" value=\"e\">" + data.data.list[i].e_option + "</th>" +
                                        "<th><input type=\""+ types +"\" name=\"option\" value=\"f\">" + data.data.list[i].f_option + "</th><th class='question_type' hidden>" + data.data.list[i].type + "</th><th class='question_id' hidden>"+ id +"</th></tr><br/>";
                                    $("#question").append(list);
                                } else if (data.data.list[i].type === 1) {
                                    types = "checkbox";
                                    var list = ""+ typed +"<th>" +
                                        data.data.list[i].question + "</th><th><input type=\""+ types +"\" name=\"option\" value=\"a\">" + data.data.list[i].a_option + "</th>" +
                                        "<th><input type=\""+ types +"\" name=\"option\" value=\"b\">" + data.data.list[i].b_option + "</th>" +
                                        "<th><input type=\""+ types +"\" name=\"option\" value=\"c\">" + data.data.list[i].c_option + "</th>" +
                                        "<th><input type=\""+ types +"\" name=\"option\" value=\"d\">" + data.data.list[i].d_option + "</th>" +
                                        "<th><input type=\""+ types +"\" name=\"option\" value=\"e\">" + data.data.list[i].e_option + "</th>" +
                                        "<th><input type=\""+ types +"\" name=\"option\" value=\"f\">" + data.data.list[i].f_option + "</th><th class='question_type' hidden>" + data.data.list[i].type + "</th><th class='question_id' hidden>"+ id +"</th></tr><br/>";
                                    $("#question").append(list);
                                }
                            }
                        } else {}
                    }
                    $('#submitTest').append("<input type='button' value=\"提交试卷\" onclick=\"submitAnswer(" + data.data.list.length + ","+ testId +")\">");
                } else {
                    alert(data.msg);
                }
            },
            error: function (data) {
                alert("请求失败");
            }
        });
    }

    function showTests(classId) {
        $.ajax({
            url: "/student/showTests",
            type: "POST",
            dataType: "json",
            data: {
                class_id: classId
            },
            success: function (data) {
                if (data.code === 10 || data.code === 11) {
                    alert(data.msg);
                    window.location.href = "/index";
                } else if (data.code === 0) {
                    $('#test').empty();
                    var nowStatus;
                    var className = $($('#class' + classId).find('th')[1]).text();
                    $('#test').append(className + "课程" + "的测试");
                    $("#test").append("<tr><th>测试id</th><th>测试名称</th><th>测试状态</th><th>测试时长</th><th>操作</th></tr>");
                    for (var i = 0; i < data.data.list.length; i++) {
                        var id = data.data.list[i].id;
                        if (data.data.list[i].status) {
                            nowStatus = "测试正在进行";
                        } else {
                            nowStatus = "测试已经结束"
                        }
                        var list = "<tr id=\"test" + id + "\"><th>" + id + "</th><th>" +
                            data.data.list[i].name + "</th><th>" +
                            nowStatus + "</th><th>" +
                            data.data.list[i].duration + "</th><th>" +
                            "<input type='button' onclick=\"showQuestions(" + id + ")\" value='进入考试'>" +
                            // "<input type='button' onclick=\"deleteTest(" + classId + "," + id + ")\" value='删除'>" +
                            // "<input type='button' onclick=\"updateTest(" + classId + "," + id + ")\" value='更新'>" +
                            // "<input type='button' onclick=\"showQuestions(" + classId + "," + id + ")\" value='查看测试试题'>" +
                            "</th></tr><br/>";
                        $("#test").append(list);
                    }
                } else {
                    alert(data.msg);
                }
            },
            error: function (data) {
                alert("请求失败");
            }
        });
    }

    function showMyClass() {
        $.ajax({
            url: "/student/showMyClasses",
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (data.code === 10 || data.code === 11) {
                    alert(data.msg);
                    window.location.href = "/index";
                } else if (data.code === 0) {
                    $('#myClass').empty();
                    $("#myClass").append("已选课程</br><tr><th>课程id</th><th>课程名称</th><th>课程容量</th><th>已选人数</th><th>操作</th></tr>");
                    for (var i = 0; i < data.data.list.length; i++) {
                        var id = data.data.list[i].id;
                        var list = "<tr id=\"class" + id + "\"><th>" + id + "</th><th id=\"class_name\">" +
                            data.data.list[i].name + "</th><th>" +
                            data.data.list[i].student_num + "</th><th>" +
                            data.data.list[i].current_num + "</th><th>" +
                            "<input type='button' onclick=\"dropClass(" + id + ")\" value='退选此课程'></th><th>" +
                            "<input type='button' onclick=\"showTests(" + id + ")\" value='显示课程测试'></th>" +
                            "</tr><br/>";
                        // "<input type='button' onclick=\"deleteClass(" + id + ")\" value='删除'>" +
                        // "<input type='button' onclick=\"updateClass(" + id + ")\" value='更新'>" +
                        // "<input type='button' onclick=\"showTests(" + id + ")\" value='查看课程测试'>" + "</th></tr><br/>";
                        $("#myClass").append(list);
                    }
                } else {
                    alert(data.msg);
                }
            },
            error: function (data) {
                alert("请求失败");
            }
        });
    }

    function showClass() {
        $.ajax({
            url: "/student/showAllClasses",
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (data.code === 10 || data.code === 11) {
                    alert(data.msg);
                    window.location.href = "/index";
                } else if (data.code === 0) {
                    $('#class').empty();
                    $("#class").append("所有课程</br><tr><th>课程id</th><th>课程名称</th><th>课程容量</th><th>已选人数</th><th>操作</th></tr>");
                    for (var i = 0; i < data.data.list.length; i++) {
                        var id = data.data.list[i].id;
                        var list = "<tr id=\"class" + id + "\"><th>" + id + "</th><th>" +
                            data.data.list[i].name + "</th><th>" +
                            data.data.list[i].student_num + "</th><th>" +
                            data.data.list[i].current_num + "</th><th>" +
                            "<input type='button' onclick=\"addClass(" + id + ")\" value='选择此课程'>" +
                            "</th></tr><br/>";
                            // "<input type='button' onclick=\"deleteClass(" + id + ")\" value='删除'>" +
                            // "<input type='button' onclick=\"updateClass(" + id + ")\" value='更新'>" +
                            // "<input type='button' onclick=\"showTests(" + id + ")\" value='查看课程测试'>" + "</th></tr><br/>";
                        $("#class").append(list);
                    }
                } else {
                    alert(data.msg);
                }
            },
            error: function (data) {
                alert("请求失败");
            }
        });
    }

    function addClass(id) {
        if (confirm("确定要选择课程?")) {
            $.ajax({
                url: "/student/addClass",
                type: "POST",
                dataType: "json",
                data: {
                    class_id: id
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.code === 10 || data.code === 11) {
                        window.location.href = "/index";
                    } else if (data.code === 0) {
                        showClass();
                        showMyClass();
                    }
                },
                error: function (data) {
                    alert("请求失败~");
                }
            });
        }
    }

    function dropClass(id) {
        if (confirm("确定要选择课程?")) {
            $.ajax({
                url: "/student/dropClass",
                type: "POST",
                dataType: "json",
                data: {
                    class_id: id
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.code === 10 || data.code === 11) {
                        window.location.href = "/index";
                    } else if (data.code === 0) {
                        showClass();
                        showMyClass();
                    }
                },
                error: function (data) {
                    alert("请求失败~");
                }
            });
        }
    }
</script>
<c:choose>
    <c:when test="<%=flag %>">
        欢迎，<%=name%> 学生<br/>
        <a href="${pageContext.request.contextPath}/student/class">我的课程</a> <br/>
        <a href="${pageContext.request.contextPath}/logout">注销</a>
    </c:when>
    <c:otherwise>
        您还未登录!
        <a href="${pageContext.request.contextPath}/index">回到首页</a>
    </c:otherwise>
</c:choose>
<div>
    <br/>
    <table id="class"></table>
    <br/>
    <table id="myClass"></table>
    <br/>
    <table id="test"></table>
    <br/>
    <table id="question"></table>
    <br/>
    <form id="submitTest"></form>
</div>
</body>
</html>
