package com.example.mike9.seg2105_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addTimeSlot extends AppCompatActivity {

    //Firebase Stuff
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;

    Spinner spinnerday;
    Spinner spinnerStart;
    Spinner spinnerEnd;
    ArrayAdapter<CharSequence> adapterday;
    ArrayAdapter<CharSequence> adapterStart;
    ArrayAdapter<CharSequence> adapterEnd;
    String day;
    int startHour;
    int endHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time_slot);

        //Carries over the user sign in
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        userID = user.getUid();

        spinnerday = findViewById(R.id.day);
        adapterday = ArrayAdapter.createFromResource(this, R.array.days, android.R.layout.simple_spinner_item);
        adapterday.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerStart = findViewById(R.id.start);
        adapterStart = ArrayAdapter.createFromResource(this, R.array.start_hour, android.R.layout.simple_spinner_item);
        adapterStart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerEnd = findViewById(R.id.end);
        adapterEnd = ArrayAdapter.createFromResource(this, R.array.end_hour, android.R.layout.simple_spinner_item);
        adapterEnd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerday.setAdapter(adapterday);
        spinnerStart.setAdapter(adapterStart);
        spinnerEnd.setAdapter(adapterEnd);

        //Sets the day
        spinnerday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Sets the start hour
        spinnerStart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startHour = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //sets the end hour
        spinnerEnd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endHour = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    public void onClickConfirmTime(View view){
        //Checks if the end time is before the start time
        if (endHour < startHour){
            Toast.makeText(addTimeSlot.this, "Please choose a valid end time", Toast.LENGTH_SHORT).show();
        }
        else {
            Timeslot newTime = new Timeslot();
            newTime.setDay(day);
            newTime.setStartHour(startHour);
            newTime.setFinishHour(endHour);
            mRef = mRef.child("Users").child("Service Provider").child(userID)
                    .child("availability").child(newTime.getDay());
            mRef.child("Start Hour").setValue(newTime.getStartHour());
            mRef.child("End Hour").setValue(newTime.getFinishHour());
            Intent spMAIN = new Intent(addTimeSlot.this,SPWelcomeScreen.class);
            startActivity(spMAIN);
        }
    }
}
