package com.example.alfred.craftsman_part;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.telephony.CellIdentityCdma;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by alfred on 5/2/16.
 */
public class CheckListAdapter extends ArrayAdapter<String> {

    CheckBox check;
    int position;
    String kontroll;
    TextView text;

    View contView;

    public CheckListAdapter(Context context, String [] value) {
        super(context, R.layout.list_adapter_view_sign, value);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View theView = inflater.inflate(R.layout.list_adapter_view_sign, parent, false);

        this.position = position;


        kontroll = getItem(position);


        check = (CheckBox) theView.findViewById(R.id.kontroll_check);



        text = (TextView) theView.findViewById(R.id.kontroll_txt);

        text.setText(kontroll);




        return theView;


    }



}
