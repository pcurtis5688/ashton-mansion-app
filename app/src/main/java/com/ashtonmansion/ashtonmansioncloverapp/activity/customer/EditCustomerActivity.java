package com.ashtonmansion.ashtonmansioncloverapp.activity.customer;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.customer.Address;
import com.clover.sdk.v1.customer.Customer;
import com.clover.sdk.v1.customer.CustomerConnector;
import com.clover.sdk.v1.customer.EmailAddress;
import com.clover.sdk.v1.customer.PhoneNumber;

import java.util.List;

public class EditCustomerActivity extends AppCompatActivity {
    //Activity vars
    private Context context;
    //STATIC LOCAL VARS
    private Account mAcct;
    private CustomerConnector mCustConn;
    private Customer editCustomer;
    private List<Address> addressList;
    private List<PhoneNumber> phoneNumberList;
    private List<EmailAddress> emailAddressList;
    //PRIVATE FIELD VARS
    private EditText customerFirstEdit;
    private EditText customerLastEdit;
    private CheckBox customerMarketingAllowedChkboxEdit;
    private EditText customerPhoneEdit;
    private List<PhoneNumber> phoneNumbers;
    private EditText customerEmailEdit;
    private EditText customerAddress1Edit;
    private EditText customerAddress2Edit;
    private EditText customerAddress3Edit;
    private Address customerAddressEntry1;
    private PhoneNumber customerPhoneEntry1;
    private EditText customerCityEdit;
    private Spinner customerStateSpinnerEdit;
    private ArrayAdapter<String> customerStateEditSpinnerAdapter;
    private EditText customerZipEdit;

    public void saveCustomerEdits(View view) {

    }

    //ACTIVITY FLOW METHODS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Customer Data");
        progressDialog.show();

        //Retrieve the Clover merchant account
        if (mAcct == null) {
            mAcct = CloverAccount.getAccount(this);
            //Break if Clover Account unreachable
            if (mAcct == null) {
                finish();
                return;
            }
        }
        //GET THE CUSTOMER object FROM THE MAIN CUSTOMER ACTIVITY
        Bundle extras = getIntent().getExtras();
        editCustomer = (Customer) extras.get("customer");
        //USE THE CUSTOMER ID TO GET ADDITIONAL DATA IN BACKGROUND but set fields that can
        customerFirstEdit = (EditText) findViewById(R.id.customer_first_name_edit);
        customerFirstEdit.setText(editCustomer.getFirstName());

        customerLastEdit = (EditText) findViewById(R.id.customer_last_name_edit);
        customerLastEdit.setText(editCustomer.getLastName());

        customerMarketingAllowedChkboxEdit = (CheckBox) findViewById(R.id.customer_marketing_allowed_chkbox_edit);
        customerMarketingAllowedChkboxEdit.setChecked(editCustomer.getMarketingAllowed());

        customerPhoneEdit = (EditText) findViewById(R.id.customer_phone_edit);
        customerPhoneEdit.setText(editCustomer.getPhoneNumbers().toString());

        customerEmailEdit = (EditText) findViewById(R.id.customer_email_edit);
        customerEmailEdit.setText(editCustomer.getEmailAddresses().toString());

        customerAddress1Edit = (EditText) findViewById(R.id.customer_address_1_edit);
        customerAddress2Edit = (EditText) findViewById(R.id.customer_address_2_edit);
        customerAddress3Edit = (EditText) findViewById(R.id.customer_address_3_edit);
        customerCityEdit = (EditText) findViewById(R.id.customer_city_edit);
        customerStateSpinnerEdit = (Spinner) findViewById(R.id.customer_state_spinner_edit);
        customerStateEditSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        customerStateSpinnerEdit.setAdapter(customerStateEditSpinnerAdapter);
        customerZipEdit = (EditText) findViewById(R.id.customer_zip_edit);

        handleAddressList();
        handlePhoneList();
        handleEmailList();
        progressDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        // disconnectCustomerConn();
        super.onPause();
    }

    //////* BELOW METHODS ARE BASICALLY COMPLETE *///////////

    private void handleAddressList() {
        addressList = editCustomer.getAddresses();
        if (addressList.size() == 0) {
            customerAddress1Edit.setHint("No Addresses Saved");
        } else if (addressList.size() == 1) {
            customerAddressEntry1 = editCustomer.getAddresses().get(0);
            customerAddress1Edit.setText(customerAddressEntry1.getAddress1());
            customerAddress2Edit.setText(customerAddressEntry1.getAddress2());
            customerAddress3Edit.setText(customerAddressEntry1.getAddress3());
            customerCityEdit.setText(customerAddressEntry1.getCity());
            customerStateSpinnerEdit.setSelection(customerStateEditSpinnerAdapter.getPosition(customerAddressEntry1.getState()));
            customerZipEdit.setText(customerAddressEntry1.getZip());
        } else if (addressList.size() > 1) {
            customerAddress1Edit.setText(editCustomer.getAddresses().toString());
        }
    }

    private void handlePhoneList() {
        List<PhoneNumber> phoneList = editCustomer.getPhoneNumbers();
        String phoneText = "";
        if (phoneList.size() == 0) {
            phoneText = "No Phone Numbers Saved";
            customerPhoneEdit.setHint(phoneText);
        } else if (phoneList.size() == 1) {
            customerPhoneEdit.setText(editCustomer.getPhoneNumbers().get(0).getPhoneNumber());
        } else if (phoneList.size() > 1) {
            phoneText = "" + phoneList.get(0).getPhoneNumber();
            for (int i = 1; i < phoneList.size(); i++) {
                phoneText = phoneText + ", " + phoneList.get(i);
            }
            customerPhoneEdit.setHint(phoneText);
        }
    }

    private void handleEmailList() {
        emailAddressList = editCustomer.getEmailAddresses();
        if (emailAddressList.size() == 0) {
            customerEmailEdit.setHint("No Email Addresses");
        } else if (emailAddressList.size() == 1) {
            customerEmailEdit.setHint(emailAddressList.get(0).getEmailAddress());
        } else if (emailAddressList.size() > 1) {
            String emailText = "" + emailAddressList.get(0).getEmailAddress();
            for (int i = 1; i < emailAddressList.size(); i++) {
                emailText = emailText + ", " + emailAddressList.get(i);
            }
            customerEmailEdit.setHint(emailText);
        }
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

    public void cancelEditCustomer(View view) {
        finish();
    }
}
