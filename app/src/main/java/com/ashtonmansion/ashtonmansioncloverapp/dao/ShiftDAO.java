package com.ashtonmansion.ashtonmansioncloverapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    private static final String TEXT_TYPE = " TEXT ";

    //TABLE NAMES
    private static final String TABLE_SHIFT_TEMPLATE = "ShiftTemplate";

    //SHIFT TEMPLATE TABLE COLUMN NAMES
    private static final String SHIFT_TEMPLATE_ID = "id_shift_template";
    private static final String SHIFT_TEMPLATE_CODE = "shift_template_code";
    private static final String SHIFT_TEMPLATE_NAME = "shift_template_name";


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


    //INSERT A SHIFT TEMPLATE RECORD INTO TABLE
    public void addShiftTemplate(int shiftTemplateCode, String shiftTemplateName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SHIFT_TEMPLATE_CODE, shiftTemplateCode);
        values.put(SHIFT_TEMPLATE_NAME, shiftTemplateName);

        db.insert(TABLE_SHIFT_TEMPLATE, null, values);
        db.close();
    }

    //GET ALL SHIFT TEMPLATES
    public List<ShiftTemplate> getAllShiftTemplates() {

        List<ShiftTemplate> shiftTemplateList = new ArrayList<ShiftTemplate>();
        String selectAllQuery = "SELECT * FROM " + TABLE_SHIFT_TEMPLATE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllQuery, null);

        //POPULATE SHIFT TEMPLATE LIST
        if (cursor.moveToFirst()) {
            do {
                ShiftTemplate shiftTemplate = new ShiftTemplate();
                shiftTemplate.setShiftID(cursor.getInt(0));
                shiftTemplate.setShiftCode(cursor.getInt(1));
                shiftTemplate.setShiftName(cursor.getString(2));
                //UPON CREATION, ADD TO LIST
                shiftTemplateList.add(shiftTemplate);
            } while (cursor.moveToNext());
        }
        return shiftTemplateList;
    }

    public void createShiftTemplateTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_SHIFT_TEMPLATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SHIFT_TEMPLATE
                + "(" + SHIFT_TEMPLATE_ID + " INTEGER PRIMARY KEY, "
                + SHIFT_TEMPLATE_CODE + " INTEGER, "
                + SHIFT_TEMPLATE_NAME + TEXT_TYPE + ")";
        db.execSQL(CREATE_SHIFT_TEMPLATE_TABLE);
        db.close();
    }
}
