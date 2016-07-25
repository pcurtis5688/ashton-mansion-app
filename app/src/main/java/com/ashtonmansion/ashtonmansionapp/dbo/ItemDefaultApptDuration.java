package com.ashtonmansion.ashtonmansionapp.dbo;

/**
 * Created by paul on 7/17/2016.
 // ItemDefaultApptDuration
 */
public class ItemDefaultApptDuration {
    // Private Vars
    int _id;
    String _item_code;
    int _hours;

    //Empty constructor
    public ItemDefaultApptDuration() {

    }

    //Constructor
    public ItemDefaultApptDuration(int id, String item_code, int hours) {
        this._id = id;
        this._item_code = item_code;
        this._hours = hours;
    }

    //Constructor without ID param
    public ItemDefaultApptDuration(String item_code, int hours) {
        this._item_code = item_code;
        this._hours = hours;
    }

    //Getters and Setters
    public int getID() {
        return this._id;
    }

    public void setID(int id) {
        this._id = id;
    }

    public String getItemCode() {
        return this._item_code;
    }

    public void setItemCode(String item_code) {
        this._item_code = item_code;
    }

    public int getHours() {
        return this._hours;
    }

    public void setHours(int hours) {
        this._hours = hours;
    }
}