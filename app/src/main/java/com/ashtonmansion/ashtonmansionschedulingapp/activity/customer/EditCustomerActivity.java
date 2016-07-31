package com.ashtonmansion.ashtonmansionschedulingapp.activity.customer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.ashtonmansion.ashtonmansionschedulingapp.R;

public class EditCustomerActivity extends AppCompatActivity {
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
    private EditText customerZipEdit;

    private void setCurrentCustomerData() {

    }

    //ACTIVITY FLOW METHODS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);

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
        customerZipEdit = (EditText) findViewById(R.id.customer_zip_edit);

        setCurrentCustomerData();
    }

    @Override
    protected void onPause() {
        // disconnectCustomerConn();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
