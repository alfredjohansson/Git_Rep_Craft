package com.example.alfred.craftsman_part;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by alfred on 4/26/16.
 */
public class RoomActivity extends Activity {

    private final static int DEFAULT=0;
    private final int WORK_PACKAGE_ID=1;
    DatabaseHelper db;
    private int floorID;
    private int projectID;
    private String workPackageID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        final Context context = this;
        final ListView workPackageList = (ListView) findViewById(R.id.work_package_list);
        Button chooseWrokPackageBtn = (Button)findViewById(R.id.choose_workpackage_btn);

        db = new DatabaseHelper(this);
        SharedPreferences projectSettings = getSharedPreferences("projectSettings",MODE_PRIVATE);
        floorID = projectSettings.getInt("projectFloor", DEFAULT);
        projectID = projectSettings.getInt("projectID", DEFAULT);

        Intent activityThatCalled = getIntent();
        int roomID = activityThatCalled.getExtras().getInt("roomID", DEFAULT);
        ImageView draw = (ImageView)findViewById(R.id.room_draw_img);
        draw.setImageResource(R.drawable.rum_ritning);

        String [] workPackages =  db.getWP(roomID, floorID, projectID); //{"WP1", "WP2", "WP3"}; //
        db.close();


        ListAdapter listAdapter = new myAdapter(this, workPackages);
        workPackageList.setAdapter(listAdapter);

        workPackageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                workPackageID = workPackageList.getItemAtPosition(position).toString();
                Toast.makeText(context, workPackageID + " Valt", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void chooseWorkPackage(View view) {
        Intent getActivityWorkPackage = new Intent(this, WorkPackageActivity.class);
        getActivityWorkPackage.putExtra("workPackageID", workPackageID);
        startActivityForResult(getActivityWorkPackage, WORK_PACKAGE_ID);
    }


}
