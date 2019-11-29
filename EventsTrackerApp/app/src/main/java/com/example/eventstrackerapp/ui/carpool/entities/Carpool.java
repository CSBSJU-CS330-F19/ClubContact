package com.example.eventstrackerapp.ui.carpool.entities;


import com.example.eventstrackerapp.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Carpool {

    private String carpoolID;
    private String carpoolTitle;
    private ArrayList<Car> cars;
    private Event event;

    public Carpool(String carpoolTitle) {
        this.carpoolTitle = carpoolTitle;
    }

    public Carpool(String carpoolID, String carpoolTitle, ArrayList<Car> cars) {
        this.carpoolID = carpoolID;
        this.carpoolTitle = carpoolTitle;
        this.cars = cars;
    }

    public Carpool(String carpoolID, String carpoolTitle, ArrayList<Car> cars, Event event) {
        this.carpoolID = carpoolID;
        this.carpoolTitle = carpoolTitle;
        this.cars = cars;
        this.event = event;
    }

    public String getCarpoolID() {
        return carpoolID;
    }

    public void setCarpoolID(String carpoolID) {
        this.carpoolID = carpoolID;
    }

    public String getCarpoolTitle() {
        return carpoolTitle;
    }

    public void setCarpoolTitle(String carpoolTitle) {
        this.carpoolTitle = carpoolTitle;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
