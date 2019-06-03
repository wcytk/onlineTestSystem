package com.wcytk.service.impl;

import com.wcytk.dao.StudentDao;
import com.wcytk.entity.Class;
import com.wcytk.entity.Question;
import com.wcytk.entity.Test;
import com.wcytk.entity.UserTest;
import com.wcytk.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao studentDao;

    @Override
    public boolean addClass(int student_id, int class_id, String create_time) {
        return studentDao.addClass(student_id, class_id, create_time);
    }

    @Override
    public boolean addUserTest(int student_id, int test_id, int grade, String create_time) {
        return studentDao.addUserTest(student_id, test_id, grade, create_time);
    }

    @Override
    public List<Class> showAllClasses() {
        return studentDao.showAllClasses();
    }

    @Override
    public List<Test> showTests(int class_id) {
        return studentDao.showTests(class_id);
    }

    @Override
    public Class getMyClass(int student_id, int class_id) {
        return studentDao.getMyClass(student_id, class_id);
    }

    @Override
    public Class getClass(int class_id) {
        return studentDao.getClass(class_id);
    }

    @Override
    public Test getTest(int test_id) {
        return studentDao.getTest(test_id);
    }

    @Override
    public UserTest getMyTest(int student_id, int test_id) {
        return studentDao.getMyTest(student_id, test_id);
    }

    @Override
    public Question getQuestion(int question_id) {
        return studentDao.getQuestion(question_id);
    }

    @Override
    public List<Class> showMyClasses(int student_id) {
        return studentDao.showMyClasses(student_id);
    }

    @Override
    public List<Question> showQuestions(int test_id) {
        return studentDao.showQuestions(test_id);
    }

    @Override
    public boolean addClassNum(int class_id) {
        return studentDao.addClassNum(class_id);
    }

    @Override
    public boolean subClassNum(int class_id) {
        return studentDao.subClassNum(class_id);
    }

    @Override
    public boolean dropClass(int student_id, int class_id) {
        return studentDao.dropClass(student_id, class_id);
    }

}
