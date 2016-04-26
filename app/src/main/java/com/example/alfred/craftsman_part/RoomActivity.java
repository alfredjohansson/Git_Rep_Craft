package com.example.alfred.craftsman_part;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by alfred on 4/26/16.
 */
public class RoomActivity extends Activity {

    private final static int DEFAULT=0;
    DatabaseHelper db;
    private int floorID;
    private int projectID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        Context context = this;
        ListView workPackageList = (ListView) findViewById(R.id.work_package_list);
        Button chooseWrokPackageBtn = (Button)findViewById(R.id.choose_workpackage_btn);
        TextView roomDrawing = (TextView)findViewById(R.id.room_drawing_txt);

        db = new DatabaseHelper(this);
        SharedPreferences projectSettings = getSharedPreferences("projectSettings",MODE_PRIVATE);
        floorID = projectSettings.getInt("projectFloor", DEFAULT);
        projectID = projectSettings.getInt("projectID", DEFAULT);

        Intent activityThatCalled = getIntent();
        int roomID = activityThatCalled.getExtras().getInt("roomID", DEFAULT);
        roomDrawing.setText("Förhansvisa rummsritning " + roomID);

        String [] workPackages =  {"WP1", "WP2", "WP3"}; //db.getWP(projectID, floorID, roomID); //
        db.close();


        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, workPackages);
        workPackageList.setAdapter(listAdapter);

    }


}
