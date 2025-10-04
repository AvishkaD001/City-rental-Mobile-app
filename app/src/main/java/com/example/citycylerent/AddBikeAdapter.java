package com.example.citycylerent;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBikeAdapter {
    private DatabaseReference databaseReference;

    public AddBikeAdapter() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Bikes");
    }

    public void addBike(String id, String name, boolean isAvailable, String stationId) {
        Bike bike = new Bike(id, name, isAvailable, stationId);
        databaseReference.child(id).setValue(bike)
                .addOnSuccessListener(aVoid -> System.out.println("Bike added successfully"))
                .addOnFailureListener(e -> System.err.println("Error: " + e.getMessage()));
    }
}
