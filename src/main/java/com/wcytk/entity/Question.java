package com.wcytk.entity;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;

import java.util.List;

public class Question {
    private int id;

    private int teacher_id;

    private String question;

    private String a_option;

    private String b_option;

    private String c_option;

    private String d_option;

    private String e_option;

    private String f_option;

    private String answer;

    private int type;

    private String create_time;

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setA_option(String a_option) {
        this.a_option = a_option;
    }

    public void setB_option(String b_option) {
        this.b_option = b_option;
    }

    public void setC_option(String c_option) {
        this.c_option = c_option;
    }

    public void setD_option(String d_option) {
        this.d_option = d_option;
    }

    public void setE_option(String e_option) {
        this.e_option = e_option;
    }

    public void setF_option(String f_option) {
        this.f_option = f_option;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return this.id;
    }

    public int getTeacher_id() {
        return this.teacher_id;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getA_option() {
        return this.a_option;
    }

    public String getB_option() {
        return this.b_option;
    }

    public String getC_option() {
        return this.c_option;
    }

    public String getD_option() {
        return this.d_option;
    }

    public String getE_option() {
        return this.e_option;
    }

    public String getF_option() {
        return this.f_option;
    }

    public String getAnswer() {
        return this.answer;
    }

    public int getType() {
        return this.type;
    }

    public String getCreate_time() {
        return this.create_time;
    }
}
