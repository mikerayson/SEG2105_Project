package com.example.mike9.seg2105_project;

import android.app.Service;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.Iterator;

public class WelcomeScreen extends AppCompatActivity {

    //Firebase things
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;

    private String serviceName, serviceRate;
    private ListView serviceList;
    private ArrayList<String> array;
    private String service;

    private Button buttonAddService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        //Carries over the user sign in
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        userID = user.getUid();

        buttonAddService = findViewById(R.id.addService);

        //UI for the service list
        serviceList = findViewById(R.id.service_list);
        array = new ArrayList<>();

        //hides addService button if user isn't admin
        if(userID.equals("hjOUPe63AgRaulruxjlgayLzsr52")){
            buttonAddService.setVisibility(View.VISIBLE);
        } else {
            buttonAddService.setVisibility(View.GONE);
        }


        //Displays the greeting

        mRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //UI for Greeting
                TextView greeting = (TextView)findViewById(R.id.Greeting);
                UserInformation user = new UserInformation();
                /*user.setFirstname(dataSnapshot.child("Home Owner").child(userID).child("firstname").getValue().toString());
                user.setLastname(dataSnapshot.child("Home Owner").child(userID).child("lastname").getValue().toString());
                greeting.setText("Welcome " + user.getFirstname() + " " + user.getLastname());


                ***
                Since we need to declare the path to get the first and last name, we need to know whether or not the user
                is a Homeowner or a service provider. Thats why I think we should have a different welcome page for
                service providers.*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Displays the services
        mRef.child("Services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        serviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = (String) serviceList.getItemAtPosition(position);

                //get service name
                String parts[] = value.split(",");
                service = parts[0];
                service.trim();

                Toast.makeText(WelcomeScreen.this, value, Toast.LENGTH_SHORT).show();
                Intent nextPage = new Intent(getApplicationContext(), ServiceInfoPage.class);
                nextPage.putExtra(service, "ServiceName");
                startActivity(nextPage);

            }
        });
    }


    public void onClickAddService(View view){
        Intent nextPage = new Intent(getApplicationContext(), AddService.class);
        startActivity(nextPage);
    }

    //Put code to view active services here
    private void showData (DataSnapshot dataSnapshot){
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