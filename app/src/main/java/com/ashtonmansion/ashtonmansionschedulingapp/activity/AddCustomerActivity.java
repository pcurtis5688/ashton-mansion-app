package com.ashtonmansion.ashtonmansionschedulingapp.activity;

import android.accounts.Account;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.ashtonmansion.ashtonmansionschedulingapp.R;
import com.ashtonmansion.ashtonmansionschedulingapp.dao.CustomerDAO;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v1.customer.CustomerConnector;
import com.clover.sdk.v3.customers.Address;
import com.clover.sdk.v3.customers.Customer;
import com.clover.sdk.v3.customers.EmailAddress;
import com.clover.sdk.v3.customers.PhoneNumber;

import java.util.ArrayList;
import java.util.List;

public class AddCustomerActivity extends AppCompatActivity {
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

    //Submit customer for creation
    public void submitAddCustomer(View view) {
        newCustomer = new Customer();
        //FIRST AND LAST
        newCustomer.setFirstName(customerFirst.getText().toString());
        newCustomer.setLastName(customerLast.getText().toString());
        newCustomer.setMarketingAllowed(customerMarketingAllowedChkbox.isChecked());
        //NEW CUSTOMER PHONE NUMBER
        PhoneNumber newCustomerPhoneNumber = new PhoneNumber();
        newCustomerPhoneNumber.setPhoneNumber(customerPhone.getText().toString());
        List<PhoneNumber> phoneNumberList = new ArrayList<PhoneNumber>();
        phoneNumberList.add(newCustomerPhoneNumber);
        newCustomer.setPhoneNumbers(phoneNumberList);
        //NEW CUSTOMER EMAIL
        EmailAddress newCustomerEmailAddress = new EmailAddress();
        newCustomerEmailAddress.setEmailAddress(customerEmail.getText().toString());
        List<EmailAddress> emailAddressList = new ArrayList<EmailAddress>();
        emailAddressList.add(newCustomerEmailAddress);
        newCustomer.setEmailAddresses(emailAddressList);
        //NEW CUSTOMER ADDRESS
        Address newCustomerAddress = new Address();
        newCustomerAddress.setAddress1(customerAddress1.getText().toString());
        newCustomerAddress.setAddress2(customerAddress2.getText().toString());
        newCustomerAddress.setAddress3(customerAddress3.getText().toString());
        newCustomerAddress.setCity(customerCity.getText().toString());
        newCustomerAddress.setState(customerStateSpinner.getSelectedItem().toString());
        newCustomerAddress.setZip(customerZip.getText().toString());
        List<Address> addressList = new ArrayList<>();
        addressList.add(newCustomerAddress);
        newCustomer.setAddresses(addressList);

        insertCustomer();
    }

    private void insertCustomer() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //TODO PROGRESS PBAR
            }

            @Override
            protected Void doInBackground(Void... params) {
                getMerchantAcct();
                connectCustomerConn();
                try {
                    customerConnector.createCustomer(newCustomer.getFirstName(), newCustomer.getLastName(), newCustomer.getMarketingAllowed());
                    //TODO GET CUST ID AND SET THE OTHER FIELDS
                } catch (RemoteException | ServiceException | ClientException | BindingException e) {
                    Log.i("Customer Creation Error", e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                //TODO PROGRESS PBAR
                finish();
            }
        }.execute();
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

    //RETURN WHEN CANCEL BUTTON PRESSED
    public void cancelAddCustomer(View view) {
        finish();
    }

    //ACTIVITY FLOW METHODS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

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
}
