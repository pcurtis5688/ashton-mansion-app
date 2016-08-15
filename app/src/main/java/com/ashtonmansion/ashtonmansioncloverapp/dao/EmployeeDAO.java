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

    public void createEmployeeInClover(final Employee employee, final Context context) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                Account merAcct = CloverAccount.getAccount(context);
                EmployeeConnector empConn = new EmployeeConnector(context, merAcct, null);

                try {
                    empConn.createEmployee(employee);
                } catch (RemoteException e2) {
                    Log.e("Remote EXC: ", e2.getMessage());
                } catch (ServiceException e3) {
                    Log.e("ServiceException EXC: ", e3.getMessage());
                } catch (ClientException e4) {
                    Log.e("ClientException EXC: ", e4.getMessage());
                } catch (BindingException e5) {
                    Log.e("BindingException EXC: ", e5.getMessage());
                } finally {
                    empConn.disconnect();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
            }

            //TODO CHECK HERE IF SUCCESS INSERT ALSO INSERT TO CLOVER?
            //TODO also should I create common classes for these types of methods related to clover accounts
        }.execute();
    }

    private boolean createEmployeeWSSuccess;

    public boolean insertEmployeeDynamics(final Employee employee) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                EmployeeWebService employeeWebService = new EmployeeWebService();
                createEmployeeWSSuccess = employeeWebService.createEmployeeServiceCall(employee);
                Log.i("Result WSDoInBG:", "" + createEmployeeWSSuccess);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                //todo progressDialog.dismiss()?
            }
        }.execute();
        return createEmployeeWSSuccess;
    }

    public long addLocalEmployeeRecord(Employee employee) {
        long newEmployeeID;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EMPLOYEE_NAME, employee.getName());
        values.put(EMPLOYEE_NICKNAME, employee.getNickname());
        //todo values.put(EMPLOYEE_ROLE, employee.get_role());
        values.put(EMPLOYEE_PIN, employee.getPin());
        values.put(EMPLOYEE_EMAIL, employee.getEmail());

        newEmployeeID = db.insert(TABLE_EMPLOYEE, null, values);
        Log.i("LocalRecSuccess: ", "" + (newEmployeeID != -1));
        db.close();
        return newEmployeeID;
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

    public void createEmployeeTableIfNotExists() {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_EMPLOYEE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EMPLOYEE
                + "(" + EMPLOYEE_ID + " INTEGER PRIMARY KEY,"
                + EMPLOYEE_NAME + TEXT_TYPE_COMMA
                + EMPLOYEE_NICKNAME + TEXT_TYPE_COMMA
                + EMPLOYEE_ROLE + TEXT_TYPE_COMMA
                + EMPLOYEE_PIN + TEXT_TYPE_COMMA
                + EMPLOYEE_EMAIL + ")";
        db.execSQL(CREATE_EMPLOYEE_TABLE);
        db.close();
    }
}