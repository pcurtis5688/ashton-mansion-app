package com.ashtonmansion.ashtonmansionschedulingapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.clover.sdk.v1.customer.Customer;

/**
 * Created by paul on 7/29/2016.
 */
public class CustomerDAO extends SQLiteOpenHelper {
    //DATABASE STATIC VARIABLES AND UTILITY STRINGS
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AshtonMansionDB";
    private static final String TEXT_TYPE_COMMA = " TEXT,";
    //EMPLOYEE TABLE AND COLUMN NAMES
    //TODO BELOW
//    private static final String TABLE_CUSTOMER = "Employee";
//    private static final String CUSTOMER_ID = "employee_id";
//    private static final String CUSTOMER_NAME = "name";

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
        SQLiteDatabase db = this.getWritableDatabase();

        createCustomerTable();

        ContentValues values = new ContentValues();
        // values.put(CUSTOMER_ID, value);
        //todo etc  values.put();
    }


    private void createCustomerTable() {
        //TODO TEST IF EMPLOYEE TABLE ALREADY EXISTS
        SQLiteDatabase db = this.getWritableDatabase();
//        String CREATE_EMPLOYEE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EMPLOYEE
//                + "(" + EMPLOYEE_NAME + TEXT_TYPE_COMMA
//                + EMPLOYEE_NICKNAME + TEXT_TYPE_COMMA
//                + EMPLOYEE_ROLE + TEXT_TYPE_COMMA
//                + EMPLOYEE_PIN + TEXT_TYPE_COMMA
//                + EMPLOYEE_EMAIL + ")";
//        db.execSQL(CREATE_EMPLOYEE_TABLE);
        db.close();
    }
}
