package com.example.alfred.craftsman_part;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        userName = (EditText)findViewById(R.id.user_name_edit);
        userPassword = (EditText)findViewById(R.id.user_pw_edit);
        errorText = (TextView)findViewById(R.id.sign_error_txt);
        sign = (Button)findViewById(R.id.sign_wp_btn);
        db = new DatabaseHelper(this);

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

        db.changeStatus(WP, userN, wpStatus);

        /*//Kollar så att användaren finns i databasen true om användaren finns
        boolean usr = db.getUserNameExist(userN);

        if(usr){
            //finns användaren så kolla så deras PW är samma som det som skrevs in i PW edit text
            if(userPw.equals(db.getUserNamePassword)){

            }
            //annars skriv ut i errorText att det är fel
            else {
                errorText.setText("Fel användarnamn eller lösenord!");
                return;
            }
            //ifall användaren inte finns ska error skrivas ut i errorText också
        } else {
            errorText.setText("Fel användarnamn eller lösenord!");
            return;
        }*/

        Intent goBack = new Intent();
        goBack.putExtra("signOK", wpStatus);
        goBack.putExtra("userID", userN);
        setResult(RESULT_OK, goBack);
        finish();


    }
}
