package com.example.mike9.seg2105_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class ServiceInfoPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;
    private FirebaseUser user;
    private ArrayList<String> arrayID;

    private TextView serviceNameText;
    private String serviceName;
    private ListView spList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_info);

        //carries iver the user sign in
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        userID = user.getUid();

        serviceNameText = findViewById(R.id.service);
        spList = findViewById(R.id.sp_list);
        arrayID = new ArrayList<>();

        Intent intent = getIntent();
        serviceName = intent.getStringExtra("ServiceName");

        serviceNameText.setText(serviceName);

        //check which SP's have that service
        mRef.child("Users").child("Service Provider").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               getSPID(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        spList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String email = (String) spList.getItemAtPosition(position);

                Intent nextPage = new Intent(getApplicationContext(), BookSP.class);
                nextPage.putExtra("email", email);
                startActivity(nextPage);
            }
        });
    }

    public void getSPID(DataSnapshot dataSnapshot){
        arrayID.clear();
        //class to store data
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            if(ds.child("Services").child(serviceName).exists()){
                arrayID.add(ds.child("firstname").getValue().toString());
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayID);
        spList.setAdapter(adapter);
    }

}
