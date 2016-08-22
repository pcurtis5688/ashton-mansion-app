package com.ashtonmansion.ashtonmansioncloverapp.activity.employee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.clover.sdk.v1.customer.Customer;

/**
 * Created by paul on 8/17/2016.
 */
public class EditEmployeeActivity extends AppCompatActivity {


    public void finalizeEmployeeModification(View view) {

    }

    public void cancelEditEmployee(View view) {
        finish();
    }

    /////////ACTIVITY FLOW METHODS ////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);
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
