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
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v1.customer.Customer;
import com.clover.sdk.v1.customer.CustomerConnector;
import com.clover.sdk.v1.printer.ReceiptContract;
import com.clover.sdk.v3.employees.PermissionSetRole;

import java.util.List;

public class CustomersActivity extends AppCompatActivity {
    //STATIC LOCAL VARS
    private Account mAcct;
    private CustomerConnector mCustConn;
    private List<Customer> customerList;

    //Method to add a new customer
    public void displayAddCustomer(View view) {
        Intent addCustomerIntent = new Intent(this, AddCustomerActivity.class);
        startActivity(addCustomerIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);
    }

    @Override
    protected void onPause() {
        disconnectCustomers();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Retrieve the Clover merchant account
        if (mAcct == null) {
            mAcct = CloverAccount.getAccount(this);

            //Break if Clover Account unreachable
            if (mAcct == null) {
                finish();
                return;
            }
        }

        //Get Customers
        connectCustomers();
        getCustomerList();
    }

    private void connectCustomers() {
        disconnectCustomers();
        if (mAcct != null) {
            mCustConn = new CustomerConnector(this, mAcct, null);
            mCustConn.connect();
        }
    }

    private void disconnectCustomers() {
        if (mCustConn != null) {
            mCustConn.disconnect();
            mCustConn = null;
        }
    }

    private void getCustomerList() {
        new AsyncTask<Void, Void, Customer>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //progress bar?
            }

            @Override
            protected Customer doInBackground(Void... params) {
                try {
                    List<Customer> customers = mCustConn.getCustomers();
                    customerList = customers;
                    return customers.get(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override

            protected void onPostExecute(Customer customer) {
                populateCustomerTable();
            }
        }.execute();
    }

    private void populateCustomerTable() {
        //Customer table vars
        TableLayout customerTable = (TableLayout) findViewById(R.id.customer_table);
        customerTable.removeAllViews();
        //Row Vars
        TableRow workingCustomerRow = new TableRow(this);
        //Line Items
        TextView custLastnameTextview = new TextView(this);
        TextView custFirstnameTextview = new TextView(this);
        TextView custPhoneTextview = new TextView(this);

        for (Customer customer : customerList) {
            workingCustomerRow.removeAllViews();
            workingCustomerRow = new TableRow(this);

            custLastnameTextview.setText(customer.getLastName());
            custFirstnameTextview.setText(customer.getFirstName());
            custPhoneTextview.setText(customer.getPhoneNumbers().toString());
            workingCustomerRow.addView(custLastnameTextview);
            workingCustomerRow.addView(custFirstnameTextview);
            workingCustomerRow.addView(custPhoneTextview);
            customerTable.addView(workingCustomerRow);
            customerTable.setStretchAllColumns(true);
        }
    }
}
