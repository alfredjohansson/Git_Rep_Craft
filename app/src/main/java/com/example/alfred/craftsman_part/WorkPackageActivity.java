package com.example.alfred.craftsman_part;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;


public class WorkPackageActivity extends Activity {

    TextView material, tools, preWork, commentsFromBefore, status;
    EditText comments_edit;
    Button sign, goBack;
    CheckBox done, notDone;
    DatabaseHelper db;
    String WPsent;
    boolean wpStatus;
    Calendar calendar;
    String itemsChecked;
    ArrayList arrayList = new ArrayList<String>();

    private final static int SIGN_WP=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_package);
        db = new DatabaseHelper(this);


        Intent intent = getIntent();
        WPsent = intent.getStringExtra("workPackageID");

        material = (TextView)findViewById(R.id.material_txt);
        tools = (TextView)findViewById(R.id.tools_txt);
        preWork = (TextView)findViewById(R.id.pre_work_txt);
        commentsFromBefore = (TextView)findViewById(R.id.al_comments_txt);
        status = (TextView)findViewById(R.id.status_txt);

        calendar = Calendar.getInstance();


        comments_edit = (EditText)findViewById(R.id.comment_edit);

        sign = (Button)findViewById(R.id.sign_wp_btn);
        goBack = (Button)findViewById(R.id.back_to_room_btn);

        //done = (CheckBox)findViewById(R.id.wp_done_check);
        //notDone = (CheckBox)findViewById(R.id.wp_not_done_check);


        setMaterialTxt();
        setToolsTxt();
        setPreWorkTxt();
        setCommentFromBeforeTxt();
        setStatus();


    }

    //Hämtar material från databasen och lägger i TextView Material
    private void setMaterialTxt (){

        String materialsDB = db.getMaterials(WPsent);
        material.append('\n' + materialsDB + '\n');

    }

    //Hämtar verktyg från databasen och lägger i TextView Tools
    private void setToolsTxt(){

        String toolsDB = db.getTools(WPsent);
        tools.append('\n' + toolsDB + '\n');


    }

    //Hämtar arbetsberedning från databasen och lägger i TextView preWork
    private void setPreWorkTxt(){
        String pre = db.getCrit(WPsent);
        preWork.append('\n' + pre + '\n');


    }

    //Hämtar kommentarer från databasen och lägger i TextView commentsFromBefore
    private void setCommentFromBeforeTxt(){

       String mes = db.getMessage(WPsent);
        commentsFromBefore.append('\n' + mes + '\n');


    }

    //signerar arbetsbacketet starta en inloggningsskärm
    public void signWorkPackage(View view) {

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

    //Visar status på AP i en textView
    public void setStatus(){

        wpStatus = db.getStatus(WPsent);

        if(wpStatus) status.append("Avslutat");
        if(!wpStatus)status.append("Ej avslutat");

    }

    public void commentWorkPackage(){

        String com = comments_edit.getText().toString();
        commentsFromBefore.append(com);
        comments_edit.setText("");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==SIGN_WP){
            //ändra status widget till true eller false
            String userThatSign = data.getStringExtra("userID");
            arrayList = data.getCharSequenceArrayListExtra("signOK");

            commentsFromBefore.append('\n' + "Kontroller utförda:" + '\n');

            for(int i = 0; i<arrayList.size(); i++){

               commentsFromBefore.append(arrayList.get(i).toString() + '\n');
            }



            int date = calendar.get(Calendar.DATE);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            commentsFromBefore.append('\n' + userThatSign + " avslutade arbetet " + date + "/" + month + " " + year);
        }
    }

    public void kommentera(View view) {

        commentWorkPackage();
    }

}
