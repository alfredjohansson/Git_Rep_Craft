package com.example.alfred.craftsman_part;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ChooseFloorActivity extends Activity {

    String floor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choose_floor);

        final ListView floor_list = (ListView)findViewById(R.id.floor_list);
        final TextView floormap = (TextView)findViewById(R.id.floor_selected_txt);

        String [] floors = {"Plan 1", "Plan 2", "Plan 3", "Plan 4", "Plan 5", "Plan 6", "Plan 7", "Plan 8", "Plan 9", "Plan 10", "Plan 11", "Plan 12"};

        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, floors);

        floor_list.setAdapter(listAdapter);


        floor_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                floor = floor_list.getItemAtPosition(position).toString();
                floormap.setText(floor);

            }
        });
    }


    public void chooseFloor(View view) {

        Intent goBackToMain = new Intent();
        goBackToMain.putExtra("floorID", floor);
        goBackToMain.addFlags(1);

        setResult(RESULT_OK, goBackToMain);
        finish();

    }

    public void getFloorID(){

    }
}
