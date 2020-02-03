package com.example.android.smartattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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
    ArrayList<String> className = new ArrayList<>();
    ArrayList<String> list = new ArrayList<String>(Arrays.asList("111,222,333,444,555,666".split(",")));
    ArrayList<String> list1 = new ArrayList<String>(Arrays.asList("true,true,false,true,false,false".split(",")));
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mark);
//        childArrayList = new ArrayList<>();
        //Firebase Database
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("table");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                className.clear();
                for (DataSnapshot classSnapshot : dataSnapshot.child("class").getChildren()) {
//                    ClassChild classChild = new ClassChild();
//                    classChild = classSnapshot.getValue(ClassChild.class);
//                    className.add(classChild.className);
                    if (!className.contains(classSnapshot.child("classname").getValue(String.class)))
                        className.add(classSnapshot.child("classname").getValue(String.class));
                }
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


    }

    @Override
    public void onCheckedBox(String s, int pos) {
        Toast.makeText(MainMark.this, s + pos, Toast.LENGTH_LONG).show();
    }
}