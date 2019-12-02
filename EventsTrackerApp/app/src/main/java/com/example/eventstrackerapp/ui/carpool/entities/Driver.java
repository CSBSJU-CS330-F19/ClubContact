package com.example.eventstrackerapp.ui.carpool.entities;

import com.example.eventstrackerapp.profile.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Driver{

    private String driverID;
    private String driverName;

    public Driver(){

    }

    public Driver(String driverID, String driverName) {
        this.driverID = driverID;
        this.driverName = driverName;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
