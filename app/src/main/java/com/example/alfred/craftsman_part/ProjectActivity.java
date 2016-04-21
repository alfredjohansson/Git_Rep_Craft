package com.example.alfred.craftsman_part;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class ProjectActivity extends Activity{


    private final static int RESULT_OK = 1;
    private final static int RESULT_NOT_OK = 0;


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


    }

    public void sendBackValue(){

        String projectId = projectId_edit.getText().toString();
        int id = Integer.parseInt(projectId);



        //Här ska ID kollas mot databasen för att se så det finns
        /*Try {

            dataBase.CheckID(id);

            catch (exeption e){
                error.setText(e);

            }

        }
            //Ifall ID finns så ska activiteten returnera ID eller 0 ifall det inte hittade något
         */

        //Intent activityThatCalled = getIntent();



            Intent getMainActivity = new Intent();

            getMainActivity.putExtra("projectID", id);

            setResult(RESULT_OK, getMainActivity);

            finish();


        }

    public void onChoose(View view) {

        sendBackValue();
    }
}
