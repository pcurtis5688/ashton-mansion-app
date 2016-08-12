package com.ashtonmansion.ashtonmansioncloverapp.activity.item;

import android.accounts.Account;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v3.inventory.InventoryConnector;
import com.clover.sdk.v3.inventory.Item;
import com.clover.sdk.v3.inventory.PriceType;

public class AddInventoryActivity extends AppCompatActivity {
    //SERVICE VARS
    private Account mAcct;
    private InventoryConnector inventoryConnector;
    private Item newItem;
    ///ITEM ACTIVITY FIELDS
    private EditText itemName;
    private CheckBox showInRegisterChkBox;
    private CheckBox nonRevenueItemChkBox;
    private RadioGroup priceTypeRadioGroup;
    private RadioButton markedRadioButton;
    private EditText itemPriceField;
    private EditText productCode;
    private EditText itemSKU;

    //TODO IMPLEMENT THE EDIT BUTTONS HERE... AND HAVE TO REINSERT
    public void submitItemForInsertion(View view) {
        //////////////GET FIELD VALUES AT TIME OF SUBMISSION
        itemName = (EditText) findViewById(R.id.add_item_name_field);
        showInRegisterChkBox = (CheckBox) findViewById(R.id.show_in_register_chkbox);
        nonRevenueItemChkBox = (CheckBox) findViewById(R.id.add_item_nonRevenue_chkbox);
        priceTypeRadioGroup = (RadioGroup) findViewById(R.id.price_radio_group);
        markedRadioButton = (RadioButton) findViewById(priceTypeRadioGroup.getCheckedRadioButtonId());
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
        //todo below is temp workaround; drops the cents. fix.
        String tempString = itemPriceField.getText().toString();
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
        newItem.setCode(productCode.getText().toString());
        newItem.setSku(itemSKU.getText().toString());
        newItem.validate();

        insertItemIntoClover();
    }

    private void insertItemIntoClover() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //TODO PROGRESS PBAR
            }

            @Override
            protected Void doInBackground(Void... params) {
                getMerchantAcct();
                connectInventoryConn();
                try {
                    inventoryConnector.createItem(newItem);
                } catch (RemoteException | ServiceException | ClientException | BindingException e) {
                    Log.i("Item Creation Error:", e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                //TODO PROGRESS PBAR
                finish();
            }
        }.execute();
    }

    private void getMerchantAcct() {
        if (mAcct == null) {
            mAcct = CloverAccount.getAccount(this);
            if (mAcct == null) {
                //BREAK IF CLOVER ACCOUNT UNREACHABLE
                finish();
            }
        }
    }

    private void connectInventoryConn() {
        disconnectInventoryConn();
        if (mAcct != null) {
            inventoryConnector = new InventoryConnector(this, mAcct, null);
            inventoryConnector.connect();
        }
    }

    private void disconnectInventoryConn() {
        if (inventoryConnector != null) {
            inventoryConnector.disconnect();
            inventoryConnector = null;
        }
    }

    //////////////////////////////////////////////////
    public void cancelAddInventory(View view) {
        finish();
    }

    //////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);
    }
}
