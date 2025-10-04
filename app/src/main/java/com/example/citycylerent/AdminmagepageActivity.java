package com.example.citycylerent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class AdminmagepageActivity  extends AppCompatActivity {



    private Button ButtonAddStation;

    private Button AddBike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main_page);

        ButtonAddStation = findViewById(R.id.Addstation);
        ButtonAddStation.setOnClickListener(v -> {
            startActivity(new Intent(AdminmagepageActivity.this, AddStationActivity.class));
        });

        AddBike = findViewById(R.id.AddBike);
        AddBike.setOnClickListener(v -> {
            startActivity(new Intent(AdminmagepageActivity.this, AddBikeActivity.class));
        });




    }
}