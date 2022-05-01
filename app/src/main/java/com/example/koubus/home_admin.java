package com.example.koubus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class home_admin extends AppCompatActivity {
    Button create_station_btn,bus_manager,manage_passenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        create_station_btn=findViewById(R.id.btn_create_station);
        bus_manager=findViewById(R.id.btn_bus_manager);
        manage_passenger=findViewById(R.id.btn_manage_passengers);

        create_station_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_to_create_station=new Intent(home_admin.this, create_station.class);
                startActivity(go_to_create_station);
            }

        });
        manage_passenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_to_manage_passenger=new Intent(home_admin.this, manage_passenger.class);
                startActivity(go_to_manage_passenger);
            }
        });


    }

}