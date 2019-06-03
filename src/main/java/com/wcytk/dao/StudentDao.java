package com.wcytk.dao;

import com.wcytk.entity.Class;
import com.wcytk.entity.Question;
import com.wcytk.entity.Test;
import com.wcytk.entity.UserTest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentDao {
    boolean addClass(@Param("student_id") int student_id, @Param("class_id") int class_id, @Param("create_time") String create_time);

    boolean addUserTest(@Param("student_id") int student_id, @Param("test_id") int test_id, @Param("grade") int grade, @Param("create_time") String create_time);

    List<Class> showAllClasses();

    List<Test> showTests(@Param("class_id") int class_id);

    Class getMyClass(@Param("student_id") int student_id, @Param("class_id") int class_id);

    Class getClass(@Param("class_id") int class_id);

    Test getTest(@Param("test_id") int test_id);

    UserTest getMyTest(@Param("student_id") int student_id, @Param("test_id") int test_id);

    Question getQuestion(@Param("question_id") int question_id);

    List<Class> showMyClasses(@Param("student_id") int student_id);

    List<Question> showQuestions(@Param("test_id") int test_id);

    boolean addClassNum(@Param("class_id") int class_id);

    boolean subClassNum(@Param("class_id") int class_id);

    boolean dropClass(@Param("student_id") int student_id, @Param("class_id") int class_id);
}
