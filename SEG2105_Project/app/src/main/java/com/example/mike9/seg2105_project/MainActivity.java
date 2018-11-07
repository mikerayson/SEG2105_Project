package com.example.mike9.seg2105_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClickLogin(View v){
        Intent login = new Intent(getApplicationContext(), WelcomeScreen.class);
        startActivity(login);
    }
    //Should we add something that carries over the information that they already put in
    //Aside from ^^^ this opens up the registration page
    public void onClickRegister(View v){
        Intent register = new Intent(getApplicationContext(), SignUp.class);
        startActivity(register);
    }
}
