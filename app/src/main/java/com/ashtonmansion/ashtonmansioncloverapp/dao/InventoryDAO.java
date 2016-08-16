package com.ashtonmansion.ashtonmansioncloverapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by paul on 8/15/2016.
 */
public class InventoryDAO extends SQLiteOpenHelper {
    //DATABASE STATIC VARIABLES AND UTILITY STRINGS
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AshtonMansionDB";
    private static final String TEXT_TYPE_COMMA = " TEXT,";
    //CUSTOMER TABLE TITLE AND COLUMNS
    private static final String TABLE_ITEM = "Inventory_Item";
    private static final String ITEM_ID = "Item_ID";
    private static final String ITEM_NAME = "Item_Name";
    private static final String ITEM_SHOW_IN_REGISTER = "Item_Show_In_Register";
    private static final String ITEM_NON_REVENUE_ITEM = "Item_Non_Revenue_Item";
    private static final String ITEM_PRICE_TYPE = "Item_Price_Type";
    private static final String ITEM_PRICE = "Item_Price";
    private static final String ITEM_PRODUCT_CODE = "Item_Product_Code";
    private static final String ITEM_SKU = "Item_SKU";

    //TODO HERE

    //////*BELOW ARE COMLETE, WRAPPED *//////////////////////////
    public void createItemTableIfNotExists() {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_ITEM_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ITEM
                + "(" + ITEM_ID + " INTEGER PRIMARY KEY,"
                + ITEM_NAME + TEXT_TYPE_COMMA
                + ITEM_SHOW_IN_REGISTER + TEXT_TYPE_COMMA
                + ITEM_NON_REVENUE_ITEM + TEXT_TYPE_COMMA
                + ITEM_PRICE_TYPE + TEXT_TYPE_COMMA
                + ITEM_PRICE + TEXT_TYPE_COMMA
                + ITEM_PRODUCT_CODE + TEXT_TYPE_COMMA
                + ITEM_SKU + ")";
        db.execSQL(CREATE_ITEM_TABLE);
        db.close();
    }

    //CONSTRUCTOR
    public InventoryDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO FIGURE OUT WHAT I NEED HERE,
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO IMPLEMENT THIS METHOD LATER
    }
}
