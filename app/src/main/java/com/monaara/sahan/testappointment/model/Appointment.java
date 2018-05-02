package com.monaara.sahan.testappointment.model;

/**
 * Created by Sahan on 4/25/2018.
 */

public class Appointment {
    private int id;
    private  String title,time,date,details;

    public Appointment() {
    }

    public Appointment(String title, String time, String date, String details) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.date = date;
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
