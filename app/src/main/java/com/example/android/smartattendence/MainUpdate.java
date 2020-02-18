package com.example.android.smartattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MainUpdate extends AppCompatActivity implements Connector {
    ArrayList<String> className = new ArrayList<>();
    ArrayList<String> studentName = new ArrayList<>();
    ArrayList<String> attendanceList = new ArrayList<>();
    ArrayList<String> studentID = new ArrayList<>();
    ArrayList<String> studentKey = new ArrayList<>();
    ArrayList<String> attendanceKey = new ArrayList<>();
    String classes[] = {"Class A", "Class B", "Class C"};
    ArrayList<String> list = new ArrayList<String>(Arrays.asList("111,222,333,444,555,666".split(",")));
    ArrayList<String> list1 = new ArrayList<String>(Arrays.asList("false,true,false,true,false,false".split(",")));
    EditText editDate, editPeriod;
    Button button;
    ProgressBar progressDetail, progressUpdate;
    ImageButton dateImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_update);
        editDate = findViewById(R.id.edit_date);
        editPeriod = findViewById(R.id.edit_period);
        progressDetail = findViewById(R.id.progress_detail);
        progressUpdate = findViewById(R.id.update_progress);
        dateImageButton = findViewById(R.id.date_picker);

        ListView listView = (ListView) findViewById(R.id.list1);
        button = findViewById(R.id.button_detail);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDetail.setVisibility(View.VISIBLE);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                // Hide the soft keyboard
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                if (editDate.getText().toString().trim().equals("")) {
                    progressDetail.setVisibility(View.INVISIBLE);
                    editDate.setError("Enter the Date");
                    editDate.requestFocus();
                    return;
                }
                if (!editDate.getText().toString().trim().equals("")) {
                    editDate.setError(null);
                    editDate.clearFocus();
                }
                if (editPeriod.getText().toString().trim().equals("")) {
                    progressDetail.setVisibility(View.INVISIBLE);
                    editPeriod.setError("Enter the period");
                    editPeriod.requestFocus();
                    return;
                }
                Button tempButton = findViewById(R.id.update_button);
                tempButton.setEnabled(true);
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("table");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean check = false;
                        className.clear();
                        for (DataSnapshot i : dataSnapshot.child("class").getChildren()) {
                            for (DataSnapshot j : i.child("attendance").getChildren()) {
                                if (j.child("date").getValue(String.class) != null)
                                    if (j.child("date").getValue(String.class).equals(editDate.getText().toString().trim())) {
                                        if (j.child("period").getValue(String.class).equals(editPeriod.getText().toString().trim())) {
                                            if (!check) {
                                                check = true;
                                                if (!className.contains(i.child("classname").getValue(String.class))) {
                                                    className.add(i.child("classname").getValue(String.class));
                                                }
                                            }
                                        }
                                    }
                            }
                        }
                        if (check) {
                            progressDetail.setVisibility(View.INVISIBLE);
                            studentName.clear();
                            attendanceList.clear();
                            studentKey.clear();
                            attendanceKey.clear();
//                                    className.get(0);
                            for (DataSnapshot iSnapshot : dataSnapshot.child("class").getChildren()) {
                                if (iSnapshot.child("classname").getValue(String.class) != null)
                                    if (iSnapshot.child("classname").getValue(String.class).equals(className.get(0))) {
                                        for (DataSnapshot jSnapshot : iSnapshot.child("attendance").getChildren()) {
                                            if (jSnapshot.child("date").getValue(String.class) != null)
                                                if (jSnapshot.child("date").getValue(String.class).equals(editDate.getText().toString())) {
                                                    if (jSnapshot.child("period").getValue(String.class).equals(editPeriod.getText().toString())) {
                                                        studentKey.add(iSnapshot.getKey());
                                                        attendanceKey.add(jSnapshot.getKey());
                                                        studentName.add(iSnapshot.child("name").getValue(String.class));
                                                        attendanceList.add(jSnapshot.child("present").getValue(String.class));
                                                    }
                                                }
                                        }
                                    }
                            }
                            ListView listView = (ListView) findViewById(R.id.list1);
                            listView.setAdapter(new MyCustomAdapter(studentName, attendanceList, MainUpdate.this, MainUpdate.this));
                        } else {
                            Toast.makeText(getApplicationContext(), "Wrong details", Toast.LENGTH_LONG).show();
                            progressDetail.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Can't access Database", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        dateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainUpdate.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        editDate.clearFocus();
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        button = findViewById(R.id.update_button);
        button.setEnabled(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressUpdate.setVisibility(View.VISIBLE);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("table");
                for (int i = 0; i < studentName.size(); i++) {
                    Attendance attendance = new Attendance(editDate.getText().toString().trim(), editPeriod.getText().toString().trim(), attendanceList.get(i));
                    databaseReference.child("class").child(studentKey.get(i)).child("attendance").child(attendanceKey.get(i)).setValue(attendance).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                progressUpdate.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onCheckedBox(String s, int pos, String t) {
//        Toast.makeText(MainUpdate.this, s + pos, Toast.LENGTH_LONG).show();
//        attendanceList.set(studentName.indexOf(s), t);
        attendanceList.set(studentName.indexOf(s), t);
    }
}