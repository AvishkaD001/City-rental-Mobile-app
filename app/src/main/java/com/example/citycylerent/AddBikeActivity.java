package com.example.citycylerent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddBikeActivity extends AppCompatActivity {
    private AddBikeAdapter addBikeAdapter;
    private EditText editTextBikeId, editTextBikeName, editTextStationId;
    private Switch switchAvailability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bike);

        addBikeAdapter = new AddBikeAdapter();

        editTextBikeId = findViewById(R.id.editTextBikeId);
        editTextBikeName = findViewById(R.id.editTextBikeName);
        editTextStationId = findViewById(R.id.editTextStationId);
        switchAvailability = findViewById(R.id.switchAvailability);
        Button buttonAddBike = findViewById(R.id.buttonAddBike);

        buttonAddBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editTextBikeId.getText().toString().trim();
                String name = editTextBikeName.getText().toString().trim();
                String stationId = editTextStationId.getText().toString().trim();
                boolean isAvailable = switchAvailability.isChecked(); // true if switch is ON, false if OFF

                if (!id.isEmpty() && !name.isEmpty() && !stationId.isEmpty()) {
                    addBikeAdapter.addBike(id, name, isAvailable, stationId);
                    Toast.makeText(AddBikeActivity.this, "Bike added!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddBikeActivity.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
