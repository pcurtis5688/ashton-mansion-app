package com.ashtonmansion.ashtonmansioncloverapp.activity.schedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import com.ashtonmansion.ashtonmansioncloverapp.R;

public class EmpScheduleActivity extends AppCompatActivity {
    private Spinner chosenEmployeeSpinner = new Spinner(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empl_schedule);

        chosenEmployeeSpinner = (Spinner) findViewById(R.id.chosen_empl_spinner);



    }

    protected void populateEmployeeSpinner(){

    }



}
