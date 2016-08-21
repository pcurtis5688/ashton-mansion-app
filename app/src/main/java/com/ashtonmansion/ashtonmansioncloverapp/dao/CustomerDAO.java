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
    //CUSTOMER TABLE TITLE AND COLUMNS
    private static final String TABLE_CUSTOMER = "Customer";
    private static final String CUSTOMER_ID = "Customer_ID";
    private static final String CUSTOMER_FIRST_NAME = "Customer_First_Name";
    private static final String CUSTOMER_LAST_NAME = "Customer_Last_Name";
    private static final String CUSTOMER_MARKETING_ALLOWED = "Customer_Marketing_Allowed";
    private static final String CUSTOMER_PHONE_NUMBERS = "Customer_Phone_Numbers";
    private static final String CUSTOMER_EMAIL_ADDRESSES = "Customer_Email_Addresses";
    private static final String CUSTOMER_ADDRESSES = "Customer_Addresses";

    public long insertLocalCustomerRecord(Customer customer) {
        long localCustomerInsertionSuccess = 0;
        //TODO COMPLETE....


        return localCustomerInsertionSuccess;
    }

    public boolean deleteLocalCustomerRecord(String customerID) {
        boolean localCustomerDeletionSuccessful = false;

        //TODO FINISH THIS
        return localCustomerDeletionSuccessful;
    }


    /* BELOW METHODS ARE ALL BASICALLY COMPLETE */////////////
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

    public void createCustomerTableIfNotExists() {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_CUSTOMER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CUSTOMER
                + "(" + CUSTOMER_ID + " INTEGER PRIMARY KEY,"
                + CUSTOMER_FIRST_NAME + TEXT_TYPE_COMMA
                + CUSTOMER_LAST_NAME + TEXT_TYPE_COMMA
                + CUSTOMER_MARKETING_ALLOWED + TEXT_TYPE_COMMA
                + CUSTOMER_PHONE_NUMBERS + TEXT_TYPE_COMMA
                + CUSTOMER_EMAIL_ADDRESSES + TEXT_TYPE_COMMA
                + CUSTOMER_ADDRESSES + ")";
        db.execSQL(CREATE_CUSTOMER_TABLE);
        db.close();
    }
}