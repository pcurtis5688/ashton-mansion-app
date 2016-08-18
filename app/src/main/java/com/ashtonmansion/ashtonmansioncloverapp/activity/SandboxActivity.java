package com.ashtonmansion.ashtonmansioncloverapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.ashtonmansion.ashtonmansioncloverapp.dao.EmployeeDAO;

public class SandboxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox);
    }

    public void dropLocalEmployeeTableAndReCreate(View view) {
        EmployeeDAO employeeDAO = new EmployeeDAO(this);
        employeeDAO.dropLocalEmployeeTableIfExists();
        employeeDAO.createEmployeeTableIfNotExists();
    }

    public void insertAnEmployeeViaWS(View view){
        //TODO MAY NEED.
    }
}
