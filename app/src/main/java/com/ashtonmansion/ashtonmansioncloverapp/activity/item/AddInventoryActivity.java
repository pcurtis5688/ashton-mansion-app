package com.ashtonmansion.ashtonmansioncloverapp.activity.item;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ashtonmansion.ashtonmansioncloverapp.R;

public class AddInventoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);
    }

    //Method to finalize the inventory add
    public void finalizeAddInventory(View view){
        //TODO COMPLETE THIS IMPLEMENTATION
    }

    //Method to return when cancel button is pressed
    public void cancelAddInventory(View view) {
        finish();
    }
}
