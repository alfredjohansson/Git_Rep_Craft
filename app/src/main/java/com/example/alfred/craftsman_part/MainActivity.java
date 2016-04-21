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

    private int idSentBack;
    private static final int PROJECT_ID=1;
    private static final int RESULT_OK=2;

    TextView idReturned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String [] rooms = {"rum 1", "rum 2", "rum 3"};

        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rooms);

        ListView roomList = (ListView)findViewById(R.id.room_list);

        idReturned = (TextView)findViewById(R.id.project_id_returned);

        roomList.setAdapter(listAdapter);



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

        return super.onOptionsItemSelected(item);
    }

    //Ska fylla listView med rum och visa planritningen ifall returnCode=TRUE
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            idSentBack = data.getIntExtra("projectID", resultCode);

            idReturned.setText(idSentBack);

    }
}
