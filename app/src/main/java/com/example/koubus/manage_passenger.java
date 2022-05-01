package com.example.koubus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class manage_passenger extends AppCompatActivity {
    String[] items={"Ali","Haydar","Cabbar"};
    AutoCompleteTextView autoCompleteText;
    ArrayAdapter<String> adapterItems;
    Button add_passenger,reset_passenger;
    EditText num_passenger;
    DB_Helper db_helper;
    String item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_passenger);
        db_helper=new DB_Helper(this);
        add_passenger=findViewById(R.id.btn_add_passenger);
        reset_passenger=findViewById(R.id.btn_reset_passengers);
        num_passenger=findViewById(R.id.edt_passenger_num);
        ArrayList<String> counties=new ArrayList<>();
        counties=db_helper.get_Station_Name();
        String[] county_holder=new String[counties.size()];


        for(int i=0;i<counties.size();i++){
            System.out.println(counties.get(i));
            county_holder[i]=counties.get(i);
        }
        autoCompleteText=findViewById(R.id.auto_complete_txt);
        adapterItems=new ArrayAdapter<String>(this,R.layout.list_item,county_holder);
        autoCompleteText.setAdapter(adapterItems);

        autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item= adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(),"Item:"+item,Toast.LENGTH_SHORT).show();
            }
        });
        reset_passenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_helper.admin_delete_passenger();
                Toast.makeText(getApplicationContext(),"All passengers at stations reset",Toast.LENGTH_SHORT).show();
            }
        });

        add_passenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int passenger_holder=Integer.parseInt(num_passenger.getText().toString());
                String station=item;
                db_helper.admin_add_passenger(station,passenger_holder);
                Toast.makeText(getApplicationContext(),"Passengers successfully added ",Toast.LENGTH_SHORT).show();
            }
        });

    }
}