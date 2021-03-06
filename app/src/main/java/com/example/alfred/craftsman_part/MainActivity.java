package com.example.alfred.craftsman_part;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String idSentBack;
    private String floorSentBack;
    private int idFromProject;
    private int roomIdFromChoosen;

    DatabaseHelper db;
    final int DEFAULT=0;
    final int PROJECT_ID=1;
    final int PROJECT_FLOOR=2;
    int ACTIVITY_ROOM=3;

    Button chooseFloor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DatabaseHelper(this);
        db.insertData();
        db.close();
        chooseFloor = (Button) findViewById(R.id.choose_room_btn);
        final ImageView draw = (ImageView)findViewById(R.id.main_draw_img);

        SharedPreferences projectSettings = getSharedPreferences("projectSettings", MODE_PRIVATE);
        int projectID = projectSettings.getInt("projectID", DEFAULT);
        int floorID = projectSettings.getInt("projectFloor",DEFAULT);



        String [] rooms = db.getRoom(projectID,floorID);
        // for(int i=0;i<rooms.length;i++) rooms[i] = "Rum " + rooms[i];
        db.close();
        ListAdapter listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, rooms);

        final ListView roomList = (ListView)findViewById(R.id.room_list);



        roomList.setAdapter(listAdapter);

        roomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                roomIdFromChoosen = Integer.parseInt(roomList.getItemAtPosition(position).toString());

                Toast.makeText(context, "Du har valt rum " + roomIdFromChoosen, Toast.LENGTH_SHORT).show();


            }
        });

        if (floorID==0)draw.setImageResource(R.drawable.fel);
        if(floorID==1)draw.setImageResource(R.drawable.ritning_app);
        if(floorID==2)draw.setImageResource(R.drawable.planritning_test);

            }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        //Ifall man klickar på välj projekt i menun så startas activiteten ProjectActivity som ska returnara projekt id?
        if (id == R.id.action_choose_project){
            Intent getProjectActivity = new Intent(this, ProjectActivity.class);
            startActivityForResult(getProjectActivity, PROJECT_ID);

        }

        if (id == R.id.action_pick_floor){
            Intent getChooseFloorActivity = new Intent(this, ChooseFloorActivity.class);
            getChooseFloorActivity.putExtra("prokectID",idFromProject);
            startActivityForResult(getChooseFloorActivity, PROJECT_FLOOR);
        }

        return super.onOptionsItemSelected(item);
    }

    public void chooseRoom(View view) {
        Intent getActivityRoom = new Intent(this, RoomActivity.class);
        getActivityRoom.putExtra("roomID", roomIdFromChoosen);
        startActivityForResult(getActivityRoom, ACTIVITY_ROOM);

    }

}
