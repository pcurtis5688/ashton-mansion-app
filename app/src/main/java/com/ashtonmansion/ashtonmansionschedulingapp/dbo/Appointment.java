package com.ashtonmansion.ashtonmansionschedulingapp.dbo;

/**
 * Created by paul on 7/18/2016.
 */
public class Appointment {
    //Private vars
    private int _id;
    private String _date;
    private String _start_time;
    private int _duration;
    private String _customer_code;
    private String _alert_type;
    private String _item_code;
    private String _note;
    private int _employee_code_1;
    private int _employee_code_2;
    private String _confirm_status;

    public Appointment() {
        //Empty constructor
    }

    //Constructor with ID param
    public Appointment(int id, String date, String start_time, int duration, String customer_code,
                       String alert_type, String item_code, String note, int employee_code_1,
                       int employee_code_2, String confirm_status) {
        this._id = id;
        this._date = date;
        this._start_time = start_time;
        this._duration = duration;
        this._customer_code = customer_code;
        this._alert_type = alert_type;
        this._item_code = item_code;
        this._note = note;
        this._employee_code_1 = employee_code_1;
        this._employee_code_2 = employee_code_2;
        this._confirm_status = confirm_status;
    }

    //Constructor without ID param
    public Appointment(String date, String start_time, int duration, String customer_code,
                       String alert_type, String item_code, String note, int employee_code_1,
                       int employee_code_2, String confirm_status) {
        this._date = date;
        this._start_time = start_time;
        this._duration = duration;
        this._customer_code = customer_code;
        this._alert_type = alert_type;
        this._item_code = item_code;
        this._note = note;
        this._employee_code_1 = employee_code_1;
        this._employee_code_2 = employee_code_2;
        this._confirm_status = confirm_status;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_start_time() {
        return _start_time;
    }

    public void set_start_time(String _start_time) {
        this._start_time = _start_time;
    }

    public int get_duration() {
        return _duration;
    }

    public void set_duration(int _duration) {
        this._duration = _duration;
    }

    public String get_customer_code() {
        return _customer_code;
    }

    public void set_customer_code(String _customer_code) {
        this._customer_code = _customer_code;
    }

    public String get_alert_type() {
        return _alert_type;
    }

    public void set_alert_type(String _alert_type) {
        this._alert_type = _alert_type;
    }

    public String get_item_code() {
        return _item_code;
    }

    public void set_item_code(String _item_code) {
        this._item_code = _item_code;
    }

    public String get_note() {
        return _note;
    }

    public void set_note(String _note) {
        this._note = _note;
    }

    public int get_employee_code_1() {
        return _employee_code_1;
    }

    public void set_employee_code_1(int _employee_code_1) {
        this._employee_code_1 = _employee_code_1;
    }

    public int get_employee_code_2() {
        return _employee_code_2;
    }

    public void set_employee_code_2(int _employee_code_2) {
        this._employee_code_2 = _employee_code_2;
    }

    public String get_confirm_status() {
        return _confirm_status;
    }

    public void set_confirm_status(String _confirm_status) {
        this._confirm_status = _confirm_status;
    }
}