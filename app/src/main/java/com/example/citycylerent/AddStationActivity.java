package com.example.citycylerent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddStationActivity extends AppCompatActivity {
    private StationAdapter stationAdapter;
    private EditText editTextStationId, editTextStationName, editTextStationLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_station);

        stationAdapter = new StationAdapter();

        editTextStationId = findViewById(R.id.editTextStationId);
        editTextStationName = findViewById(R.id.editTextStationName);
        editTextStationLocation = findViewById(R.id.editTextStationLocation);
        Button buttonAddStation = findViewById(R.id.buttonAddStation);

        buttonAddStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editTextStationId.getText().toString().trim();
                String name = editTextStationName.getText().toString().trim();
                String location = editTextStationLocation.getText().toString().trim();

                if (!id.isEmpty() && !name.isEmpty() && !location.isEmpty()) {
                    stationAdapter.addStation(id, name, location, new StationAdapter.FirebaseCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(AddStationActivity.this, "Station added!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            Toast.makeText(AddStationActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(AddStationActivity.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
