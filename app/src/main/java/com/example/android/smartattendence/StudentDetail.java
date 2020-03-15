package com.example.android.smartattendence;

public class StudentDetail {
    String classname;
    String name;
    String rollno;

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public StudentDetail(String classname, String name, String rollno) {
        this.classname = classname;
        this.name = name;
        this.rollno = rollno;
    }

    public StudentDetail() {
    }
}
