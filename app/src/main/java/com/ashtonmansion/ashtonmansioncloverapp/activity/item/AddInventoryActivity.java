package com.ashtonmansion.ashtonmansioncloverapp.activity.item;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.Context;
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
    //ACTIVITY INSTANCE VARS
    private Context addInventoryActivityContext;
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

    //////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);
        addInventoryActivityContext = this;
    }

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
        long testLong = 0;
        String tempPriceString = itemPriceField.getText().toString().replaceAll("\\s", "");
        if (!tempPriceString.equalsIgnoreCase("")) {
            testLong = Long.parseLong(tempPriceString);
        }

        if (testLong != 0 && !tempPriceString.equalsIgnoreCase("") && tempPriceString != null) {
            newItem.setPrice(testLong);
        } else {
            testLong = 0;
            newItem.setPrice(testLong);
        }

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
            ProgressDialog progressDialog = new ProgressDialog(addInventoryActivityContext);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage("Adding Item...");
                progressDialog.show();
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
                disconnectInventoryConn();
                finish();
                progressDialog.dismiss();
            }
        }.execute();
    }

    public void cancelAddInventory(View view) {
        finish();
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
}
