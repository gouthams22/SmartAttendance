package com.example.android.smartattendence;

public class ClassParent {
    ClassChild classChild;

    public ClassParent() {

    }

    public ClassParent(ClassChild classChild) {
        this.classChild = classChild;
    }

    public ClassChild getClassChild() {
        return classChild;
    }

    public void setClassChild(ClassChild classChild) {
        this.classChild = classChild;
    }
}
