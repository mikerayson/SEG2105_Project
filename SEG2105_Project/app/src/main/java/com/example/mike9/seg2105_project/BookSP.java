package com.example.mike9.seg2105_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookSP extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;
    private FirebaseUser user;

    String spID, spEmail;
    TextView tvFn, tvLn, tvCn, tvEmail, tvPn, tvAddress, tvLicense, tvDescrip, tvRating;
    Spinner spinnerTime;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_sp);

        //carries iver the user sign in
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        userID = user.getUid();

        //Intent intent = getIntent();
        //spEmail = intent.getStringExtra("email");

        //initialize textviews
        tvFn = findViewById(R.id.firstName);
        tvLn = findViewById(R.id.Lastname);
        tvCn = findViewById(R.id.companyName);
        tvEmail = findViewById(R.id.email);
        tvPn = findViewById(R.id.phoneNumber);
        tvAddress = findViewById(R.id.address);
        tvLicense = findViewById(R.id.licensed);
        tvDescrip = findViewById(R.id.description);
        tvRating = findViewById(R.id.rating);
        spinnerTime = findViewById(R.id.spinner2);

        mRef.child("Users").child("Service Provider").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getID(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getID(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            if(ds.child("firstname").getValue().equals(spEmail)){
                spID = ds.getKey();
                //set all textfields
                tvFn.setText(ds.child("firstname").getValue().toString());
                tvLn.setText(ds.child("lastname").getValue().toString());
                tvCn.setText(ds.child("companyName").getValue().toString());
                tvEmail.setText(ds.child("email").getValue().toString());
                tvPn.setText(ds.child("phoneNumber").getValue().toString());
                tvAddress.setText(ds.child("address").getValue().toString());
                tvLicense.setText(ds.child("address").getValue().toString());
                tvDescrip.setText(ds.child("description").getValue().toString());
                tvRating.setText(ds.child("rating").getValue().toString());
            }
        }
    }

    public void onClickBook(){
        //add a thing in database for SP and user
        mRef.child("Users").child("Service Provider").child(spID).child("bookings").child(userID).child("name").setValue("time");
        mRef.child("Users").child("Service Provider").child(spID).child("bookings").child(userID).child("time").setValue("time");
        mRef.child("Users").child("Home Owner").child(userID).child("History").child(spID).child("time");
    }
}
