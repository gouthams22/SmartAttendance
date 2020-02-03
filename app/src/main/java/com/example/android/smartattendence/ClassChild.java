package com.example.android.smartattendence;

public class ClassChild {
    String className;
    String studentName;

    public ClassChild() {
    }

    public ClassChild(String className, String studentName) {
        this.className = className;
        this.studentName = studentName;
    }

    public String getClassName() {
        return className;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
