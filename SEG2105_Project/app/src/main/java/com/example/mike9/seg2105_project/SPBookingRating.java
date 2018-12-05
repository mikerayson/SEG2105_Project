package com.example.mike9.seg2105_project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SPBookingRating extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;

    private ListView ratingList, bookingList;
    private String spID;
    private ArrayList<String> rArrayList, bArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_booking_rating);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        userID = user.getUid();

        ratingList = findViewById(R.id.list2);
        bookingList = findViewById(R.id.list1);
        rArrayList = new ArrayList<>();
        bArrayList = new ArrayList<>();

        //get intent extras
        /*

        mRef.child("Users").child("Service Provider").child(spID).child("bookings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //showBooking(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRef.child("Users").child("Service Provider").child(spID).child("ratings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //showRating(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */
    }

    public void showBooking(DataSnapshot dataSnapshot){
        //add new class to store booking
        bArrayList.clear();
        for(DataSnapshot ds : dataSnapshot.getChildren()){

        }
    }

    public void showRating(DataSnapshot dataSnapshot){
        //add new class to store rating
        rArrayList.clear();
        for(DataSnapshot ds: dataSnapshot.getChildren()){

        }
    }
}
