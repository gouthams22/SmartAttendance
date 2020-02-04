package com.example.android.smartattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashSet;

public class MainMark extends AppCompatActivity implements Connector {
    FirebaseUser firebaseUser;
    //    String classes[] = {"Class A", "Class B", "Class C"};
    //    ArrayList<ClassChild> classChildren = new ArrayList<>();
    ArrayList<String> className = new ArrayList<>();
    ArrayList<String> studentName = new ArrayList<>();
    ArrayList<String> studentID = new ArrayList<>();
    ArrayList<String> attendanceList = new ArrayList<>();
    //    ArrayList<String> list = new ArrayList<String>(Arrays.asList("111,222".split(",")));//,333,444,555,666
//    ArrayList<String> list1 = new ArrayList<String>(Arrays.asList("true,false".split(",")));//true,false,true,false,
    DatabaseReference databaseReference;
    Button markButton;
    ProgressBar progressBar, submitProgress;
    TextView textView;
    boolean duplicate;
    private String clickedClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mark);
        textView = findViewById(R.id.date_text);
        textView.setText("Date : " + getCurrentDate());
        textView = findViewById(R.id.period_text);
        textView.setText("Period : " + (getPeriod(Calendar.getInstance().get(Calendar.HOUR)) == -1 ? "Outside class hour" : getPeriod(Calendar.getInstance().get(Calendar.HOUR))));
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        submitProgress = findViewById(R.id.submit_progress);
        markButton = findViewById(R.id.mark_button);
        markButton.setVisibility(Button.GONE);
        progressBar.setVisibility(ProgressBar.VISIBLE);
//        childArrayList = new ArrayList<>();
        //Firebase Database
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("table");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                markButton.setVisibility(View.VISIBLE);
                progressBar.setVisibility(ProgressBar.GONE);
                className.clear();
                for (DataSnapshot classSnapshot : dataSnapshot.child("class").getChildren()) {
//                    ClassChild classChild = new ClassChild();
//                    classChild = classSnapshot.getValue(ClassChild.class);
//                    className.add(classChild.className);
//                    classChildren.add(classSnapshot.getValue(ClassChild.class));
                    if (!className.contains(classSnapshot.child("classname").getValue(String.class))) {
                        className.add(classSnapshot.child("classname").getValue(String.class));
                        String s = classSnapshot.getKey();
//                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    }
                }
                Spinner spinner = (Spinner) findViewById(R.id.spinner1);
                ArrayAdapter adap1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_class, className);
                adap1.setDropDownViewResource(R.layout.spinner_dropdown);
                spinner.setAdapter(adap1);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            clickedClass = ((TextView) view).getText().toString();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        FirebaseDatabase.getInstance().getReference("table").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                studentName.clear();
                                studentID.clear();
                                for (DataSnapshot classSnapshot : dataSnapshot.child("class").getChildren()) {
//                    ClassChild classChild = new ClassChild();
//                    classChild = classSnapshot.getValue(ClassChild.class);
//                    className.add(classChild.className);
//                    classChildren.add(classSnapshot.getValue(ClassChild.class));
                                    if (classSnapshot.child("classname").getValue(String.class).equals(clickedClass)) {
                                        studentName.add(classSnapshot.child("name").getValue(String.class));
                                        studentID.add(classSnapshot.getKey());
                                    }
//                                    attendanceList.add(classSnapshot.child("attendance").child("present").getValue(String.class));
                                }
                                ListView listView = (ListView) findViewById(R.id.list1);
                                attendanceList = new ArrayList<String>();
                                for (int i = 0; i < studentName.size(); i++)
                                    attendanceList.add("false");
                                listView.setAdapter(new MyCustomAdapter(studentName, attendanceList, MainMark.this, MainMark.this));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Reference Error", Toast.LENGTH_LONG).show();
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot iDataSnapshot : dataSnapshot.child("class").getChildren()) {
                    for (DataSnapshot jDataSnapshot : iDataSnapshot.child("attendance").getChildren()) {
                        String tempDate = "" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR);
                        if (jDataSnapshot.child("date").getValue(String.class) != null)
                            if (jDataSnapshot.child("date").getValue(String.class).equals(tempDate)) {
                                if (jDataSnapshot.child("period").getValue(String.class) != null)
                                    if (jDataSnapshot.child("period").getValue(String.class).equals("" + getPeriod(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)))) {
                                        duplicate = true;
                                        return;
                                    }
                            }
                    }
                }
                duplicate = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        Toast.makeText(MainMark.this, list1.get(1), Toast.LENGTH_LONG).show();
        ListView listView = (ListView) findViewById

                (R.id.list1);
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
//        ArrayAdapter adap1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_class, classes);
        ArrayAdapter adap1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_class, className);
        adap1.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adap1);
//        listView.setAdapter(new MyCustomAdapter(list, list1, MainMark.this, MainMark.this));
        markButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitProgress.setVisibility(ProgressBar.VISIBLE);
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DATE);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int period = getPeriod(hour);
                if (period == -1) {
                    Toast.makeText(getApplicationContext(), "Are you marking attendance at the right time?", Toast.LENGTH_LONG).show();
                    submitProgress.setVisibility(ProgressBar.GONE);
                    return;
                }
                if (duplicate) {
                    Toast.makeText(getApplicationContext(), "Attendance already entered! Try updating", Toast.LENGTH_LONG).show();
                    submitProgress.setVisibility(ProgressBar.GONE);
                } else {
                    for (int i = 0; i < attendanceList.size(); i++) {
                        String tempDate = "" + day + "/" + month + "/" + year;
                        Attendance attendance = new Attendance(tempDate, period + "", attendanceList.get(i));
                        String tempID = databaseReference.child("class").child(studentID.get(i)).child("attendance").push().getKey();
                        databaseReference.child("class").child(studentID.get(i)).child("attendance").child(tempID).setValue(attendance);
//                        String temps = studentID.get(i);
//                        Toast.makeText(getApplicationContext(), temps, Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getApplicationContext(), "Marked attendance successfully!", Toast.LENGTH_LONG).show();
                    submitProgress.setVisibility(ProgressBar.GONE);
                }

            }
        });
    }

    @Override
    public void onCheckedBox(String s, int pos, String t) {
        int position = studentName.indexOf(s);
//        Toast.makeText(MainMark.this, attendanceList.get(position), Toast.LENGTH_LONG).show();
//        Toast.makeText(MainMark.this, s + pos, Toast.LENGTH_LONG).show();
        attendanceList.set(studentName.indexOf(s), t);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            finish();
    }

    public int getPeriod(int hour) {
        if (hour >= 9 && hour <= 12) return hour - 8;
        else if (hour >= 14 && hour <= 15) return hour - 9;
        else return -1;
    }

    public String getCurrentDate() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR);
    }


}