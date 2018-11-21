package com.example.mike9.seg2105_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SPDeleteService extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;

    private Button deleteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_delete_service);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        userID = user.getUid();
    }

    public void onClickDeleteServiceSP(View view){
        EditText serviceNameET = findViewById(R.id.deleteServiceName);
        String serviceName = serviceNameET.toString();

        if(serviceName.isEmpty()){
            serviceNameET.setError("Enter Service Name");
            serviceNameET.requestFocus();
            return;
        } else{
            mRef.child("Users").child("Service Provider").child(userID).child("Services").child(serviceName).removeValue();
            Toast.makeText(this, serviceName + " deleted", Toast.LENGTH_SHORT).show();
        }
    }

}
