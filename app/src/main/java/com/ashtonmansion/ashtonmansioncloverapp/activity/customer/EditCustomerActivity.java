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
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v1.customer.Address;
import com.clover.sdk.v1.customer.Customer;
import com.clover.sdk.v1.customer.CustomerConnector;
import com.clover.sdk.v1.customer.EmailAddress;
import com.clover.sdk.v1.customer.PhoneNumber;

import java.util.List;

public class EditCustomerActivity extends AppCompatActivity {
    //Activity vars
    private Context editCustomerActivityContext;
    //STATIC LOCAL VARS
    private Account mAcct;
    private CustomerConnector customerConnector;
    private Customer editCustomer;
    private List<PhoneNumber> phoneNumberList;
    private List<Address> addressList;
    private List<EmailAddress> emailAddressList;
    //PRIVATE FIELD VARS
    private EditText customerPhoneEdit;
    private List<PhoneNumber> phoneNumbers;
    private EditText customerEmailEdit;
    private EditText customerAddress1Edit;
    private EditText customerAddress2Edit;
    private EditText customerAddress3Edit;
    private EditText customerCityEdit;
    private Spinner customerStateSpinnerEdit;
    private ArrayAdapter<String> customerStateEditSpinnerAdapter;
    private EditText customerZipEdit;

    public void saveCustomerEdits(View view) {
        //TODO THIS AS WELL AS OTHER ADJUSTMENTS TO THE EDIT ACTIVITIES
    }

    private void handlePhoneList() {
        //TODO THINK ABOUT THIS
    }

    private void handleAddressList() {
        //TODO THINK ABOUT THIS
    }

    private void handleEmailList() {
        //TODO THINK ABOUT THIS
    }

    ///WORKING ABOVE
    private void getAdditionalCustomerData(final String customerID) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    getMerchantAccount();
                    connectCustomerConn();
                    Customer tempCustomer = customerConnector.getCustomer(customerID);
                    phoneNumberList = tempCustomer.getPhoneNumbers();
                    emailAddressList = tempCustomer.getEmailAddresses();
                    addressList = tempCustomer.getAddresses();
                } catch (RemoteException | ServiceException | ClientException | BindingException e1) {
                    Log.e("Clover Excpt: ", e1.getMessage());
                } catch (Exception e2) {
                    Log.e("Generic Excpt: ", e2.getMessage());
                } finally {
                    disconnectCustomerConn();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                handleAdditionalCustomerData();
            }
        }.execute();
    }

    private void handleAdditionalCustomerData() {
        //PHONE HANDLING
        handlePhoneList();
        //EMAIL HANDLING
        handleEmailList();
        //ADDRESS HANDLING
        handleAddressList();
    }

    //TODO THIS IS THE HANDLE PLACEHOLDER

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

    public void cancelEditCustomer(View view) {
        finish();
    }

    //ACTIVITY FLOW METHODS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);

        //GET THE CUSTOMER object FROM THE MAIN CUSTOMER ACTIVITY
        Bundle extras = getIntent().getExtras();
        editCustomer = (Customer) extras.get("customer");
        //GET CUSTOMER FIELDS ON PAGE
        EditText customerFirstEdit = (EditText) findViewById(R.id.customer_first_name_edit);
        EditText customerLastEdit = (EditText) findViewById(R.id.customer_last_name_edit);
        CheckBox customerMarketingAllowedChkboxEdit = (CheckBox) findViewById(R.id.customer_marketing_allowed_chkbox_edit);
        customerPhoneEdit = (EditText) findViewById(R.id.customer_phone_edit);
        customerEmailEdit = (EditText) findViewById(R.id.customer_email_edit);
        customerAddress1Edit = (EditText) findViewById(R.id.customer_address_1_edit);
        customerAddress2Edit = (EditText) findViewById(R.id.customer_address_2_edit);
        customerAddress3Edit = (EditText) findViewById(R.id.customer_address_3_edit);
        customerCityEdit = (EditText) findViewById(R.id.customer_city_edit);
        customerStateSpinnerEdit = (Spinner) findViewById(R.id.customer_state_spinner_edit);
        customerStateEditSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        customerStateSpinnerEdit.setAdapter(customerStateEditSpinnerAdapter);
        customerZipEdit = (EditText) findViewById(R.id.customer_zip_edit);
        //SET THE FIELDS THAT ARE AVAILABLE IN CUSTOMER OBJECT
        //FIRST NAME HANDLING
        String editCustomerFirstName = editCustomer.getFirstName().replaceAll("\\s", "");
        if (editCustomerFirstName != null && !editCustomerFirstName.equalsIgnoreCase("")) {
            customerFirstEdit.setText(editCustomerFirstName);
        } else {
            customerFirstEdit.setText(getResources().getString(R.string.edit_customer_no_first_name_str));
        }
        //LAST NAME HANDLING
        String editCustomerLastName = editCustomer.getLastName().replaceAll("\\s", "");
        if (editCustomerLastName != null && !editCustomerLastName.equalsIgnoreCase("")) {
            customerLastEdit.setText(editCustomerLastName);
        } else {
            customerLastEdit.setText(getResources().getString(R.string.edit_customer_no_last_name_str));
        }
        //CHECKBOX....
        customerMarketingAllowedChkboxEdit.setChecked(editCustomer.getMarketingAllowed());
        //USE THE CUSTOMER ID TO GET ADDITIONAL DATA
        getAdditionalCustomerData(editCustomer.getId());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
