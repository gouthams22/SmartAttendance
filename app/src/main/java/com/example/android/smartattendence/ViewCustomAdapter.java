package com.example.android.smartattendence;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewCustomAdapter extends BaseAdapter implements ListAdapter {

  //  private Connector conn;


    private ArrayList<String> dateList = new ArrayList<String>();
    private ArrayList<String> attendenceList = new ArrayList<String>();
    private ArrayList<String> periodList = new ArrayList<String>();
    private Context context;

    public ViewCustomAdapter(ArrayList<String> nameList, ArrayList<String> list3, ArrayList<String> list2, Context context, Activity activity) {
        this.dateList = nameList;
        this.attendenceList = list2;
        this.periodList= list3;
        this.context = context;
        try {
 //           conn = (Connector) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity + " must implements MyCustomClickListener");
        }
    }

    @Override
    public int getCount() {
        return dateList.size();
    }

    @Override
    public Object getItem(int pos) {
        return dateList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        //return nameList.get(pos).getId();
        return 0;
        //just return 0 if your nameList items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.view_students, null);
        }

        //Handle TextView and display string from your nameList
        final TextView text = (TextView) view.findViewById(R.id.listText1);
        text.setText(dateList.get(position));

        //Handle buttons and add onClickListeners
        final TextView text1 = (TextView) view.findViewById(R.id.listText2);
        text1.setText(attendenceList.get(position));

        final TextView text2 = (TextView) view.findViewById(R.id.listText3);
        text2.setText(periodList.get(position));
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (button.isChecked()) {
//                    conn.onCheckedBox(nameList.get(position), position, "true");
//                } else {
//                    conn.onCheckedBox(nameList.get(position), position, "false");
//                }
//            }
//        });

        return view;
    }


}