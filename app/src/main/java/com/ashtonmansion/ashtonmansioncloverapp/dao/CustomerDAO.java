package com.ashtonmansion.ashtonmansioncloverapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.clover.sdk.v3.customers.Customer;


/**
 * Created by paul on 7/29/2016.
 */
public class CustomerDAO extends SQLiteOpenHelper {
    //DATABASE STATIC VARIABLES AND UTILITY STRINGS
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AshtonMansionDB";
    private static final String TEXT_TYPE_COMMA = " TEXT,";
    //TODO BELOW

    //CONSTRUCTOR
    public CustomerDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //// TODO: 7/29/2016 figure out what to do here

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //// TODO: 7/29/2016 figure out what to do here

    }

    public void addCustomer(Customer customer, Context context) {

    }


}
