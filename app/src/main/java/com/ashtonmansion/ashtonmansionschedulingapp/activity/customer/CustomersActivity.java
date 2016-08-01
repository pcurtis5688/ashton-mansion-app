package com.ashtonmansion.ashtonmansionschedulingapp.activity.customer;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ashtonmansion.ashtonmansionschedulingapp.R;
import com.ashtonmansion.ashtonmansionschedulingapp.activity.customer.AddCustomerActivity;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.customer.Customer;
import com.clover.sdk.v1.customer.CustomerConnector;

import java.util.List;

public class CustomersActivity extends AppCompatActivity {
    //STATIC LOCAL VARS
    private Account mAcct;
    private CustomerConnector mCustConn;
    private List<Customer> customerList;
    //Activity view vars
    private TableLayout customerTable;
    private TableRow customerTableHeaderRow;
    private TableRow newCustomerRow;
    private TextView custLastnameTextview;
    private TextView custFirstnameTextview;
    private TextView custPhoneTextview;

    //Method to add a new customer
    public void displayAddCustomer(View view) {
        Intent addCustomerIntent = new Intent(this, AddCustomerActivity.class);
        startActivity(addCustomerIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        customerTable = (TableLayout) findViewById(R.id.customer_table);
    }

    @Override
    protected void onPause() {
        disconnectCustomerConn();
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
        connectCustomerConn();
        getCustomerList();
    }

    private void connectCustomerConn() {
        disconnectCustomerConn();
        if (mAcct != null) {
            mCustConn = new CustomerConnector(this, mAcct, null);
            mCustConn.connect();
        }
    }

    private void disconnectCustomerConn() {
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
                disconnectCustomerConn();
            }
        }.execute();
    }

    private void populateCustomerTable() {
        //Clear the table data
        customerTable.removeAllViews();
        //Create the header row for the employee table
        //TODO ABOVE
        createCustomerTableHeaderRow();

        for (Customer customer : customerList) {
            //Create new row to be added to the table
            final String customerId = customer.getId();
            newCustomerRow = new TableRow(this);
            newCustomerRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickedThisID(customerId);
                }
            });
            //Create the new views for the new row
            custLastnameTextview = new TextView(this);
            custFirstnameTextview = new TextView(this);
            custPhoneTextview = new TextView(this);
            //Set the new data for the new row
            custLastnameTextview.setText(customer.getLastName());
            custFirstnameTextview.setText(customer.getFirstName());
            custPhoneTextview.setText(customer.getPhoneNumbers().toString());
            //Add the new data to the new row
            newCustomerRow.addView(custLastnameTextview);
            newCustomerRow.addView(custFirstnameTextview);
            newCustomerRow.addView(custPhoneTextview);
            //Add the new row to the table
            customerTable.addView(newCustomerRow);
        }
    }

    private void createCustomerTableHeaderRow() {
        //TODO THIS
    }

    public void clickedThisID(String id) {
        Context context = getApplicationContext();
        CharSequence text = "Hello customer " + id + "!!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
