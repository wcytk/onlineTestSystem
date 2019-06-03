package com.wcytk.service;

import com.wcytk.entity.Class;
import com.wcytk.entity.Question;
import com.wcytk.entity.Test;

import java.util.List;

public interface TeacherService {
    public boolean addClass(int teacher_id, String name, int student_num, int current_num, String create_time);

    public List<Class> showClasses(int teacher_id);

    public Class getClass(int id, int teacher_id);

    public List<Test> getQuestionTestId(int question_id);

    public boolean deleteClass(int id, int teacher_id);

    public boolean updateClass(int id, int teacher_id, String name, int student_num);

    public boolean addTest(int class_id, String name, int student_num, int current_num, String create_time, boolean status, int duration, int full_grade);

    public List<Test> showTests(int class_id);

    public Test getTest(int id, int class_id);

    public boolean deleteTest(int id, int class_id);

    public boolean updateTest(int id, int class_id, String name, int student_num, boolean status, int duration);

    public boolean addTestFullGrade(int id, int question_grade);

    public boolean subTestFullGrade(int id, int question_grade);

    public boolean addQuestion(int teacher_id, String question, String a_option, String b_option, String c_option, String d_option, String e_option, String f_option, String answer, int type, String create_time, int grade);

    public boolean addQuestionToTest(int question_id, int test_id);

    public List<Test> showQuestions(int test_id);

    public List<Question> showTeacherQuestion(int teacher_id);

    public Question getQuestion(int id, int teacher_id);

    public boolean deleteQuestion(int id, int teacher_id);

    public boolean deleteQuestionFromTest(int question_id, int test_id);

    public boolean updateQuestion(int id, String question, String a_option, String b_option, String c_option, String d_option, String e_option, String f_option, String answer, int type, int grade);

}
