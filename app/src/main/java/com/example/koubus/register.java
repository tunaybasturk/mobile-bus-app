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

public class register extends AppCompatActivity {
    EditText register_username,register_password;
    Button btn_register,btn_go_back;
    ActionBar actionBar;
    private String id,username,password;

    DB_Helper db_helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register_username=findViewById(R.id.Register_Username);
        register_password=findViewById(R.id.Register_Password);
        btn_register=findViewById(R.id.register);


        actionBar=getSupportActionBar();
        actionBar.setTitle("Register");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        db_helper=new DB_Helper(this);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=register_username.getText().toString();
                String password=register_password.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
                    Toast.makeText(register.this,"All fields required",Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuser=db_helper.checkusername(username);
                    if(checkuser==false){
                        Boolean insert=db_helper.insertData(username,password,"No","Not Selected");
                        if(insert){
                            Toast.makeText(register.this,"Registered Succesfully",Toast.LENGTH_LONG).show();
                            Intent go_to_main_page=new Intent(register.this,MainActivity.class);
                            startActivity(go_to_main_page);
                        }
                        else{
                            Toast.makeText(register.this,"Registration Failed",Toast.LENGTH_SHORT).show();

                        }
                    }
                    else{
                        Toast.makeText(register.this,"User already exist",Toast.LENGTH_SHORT).show();
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