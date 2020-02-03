package com.example.android.smartattendence;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    boolean remember;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.load);
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    if (firebaseUser.isEmailVerified()) {
                        Toast.makeText(getApplicationContext(), "You are Logged in!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };
        /*
        //Start of checkbox
        CheckBox checkBox = findViewById(R.id.remember_checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                remember = isChecked;
            }
        });
        if (remember) {
            EditText userEdit = (EditText) findViewById(R.id.username);
            String username = userEdit.getText().toString().trim();
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            intent.putExtra("user", "goutham");
            startActivity(intent);
        }*/
        Button button = (Button) findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText userEdit = (EditText) findViewById(R.id.username);
                String username = userEdit.getText().toString().trim();
                final EditText passwordEdit = (EditText) findViewById(R.id.password);
                String password = passwordEdit.getText().toString().trim();
                if (username.isEmpty()) {
                    userEdit.setError("Enter email id");
                    userEdit.requestFocus();
//                    return;
                } else if (password.isEmpty()) {
                    passwordEdit.setError("Enter password");
                    passwordEdit.requestFocus();
                } else if (!username.isEmpty() && !password.isEmpty()) {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Login unsuccessful", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(ProgressBar.GONE);
                            } else {
                                if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                    userEdit.setText("");
                                    passwordEdit.setText("");
                                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                                    progressBar.setVisibility(ProgressBar.GONE);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Please verify your E-mail address", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(ProgressBar.GONE);
                                }
                            }
                        }
                    });
                }
            }
        });

        TextView textView = findViewById(R.id.link_signup);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
        textView = findViewById(R.id.link_forget);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userEdit = (EditText) findViewById(R.id.username);
                String username = userEdit.getText().toString().trim();
                if (username.isEmpty()) {
                    userEdit.setError("Enter email id to reset");
                    userEdit.requestFocus();
                } else {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                    firebaseAuth.sendPasswordResetEmail(username)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressBar.setVisibility(ProgressBar.GONE);
                                        Toast.makeText(getApplicationContext(), "An email has been sent to you.", Toast.LENGTH_LONG).show();
                                    } else {
                                        progressBar.setVisibility(ProgressBar.GONE);
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

        //Function created :When typing if the user touches outside the keyboard, the keyboard disappears
        LinearLayout coordinatorLayout = findViewById(R.id.login_layout);
        coordinatorLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager inputMethodManager = (InputMethodManager)
                        v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                // Hide the soft keyboard
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}
/*

 */