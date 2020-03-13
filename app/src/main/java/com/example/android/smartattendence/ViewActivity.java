package com.example.android.smartattendence;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    ArrayList<String> dateList = new ArrayList<>();
    ArrayList<String> attendanceList = new ArrayList<>();
    EditText editSubject, editRollno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        editRollno=findViewById(R.id.edit_rollno);
        editSubject=findViewById(R.id.edit_subject);
        dateList.add("Date");
        dateList.add("2");
        dateList.add("3");
        attendanceList.add("Attendence");
        attendanceList.add("2");
        attendanceList.add("3");
        ListView listView = findViewById(R.id.list1);
        listView.setAdapter(new ViewCustomAdapter(dateList, attendanceList,dateList, ViewActivity.this, ViewActivity.this));
    }
}
