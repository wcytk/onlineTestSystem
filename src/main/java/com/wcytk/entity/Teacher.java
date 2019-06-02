package com.wcytk.entity;

public class Teacher {
    private String name;

    private int teacher_id;

    private int id;

    private String passwd;

    public void setName(String name){
        this.name = name;
    }

    public void setId(int id) { this.id = id; }

    public void setPasswd(String passwd) { this.passwd = passwd; }

    public void setTeacher_id(int teacher_id){
        this.teacher_id = teacher_id;
    }

    public String getName(){
        return this.name;
    }

    public int getId() { return this.id; }

    public String getPasswd() { return this.passwd; }

    public int getTeacher_id(){
        return this.teacher_id;
    }
}
