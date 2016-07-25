package com.ashtonmansion.ashtonmansionapp.dbo;

import java.sql.Time;

/**
 * Created by paul on 7/18/2016.
 */
public class Settings {
    //Private vars
    private int _id;
    private int _max_appt_hours;
    private int _advance_alert_days;
    private String _alert_time_of_day;
    private boolean _alerts_weekdays_only;
    private boolean _avoid_holiday_alerts;
    private int _default_duration;

    public Settings() {
        //Empty constructor
    }

    //Constructor without ID
    public Settings(int _max_appt_hours, int _advance_alert_days, String _alert_time_of_day,
                    boolean _alerts_weekdays_only, boolean _avoid_holiday_alerts, int _default_duration) {
        this._max_appt_hours = _max_appt_hours;
        this._advance_alert_days = _advance_alert_days;
        this._alert_time_of_day = _alert_time_of_day;
        this._alerts_weekdays_only = _alerts_weekdays_only;
        this._avoid_holiday_alerts = _avoid_holiday_alerts;
        this._default_duration = _default_duration;
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

    public String get_alert_time_of_day() {
        return _alert_time_of_day;
    }

    public void set_alert_time_of_day(String _alert_time_of_day) {
        this._alert_time_of_day = _alert_time_of_day;
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

    public int get_default_duration() {
        return _default_duration;
    }

    public void set_default_duration(int _default_duration) {
        this._default_duration = _default_duration;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
