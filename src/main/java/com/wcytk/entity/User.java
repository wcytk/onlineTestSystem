package com.wcytk.entity;

public class User {
    private String name;

    private int student_id;

    private int id;

    private String passwd;

    public void setName(String name){
        this.name = name;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public void setId(int id) { this.id = id; }

    public void setPasswd(String passwd) { this.passwd = passwd; }

    public String getName(){
        return this.name;
    }

    public int getId() { return this.id; }

    public String getPasswd() { return this.passwd; }

    public int getStudent_id() {
        return this.student_id;
    }

}
