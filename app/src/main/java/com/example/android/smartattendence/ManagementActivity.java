package com.example.android.smartattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ManagementActivity extends AppCompatActivity {
    ArrayList<String> studentID = new ArrayList<>();
    ArrayList<String> studentRoll = new ArrayList<>();
    ArrayList<String> studentName = new ArrayList<>();
    ArrayList<String> className = new ArrayList<>();
    Button add;
    Spinner spinner;
    ProgressBar progressBar;
    private String clickedClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        add = findViewById(R.id.add_button);
        spinner = findViewById(R.id.spinner_subject);
        progressBar = findViewById(R.id.progress_subject);
        progressBar.setVisibility(View.VISIBLE);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("table");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                className.clear();
                for (DataSnapshot classSnapshot : dataSnapshot.child("class").getChildren()) {
                    if (!className.contains(classSnapshot.child("classname").getValue(String.class))) {
                        className.add(classSnapshot.child("classname").getValue(String.class));
                    }
                }
                Collections.sort(className);
                Spinner spinner = (Spinner) findViewById(R.id.spinner_class);
                ArrayAdapter adap1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_class, className);
                adap1.setDropDownViewResource(R.layout.spinner_dropdown);
                spinner.setAdapter(adap1);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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
                                studentName.clear();
                                for (DataSnapshot classSnapshot : dataSnapshot.child("class").getChildren()) {
                                    if (classSnapshot.child("classname").getValue(String.class).equals(clickedClass)) {
                                        studentRoll.add(classSnapshot.child("rollno").getValue(String.class));
                                        studentID.add(classSnapshot.getKey());
                                        studentName.add(classSnapshot.child("name").getValue(String.class));
                                    }
                                }
                                ListView listView = (ListView) findViewById(R.id.list1);
                                ArrayList<String> duplicateRoll = new ArrayList<>(studentRoll);
                                Collections.sort(studentRoll);
                                studentID = sortAttendanceList(studentID, studentRoll, duplicateRoll);
                                studentName = sortAttendanceList(studentName, studentRoll, duplicateRoll);
//                                attendanceKey = sortAttendanceList(attendanceKey, studentRoll, duplicateRoll);
//                                attendanceList = sortAttendanceList(attendanceList, studentRoll, duplicateRoll);
                                listView.setAdapter(new ManagementCustomAdapter(studentRoll, studentRoll, getApplicationContext(), ManagementActivity.this));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "Couldn't retrieve the database", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Couldn't retrieve the database", Toast.LENGTH_SHORT).show();
            }
        });
        ListView listView = findViewById(R.id.list1);
        listView.setAdapter(new ManagementCustomAdapter(studentRoll, null, ManagementActivity.this, ManagementActivity.this));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String roll = studentRoll.get(position);
                final String tempId = studentID.get(position);
                final String tempName = studentName.get(position);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ManagementActivity.this);
                // Setting Dialog Title
                alertDialog.setTitle("Student Management");
                // Setting Dialog Message
                alertDialog.setMessage("Roll number: " + studentRoll.get(position) + " \nName: " + studentName.get(position));
                // Setting Icon to Dialog
//                alertDialog.setIcon(R.drawable.ic_launcher_background);
                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CustomDialogClass cdd = new CustomDialogClass(ManagementActivity.this, tempName, roll, tempId);
                        cdd.show();
                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        // dialog.cancel();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("table");
                        databaseReference.child("class").child(tempId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Successfully deleted", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                // Showing Alert Message
                alertDialog.show();

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickedClass != null) {
                    AddCustomClass a = new AddCustomClass(ManagementActivity.this, clickedClass);
                    a.show();
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
}
