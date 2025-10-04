package com.example.citycylerent;

public class Bike {
    public String id;
    public String name;
    public boolean isAvailable;
    public String stationId;
    public String reservedBy;
    public String reservationDate;
    public String reservationTime;
    public String stationName;
    public String stationLocation;

    // Default constructor (Required for Firebase)
    public Bike() {
    }

    // Constructor for general bike details
    public Bike(String id, String name, boolean isAvailable, String stationId) {
        this.id = id;
        this.name = name;
        this.isAvailable = isAvailable;
        this.stationId = stationId;
        this.reservedBy = "";
        this.reservationDate = "";
        this.reservationTime = "";
        this.stationName = "";
        this.stationLocation = "";
    }

    // Constructor for rental history with reservation details
    public Bike(String name, String reservationDate, String reservationTime, String stationId) {
        this.name = name;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.stationId = stationId;
    }
}
