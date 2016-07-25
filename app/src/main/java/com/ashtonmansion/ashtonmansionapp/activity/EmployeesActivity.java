package com.ashtonmansion.ashtonmansionapp.activity;

import android.accounts.Account;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ashtonmansion.ashtonmansionapp.R;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.ResultStatus;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v1.printer.ReceiptContract;
import com.clover.sdk.v3.employees.Employee;
import com.clover.sdk.v3.employees.EmployeeConnector;

import java.util.ArrayList;
import java.util.List;

public class EmployeesActivity extends AppCompatActivity {
    //STATIC LOCAL VARS
    private Account mAcct;
    private EmployeeConnector mEmpConn;
    private List<Employee> employeeList;

    //Method to open the add employee activity/screen
    private void displayAddEmployee(View view) {
        Intent addEmployeeIntent = new Intent(this, AddEmployeeActivity.class);
        startActivity(addEmployeeIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);
    }

    @Override
    protected void onPause() {
        disconnectEmployees();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Retrieve the Clover merchant account
        if (mAcct == null) {
            mAcct = CloverAccount.getAccount(this);

            //Return if the Clover Account unreachable
            if (mAcct == null) {
                finish();
                return;
            }
        }

        //Connect emp connector
        connectEmployees();
        getEmployeeList();
    }

    private void connectEmployees() {
        disconnectEmployees();
        if (mAcct != null) {

            mEmpConn = new EmployeeConnector(this, mAcct, null);
            mEmpConn.connect();
        }
    }

    private void disconnectEmployees() {
        if (mEmpConn != null) {
            mEmpConn.disconnect();
            mEmpConn = null;
        }
    }

    private void getEmployeeList() {
        new AsyncTask<Void, Void, Employee>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //can show progress bar here
            }

            @Override
            protected Employee doInBackground(Void... params) {
                try {
                    List<Employee> employees = mEmpConn.getEmployees();
                    employeeList = employees;
                    return employees.get(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Employee employee) {
                populateEmployeeTable();
            }
        }.execute();
    }


    private void populateEmployeeTable() {
        //Employee table vars
        TableLayout employeeTable = (TableLayout) findViewById(R.id.employee_table);
        TableRow workingEmployeeRow = new TableRow(this);

        TextView empIdTextview = new TextView(this);
        TextView empNameTextview = new TextView(this);
        TextView empNicknameTextView = new TextView(this);
        TextView empEmailTextview = new TextView(this);
        TextView empRoleTextview = new TextView(this);

        for (Employee currentEmp : employeeList) {
            empIdTextview.setText(currentEmp.getId());
            empNicknameTextView.setText(currentEmp.getNickname());
            empEmailTextview.setText(currentEmp.getEmail());
            empRoleTextview.setText(currentEmp.getRole().name());
            workingEmployeeRow.addView(empIdTextview);
            workingEmployeeRow.addView(empNameTextview);
            workingEmployeeRow.addView(empNicknameTextView);
            workingEmployeeRow.addView(empEmailTextview);
            workingEmployeeRow.addView(empRoleTextview);
            employeeTable.addView(workingEmployeeRow);
        }
        //TODO FINISH THE TABLE IMPLEMENTATIONS

    }
}
