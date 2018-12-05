package com.example.mike9.seg2105_project;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class ServiceInfoPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private FirebaseUser user;
    private String userID;

    private ArrayList<String> array;
    private ListView mTimeSlots;
    private String compName;
    private TextView mCompName;
    private TextView mOwnerName;
    private TextView mAddress;
    private TextView mPhoneNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_info);

        //Carries over the user sign in
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        userID = user.getUid();

        //Carries over the information on what serviceprovders to display
        Intent nextPage = getIntent();
        Bundle b = nextPage.getExtras();

        if (b != null){
            compName = (String) b.get ("CompName");
        }

        //Sets up the UI
        mCompName = findViewById(R.id.CompanyName);
        mOwnerName = findViewById(R.id.OwnerName);
        mAddress = findViewById(R.id.Address);
        mPhoneNum = findViewById(R.id.Phone);
        mTimeSlots = findViewById(R.id.availabilities);

        //Retrieve Information for all the UI
        mRef.child("Users").child("Service Provider").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void showData(DataSnapshot dataSnapshot){
        //Sets up the textViews
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            if (ds.child("companyName").getValue().toString().equals(compName)) {
                mCompName.setText(compName);
                mAddress.setText(ds.child("adress").getValue().toString());
                String ownerName = ds.child("firstname").getValue().toString() + " " + ds.child("lastname").getValue().toString();
                mOwnerName.setText(ownerName);
                mPhoneNum.setText(ds.child("phoneNum").getValue().toString());
                //showTimeData(ds);
            }
        }
    }

    public void showTimeData(DataSnapshot dataSnapshot){
        array.clear();
        Timeslot newTimeSlot = new Timeslot();
        for(DataSnapshot ds : dataSnapshot.child("availability").getChildren()){
            newTimeSlot.setDay(ds.getKey());
            if(ds.child("Start Hour").getValue(Integer.class) != null){
                newTimeSlot.setStartHour(ds.child("Start Hour").getValue(Integer.class));
            }
            if(ds.child("End Hour").getValue(Integer.class) != null){
                newTimeSlot.setFinishHour(ds.child("End Hour").getValue(Integer.class));
            }
            array.add(newTimeSlot.toString());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
        mTimeSlots.setAdapter(adapter);
    }
}