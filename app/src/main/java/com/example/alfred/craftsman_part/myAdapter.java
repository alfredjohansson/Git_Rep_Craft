package com.example.alfred.craftsman_part;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alfred on 5/1/16.
 */
public class myAdapter extends ArrayAdapter<String> {

    DatabaseHelper db;


    public myAdapter(Context context, String [] value) {
        super(context, R.layout.list_check_adapter, value);

        db = new DatabaseHelper(context);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater theInflator = LayoutInflater.from(getContext());
        View theView = theInflator.inflate(R.layout.list_check_adapter, parent, false);

        String controllItems = getItem(position);
        TextView controllText = (TextView) theView.findViewById(R.id.controll_txt);
        ImageView controllImg = (ImageView) theView.findViewById(R.id.done_img);

        boolean status = db.getStatus(controllItems);

        if (status){
            controllText.setText(controllItems);
            controllImg.setImageResource(R.drawable.index);


        }

           else {
            controllImg.setImageResource(R.drawable.unchecked);
            controllText.setText(controllItems);
        }
        return theView;
    }
}
