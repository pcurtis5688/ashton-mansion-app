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
import com.clover.sdk.v1.customer.CustomerConnector;
import com.clover.sdk.v3.customers.Customer;
import com.clover.sdk.v3.customers.Address;
import com.clover.sdk.v3.customers.EmailAddress;
import com.clover.sdk.v3.customers.PhoneNumber;

import java.util.ArrayList;
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
    private EditText customerFirstEdit;
    private EditText customerLastEdit;
    private CheckBox customerMarketingAllowedChkboxEdit;
    private EditText customerPhoneEdit;
    private EditText customerEmailEdit;
    private EditText customerAddress1Edit;
    private EditText customerAddress2Edit;
    private EditText customerAddress3Edit;
    private EditText customerCityEdit;
    private Spinner customerStateSpinnerEdit;
    private ArrayAdapter<String> customerStateEditSpinnerAdapter;
    private EditText customerZipEdit;

    public void saveCustomerEdits(View view) {
        Customer editedCustomer = createNewCustomerInstanceAndSetData();
        //TODO I'm here.
    }

    private Customer createNewCustomerInstanceAndSetData() {
        com.clover.sdk.v3.customers.Customer editedCustomerInstance = new com.clover.sdk.v3.customers.Customer();
        editedCustomerInstance.setFirstName(customerFirstEdit.getText().toString());
        editedCustomerInstance.setLastName(customerLastEdit.getText().toString());
        editedCustomerInstance.setMarketingAllowed(customerMarketingAllowedChkboxEdit.isChecked());

        //NEW CUSTOMER PHONE NUMBER
        PhoneNumber newCustomerPhoneNumber = new PhoneNumber();
        newCustomerPhoneNumber.setPhoneNumber(customerPhoneEdit.getText().toString());
        List<PhoneNumber> newCustomerPhoneNumberList = new ArrayList<>();
        newCustomerPhoneNumberList.add(newCustomerPhoneNumber);
        editedCustomerInstance.setPhoneNumbers(newCustomerPhoneNumberList);

        //NEW CUSTOMER EMAIL
        EmailAddress newCustomerEmailAddress = new EmailAddress();
        newCustomerEmailAddress.setEmailAddress(customerEmailEdit.getText().toString());
        List<EmailAddress> newCustomerEmailAddressList = new ArrayList<>();
        newCustomerEmailAddressList.add(newCustomerEmailAddress);
        editedCustomerInstance.setEmailAddresses(newCustomerEmailAddressList);

        //NEW CUSTOMER ADDRESS
        Address newCustomerAddress = new Address();
        newCustomerAddress.setAddress1(customerAddress1Edit.getText().toString());
        newCustomerAddress.setAddress2(customerAddress2Edit.getText().toString());
        newCustomerAddress.setAddress3(customerAddress3Edit.getText().toString());
        newCustomerAddress.setCity(customerCityEdit.getText().toString());
        newCustomerAddress.setState(customerStateSpinnerEdit.getSelectedItem().toString());
        newCustomerAddress.setZip(customerZipEdit.getText().toString());
        List<Address> newCustomerAddressList = new ArrayList<>();
        newCustomerAddressList.add(newCustomerAddress);
        editedCustomerInstance.setAddresses(newCustomerAddressList);

        return editedCustomerInstance;
    }

    ///WORKING ABOVE
    private void getAdditionalCustomerData(final String customerID) {
        Log.i("Customer ID: ", "" + customerID);
        new AsyncTask<Void, Void, Void>() {
            ProgressDialog additionalCustomerDataProgress = new ProgressDialog(editCustomerActivityContext);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                additionalCustomerDataProgress.setMessage("Loading Additional Data...");
                additionalCustomerDataProgress.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    getMerchantAccount();
                    connectCustomerConn();
                    //todo

                    //     phoneNumberList = customerConnector.getCustomer(customerID).getPhoneNumbers();
                    //        emailAddressList = customerConnector.getCustomer(customerID).getEmailAddresses();
                    //        addressList = customerConnector.getCustomer(customerID).getAddresses();
                } catch (RemoteException | ServiceException | ClientException | BindingException e1) {
                    Log.e("Clover Excpt: ", e1.getMessage());
                } catch (Exception e2) {
                    Log.e("Generic Excpt: ", e2.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                disconnectCustomerConn();
                handleAdditionalCustomerData();
                additionalCustomerDataProgress.dismiss();
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

    private void handlePhoneList() {
        if (phoneNumberList.size() > 0) {
            PhoneNumber number = phoneNumberList.get(0);
            customerPhoneEdit.setText(number.getPhoneNumber());
        } else {
            customerPhoneEdit.setHint(getResources().getString(R.string.edit_customer_no_phoneNumbers_str));
        }
    }

    private void handleEmailList() {
        if (emailAddressList.size() > 0) {
            EmailAddress email = emailAddressList.get(0);
            customerEmailEdit.setText(email.getEmailAddress());
        } else {
            customerEmailEdit.setHint(getResources().getString(R.string.edit_customer_no_emailAddresses_str));
        }
    }

    private void handleAddressList() {
        if (addressList.size() > 0) {
            Address address = addressList.get(0);
            customerAddress1Edit.setText(address.getAddress1());
            customerAddress2Edit.setText(address.getAddress2());
            customerAddress3Edit.setText(address.getAddress3());
        } else {
            customerAddress1Edit.setHint(getResources().getString(R.string.edit_customer_no_addresses_str));
            customerAddress2Edit.setHint(getResources().getString(R.string.customer_address_2_prompt_str));
            customerAddress3Edit.setHint(getResources().getString(R.string.customer_address_3_prompt_str));
        }
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

    public void cancelEditCustomer(View view) {
        finish();
    }

    //ACTIVITY FLOW METHODS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);
        editCustomerActivityContext = this;
        //GET THE CUSTOMER object FROM THE MAIN CUSTOMER ACTIVITY
        Bundle extras = getIntent().getExtras();
        editCustomer = (Customer) extras.get("customer");
        //GET CUSTOMER FIELDS ON PAGE
        customerFirstEdit = (EditText) findViewById(R.id.customer_first_name_edit);
        customerLastEdit = (EditText) findViewById(R.id.customer_last_name_edit);
        customerMarketingAllowedChkboxEdit = (CheckBox) findViewById(R.id.customer_marketing_allowed_chkbox_edit);
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
        //CHECKBOX HANDLING
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
