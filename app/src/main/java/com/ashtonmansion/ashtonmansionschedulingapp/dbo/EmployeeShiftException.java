package com.ashtonmansion.ashtonmansionschedulingapp.dbo;

import java.util.Date;

/**
 * Created by paul on 7/18/2016.
 */
public class EmployeeShiftException {
    //Private vars
    int _id;
    Date _exception_date;
    String _start_time;
    int _available_hours;
    int _employee_code;

    //Empty constructor
    public EmployeeShiftException(){

    }

    //Constructor with ID
    public EmployeeShiftException(int id, Date exception_date, String start_time, int available_hours, int employee_code){
        this._id = id;
        this._exception_date = exception_date;
        this._start_time = start_time;
        this._available_hours = available_hours;
        this._employee_code = employee_code;
    }

    //Constructor without ID
    public EmployeeShiftException(Date exception_date, String start_time, int available_hours, int employee_code){
        this._exception_date = exception_date;
        this._start_time = start_time;
        this._available_hours = available_hours;
        this._employee_code = employee_code;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Date get_exception_date() {
        return _exception_date;
    }

    public void set_exception_date(Date _exception_date) {
        this._exception_date = _exception_date;
    }

    public String get_start_time() {
        return _start_time;
    }

    public void set_start_time(String _start_time) {
        this._start_time = _start_time;
    }

    public int get_available_hours() {
        return _available_hours;
    }

    public void set_available_hours(int _available_hours) {
        this._available_hours = _available_hours;
    }

    public int get_employee_code() {
        return _employee_code;
    }

    public void set_employee_code(int _employee_code) {
        this._employee_code = _employee_code;
    }
}