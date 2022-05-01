package com.example.koubus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class DB_Helper extends SQLiteOpenHelper {
    create_station parameter_helper;
    ContentValues values=new ContentValues();
    public static final String DBNAME="users.db";
    public DB_Helper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users(username TEXT primary key,password TEXT,admin TEXT,station_name TEXT)");
        sqLiteDatabase.execSQL("create table station(station_name TEXT primary key,latitude TEXT,longitude TEXT,passengers INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
        sqLiteDatabase.execSQL("drop table if exists station");
    }
    public Boolean insertData(String username,String password,String admin,String station_name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("username",username);
        values.put("password",password);
        values.put("admin",admin);
        values.put("station_name",station_name);

        long result=db.insert("users",null,values);
        if (result==-1)
            return  false;
        else
            return true;

    }
    public Boolean insert_station_Data(String station_name,String latitude,String longitude,int num_passenger){
        SQLiteDatabase db=this.getWritableDatabase();

        values.put("station_name",station_name);
        values.put("latitude",latitude);
        values.put("longitude",longitude);
        values.put("passengers",num_passenger);
        long result=db.insert("station",null,values);
        if (result==-1)
            return  false;
        else
            return true;
    }
    public ArrayList<String> get_Latitude(){
        ArrayList<String> station_Latitude = new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select latitude from station ",null);


        while(cursor.moveToNext()) {
            String data=cursor.getString(cursor.getColumnIndexOrThrow("latitude"));
            station_Latitude.add(data);
        }
        cursor.close();
        return station_Latitude;
    }
    public ArrayList<String> get_Longitude(){
        ArrayList<String> station_Longitude = new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select longitude from station ",null);


        while(cursor.moveToNext()) {
            String data=cursor.getString(cursor.getColumnIndexOrThrow("longitude"));
            station_Longitude.add(data);
        }
        cursor.close();
        return station_Longitude;
    }

    public ArrayList<String> get_Station_Name(){
        ArrayList<String> station_name = new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select station_name from station ORDER BY rowid",null);


        while(cursor.moveToNext()) {
            String data=cursor.getString(cursor.getColumnIndexOrThrow("station_name"));
            station_name.add(data);
        }
        cursor.close();
        return station_name;
    }
    public void user_add_passenger(String inbound_station) {
        System.out.println("Buraya giriyor mu? " + inbound_station);
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteDatabase db2 = this.getWritableDatabase();
        System.out.println("DB oluşturuyor mu? " + inbound_station);
        Cursor cursor = db.rawQuery("select passengers from station where station_name=?", new String[]{inbound_station});
        System.out.println("Sorgu yapıyor mu? " + inbound_station);
        //String num_of_passenger_str="";
        int num_of_passenger = 0;
        while (cursor.moveToNext()) {
            num_of_passenger = cursor.getInt(cursor.getColumnIndexOrThrow("passengers"));
            System.out.println("Giriyoor");
        }
        num_of_passenger += 1;
        System.out.println("Passenger_str=" + num_of_passenger);
        db2.execSQL("UPDATE station SET passengers=? WHERE station_name=? ",new String[]{String.valueOf(num_of_passenger),inbound_station});


    }
    public void admin_add_passenger(String inbound_station, int arriving_passengers){
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteDatabase db2 = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select passengers from station where station_name=?", new String[]{inbound_station});
        int num_of_passenger = 0;
        while (cursor.moveToNext()) {
            num_of_passenger = cursor.getInt(cursor.getColumnIndexOrThrow("passengers"));
            System.out.println("Giriyoor");
        }
        num_of_passenger+=arriving_passengers;
        db2.execSQL("UPDATE station SET passengers=? WHERE station_name=? ",new String[]{String.valueOf(num_of_passenger),inbound_station});
    }

    public void admin_delete_passenger(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE station SET passengers=?  ",new String[]{String.valueOf(0)});
        String holder="Not Selected";
        String holder2="No";
        db.execSQL("UPDATE users SET station_name=? Where admin=? ",new String[]{holder,holder2});

    }



    public Boolean checkusername(String username){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from users where username=?",new String[]{username});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username,String password,String admin){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from users where username=? and password=? and admin=?",new String[]{username,password,admin});

        if (cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }

    }

    public Boolean has_user_station(String username,String password){
        System.out.println("Girdim mi ki :D");
        String control="";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select  station_name from users where username=? and password=?", new String[]{username,password});
        System.out.println("geliyorum cursor");
        while (cursor.moveToNext()) {
            control= cursor.getString(cursor.getColumnIndexOrThrow("station_name"));
            System.out.println("Giriyoor  "+control);
        }
        if (!control.contains("Not Selected")){
            System.out.println("True");
            return true;
        }
        else {
            System.out.println("False");
            return false;
        }
    }
    public void user_add_station(String username,String password,String inbound_station_name){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE users SET station_name=? WHERE username=? and password=? ",new String[]{inbound_station_name,username,password});

    }


    public void user_change_station(String username,String password,String station_change){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE users SET station_name=? WHERE username=? and password=? ",new String[]{station_change,username,password});

    }

    public void reduce_previous_station(String username , String password){
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteDatabase db2 = this.getWritableDatabase();
        SQLiteDatabase db3 = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select  station_name from users where username=? and password=?", new String[]{username,password});
        String previous_station="";
        while (cursor.moveToNext()) {
            previous_station= cursor.getString(cursor.getColumnIndexOrThrow("station_name"));

        }
        System.out.println("stationnn "+ previous_station);
        int num_of_passengers=0;
        Cursor cursor2=db3.rawQuery("select  passengers from station where station_name=?", new String[]{previous_station});
        while (cursor2.moveToNext()) {
            num_of_passengers= cursor2.getInt(cursor2.getColumnIndexOrThrow("passengers"));

        }
        num_of_passengers--;
        System.out.println("azalttım "+num_of_passengers);
        db2.execSQL("UPDATE station SET passengers=? WHERE station_name=? ",new String[]{String.valueOf(num_of_passengers),previous_station});




    }

    public ArrayList<Integer> get_Passengers_Number(){
        ArrayList<Integer> num_of_passenger = new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select passengers from station ORDER BY rowid",null);


        while(cursor.moveToNext()) {
            int data=cursor.getInt(cursor.getColumnIndexOrThrow("passengers"));
            num_of_passenger.add(data);
        }
        cursor.close();
        return num_of_passenger;
    }


}