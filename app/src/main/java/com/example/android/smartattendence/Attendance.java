package com.example.android.smartattendence;

public class Attendance {
    String date;
    String period;
    String present;

    public Attendance() {
    }

    public Attendance(String date, String period, String present) {
        this.date = date;
        this.period = period;
        this.present = present;
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
}
