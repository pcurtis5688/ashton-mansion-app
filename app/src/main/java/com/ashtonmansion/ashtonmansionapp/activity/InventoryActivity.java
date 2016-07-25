package com.ashtonmansion.ashtonmansionapp.activity;

import android.accounts.Account;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ashtonmansion.ashtonmansionapp.R;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v3.inventory.InventoryConnector;
import com.clover.sdk.v3.inventory.Item;

import java.util.List;

public class InventoryActivity extends AppCompatActivity {

    private Account mAcct;
    private InventoryConnector mInvConn;
    private List<Item> itemList;

    //Method to add new inventory
    public void displayAddInventory(View view) {
        Intent addInventoryItemIntent = new Intent(this, AddInventoryActivity.class);
        startActivity(addInventoryItemIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
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
                populateItemDetail();
            }
        }.execute();
    }

    private void populateItemTable() {
        //Item table vars
        TableLayout itemTable = (TableLayout) findViewById(R.id.inventory_table);
        TableRow workingInventoryRow = new TableRow(this);

        TextView itemNameTextview = new TextView(this);
        TextView itemModifierTextview = new TextView(this);
        TextView itemProductCodeTextview = new TextView(this);
        TextView itemSkuTextview = new TextView(this);
        TextView itemPriceTextview = new TextView(this);

        for (Item item : itemList) {
            itemNameTextview.setText(item.getName());
            itemModifierTextview.setText(item.getModifierGroups().toString());
            itemProductCodeTextview.setText(item.getCode());
            itemSkuTextview.setText(item.getSku());
            itemPriceTextview.setText(item.getPrice().toString());
            workingInventoryRow.addView(itemNameTextview);
            workingInventoryRow.addView(itemModifierTextview);
            workingInventoryRow.addView(itemProductCodeTextview);
            workingInventoryRow.addView(itemSkuTextview);
            workingInventoryRow.addView(itemPriceTextview);
            itemTable.addView(workingInventoryRow);
        }
    }

    private void populateItemDetail() {
        Item testItem = itemList.get(0);
        TextView itemDetailNameTv = (TextView) findViewById(R.id.item_detail_name);
        itemDetailNameTv.setText(testItem.getName());


        String testData = "Testing data: " + testItem.getName();
        itemDetailNameTv.setText(testData);
    }
}