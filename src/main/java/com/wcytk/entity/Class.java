package com.wcytk.entity;

public class Class {
    private int id;

    private int teacher_id;

    private String name;

    private int current_num;

    private int student_num;

    private String create_time;

    public void setId(int id) {
        this.id = id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public void setCurrent_num(int current_num) {
        this.current_num = current_num;
    }

    public void setStudent_num(int student_num) {
        this.student_num = student_num;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getId() {
        return this.id;
    }

    public int getTeacher_id() {
        return this.teacher_id;
    }

    public int getCurrent_num() {
        return this.current_num;
    }

    public int getStudent_num() {
        return this.student_num;
    }

    public String getName() {
        return this.name;
    }

    public String getCreate_time() {
        return this.create_time;
    }
}
