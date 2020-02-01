package com.example.android.smartattendence;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainMark extends AppCompatActivity implements Connector {

    String classes[] = {"Class A", "Class B", "Class C"};
    ArrayList<String> list = new ArrayList<String>(Arrays.asList("111,222,333,444,555,666".split(",")));
    ArrayList<String> list1 = new ArrayList<String>(Arrays.asList("true,true,false,true,false,false".split(",")));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mark);
        Toast.makeText(MainMark.this, list1.get(1), Toast.LENGTH_LONG).show();

        ListView listView = (ListView) findViewById(R.id.list1);
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter adap1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_class, classes);
        adap1.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adap1);
        listView.setAdapter(new MyCustomAdapter(list, list1, MainMark.this, MainMark.this));


    }

    @Override
    public void onCheckedBox(String s, int pos) {
        Toast.makeText(MainMark.this, s + pos, Toast.LENGTH_LONG).show();
    }
}