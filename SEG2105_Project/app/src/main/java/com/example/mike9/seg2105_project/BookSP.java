package com.example.mike9.seg2105_project;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookSP extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;
    private FirebaseUser user;

    private ListView sp_list;
    private ArrayList<String> array;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_sp);

        //carries iver the user sign in
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        userID = user.getUid();

        //Intent intent = getIntent();
        //spEmail = intent.getStringExtra("email");

        sp_list = findViewById(R.id.list_sp);
        array = new ArrayList<>();

        mRef.child("Users").child("Service Provider").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showdata(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void showdata(DataSnapshot dataSnapshot){
        array.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()){
            array.add(ds.child("companyName").getValue().toString());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
        sp_list.setAdapter(adapter);
    }


    /*public void onClickBook(View view){
        //add a thing in database for SP and user
        mRef.child("Users").child("Service Provider").child(spID).child("bookings").child(userID).child("name").setValue("time");
        mRef.child("Users").child("Service Provider").child(spID).child("bookings").child(userID).child("time").setValue("time");
        mRef.child("Users").child("Home Owner").child(userID).child("History").child(spID).child("time");
    }*/
}
