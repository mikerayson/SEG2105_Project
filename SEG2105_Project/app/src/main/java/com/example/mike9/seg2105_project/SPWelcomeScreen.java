package com.example.mike9.seg2105_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SPWelcomeScreen extends AppCompatActivity {

    //Firebase Dependencies
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;

    private TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        txtView=(TextView)findViewById(R.id.textView);

        //Carries over the user sign in
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        userID = user.getUid();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spwelcome_screen);

        Toast.makeText(SPWelcomeScreen.this, "Hello Service Provider: "+userID, Toast.LENGTH_LONG).show();
    }
}
