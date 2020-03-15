package com.example.android.smartattendence;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class AddCustomClass extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public String name, rollno, className;
    public EditText ename, roll;
    public Button yes, no;

    public AddCustomClass(Activity a, String className) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.name=name;
        this.rollno=rollno;
        this.className = className;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        ename = findViewById(R.id.edit_name);
        roll = findViewById(R.id.edit_rollno);
        yes.setText("Add");
        no.setText("Cancel");
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:

                dismiss();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
