package com.ashtonmansion.ashtonmansionschedulingapp.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.ashtonmansion.ashtonmansionschedulingapp.R;
import com.ashtonmansion.ashtonmansionschedulingapp.dao.EmployeeDAO;
import com.ashtonmansion.ashtonmansionschedulingapp.dbo.Employee;


public class AddEmployeeActivity extends AppCompatActivity {
    //Activity Context
    Context context;
    //PRIVATE VARS
    private Employee newEmployee;
    private EmployeeDAO employeeDAO;
    private EditText empName;
    private EditText empNickname;
    private Spinner empRole;
    private EditText empLoginPIN;
    private EditText empEmail;


    //Activity flow methods
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

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //public methods
    //Submit employee for creation
    public void submitNewEmployee(View view) {
        newEmployee = new Employee();
        newEmployee.set_name(empName.getText().toString());
        newEmployee.set_nickname(empNickname.getText().toString());
        newEmployee.set_role(empRole.getSelectedItem().toString());
        newEmployee.set_loginPIN(empLoginPIN.getText().toString());
        newEmployee.set_email(empEmail.getText().toString());

        new insertEmployeeServiceCall().execute();
    }

    private class insertEmployeeServiceCall extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            //Progress bar for insertion
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            employeeDAO = new EmployeeDAO(context);

            //TODO IMPLEMENT THIS METHOD
            employeeDAO.addEmployee(newEmployee, context);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //TODO PROGRESS BAR
        }
    }


    //Method to return when cancel button is pressed
    public void cancelAddEmployee(View view) {
        finish();
    }
}
