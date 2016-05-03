package com.example.alfred.craftsman_part;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by alfred on 4/27/16.
 */
public class SignActivity extends Activity {

    EditText userName, userPassword;
    TextView errorText;
    Button sign;
    DatabaseHelper db;
    boolean wpStatus;
    String WP;
    String [] selected = new String[3];
    int i = 0;
    ArrayList arrayList = new ArrayList<String>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        final TextView test = (TextView)findViewById(R.id.test);


        userName = (EditText)findViewById(R.id.user_name_edit);
        userPassword = (EditText)findViewById(R.id.user_pw_edit);
        errorText = (TextView)findViewById(R.id.sign_error_txt);
        sign = (Button)findViewById(R.id.sign_wp_btn);
        db = new DatabaseHelper(this);

        final String [] controllList = {"Kontrollera dörrmått" , "Kontroll av dimensioner", "Kotroll av regelavstånd", "Kontroll av kortlingar", "Kontroll av isolering", "Kontroll av El och VVS", "Kontroll av Brandklass" , "Kontroll av ljudklass"};
        ListAdapter listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item, controllList );
        final ListView checkList = (ListView)findViewById(R.id.controll_list);

        checkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String item = checkList.getItemAtPosition(position).toString();

                if(!exist(item)){
                    arrayList.add(item);
                    test.append(item + '\n');
                }



            }
        });

        checkList.setAdapter(listAdapter);

        Intent activityThatCalled = getIntent();
        wpStatus = activityThatCalled.getBooleanExtra("wpStatus", false);
        WP = activityThatCalled.getExtras().getString("wpID");


    }
    //Skriver udner AP
    public void sign(View view) {
        onSignClick();
    }
    //Hanterar kommunikationen med databasen så att använderaren är okej
    private void onSignClick(){

        String userN = userName.getText().toString();
        String userPw = userPassword.getText().toString();

        if(db.getStatus(WP)) db.changeStatus(WP, userN, false);

        else db.changeStatus(WP,userN,true);



        Intent goBack = new Intent();
        goBack.putExtra("signOK", arrayList);
        goBack.putExtra("userID", userN);
        setResult(RESULT_OK, goBack);
        finish();


    }

    public boolean exist (String string){

        for(int i =0; i< arrayList.size();i++){

            if(string.equals(arrayList.get(i))){

                return true;
            }


        }

        return false;
    }
}
