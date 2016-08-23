package com.ashtonmansion.ashtonmansioncloverapp.activity.item;

import android.accounts.Account;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v3.inventory.InventoryConnector;
import com.clover.sdk.v3.inventory.Item;

import java.util.List;

public class InventoryActivity extends AppCompatActivity {
    // ACTIVITY INSTANCE VARS
    private Context inventoryActivityContext;
    // PRIVATE ACTIVITY VARS
    private Account mAcct;
    private InventoryConnector inventoryConnector;
    private List<Item> itemList;
    //ITEM TABLE OBJECT
    private TableLayout itemTable;

    private void getInventoryListAndPopulate() {
        new AsyncTask<Void, Void, Void>() {
            ProgressDialog progressDialog = new ProgressDialog(inventoryActivityContext);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage("Loading Inventory...");
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    itemList = inventoryConnector.getItems();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                populateItemTable();
                progressDialog.dismiss();
                disconnectInventory();
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
            for (final Item item : itemList) {
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
                //HANDLE EDIT CLICK LISTENERS
                Button editCustomerButton = new Button(inventoryActivityContext);
                editCustomerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editItem(item);
                    }
                });
                editCustomerButton.setText(getResources().getString(R.string.edit_string_for_buttons));
                //HANDLE THE DELETE BUTTON
                Button deleteCustomerButton = new Button(inventoryActivityContext);
                deleteCustomerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteThisItem(item.getId(), item.getName());
                    }
                });
                deleteCustomerButton.setText(getResources().getString(R.string.delete_string_for_buttons));
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
            noInvTV.setText(getResources().getString(R.string.no_inventory_available_message));
            newItemRow.addView(noInvTV);
        }
    }

    private void editItem(final Item item) {
        Intent editItemIntent = new Intent(this, EditItemActivity.class);
        editItemIntent.putExtra("item", item);
        startActivity(editItemIntent);
    }

    private void deleteThisItem(final String itemID, final String itemName) {
        new AlertDialog.Builder(this)
                .setTitle("Delete " + itemName + "?")
                .setMessage("Delete Item: " + itemName + " from inventory?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String confirmationToastString = itemName + " Deleted!";
                        deleteItem(itemID);
                        Toast.makeText(InventoryActivity.this, confirmationToastString, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    private void deleteItem(final String itemID) {
        new AsyncTask<Void, Void, Void>() {
            ProgressDialog progressDialog = new ProgressDialog(inventoryActivityContext);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage("Deleting Item...");
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    getMerchantAccount();
                    connectInventory();
                    inventoryConnector.deleteItem(itemID);
                } catch (RemoteException | ServiceException | ClientException | BindingException e1) {
                    Log.e("Clover excptn: ", e1.getClass().getName() + "," + e1.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                disconnectInventory();
                progressDialog.dismiss();
                getInventoryListAndPopulate();
            }
        }.execute();
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
        //Connect to Clover Inventory and Populate Table
        getMerchantAccount();
        connectInventory();
        getInventoryListAndPopulate();
    }

    //MERCHANT/CLOVER CONNECTIONS
    private void getMerchantAccount() {
        //Retrieve Clover Account
        if (mAcct == null) {
            mAcct = CloverAccount.getAccount(this);

            if (mAcct == null) {
                finish();
            }
        }
    }

    private void connectInventory() {
        disconnectInventory();
        if (mAcct != null) {
            inventoryConnector = new InventoryConnector(this, mAcct, null);
            inventoryConnector.connect();
        }
    }

    private void disconnectInventory() {
        if (inventoryConnector != null) {
            inventoryConnector.disconnect();
            inventoryConnector = null;
        }
    }

}