package com.example.eventstrackerapp.ui.carpool.entities;


import com.example.eventstrackerapp.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Carpool {

    private String carpoolID;
    private String carpoolTitle;
    private Car car;
    private Event event;

    public Carpool(){

    }

    public Carpool(String carpoolTitle) {
        this.carpoolTitle = carpoolTitle;
    }

    public Carpool(String carpoolID, String carpoolTitle, Car car) {
        this.carpoolID = carpoolID;
        this.carpoolTitle = carpoolTitle;
        this.car = car;
    }

    public Carpool(String carpoolID, String carpoolTitle, Car car, Event event) {
        this.carpoolID = carpoolID;
        this.carpoolTitle = carpoolTitle;
        this.car = car;
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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
