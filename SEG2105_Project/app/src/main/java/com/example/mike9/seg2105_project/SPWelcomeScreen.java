package com.example.mike9.seg2105_project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class SPWelcomeScreen extends AppCompatActivity {

    //Firebase Dependencies
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;

    private String serviceName, serviceRate;
    private ListView serviceList;
    private ListView timesList;
    private ArrayList<String> arrayTimes;
    private ArrayList<String> array;

    private TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spwelcome_screen);

        txtView = (TextView) findViewById(R.id.textView2);

        //Carries over the user sign in
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        userID = user.getUid();

        serviceList = findViewById(R.id.serviceList);
        timesList = findViewById(R.id.time_list);
        array = new ArrayList<>();
        arrayTimes = new ArrayList<>();

        //trying to display services provider has, needs to add service child in database
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              showTimeData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        mRef = mFirebaseDatabase.getReference();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showServiceData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });


    }
    private void openAddServicePage(){
        Intent openAddPage = new Intent(SPWelcomeScreen.this, SPAddService.class);
        startActivity(openAddPage);
    }

    public void onClickAddServiceSP(View view){
        openAddServicePage();
    }

    public void onClickAddTimeSlot(View view){
        Intent openTimeSlotPage = new Intent(this, addTimeSlot.class);
        startActivity(openTimeSlotPage);
    }

    public void showTimeData(DataSnapshot dataSnapshot){
        arrayTimes.clear();
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
            arrayTimes.add(newTimeSlot.toString());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayTimes);
        timesList.setAdapter(adapter);
    }

    public void showServiceData(DataSnapshot dataSnapshot){
        array.clear();
        ServiceInformation newService = new ServiceInformation();
        for(DataSnapshot ds : dataSnapshot.child("Users").child("Service Provider").child(userID).child("Services").getChildren()){
            newService.setName(ds.getKey());
            newService.setRate(ds.getValue(String.class));
            array.add(newService.toString());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
        serviceList.setAdapter(adapter);
    }
}