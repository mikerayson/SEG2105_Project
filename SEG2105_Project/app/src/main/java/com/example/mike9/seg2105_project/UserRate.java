package com.example.mike9.seg2105_project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class UserRate extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;

    private TextView spNameTv;
    private EditText etRating, etDescription;

    private ListView historyList;
    private ArrayList<String> array;
    private String spName;
    private String serviceId;
    private String serviceName;
    double totalRating = 0;
    double i = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rate);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        userID = user.getUid();

        spNameTv = findViewById(R.id.spName10);
        etRating = findViewById(R.id.rating);
        etDescription = findViewById(R.id.rDescription);
        historyList = findViewById(R.id.hList);
        array = new ArrayList<>();

        //final LinearLayout rateLayout = findViewById(R.id.rate);
        //rateLayout.setVisibility(View.GONE);


        mRef.child("Users").child("Home Owner").child(userID).child("History").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showHistory(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = (String) historyList.getItemAtPosition(position);
                String parts[] = value.split(",");
                serviceId = parts[0];
                serviceName = parts[1];
                serviceId.trim();
                serviceName.trim();
                spNameTv.setText(spName);
                //rateLayout.setVisibility(View.VISIBLE);
            }
        });



    }

    public void showHistory(DataSnapshot dataSnapshot){
        array.clear();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            String cName = ds.child("companyName").getValue().toString();
            String id = ds.getKey();
            array.add(id + ", " + cName);
        }
       // ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
        //historyList.setAdapter(adapter);
    }

    public void onClickSubmitRate(View view){
        Toast.makeText(this, "Opps", Toast.LENGTH_SHORT).show();
        /*
        int rating = Integer.parseInt(etRating.getText().toString());
        if((rating > 5) || (rating < 1)){
            etRating.setError("Please enter a rating from 1 - 5");
            etRating.requestFocus();
            return;
        }
        String description = etDescription.getText().toString();
        mRef.child("Users").child("Service Provider").child(serviceId).child("Rating").child(userID).child("rating").setValue(rating);
        mRef.child("Users").child("Service Provider").child(serviceId).child("Rating").child(userID).child("description").setValue(description);
        */
    }
}
