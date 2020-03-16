package com.example.android.smartattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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
import java.util.Calendar;
import java.util.Collections;

public class MainMark extends AppCompatActivity implements Connector {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ArrayList<String> className = new ArrayList<>();
    ArrayList<String> studentRoll = new ArrayList<>();
    ArrayList<String> studentID = new ArrayList<>();
    ArrayList<String> attendanceList = new ArrayList<>();
    ArrayList<String> subjectList = new ArrayList<>();
    DatabaseReference databaseReference;
    Button markButton;
    ProgressBar progressBar, submitProgress;
    TextView textView;
    LinearLayout beginLayout, endLayout;
    boolean duplicate;
    private String clickedClass, clickedSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mark);
        beginLayout = findViewById(R.id.layout_begin);
        endLayout = findViewById(R.id.layout_end);
        beginLayout.setVisibility(View.INVISIBLE);
        endLayout.setVisibility(View.INVISIBLE);
        textView = findViewById(R.id.date_text);
        textView.setText("Date : " + getCurrentDate());
        textView = findViewById(R.id.period_text);
        textView.setText("Period : " + (getPeriod(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) == -1 ? "Outside class hour" : getPeriod(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))));
        progressBar = (ProgressBar) findViewById(R.id.progress_main);
        submitProgress = findViewById(R.id.submit_progress);
        markButton = findViewById(R.id.mark_button);
        markButton.setVisibility(View.GONE);
        progressBar.setVisibility(ProgressBar.VISIBLE);
//test
        //Firebase Database
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("table");

        //Retrieving the subjects from teachers' list
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                    for (DataSnapshot subjectSnapshot : dataSnapshot.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("subject").getChildren()) {
                        subjectList.add(subjectSnapshot.child("subname").getValue(String.class));
                    }
                    Spinner spinner = findViewById(R.id.spinner_subject);
                    ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_class, subjectList);
                    adapter.setDropDownViewResource(R.layout.spinner_dropdown);
                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            try {
                                clickedSubject = ((TextView) view).getText().toString();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Unable to connect to the database", Toast.LENGTH_SHORT).show();
            }
        });

        //Retrieving users from database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                markButton.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                beginLayout.setVisibility(View.VISIBLE);
                endLayout.setVisibility(View.VISIBLE);
                className.clear();
                for (DataSnapshot classSnapshot : dataSnapshot.child("class").getChildren()) {
                    if (!className.contains(classSnapshot.child("classname").getValue(String.class))) {
                        className.add(classSnapshot.child("classname").getValue(String.class));
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
                                studentRoll.clear();
                                studentID.clear();
                                for (DataSnapshot classSnapshot : dataSnapshot.child("class").getChildren()) {
                                    if (classSnapshot.child("classname").getValue(String.class).equals(clickedClass)) {
                                        studentRoll.add(classSnapshot.child("rollno").getValue(String.class));
                                        studentID.add(classSnapshot.getKey());
                                    }
                                }
                                ListView listView = (ListView) findViewById(R.id.list1);
                                ArrayList<String> duplicateRoll = new ArrayList<>(studentRoll);
                                Collections.sort(studentRoll);
                                studentID = sortAttendanceList(studentID, studentRoll, duplicateRoll);
                                attendanceList = new ArrayList<String>();
                                for (int i = 0; i < studentRoll.size(); i++)
                                    attendanceList.add("false");
                                listView.setAdapter(new MyCustomAdapter(studentRoll, attendanceList, MainMark.this, MainMark.this));
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
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter adap1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_class, className);
        adap1.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adap1);

        //OnClickListener for marking attendance from app to FireBase
        markButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitProgress.setVisibility(View.VISIBLE);
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DATE);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int period = getPeriod(hour);
                if (period == -1) {
                    Toast.makeText(getApplicationContext(), "Are you marking attendance at the right time?", Toast.LENGTH_LONG).show();
                    submitProgress.setVisibility(View.GONE);
                    return;
                }
                if (duplicate) {
                    Toast.makeText(getApplicationContext(), "Attendance already entered! Try updating", Toast.LENGTH_LONG).show();
                    submitProgress.setVisibility(View.GONE);
                } else {
                    for (int i = 0; i < attendanceList.size(); i++) {
                        String tempDate = "" + day + "/" + month + "/" + year;
                        Spinner spinner = findViewById(R.id.spinner_subject);
                        Attendance attendance = new Attendance(tempDate, period + "", attendanceList.get(i), firebaseUser.getUid(), clickedSubject);
                        String pushID = databaseReference.child("class").child(studentID.get(i)).child("attendance").push().getKey();
                        databaseReference.child("class").child(studentID.get(i)).child("attendance").child(pushID).setValue(attendance);
                    }
                    Toast.makeText(getApplicationContext(), "Marked attendance successfully!", Toast.LENGTH_LONG).show();
                    submitProgress.setVisibility(View.GONE);
                }

            }
        });
    }

    private ArrayList<String> sortAttendanceList(ArrayList<String> attendanceList, ArrayList<String> studentRoll, ArrayList<String> duplicateRoll) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < attendanceList.size(); i++) {
            arrayList.add(attendanceList.get(duplicateRoll.indexOf(studentRoll.get(i))));
        }
        return arrayList;
    }

    @Override
    public void onCheckedBox(String s, int pos, String t) {
        attendanceList.set(studentRoll.indexOf(s), t);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            finish();
        } else if (!FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
            finish();
        }
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