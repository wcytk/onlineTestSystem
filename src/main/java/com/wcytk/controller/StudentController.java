package com.wcytk.controller;

import com.sun.org.apache.regexp.internal.RE;
import com.wcytk.entity.*;
import com.wcytk.entity.Class;
import com.wcytk.service.StudentService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;

    @ResponseBody
    @RequestMapping(value = "/student/showAllClasses", method = RequestMethod.POST)
    public JSONObject studentShowAllClasses(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        if (request.getSession().getAttribute("user") != null) {
            try {
                List<Class> classes = studentService.showAllClasses();
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("list", classes);
                jsonObject.put("data", jsonObject1);
                jsonObject.put("code", 0);
                jsonObject.put("msg", "课程查询成功!");
                return jsonObject;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (request.getSession().getAttribute("teacher") != null) {
                jsonObject.put("code", 11);
                jsonObject.put("msg", "您不是学生!");
            } else {
                jsonObject.put("code", 10);
                jsonObject.put("msg", "请先登录!");
            }
            return jsonObject;
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "查询课程失败!");
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/student/submitAnswer", method = RequestMethod.POST)
    public JSONObject studentSubmitAnswer(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String testId = request.getParameter("test_id");
        String answers = request.getParameter("answers");
        JSONArray jsonArray = JSONArray.fromObject(answers);
        int grade = 0;
        if (request.getSession().getAttribute("user") != null) {
            User user = (User) request.getSession().getAttribute("user");
            int student_id = user.getStudent_id();
            if (isInteger(testId)) {
                int test_id = Integer.parseInt(testId);
                Test test = new Test();
                try {
                    test = studentService.getTest(test_id);
                    if (!test.getStatus()) {
                        jsonObject.put("code", 1);
                        jsonObject.put("msg", "测试已结束!");
                        return jsonObject;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    jsonObject.put("code", 1);
                    jsonObject.put("msg", "查询测试失败!");
                    return jsonObject;
                }
                try {
                    UserTest userTest = studentService.getMyTest(student_id, test_id);
                    if (userTest != null) {
                        jsonObject.put("code", 1);
                        jsonObject.put("msg", "您已提交过本测试!");
                        return jsonObject;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    int question_id;
                    String answer;
                    if (jsonObject1.get("question_id") == null) {
                        question_id = 0;
                    }
                    if (isInteger(jsonObject1.get("question_id").toString())) {
                        question_id = Integer.parseInt(jsonObject1.get("question_id").toString());
                        answer = jsonObject1.get("answer").toString();
                        if (jsonObject1.get("answer") == null) {
                            answer = "";
                        }
                        try {
                            Question question = studentService.getQuestion(question_id);
                            String questionAnswer = question.getAnswer();
                            System.out.println(questionAnswer+" "+answer);
                            if (answer.equals(questionAnswer)) {
                                grade += question.getGrade();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            jsonObject.put("code", 1);
                            jsonObject.put("msg", "查询题目出错!");
                            return jsonObject;
                        }
                    }
                }
                String create_time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Timestamp(System.currentTimeMillis()));
                try {
                    boolean sa = studentService.addUserTest(student_id, test_id, grade * 100 / test.getFull_grade(), create_time);
                    if (sa) {
                        jsonObject.put("code", 0);
                        jsonObject.put("msg", "考试提交成功!");
                        return jsonObject;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (request.getSession().getAttribute("teacher") != null) {
                jsonObject.put("code", 11);
                jsonObject.put("msg", "您不是学生!");
            } else {
                jsonObject.put("code", 10);
                jsonObject.put("msg", "请先登录!");
            }
            return jsonObject;
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "考试提交失败!");
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/student/showQuestions", method = RequestMethod.POST)
    public JSONObject studentShowQuestions(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String testId = request.getParameter("test_id");
        if (request.getSession().getAttribute("user") != null) {
            if (isInteger(testId)) {
                int test_id = Integer.parseInt(testId);
                try {
                    Test test = studentService.getTest(test_id);
                    if (!test.getStatus()) {
                        jsonObject.put("code", 1);
                        jsonObject.put("msg", "测试已结束!");
                        return jsonObject;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    jsonObject.put("code", 1);
                    jsonObject.put("msg", "查询测试失败!");
                    return jsonObject;
                }
                try {
                    List<Question> questions = studentService.showQuestions(test_id);
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("list", questions);
                    jsonObject.put("data", jsonObject1);
                    jsonObject.put("code", 0);
                    jsonObject.put("msg", "试题查询成功!");
                    return jsonObject;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (request.getSession().getAttribute("teacher") != null) {
                jsonObject.put("code", 11);
                jsonObject.put("msg", "您不是学生!");
            } else {
                jsonObject.put("code", 10);
                jsonObject.put("msg", "请先登录!");
            }
            return jsonObject;
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "查询试题失败!");
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/student/showTests", method = RequestMethod.POST)
    public JSONObject studentShowTests(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String classId = request.getParameter("class_id");
        if (request.getSession().getAttribute("user") != null) {
            if (isInteger(classId)) {
                int class_id = Integer.parseInt(classId);
                try {
                    List<Test> tests = studentService.showTests(class_id);
                    Question question = new Question();
                    for (Test test : tests) {
                        test.setQuestion(question);
                    }
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("list", tests);
                    jsonObject.put("data", jsonObject1);
                    jsonObject.put("code", 0);
                    jsonObject.put("msg", "测试查询成功!");
                    return jsonObject;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (request.getSession().getAttribute("teacher") != null) {
                jsonObject.put("code", 11);
                jsonObject.put("msg", "您不是学生!");
            } else {
                jsonObject.put("code", 10);
                jsonObject.put("msg", "请先登录!");
            }
            return jsonObject;
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "查询测试失败!");
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/student/showMyClasses", method = RequestMethod.POST)
    public JSONObject studentShowMyClasses(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        if (request.getSession().getAttribute("user") != null) {
            User student = (User) request.getSession().getAttribute("user");
            int student_id = student.getStudent_id();
            try {
                List<Class> classes = studentService.showMyClasses(student_id);
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("list", classes);
                jsonObject.put("data", jsonObject1);
                jsonObject.put("code", 0);
                jsonObject.put("msg", "课程查询成功!");
                return jsonObject;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (request.getSession().getAttribute("teacher") != null) {
                jsonObject.put("code", 11);
                jsonObject.put("msg", "您不是学生!");
            } else {
                jsonObject.put("code", 10);
                jsonObject.put("msg", "请先登录!");
            }
            return jsonObject;
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "查询课程失败!");
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/student/addClass", method = RequestMethod.POST)
    public JSONObject studentAddClass(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String classId = request.getParameter("class_id");
        int current_num = 0;
        if (isInteger(classId)) {
            int class_id = Integer.parseInt(classId);
            if (request.getSession().getAttribute("user") != null) {
                User student = (User) request.getSession().getAttribute("user");
                int student_id = student.getStudent_id();
                Class newClass = new Class();
                try {
                    newClass = studentService.getClass(class_id);
                    Class myClass = studentService.getMyClass(student_id, class_id);
                    if (myClass != null) {
                        jsonObject.put("code", 1);
                        jsonObject.put("msg", "您已选择此课程!");
                        return jsonObject;
                    }
                    if (newClass == null) {
                        jsonObject.put("code", 1);
                        jsonObject.put("msg", "未查询到课程!");
                        return jsonObject;
                    }
                    if (newClass.getCurrent_num() >= newClass.getStudent_num()) {
                        jsonObject.put("code", 1);
                        jsonObject.put("msg", "当前课程人数已满!");
                        return jsonObject;
                    }
                } catch (Exception e) {
                    jsonObject.put("code", 1);
                    jsonObject.put("msg", "查询课程出错!");
                    return jsonObject;
                }
                String create_time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Timestamp(System.currentTimeMillis()));
                try {
                    boolean s = studentService.addClass(student_id, class_id, create_time);
                    boolean s1 = studentService.addClassNum(class_id);
                    if (s && s1) {
                        jsonObject.put("code", 0);
                        jsonObject.put("msg", "选课成功!");
                        return jsonObject;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (request.getSession().getAttribute("teacher") != null) {
                    jsonObject.put("code", 11);
                    jsonObject.put("msg", "您不是学生!");
                } else {
                    jsonObject.put("code", 10);
                    jsonObject.put("msg", "请先登录!");
                }
                return jsonObject;
            }
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "选课失败!");
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/student/dropClass", method = RequestMethod.POST)
    public JSONObject studentDropClass(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String classId = request.getParameter("class_id");
        if (isInteger(classId)) {
            int class_id = Integer.parseInt(classId);
            if (request.getSession().getAttribute("user") != null) {
                User student = (User) request.getSession().getAttribute("user");
                int student_id = student.getStudent_id();
                Class newClass = new Class();
                try {
                    newClass = studentService.getMyClass(student_id, class_id);
                    if (newClass == null) {
                        jsonObject.put("code", 1);
                        jsonObject.put("msg", "您未选择此课程!");
                        return jsonObject;
                    }
                    Class oldClass = studentService.getClass(class_id);
                    if (oldClass.getCurrent_num() <= 0) {
                        jsonObject.put("code", 1);
                        jsonObject.put("msg", "您未选择此课程!");
                        return jsonObject;
                    }
                } catch (Exception e) {
                    jsonObject.put("code", 1);
                    jsonObject.put("msg", "查询课程出错!");
                    return jsonObject;
                }
                try {
                    boolean s = studentService.dropClass(student_id, class_id);
                    boolean s1 = studentService.subClassNum(class_id);
                    if (s && s1) {
                        jsonObject.put("code", 0);
                        jsonObject.put("msg", "退选课程成功!");
                        return jsonObject;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (request.getSession().getAttribute("user") != null) {
                    jsonObject.put("code", 11);
                    jsonObject.put("msg", "您不是学生!");
                } else {
                    jsonObject.put("code", 10);
                    jsonObject.put("msg", "请先登录!");
                }
                return jsonObject;
            }
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "退选失败!");
        return jsonObject;
    }

    private static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        if (str.equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile("[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    @RequestMapping(value = "/student/class", method = {RequestMethod.GET, RequestMethod.POST})
    public String studentClass() {
        return "/student/studentClass";
    }
}
