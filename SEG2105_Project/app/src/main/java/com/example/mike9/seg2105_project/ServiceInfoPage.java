package com.example.mike9.seg2105_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ServiceInfoPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;

    TextView service;


    EditText et1, et2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_info);

        //service = findViewById(R.id.service);

        Bundle bundle = getIntent().getExtras();

        service.setText(bundle.getString("ServiceName"));
    }

}
