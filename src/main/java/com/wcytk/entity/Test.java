package com.wcytk.entity;

import java.util.List;

public class Test {
    private int id;

    private int class_id;

    private int student_num;

    private int current_num;

    private String name;

    private String create_time;

    private boolean status;

    private int duration;

    private Question question;

    public void setClass_id(int class_id) {
        this.class_id = class_id;
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

    public void setCurrent_num(int current_num) {
        this.current_num = current_num;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getId() {
        return this.id;
    }

    public int getClass_id() {
        return this.class_id;
    }

    public int getStudent_num() {
        return this.student_num;
    }

    public int getCurrent_num() {
        return this.current_num;
    }

    public String getName() {
        return this.name;
    }

    public String getCreate_time() {
        return this.create_time;
    }

    public boolean getStatus() {
        return this.status;
    }

    public int getDuration() {
        return this.duration;
    }

    public Question getQuestion() {
        return this.question;
    }
}
