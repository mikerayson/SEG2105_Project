package com.example.mike9.seg2105_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class SPAddService extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;

    private ListView serviceListSP;
    private ArrayList<String> array;
    private String serviceName;
    private String serviceRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_add_service);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        userID = user.getUid();

        serviceListSP = findViewById(R.id.serviceListSP);
        array = new ArrayList<>();

        mRef.child("Services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        serviceListSP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = (String) serviceListSP.getItemAtPosition(position);

                //get service name
                String parts[] = value.split(",");
                serviceName = parts[0];
                serviceRate = parts[1];
                serviceName.trim();
                serviceRate.trim();

                Toast.makeText(SPAddService.this, serviceName + " added", Toast.LENGTH_SHORT).show();
                mRef.child("Users").child("Service Provider").child(userID).child("Services").child(serviceName).setValue(serviceRate);
                openWelcomePage();
            }
        });
    }

    private void showData (DataSnapshot dataSnapshot){
        array.clear();
        ServiceInformation sInfo = new ServiceInformation();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            sInfo.setName(ds.getKey());
            sInfo.setRate(ds.getValue().toString());
            array.add(sInfo.toString());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
        serviceListSP.setAdapter(adapter);
    }

    public void onClickToDelete(View view){
        Intent deletePage = new Intent(SPAddService.this, SPDeleteService.class);
        startActivity(deletePage);
    }

    public void openWelcomePage(){
        startWelcomePage();
    }

    private void startWelcomePage(){
        Intent openWelcomepage = new Intent(SPAddService.this, SPWelcomeScreen.class);
        startActivity(openWelcomepage);
    }
}
