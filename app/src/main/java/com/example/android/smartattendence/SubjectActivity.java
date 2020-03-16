package com.example.android.smartattendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SubjectActivity extends AppCompatActivity {
    ArrayList<String> subject_list = new ArrayList<>();
    EditText subject, name;
    ImageButton add;
    Button submit;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        progressBar = findViewById(R.id.progress_subject);
        subject = findViewById(R.id.edit_subject);
        name = findViewById(R.id.edit_name);
        add = findViewById(R.id.add_subject);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subject.getText().toString().trim().isEmpty()) {
                    subject.setError("Field Blank");
                    subject.requestFocus();
                } else {
                    subject_list.add(subject.getText().toString().trim());
                    Toast.makeText(getApplicationContext(), "Added subject", Toast.LENGTH_SHORT).show();
                    subject.setText("");
                }
            }
        });
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                if (name.getText().toString().trim().isEmpty()) {
                    name.setError("Field Blank");
                    name.requestFocus();
                    progressBar.setVisibility(View.GONE);
                } else if (subject_list.size() == 0) {
                    Toast.makeText(getApplicationContext(), "No subjects added", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                } else {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("table");
                    databaseReference.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").setValue(name.getText().toString().trim());
                    for (int i = 0; i < subject_list.size(); i++) {
                        String key = databaseReference.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("subject").push().getKey();
                        databaseReference.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("subject").child(key).child("subname").setValue(subject_list.get(i))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_LONG).show();
                                    }
                                });
//                        startActivity(new Intent(SubjectActivity.this,MenuActivity.class));
                        finish();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
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
}
