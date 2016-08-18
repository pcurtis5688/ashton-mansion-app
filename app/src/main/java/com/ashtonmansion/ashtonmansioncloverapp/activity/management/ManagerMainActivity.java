package com.ashtonmansion.ashtonmansioncloverapp.activity.management;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.ashtonmansion.ashtonmansioncloverapp.activity.shift.ManagerShiftActivity;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v3.employees.Employee;
import com.clover.sdk.v3.employees.EmployeeConnector;

import java.util.List;

public class ManagerMainActivity extends AppCompatActivity {
    //Activity vars
    private Context managerMainActivityContext;
    //STATIC LOCAL VARS
    private Account mAcct;
    private EmployeeConnector mEmplConn;
    private List<Employee> employeeList;
    private final String managerID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getMerchantAccount() {
        //Retrieve the Clover merchant account
        if (mAcct == null) {
            mAcct = CloverAccount.getAccount(this);

            //Break if Clover Account unreachable
            if (mAcct == null) {
                finish();
                return;
            }
        }
    }

    private void connectEmployeeConn() {
        disconnectEmployeeConn();
        if (mAcct != null) {

            mEmplConn = new EmployeeConnector(this, mAcct, null);
            mEmplConn.connect();
        }
    }

    private void disconnectEmployeeConn() {
        if (mEmplConn != null) {
            mEmplConn.disconnect();
            mEmplConn = null;
        }
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
                    employeeList = mEmplConn.getEmployees();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return employeeList;
            }

            @Override
            protected void onPostExecute(List<Employee> employeeList) {

            }
        }.execute();
    }

    public void showManagerShiftPage(View view) {
        Intent managerShiftIntent = new Intent(this, ManagerShiftActivity.class);
        startActivity(managerShiftIntent);
    }

    public static void editEmployeeShiftActivity() {
        //TODO COMPLETE IMPLEMENTATION
    }
}
