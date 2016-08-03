package com.ashtonmansion.ashtonmansioncloverapp.activity.item;

import android.accounts.Account;
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
    // Private account vars
    private Account mAcct;
    private InventoryConnector mInvConn;
    private List<Item> itemList;
    //Inventory view vars
    private TableLayout itemTable;
    private TableRow itemHeaderRow;
    private TableRow newItemRow;
    private TextView itemNameTextview;
    private TextView itemModifierTextview;
    private TextView itemProductCodeTextview;
    private TextView itemSkuTextview;
    private TextView itemPriceTextview;

    public void displayAddInventory(View view) {
        Intent addInventoryItemIntent = new Intent(this, AddInventoryActivity.class);
        startActivity(addInventoryItemIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

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
        //Clear the item table
        itemTable.removeAllViews();
        //Create item header row
        createItemTableHeaderRow();
        //TODO THIS

        for (Item item : itemList) {
            //Create new row to add to the table
            newItemRow = new TableRow(this);
            //Create new views to populate data
            itemNameTextview = new TextView(this);
            itemModifierTextview = new TextView(this);
            itemProductCodeTextview = new TextView(this);
            itemSkuTextview = new TextView(this);
            itemPriceTextview = new TextView(this);
            //Set the new data for the new row
            itemNameTextview.setText(item.getName());
            itemModifierTextview.setText(item.getModifierGroups().toString());
            itemProductCodeTextview.setText(item.getCode());
            itemSkuTextview.setText(item.getSku());
            itemPriceTextview.setText(item.getPrice().toString());
            //Add the new data to the new row
            newItemRow.addView(itemNameTextview);
            newItemRow.addView(itemModifierTextview);
            newItemRow.addView(itemProductCodeTextview);
            newItemRow.addView(itemSkuTextview);
            newItemRow.addView(itemPriceTextview);
            //Add the new row to the table
            itemTable.addView(newItemRow);
        }
    }

    private void populateItemDetail() {
        //Todo decide on this implementation
         /* Item testItem = itemList.get(0);
        TextView itemDetailNameTv = (TextView) findViewById(R.id.item_detail_name);
        itemDetailNameTv.setText(testItem.getName());

        String testData = "Testing data: " + testItem.getName();
        itemDetailNameTv.setText(testData);*/
    }

    private void createItemTableHeaderRow() {
        //TODO THIS
    }
}