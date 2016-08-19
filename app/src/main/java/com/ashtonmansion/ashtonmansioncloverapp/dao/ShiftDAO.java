package com.ashtonmansion.ashtonmansioncloverapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ashtonmansion.ashtonmansioncloverapp.dbo.ShiftException;
import com.ashtonmansion.ashtonmansioncloverapp.dbo.ShiftTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paul on 8/7/2016.
 */
public class ShiftDAO extends SQLiteOpenHelper {
    //DATABASE STATIC VARIABLES AND UTILITY STRINGS
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AshtonMansionDB";
    private static final String TEXT_TYPE_COMMA = " TEXT, ";

    //SHIFT EXCEPTION TABLE AND COLUMN NAMES
    private static final String TABLE_SHIFT_EXCEPTION = "Shift_Exception";
    private static final String SHIFT_EXCEPTION_ID = "id_shift_exception";
    private static final String SHIFT_EXCEPTION_DATE = "exception_date";
    private static final String SHIFT_EXCEPTION_TIME = "exception_time";
    private static final String SHIFT_EXCEPTION_EMPLOYEE = "exception_employee";
    private static final String SHIFT_EXCEPTION_DURATION = "exception_duration";

    //SHIFT TEMPLATE TABLE AND COLUMN NAMES
    private static final String TABLE_SHIFT_TEMPLATE = "Shift_Template";
    private static final String SHIFT_TEMPLATE_ID = "id_shift_template";
    private static final String SHIFT_TEMPLATE_CODE = "shift_template_code";
    private static final String SHIFT_TEMPLATE_NAME = "shift_template_name";


    //CREATE, EDIT, OR DELETE SHIFT TEMPLATE METHODS
    public long addShiftTemplate(ShiftTemplate shiftTemplate) {
        long newShiftTemplateID;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SHIFT_TEMPLATE_CODE, shiftTemplate.getShiftCode());
        values.put(SHIFT_TEMPLATE_NAME, shiftTemplate.getShiftName());

        newShiftTemplateID = db.insert(TABLE_SHIFT_TEMPLATE, null, values);
        db.close();

        return newShiftTemplateID;
    }

    public void deleteShiftTemplate(String shiftTemplateID) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.delete(TABLE_SHIFT_TEMPLATE, SHIFT_TEMPLATE_ID + "= ?", new String[]{shiftTemplateID});
        } catch (Exception e) {
            Log.e("Exception occurred ", "while deleting shift template: " + e.getMessage());
        } finally {
            db.close();
        }
    }

    //CREATE, EDIT, OR DELETE SHIFT EXCEPTION METHODS
    //TODO THESE

    //SHIFT TEMPLATE OR EXCEPTION RETRIEVAL METHODS
    public List<ShiftTemplate> getAllShiftTemplates() {

        List<ShiftTemplate> shiftTemplateList = new ArrayList<ShiftTemplate>();
        String selectAllQuery = "SELECT * FROM " + TABLE_SHIFT_TEMPLATE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllQuery, null);

        //POPULATE SHIFT TEMPLATE LIST
        if (cursor.moveToFirst()) {
            do {
                ShiftTemplate shiftTemplate = new ShiftTemplate();
                shiftTemplate.setShiftID(cursor.getString(0));
                shiftTemplate.setShiftCode(cursor.getString(1));
                shiftTemplate.setShiftName(cursor.getString(2));
                //UPON CREATION, ADD TO LIST
                shiftTemplateList.add(shiftTemplate);
            } while (cursor.moveToNext());
        }
        return shiftTemplateList;
    }

    public List<ShiftException> getAllShiftExceptions() {
        List<ShiftException> shiftExceptions = new ArrayList<>();
        String selectAllQuery = "SELECT * FROM " + TABLE_SHIFT_EXCEPTION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllQuery, null);

        //POPULATE SHIFT TEMPLATE LIST
        if (cursor.moveToFirst()) {
            do {
                ShiftException shiftException = new ShiftException();

                shiftException.setShiftExceptionID(cursor.getInt(0));
                shiftException.setEmployeeID(cursor.getString(1));
                shiftException.setShiftExceptionDate(cursor.getString(2));
                shiftException.setShiftExceptionTime(cursor.getString(3));
                shiftException.setEmployeeID(cursor.getString(1));
                shiftException.setDuration(cursor.getLong(4));

                shiftExceptions.add(shiftException);
            } while (cursor.moveToNext());
        }
        return shiftExceptions;
    }

    //CREATE OR DROP SHIFT TABLES
    public void createShiftTemplateTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_SHIFT_TEMPLATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SHIFT_TEMPLATE
                + "(" + SHIFT_TEMPLATE_ID + " INTEGER PRIMARY KEY, "
                + SHIFT_TEMPLATE_CODE + TEXT_TYPE_COMMA
                + SHIFT_TEMPLATE_NAME + " TEXT)";
        db.execSQL(CREATE_SHIFT_TEMPLATE_TABLE);
        db.close();
    }

    public void createShiftExceptionTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_SHIFT_EXCEPTION_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SHIFT_EXCEPTION
                + "(" + SHIFT_EXCEPTION_ID + " INTEGER PRIMARY KEY, "
                + SHIFT_EXCEPTION_DATE + TEXT_TYPE_COMMA
                + SHIFT_EXCEPTION_TIME + TEXT_TYPE_COMMA
                + SHIFT_EXCEPTION_EMPLOYEE + TEXT_TYPE_COMMA
                + SHIFT_EXCEPTION_DURATION + " TEXT)";
        db.execSQL(CREATE_SHIFT_EXCEPTION_TABLE);
        db.close();
    }

    public void dropShiftTemplateTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_SHIFT_TEMPLATE + "'");
        db.close();
    }

    public void dropShiftExceptionTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_SHIFT_EXCEPTION + "'");
        db.close();
    }

    //CONSTRUCTOR, OVERRIDDEN METHODS
    public ShiftDAO(Context context) {
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
}
