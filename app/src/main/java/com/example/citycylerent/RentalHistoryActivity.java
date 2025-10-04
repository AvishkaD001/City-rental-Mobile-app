package com.example.citycylerent;



import android.os.Bundle;
import android.util.Log;
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
import java.util.List;

public class RentalHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRentals;
    private RentalHistoryAdapter rentalHistoryAdapter;
    private List<Bike> rentalHistoryList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_history);

        // Initialize Firebase Auth and Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize views
        recyclerViewRentals = findViewById(R.id.recyclerViewRentals);

        // Set up RecyclerView
        rentalHistoryList = new ArrayList<>();
        rentalHistoryAdapter = new RentalHistoryAdapter(rentalHistoryList);
        recyclerViewRentals.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRentals.setAdapter(rentalHistoryAdapter);

        // Fetch rental history for the current user
        fetchRentalHistory();
    }

    private void fetchRentalHistory() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail(); // Get the current user's email

            mDatabase.child("Bikes").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    rentalHistoryList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Bike bike = snapshot.getValue(Bike.class);
                        if (bike != null && bike.reservedBy != null && bike.reservedBy.equals(userEmail)) {
                            // Create a BikeReservation object for the rental history
                            Bike reservation = new Bike(
                                    bike.name,
                                    bike.reservationDate,
                                    bike.reservationTime,
                                    bike.stationId
                            );
                            rentalHistoryList.add(reservation);
                            Log.d("RentalHistoryActivity", "Fetched reservation: " + bike.name);
                        }
                    }
                    rentalHistoryAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("RentalHistoryActivity", "Failed to fetch rental history: " + databaseError.getMessage());
                    Toast.makeText(RentalHistoryActivity.this, "Failed to fetch rental history", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }
}