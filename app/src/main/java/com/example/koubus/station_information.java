package com.example.koubus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class station_information extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_information);
        ScrollView mainLayout = findViewById(R.id.mainLayout);
        DB_Helper db_helper=new DB_Helper(this);
        TableLayout table = new TableLayout(this);
        ArrayList<String>stations=new ArrayList<>();
        ArrayList<String>num_of_passengers=new ArrayList<>();
        stations.add("Station Name");
        num_of_passengers.add("Passenger");
        int num_of_passenger_sum=0;
        for(int i=0;i<db_helper.get_Passengers_Number().size();i++){
            num_of_passengers.add(String.valueOf(db_helper.get_Passengers_Number().get(i)));
            stations.add(db_helper.get_Station_Name().get(i));
            num_of_passenger_sum+=db_helper.get_Passengers_Number().get(i);
        }
        stations.add("Total Passengers");
        num_of_passengers.add(String.valueOf(num_of_passenger_sum));




        for (int i = 0; i < stations.size(); i++) {
            TableRow row = new TableRow(station_information.this);
            for (int j = 0; j < 2; j++) {
                if (j == 0) {
                    if(i==0){
                        String value = stations.get(i);
                        TextView tv = new TextView(station_information.this);
                        tv.setText(String.valueOf(value));
                        tv.setTextSize(30);
                        tv.setTextColor(getResources().getColor(R.color.black));
                        tv.setBackgroundResource(R.drawable.table);
                        tv.setWidth(720);
                        tv.setGravity(Gravity.CENTER);
                        tv.setPadding(20, 20, 20, 20);
                        row.addView(tv);
                    }
                    else {
                        String value = stations.get(i);
                        TextView tv = new TextView(station_information.this);
                        tv.setText(String.valueOf(value));
                        tv.setTextSize(30);
                        tv.setTextColor(getResources().getColor(R.color.black));
                        tv.setBackgroundResource(R.drawable.table2);
                        tv.setWidth(720);
                        tv.setGravity(Gravity.CENTER);
                        tv.setPadding(20, 20, 20, 20);

                        if(i==stations.size()-1){
                            tv.setTextSize(20);
                            tv.setPadding(50, 55, 50, 35);
                            tv.setTextColor(getResources().getColor(android.R.color.black));

                        }
                        row.addView(tv);
                    }


                }
                if (j == 1) {
                    if(i==0){
                        String value = num_of_passengers.get(i);
                        TextView tv = new TextView(station_information.this);
                        tv.setText(String.valueOf(value));
                        tv.setTextSize(30);
                        tv.setTextColor(getResources().getColor(R.color.black));
                        tv.setBackgroundResource(R.drawable.table);
                        tv.setWidth(720);
                        tv.setGravity(Gravity.CENTER);
                        tv.setPadding(20, 20, 20, 20);
                        row.addView(tv);
                    }
                    else{
                        String value = num_of_passengers.get(i);
                        TextView tv = new TextView(station_information.this);
                        tv.setText(String.valueOf(value));
                        tv.setTextSize(30);
                        tv.setTextColor(getResources().getColor(R.color.black));
                        tv.setBackgroundResource(R.drawable.table3);
                        tv.setWidth(720);
                        tv.setGravity(Gravity.CENTER);
                        tv.setPadding(20, 20, 20, 20);
                        row.addView(tv);
                    }

                }

            }
            table.addView(row);

        }
        mainLayout.addView(table);
    }
}