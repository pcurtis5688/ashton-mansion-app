package com.ashtonmansion.ashtonmansioncloverapp.activity.item;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v3.inventory.InventoryConnector;
import com.clover.sdk.v3.inventory.Item;

import java.util.List;

public class InventoryActivity extends AppCompatActivity {
    // PRIVATE ACTIVITY VARS
    private Account mAcct;
    private InventoryConnector mInvConn;
    private List<Item> itemList;
    private Context inventoryActivityContext;
    //ITEM TABLE OBJECT
    private TableLayout itemTable;

    private void getInventoryList() {
        new AsyncTask<Void, Void, Item>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //progress bar here
            }

            @Override
            protected Item doInBackground(Void... params) {
                try {
                    List<Item> items = mInvConn.getItems();
                    itemList = items;
                    return items.get(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Item item) {
                populateItemTable();
            }
        }.execute();
    }

    private void populateItemTable() {
        //CLEAR INVENTORY/ITEM TABLE
        itemTable.removeAllViews();
        //CREATE HEADER ROW
        createItemTableHeaderRow();
        TableRow newItemRow = new TableRow(this);
        //Create new row to add to the table
        if (itemList != null && itemList.size() > 0) {
            for (Item item : itemList) {
                //CREATE NEW INVENTORY ROW
                newItemRow = new TableRow(this);
                //CREATE NEW INVENTORY COLUMN VIEWS
                TextView itemNameTextview = new TextView(this);
                TextView itemModifierTextview = new TextView(this);
                TextView itemProductCodeTextview = new TextView(this);
                TextView itemSkuTextview = new TextView(this);
                TextView itemPriceTextview = new TextView(this);
                //POPULATE TEXT FIELDS WITH DATA
                itemNameTextview.setText(item.getName());
                itemModifierTextview.setText(item.getModifierGroups().toString());
                itemProductCodeTextview.setText(item.getCode());
                itemSkuTextview.setText(item.getSku());
                itemPriceTextview.setText(item.getPrice().toString());
                //ADD DATA TO THE NEW ROW
                newItemRow.addView(itemNameTextview);
                newItemRow.addView(itemModifierTextview);
                newItemRow.addView(itemProductCodeTextview);
                newItemRow.addView(itemSkuTextview);
                newItemRow.addView(itemPriceTextview);
                //ADD THE NEW ROW TO THE TABLE
                itemTable.addView(newItemRow);
            }
        } else {
            TextView noInvTV = new TextView(this);
            noInvTV.setText("No inventory... Please Add.");
            newItemRow.addView(noInvTV);
        }
    }

    private void createItemTableHeaderRow() {
        //CREATE HEADER ROW
        TableRow inventoryTableHeaderRow = new TableRow(inventoryActivityContext);
        //CREATE HEADER TEXT VIEWS
        TextView itemNameHeaderTV = new TextView(inventoryActivityContext);
        TextView itemModifierHeaderTV = new TextView(inventoryActivityContext);
        TextView itemProductCodeHeaderTV = new TextView(inventoryActivityContext);
        TextView itemSkuHeaderTV = new TextView(inventoryActivityContext);
        TextView itemPriceHeaderTV = new TextView(inventoryActivityContext);
        //SET TEXT OF HEADERS
        itemNameHeaderTV.setText(getResources().getString(R.string.inventory_table_name_header_str));
        itemModifierHeaderTV.setText(getResources().getString(R.string.inventory_table_modifier_header_str));
        itemProductCodeHeaderTV.setText(getResources().getString(R.string.inventory_table_product_code_header_str));
        itemSkuHeaderTV.setText(getResources().getString(R.string.inventory_table_sku_header_str));
        itemPriceHeaderTV.setText(getResources().getString(R.string.inventory_table_price_header_str));
        //ADD THE FIELDS TO THE NEW ROW
        inventoryTableHeaderRow.addView(itemNameHeaderTV);
        inventoryTableHeaderRow.addView(itemModifierHeaderTV);
        inventoryTableHeaderRow.addView(itemProductCodeHeaderTV);
        inventoryTableHeaderRow.addView(itemSkuHeaderTV);
        inventoryTableHeaderRow.addView(itemPriceHeaderTV);
        //ADD THE NEW ROW TO THE TABLE
        itemTable.addView(inventoryTableHeaderRow);
    }

    private void connectInventory() {
        disconnectInventory();
        if (mAcct != null) {
            mInvConn = new InventoryConnector(this, mAcct, null);
            mInvConn.connect();
        }
    }

    private void disconnectInventory() {
        if (mInvConn != null) {
            mInvConn.disconnect();
            mInvConn = null;
        }
    }

    public void displayAddInventory(View view) {
        Intent addInventoryItemIntent = new Intent(this, AddInventoryActivity.class);
        startActivity(addInventoryItemIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        inventoryActivityContext = this;
        itemTable = (TableLayout) findViewById(R.id.item_table);
    }

    @Override
    protected void onPause() {
        disconnectInventory();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Retrieve Clover Account
        if (mAcct == null) {
            mAcct = CloverAccount.getAccount(this);

            if (mAcct == null) {
                finish();
                return;
            }
        }

        //Connect the Merchant Inventory Connector
        connectInventory();
        getInventoryList();
    }
}