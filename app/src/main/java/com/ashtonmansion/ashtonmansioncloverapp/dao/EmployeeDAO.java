package com.ashtonmansion.ashtonmansioncloverapp.dao;

import android.accounts.Account;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.RemoteException;
import android.util.Log;

import com.ashtonmansion.ashtonmansioncloverapp.dbo.Employee;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v3.employees.EmployeeConnector;

/**
 * Created by paul on 7/28/2016.
 */
public class EmployeeDAO extends SQLiteOpenHelper {
    //DATABASE STATIC VARIABLES AND UTILITY STRINGS
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AshtonMansionDB";
    private static final String TEXT_TYPE_COMMA = " TEXT,";
    //EMPLOYEE TABLE COLUMN NAMES
    private static final String TABLE_EMPLOYEE = "Employee";

    private static final String EMPLOYEE_ID = "employee_id";
    private static final String EMPLOYEE_NAME = "name";
    private static final String EMPLOYEE_NICKNAME = "nickname";
    private static final String EMPLOYEE_ROLE = "role";
    private static final String EMPLOYEE_PIN = "pin";
    private static final String EMPLOYEE_EMAIL = "email";

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

    public void insertEmployeeIntoNAV(Employee employee, Context context) {


    }

    public void addLocalEmployeeRecord(Employee employee, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        createEmployeeTable();

        ContentValues values = new ContentValues();
        values.put(EMPLOYEE_NAME, employee.get_name());
        values.put(EMPLOYEE_NICKNAME, employee.get_nickname());
        values.put(EMPLOYEE_ROLE, employee.get_role());
        values.put(EMPLOYEE_PIN, employee.get_loginPIN());
        values.put(EMPLOYEE_EMAIL, employee.get_email());

        db.insert(TABLE_EMPLOYEE, null, values);
        db.close();


        //TODO CHECK HERE IF SUCCESS INSERT ALSO INSERT TO CLOVER?
        //TODO also should I create common classes for these types of methods related to clover accounts
        Account merAcct = CloverAccount.getAccount(context);
        EmployeeConnector empConn = new EmployeeConnector(context, merAcct, null);
        com.clover.sdk.v3.employees.Employee cloverEmployee = new com.clover.sdk.v3.employees.Employee();
        cloverEmployee.setName(employee.get_name());
        cloverEmployee.setNickname(employee.get_nickname());
        // TODO THIS: cloverEmployee.setRole();
        cloverEmployee.setPin(employee.get_loginPIN());
        cloverEmployee.setEmail(employee.get_email());
        try {
            empConn.createEmployee(cloverEmployee);
        } catch (RemoteException | ClientException | ServiceException | BindingException e) {
            Log.i("Clover Exception: ", e.toString());
        }
    }


    private void createEmployeeTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_EMPLOYEE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EMPLOYEE
                + "(" + EMPLOYEE_NAME + TEXT_TYPE_COMMA
                + EMPLOYEE_NICKNAME + TEXT_TYPE_COMMA
                + EMPLOYEE_ROLE + TEXT_TYPE_COMMA
                + EMPLOYEE_PIN + TEXT_TYPE_COMMA
                + EMPLOYEE_EMAIL + ")";
        db.execSQL(CREATE_EMPLOYEE_TABLE);
        db.close();
    }
}
