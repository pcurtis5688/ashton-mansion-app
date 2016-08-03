package com.ashtonmansion.ashtonmansioncloverapp.activity.employee;

import android.accounts.Account;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v3.employees.Employee;
import com.clover.sdk.v3.employees.EmployeeConnector;


public class AddEmployeeActivity extends AppCompatActivity {
    //SERVICE VARS
    private Account mAcct;
    private EmployeeConnector employeeConnector;
    private Employee newEmployee;
    //PRIVATE FIELD VARS
    private EditText employeeName;
    private EditText employeeNickname;
    private Spinner employeeRoleSpinner;
    private EditText employeePIN;
    private EditText employeeEmail;

    //Submit employee for creation
    public void submitNewEmployee(View view) {
        newEmployee = new Employee();
        newEmployee.setName(employeeName.getText().toString());
        newEmployee.setNickname(employeeNickname.getText().toString());
        //role!?!?!?
        newEmployee.setPin(employeePIN.getText().toString());
        newEmployee.setEmail(employeeEmail.getText().toString());

        insertEmployee();
    }

    private void insertEmployee() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //TODO PROGRESS PBAR
            }

            @Override
            protected Void doInBackground(Void... params) {
                getMerchantAcct();
                connectEmployeeConn();
                try {
                    employeeConnector.createEmployee(newEmployee);
                } catch (RemoteException | ServiceException | ClientException | BindingException e) {
                    Log.i("Employee Creation Error", e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                //TODO PROGRESS PBAR
                finish();
            }
        }.execute();
    }

    private void getMerchantAcct() {
        if (mAcct == null) {
            mAcct = CloverAccount.getAccount(this);
            if (mAcct == null) {
                //BREAK IF CLOVER ACCOUNT UNREACHABLE
                finish();
            }
        }
    }


    private void connectEmployeeConn() {
        disconnectEmployeeConn();
        if (mAcct != null) {
            employeeConnector = new EmployeeConnector(this, mAcct, null);
            employeeConnector.connect();
        }
    }

    private void disconnectEmployeeConn() {
        if (employeeConnector != null) {
            employeeConnector.disconnect();
            employeeConnector = null;
        }
    }

    //RETURN WHEN CANCEL BUTTON PRESSED
    public void cancelAddEmployee(View view) {
        finish();
    }

    //ACTIVITY FLOW METHODS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        employeeName = (EditText) findViewById(R.id.add_employee_name);
        employeeNickname = (EditText) findViewById(R.id.add_employee_nickname);
        employeeRoleSpinner = (Spinner) findViewById(R.id.add_employee_role_spinner);
        employeePIN = (EditText) findViewById(R.id.add_employee_pin);
        employeeEmail = (EditText) findViewById(R.id.add_employee_email);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
