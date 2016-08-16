package com.ashtonmansion.ashtonmansioncloverapp.dao;

import android.accounts.Account;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.util.Log;

import com.ashtonmansion.ashtonmansioncloverapp.webservices.employeews.EmployeeWebService;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v3.employees.AccountRole;
import com.clover.sdk.v3.employees.Employee;
import com.clover.sdk.v3.employees.EmployeeConnector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paul on 7/28/2016.
 */
public class EmployeeDAO extends SQLiteOpenHelper {
    //DATABASE STATIC VARIABLES AND UTILITY STRINGS////////GENERIC VARS
    private static final String DATABASE_NAME = "AshtonMansionDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE_COMMA = " TEXT,";
    //LOCAL EMPLOYEE TABLE
    private static final String TABLE_EMPLOYEE = "Employee";
    private static final String EMPLOYEE_ID = "Employee_ID";
    private static final String EMPLOYEE_NAME = "Employee_Name";
    private static final String EMPLOYEE_NICKNAME = "Employee_Nickname";
    private static final String EMPLOYEE_ROLE = "Employee_Role";
    private static final String EMPLOYEE_PIN = "Employee_Pin";
    private static final String EMPLOYEE_EMAIL = "Employee_Email";

    /* BELOW METHODS ARE BASICALLY COMPLETE *///////////
    //CONSTRUCTOR
    public EmployeeDAO(Context context) {
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

    public void createEmployeeTableIfNotExists() {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_EMPLOYEE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EMPLOYEE
                + "(" + EMPLOYEE_ID + " TEXT PRIMARY KEY,"
                + EMPLOYEE_NAME + TEXT_TYPE_COMMA
                + EMPLOYEE_NICKNAME + TEXT_TYPE_COMMA
                + EMPLOYEE_ROLE + TEXT_TYPE_COMMA
                + EMPLOYEE_PIN + TEXT_TYPE_COMMA
                + EMPLOYEE_EMAIL + ")";
        db.execSQL(CREATE_EMPLOYEE_TABLE);
        db.close();
    }

    public void dropLocalEmployeeTableIfExists() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_EMPLOYEE + "'");
        db.close();
    }

    public List<Employee> getLocalEmployeeRecords() {
        List<Employee> employeeList = new ArrayList<>();
        String selectAllQuery = "SELECT * FROM " + TABLE_EMPLOYEE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllQuery, null);

        //POPULATE LOCAL EMPLOYEE LIST
        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();
                //TODO IDS ARE GOING TO BE INCONSISTENT
                employee.setId(cursor.getString(0));
                employee.setName(cursor.getString(1));
                employee.setNickname(cursor.getString(2));
                //TODO FIX ROLES HERE
                employee.setRole(AccountRole.EMPLOYEE);
                employee.setPin(cursor.getString(4));
                employee.setEmail(cursor.getString(5));
                //UPON EMPLOYEE CREATION, ADD TO LIST
                employeeList.add(employee);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return employeeList;
    }

    public boolean addLocalEmployeeRecord(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EMPLOYEE_ID, employee.getId());
        values.put(EMPLOYEE_NAME, employee.getName());
        values.put(EMPLOYEE_NICKNAME, employee.getNickname());
        values.put(EMPLOYEE_ROLE, employee.getRole().toString());
        values.put(EMPLOYEE_PIN, employee.getPin());
        values.put(EMPLOYEE_EMAIL, employee.getEmail());

        long newLocalEmployeeID = db.insert(TABLE_EMPLOYEE, null, values);
        db.close();
        return (newLocalEmployeeID != -1);
    }

    public boolean deleteLocalEmployeeRecord(Employee employee) {
        int rowsDeleted = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            rowsDeleted = db.delete(TABLE_EMPLOYEE, EMPLOYEE_ID + "= ?", new String[]{employee.getId()});
        } catch (Exception e) {
            Log.e("Generic Exception", " in delete local record: " + e.getMessage());
        } finally {
            db.close();
        }
        return (rowsDeleted > 0);
    }
}