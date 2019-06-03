package com.wcytk.entity;

public class Answer {
    private int question_id;

    private String answer;

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getQuestion_id() {
        return this.question_id;
    }

    public String getAnswer() {
        return this.answer;
    }
}
