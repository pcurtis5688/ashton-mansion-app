package com.ashtonmansion.ashtonmansionapp.activity;

import android.accounts.Account;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ashtonmansion.ashtonmansionapp.R;
import com.ashtonmansion.ashtonmansionapp.activity.AppointmentsActivity;
import com.ashtonmansion.ashtonmansionapp.activity.CustomersActivity;
import com.ashtonmansion.ashtonmansionapp.activity.EmployeesActivity;
import com.ashtonmansion.ashtonmansionapp.activity.InventoryActivity;
import com.ashtonmansion.ashtonmansionapp.activity.SettingsActivity;
import com.ashtonmansion.ashtonmansionapp.utility.WebServices;
import com.clover.sdk.util.CloverAccount;

public class MainActivity extends AppCompatActivity {

    private Account mAcct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Retrieve Clover Account
        if (mAcct == null) {
            mAcct = CloverAccount.getAccount(this);

            if (mAcct == null) {
                return;
            }
        }
    }

    //Method to View/Edit Appointments
    public void showApptPage(View view) {
        Intent activityPageIntent = new Intent(this, AppointmentsActivity.class);
        startActivity(activityPageIntent);
    }

    //Method to View/Edit Customers
    public void showCustPage(View view) {
        Intent customerPageIntent = new Intent(this, CustomersActivity.class);
        startActivity(customerPageIntent);
    }

    //Method to View/Edit Employees
    public void showEmployeePage(View view) {
        Intent employeePageIntent = new Intent(this, EmployeesActivity.class);
        startActivity(employeePageIntent);
    }

    //Method to View/Edit Inventory
    public void showInventoryPage(View view) {
        Intent inventoryPageIntent = new Intent(this, InventoryActivity.class);
        startActivity(inventoryPageIntent);
    }

    //Method to View/Edit Settings
    public void showSettingsPage(View view) {
        Intent settingsPageIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsPageIntent);
    }

    //Test method for web services
    public void testInsertAppointment(View view){
        WebServices wsTest = new WebServices();
        wsTest.execute();
    }
}