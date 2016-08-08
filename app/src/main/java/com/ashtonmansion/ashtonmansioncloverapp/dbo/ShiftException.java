package com.ashtonmansion.ashtonmansioncloverapp.dbo;


/**
 * Created by paul on 8/8/2016.
 */
public class ShiftException {
    private int shiftExceptionID;
    private String employeeID;
    private String shiftExceptionDate;
    private String shiftExceptionTime;
    private long duration;

    public ShiftException() {
        //SHIFT EXCEPTION BUILDER CONSTRUCTOR
    }

    public ShiftException(String employeeID, String date, String shiftExceptionTime, long duration) {
        this.shiftExceptionDate = date;
        this.employeeID = employeeID;
        this.shiftExceptionTime = shiftExceptionTime;
        this.duration = duration;
    }

    public int getShiftExceptionID() {
        return shiftExceptionID;
    }

    public void setShiftExceptionID(int shiftExceptionID) {
        this.shiftExceptionID = shiftExceptionID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getShiftExceptionDate() {
        return shiftExceptionDate;
    }

    public void setShiftExceptionDate(String shiftExceptionDate) {
        this.shiftExceptionDate = shiftExceptionDate;
    }

    public String getShiftExceptionTime() {
        return shiftExceptionTime;
    }

    public void setShiftExceptionTime(String shiftExceptionTime) {
        this.shiftExceptionTime = shiftExceptionTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
