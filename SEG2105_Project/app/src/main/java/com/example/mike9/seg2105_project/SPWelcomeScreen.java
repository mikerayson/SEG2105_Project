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

    //Firebase dependencies
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;

    private String serviceName, serviceRate;
    private ListView serviceList;
    private ListView times;
    private ArrayList<String> arrayTimes;
    private ArrayList<String> arrayServices;

    private TextView textView;
    private Button addTime, addService, delService;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spwelcome_screen);

        textView = findViewById(R.id.textView2);

        //carries iver the user sign in
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        userID = user.getUid();

        serviceList = findViewById(R.id.serviceList);
        arrayServices = new ArrayList<>();
        arrayTimes = new ArrayList<>();
        times = findViewById(R.id.timeList);

        //displaying times
        mRef.child("Users").child("Service Provider").child(userID).child("availability").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showTime(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //displaying services
        mRef.child("Users").child("Service Provider").child(userID).child("Services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showService(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void showTime(DataSnapshot dataSnapshot){
        arrayTimes.clear();
        Timeslot timeslot = new Timeslot();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            timeslot.setDay(ds.child("day").getValue().toString());
            timeslot.setFinishHour(Integer.parseInt(ds.child("finishHour").getValue().toString()));
            timeslot.setStartHour(Integer.parseInt(ds.child("startHour").getValue().toString().toString()));
            arrayTimes.add(timeslot.toString());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayTimes);
        times.setAdapter(adapter);
    }

    public void showService(DataSnapshot dataSnapshot){
        arrayServices.clear();
        ServiceInformation  sInfo = new ServiceInformation();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            sInfo.setName(ds.getKey().toString());
            sInfo.setRate(ds.getValue().toString());
            arrayServices.add(sInfo.toString());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayServices);
        serviceList.setAdapter(adapter);
    }

    public void onClickDeleteService(View view){
        Intent i = new Intent(this, SPDeleteService.class);
        startActivity(i);
    }

    public void onClickAddTimeSlot(View view){
        Intent i = new Intent(this, addTimeSlot.class);
        startActivity(i);
    }

    public void onClickAddServiceSP(View view){
        Intent i = new Intent(this, SPAddService.class);
        startActivity(i);
    }
}