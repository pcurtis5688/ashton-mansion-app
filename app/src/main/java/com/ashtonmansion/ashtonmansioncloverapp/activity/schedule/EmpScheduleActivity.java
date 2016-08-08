package com.ashtonmansion.ashtonmansioncloverapp.activity.schedule;

import android.accounts.Account;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.clover.sdk.v3.employees.Employee;
import com.clover.sdk.v3.employees.EmployeeConnector;

import java.util.ArrayList;
import java.util.List;

public class EmpScheduleActivity extends AppCompatActivity {
    //DATA FIELDS
    private Account mAcct;
    private EmployeeConnector mEmplConn;

    private List<Employee> employeeList;
    //UI FIELDS
    private Spinner chosenEmployeeSpinner = new Spinner(this);
    private ArrayAdapter<String> chosenEmployeeSpinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empl_schedule);

        chosenEmployeeSpinner = (Spinner) findViewById(R.id.chosen_empl_spinner);
    }

    private void getEmployeeList() {
        new AsyncTask<Void, Void, List<Employee>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //can show progress bar here
            }

            @Override
            protected List<Employee> doInBackground(Void... params) {
                try {
                    List<Employee> employees = mEmplConn.getEmployees();
                    return employees;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<Employee> employees) {
                employeeList = employees;
                populateEmployeeSpinner();
            }
        }.execute();
    }

    private void connectEmployeeConnector() {
        disconnectEmployeeConnector();
        if (mAcct != null) {
            mEmplConn = new EmployeeConnector(this, mAcct, null);
            mEmplConn.connect();
        }
    }

    private void disconnectEmployeeConnector() {
        if (mEmplConn != null) {
            mEmplConn.disconnect();
            mEmplConn = null;
        }
    }

    private void populateEmployeeSpinner() {
        List<String> chosenEmplSpinnerList = new ArrayList<String>();
        for (Employee employee : employeeList) {
            chosenEmplSpinnerList.add(employee.getName());
        }
        chosenEmployeeSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, chosenEmplSpinnerList);

    }
}
