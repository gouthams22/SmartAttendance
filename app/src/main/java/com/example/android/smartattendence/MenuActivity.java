package com.example.android.smartattendence;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuActivity extends AppCompatActivity {
    TextView textView;
    DatabaseReference databaseReference;
    RelativeLayout relativeLayout;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        progressBar = findViewById(R.id.progress_menu);
        progressBar.setVisibility(View.VISIBLE);
        relativeLayout = findViewById(R.id.relative_menu);
        relativeLayout.setVisibility(View.GONE);
        databaseReference = FirebaseDatabase.getInstance().getReference("table");

        Button button = findViewById(R.id.button_mark);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, MainMark.class);
                startActivity(i);
            }
        });
        button = findViewById(R.id.button_update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, MainUpdate.class);
                startActivity(i);
            }
        });
        button = findViewById(R.id.sign_out);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });
        button = findViewById(R.id.button_view);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, ViewActivity.class);
                startActivity(i);
            }
        });
        button = findViewById(R.id.button_management);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, ManagementActivity.class);
                startActivity(i);
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
        } else {
            textView = findViewById(R.id.welcome_message);
            textView.setText("Welcome " + FirebaseAuth.getInstance().getCurrentUser().getEmail());
        }
        //hello
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                relativeLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    return;
                }
                if (dataSnapshot.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue() == null) {
                    Intent subIntent = new Intent(MenuActivity.this, SubjectActivity.class);
                    startActivity(subIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        textView = findViewById(R.id.welcome_message);
        textView.setText("");
    }
}
