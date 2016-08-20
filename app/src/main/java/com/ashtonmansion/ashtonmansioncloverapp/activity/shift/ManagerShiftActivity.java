package com.ashtonmansion.ashtonmansioncloverapp.activity.shift;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.clover.sdk.v3.employees.Employee;

public class ManagerShiftActivity extends AppCompatActivity {
    private Employee currentEmp = new Employee();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_management);

        //TODO FIGURE OUT HOW TO GET LOGGED IN USER ID AND POPULATE THIS HERE
    }

    public void displayShiftsActivity(View view) {
        Intent shiftsActivityIntent = new Intent(this, ShiftsActivity.class);
        startActivity(shiftsActivityIntent);
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
