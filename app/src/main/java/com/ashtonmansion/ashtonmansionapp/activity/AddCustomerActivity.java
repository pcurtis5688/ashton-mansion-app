package com.ashtonmansion.ashtonmansionapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ashtonmansion.ashtonmansionapp.R;

public class AddCustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
    }

    public void finalizeAddCustomer(View view){
        //TODO FINISH IMPLEMENTATION
    }
    //Method to return when cancel button is pressed
    public void cancelAddCustomer(View view) {
        finish();
    }
}
