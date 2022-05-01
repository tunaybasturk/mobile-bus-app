package com.example.koubus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class user_login extends AppCompatActivity {
    Button btn_user_login;
    EditText user_login_username,user_login_password;
    ActionBar actionBar;
    DB_Helper db_helper;
    private static String username="";
    private static String password="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        btn_user_login=findViewById(R.id.login);
        user_login_username=findViewById(R.id.username);
        user_login_password=findViewById(R.id.password);
        db_helper=new DB_Helper(this);
        actionBar=getSupportActionBar();
        actionBar.setTitle("User Login");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        btn_user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=user_login_username.getText().toString();
                password=user_login_password.getText().toString();
                System.out.println(username+""+password);
                String admin="No";
                if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
                    Toast.makeText(user_login.this,"All fields Required",Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkuserpass=db_helper.checkusernamepassword(username,password,admin);
                    if(checkuserpass==true){
                        Intent go_to_user_home=new Intent(user_login.this,home_user.class);
                        startActivity(go_to_user_home);
                    }
                    else{
                        Toast.makeText(user_login.this,"Wrong username or password",Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


    }

    public static String getUsername() {
        return username;
    }



    public static String getPassword() {
        return password;
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}