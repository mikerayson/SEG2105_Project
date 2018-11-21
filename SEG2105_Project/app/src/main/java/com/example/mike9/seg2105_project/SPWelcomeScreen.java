package com.example.mike9.seg2105_project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SPWelcomeScreen extends AppCompatActivity {

    //Firebase Dependencies
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;

    private String serviceName, serviceRate;
    private ListView serviceList;
    private ListView times;
    private ArrayList<String> arrayTimes;
    private ArrayList<String> array;

    private TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        txtView = (TextView) findViewById(R.id.textView2);

        //Carries over the user sign in
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        userID = user.getUid();

        serviceList = findViewById(R.id.service_list);
        array = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spwelcome_screen);

        //trying to display services provider has, needs to add service child in database
        mRef.child("Users").child("Service Provider").child(userID).child("availability").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void onClickAddServiceSP(View view){
        openAddServicePage();
    }

    private void openAddServicePage(){
        Intent openAddPage = new Intent(SPWelcomeScreen.this, SPAddService.class);
        startActivity(openAddPage);
    }

    public void onClickAddTimeSlot(View view){
        Intent openTimeSlotPage = new Intent(this, addTimeSlot.class);
        startActivity(openTimeSlotPage);
    }

    public void showData(DataSnapshot dataSnapshot){
        arrayTimes.clear();
        Timeslot newTimeSlot = new Timeslot();
        for(DataSnapshot ds : dataSnapshot.child("Users").child("Service Provider").child(userID).child("availability").getChildren()){
            newTimeSlot.setDay(ds.getValue(Timeslot.class).getDay());
            newTimeSlot.setStartHour(ds.getValue(Timeslot.class).getStartHour());
            newTimeSlot.setFinishHour(ds.getValue(Timeslot.class).getFinishHour());
            arrayTimes.add(newTimeSlot.toString());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayTimes);
        times.setAdapter(adapter);
    }
}