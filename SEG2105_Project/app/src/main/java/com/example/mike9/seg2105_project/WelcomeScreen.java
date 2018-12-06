package com.example.mike9.seg2105_project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

public class WelcomeScreen extends AppCompatActivity {

    //Firebase things
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;

    private String serviceName, serviceRate;
    private ListView serviceList;
    private ListView searchResult;
    private ListView timeResult;
    private ArrayList<String> array;
    private ArrayList<String> resultArray;
    private ArrayList<String> timeArray;
    private String service;

    private EditText searchBar;



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



        //UI for the service list
        serviceList = findViewById(R.id.user_list);
        searchResult = findViewById(R.id.search_result);
        timeResult = findViewById(R.id.time_result);
        searchBar = findViewById(R.id.searchBar);
        array = new ArrayList<>();
        resultArray = new ArrayList<>();
        timeArray = new ArrayList<>();

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                    setServiceAdapter(s.toString());
                    setTimeAdapter(s.toString());
                }
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
        timeResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = (String) timeResult.getItemAtPosition(position);

                //get service name
                String parts[] = value.split(",");
                service = parts[0];
                service.trim();

                Toast.makeText(WelcomeScreen.this, value, Toast.LENGTH_SHORT).show();
                Intent nextPage = new Intent(getApplicationContext(), BookSP.class);
                nextPage.putExtra("ServiceName", service);
                startActivity(nextPage);

            }
        });
        searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = (String) serviceList.getItemAtPosition(position);

                //get service name
                String parts[] = value.split(",");
                service = parts[0];
                service.trim();

                Toast.makeText(WelcomeScreen.this, value, Toast.LENGTH_SHORT).show();
                Intent nextPage = new Intent(getApplicationContext(), BookSP.class);
                nextPage.putExtra("ServiceName", service);
                startActivity(nextPage);

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
                Intent nextPage = new Intent(getApplicationContext(), BookSP.class);
                nextPage.putExtra("ServiceName", service);
                startActivity(nextPage);

            }
        });
    }




    //Put code to view active services here
    private void showServiceData(DataSnapshot dataSnapshot){
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

    private void showSearchResult(DataSnapshot dataSnapshot,String str){
        resultArray.clear();
        ServiceInformation sInfo = new ServiceInformation();
        for (DataSnapshot ds : dataSnapshot.getChildren()){
            String innerService = ds.getKey();
            String value = ds.getValue().toString();
            sInfo.setName(innerService);
            sInfo.setRate(value);



            if(innerService !=null){
                if(innerService.contains(str)){
                    resultArray.add(sInfo.toString());

                    System.out.println(sInfo.toString());
                }
            }
            if(value != null){
                if(value.contains(str)){
                    resultArray.add(sInfo.toString());

                    System.out.println(sInfo.toString());
                }
            }


        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, resultArray);
        searchResult.setAdapter(adapter);
    }
    private void showTimeSearchResult(DataSnapshot dataSnapshot,String str){
        timeArray.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren()){
            String currentProvider = ds.getKey();
            String companyName = ds.child("companyName").getValue().toString();
            DataSnapshot dataS = ds.child("availability");
            for(DataSnapshot ds2 : dataS.getChildren()){
                String day = ds2.getKey();
                String startHour = ds2.child("Start Hour").getValue().toString();
                String endHour = ds2.child("End Hour").getValue().toString();
                String result = companyName +": "+ day +" "+ startHour +"-"+ endHour;

                if(day != null){
                    if(day.contains(str)){
                        timeArray.add(result);
                    }
                }
                if(startHour != null){
                    if(startHour.contains(str)){
                        timeArray.add(result);
                    }
                }
                if(endHour != null){
                    if(endHour.contains(str)){
                        timeArray.add(result);
                    }
                }
            }

        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, timeArray);
        timeResult.setAdapter(adapter);

    }

    private void setServiceAdapter(final String str){
        mRef.child("Services").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showSearchResult(dataSnapshot,str);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void setTimeAdapter(final String str){
        mRef.child("Users").child("Service Provider").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showTimeSearchResult(dataSnapshot,str);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onClickServiceRate(View view){
        Intent i = new Intent(getApplicationContext(), UserRate.class);
        startActivity(i);
    }

}