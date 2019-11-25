package com.example.eventstrackerapp.ui.carpool.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Car {

    private String carID;
    private Driver driver;
    private ArrayList<Passenger> riders;
    private int seats;
    private String type;
    private ArrayList<String> allPickUpLocations;
    private ArrayList<String> allDropOffLocations;
    private ArrayList<Date> allPickUpTimes;

    public Car(String carID, Driver driver, ArrayList<Passenger> riders, int seats, String type,
               ArrayList<String> allPickUpLocations, ArrayList<String> allDropOffLocations, ArrayList<Date> allPickUpTimes) {
        this.carID = carID;
        this.driver = driver;
        this.riders = riders;
        this.seats = seats;
        this.type = type;
        this.allPickUpLocations = allPickUpLocations;
        this.allDropOffLocations = allDropOffLocations;
        this.allPickUpTimes = allPickUpTimes;
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

    public ArrayList<String> getAllPickUpLocations() {
        return allPickUpLocations;
    }

    public void setAllPickUpLocations(ArrayList<String> allPickUpLocations) {
        this.allPickUpLocations = allPickUpLocations;
    }

    public ArrayList<String> getAllDropOffLocations() {
        return allDropOffLocations;
    }

    public void setAllDropOffLocations(ArrayList<String> allDropOffLocations) {
        this.allDropOffLocations = allDropOffLocations;
    }

    public ArrayList<Date> getAllPickUpTimes() {
        return allPickUpTimes;
    }

    public void setAllPickUpTimes(ArrayList<Date> allPickUpTimes) {
        this.allPickUpTimes = allPickUpTimes;
    }
}
