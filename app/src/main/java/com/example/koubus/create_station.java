package com.example.koubus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class create_station extends AppCompatActivity {
    EditText station_name,latitude,longitude;
    Button add_station;
    DB_Helper db_helper;
    String name,lat_str,lng_str;
    Button show_stations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_station);
        station_name=findViewById(R.id.edt_station_name);
        latitude=findViewById(R.id.edt_latitude);
        longitude=findViewById(R.id.edt_longitude);
        add_station=findViewById(R.id.btn_add_station);
        show_stations=findViewById(R.id.btn_show_station);
        db_helper=new DB_Helper(this);

        add_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=station_name.getText().toString();
                lat_str=latitude.getText().toString();
                lng_str=longitude.getText().toString();
                float lat=Float.valueOf(lat_str);
                float lng=Float.valueOf(lng_str);

                if(TextUtils.isEmpty(name)||TextUtils.isEmpty(lat_str) ||TextUtils.isEmpty(lng_str)){
                    Toast.makeText(create_station.this,"All fields Required",Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean insert_station=db_helper.insert_station_Data(name,lat_str,lng_str,0);
                    if(insert_station){
                        Toast.makeText(create_station.this,"New Station added succesfully",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(create_station.this,"Failed to add station",Toast.LENGTH_SHORT).show();

                    }

                }



            }

        });
        show_stations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_to_manage_passenger=new Intent(create_station.this, station_information.class);
                startActivity(go_to_manage_passenger);
            }
        });


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat_str() {
        return lat_str;
    }

    public void setLat_str(String lat_str) {
        this.lat_str = lat_str;
    }

    public String getLng_str() {
        return lng_str;
    }

    public void setLng_str(String lng_str) {
        this.lng_str = lng_str;
    }
}