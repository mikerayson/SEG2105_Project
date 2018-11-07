package com.example.michael.seg2105_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.content.Intent;
import android.view.View;

public class LoginScreen extends AppCompatActivity {

    //Gonna be used to check if they entered a valid user/pass combo
    public String username;
    public String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }
    //Need Database implementation to be able to add in checking username/password
    //Does work to open the Welcome page though
    public void onClickLogin(View v){
        Intent login = new Intent(getApplicationContext(), WelcomeSceen.class);
        startActivity(login);
    }
    //Should we add something that carries over the information that they already put in
    //Aside from ^^^ this opens up the registration page
    public void onClickRegister(View v){
        Intent register = new Intent(getApplicationContext(), SignUp.class);
        startActivity(register);
    }

}
