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
    private Spinner alertWeekdaysOnlySpinner;
    private Spinner avoidHolidayAlertsSpinner;
    private ArrayAdapter<String> maxApptHoursAdapter;
    private ArrayAdapter<String> advanceAlertDaysAdapter;
    private ArrayAdapter<String> defaultDurationAdapter;
    private ArrayAdapter<String> alertWeekdaysOnlyAdapter;
    private ArrayAdapter<String> avoidHolidayAlertsAdapter;
    private TimePicker alertTimePicker;
    //DATABASE VARS
    private Settings settingsFromDb;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        dbHandler = new DatabaseHandler(this);

        populateWeekdayAlertsOnlyDropdown();
        populateAvoidHolidayAlertsDropdown();
        populateMaxDurationSpinnerDropdown();
        populateAdvanceAlertDaysSpinnerDropdown();
        populateDefaultDurationSpinnerDropdown();

        // If settings already exist in db, get them and use them
        // to populate the settings page, else create the table
        if (dbHandler.hasSettings()) {
            //TODO THIS IS WHERE I STOPPED OFF DEBUGGING TIME OF DAY AND COLUMN INDEX CHANGES, CONTINUE
            settingsFromDb = dbHandler.getSettings();
            populateExistingSettings();
        } //TODO IS ELSE EVEN NEEDED ANYMORE?
    }

    //PRIVATE INTERNAL METHODS
    private void populateWeekdayAlertsOnlyDropdown() {
        alertWeekdaysOnlySpinner = (Spinner) findViewById(R.id.alert_weekday_only_spinner);

        List<String> alertsWeekdaysOnlyList = new ArrayList<String>();
        alertsWeekdaysOnlyList.add("Yes");
        alertsWeekdaysOnlyList.add("No");

        alertWeekdaysOnlyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, alertsWeekdaysOnlyList);
        alertWeekdaysOnlySpinner.setAdapter(alertWeekdaysOnlyAdapter);
    }

    private void populateAvoidHolidayAlertsDropdown() {
        avoidHolidayAlertsSpinner = (Spinner) findViewById(R.id.avoid_holiday_alerts_setting_spinner);

        List<String> avoidHolidayAlertsList = new ArrayList<String>();
        avoidHolidayAlertsList.add("Yes");
        avoidHolidayAlertsList.add("No");

        avoidHolidayAlertsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, avoidHolidayAlertsList);
        avoidHolidayAlertsSpinner.setAdapter(avoidHolidayAlertsAdapter);
    }

    private void populateMaxDurationSpinnerDropdown() {
        maxDurationSpinner = (Spinner) findViewById(R.id.max_appt_hours_setting_spinner);

        List<String> maxDurationHoursList = new ArrayList<String>();
        for (int i = 1; i < 49; i++) {
            maxDurationHoursList.add("" + i);
        }
        maxApptHoursAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, maxDurationHoursList);
        maxApptHoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maxDurationSpinner.setAdapter(maxApptHoursAdapter);

    }

    private void populateAdvanceAlertDaysSpinnerDropdown() {
        advanceAlertDaysSpinner = (Spinner) findViewById(R.id.advance_alert_days_setting_spinner);

        List<String> advanceAlertDaysList = new ArrayList<String>();
        for (int i = 1; i < 15; i++) {
            advanceAlertDaysList.add("" + i);
        }
        advanceAlertDaysAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, advanceAlertDaysList);
        advanceAlertDaysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        advanceAlertDaysSpinner.setAdapter(advanceAlertDaysAdapter);
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

    private void populateExistingSettings() {
        maxDurationSpinner.setSelection(maxApptHoursAdapter.getPosition("" + settingsFromDb.get_max_appt_hours()));
        advanceAlertDaysSpinner.setSelection(advanceAlertDaysAdapter.getPosition("" + settingsFromDb.get_advance_alert_days()));
        defaultDurationSpinner.setSelection(defaultDurationAdapter.getPosition("" + settingsFromDb.get_default_duration()));

        alertWeekdaysOnlySpinner = (Spinner) findViewById(R.id.alert_weekday_only_spinner);
        if (settingsFromDb.is_alerts_weekdays_only()) {
            alertWeekdaysOnlySpinner.setSelection(0);
        } else {
            alertWeekdaysOnlySpinner.setSelection(1);
        }

        avoidHolidayAlertsSpinner = (Spinner) findViewById(R.id.avoid_holiday_alerts_setting_spinner);
        if (settingsFromDb.is_avoid_holiday_alerts()) {
            avoidHolidayAlertsSpinner.setSelection(0);
        } else {
            avoidHolidayAlertsSpinner.setSelection(1);
        }

        // TODO API NOT HIGH ENOUGH FOR THIS.
        // alertTimePicker.setHour(settingsFromDb.get_alert_time_of_day_hour());
        //   alertTimePicker.setHour(settingsFromDb.get_alert_time_of_day_minute());

    }


    //PUBLIC METHODS CALLED FROM UI
    public void saveSettingsAndReturn(View view) {
        settings = new Settings();
        settings.set_max_appt_hours(Integer.parseInt(maxDurationSpinner.getSelectedItem().toString()));
        settings.set_advance_alert_days(Integer.parseInt(advanceAlertDaysSpinner.getSelectedItem().toString()));
        settings.set_default_duration(Integer.parseInt(defaultDurationSpinner.getSelectedItem().toString()));

        alertWeekdaysOnlySpinner = (Spinner) findViewById(R.id.alert_weekday_only_spinner);
        avoidHolidayAlertsSpinner = (Spinner) findViewById(R.id.avoid_holiday_alerts_setting_spinner);
        if (alertWeekdaysOnlySpinner.getSelectedItem().toString().equalsIgnoreCase("yes")) {
            settings.set_alerts_weekdays_only(true);
        } else {
            settings.set_alerts_weekdays_only(false);
        }
        if (avoidHolidayAlertsSpinner.getSelectedItem().toString().equalsIgnoreCase("yes")) {
            settings.set_avoid_holiday_alerts(true);
        } else {
            settings.set_avoid_holiday_alerts(false);
        }

        alertTimePicker = (TimePicker) findViewById(R.id.alert_time_of_day_setting_timepicker);
        // settings.set_alert_time_of_day_hour(alertTimePicker.getHour());
        // settings.set_alert_time_of_day_minute(alertTimePicker.getMinute());
        //TODO api does not supoprt these, fix

        dbHandler.saveSettings(settings);
        finish();
    }
}
