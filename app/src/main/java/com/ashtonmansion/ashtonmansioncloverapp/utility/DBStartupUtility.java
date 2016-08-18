package com.ashtonmansion.ashtonmansioncloverapp.utility;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ashtonmansion.ashtonmansioncloverapp.dao.AppointmentDAO;
import com.ashtonmansion.ashtonmansioncloverapp.dao.CustomerDAO;
import com.ashtonmansion.ashtonmansioncloverapp.dao.EmployeeDAO;
import com.ashtonmansion.ashtonmansioncloverapp.dao.InventoryDAO;
import com.ashtonmansion.ashtonmansioncloverapp.dao.ShiftDAO;

/**
 * Created by paul on 8/13/2016.
 */
public class DBStartupUtility extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "AshtonMansionDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE_COMMA = " TEXT,";
    private boolean hasEmployeeTable = false;
    private boolean hasCustomerTable = false;
    private boolean hasInventoryTable = false;
    private boolean hasAppointmentTable = false;
    private boolean hasShiftTemplateTable = false;
    private boolean hasShiftExceptionTable = false;
    //TODO This should still not be done on *every* creation of mainactivity

    public void checkDatabase(Context context) {
        SQLiteDatabase db = getReadableDatabase();
        String queryAppointmentTable = checkTable("Appointment");
        String queryEmployeeTable = checkTable("Employee");
        String queryCustomerTable = checkTable("Customer");
        String queryInventoryTable = checkTable("Inventory_Item");
        String queryShiftTemplateTable = checkTable("Shift_Template");
        String queryShiftExceptionTable = checkTable("Shift_Exception");

        ///////
        Cursor cursor = db.rawQuery(queryAppointmentTable, null);
        if (cursor.moveToFirst()) {
            hasAppointmentTable = true;
            Log.i("Table Located", "Appointment");
        } else {
            Log.e("Needs Table, Creating: ", "Appointment");
            AppointmentDAO appointmentDAO = new AppointmentDAO(context);
            appointmentDAO.createAppointmentTable();
        }
        //////
        cursor = db.rawQuery(queryEmployeeTable, null);
        if (cursor.moveToFirst()) {
            hasEmployeeTable = true;
            Log.i("Table Located", "Employee");
        } else {
            Log.e("Needs Table, Creating: ", "Employee");
            EmployeeDAO employeeDAO = new EmployeeDAO(context);
            employeeDAO.createEmployeeTableIfNotExists();
        }
        //////
        cursor = db.rawQuery(queryCustomerTable, null);
        if (cursor.moveToFirst()) {
            hasCustomerTable = true;
            Log.i("Table Located", "Customer");
        } else {
            Log.e("Needs Table, Creating: ", "Customer");
            CustomerDAO customerDAO = new CustomerDAO(context);
            customerDAO.createCustomerTableIfNotExists();
        }
        //////
        cursor = db.rawQuery(queryInventoryTable, null);
        if (cursor.moveToFirst()) {
            hasInventoryTable = true;
            Log.i("Table Located", "Inventory_Item");
        } else {
            Log.e("Needs Table, Creating: ", "Inventory_Item");
            InventoryDAO inventoryDAO = new InventoryDAO(context);
            inventoryDAO.createItemTableIfNotExists();
        }
        //////
        cursor = db.rawQuery(queryShiftTemplateTable, null);
        if (cursor.moveToFirst()) {
            hasShiftTemplateTable = true;
            Log.i("Table Located", "Shift_Template");
        } else {
            Log.e("Needs Table, Creating: ", "Shift_Template");
            ShiftDAO shiftDAO = new ShiftDAO(context);
            shiftDAO.createShiftTemplateTable();
        }
        //////
        cursor = db.rawQuery(queryShiftExceptionTable, null);
        if (cursor.moveToFirst()) {
            hasShiftExceptionTable = true;
            Log.i("Table Located", "Shift_Exception");
        } else {
            Log.e("Needs Table, Creating: ", "Shift_Exception");
            ShiftDAO shiftDAO = new ShiftDAO(context);
            shiftDAO.createShiftExceptionTable();
        }
    }

    public DBStartupUtility(Context context) {
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

    public static String checkTable(String table_name) {
        String selectAllQuery = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + table_name + "'";
        return selectAllQuery;
    }
}
