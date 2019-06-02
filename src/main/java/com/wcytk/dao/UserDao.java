package com.wcytk.dao;

import com.wcytk.entity.Teacher;
import com.wcytk.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    User SelectStudent(@Param("student_id") int student_id, @Param("passwd") String passwd);

    Teacher selectTeacher(@Param("teacher_id") int teacher_id, @Param("passwd") String passwd);

    Teacher selectTeacherId(@Param("teacher_id") int teacher_id);

    User selectStudentId(@Param("student_id") int student_id);

    boolean AddUser(@Param("name") String name, @Param("passwd") String passwd, @Param("student_id") int student_id);

    boolean addTeacher(@Param("name") String name, @Param("passwd") String passwd, @Param("teacher_id") int teacher_id);
}
