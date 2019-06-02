package com.wcytk.service.impl;

import com.wcytk.dao.UserDao;
import com.wcytk.entity.Teacher;
import com.wcytk.entity.User;
import com.wcytk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User SelectStudent(int student_id, String passwd) {
        return userDao.SelectStudent(student_id, passwd);
    }

    @Override
    public Teacher selectTeacher(int teacher_id, String passwd) {
        return userDao.selectTeacher(teacher_id, passwd);
    }

    @Override
    public Teacher selectTeacherId(int teacher_id) {
        return userDao.selectTeacherId(teacher_id);
    }

    @Override
    public User selectStudentId(int student_id) {
        return userDao.selectStudentId(student_id);
    }

    @Override
    public boolean AddUser(String name, String passwd, int student_id) { return userDao.AddUser(name, passwd, student_id); }

    @Override
    public boolean addTeacher(String name, String passwd, int teacher_id) {
        return userDao.addTeacher(name, passwd, teacher_id);
    }
}
