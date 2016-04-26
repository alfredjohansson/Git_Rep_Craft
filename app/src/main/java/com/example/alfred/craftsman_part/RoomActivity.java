package com.example.alfred.craftsman_part;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by alfred on 4/26/16.
 */
public class RoomActivity extends Activity {

    private final static int DEFAULT=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        ListView workPackageList = (ListView) findViewById(R.id.work_package_list);
        Button chooseWrokPackageBtn = (Button)findViewById(R.id.choose_workpackage_btn);
        TextView roomDrawing = (TextView)findViewById(R.id.room_drawing_txt);
        Intent activityThatCalled = getIntent();
        int roomID = activityThatCalled.getExtras().getInt("roomID", DEFAULT);
        roomDrawing.setText("Förhansvisa rummsritning " + roomID);
    }


}
