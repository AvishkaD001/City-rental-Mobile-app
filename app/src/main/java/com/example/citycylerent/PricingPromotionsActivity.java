package com.example.citycylerent;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PricingPromotionsActivity extends AppCompatActivity {

    private TextView textViewPricingDetails, textViewPromotionsDetails;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricing_promotions);

        // Initialize Firebase Auth and Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize UI elements
        textViewPricingDetails = findViewById(R.id.textViewPricingDetails);
        textViewPromotionsDetails = findViewById(R.id.textViewPromotionsDetails);

        // Fetch pricing and promotions
        fetchPricingPromotions();
    }

    private void fetchPricingPromotions() {
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "User not authenticated!", Toast.LENGTH_SHORT).show();
            return;
        }

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Fetch Pricing data
                    String hourly = dataSnapshot.child("Pricing").child("hourly").getValue(String.class);
                    String daily = dataSnapshot.child("Pricing").child("daily").getValue(String.class);
                    String monthly = dataSnapshot.child("Pricing").child("monthly").getValue(String.class);

                    // Fetch Promotions data
                    String discount = dataSnapshot.child("Promotions").child("discount").getValue(String.class);
                    String membership = dataSnapshot.child("Promotions").child("membership").getValue(String.class);

                    // Update UI
                    textViewPricingDetails.setText("Pricing Plans:\n\nHourly: " + hourly + "\nDaily: " + daily + "\nMonthly: " + monthly);
                    textViewPromotionsDetails.setText("Special Offers:\n\n" + discount + "\n" + membership);
                } else {
                    Toast.makeText(PricingPromotionsActivity.this, "No data found in Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("PricingPromotions", "Firebase Error: " + databaseError.getMessage());
                Toast.makeText(PricingPromotionsActivity.this, "Failed to load data: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
