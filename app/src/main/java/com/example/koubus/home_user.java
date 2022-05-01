package com.example.koubus;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.koubus.databinding.ActivityHomeUserBinding;

import java.util.ArrayList;
import java.util.List;

public class home_user extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityHomeUserBinding binding;
    int counter=0,counter2=0;
    DB_Helper db_helper;
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    Button select_station,change_station;
    String item;
    int counter_for_warning=0;
    user_login user_information;
    ArrayList<LatLng>Counties=new ArrayList<LatLng>();
    List<LatLng> points=new ArrayList<LatLng>();

    // Add a marker in Sydney and move the camera
    /*LatLng başiskele = new LatLng(	40.766666, 29.916668);
    LatLng çayırova = new LatLng(	40.7999968, 29.416665);
    LatLng darıca = new LatLng(	40.7597, 29.3856);
    LatLng derince = new LatLng(	40.756189, 29.830918);
    LatLng dilovası = new LatLng(	40.787622, 29.5441580);
    LatLng gebze = new LatLng(	40.802516, 29.439794);
    LatLng gölcük = new LatLng(	40.7167517, 29.81948750);
    LatLng kandıra = new LatLng(	41.070363, 30.152394);
    LatLng karamürsel = new LatLng(	40.691274, 29.616418);
    LatLng kartepe = new LatLng(	40.753369, 30.023216);
    LatLng körfez = new LatLng(	40.776382, 29.737718);
    LatLng izmit = new LatLng(	40.766666, 29.916668);*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db_helper=new DB_Helper(this);
        autoCompleteTxt=findViewById(R.id.auto_complete_txt);
        select_station=findViewById(R.id.btn_add_station);
        change_station=findViewById(R.id.btn_change_station);
        ArrayList<String> counties=new ArrayList<>();
        ArrayList<String> latitude_list=new ArrayList<>();
        ArrayList<String> longitude_list=new ArrayList<>();
        counties=db_helper.get_Station_Name();
        latitude_list=db_helper.get_Latitude();
        longitude_list=db_helper.get_Longitude();
        Double[] lat_list = new Double[latitude_list.size()];
        Double[] lng_list = new Double[longitude_list.size()];
        for(int i=0;i<lat_list.length;i++){
            lat_list[i]=Double.parseDouble(latitude_list.get(i));
            lng_list[i]=Double.parseDouble(longitude_list.get(i));
        }

        String[] county_holder=new String[counties.size()];


        for(int i=0;i<counties.size();i++){
            System.out.println(counties.get(i));
            county_holder[i]=counties.get(i);
        }
        for(int i=0;i<latitude_list.size();i++){
            System.out.println("Buraya da girdim");
            points.add(new LatLng(lat_list[i],lng_list[i]));
        }


        adapterItems=new ArrayAdapter<String>(this,R.layout.list_item,county_holder);
        autoCompleteTxt.setAdapter(adapterItems);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item=adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
                for (int j=0;j<county_holder.length;j++){
                    if(county_holder[j]==item){
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(points.get(j),15f));
                        break;
                    }
                }
            }
        });

        select_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("asaaaaaaaaaa");
                System.out.println(user_information.getUsername());
                String username=user_information.getUsername();
                String password=user_information.getPassword();
                System.out.println("Home user bebek   :   "+username+ "  "+password);
                Boolean control=db_helper.has_user_station(username,password);
                if(!control){
                    Toast.makeText(home_user.this,item+" Station Selected...",Toast.LENGTH_SHORT).show();
                    db_helper.user_add_station(username,password,item);
                    db_helper.user_add_passenger(item);
                }
                else{
                    Toast.makeText(home_user.this," Station already selected you can change it...",Toast.LENGTH_SHORT).show();
                }

            }
        });

        change_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=user_information.getUsername();
                String password=user_information.getPassword();
                db_helper.reduce_previous_station(username,password);
                db_helper.user_change_station(username,password,item);
                db_helper.user_add_passenger(item);
                Toast.makeText(home_user.this,"Station succesfully changed to "+item,Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(points.get(0),15f));
        for(int i=0;i<points.size();i++){
           mMap.addMarker(new MarkerOptions().position(points.get(i)).icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.durak)));

       }






    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context,int vectorResId){
        Drawable vectorDrawable= ContextCompat.getDrawable(context,vectorResId);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap=Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}