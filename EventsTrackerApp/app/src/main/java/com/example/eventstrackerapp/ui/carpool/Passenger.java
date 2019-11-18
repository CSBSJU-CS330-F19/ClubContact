package com.example.eventstrackerapp.ui.carpool;

import java.util.Date;

public class Passenger {

    private Date pickUpTime;
    private String pickUpLocation;
    private String dropOffLocation;

    public Passenger(){

    }

    public Passenger(Date pickUpTime, String pickUpLocation, String dropOffLocation){
        this.pickUpTime = pickUpTime;
        this.pickUpLocation = pickUpLocation;
        this.dropOffLocation = dropOffLocation;
    }

    public Date getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(Date pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public String getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(String dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

}
