package com.ashtonmansion.ashtonmansionschedulingapp.dbo;

/**
 * Created by paul on 7/18/2016.
 */
public class Settings {
    //Private vars
    private int _id;
    private int _max_appt_hours;
    private int _advance_alert_days;
    private int _default_duration;

    private boolean _alerts_weekdays_only;
    private boolean _avoid_holiday_alerts;

    private int _alert_time_of_day_hour;
    private int _alert_time_of_day_minute;


    public Settings() {
        //Empty constructor
    }

    //Constructor without ID
    public Settings(int _max_appt_hours, int _advance_alert_days, int _default_duration,
                    boolean _alerts_weekdays_only, boolean _avoid_holiday_alerts, int _alert_time_of_day_hour, int _alert_time_of_day_minute) {
        this._max_appt_hours = _max_appt_hours;
        this._advance_alert_days = _advance_alert_days;
        this._default_duration = _default_duration;

        this._alerts_weekdays_only = _alerts_weekdays_only;
        this._avoid_holiday_alerts = _avoid_holiday_alerts;

        this._alert_time_of_day_hour = _alert_time_of_day_hour;
        this._alert_time_of_day_minute = _alert_time_of_day_minute;
    }

    public int get_max_appt_hours() {
        return _max_appt_hours;
    }

    public void set_max_appt_hours(int _max_appt_hours) {
        this._max_appt_hours = _max_appt_hours;
    }

    public int get_advance_alert_days() {
        return _advance_alert_days;
    }

    public void set_advance_alert_days(int _advance_alert_days) {
        this._advance_alert_days = _advance_alert_days;
    }

    public int get_default_duration() {
        return _default_duration;
    }

    public void set_default_duration(int _default_duration) {
        this._default_duration = _default_duration;
    }

    public int get_alert_time_of_day_hour() {
        return _alert_time_of_day_hour;
    }

    public void set_alert_time_of_day_hour(int _alert_time_of_day_hour) {
        this._alert_time_of_day_hour = _alert_time_of_day_hour;
    }

    public int get_alert_time_of_day_minute() {
        return _alert_time_of_day_hour;
    }

    public void set_alert_time_of_day_minute(int _alert_time_of_day_minute) {
        this._alert_time_of_day_minute = _alert_time_of_day_minute;
    }


    public boolean is_alerts_weekdays_only() {
        return _alerts_weekdays_only;
    }

    public void set_alerts_weekdays_only(boolean _alerts_weekdays_only) {
        this._alerts_weekdays_only = _alerts_weekdays_only;
    }

    public boolean is_avoid_holiday_alerts() {
        return _avoid_holiday_alerts;
    }

    public void set_avoid_holiday_alerts(boolean _avoid_holiday_alerts) {
        this._avoid_holiday_alerts = _avoid_holiday_alerts;
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
