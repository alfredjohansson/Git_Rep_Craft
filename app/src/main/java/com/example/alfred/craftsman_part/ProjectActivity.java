package com.example.alfred.craftsman_part;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class ProjectActivity extends Activity{




    String projectId;
    int id;
    DatabaseHelper db;

    Button choose, back;
    TextView error;
    EditText projectId_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_project);

        error = (TextView)findViewById(R.id.login_error_txt);
        choose = (Button)findViewById(R.id.chose_project_Btn);
        back = (Button)findViewById(R.id.return_to_Main_btn);
        projectId_edit = (EditText)findViewById(R.id.project_id_edit);
        db = new DatabaseHelper(this);


    }

    public void sendBackValue(){


        projectId = projectId_edit.getText().toString();
        id = Integer.parseInt(projectId);
        SharedPreferences ID = getSharedPreferences("projectSettings", MODE_PRIVATE);



        //Kollar ifall id finns i databasen
       boolean exist = db.checkWpId(id);
        db.close();

        //Ska stoppa ifall inget har skrivits i editText
        if (projectId_edit.getText().length()==0) {
            error.append("Fel: Måste välja ett projekt!");
            return;
        }

        //Ger fel ifall id inte finns i databesen
        else if (!exist){

            error.setText("Fel: Projekt hittades inte");
            return;
        }
        //Ifall id finns returnera det till Main och gå tillbaka

        else if (exist) {

            ID.edit().putInt("projectID",id).commit();



            Intent goBack = new Intent();

            goBack.putExtra("projectID",projectId);

            setResult(RESULT_OK, goBack);

            finish();
        }

    }

    public void onChoose(View view) {

        sendBackValue();

    }

    public void goBack(View view) {

        Intent goBack = new Intent();

        setResult(RESULT_CANCELED, goBack);

        finish();
    }
}
