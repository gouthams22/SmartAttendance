package com.example.android.smartattendence;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        }
        Button button = (Button) findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userEdit = (EditText) findViewById(R.id.username);
                String username = userEdit.getText().toString().trim();
                EditText passwordEdit = (EditText) findViewById(R.id.password);
                String password = passwordEdit.getText().toString().trim();
                if (username.isEmpty()) {
                    userEdit.setError("Enter email id");
                    userEdit.requestFocus();
//                    return;
                } else if (password.isEmpty()) {
                    passwordEdit.setError("Enter password");
                    passwordEdit.requestFocus();
                } else if ((username.equals("goutham") && password.equals("goutham")) || (username.equals("gokul") && password.equals("gokul")) || (username.equals("karthik") && password.equals("karthik")) || (username.equals("sayannah") && password.equals("sayannah")) || (username.equals("nithish") && password.equals("nithish"))) {
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                }
            }
        });

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
}
