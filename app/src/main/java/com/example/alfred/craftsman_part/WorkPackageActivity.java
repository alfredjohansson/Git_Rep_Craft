package com.example.alfred.craftsman_part;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class WorkPackageActivity extends Activity {

    TextView material, tools, preWork, commentsFromBefore;
    EditText comments_edit;
    Button sign, goBack;
    CheckBox done, notDone;
    DatabaseHelper db;
    String WPsent;
    boolean wpStatus;

    private final static int SIGN_WP=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_package);
        db = new DatabaseHelper(this);

        material = (TextView)findViewById(R.id.material_txt);
        tools = (TextView)findViewById(R.id.tools_txt);
        preWork = (TextView)findViewById(R.id.pre_work_txt);
        commentsFromBefore = (TextView)findViewById(R.id.al_comments_txt);

        comments_edit = (EditText)findViewById(R.id.comment_edit);

        sign = (Button)findViewById(R.id.sign_wp_btn);
        goBack = (Button)findViewById(R.id.back_to_room_btn);

        done = (CheckBox)findViewById(R.id.wp_done_check);
        notDone = (CheckBox)findViewById(R.id.wp_not_done_check);


        setMaterialTxt();
        setToolsTxt();
        setPreWorkTxt();
        setCommentFromBeforeTxt();


    }
    //Hämtar material från databasen och lägger i TextView Material
    private void setMaterialTxt (){
        Intent activityThatCalled = getIntent();
        WPsent = activityThatCalled.getExtras().getString("workPackageID");
        material.append(WPsent);

    }
    //Hämtar verktyg från databasen och lägger i TextView Tools
    private void setToolsTxt(){


    }
    //Hämtar arbetsberedning från databasen och lägger i TextView preWork
    private void setPreWorkTxt(){



    }
    //Hämtar kommentarer från databasen och lägger i TextView commentsFromBefore
    private void setCommentFromBeforeTxt(){

       String mes = db.getMessage(WPsent);
        commentsFromBefore.append(mes);


    }
    //signerar arbetsbacketet starta en inloggningsskärm
    public void signWorkPackage(View view) {

        if(done.isChecked()) wpStatus = true;
        if(notDone.isChecked()) wpStatus = false;

        Intent getActivitySign = new Intent(this,SignActivity.class);
        getActivitySign.putExtra("wpStatus", wpStatus);
        getActivitySign.putExtra("wpID", WPsent);
        startActivityForResult(getActivitySign, SIGN_WP);

    }

    //Tillbaka till RoomActivity
    public void goBackToRoom(View view) {
        Intent backToRoomActivity = new Intent();
        setResult(RESULT_CANCELED, backToRoomActivity);
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==SIGN_WP){
            //ändra status widget till true eller false
            String userThatSign = data.getStringExtra("userID");
            boolean stat = data.getBooleanExtra("signOK", false);
            String com = comments_edit.getText().toString();
            if(stat) commentsFromBefore.append( userThatSign + " kommenterade " + com + " och satte status på " + WPsent + " till Avslutat" );

            else commentsFromBefore.append(userThatSign + " kommenterade " + com + " och satte status på " + WPsent + " till Ej Avslutat");
        }
    }
}
