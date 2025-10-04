package com.example.citycylerent;



import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BikeReservationActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBikes;
    private BikeAdapter bikeAdapter;
    private List<Bike> bikeList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private String selectedDate = "";
    private String selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_reservation);

        // Initialize Firebase Auth and Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize views
        recyclerViewBikes = findViewById(R.id.recyclerViewBikes);
        SearchView searchView = findViewById(R.id.searchView);
        Button buttonSelectDate = findViewById(R.id.buttonSelectDate);
        Button buttonSelectTime = findViewById(R.id.buttonSelectTime);

        // Set up RecyclerView
        bikeList = new ArrayList<>();
        bikeAdapter = new BikeAdapter(bikeList, bike -> {
            // Handle bike reservation
            reserveBike(bike);
        });
        recyclerViewBikes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBikes.setAdapter(bikeAdapter);

        // Fetch bikes from Firebase
        fetchBikes();

        // Set up search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                bikeAdapter.getFilter().filter(newText);
                return true;
            }
        });

        // Set up date picker
        buttonSelectDate.setOnClickListener(v -> showDatePicker());

        // Set up time picker
        buttonSelectTime.setOnClickListener(v -> showTimePicker());
    }

    private void fetchBikes() {
        mDatabase.child("Bikes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bikeList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Bike bike = snapshot.getValue(Bike.class);
                    if (bike != null) {
                        // Fetch station details for the bike
                        fetchStationDetails(bike);
                        bikeList.add(bike);
                        Log.d("BikeReservationActivity", "Fetched bike: " + bike.name);
                    }
                }
                bikeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("BikeReservationActivity", "Failed to fetch bikes: " + databaseError.getMessage());
            }
        });
    }

    private void fetchStationDetails(Bike bike) {
        mDatabase.child("Stations").child(bike.stationId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    bike.stationName = dataSnapshot.child("name").getValue(String.class);
                    bike.stationLocation = dataSnapshot.child("location").getValue(String.class);
                    bikeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("BikeReservationActivity", "Failed to fetch station details: " + databaseError.getMessage());
            }
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, month1, dayOfMonth) -> {
                    selectedDate = year1 + "-" + (month1 + 1) + "-" + dayOfMonth;
                    Toast.makeText(this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute1) -> {
                    selectedTime = hourOfDay + ":" + minute1;
                    Toast.makeText(this, "Selected Time: " + selectedTime, Toast.LENGTH_SHORT).show();
                },
                hour, minute, true
        );
        timePickerDialog.show();
    }

    private void reserveBike(Bike bike) {
        if (bike.isAvailable) {
            if (selectedDate.isEmpty() || selectedTime.isEmpty()) {
                Toast.makeText(this, "Please select a date and time", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get the current user
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                String userEmail = currentUser.getEmail(); // Get the user's email

                // Update bike reservation details
                mDatabase.child("Bikes").child(bike.id).child("isAvailable").setValue(false);
                mDatabase.child("Bikes").child(bike.id).child("reservedBy").setValue(userEmail);
                mDatabase.child("Bikes").child(bike.id).child("reservationDate").setValue(selectedDate);
                mDatabase.child("Bikes").child(bike.id).child("reservationTime").setValue(selectedTime)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(this, "Bike reserved successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Failed to reserve bike", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        } else {
            Toast.makeText(this, "Bike is not available", Toast.LENGTH_SHORT).show();
        }
    }
}