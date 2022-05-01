package com.example.koubus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn_user,btn_admin,btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_user=findViewById(R.id.User_login);
        btn_admin=findViewById(R.id.Admin_login);
        btn_register=findViewById(R.id.register);
        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_to_user_login=new Intent(MainActivity.this,user_login.class);
                startActivity(go_to_user_login);
            }
        });


        btn_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_to_admin_login=new Intent(MainActivity.this,admin_login.class);
                startActivity(go_to_admin_login);
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_to_register=new Intent(MainActivity.this,register.class);
                startActivity(go_to_register);
            }
        });


    }
}