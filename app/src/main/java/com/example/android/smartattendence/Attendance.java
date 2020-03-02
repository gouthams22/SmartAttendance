package com.example.android.smartattendence;

public class Attendance {
    String date;
    String period;
    String present;
    String tid;
    String subname;

    public Attendance() {
    }

    public Attendance(String date, String period, String present, String tid, String subname) {
        this.date = date;
        this.period = period;
        this.present = present;
        this.tid = tid;
        this.subname = subname;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
