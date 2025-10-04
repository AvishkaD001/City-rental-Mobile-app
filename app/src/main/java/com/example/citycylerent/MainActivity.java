package com.example.citycylerent;



import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button profileButton;
    private Button BikeButton;

    private Button renthistoryButton;

    private Button PricingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        });

        BikeButton = findViewById(R.id.BikeButton);
        BikeButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, BikeReservationActivity.class));
        });

        renthistoryButton = findViewById(R.id.renthistoryButton);
        renthistoryButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RentalHistoryActivity.class));
        });

        PricingButton = findViewById(R.id. PricingButton);
        PricingButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, PricingPromotionsActivity.class));
        });




    }
}