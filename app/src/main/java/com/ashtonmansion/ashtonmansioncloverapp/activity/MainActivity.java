package com.ashtonmansion.ashtonmansioncloverapp.activity;

import android.accounts.Account;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.ashtonmansion.ashtonmansioncloverapp.activity.appointment.AppointmentsActivity;
import com.ashtonmansion.ashtonmansioncloverapp.activity.customer.CustomersActivity;
import com.ashtonmansion.ashtonmansioncloverapp.activity.employee.EmployeesActivity;
import com.ashtonmansion.ashtonmansioncloverapp.activity.item.InventoryActivity;
import com.ashtonmansion.ashtonmansioncloverapp.activity.management.ManagerMainActivity;
import com.ashtonmansion.ashtonmansioncloverapp.utility.DBStartupUtility;
import com.clover.sdk.util.CloverAccount;

public class MainActivity extends AppCompatActivity {
    private Account mAcct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBStartupUtility dbStartupUtility = new DBStartupUtility(this);
        dbStartupUtility.checkDatabase(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getMerchantAccount();
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

    //Method to enter the sandbox
    public void showSandboxPage(View view){
        Intent sandboxPageIntent = new Intent(this, SandboxActivity.class);
        startActivity(sandboxPageIntent);
    }
    //Method to enter the sandbox
    public void showManagementPage(View view){
        Intent mainManagerIntent = new Intent(this, ManagerMainActivity.class);
        startActivity(mainManagerIntent);
    }

    private void getMerchantAccount() {
        if (mAcct == null) {
            mAcct = CloverAccount.getAccount(this);

            if (mAcct == null) {
                return;
            }
        }
    }
}