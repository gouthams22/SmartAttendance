package com.example.android.smartattendence;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        textView = findViewById(R.id.welcome_message);
        textView.setText("");
    }
}
