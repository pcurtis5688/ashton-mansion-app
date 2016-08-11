package com.ashtonmansion.ashtonmansioncloverapp.activity.item;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.clover.sdk.v3.inventory.Item;

public class AddInventoryActivity extends AppCompatActivity {
    private EditText itemName;
    private CheckBox showInRegisterChkBox;
    private CheckBox nonRevenueItemChkBox;
    private RadioGroup priceTypeRadioGroup;
    private RadioButton markedRadioButton;
    private EditText productCode;
    private EditText itemSKU;
    private Item newItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);
    }

    //TODO IMPLEMENT THE EDIT BUTTONS HERE... AND HAVE TO REINSERT
    public void insertItemIntoClover(View view) {
        //////////////GET FIELD VALUES AT TIME OF SUBMISSION
        itemName = (EditText) findViewById(R.id.add_item_name_field);
        showInRegisterChkBox = (CheckBox) findViewById(R.id.show_in_register_chkbox);
        nonRevenueItemChkBox = (CheckBox) findViewById(R.id.add_item_nonRevenue_chkbox);
        priceTypeRadioGroup = (RadioGroup) findViewById(R.id.price_radio_group);
        markedRadioButton = (RadioButton) findViewById(priceTypeRadioGroup.getCheckedRadioButtonId());
        productCode = (EditText) findViewById(R.id.add_item_prodCode);
        itemSKU = (EditText) findViewById(R.id.add_item_sku);

        Log.i("root view: ", markedRadioButton.getRootView().toString());

        //////////////POPULATE THE CLOVER ITEM OBJECT FROM FIELDS
        newItem = new Item();
        newItem.setName(itemName.getText().toString());

        if (showInRegisterChkBox.isChecked()) {
            newItem.setHidden(false);
        } else {
            newItem.setHidden(true);
        }

        if (nonRevenueItemChkBox.isChecked()) {
            newItem.setIsRevenue(false);
        } else {
            newItem.setIsRevenue(true);
        }

        newItem.setSku(itemSKU.getText().toString());
        newItem.validate();
    }

    //////////////////////////////////////////////////
    public void cancelAddInventory(View view) {
        finish();
    }
}
