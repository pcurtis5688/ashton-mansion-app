package com.ashtonmansion.ashtonmansionapp.dao;

import android.accounts.Account;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ashtonmansion.ashtonmansionapp.dbo.Appointment;
import com.ashtonmansion.ashtonmansionapp.dbo.Employee;
import com.ashtonmansion.ashtonmansionapp.dbo.Settings;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v3.employees.EmployeeConnector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paul on 7/18/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    //DATABASE STATIC VARIABLES AND UTILITY STRINGS
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AshtonMansionDB";
    private static final String TEXT_TYPE_COMMA = " TEXT,";

    //TABLE NAMES
    private static final String TABLE_APPOINTMENT = "Appointment";
    private static final String TABLE_EMPLOYEE = "Employee";

    //APPOINTMENT TABLE COLUMN NAMES
    private static final String APPOINTMENT_ID = "appt_id";
    private static final String APPOINTMENT_DATE = "date";
    private static final String APPOINTMENT_START_TIME = "start_time";
    private static final String APPOINTMENT_DURATION = "duration";
    private static final String APPOINTMENT_CUSTOMER_CODE = "customer_code";
    private static final String APPOINTMENT_ALERT_TYPE = "alert_type";
    private static final String APPOINTMENT_ITEM_CODE = "item_code";
    private static final String APPOINTMENT_NOTE = "note";
    private static final String APPOINTMENT_EMPLOYEE_CODE_1 = "employee_code_1";
    private static final String APPOINTMENT_EMPLOYEE_CODE_2 = "employee_code_2";
    private static final String APPOINTMENT_CONFIRM_STATUS = "confirm_status";

    //EMPLOYEE TABLE COLUMN NAMES
    private static final String EMPLOYEE_ID = "employee_id";
    private static final String EMPLOYEE_NAME = "name";
    private static final String EMPLOYEE_NICKNAME = "nickname";
    private static final String EMPLOYEE_ROLE = "role";
    private static final String EMPLOYEE_PIN = "pin";
    private static final String EMPLOYEE_EMAIL = "email";

    //DATABASE HANDLER METHODS
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO SHOULD THIS ALSO BE TESTED AND A TABLE CREATED
        String CREATE_APPOINTMENT_TABLE = "CREATE TABLE " + TABLE_APPOINTMENT
                + "(" + APPOINTMENT_ID + " INTEGER PRIMARY KEY,"
                + APPOINTMENT_DATE + TEXT_TYPE_COMMA
                + APPOINTMENT_START_TIME + TEXT_TYPE_COMMA
                + APPOINTMENT_DURATION + TEXT_TYPE_COMMA
                + APPOINTMENT_CUSTOMER_CODE + TEXT_TYPE_COMMA
                + APPOINTMENT_ALERT_TYPE + TEXT_TYPE_COMMA
                + APPOINTMENT_ITEM_CODE + TEXT_TYPE_COMMA
                + APPOINTMENT_NOTE + TEXT_TYPE_COMMA
                + APPOINTMENT_EMPLOYEE_CODE_1 + TEXT_TYPE_COMMA
                + APPOINTMENT_EMPLOYEE_CODE_2 + TEXT_TYPE_COMMA
                + APPOINTMENT_CONFIRM_STATUS + ")";
        db.execSQL(CREATE_APPOINTMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO IMPLEMENT THIS METHOD LATER
    }

    //INSERT AN APPOINTMENT RECORD INTO THE APPOINTMENT TABLES
    public void addAppointment(Appointment appointment) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(APPOINTMENT_DATE, appointment.get_date().toString());
        values.put(APPOINTMENT_START_TIME, appointment.get_start_time());
        values.put(APPOINTMENT_DURATION, appointment.get_duration());
        values.put(APPOINTMENT_CUSTOMER_CODE, appointment.get_customer_code());
        values.put(APPOINTMENT_ALERT_TYPE, appointment.get_alert_type());
        values.put(APPOINTMENT_ITEM_CODE, appointment.get_item_code());
        values.put(APPOINTMENT_NOTE, appointment.get_note());
        values.put(APPOINTMENT_EMPLOYEE_CODE_1, appointment.get_employee_code_1());
        values.put(APPOINTMENT_EMPLOYEE_CODE_2, appointment.get_employee_code_2());
        values.put(APPOINTMENT_CONFIRM_STATUS, appointment.get_confirm_status());

        db.insert(TABLE_APPOINTMENT, null, values);
        db.close();
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> appointmentList = new ArrayList<Appointment>();
        //SELECT ALL APPOINTMENTS
        String selectAllQuery = "SELECT * FROM " + TABLE_APPOINTMENT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllQuery, null);

        //POPULATE APPOINTMENT LIST
        if (cursor.moveToFirst()) {
            do {
                Appointment appointment = new Appointment();
                appointment.set_id(Integer.parseInt(cursor.getString(0)));
                appointment.set_date(cursor.getString(1));
                appointment.set_start_time(cursor.getString(2));
                appointment.set_duration(Integer.parseInt(cursor.getString(3)));
                appointment.set_customer_code(cursor.getString(4));
                appointment.set_alert_type(cursor.getString(5));
                appointment.set_item_code(cursor.getString(6));
                appointment.set_note(cursor.getString(7));
                appointment.set_employee_code_1(Integer.parseInt(cursor.getString(8)));
                appointment.set_employee_code_2(Integer.parseInt(cursor.getString(9)));
                appointment.set_confirm_status(cursor.getString(10));
                //UPON APPOINTMENT CREATION, ADD TO LIST
                appointmentList.add(appointment);
            } while (cursor.moveToNext());
        }
        return appointmentList;
    }

    public void addEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

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
        //    Account merAcct = CloverAccount.getAccount();
        //     EmployeeConnector empConn = new EmployeeConnector();
    }

    //SETTINGS METHODS AND VARS
    private static final String TABLE_SETTINGS = "Settings";
    private static final String SETTINGS_MAX_APPT_HOURS = "max_appt_hours";
    private static final String SETTINGS_ADVANCE_ALERT_DAYS = "advance_alert_days";
    private static final String SETTINGS_WEEKDAY_ALERTS_ONLY = "weekday_alerts_only";
    private static final String SETTINGS_AVOID_HOLIDAY_ALERTS = "avoid_holiday_alerts";
    private static final String SETTINGS_DEFAULT_APPT_DURATION = "default_appt_duration";
    private static final String SETTINGS_ALERT_TIME_OF_DAY_HOUR = "alert_time_of_day_hour";
    private static final String SETTINGS_ALERT_TIME_OF_DAY_MINUTE = "alert_time_of_day_minute";

    public void createSettingsTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_SETTINGS_TABLE = "CREATE TABLE " + TABLE_SETTINGS
                + "(" + SETTINGS_MAX_APPT_HOURS + TEXT_TYPE_COMMA
                + SETTINGS_ADVANCE_ALERT_DAYS + TEXT_TYPE_COMMA
                + SETTINGS_ALERT_TIME_OF_DAY_HOUR + TEXT_TYPE_COMMA
                + SETTINGS_ALERT_TIME_OF_DAY_MINUTE + TEXT_TYPE_COMMA
                + SETTINGS_WEEKDAY_ALERTS_ONLY + TEXT_TYPE_COMMA
                + SETTINGS_AVOID_HOLIDAY_ALERTS + TEXT_TYPE_COMMA
                + SETTINGS_DEFAULT_APPT_DURATION + ")";
        db.execSQL(CREATE_SETTINGS_TABLE);
    }

    public boolean hasSettings() {
        SQLiteDatabase db = this.getReadableDatabase();
        String checkSettingsTableSql = "SELECT COUNT(*) FROM sqlite_master WHERE type='table' AND name='Settings'";
        Cursor cursor = db.rawQuery(checkSettingsTableSql, null);

        if (!cursor.moveToFirst()) {
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    public Settings getSettings() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectSettingsQuery = "SELECT * FROM SETTINGS";
        Cursor cursor = db.rawQuery(selectSettingsQuery, null);
        Settings userSettings = new Settings();

        if (cursor.moveToFirst()) {
            userSettings.set_max_appt_hours(Integer.parseInt(cursor.getString(cursor.getColumnIndex("max_appt_hours"))));
            userSettings.set_advance_alert_days(Integer.parseInt(cursor.getString(cursor.getColumnIndex("advance_alert_days"))));
            userSettings.set_default_duration(Integer.parseInt(cursor.getString(cursor.getColumnIndex("default_appt_duration"))));

            userSettings.set_alert_time_of_day_hour(Integer.parseInt(cursor.getString(cursor.getColumnIndex("alert_time_of_day_hour"))));
            userSettings.set_alert_time_of_day_minute(Integer.parseInt(cursor.getString(cursor.getColumnIndex("alert_timeof_day_minute"))));

            if (cursor.getString(cursor.getColumnIndex("weekday_alerts_only")).equalsIgnoreCase("true")) {
                userSettings.set_alerts_weekdays_only(true);
            } else {
                userSettings.set_alerts_weekdays_only(false);
            }
            if (cursor.getString(cursor.getColumnIndex("avoid_holiday_alerts")).equalsIgnoreCase("true")) {
                userSettings.set_avoid_holiday_alerts(true);
            } else {
                userSettings.set_avoid_holiday_alerts(false);
            }

            //TODO FIGURE OUT THIS EXCEPTION
        }
        return userSettings;
    }

    public void saveSettings(Settings settings) {
        clearSettings();
        createSettingsTable();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SETTINGS_MAX_APPT_HOURS, settings.get_max_appt_hours());
        values.put(SETTINGS_ADVANCE_ALERT_DAYS, settings.get_advance_alert_days());
        values.put(SETTINGS_DEFAULT_APPT_DURATION, settings.get_default_duration());

        if (settings.is_alerts_weekdays_only()) {
            values.put(SETTINGS_WEEKDAY_ALERTS_ONLY, "true");
        } else {
            values.put(SETTINGS_WEEKDAY_ALERTS_ONLY, "false");
        }
        if (settings.is_avoid_holiday_alerts()) {
            values.put(SETTINGS_AVOID_HOLIDAY_ALERTS, "true");
        } else {
            values.put(SETTINGS_AVOID_HOLIDAY_ALERTS, "false");
        }

        values.put(SETTINGS_ALERT_TIME_OF_DAY_HOUR, settings.get_alert_time_of_day_hour());
        values.put(SETTINGS_ALERT_TIME_OF_DAY_MINUTE, settings.get_alert_time_of_day_minute());

        db.insert(TABLE_SETTINGS, null, values);
        db.close();
    }

    public void clearSettings() {
        SQLiteDatabase db = this.getWritableDatabase();
        // String dropSettingsTableSql = "DROP TABLE SETTINGS";
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
    }

    //TODO FIGURE OUT WHERE I SHOULD IMPLEMENT THE BELOW FOR TABLE
    //TODO CREATION UPON APPINSTALLATION
    //TODO         createSettingsTable(db);
}
