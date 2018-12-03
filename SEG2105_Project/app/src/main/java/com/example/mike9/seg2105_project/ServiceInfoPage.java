package com.example.mike9.seg2105_project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServiceInfoPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private FirebaseUser user;
    private String userID;

    private ArrayList<String> timeList;

    TextView companyName;
    TextView ownerName;
    TextView address;
    TextView phoneNum;
    ListView timeSlots;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_info);

        //carries iver the user sign in
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        userID = user.getUid();

        //intialize the edit texts
        //to find the right user id
        companyName = findViewById(R.id.CompanyName);
        ownerName = findViewById(R.id.OwnerName);
        address = findViewById(R.id.Address);
        phoneNum = findViewById(R.id.Phone);
        timeSlots = findViewById(R.id.availabilities);
         timeList = new ArrayList<>();





        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showCompName(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showOwnerName(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showAddress(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showPhone(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showTimeSlots(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void showCompName(DataSnapshot dataSnapshot){


    }
    public void showOwnerName(DataSnapshot dataSnapshot){

    }
    public void showAddress(DataSnapshot dataSnapshot){

    }
    public void showPhone(DataSnapshot dataSnapshot){

    }
    public void showTimeSlots(DataSnapshot dataSnapshot){
        timeList.clear();
        Timeslot newTimeSlot = new Timeslot();
        for(DataSnapshot ds : dataSnapshot.child("Users").child("Service Provider").child(userID).child("availability").getChildren()){
            newTimeSlot.setDay(ds.getKey());

            //DEBUGGING INFO////TO BE DELETED///////////////////////
            System.out.println(ds.child("Start Hour").getValue());//
            System.out.println(ds.child("End Hour").getValue());  //
            ////////////////////////////////////////////////////////
            if(ds.child("Start Hour").getValue(Integer.class) != null){
                newTimeSlot.setStartHour(ds.child("Start Hour").getValue(Integer.class));
            }
            if(ds.child("End Hour").getValue(Integer.class) != null){
                newTimeSlot.setFinishHour(ds.child("End Hour").getValue(Integer.class));
            }
            timeList.add(newTimeSlot.toString());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, timeList);
        timeSlots.setAdapter(adapter);
    }

}
