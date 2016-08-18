package com.ashtonmansion.ashtonmansioncloverapp.activity.customer;

import com.ashtonmansion.ashtonmansioncloverapp.R;

import android.accounts.Account;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ashtonmansion.ashtonmansioncloverapp.dao.CustomerDAO;
import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v1.customer.Address;
import com.clover.sdk.v1.customer.CustomerConnector;
import com.clover.sdk.v1.customer.Customer;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.customer.EmailAddress;
import com.clover.sdk.v1.customer.PhoneNumber;

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

    private TableRow createCustomerTableHeaderRow() {
        //MAKE THE HEADER ROW
        TableRow customerTableHeaderRow = new TableRow(customersActivityContext);
        //MAKE THE TEXTVIEWS FOR THE HEADERS
        TextView nameHeaderTV = new TextView(customersActivityContext);
        TextView phoneNumbersHeaderTV = new TextView(customersActivityContext);
        TextView emailAddressesHeaderTV = new TextView(customersActivityContext);
        TextView addressesHeaderTV = new TextView(customersActivityContext);
        //POPULATE THE HEADER TEXT
        //TODO USE STRING RESOURCES
        nameHeaderTV.setText("Name");
        phoneNumbersHeaderTV.setText("Phone Numbers");
        emailAddressesHeaderTV.setText("Email Addresses");
        addressesHeaderTV.setText("Addresses");
        //ADD THE HEADERS TO THE HEADER ROW
        customerTableHeaderRow.addView(nameHeaderTV);
        customerTableHeaderRow.addView(phoneNumbersHeaderTV);
        customerTableHeaderRow.addView(emailAddressesHeaderTV);
        customerTableHeaderRow.addView(addressesHeaderTV);
        //RETURN THE ROW
        return customerTableHeaderRow;
    }

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
        customerTable.addView(createCustomerTableHeaderRow());

        if (customers != null && customers.size() != 0) {
            for (final Customer customer : customers) {
                //CREATE THE NEW ROW AND VIEWS TO BE ADDED
                TableRow newCustomerRow = new TableRow(this);
                TextView custNameTextView = new TextView(this);
                TextView custPhoneTextView = new TextView(this);
                TextView custEmailTextView = new TextView(this);
                TextView custAddressTextView = new TextView(this);

                //HANDLE ANY FORMATTING
                String fullName = getCustomerFullName(customer);
                String formattedPhoneNumbers = getPhoneNumbersString(customer);
                String formattedEmailAddresses = getEmailAddressString(customer);
                String formattedAddresses = getAddressString(customer);

                //ADD DATA TO THE COMPONENTS
                custNameTextView.setText(fullName);
                custPhoneTextView.setText(formattedPhoneNumbers);
                custEmailTextView.setText(formattedEmailAddresses);
                custAddressTextView.setText(formattedAddresses);

                //HANDLE EDIT CLICK LISTENERS
                Button editCustomerButton = new Button(customersActivityContext);
                editCustomerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editThisCustomer(customer);
                    }
                });               //TODO USE STRING RESOURCE
                editCustomerButton.setText("Edit");
                //HANDLE THE DELETE BUTTON
                Button deleteCustomerButton = new Button(customersActivityContext);
                deleteCustomerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteThisCustomer(customer);
                    }
                });                //TODO USE STRING RESOURCE
                deleteCustomerButton.setText("Delete");

                //ADD COMPONENTS TO THE NEW ROW
                newCustomerRow.addView(custNameTextView);
                newCustomerRow.addView(custPhoneTextView);
                newCustomerRow.addView(custEmailTextView);
                newCustomerRow.addView(custAddressTextView);
                newCustomerRow.addView(editCustomerButton);
                newCustomerRow.addView(deleteCustomerButton);
                //Add the new row to the table
                customerTable.addView(newCustomerRow);
            }
        } else {
            customerTable.addView(getNoCustomerTableRow());
        }
    }

    //TODO STILL DYNAMICS AND LOCAL TO DO IN DELETECUSTOMER
    private void deleteCustomer(final String customerID) {
        new AsyncTask<Void, Void, Void>() {
            ProgressDialog progress = new ProgressDialog(customersActivityContext);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress.setMessage("Deleting...");
                progress.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                boolean customerSuccessfullyDeletedClover;
                boolean customerSuccessfullyDeletedDynamics = false;
                boolean customerSuccessfullyDeletedLocal = false;
                CustomerDAO customerDAO = new CustomerDAO(customersActivityContext);
                try {
                    connectCustomerConn();
                    customerConnector.deleteCustomer(customerID);
                    customerSuccessfullyDeletedClover = true;
                    disconnectCustomerConn();
                } catch (ServiceException | BindingException | ClientException | RemoteException e) {
                    customerSuccessfullyDeletedClover = false;
                    Log.e("Clover Exception: ", e.getMessage());
                } catch (Exception e2) {
                    customerSuccessfullyDeletedClover = false;
                    Log.e("Generic Exception: ", "" + e2.getMessage());
                }
                /////IF DELETED SUCCESSFULLY IN CLOVER
                if (customerSuccessfullyDeletedClover) {
                    /////DYNAMICS DELETION
                    //TODO DYNAMICS DELETE METHOD

                    //if (customerSuccessfullyDeletedDynamics) {
                    //TODO LOCAL DELETION METHOD IN DAO
                    //  customerSuccessfullyDeletedLocal = customerDAO.deleteLocalEmployeeRecord(employee);
                    if (!customerSuccessfullyDeletedLocal) {
                        Log.e("Local deletion err: ", "See Above...");
                    }
                    // } else {
                    //    Log.e("Dynamics Delete: ", "Failed: ");
                    //}
                } else {
                    Log.e("Clover Delete: ", "Failed: ");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                progress.dismiss();
                getCustomerListAndPopulateTable();
            }
        }.execute();
    }

    private String getCustomerFullName(Customer customer) {
        return customer.getFirstName() + " " + customer.getLastName();
    }

    private String getPhoneNumbersString(Customer customer) {
        List<PhoneNumber> phoneNumberList = customer.getPhoneNumbers();
        int numberPhoneNumbers = phoneNumberList.size();
        String phoneNumberString = "";
        if (numberPhoneNumbers == 0) {
            phoneNumberString = "No Phone Numbers";
        } else if (numberPhoneNumbers == 1) {
            phoneNumberString = phoneNumberList.get(0).getPhoneNumber();
        } else {
            for (int i = 0; i < (numberPhoneNumbers - 1); i++) {
                phoneNumberString = phoneNumberString + phoneNumberList.get(i) + ", ";
            }
            phoneNumberString = phoneNumberString + phoneNumberList.get(numberPhoneNumbers);
        }
        return phoneNumberString;
    }

    private String getEmailAddressString(Customer customer) {
        List<EmailAddress> emailAddressList = customer.getEmailAddresses();
        int numberEmailAddresses = emailAddressList.size();
        String emailAddressString = "";
        if (numberEmailAddresses == 0) {
            emailAddressString = "No Email Addresses";
        } else if (numberEmailAddresses == 1) {
            emailAddressString = emailAddressList.get(0).getEmailAddress();
        } else {
            for (int i = 0; i < (numberEmailAddresses - 1); i++) {
                emailAddressString = emailAddressString + emailAddressList.get(i) + ", ";
            }
            emailAddressString = emailAddressString + emailAddressList.get(numberEmailAddresses);
        }
        return emailAddressString;
    }

    private String getAddressString(Customer customer) {
        List<Address> addressList = customer.getAddresses();
        int numberAddresses = addressList.size();
        String addressString = "";
        if (numberAddresses == 0) {
            addressString = "No Addresses";
        } else if (numberAddresses == 1) {
            addressString = addressList.get(0).getAddress1() + " "
                    + addressList.get(0).getAddress2() + " "
                    + addressList.get(0).getAddress3();
        } else {
            for (int i = 0; i < (numberAddresses - 1); i++) {
                addressString = addressString + addressList.get(numberAddresses).getAddress1() + " "
                        + addressList.get(numberAddresses).getAddress2() + " "
                        + addressList.get(numberAddresses).getAddress3() + ", ";
            }
            addressString = addressString + addressList.get(numberAddresses).getAddress1() + " "
                    + addressList.get(numberAddresses).getAddress2() + " "
                    + addressList.get(numberAddresses).getAddress3();
        }
        return addressString;
    }

    private TableRow getNoCustomerTableRow() {
        TableRow noCustomerDataRow = new TableRow(customersActivityContext);
        TextView noCustomerDataTV = new TextView(customersActivityContext);
        //todo string resource for below
        noCustomerDataTV.setText("No Customer Data! Please Add");
        noCustomerDataRow.addView(noCustomerDataTV);
        return noCustomerDataRow;
    }

    public void displayAddCustomer(View view) {
        Intent addCustomerIntent = new Intent(this, AddCustomerActivity.class);
        startActivity(addCustomerIntent);
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

    private void deleteThisCustomer(final Customer customer) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Customer?")
                .setMessage("Delete Customer: " + getCustomerFullName(customer) + "?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String confirmationToastString = "Customer " + getCustomerFullName(customer) + " Deleted!";
                        deleteCustomer(customer.getId());
                        Toast.makeText(CustomersActivity.this, confirmationToastString, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
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
