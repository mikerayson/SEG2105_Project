package com.example.mike9.seg2105_project;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    private String param;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_sp);

        //carries iver the user sign in
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        userID = user.getUid();

        //Carries over the information on what serviceprovders to display
        Intent nextPage = getIntent();
        Bundle b = nextPage.getExtras();

        if (b != null){
            param = (String) b.get ("ServiceName");
        }

        //Stuff for the booking list
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
        sp_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Gets the company name
                String companyName = (String) sp_list.getItemAtPosition(position);
                //Starts the booking intent and carries the info over
                Intent book = new Intent(getApplicationContext(), ServiceInfoPage.class);
                book.putExtra("CompName", companyName);
                startActivity(book);
            }


        });

    }
    public void showdata(DataSnapshot dataSnapshot){
        array.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()){
            if (ds.child("Services").hasChild(param)) {
                array.add(ds.child("companyName").getValue().toString());
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
        sp_list.setAdapter(adapter);
    }
}
