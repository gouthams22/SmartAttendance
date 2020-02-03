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
import java.util.LinkedHashSet;

public class MainMark extends AppCompatActivity implements Connector {
    FirebaseUser firebaseUser;
    String classes[] = {"Class A", "Class B", "Class C"};
    //    ArrayList<ClassChild> classChildren = new ArrayList<>();
    ArrayList<String> className = new ArrayList<>();
    ArrayList<String> studentName = new ArrayList<>();
    ArrayList<String> attendanceList = new ArrayList<>();
    ArrayList<String> list = new ArrayList<String>(Arrays.asList("111,222".split(",")));//,333,444,555,666
    ArrayList<String> list1 = new ArrayList<String>(Arrays.asList("true,false".split(",")));//true,false,true,false,
    DatabaseReference databaseReference;
    Button markButton;
    ProgressBar progressBar;
    private String clickedClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mark);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(ProgressBar.VISIBLE);
//        childArrayList = new ArrayList<>();
        //Firebase Database
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("table");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(ProgressBar.GONE);
                className.clear();
                for (DataSnapshot classSnapshot : dataSnapshot.child("class").getChildren()) {
//                    ClassChild classChild = new ClassChild();
//                    classChild = classSnapshot.getValue(ClassChild.class);
//                    className.add(classChild.className);
//                    classChildren.add(classSnapshot.getValue(ClassChild.class));
                    if (!className.contains(classSnapshot.child("classname").getValue(String.class)))
                        className.add(classSnapshot.child("classname").getValue(String.class));
                }
                Spinner spinner = (Spinner) findViewById(R.id.spinner1);
                ArrayAdapter adap1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_class, className);
                adap1.setDropDownViewResource(R.layout.spinner_dropdown);
                spinner.setAdapter(adap1);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        clickedClass = ((TextView) view).getText().toString();
                        Toast.makeText(getApplicationContext(), clickedClass, Toast.LENGTH_LONG).show();
                        FirebaseDatabase.getInstance().getReference("table").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                studentName.clear();
                                attendanceList.clear();
                                for (DataSnapshot classSnapshot : dataSnapshot.child("class").getChildren()) {
//                    ClassChild classChild = new ClassChild();
//                    classChild = classSnapshot.getValue(ClassChild.class);
//                    className.add(classChild.className);
//                    classChildren.add(classSnapshot.getValue(ClassChild.class));
                                    if (classSnapshot.child("classname").getValue(String.class).equals(clickedClass))
                                        studentName.add(classSnapshot.child("name").getValue(String.class));
//                                    attendanceList.add(classSnapshot.child("attendance").child("present").getValue(String.class));
                                }
                                ListView listView = (ListView) findViewById(R.id.list1);
                                ArrayList<String> attendanceList = new ArrayList<String>();
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

        Toast.makeText(MainMark.this, list1.get(1), Toast.LENGTH_LONG).show();
        ListView listView = (ListView) findViewById(R.id.list1);
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
//        ArrayAdapter adap1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_class, classes);
        ArrayAdapter adap1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_class, className);
        adap1.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adap1);
        listView.setAdapter(new MyCustomAdapter(list, list1, MainMark.this, MainMark.this));
        markButton = findViewById(R.id.mark_button);
    }

    @Override
    public void onCheckedBox(String s, int pos) {
        Toast.makeText(MainMark.this, s + pos, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            finish();
    }
}