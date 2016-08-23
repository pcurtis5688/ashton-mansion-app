package com.ashtonmansion.ashtonmansioncloverapp.activity.item;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.clover.sdk.v3.customers.Customer;
import com.clover.sdk.v3.inventory.Item;

public class EditItemActivity extends AppCompatActivity {
    //ACTIVITY INSTANCE VARS
    private Context editItemActivityContext;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        editItemActivityContext = this;
        //GET THE CUSTOMER object FROM THE MAIN CUSTOMER ACTIVITY
        Bundle itemBundle = getIntent().getExtras();
        Item itemPassedInstance = (Item) itemBundle.get("item");
        TextView testTV = (TextView) findViewById(R.id.textTV);
        testTV.setText("item passed: " + itemPassedInstance.getName());
    }
}
