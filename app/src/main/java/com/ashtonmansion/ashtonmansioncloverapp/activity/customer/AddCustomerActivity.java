package com.ashtonmansion.ashtonmansioncloverapp.activity.customer;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.ashtonmansion.ashtonmansioncloverapp.dao.CustomerDAO;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.customerws.CustomerWebService;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v1.customer.CustomerConnector;
import com.clover.sdk.v3.customers.Address;
import com.clover.sdk.v3.customers.Customer;
import com.clover.sdk.v3.customers.EmailAddress;
import com.clover.sdk.v3.customers.PhoneNumber;
import com.clover.sdk.v3.inventory.PriceType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddCustomerActivity extends AppCompatActivity {
    //INSTANCE VARS
    private Context addCustomerContext;
    //SERVICE VARS
    private Account mAcct;
    private CustomerConnector customerConnector;
    private Customer newCustomer;
    //PRIVATE FIELD VARS
    private EditText customerFirst;
    private EditText customerLast;
    private CheckBox customerMarketingAllowedChkbox;
    private EditText customerPhone;
    private EditText customerEmail;
    private EditText customerAddress1;
    private EditText customerAddress2;
    private EditText customerAddress3;
    private EditText customerCity;
    private Spinner customerStateSpinner;
    private EditText customerZip;
    private PhoneNumber newCustomerPhoneNumber = new PhoneNumber();
    private Address newCustomerAddress = new Address();
    private EmailAddress newCustomerEmailAddress = new EmailAddress();
    private List<PhoneNumber> phoneNumberList;
    private List<EmailAddress> emailAddressList;
    private List<Address> addressList;
    /////RESULT FLAGS
    private long newlyAssignedCustomerID;
    private boolean cloverInsertSuccess;
    private boolean createCustomerWSSuccess;

    //Submit customer for creation
    public void submitAddCustomer(View view) {
        ///*CREATE CLOVER CUSTOMER INSTANCE AND SET DATA///
        createNewCustomerAndSetData();

        /////CLOVER, DYNAMICS, AND LOCAL INSERTION/////////
        doBackgroundCloverDynamicsAndLocalInsertions();

        ///////////* OUTCOME HANDLING *////////////////////
        ////////////TODO ANYTHING HERE?*//////////////////
    }

    private void doBackgroundCloverDynamicsAndLocalInsertions() {
        new AsyncTask<Void, Void, Void>() {
            ProgressDialog progress = new ProgressDialog(addCustomerContext);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                ///////////* CLOVER INSERTION *////////////////////////
                getMerchantAcct();
                connectCustomerConn();
                try {
                    for (Address currentAddress : addressList) {
                        customerConnector.addAddress(newCustomer.getId(), currentAddress.getAddress1(), currentAddress.getAddress2(), currentAddress.getAddress3(), currentAddress.getCity(), currentAddress.getState(), currentAddress.getZip());
                    }
                    for (PhoneNumber phoneNumber : phoneNumberList) {
                        customerConnector.addPhoneNumber(newCustomer.getId(), phoneNumber.getPhoneNumber());
                    }
                    for (EmailAddress emailAddress : emailAddressList) {
                        customerConnector.addEmailAddress(newCustomer.getId(), emailAddress.getEmailAddress());
                    }
                    customerConnector.createCustomer((newCustomer.getFirstName().toString()), (newCustomer.getLastName().toString()), (newCustomer.getMarketingAllowed()));
                    //TODO GET CUST ID AND SET THE OTHER FIELDS
                    cloverInsertSuccess = true;
                } catch (RemoteException | ServiceException | ClientException | BindingException e) {
                    cloverInsertSuccess = true;
                    Log.e("Clover Exception: ", e.getMessage());
                } catch (Exception e2) {
                    cloverInsertSuccess = true;
                    Log.e("Generic Exception: ", e2.getMessage());
                } finally {
                    customerConnector.disconnect();
                    Log.i("Cust Clover Success: ", "" + cloverInsertSuccess);
                }

                //////ONLY CONTINUE IF CLOVER INSERT SUCCESSFUL//////
                if (cloverInsertSuccess == true) {
                    ///////////* DYNAMICS INSERTION *////////////////////////
                    //TODO DYNAMICS AND LOCAL ADD
                    CustomerWebService customerWebService = new CustomerWebService();
                    createCustomerWSSuccess = customerWebService.createCustomerServiceCall(newCustomer);

                    ////ONLY CONTINUE IF DYNAMICS INSERT SUCCESSFUL??////
                    if (createCustomerWSSuccess == true) {
                        ///////////* LOCAL INSERTION *////////////////////////
                        try {
                            CustomerDAO customerDAO = new CustomerDAO(addCustomerContext);
                            newlyAssignedCustomerID = customerDAO.insertLocalCustomerRecord(newCustomer);
                        } catch (Exception e) {
                            Log.e("Generic Exception: ", "Local insertion customer: " + e.getMessage());
                        }
                        if (newlyAssignedCustomerID == -1) {
                            Log.e("Exception in SQLite: ", "See Above");
                        } else {
                            Log.i("New LOCAL Customer ID: ", "" + newlyAssignedCustomerID);
                        }
                    } else {
                        Log.e("Cust WS Call: ", "failed");
                    }
                } else {
                    Log.e("Break;CloverFail: ", "See Exception above");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                progress.dismiss();
                finish();
            }
        }.execute();
    }

    //* ACTIVITY FLOW METHODS *//////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        addCustomerContext = this;
        customerFirst = (EditText) findViewById(R.id.customer_first_name);
        customerLast = (EditText) findViewById(R.id.customer_last_name);
        customerMarketingAllowedChkbox = (CheckBox) findViewById(R.id.customer_marketing_allowed_chkbox);
        customerPhone = (EditText) findViewById(R.id.customer_phone);
        customerEmail = (EditText) findViewById(R.id.customer_email);
        customerAddress1 = (EditText) findViewById(R.id.customer_address_1);
        customerAddress2 = (EditText) findViewById(R.id.customer_address_2);
        customerAddress3 = (EditText) findViewById(R.id.customer_address_3);
        customerCity = (EditText) findViewById(R.id.customer_city);
        customerStateSpinner = (Spinner) findViewById(R.id.customer_state_spinner);
        customerZip = (EditText) findViewById(R.id.customer_zip);
    }

    @Override
    protected void onPause() {
        disconnectCustomerConn();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /*THESE METHODS ARE COMPLETE*////////////////////
    private void createNewCustomerAndSetData() {
        emailAddressList = new ArrayList<>();
        phoneNumberList = new ArrayList<>();
        addressList = new ArrayList<>();
        newCustomer = new Customer().setFirstName(customerFirst.getText().toString());
        newCustomer.setLastName(customerLast.getText().toString());
        newCustomer.setMarketingAllowed(customerMarketingAllowedChkbox.isChecked());

        //NEW CUSTOMER PHONE NUMBER
        newCustomerPhoneNumber.setPhoneNumber(customerPhone.getText().toString());
        phoneNumberList.add(newCustomerPhoneNumber);
        newCustomer.setPhoneNumbers(phoneNumberList);

        //NEW CUSTOMER EMAIL
        newCustomerEmailAddress.setEmailAddress(customerEmail.getText().toString());
        emailAddressList = new ArrayList<>();
        emailAddressList.add(newCustomerEmailAddress);
        newCustomer.setEmailAddresses(emailAddressList);

        //NEW CUSTOMER ADDRESS
        newCustomerAddress.setAddress1(customerAddress1.getText().toString());
        newCustomerAddress.setAddress2(customerAddress2.getText().toString());
        newCustomerAddress.setAddress3(customerAddress3.getText().toString());
        newCustomerAddress.setCity(customerCity.getText().toString());
        newCustomerAddress.setState(customerStateSpinner.getSelectedItem().toString());
        newCustomerAddress.setZip(customerZip.getText().toString());
        addressList = new ArrayList<>();
        addressList.add(newCustomerAddress);
        newCustomer.setAddresses(addressList);
    }

    private void getMerchantAcct() {
        if (mAcct == null) {
            mAcct = CloverAccount.getAccount(this);
            if (mAcct == null) {
                //BREAK IF CLOVER ACCOUNT UNREACHABLE
                finish();
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

    public void cancelAddCustomer(View view) {
        finish();
    }
}
