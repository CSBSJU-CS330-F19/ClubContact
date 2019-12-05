package com.example.eventstrackerapp.ui.carpool.entities;

import com.example.eventstrackerapp.profile.User;

import java.util.Date;

public class Passenger {

    private String passengerID;
    private String passengerName;
    private String pickUpLocation;
    private Date pickUpTime;
    private String dropOffLocation;

    public Passenger(){}

    public Passenger(String passengerID, String passengerName, String pickUpLocation, Date pickUpTime, String dropOffLocation) {
        this.passengerID = passengerID;
        this.passengerName = passengerName;
        this.pickUpLocation = pickUpLocation;
        this.pickUpTime = pickUpTime;
        this.dropOffLocation = dropOffLocation;
    }

    public String getPassengerID() {
        return passengerID;
    }

    public void setPassengerID(String passengerID) {
        this.passengerID = passengerID;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public Date getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(Date pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(String dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }
}
