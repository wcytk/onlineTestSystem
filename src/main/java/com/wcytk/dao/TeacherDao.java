package com.wcytk.dao;

import com.wcytk.entity.Class;
import com.wcytk.entity.Question;
import com.wcytk.entity.Test;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherDao {
    boolean addClass(@Param("teacher_id") int teacher_id, @Param("name") String name, @Param("student_num") int student_num, @Param("current_num") int current_num, @Param("create_time") String create_time);

    List<Class> showClasses(@Param("teacher_id") int teacher_id);

    Class getClass(@Param("id") int id, @Param("teacher_id") int teacher_id);

    boolean deleteClass(@Param("id") int id, @Param("teacher_id") int teacher_id);

    boolean updateClass(@Param("id") int id, @Param("teacher_id") int teacher_id, @Param("name") String name, @Param("student_num") int student_num);

    boolean addTest(@Param("class_id") int class_id, @Param("name") String name, @Param("student_num") int student_num, @Param("current_num") int current_num, @Param("create_time") String create_time, @Param("status") boolean status, @Param("duration") int duration);

    List<Test> showTests(@Param("class_id") int class_id);

    Test getTest(@Param("id") int id, @Param("class_id") int class_id);

    boolean deleteTest(@Param("id") int id, @Param("class_id") int class_id);

    boolean updateTest(@Param("id") int id, @Param("class_id") int class_id, @Param("name") String name, @Param("student_num") int student_num, @Param("status") boolean status, @Param("duration") int duration);

    boolean addQuestion(@Param("teacher_id") int teacher_id, @Param("question") String question, @Param("a_option") String a_option, @Param("b_option") String b_option, @Param("c_option") String c_option, @Param("d_option") String d_option, @Param("e_option") String e_option, @Param("f_option") String f_option, @Param("answer") String answer, @Param("type") int type, @Param("create_time") String create_time);

    boolean addQuestionToTest(@Param("question_id") int question_id, @Param("test_id") int test_id);

    List<Test> showQuestions(@Param("test_id") int test_id);

    List<Question> showTeacherQuestion(@Param("teacher_id") int teacher_id);

    Question getQuestion(@Param("id") int id, @Param("teacher_id") int teacher_id);

    boolean deleteQuestion(@Param("id") int id, @Param("teacher_id") int teacher_id);

    boolean updateQuestion(@Param("id") int id, @Param("question") String question, @Param("a_option") String a_option, @Param("b_option") String b_option, @Param("c_option") String c_option, @Param("d_option") String d_option, @Param("e_option") String e_option, @Param("f_option") String f_option, @Param("answer") String answer, @Param("type") int type);
}
