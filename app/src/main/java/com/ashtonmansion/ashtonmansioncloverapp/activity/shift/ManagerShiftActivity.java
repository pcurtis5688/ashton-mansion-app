package com.ashtonmansion.ashtonmansioncloverapp.activity.shift;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ashtonmansion.ashtonmansioncloverapp.R;

public class ManagerShiftActivity extends AppCompatActivity {
    private String managerID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_shift);

        managerID = "logged in manager";
        //TODO FIGURE OUT HOW TO GET LOGGED IN USER ID AND POPULATE THIS HERE

    }

    public void displayShiftTemplateActivity(View view) {
        Intent shiftTemplateActivityIntent = new Intent(this, ShiftTemplateManagementActivity.class);
        startActivity(shiftTemplateActivityIntent);
    }

    public void displayShiftExceptionActivity(View view) {
        Intent shiftExceptionActivityIntent = new Intent(this, ShiftExceptionManagementActivity.class);
        startActivity(shiftExceptionActivityIntent);
    }
}
