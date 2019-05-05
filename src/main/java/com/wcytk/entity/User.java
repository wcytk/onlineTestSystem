package com.wcytk.entity;

public class User {
    private String name;

    private int teacher;

    private int id;

    private String passwd;

    private int grade;

    public void setName(String name){
        this.name = name;
    }

    public void setId(int id) { this.id = id; }

    public void setPasswd(String passwd) { this.passwd = passwd; }

    public void setTeacher(int teacher){
        this.teacher = teacher;
    }

    public void setGrade(int teacher){
        this.grade = grade;
    }

    public String getName(){
        return this.name;
    }

    public int getId() { return this.id; }

    public String getPasswd() { return this.passwd; }

    public int getTeacher(){
        return this.teacher;
    }

    public int getGrade(){
        return this.grade;
    }
}
