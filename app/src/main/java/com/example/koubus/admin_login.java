package com.example.koubus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class admin_login extends AppCompatActivity {
    ActionBar actionBar;
    Button admin_login;
    DB_Helper db_helper;
    EditText admin_login_username,admin_login_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);


        admin_login=findViewById(R.id.login);
        admin_login_username=findViewById(R.id.admin_username);
        admin_login_password=findViewById(R.id.admin_password);
        db_helper=new DB_Helper(this);
        actionBar=getSupportActionBar();
        actionBar.setTitle("Admin Login");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


    admin_login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String username=admin_login_username.getText().toString();
            String password=admin_login_password.getText().toString();
            String admin="Yes";
            if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
                Toast.makeText(admin_login.this,"All fields Required",Toast.LENGTH_SHORT).show();
            }
            else{
                Boolean checkuserpass=db_helper.checkusernamepassword(username,password,admin);
                if(checkuserpass){
                    Toast.makeText(admin_login.this,"Admin Login Succesfully",Toast.LENGTH_SHORT).show();
                    Intent go_to_home_admin=new Intent(com.example.koubus.admin_login.this,home_admin.class);
                    startActivity(go_to_home_admin);
                }
                else{
                    Toast.makeText(admin_login.this,"Wrong username or password",Toast.LENGTH_SHORT).show();
                }

            }
        }
    });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}