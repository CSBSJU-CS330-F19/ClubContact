package com.example.eventstrackerapp.ui.carpool.entities;

import java.util.ArrayList;
import java.util.Date;

public class Car {

    private String carID;
    private Driver driver;
    private ArrayList<Passenger> riders;
    private int seats;
    private String type;
    private String allPickUpLocation;
    private String allDropOffLocation;
    private Date allPickUpTime;

    public Car(){}

    public Car(String carID, Driver driver, ArrayList<Passenger> riders, int seats, String type,
               String allPickUpLocation, String allDropOffLocation, Date allPickUpTime) {
        this.carID = carID;
        this.driver = driver;
        this.riders = riders;
        this.seats = seats;
        this.type = type;
        this.allPickUpLocation = allPickUpLocation;
        this.allDropOffLocation = allDropOffLocation;
        this.allPickUpTime = allPickUpTime;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public ArrayList<Passenger> getRiders() {
        return riders;
    }

    public void setRiders(ArrayList<Passenger> riders) {
        this.riders = riders;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAllPickUpLocation() {
        return allPickUpLocation;
    }

    public void setAllPickUpLocation(String allPickUpLocation) {
        this.allPickUpLocation = allPickUpLocation;
    }

    public String getAllDropOffLocation() {
        return allDropOffLocation;
    }

    public void setAllDropOffLocation(String allDropOffLocation) {
        this.allDropOffLocation = allDropOffLocation;
    }

    public Date getAllPickUpTime() {
        return allPickUpTime;
    }

    public void setAllPickUpTime(Date allPickUpTime) {
        this.allPickUpTime = allPickUpTime;
    }
}
