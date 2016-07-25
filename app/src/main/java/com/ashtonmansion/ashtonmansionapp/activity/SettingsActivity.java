package com.ashtonmansion.ashtonmansionapp.activity;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.ashtonmansion.ashtonmansionapp.R;
import com.ashtonmansion.ashtonmansionapp.dao.DatabaseHandler;
import com.ashtonmansion.ashtonmansionapp.dbo.Settings;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    //PRIVATE SETTINGS VARS
    private Settings settings;
    private int max_appt_hours;
    private int advance_alert_days;
    private boolean alerts_weekdays_only;
    private boolean avoid_holiday_alerts;
    private int default_duration;
    //DYNAMICALLY CREATED FIELDS
    private Spinner maxDurationSpinner;
    private Spinner advanceAlertDaysSpinner;
    private Spinner defaultDurationSpinner;
    private ArrayAdapter<String> defaultDurationAdapter;
    private TimePicker alertTimePicker;
    private Spinner alertsWeekdaysOnlySpinner;
    private Spinner avoidHolidayAlertsSpinner;
    //DATABASE VARS
    private Settings settingsFromDb;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        dbHandler = new DatabaseHandler(this);

        populateMaxDurationSpinnerDropdown();
        populateAdvanceAlertDaysSpinnerDropdown();
        populateDefaultDurationSpinnerDropdown();

        // If settings already exist in db, get them and use them
        // to populate the settings page, else create the table
        if (dbHandler.hasSettings()) {
            settingsFromDb = dbHandler.getSettings();
            maxDurationSpinner = (Spinner) findViewById(R.id.max_appt_hours_setting_spinner);
            maxDurationSpinner.setSelection(defaultDurationAdapter.getPosition("" + settingsFromDb.get_default_duration()));
        }
    }

    public void saveSettingsAndReturn(View view) {
        settings = new Settings();
        settings.set_max_appt_hours(Integer.parseInt(maxDurationSpinner.getSelectedItem().toString()));
        settings.set_advance_alert_days(Integer.parseInt(advanceAlertDaysSpinner.getSelectedItem().toString()));
        alertTimePicker = (TimePicker) findViewById(R.id.alert_time_of_day_setting_timepicker);
        //TODO IMPLEMENT THIS TIMEPICKER

        alertsWeekdaysOnlySpinner = (Spinner) findViewById(R.id.weekday_alerts_only_setting_spinner);
        avoidHolidayAlertsSpinner = (Spinner) findViewById(R.id.avoid_holiday_alerts_setting_spinner);
        if (alertsWeekdaysOnlySpinner.getSelectedItem().toString().equalsIgnoreCase("yes")) {
            settings.set_alerts_weekdays_only(true);
        } else {
            settings.set_alerts_weekdays_only(false);
        }
        if (avoidHolidayAlertsSpinner.getSelectedItem().toString().equalsIgnoreCase("yes")) {
            settings.set_avoid_holiday_alerts(true);
        } else {
            settings.set_avoid_holiday_alerts(false);
        }
        settings.set_default_duration(Integer.parseInt(defaultDurationSpinner.getSelectedItem().toString()));
        dbHandler.saveSettings(settings);
        finish();
    }

    private void populateMaxDurationSpinnerDropdown() {
        maxDurationSpinner = (Spinner) findViewById(R.id.max_appt_hours_setting_spinner);

        List<String> maxDurationHoursList = new ArrayList<String>();
        for (int i = 1; i < 49; i++) {
            maxDurationHoursList.add("" + i);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, maxDurationHoursList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maxDurationSpinner.setAdapter(arrayAdapter);

    }

    private void populateAdvanceAlertDaysSpinnerDropdown() {
        advanceAlertDaysSpinner = (Spinner) findViewById(R.id.advance_alert_days_setting_spinner);

        List<String> advanceAlertDaysList = new ArrayList<String>();
        for (int i = 1; i < 15; i++) {
            advanceAlertDaysList.add("" + i);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, advanceAlertDaysList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        advanceAlertDaysSpinner.setAdapter(arrayAdapter);
    }

    private void populateDefaultDurationSpinnerDropdown() {
        defaultDurationSpinner = (Spinner) findViewById(R.id.default_appt_duration_spinner);

        List<String> defaultDurationList = new ArrayList<String>();
        for (int i = 1; i < 11; i++) {
            defaultDurationList.add("" + i);
        }
        defaultDurationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, defaultDurationList);
        defaultDurationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        defaultDurationSpinner.setAdapter(defaultDurationAdapter);
    }
}
