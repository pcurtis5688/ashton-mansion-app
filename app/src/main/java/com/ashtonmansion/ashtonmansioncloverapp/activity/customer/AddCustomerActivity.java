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

import java.util.ArrayList;
import java.util.List;

public class AddCustomerActivity extends AppCompatActivity {
    //CLOVER-SERVICE VARS
    private Account mAcct;
    private CustomerConnector customerConnector;
    //PRIVATE FIELD VARS / INSTANCE VARS
    private Context addCustomerContext;
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

    public void submitAddCustomer(View view) {
        ///*'BUILD' NEW CLOVER CUSTOMER INSTANCE AND SET DATA///
        Customer newCustomer = createNewCustomerAndSetData();
        /////CLOVER, DYNAMICS, AND LOCAL INSERTION/////////
        doBackgroundCloverDynamicsAndLocalInsertions(newCustomer);
        ///////////*TODO OUTCOME HANDLING ??*////////////////////
    }

    private void doBackgroundCloverDynamicsAndLocalInsertions(final Customer newCustomer) {
        new AsyncTask<Void, Void, Void>() {
            ProgressDialog progress = new ProgressDialog(addCustomerContext);
            /////RESULTING FLAGS / CODES
            private boolean cloverInsertSuccess;
            private boolean createCustomerWSSuccess;
            String newlyAssignedCloverCustomerID = "";
            long sqliteReturnResult = 0;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress.setMessage("Adding Customer...");
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
                    com.clover.sdk.v1.customer.Customer returnCustomer = customerConnector.createCustomer((newCustomer.getFirstName().toString()), (newCustomer.getLastName().toString()), (newCustomer.getMarketingAllowed()));
                    newlyAssignedCloverCustomerID = returnCustomer.getId();
                    newCustomer.setId(newlyAssignedCloverCustomerID);
                    cloverInsertSuccess = true;
                } catch (RemoteException | ServiceException | ClientException | BindingException e) {
                    cloverInsertSuccess = false;
                    Log.e("Clover Exception: ", e.getMessage());
                } catch (Exception e2) {
                    cloverInsertSuccess = false;
                    Log.e("Generic Exception: ", e2.getMessage());
                } finally {
                    customerConnector.disconnect();
                }

                //////ONLY CONTINUE IF CLOVER INSERT SUCCESSFUL//////
                if (cloverInsertSuccess) {
                    ///////////* DYNAMICS INSERTION *////////////////////////
                    CustomerWebService customerWebService = new CustomerWebService();
                    createCustomerWSSuccess = customerWebService.createCustomerServiceCall(newCustomer);

                    ////ONLY CONTINUE IF DYNAMICS INSERT SUCCESSFUL??////
                    if (createCustomerWSSuccess) {
                        ///////////* LOCAL INSERTION *////////////////////////
                        try {
                            CustomerDAO customerDAO = new CustomerDAO(addCustomerContext);
                            customerDAO.insertLocalCustomerRecord(newCustomer);
                        } catch (Exception e) {
                            Log.e("Generic Exception: ", "Local insertion customer: " + e.getMessage());
                        }
                    }
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

    private Customer createNewCustomerAndSetData() {
        emailAddressList = new ArrayList<>();
        phoneNumberList = new ArrayList<>();
        addressList = new ArrayList<>();
        Customer newCustomer = new Customer().setFirstName(customerFirst.getText().toString());
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

        return newCustomer;
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
}
