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
import android.widget.Button;
import android.widget.EditText;
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
    private ArrayList<String> array;
    private ArrayList<String> resultArray;
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
        searchBar = findViewById(R.id.searchBar);
        array = new ArrayList<>();
        resultArray = new ArrayList<>();

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                    setAdapter(s.toString());
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
                nextPage.putExtra(service, "ServiceName");
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
                nextPage.putExtra(service, "ServiceName");
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
            String service = ds.getKey();
            String value = ds.getValue().toString();
            sInfo.setName(service);
            sInfo.setRate(value);

         //   System.out.println(str);
         //   System.out.println(service);
         //   System.out.println(value);

            if(service !=null){
                if(service.contains(str)){

                    resultArray.add(sInfo.toString());
                }
            }

            if(value != null){
                if(value.contains(str)){
                    resultArray.add(sInfo.toString());
                }
            }


        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, resultArray);
        searchResult.setAdapter(adapter);
    }

    private void setAdapter(final String str){
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

}