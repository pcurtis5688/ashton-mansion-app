package com.ashtonmansion.ashtonmansioncloverapp.dbo;

/**
 * Created by paul on 8/7/2016.
 */
public class ShiftTemplate {
    private int shiftID;
    private int shiftCode;
    private String shiftName;

    public ShiftTemplate(){
        //builder
    }

    public ShiftTemplate(int shiftCode, String shiftName) {
        this.shiftCode = shiftCode;
        this.shiftName = shiftName;
    }

    public int getShiftID() {
        return shiftID;
    }

    public void setShiftID(int shiftID) {
        this.shiftID = shiftID;
    }

    public int getShiftCode() {
        return shiftCode;
    }

    public void setShiftCode(int shiftCode) {
        this.shiftCode = shiftCode;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }
}
