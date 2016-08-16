package com.ashtonmansion.ashtonmansioncloverapp.activity.customer;

import com.ashtonmansion.ashtonmansioncloverapp.R;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.clover.sdk.v1.customer.CustomerConnector;
import com.clover.sdk.v1.customer.Customer;
import com.clover.sdk.util.CloverAccount;

import java.util.List;

public class CustomersActivity extends AppCompatActivity {
    //Activity vars
    private Context customersActivityContext;
    //STATIC LOCAL VARS
    private Account mAcct;
    private CustomerConnector customerConnector;
    private List<Customer> customers;
    //TABLE VARIABLES
    private TableLayout customerTable;
    private TableRow customerTableHeaderRow;
    private TableRow newCustomerRow;
    private TextView custLastnameTextview;
    private TextView custFirstnameTextview;
    private TextView custPhoneTextview;

    private void getCustomerListAndPopulateTable() {
        new AsyncTask<Void, Void, Void>() {
            ProgressDialog progressDialog = new ProgressDialog(customersActivityContext);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage("Loading Customers...");
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    getMerchantAccount();
                    connectCustomerConn();
                    customers = customerConnector.getCustomers();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                populateCustomerTable();
                disconnectCustomerConn();
                super.onPostExecute(result);
                progressDialog.dismiss();
            }
        }.execute();
    }

    private void populateCustomerTable() {
        //Clear the table data
        customerTable.removeAllViews();
        //Create the header row for the employee table
        createCustomerTableHeaderRow();

        if (customers != null && customers.size() != 0) {
            for (final Customer customer : customers) {
                //Create new row to be added to the table
                newCustomerRow = new TableRow(this);
                newCustomerRow.setPadding(0, 50, 0, 50);
                newCustomerRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editThisCustomer(customer);
                    }
                });
                //Create the new views for the new row
                custLastnameTextview = new TextView(this);
                custFirstnameTextview = new TextView(this);
                custPhoneTextview = new TextView(this);
                Button deleteCustomerButton = new Button(customersActivityContext);
                deleteCustomerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteThisCustomer(customer);
                    }
                });
                //Set the new data for the new row
                custLastnameTextview.setText(customer.getLastName());
                custFirstnameTextview.setText(customer.getFirstName());
                custPhoneTextview.setText(customer.getPhoneNumbers().toString());
                deleteCustomerButton.setText("Delete Customer");
                //Add the new data to the new row
                newCustomerRow.addView(custLastnameTextview);
                newCustomerRow.addView(custFirstnameTextview);
                newCustomerRow.addView(custPhoneTextview);
                newCustomerRow.addView(deleteCustomerButton);
                //Add the new row to the table
                customerTable.addView(newCustomerRow);
            }
        } else {
            TableRow noCustomerDataRow = new TableRow(customersActivityContext);
            TextView noCustomerDataTV = new TextView(customersActivityContext);
            //todo string resource for below
            noCustomerDataTV.setText("No Customer Data! Please Add");
            noCustomerDataRow.addView(noCustomerDataTV);
            customerTable.addView(noCustomerDataRow);
        }
    }

    private void createCustomerTableHeaderRow() {
        //TODO THIS
    }

    private void deleteThisCustomer(Customer customer) {
        //TODO THIS
    }

    private void editThisCustomer(Customer customer) {
        Intent editCustomerIntent = new Intent(this, EditCustomerActivity.class);
        editCustomerIntent.putExtra("customer", customer);
        startActivity(editCustomerIntent);
    }

    private void getMerchantAccount() {
        if (mAcct == null) {
            mAcct = CloverAccount.getAccount(this);

            //Break if Clover Account unreachable
            if (mAcct == null) {
                finish();
                return;
            }
        }
    }

    private void connectCustomerConn() {
        disconnectCustomerConn();
        if (mAcct != null) {
            customerConnector = new CustomerConnector(this, mAcct, null);
            customerConnector.connect();
        }
    }

    private void disconnectCustomerConn() {
        if (customerConnector != null) {
            customerConnector.disconnect();
            customerConnector = null;
        }
    }

    public void displayAddCustomer(View view) {
        Intent addCustomerIntent = new Intent(this, AddCustomerActivity.class);
        startActivity(addCustomerIntent);
    }

    /////////////////ACTIVITY FLOW METHODS ///////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);
        customersActivityContext = this;
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

        getCustomerListAndPopulateTable();
    }
}
