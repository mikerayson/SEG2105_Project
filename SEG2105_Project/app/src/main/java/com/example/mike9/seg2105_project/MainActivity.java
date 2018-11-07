package com.example.mike9.seg2105_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onClickLogin(View v){
        Intent login = new Intent(getApplicationContext(), WelcomeScreen.class);
        startActivity(login);
    }

    public void onClickRegister(View v){
        Intent register = new Intent(getApplicationContext(), SignUp.class);
        startActivity(register);
    }

}
