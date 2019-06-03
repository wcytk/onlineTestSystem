package com.wcytk.service.impl;

import com.wcytk.dao.TeacherDao;
import com.wcytk.entity.Question;
import com.wcytk.entity.Test;
import com.wcytk.service.TeacherService;
import com.wcytk.entity.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherDao teacherDao;

    @Override
    public boolean addClass(int teacher_id, String name, int student_num, int current_num, String create_time) {
        return teacherDao.addClass(teacher_id, name, student_num, current_num, create_time);
    }

    @Override
    public List<Class> showClasses(int teacher_id) {
        return teacherDao.showClasses(teacher_id);
    }

    @Override
    public Class getClass(int id, int teacher_id) {
        return teacherDao.getClass(id, teacher_id);
    }

    @Override
    public List<Test> getQuestionTestId(int question_id) {
        return teacherDao.getQuestionTestId(question_id);
    }

    @Override
    public boolean deleteClass(int id, int teacher_id) {
        return teacherDao.deleteClass(id, teacher_id);
    }

    @Override
    public boolean updateClass(int id, int teacher_id, String name, int student_num) {
        return teacherDao.updateClass(id, teacher_id, name, student_num);
    }

    @Override
    public boolean addTest(int class_id, String name, int student_num, int current_num, String create_time, boolean status, int duration, int question_grade) {
        return teacherDao.addTest(class_id, name, student_num, current_num, create_time, status, duration, question_grade);
    }

    @Override
    public List<Test> showTests(int class_id) {
        return teacherDao.showTests(class_id);
    }

    @Override
    public Test getTest(int id, int class_id) {
        return teacherDao.getTest(id, class_id);
    }

    @Override
    public boolean deleteTest(int id, int class_id) {
        return teacherDao.deleteTest(id, class_id);
    }

    @Override
    public boolean updateTest(int id, int class_id, String name, int student_num, boolean status, int duration) {
        return teacherDao.updateTest(id, class_id, name, student_num, status, duration);
    }

    @Override
    public boolean addTestFullGrade(int id, int question_grade) {
        return teacherDao.addTestFullGrade(id, question_grade);
    }

    @Override
    public boolean subTestFullGrade(int id, int question_grade) {
        return teacherDao.subTestFullGrade(id, question_grade);
    }

    @Override
    public boolean addQuestion(int teacher_id, String question, String a_option, String b_option, String c_option, String d_option, String e_option, String f_option, String answer, int type, String create_time, int grade) {
        return teacherDao.addQuestion(teacher_id, question, a_option, b_option, c_option, d_option, e_option, f_option, answer, type, create_time, grade);
    }

    @Override
    public boolean addQuestionToTest(int question_id, int test_id) {
        return teacherDao.addQuestionToTest(question_id, test_id);
    }

    @Override
    public List<Test> showQuestions(int test_id) {
        return teacherDao.showQuestions(test_id);
    }

    @Override
    public List<Question> showTeacherQuestion(int teacher_id) {
        return teacherDao.showTeacherQuestion(teacher_id);
    }

    @Override
    public Question getQuestion(int id, int teacher_id) {
        return teacherDao.getQuestion(id, teacher_id);
    }

    @Override
    public boolean deleteQuestion(int id, int teacher_id) {
        return teacherDao.deleteQuestion(id, teacher_id);
    }

    @Override
    public boolean deleteQuestionFromTest(int question_id, int test_id) {
        return teacherDao.deleteQuestionFromTest(question_id, test_id);
    }

    @Override
    public boolean updateQuestion(int id, String question, String a_option, String b_option, String c_option, String d_option, String e_option, String f_option, String answer, int type, int grade) {
        return teacherDao.updateQuestion(id, question, a_option, b_option, c_option, d_option, e_option, f_option, answer, type, grade);
    }

}
