package com.ashtonmansion.ashtonmansioncloverapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.ashtonmansion.ashtonmansioncloverapp.dao.EmployeeDAO;
import com.ashtonmansion.ashtonmansioncloverapp.dao.ShiftDAO;

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

    public void dropShiftTemplateTableAndReCreate(View view) {
        ShiftDAO shiftDAO = new ShiftDAO(this);
        shiftDAO.dropShiftTemplateTable();
        shiftDAO.createShiftTemplateTable();
    }

    public void dropShiftExceptionsTableAndReCreate(View view) {
        ShiftDAO shiftDAO = new ShiftDAO(this);
        shiftDAO.dropShiftExceptionTable();
        shiftDAO.createShiftExceptionTable();
    }

}
