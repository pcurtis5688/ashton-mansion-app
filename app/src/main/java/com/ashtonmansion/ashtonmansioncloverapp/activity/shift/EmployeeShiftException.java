package com.ashtonmansion.ashtonmansioncloverapp.activity.shift;

import java.sql.Time;
import java.util.Date;

/**
 * Created by paul on 8/5/2016.
 */
public class EmployeeShiftException {
    private Date shiftExceptionDate;
    private String employeeID;
    private Time shiftExceptionTime;
    private long duration;

    public EmployeeShiftException(Date date, String employeeID, Time shiftExceptionTime, long duration) {
        this.shiftExceptionDate = date;
        this.employeeID = employeeID;
        this.shiftExceptionTime = shiftExceptionTime;
        this.duration = duration;

    }

    public Date getShiftExceptionDate() {
        return shiftExceptionDate;
    }

    public void setShiftExceptionDate(Date shiftExceptionDate) {
        this.shiftExceptionDate = shiftExceptionDate;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public Time getShiftExceptionTime() {
        return shiftExceptionTime;
    }

    public void setShiftExceptionTime(Time shiftExceptionTime) {
        this.shiftExceptionTime = shiftExceptionTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
