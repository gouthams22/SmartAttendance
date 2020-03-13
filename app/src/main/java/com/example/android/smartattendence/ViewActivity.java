package com.example.android.smartattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    ArrayList<String> dateList = new ArrayList<>();
    ArrayList<String> attendanceList = new ArrayList<>();
    ArrayList<String> periodList = new ArrayList<>();
    EditText editSubject, editRollNo;
    Button button;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        progressBar = findViewById(R.id.progress_view);
        editRollNo = findViewById(R.id.edit_rollno);
        editSubject = findViewById(R.id.edit_subject);
        button = findViewById(R.id.button_view);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateList.clear();
                attendanceList.clear();
                periodList.clear();
                ListView listView = findViewById(R.id.list1);
                listView.setAdapter(new ViewCustomAdapter(dateList, periodList, attendanceList, ViewActivity.this, ViewActivity.this));
                editSubject.setEnabled(false);
                editRollNo.setEnabled(false);
                if (editSubject.getText().toString().trim().equals("")) {
                    editSubject.setError("Enter subject!");
                    editSubject.requestFocus();
                    editSubject.setEnabled(true);
                    editRollNo.setEnabled(true);
                } else if (editRollNo.getText().toString().trim().equals("")) {
                    editRollNo.setError("Enter Roll No.!");
                    editRollNo.requestFocus();
                    editSubject.setEnabled(true);
                    editRollNo.setEnabled(true);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("table");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String key = null;
                            for (DataSnapshot iSnapshot : dataSnapshot.child("class").getChildren()) {
                                if (iSnapshot.child("rollno").getValue(String.class).equals(editRollNo.getText().toString().trim())) {
                                    key = iSnapshot.getKey();
                                    break;
                                }
                            }
                            if (key == null) {
                                Toast.makeText(getApplicationContext(), "Student not found!", Toast.LENGTH_SHORT).show();
                                editRollNo.setEnabled(true);
                                editSubject.setEnabled(true);
                                progressBar.setVisibility(View.INVISIBLE);
                                return;
                            }
                            dateList.clear();
                            attendanceList.clear();
                            periodList.clear();
                            dateList.add("Date");
                            attendanceList.add("Attendence");
                            periodList.add("Period");
                            for (DataSnapshot iSnapshot : dataSnapshot.child("class").child(key).child("attendance").getChildren()) {
                                if (iSnapshot.child("subname").getValue(String.class).equals(editSubject.getText().toString().trim())) {
                                    Attendance attendance = iSnapshot.getValue(Attendance.class);
                                    dateList.add(attendance.getDate());
                                    periodList.add(attendance.getPeriod());
                                    attendanceList.add(attendance.getPresent().equals("true") ? "Present" : "Absent");
                                }
                            }
                            ListView listView = findViewById(R.id.list1);
                            listView.setAdapter(new ViewCustomAdapter(dateList, periodList, attendanceList, ViewActivity.this, ViewActivity.this));
                            editRollNo.setEnabled(true);
                            editSubject.setEnabled(true);
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        ListView listView = findViewById(R.id.list1);
        listView.setAdapter(new ViewCustomAdapter(dateList, periodList, attendanceList, ViewActivity.this, ViewActivity.this));
    }
}
