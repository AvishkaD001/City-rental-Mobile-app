package com.example.citycylerent;

public class Station {
    private String id, name, location;

    // Default constructor required for Firebase
    public Station() { }

    public Station(String id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    // Getter methods for Firebase
    public String getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
}
