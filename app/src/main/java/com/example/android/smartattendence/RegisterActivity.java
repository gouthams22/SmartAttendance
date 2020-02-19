package com.example.android.smartattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText userEdit, passwordEdit;
    TextView textSignIn;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        userEdit = findViewById(R.id.username);
        passwordEdit = findViewById(R.id.password);
        textSignIn = findViewById(R.id.link_signin);
        btnSignUp = findViewById(R.id.register_button);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString().trim();
                if (email.isEmpty()) {
                    userEdit.setError("Enter Email");
                    userEdit.requestFocus();
                } else if (password.isEmpty()) {
                    passwordEdit.setError("Enter Password");
                    passwordEdit.requestFocus();
                } else if (checkEmail(email)) {
                    userEdit.setError("Username is not valid!");
                    userEdit.requestFocus();
                } else if (checkPassword(password)) {
                    passwordEdit.setError("Minimum 8 Characters!");
                    passwordEdit.requestFocus();
                } else if (!(email.isEmpty() || password.isEmpty())) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Sign Up Unsuccessful", Toast.LENGTH_SHORT).show();
                            } else {
                                firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            userEdit.setText("");
                                            passwordEdit.setText("");
                                            Toast.makeText(getApplicationContext(), "Registered Successfully! Please verify your E-mail address", Toast.LENGTH_LONG).show();
//                                            Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
//                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                }
            }
        });
        textSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
        //Function created :When typing if the user touches outside the keyboard, the keyboard disappears
        LinearLayout coordinatorLayout = findViewById(R.id.register_layout);
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
    public boolean checkEmail(String email) {
        return !email.contains("@") || !email.contains(".");
    }

    public boolean checkPassword(String password) {
        return password.length() < 8;
    }

}
