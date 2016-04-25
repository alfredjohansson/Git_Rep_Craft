package com.example.alfred.craftsman_part;

import android.content.Intent;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String idSentBack;
    private String floorSentBack;

    DatabaseHelper db;
    final int PROJECT_ID=1;
    final int PROJECT_FLOOR=2;
    String [] rooms;


    TextView idReturned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DatabaseHelper(this);
        db.insertData();
        db.close();
        rooms = new String[]{"rum 1", "rum 2", "rum 3",
                "rum 4",
                "rum 5",
                "rum 6",
                "rum 7",
                "rum 8",
                "rum 9", "rum 10"};

        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rooms);

        final ListView roomList = (ListView)findViewById(R.id.room_list);

        idReturned = (TextView)findViewById(R.id.id_returned_txt);

        roomList.setAdapter(listAdapter);

        roomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idReturned.setText(roomList.getItemAtPosition(position).toString());


            }
        });

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
            startActivityForResult(getChooseFloorActivity, PROJECT_FLOOR);
        }

        return super.onOptionsItemSelected(item);
    }

    //Ska fylla listView med rum och visa planritningen ifall returnCode=TRUE
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            //ID från välj projekt

            if(PROJECT_ID==requestCode) {
                idSentBack = data.getStringExtra("projectID");
                int test = Integer.parseInt(idSentBack);
                String [] blabla = db.getFloors(test);
               // db.close();

                for(int i = 0 ; i < blabla.length; i++){
                    idReturned.append(" "+ blabla[i]);
                }

                //idReturned.setText(idSentBack);


            } else if(PROJECT_FLOOR == requestCode) {
                floorSentBack = data.getStringExtra("floorID");
                idReturned.setText(floorSentBack);
            }



    }

}
