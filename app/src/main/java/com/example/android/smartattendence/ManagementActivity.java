package com.example.android.smartattendence;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ManagementActivity extends AppCompatActivity {
    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> rollnoList = new ArrayList<>();
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        add=findViewById(R.id.add_button);
        nameList.add("1");
        nameList.add("2");
        nameList.add("3");
        ListView listView = findViewById(R.id.list1);
        listView.setAdapter(new ManagentCustomAdapter(nameList, null, ManagementActivity.this, ManagementActivity.this));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ManagementActivity.this);
                // Setting Dialog Title
                alertDialog.setTitle("Confirm Delete");
                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want delete "+nameList.get(position)+" ?");
                // Setting Icon to Dialog
//                alertDialog.setIcon(R.drawable.ic_launcher_background);
                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Successfully deleted", Toast.LENGTH_SHORT).show();
                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        // dialog.cancel();
                        CustomDialogClass cdd=new CustomDialogClass(ManagementActivity.this,"hi","hello");

                        cdd.show();
                    }
                });
                // Showing Alert Message
                alertDialog.show();

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCustomClass a=new AddCustomClass(ManagementActivity.this);
                a.show();
            }
        });

    }
}
