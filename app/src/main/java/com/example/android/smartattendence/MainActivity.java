package com.example.android.smartattendence;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.username);
                String username = editText.getText().toString().trim();
                editText = (EditText) findViewById(R.id.password);
                String password = editText.getText().toString().trim();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Field cannot be blank", Toast.LENGTH_SHORT).show();
                    return;
                } else if ((username.equals("goutham") && password.equals("goutham")) || (username.equals("gokul") && password.equals("gokul")) || (username.equals("karthik") && password.equals("karthik")) || (username.equals("sayannah") && password.equals("sayannah")) || (username.equals("nithish") && password.equals("nithish"))) {
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
