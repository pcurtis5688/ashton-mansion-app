package com.ashtonmansion.ashtonmansionapp.dbo;

import java.util.Date;

/**
 * Created by paul on 7/18/2016.
 */
public class EmployeeShift {
    //Private Vars
    int _id;
    int _employee_code;
    Date _start_date;
    Date _end_date;
    int _shift_code;

    public EmployeeShift(){
        //Empty constructor
    }

    //Constructor with ID param
    public EmployeeShift(int _id, int _employee_code, Date _start_date, Date _end_date, int _shift_code){
        this._id = _id;
        this._employee_code = _employee_code;
        this._start_date = _start_date;
        this._end_date = _end_date;
        this._shift_code = _shift_code;
    }

    //Constructor without ID param
    public EmployeeShift(int _employee_code, Date _start_date, Date _end_date, int _shift_code){
        this._employee_code = _employee_code;
        this._start_date = _start_date;
        this._end_date = _end_date;
        this._shift_code = _shift_code;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_employee_code() {
        return _employee_code;
    }

    public void set_employee_code(int _employee_code) {
        this._employee_code = _employee_code;
    }

    public Date get_start_date() {
        return _start_date;
    }

    public void set_start_date(Date _start_date) {
        this._start_date = _start_date;
    }

    public Date get_end_date() {
        return _end_date;
    }

    public void set_end_date(Date _end_date) {
        this._end_date = _end_date;
    }

    public int get_shift_code() {
        return _shift_code;
    }

    public void set_shift_code(int _shift_code) {
        this._shift_code = _shift_code;
    }
}
