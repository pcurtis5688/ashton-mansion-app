package com.ashtonmansion.ashtonmansioncloverapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ashtonmansion.ashtonmansioncloverapp.dbo.Appointment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paul on 7/29/2016.
 */
public class AppointmentDAO extends SQLiteOpenHelper {
    //DATABASE STATIC VARIABLES AND UTILITY STRINGS
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AshtonMansionDB";
    private static final String TEXT_TYPE_COMMA = " TEXT,";

    //TABLE NAMES
    private static final String TABLE_APPOINTMENT = "Appointment";

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

    public AppointmentDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO SHOULD THIS ALSO BE TESTED AND A TABLE CREATED

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO LATER ON DECIDE ON DB UPGRADE FEATURES
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
                appointment.set_id(cursor.getString(0));
                appointment.set_date(cursor.getString(1));
                appointment.set_start_time(cursor.getString(2));
                appointment.set_duration(cursor.getString(3));
                appointment.set_customer_code(cursor.getString(4));
                appointment.set_alert_type(cursor.getString(5));
                appointment.set_item_code(cursor.getString(6));
                appointment.set_note(cursor.getString(7));
                appointment.set_employee_code_1(cursor.getString(8));
                appointment.set_employee_code_2(cursor.getString(9));
                appointment.set_confirm_status(cursor.getString(10));
                //UPON APPOINTMENT CREATION, ADD TO LIST
                appointmentList.add(appointment);
            } while (cursor.moveToNext());
        }
        return appointmentList;
    }

    public void createAppointmentTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_APPOINTMENT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_APPOINTMENT
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
        db.close();
    }
}
