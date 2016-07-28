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
    //Local View Vars
    private TableLayout employeeTable;
    TableRow workingEmployeeRow;
    TextView empIdTextview;
    TextView empNameTextview;
    TextView empNicknameTextView;
    TextView empEmailTextview;
    TextView empRoleTextview;

    //Method to open the add employee activity/screen
    public void displayAddEmployee(View view) {
        Intent addEmployeeIntent = new Intent(this, AddEmployeeActivity.class);
        startActivity(addEmployeeIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);

        employeeTable = (TableLayout) findViewById(R.id.employee_table);
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
        //Clear the employee table if has content
        employeeTable.removeAllViews();
        //create the header row for the employee table
        //TODO this
        createEmployeeTableHeaderRow();

        for (Employee currentEmp : employeeList) {
            //Create new row to add to table
            workingEmployeeRow = new TableRow(this);
            //Create new views to populate data
            empIdTextview = new TextView(this);
            empNameTextview = new TextView(this);
            empNicknameTextView = new TextView(this);
            empEmailTextview = new TextView(this);
            empRoleTextview = new TextView(this);
            //Set the new data for the new row
            empIdTextview.setText(currentEmp.getId());
            empNameTextview.setText(currentEmp.getName());
            empNicknameTextView.setText(currentEmp.getNickname());
            empEmailTextview.setText(currentEmp.getEmail());
            empRoleTextview.setText(currentEmp.getRole().name());
            //Add the new data to the new row
            workingEmployeeRow.addView(empIdTextview);
            workingEmployeeRow.addView(empNameTextview);
            workingEmployeeRow.addView(empNicknameTextView);
            workingEmployeeRow.addView(empEmailTextview);
            workingEmployeeRow.addView(empRoleTextview);
            //Add the new row to the table
            employeeTable.addView(workingEmployeeRow);
        }
    }

    private void createEmployeeTableHeaderRow() {
        //Todo this
    }
}
