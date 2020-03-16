package com.example.android.smartattendence;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomDialogClass extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public String name, rollno, studentId;
    EditText sname, roll;
    public Button yes, no;

    public CustomDialogClass(Activity a, String name, String rollno, String studentId) {
        super(a);
        this.c = a;
        this.studentId = studentId;
        this.name = name;
        this.rollno = rollno;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        sname = findViewById(R.id.edit_name);
        roll = findViewById(R.id.edit_rollno);
        sname.setText(name);
        roll.setText(rollno);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("table");
                if (sname.getText().toString().trim().equals("")) {
                    sname.setError("Field Blank");
                    sname.requestFocus();
                } else if (roll.getText().toString().trim().equals("")) {
                    roll.setError("Field Blank");
                    roll.requestFocus();
                } else {
                    databaseReference.child("class").child(studentId).child("name").setValue(sname.getText().toString().trim());
                    databaseReference.child("class").child(studentId).child("rollno").setValue(roll.getText().toString().trim());
                }
                dismiss();
//                    c.finish();
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
