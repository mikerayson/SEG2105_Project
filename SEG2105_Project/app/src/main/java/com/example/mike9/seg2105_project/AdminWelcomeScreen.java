package com.example.mike9.seg2105_project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminWelcomeScreen extends AppCompatActivity {

    //Firebase things
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;

    private String serviceName, serviceRate;
    private ListView serviceList;
    private ListView userList;
    private ArrayList<String> serviceArray;
    private ArrayList<String> userArray;
    private String service;

    private Button buttonAddService;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome_screen);

        //Carries over the user sign in
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        userID = user.getUid();

        buttonAddService = findViewById(R.id.addService);

        //UI for the service list
        serviceList = findViewById(R.id.service_list);
        userList = findViewById(R.id.user_list);
        serviceArray = new ArrayList<>();
        userArray = new ArrayList<>();




        //Displays the greeting

        mRef.child("Users").child("Home Owner").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showUserData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Displays the services
        mRef.child("Services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showServiceData(dataSnapshot);
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

                Toast.makeText(AdminWelcomeScreen.this, value, Toast.LENGTH_SHORT).show();
                Intent nextPage = new Intent(getApplicationContext(), ServiceInfoPage.class);
                nextPage.putExtra(service, "ServiceName");
                startActivity(nextPage);

            }
        });
    }
    public void openAddService(){
        Intent nextPage = new Intent(getApplicationContext(), AddService.class);
        startActivity(nextPage);
    }
    public void onClickAddService(View view){
        openAddService();
    }

    //Put code to view active services here
    private void showServiceData (DataSnapshot dataSnapshot){
        serviceArray.clear();
        ServiceInformation sInfo = new ServiceInformation();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            sInfo.setName(ds.getKey());
            sInfo.setRate(ds.getValue().toString());
            serviceArray.add(sInfo.toString());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, serviceArray);
        serviceList.setAdapter(adapter);
    }
    private void showUserData(DataSnapshot dataSnapshot){
        userArray.clear();
        UserInformation user = new UserInformation();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            user.setFirstname(ds.getKey());
            user.setLastname(ds.getValue().toString());
            userArray.add(user.toString());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, userArray);
        userList.setAdapter(adapter);
    }

}
