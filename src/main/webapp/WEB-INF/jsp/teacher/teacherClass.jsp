<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.wcytk.entity.Teacher" %><%--
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
    Teacher teacher = new Teacher();
    boolean flag = false;
    if (session.getAttribute("teacher") != null) {
        teacher = (Teacher) session.getAttribute("teacher");
        name = teacher.getName();
        flag = true;
    }
    if (!flag) {
        response.sendRedirect("/index");
    }
%>
<script type="text/javascript">
    window.onload = function () {
        showClass();
        showAllQuestions();
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

    function updateClass(classId) {
        var name = $('#class' + classId).find('input[name=\'name\']').val();
        var student_num = $('#class' + classId).find('input[name=\'student_num\']').val();
        $.ajax({
            url: "/teacher/updateClass",
            type: "POST",
            dataType: "json",
            data: {
                id: classId,
                name: name,
                student_num: student_num
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
        })
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

    function updateTest(classId, testId) {
        var name = $('#test' + testId).find('input[name=\'name\']').val();
        var student_num = $('#test' + testId).find('input[name=\'student_num\']').val();
        var duration = $('#test' + testId).find('input[name=\'duration\']').val();
        var status = $('#test' + testId).find('input[name=\'status' + testId + '\']').val();
        $.ajax({
            url: "/teacher/updateTest",
            type: "POST",
            dataType: "json",
            data: {
                class_id: classId,
                test_id: testId,
                name: name,
                student_num: student_num,
                status: status,
                duration: duration
            },
            success: function (data) {
                alert(data.msg);
                if (data.code === 10 || data.code === 11) {
                    window.location.href = "/index";
                } else if (data.code === 0) {
                    showQuestions(classId, testId);
                }
            },
            error: function (data) {
                alert("请求失败~");
            }
        });
    }

    function updateQuestion(id) {
        var question = $('#question' + id).find('input[name=\'question\']').val();
        var a_option = $('#question' + id).find('input[name=\'a_option\']').val();
        var b_option = $('#question' + id).find('input[name=\'b_option\']').val();
        var c_option = $('#question' + id).find('input[name=\'c_option\']').val();
        var d_option = $('#question' + id).find('input[name=\'d_option\']').val();
        var e_option = $('#question' + id).find('input[name=\'e_option\']').val();
        var f_option = $('#question' + id).find('input[name=\'f_option\']').val();
        var answer = $('#question' + id).find('input[name=\'answer\']').val();
        var type = $('#question' + id).find('input[name=\'type\']').val();
        var grade = $('#question' + id).find('input[name=\'grade\']').val();
        $.ajax({
            url: "/teacher/updateQuestion",
            type: "POST",
            dataType: "json",
            data: {
                question_id: id,
                question: question,
                a_option: a_option,
                b_option: b_option,
                c_option: c_option,
                d_option: d_option,
                e_option: e_option,
                f_option: f_option,
                answer: answer,
                type: type,
                grade: grade
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

    function showQuestions(classId, testId) {
        $.ajax({
            url: "/teacher/showQuestions",
            type: "POST",
            dataType: "json",
            data: {
                class_id: classId,
                test_id: testId
            },
            success: function (data) {
                if (data.code === 10 || data.code === 11) {
                    alert(data.msg);
                    window.location.href = "/index";
                } else if (data.code === 0) {
                    $('#testQuestion').empty();
                    $('#addTestQuestions').empty();
                    var testName = $('#test' + testId).find('input[name=\'name\']').val();
                    $('#testQuestion').append(testName + "的试题");
                    $("#testQuestion").append("<tr><th>题目id</th><th>教师id</th><th>题干</th><th>选项A</th><th>选项B</th><th>选项C</th><th>选项D</th><th>选项E</th><th>选项F</th><th>答案</th><th>类型</th><th>分值</th><th>创建时间</th></tr>");
                    for (var i = 0; i < data.data.list.length; i++) {
                        var id = data.data.list[i].id;
                        console.log(data.data.list[i].question);
                        var list = "<tr id=\"question" + id + "\"><th>" + id + "</th><th>" +
                            data.data.list[i].teacher_id + "</th><th><input name=\"question\" value=\"" +
                            data.data.list[i].question + "\"></th><th><input name=\"a_option\" value=\"" +
                            data.data.list[i].a_option + "\"></th><th><input name=\"b_option\" value=\"" +
                            data.data.list[i].b_option + "\"></th><th><input name=\"c_option\" value=\"" +
                            data.data.list[i].c_option + "\"></th><th><input name=\"d_option\" value=\"" +
                            data.data.list[i].d_option + "\"></th><th><input name=\"e_option\" value=\"" +
                            data.data.list[i].e_option + "\"></th><th><input name=\"f_option\" value=\"" +
                            data.data.list[i].f_option + "\"></th><th><input name=\"answer\" value=\"" +
                            data.data.list[i].answer + "\"></th><th><input name=\"type\" value=\"" +
                            data.data.list[i].type + "\"></th><th><input name=\"grade\" value=\"" +
                            data.data.list[i].grade + "\"></th><th>" +
                            data.data.list[i].create_time + "</th><th>" +
                            "<input type='button' onclick=\"deleteQuestionFromTest(" + testId + "," + id + ")\" value='删除'>" +
                            "<input type='button' onclick=\"updateQuestion(" + id + ")\" value='更新'>" +
                            "</th></tr><br/>";
                        $("#testQuestion").append(list);
                    }
                    $('#addTestQuestions').append("<input type=\"text\" name=\"question\" placeholder=\"题干\" id=\"addNewQuestion\"/>" +
                        "<input type=\"text\" name=\"a_option\" placeholder=\"A选项\" id=\"addAOption\"/>" +
                        "<input type=\"text\" name=\"b_option\" placeholder=\"B选项\" id=\"addBOption\"/>" +
                        "<input type=\"text\" name=\"c_option\" placeholder=\"C选项\" id=\"addCOption\"/>" +
                        "<input type=\"text\" name=\"d_option\" placeholder=\"D选项\" id=\"addDOption\"/>" +
                        "<input type=\"text\" name=\"e_option\" placeholder=\"E选项\" id=\"addEOption\"/>" +
                        "<input type=\"text\" name=\"f_option\" placeholder=\"F选项\" id=\"addFOption\"/>" +
                        "<input type=\"text\" name=\"answer\" placeholder=\"答案\" id=\"addAnswer\"/>" +
                        "<input type=\"text\" name=\"type\" placeholder=\"类型\" id=\"addType\"/>" +
                        "<input type=\"text\" name=\"type\" placeholder=\"分值\" id=\"addGrade\"/><br/>" +
                        "<input type=\"button\" value=\"添加题目\" onclick=\"addQuestion()\"/>")
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
            url: "/teacher/showTests",
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
                    $('#addTest').empty();
                    var className = $('#class' + classId).find('input[name=\'name\']').val();
                    $('#test').append(className + "课程" + "的测试");
                    $("#test").append("<tr><th>测试id</th><th>课程id</th><th>测试名称</th><th>测试容量</th><th>已选人数</th><th>创建时间</th><th>测试状态</th><th>测试时长</th><th>总分数</th><th>操作</th></tr>");
                    for (var i = 0; i < data.data.list.length; i++) {
                        var id = data.data.list[i].id;
                        var list = "<tr id=\"test" + id + "\"><th>" + id + "</th><th>" +
                            data.data.list[i].class_id + "</th><th><input name=\"name\" value=\"" +
                            data.data.list[i].name + "\"></th><th><input name=\"student_num\" value=\"" +
                            data.data.list[i].student_num + "\"></th><th>" +
                            data.data.list[i].current_num + "</th><th>" +
                            data.data.list[i].create_time + "</th><th><input name=\"status" + id + "\" type=\"checkbox\" data-size=\"mini\" value=\"" +
                            data.data.list[i].status + "\"></th><th><input name=\"duration\" value=\"" +
                            data.data.list[i].duration + "\"></th><th>" +
                            data.data.list[i].full_grade + "</th><th>" +
                            "<input type='button' onclick=\"deleteTest(" + classId + "," + id + ")\" value='删除'>" +
                            "<input type='button' onclick=\"updateTest(" + classId + "," + id + ")\" value='更新'>" +
                            "<input type='button' onclick=\"showQuestions(" + classId + "," + id + ")\" value='查看测试试题'>" +
                            "</th></tr><br/>";
                        $("#test").append(list);
                        $("#test").append("<script type=\"text/javascript\">" +
                            "                        $('[name=\"status" + id + "\"]').bootstrapSwitch({" +
                            "                            onText: \"开启\"," +
                            "                            offText: \"关闭\"," +
                            "                            onColor: \"success\"," +
                            "                            offColor: \"warning\"," +
                            "                            onSwitchChange: function (event, state) {" +
                            "                                if (state === true) {" +
                            "                                    $(this).val(\"true\");" +
                            "                                } else {" +
                            "                                    $(this).val(\"false\");" +
                            "                                }" +
                            "                            }" +
                            "                        }).bootstrapSwitch('state', " + data.data.list[i].status + ")" +
                            "                    <\/script>");
                    }
                    $('#addTest').append("<input type=\"text\" name=\"name\" placeholder=\"测试名称\" id=\"addTestName\"/>" +
                        "<input type=\"text\" name=\"student_num\" placeholder=\"测试容量\" id=\"addTestStudentNum\"/>" +
                        "<input type=\"text\" name=\"duration\" placeholder=\"测试时长\" id=\"addTestDuration\"/><br/>" +
                        "<input type=\"button\" value=\"添加测试\" onclick=\"addTest(" + classId + ")\"/>")
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
            url: "/teacher/showClasses",
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (data.code === 10 || data.code === 11) {
                    alert(data.msg);
                    window.location.href = "/index";
                } else if (data.code === 0) {
                    $('#class').empty();
                    $("#class").append("<tr><th>课程id</th><th>教师id</th><th>课程名称</th><th>课程容量</th><th>已选人数</th><th>创建时间</th><th>操作</th></tr>");
                    for (var i = 0; i < data.data.list.length; i++) {
                        var id = data.data.list[i].id;
                        var list = "<tr id=\"class" + id + "\"><th>" + id + "</th><th>" +
                            data.data.list[i].teacher_id + "</th><th><input name=\"name\" value=\"" +
                            data.data.list[i].name + "\"></th><th><input name=\"student_num\" value=\"" +
                            data.data.list[i].student_num + "\"></th><th>" +
                            data.data.list[i].current_num + "</th><th>" +
                            data.data.list[i].create_time + "</th><th>" +
                            "<input type='button' onclick=\"deleteClass(" + id + ")\" value='删除'>" +
                            "<input type='button' onclick=\"updateClass(" + id + ")\" value='更新'>" +
                            "<input type='button' onclick=\"showTests(" + id + ")\" value='查看课程测试'>" + "</th></tr><br/>";
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

    function addClass() {
        if (confirm("确定要添加这个课程?")) {
            $.ajax({
                url: "/teacher/addClass",
                type: "POST",
                dataType: "json",
                data: {
                    name: $("#addName").val(),
                    student_num: $("#addStudentNum").val()
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

    function showAllQuestions() {
        $.ajax({
            url: "/teacher/showAllQuestions",
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (data.code === 10 || data.code === 11) {
                    alert(data.msg);
                    window.location.href = "/index";
                } else if (data.code === 0) {
                    $('#question').empty();
                    $('#addQuestions').empty();
                    $('#question').append("所有试题");
                    $("#question").append("<tr><th>题目id</th><th>教师id</th><th>题干</th><th>选项A</th><th>选项B</th><th>选项C</th><th>选项D</th><th>选项E</th><th>选项F</th><th>答案</th><th>类型</th><th>分值</th><th>创建时间</th><th>加入到</th><th>操作</th></tr>");
                    for (var i = 0; i < data.data.list.length; i++) {
                        var id = data.data.list[i].id;
                        var list = "<tr id=\"question" + id + "\"><th>" + id + "</th><th>" +
                            data.data.list[i].teacher_id + "</th><th><input name=\"question\" value=\"" +
                            data.data.list[i].question + "\"></th><th><input name=\"a_option\" value=\"" +
                            data.data.list[i].a_option + "\"></th><th><input name=\"b_option\" value=\"" +
                            data.data.list[i].b_option + "\"></th><th><input name=\"c_option\" value=\"" +
                            data.data.list[i].c_option + "\"></th><th><input name=\"d_option\" value=\"" +
                            data.data.list[i].d_option + "\"></th><th><input name=\"e_option\" value=\"" +
                            data.data.list[i].e_option + "\"></th><th><input name=\"f_option\" value=\"" +
                            data.data.list[i].f_option + "\"></th><th><input name=\"answer\" value=\"" +
                            data.data.list[i].answer + "\"></th><th><input name=\"type\" value=\"" +
                            data.data.list[i].type + "\"></th><th><input name=\"grade\" value=\"" +
                            data.data.list[i].grade + "\"></th><th>" +
                            data.data.list[i].create_time + "</th><th>" +
                            "<input placeholder='测试id' name=\"addToTest\"></th><th>" +
                            "<input type='button' onclick=\"deleteQuestion(" + id + ")\" value='删除'>" +
                            "<input type='button' onclick=\"updateQuestion(" + id + ")\" value='更新'>" +
                            "<input type='button' onclick=\"addQuestionToTest("+ id +")\" value=\"添加\"></th></tr><br/>";
                        $("#question").append(list);
                    }
                    $('#addQuestions').append("<input type=\"text\" name=\"question\" placeholder=\"题干\" id=\"addNewQuestion\"/>" +
                        "<input type=\"text\" name=\"a_option\" placeholder=\"A选项\" id=\"addAOption\"/>" +
                        "<input type=\"text\" name=\"b_option\" placeholder=\"B选项\" id=\"addBOption\"/>" +
                        "<input type=\"text\" name=\"c_option\" placeholder=\"C选项\" id=\"addCOption\"/>" +
                        "<input type=\"text\" name=\"d_option\" placeholder=\"D选项\" id=\"addDOption\"/>" +
                        "<input type=\"text\" name=\"e_option\" placeholder=\"E选项\" id=\"addEOption\"/>" +
                        "<input type=\"text\" name=\"f_option\" placeholder=\"F选项\" id=\"addFOption\"/>" +
                        "<input type=\"text\" name=\"answer\" placeholder=\"答案\" id=\"addAnswer\"/>" +
                        "<input type=\"text\" name=\"type\" placeholder=\"类型\" id=\"addType\"/>" +
                        "<input type=\"text\" name=\"type\" placeholder=\"分值\" id=\"addGrade\"/><br/>" +
                        "<input type=\"button\" value=\"添加题目\" onclick=\"addQuestion()\"/>")
                }
            },
            error: function (data) {
                alert("请求失败~");
            }
        });
    }
</script>
<c:choose>
    <c:when test="<%=flag %>">
        欢迎，<%=name%> 教师<br/>
        <a href="${pageContext.request.contextPath}/teacher/class">课程管理</a> <br/>
        <a href="${pageContext.request.contextPath}/logout">注销</a>
    </c:when>
    <c:otherwise>
        您还未登录!
        <a href="${pageContext.request.contextPath}/index">回到首页</a>
    </c:otherwise>
</c:choose>
<div>
    当前课程<br/>
    <table id="class"></table>
    <form>
        <input type="text" name="name" placeholder="课程名称" id="addName"/>
        <input type="text" name="student_num" placeholder="课程容量" id="addStudentNum"/><br/>
        <input type="button" value="添加课程" onclick="addClass()"/>
    </form>
    <br/>
    <table id="test"></table>
    <form id="addTest"></form>
    <br/>
    <table id="testQuestion"></table>
    <form id="addTestQuestions"></form>
    <br/>
    <table id="question"></table>
    <form id="addQuestions"></form>
</div>
</body>
</html>
