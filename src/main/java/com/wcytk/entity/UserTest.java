package com.wcytk.entity;

public class UserTest {
    private int id;

    private int student_id;

    private int test_id;

    private int grade;

    private String create_time;

    public void setId(int id) {
        this.id = id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getId() {
        return this.id;
    }

    public int getStudent_id() {
        return this.student_id;
    }

    public int getTest_id() {
        return this.test_id;
    }

    public int getGrade() {
        return this.grade;
    }

    public String getCreate_time() {
        return this.create_time;
    }
}
