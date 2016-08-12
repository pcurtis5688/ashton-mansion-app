package com.ashtonmansion.ashtonmansioncloverapp.activity.item;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.clover.sdk.v3.inventory.InventoryConnector;
import com.clover.sdk.v3.inventory.Item;
import com.clover.sdk.v3.inventory.PriceType;

public class AddInventoryActivity extends AppCompatActivity {
    private EditText itemName;
    private CheckBox showInRegisterChkBox;
    private CheckBox nonRevenueItemChkBox;
    private RadioGroup priceTypeRadioGroup;
    private RadioButton markedRadioButton;
    private EditText itemPriceField;
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
        //int radioID = priceTypeRadioGroup.indexOfChild(markedRadioButton);
        String radioSelection = (String) markedRadioButton.getText();

        itemPriceField = (EditText) findViewById(R.id.add_item_price_field);
        productCode = (EditText) findViewById(R.id.add_item_prodCode);
        itemSKU = (EditText) findViewById(R.id.add_item_sku);
        //////////////POPULATE THE CLOVER ITEM OBJECT FROM FIELDS
        newItem = new Item();
        newItem.setName(itemName.getText().toString());
        if (radioSelection.equalsIgnoreCase("Fixed")) {
            newItem.setPriceType(PriceType.FIXED);
        } else if (radioSelection.equalsIgnoreCase("Variable")) {
            newItem.setPriceType(PriceType.VARIABLE);
        } else {
            newItem.setPriceType(PriceType.PER_UNIT);
        }
        newItem.setPriceType(PriceType.FIXED);
        String tempString = itemPriceField.getText().toString();
        //todo below is temp workaround; drops the cents. fix.

        long testLong = (long) Double.parseDouble(tempString);
        newItem.setPrice(testLong);

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

        //todo don't make assumptions. but i think the below is next
        //InventoryConnector invConn = new InventoryConnector(this, mAcct, null);
    }

    //////////////////////////////////////////////////
    public void cancelAddInventory(View view) {
        finish();
    }
}
