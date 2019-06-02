package com.wcytk.controller;

import com.wcytk.entity.Class;
import com.wcytk.entity.Question;
import com.wcytk.entity.Teacher;
import com.wcytk.entity.Test;
import com.wcytk.service.TeacherService;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Controller
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @ResponseBody
    @RequestMapping(value = "/teacher/addClass", method = RequestMethod.POST)
    public JSONObject teacherAddClass(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String name = request.getParameter("name");
        String studentNum = request.getParameter("student_num");
        int current_num = 0;
        if (isInteger(studentNum)) {
            int student_num = Integer.parseInt(studentNum);
            if (request.getSession().getAttribute("teacher") != null) {
                Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
                int teacher_id = teacher.getTeacher_id();
                String create_time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Timestamp(System.currentTimeMillis()));
                try {
                    boolean s = teacherService.addClass(teacher_id, name, student_num, current_num, create_time);
                    if (s) {
                        jsonObject.put("code", 0);
                        jsonObject.put("msg", "��ӿ��óɹ�!");
                        return jsonObject;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (request.getSession().getAttribute("user") != null) {
                    jsonObject.put("code", 11);
                    jsonObject.put("msg", "�����ǽ�ʦ!");
                } else {
                    jsonObject.put("code", 10);
                    jsonObject.put("msg", "���ȵ�¼!");
                }
                return jsonObject;
            }
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "��ӿ���ʧ��!");
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/teacher/addTest", method = RequestMethod.POST)
    public JSONObject teacherAddTest(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String classId = request.getParameter("class_id");
        String name = request.getParameter("name");
        String studentNum = request.getParameter("student_num");
        String time = request.getParameter("duration");
        int current_num = 0;
        if (isInteger(studentNum) && isInteger(classId) && isInteger(time)) {
            int student_num = Integer.parseInt(studentNum);
            int class_id = Integer.parseInt(classId);
            int duration = Integer.parseInt(time);
            if (request.getSession().getAttribute("teacher") != null) {
                Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
                int teacher_id = teacher.getTeacher_id();
                if (teacherService.getClass(class_id, teacher_id) == null) {
                    jsonObject.put("code", 1);
                    jsonObject.put("msg", "δ��ѯ���༶!");
                    return jsonObject;
                }
                String create_time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Timestamp(System.currentTimeMillis()));
                try {
                    boolean s = teacherService.addTest(class_id, name, student_num, current_num, create_time, false, duration);
                    if (s) {
                        jsonObject.put("code", 0);
                        jsonObject.put("msg", "��ӿ��óɹ�!");
                        return jsonObject;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (request.getSession().getAttribute("user") != null) {
                    jsonObject.put("code", 11);
                    jsonObject.put("msg", "�����ǽ�ʦ!");
                } else {
                    jsonObject.put("code", 10);
                    jsonObject.put("msg", "���ȵ�¼!");
                }
                return jsonObject;
            }
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "��ӿ���ʧ��!");
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/teacher/addQuestionToTest", method = RequestMethod.POST)
    public JSONObject teacherAddQuestionToTest(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String testId = request.getParameter("test_id");
        String questionId = request.getParameter("question_id");
        int current_num = 0;
        if (isInteger(questionId) && isInteger(testId)) {
            int test_id = Integer.parseInt(testId);
            int question_id = Integer.parseInt(questionId);
            if (request.getSession().getAttribute("teacher") != null) {
                Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
                int teacher_id = teacher.getTeacher_id();
                if (teacherService.getQuestion(question_id, teacher_id) == null) {
                    jsonObject.put("code", 1);
                    jsonObject.put("msg", "δ��ѯ������!");
                    return jsonObject;
                }
                try {
                    boolean s = teacherService.addQuestionToTest(question_id, test_id);
                    if (s) {
                        jsonObject.put("code", 0);
                        jsonObject.put("msg", "��ӵ����Գɹ�!");
                        return jsonObject;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (request.getSession().getAttribute("user") != null) {
                    jsonObject.put("code", 11);
                    jsonObject.put("msg", "�����ǽ�ʦ!");
                } else {
                    jsonObject.put("code", 10);
                    jsonObject.put("msg", "���ȵ�¼!");
                }
                return jsonObject;
            }
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "��ӵ�����ʧ��!");
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/teacher/addQuestion", method = RequestMethod.POST)
    public JSONObject teacherAddQuestion(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String question = request.getParameter("question");
        String a_option = request.getParameter("a_option");
        String b_option = request.getParameter("b_option");
        String c_oprion = request.getParameter("c_option");
        String d_option = request.getParameter("d_option");
        String e_option = request.getParameter("e_option");
        String f_option = request.getParameter("f_option");
        String answer = request.getParameter("answer");
        String t = request.getParameter("type");
        if (isInteger(t)) {
            // type == 0 ����ѡ����
            // type == 1 ����ѡ����
            // type == 2 �ж���
            // type == 3 �����
            int type = Integer.parseInt(t);
            if (request.getSession().getAttribute("teacher") != null) {
                Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
                int teacher_id = teacher.getTeacher_id();
                String create_time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Timestamp(System.currentTimeMillis()));
                try {
                    boolean s = teacherService.addQuestion(teacher_id, question, a_option, b_option, c_oprion, d_option, e_option, f_option, answer, type, create_time);
                    if (s) {
                        jsonObject.put("code", 0);
                        jsonObject.put("msg", "�����Ŀ�ɹ�!");
                        return jsonObject;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (request.getSession().getAttribute("user") != null) {
                    jsonObject.put("code", 11);
                    jsonObject.put("msg", "�����ǽ�ʦ!");
                } else {
                    jsonObject.put("code", 10);
                    jsonObject.put("msg", "���ȵ�¼!");
                }
                return jsonObject;
            }
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "�����Ŀʧ��!");
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/teacher/showClasses", method = RequestMethod.POST)
    public JSONObject teacherShowClasses(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        if (request.getSession().getAttribute("teacher") != null) {
            Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
            int teacher_id = teacher.getTeacher_id();
            try {
                List<Class> classes = teacherService.showClasses(teacher_id);
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("list", classes);
                jsonObject.put("data", jsonObject1);
                jsonObject.put("code", 0);
                jsonObject.put("msg", "��ʦ��ѯ�γ̳ɹ�!");
                return jsonObject;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (request.getSession().getAttribute("user") != null) {
                jsonObject.put("code", 11);
                jsonObject.put("msg", "�����ǽ�ʦ!");
            } else {
                jsonObject.put("code", 10);
                jsonObject.put("msg", "���ȵ�¼!");
            }
            return jsonObject;
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "��ʦ��ѯ�γ�ʧ��!");
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/teacher/showTests", method = RequestMethod.POST)
    public JSONObject teacherShowTests(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String classId = request.getParameter("class_id");
        if (isInteger(classId)) {
            int class_id = Integer.parseInt(classId);
            if (request.getSession().getAttribute("teacher") != null) {
                Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
                int teacher_id = teacher.getTeacher_id();
                if (teacherService.getClass(class_id, teacher_id) == null) {
                    jsonObject.put("code", 1);
                    jsonObject.put("msg", "δ��ѯ���༶!");
                    return jsonObject;
                }
                try {
                    List<Test> tests = teacherService.showTests(class_id);
                    Question question = new Question();
                    question.setQuestion("test");
                    for (Test test : tests) {
                        test.setQuestion(question);
                    }
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("list", tests);
                    jsonObject.put("data", jsonObject1);
                    jsonObject.put("code", 0);
                    jsonObject.put("msg", "��ʦ��ѯ���Գɹ�!");
                    System.out.println(jsonObject);
                    return jsonObject;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (request.getSession().getAttribute("user") != null) {
                    jsonObject.put("code", 11);
                    jsonObject.put("msg", "�����ǽ�ʦ!");
                } else {
                    jsonObject.put("code", 10);
                    jsonObject.put("msg", "���ȵ�¼!");
                }
                return jsonObject;
            }
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "��ʦ��ѯ����ʧ��!");
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/teacher/showQuestions", method = RequestMethod.POST)
    public JSONObject teacherShowQuestions(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String classId = request.getParameter("class_id");
        String testId = request.getParameter("test_id");
        if (isInteger(classId) && isInteger(testId)) {
            int class_id = Integer.parseInt(classId);
            int test_id = Integer.parseInt(testId);
            if (request.getSession().getAttribute("teacher") != null) {
                Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
                int teacher_id = teacher.getTeacher_id();
                if (teacherService.getClass(class_id, teacher_id) == null) {
                    jsonObject.put("code", 1);
                    jsonObject.put("msg", "δ��ѯ���༶!");
                    return jsonObject;
                }
                if (teacherService.getTest(test_id, class_id) == null) {
                    jsonObject.put("code", 1);
                    jsonObject.put("msg", "δ��ѯ������!");
                }
                try {
                    List<Test> tests = teacherService.showQuestions(test_id);
                    List<Question> questions = new ArrayList<>();
                    if (tests == null) {
                        jsonObject.put("code", 1);
                        jsonObject.put("msg", "�˲��Ի�û������!");
                        return jsonObject;
                    }
                    for (Test test : tests) {
                        questions.add(test.getQuestion());
                    }
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("list", questions);
                    jsonObject.put("data", jsonObject1);
                    jsonObject.put("code", 0);
                    jsonObject.put("msg", "��ʦ��ѯ��Ŀ�ɹ�!");
                    return jsonObject;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (request.getSession().getAttribute("user") != null) {
                    jsonObject.put("code", 11);
                    jsonObject.put("msg", "�����ǽ�ʦ!");
                } else {
                    jsonObject.put("code", 10);
                    jsonObject.put("msg", "���ȵ�¼!");
                }
                return jsonObject;
            }
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "��ʦ��ѯ��Ŀʧ��!");
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/teacher/showAllQuestions", method = RequestMethod.POST)
    public JSONObject teacherShowAllQuestions(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        if (request.getSession().getAttribute("teacher") != null) {
            Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
            int teacher_id = teacher.getTeacher_id();
            try {
                List<Question> questions = teacherService.showTeacherQuestion(teacher_id);
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("list", questions);
                jsonObject.put("data", jsonObject1);
                jsonObject.put("code", 0);
                jsonObject.put("msg", "��ʦ��ѯ��Ŀ�ɹ�!");
                return jsonObject;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (request.getSession().getAttribute("user") != null) {
                jsonObject.put("code", 11);
                jsonObject.put("msg", "�����ǽ�ʦ!");
            } else {
                jsonObject.put("code", 10);
                jsonObject.put("msg", "���ȵ�¼!");
            }
            return jsonObject;
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "��ʦ��ѯ��Ŀʧ��!");
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/teacher/deleteTest", method = RequestMethod.POST)
    public JSONObject teacherDeleteTest(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String classId = request.getParameter("class_id");
        String testId = request.getParameter("test_id");
        if (isInteger(classId) && isInteger(testId)) {
            int class_id = Integer.parseInt(classId);
            int test_id = Integer.parseInt(testId);
            if (request.getSession().getAttribute("teacher") != null) {
                Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
                int teacher_id = teacher.getTeacher_id();
                if (teacherService.getClass(class_id, teacher_id) == null) {
                    jsonObject.put("code", 1);
                    jsonObject.put("msg", "δ��ѯ���༶!");
                    return jsonObject;
                }
                try {
                    boolean s = teacherService.deleteTest(test_id, class_id);
                    if (s) {
                        jsonObject.put("code", 0);
                        jsonObject.put("msg", "ɾ�����Գɹ�!");
                        return jsonObject;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (request.getSession().getAttribute("user") != null) {
                    jsonObject.put("code", 11);
                    jsonObject.put("msg", "�����ǽ�ʦ!");
                } else {
                    jsonObject.put("code", 10);
                    jsonObject.put("msg", "���ȵ�¼!");
                }
                return jsonObject;
            }
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "ɾ������ʧ��!");
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/teacher/deleteClass", method = RequestMethod.POST)
    public JSONObject teacherDeleteClass(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String classId = request.getParameter("id");
        System.out.println(classId);
        if (request.getSession().getAttribute("teacher") != null) {
            if (isInteger(classId)) {
                int id = Integer.parseInt(classId);
                Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
                int teacher_id = teacher.getTeacher_id();
                if (teacherService.getClass(id, teacher_id) == null) {
                    jsonObject.put("code", 1);
                    jsonObject.put("msg", "δ��ѯ���༶!");
                    return jsonObject;
                }
                try {
                    boolean s = teacherService.deleteClass(id, teacher_id);
                    if (s) {
                        jsonObject.put("code", 0);
                        jsonObject.put("msg", "ɾ�����óɹ�!");
                        return jsonObject;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (request.getSession().getAttribute("user") != null) {
                jsonObject.put("code", 11);
                jsonObject.put("msg", "�����ǽ�ʦ!");
            } else {
                jsonObject.put("code", 10);
                jsonObject.put("msg", "���ȵ�¼!");
            }
            return jsonObject;
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "ɾ������ʧ��!");
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/teacher/deleteQuestion", method = RequestMethod.POST)
    public JSONObject teacherDeleteQuestion(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String questionId = request.getParameter("question_id");
        if (isInteger(questionId)) {
            int question_id = Integer.parseInt(questionId);
            if (request.getSession().getAttribute("teacher") != null) {
                Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
                int teacher_id = teacher.getTeacher_id();
                if (teacherService.getQuestion(question_id, teacher_id) == null) {
                    jsonObject.put("code", 1);
                    jsonObject.put("msg", "δ��ѯ������!");
                    return jsonObject;
                }
                try {
                    boolean s = teacherService.deleteQuestion(question_id, teacher_id);
                    if (s) {
                        jsonObject.put("code", 0);
                        jsonObject.put("msg", "ɾ������ɹ�!");
                        return jsonObject;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (request.getSession().getAttribute("user") != null) {
                    jsonObject.put("code", 11);
                    jsonObject.put("msg", "�����ǽ�ʦ!");
                } else {
                    jsonObject.put("code", 10);
                    jsonObject.put("msg", "���ȵ�¼!");
                }
                return jsonObject;
            }
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "ɾ������ʧ��!");
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/teacher/updateClass", method = RequestMethod.POST)
    public JSONObject teacherUpdateClass(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String name = request.getParameter("name");
        String classId = request.getParameter("id");
        String studentNum = request.getParameter("student_num");
        System.out.println(name + " " + classId + " " + studentNum);
        if (request.getSession().getAttribute("teacher") != null) {
            if (isInteger(classId) && isInteger(studentNum)) {
                int id = Integer.parseInt(classId);
                int student_num = Integer.parseInt(studentNum);
                Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
                int teacher_id = teacher.getTeacher_id();
                if (teacherService.getClass(id, teacher_id) == null) {
                    jsonObject.put("code", 1);
                    jsonObject.put("msg", "δ��ѯ���༶!");
                    return jsonObject;
                }
                try {
                    boolean s = teacherService.updateClass(id, teacher_id, name, student_num);
                    if (s) {
                        jsonObject.put("code", 0);
                        jsonObject.put("msg", "���¿��óɹ�!");
                        return jsonObject;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (request.getSession().getAttribute("user") != null) {
                jsonObject.put("code", 11);
                jsonObject.put("msg", "�����ǽ�ʦ!");
            } else {
                jsonObject.put("code", 10);
                jsonObject.put("msg", "���ȵ�¼!");
            }
            return jsonObject;
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "���¿���ʧ��!");
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/teacher/updateTest", method = RequestMethod.POST)
    public JSONObject teacherUpdateTest(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String name = request.getParameter("name");
        String classId = request.getParameter("class_id");
        String testId = request.getParameter("test_id");
        String studentNum = request.getParameter("student_num");
        String time = request.getParameter("duration");
        if (request.getSession().getAttribute("teacher") != null) {
            if (isInteger(classId) && isInteger(studentNum) && isInteger(testId) && isInteger(time)) {
                int class_id = Integer.parseInt(classId);
                int student_num = Integer.parseInt(studentNum);
                int test_id = Integer.parseInt(testId);
                int duration = Integer.parseInt(time);
                boolean status;
                status = request.getParameter("status").equals("true");
                Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
                int teacher_id = teacher.getTeacher_id();
                if (teacherService.getClass(class_id, teacher_id) == null) {
                    jsonObject.put("code", 1);
                    jsonObject.put("msg", "δ��ѯ���༶!");
                    return jsonObject;
                }
                try {
                    boolean stu = teacherService.updateTest(test_id, class_id, name, student_num, status, duration);
                    if (stu) {
                        jsonObject.put("code", 0);
                        jsonObject.put("msg", "���²��Գɹ�!");
                        return jsonObject;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (request.getSession().getAttribute("user") != null) {
                jsonObject.put("code", 11);
                jsonObject.put("msg", "�����ǽ�ʦ!");
            } else {
                jsonObject.put("code", 10);
                jsonObject.put("msg", "���ȵ�¼!");
            }
            return jsonObject;
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "���²���ʧ��!");
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/teacher/updateQuestion", method = RequestMethod.POST)
    public JSONObject teacherUpdateQuestion(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String questionId = request.getParameter("question_id");
        String question = request.getParameter("question");
        String a_option = request.getParameter("a_option");
        String b_option = request.getParameter("b_option");
        String c_oprion = request.getParameter("c_option");
        String d_option = request.getParameter("d_option");
        String e_option = request.getParameter("e_option");
        String f_option = request.getParameter("f_option");
        String answer = request.getParameter("answer");
        String t = request.getParameter("type");
        if (request.getSession().getAttribute("teacher") != null) {
            System.out.println("ok1");
            if (isInteger(t) && isInteger(questionId)) {
                System.out.println("ok2");
                int question_id = Integer.parseInt(questionId);
                int type = Integer.parseInt(t);
                Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
                int teacher_id = teacher.getTeacher_id();
                if (teacherService.getQuestion(question_id, teacher_id) == null) {
                    jsonObject.put("code", 1);
                    jsonObject.put("msg", "δ��ѯ������!");
                    return jsonObject;
                }
                try {
                    boolean stu = teacherService.updateQuestion(question_id, question, a_option, b_option, c_oprion, d_option, e_option, f_option, answer, type);
                    if (stu) {
                        jsonObject.put("code", 0);
                        jsonObject.put("msg", "��������ɹ�!");
                        return jsonObject;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (request.getSession().getAttribute("user") != null) {
                jsonObject.put("code", 11);
                jsonObject.put("msg", "�����ǽ�ʦ!");
            } else {
                jsonObject.put("code", 10);
                jsonObject.put("msg", "���ȵ�¼!");
            }
            return jsonObject;
        }
        jsonObject.put("code", 1);
        jsonObject.put("msg", "��������ʧ��!");
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

    @RequestMapping(value = "/teacher/class", method = {RequestMethod.GET, RequestMethod.POST})
    public String teacherClass() {
        return "/teacher/teacherClass";
    }

}
