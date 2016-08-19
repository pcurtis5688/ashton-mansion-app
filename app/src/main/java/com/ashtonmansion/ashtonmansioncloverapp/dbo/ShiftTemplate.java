package com.ashtonmansion.ashtonmansioncloverapp.dbo;

/**
 * Created by paul on 8/7/2016.
 */
public class ShiftTemplate {
    private String shiftID;
    private String shiftCode;
    private String shiftName;

    public ShiftTemplate() {
        //builder
    }

    public String getShiftID() {
        return shiftID;
    }

    public void setShiftID(String shiftID) {
        this.shiftID = shiftID;
    }

    public String getShiftCode() {
        return shiftCode;
    }

    public void setShiftCode(String shiftCode) {
        this.shiftCode = shiftCode;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }
}
