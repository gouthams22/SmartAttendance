package com.example.android.smartattendence;

public class ClassChild {
    String className;
    String name;
    Attendance[] attendance;

    public ClassChild() {
    }

    public ClassChild(String className, String name) {
        this.className = className;
        this.name = name;
    }

    public ClassChild(String className, String name, Attendance[] attendance) {
        this.className = className;
        this.name = name;
        this.attendance = attendance;
    }

    public Attendance[] getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance[] attendance) {
        this.attendance = attendance;
    }

    public String getClassName() {
        return className;
    }

    public String getStudentName() {
        return name;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setStudentName(String name) {
        this.name = name;
    }
}
