package com.ashtonmansion.ashtonmansionapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.ashtonmansion.ashtonmansionapp.R;
import com.ashtonmansion.ashtonmansionapp.dao.DatabaseHandler;
import com.ashtonmansion.ashtonmansionapp.dbo.Employee;


public class AddEmployeeActivity extends AppCompatActivity {
    //PRIVATE VARS
    private Employee newEmployee;
    private DatabaseHandler dbHandler;
    private EditText empName;
    private EditText empNickname;
    private Spinner empRole;
    private EditText empLoginPIN;
    private EditText empEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        empName = (EditText) findViewById(R.id.add_employee_name);
        empNickname = (EditText) findViewById(R.id.add_employee_nickname);
        empRole = (Spinner) findViewById(R.id.add_employee_role_spinner);
        empLoginPIN = (EditText) findViewById(R.id.add_employee_pin);
        empEmail = (EditText) findViewById(R.id.add_employee_email);
    }

    //Method to finalize add employee action
    public void finalizeAddEmployee(View view) {
        dbHandler = new DatabaseHandler(this);
        newEmployee = new Employee();
        newEmployee.set_name(empName.getText().toString());
        newEmployee.set_nickname(empNickname.getText().toString());
        newEmployee.set_role(empRole.getSelectedItem().toString());
        newEmployee.set_loginPIN(Integer.parseInt(empLoginPIN.getText().toString()));
        newEmployee.set_email(empEmail.getText().toString());
        //TODO IMPLEMENT THIS METHOD
        dbHandler.addEmployee(newEmployee);
    }

    //Method to return when cancel button is pressed
    public void cancelAddEmployee(View view) {
        finish();
    }
}
