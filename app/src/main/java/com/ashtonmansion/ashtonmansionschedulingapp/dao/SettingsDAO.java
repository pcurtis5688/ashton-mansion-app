package com.ashtonmansion.ashtonmansionschedulingapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ashtonmansion.ashtonmansionschedulingapp.dbo.Settings;

/**
 * Created by paul on 7/29/2016.
 */
public class SettingsDAO extends SQLiteOpenHelper {
    //DATABASE STATIC VARIABLES AND UTILITY STRINGS
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AshtonMansionDB";
    private static final String TEXT_TYPE_COMMA = " TEXT,";

    //SETTINGS METHODS AND VARS
    private static final String TABLE_SETTINGS = "Settings";
    private static final String SETTINGS_MAX_APPT_HOURS = "max_appt_hours";
    private static final String SETTINGS_ADVANCE_ALERT_DAYS = "advance_alert_days";
    private static final String SETTINGS_WEEKDAY_ALERTS_ONLY = "weekday_alerts_only";
    private static final String SETTINGS_AVOID_HOLIDAY_ALERTS = "avoid_holiday_alerts";
    private static final String SETTINGS_DEFAULT_APPT_DURATION = "default_appt_duration";
    private static final String SETTINGS_ALERT_TIME_OF_DAY_HOUR = "alert_time_of_day_hour";
    private static final String SETTINGS_ALERT_TIME_OF_DAY_MINUTE = "alert_time_of_day_minute";

    public SettingsDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO WHAT NEEDS TO BE DONE HERE
        createSettingsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO LATER ON DECIDE ON DB UPGRADE FEATURES
    }

    private void createSettingsTable() {
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
            userSettings.set_alert_time_of_day_minute(Integer.parseInt(cursor.getString(cursor.getColumnIndex("alert_time_of_day_minute"))));

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
}
