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
        array = new ArrayList<>();

          
        //Displays the services
        mRef.child("Users").child("Service Provider").child(userID).child("Services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData2(dataSnapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void openAddServicePage(){
        Intent openAddPage = new Intent(SPWelcomeScreen.this, SPAddService.class);
        startActivity(openAddPage);
    }
    private void openDeleteServicePage(){
            Intent openDelPage = new Intent(SPWelcomeScreen.this, SPDeleteService.class);
            startActivity(openDelPage);
        }

    public void onClickAddServiceSP(View view){
        openAddServicePage();
    }
    public void onClickdeleteService(View view){
        openDeleteServicePage();
    }

    public void onClickAddTimeSlot(View view){
        Intent openTimeSlotPage = new Intent(this, addTimeSlot.class);
        startActivity(openTimeSlotPage);
    }

    private void showData2(DataSnapshot dataSnapshot){
        array.clear();
        ServiceInformation sInfo = new ServiceInformation();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            sInfo.setName(ds.getKey());
            sInfo.setRate(ds.getValue().toString());
            array.add(sInfo.toString());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
        serviceList.setAdapter(adapter);
    }
}
