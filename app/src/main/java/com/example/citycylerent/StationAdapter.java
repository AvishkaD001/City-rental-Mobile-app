package com.example.citycylerent;

import android.util.Log;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StationAdapter {
    private DatabaseReference databaseReference;

    public StationAdapter() {
        // Initialize Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Stations");
    }

    // Callback Interface
    public interface FirebaseCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    // Method to add a station with callback
    public void addStation(String id, String name, String location, FirebaseCallback callback) {
        Station station = new Station(id, name, location);
        databaseReference.child(id).setValue(station)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firebase", "Station added successfully");
                    callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Error: " + e.getMessage());
                    callback.onFailure(e.getMessage());
                });
    }
}
