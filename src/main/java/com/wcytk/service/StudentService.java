package com.wcytk.service;

import com.wcytk.entity.Class;
import com.wcytk.entity.Question;
import com.wcytk.entity.Test;
import com.wcytk.entity.UserTest;

import java.util.List;

public interface StudentService {
    public boolean addClass(int student_id, int class_id, String create_time);

    public boolean addUserTest(int student_id, int test_id, int grade, String create_time);

    public List<Class> showAllClasses();

    public List<Test> showTests(int class_id);

    public Class getMyClass(int student_id, int class_id);

    public Class getClass(int class_id);

    public Test getTest(int test_id);

    public UserTest getMyTest(int student_id, int test_id);

    public Question getQuestion(int question_id);

    public List<Class> showMyClasses(int student_id);

    public List<Question> showQuestions(int test_id);

    public boolean addClassNum(int class_id);

    public boolean subClassNum(int class_id);

    public boolean dropClass(int student_id, int class_id);
}
