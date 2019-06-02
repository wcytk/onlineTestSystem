package com.wcytk.service;

import com.wcytk.entity.Teacher;
import com.wcytk.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {
    public User SelectStudent(int student_id, String passwd);

    public Teacher selectTeacher(int teacher_id, String passwd);

    public Teacher selectTeacherId(int teacher_id);

    public User selectStudentId(int student_id);

    public boolean AddUser(String name, String passwd, int student_id);

    public boolean addTeacher(String name, String passwd, int teacher_id);
}
