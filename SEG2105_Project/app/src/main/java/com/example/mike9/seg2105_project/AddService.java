package com.example.mike9.seg2105_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddService extends AppCompatActivity {

    //Firebase things
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        //Carries over the user sign in
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        userID = user.getUid();

    }
    public void onClickAddService(View view){
        //Check if user is admin
        String userId = user.getUid();
        if (userId.equals("hjOUPe63AgRaulruxjlgayLzsr52")) {
            //Get the information from the EditTexts
            EditText mServiceName = (EditText) findViewById(R.id.serviceName);
            EditText mServiceRate = (EditText) findViewById(R.id.serviceRate);
            String serviceName = mServiceName.getText().toString();
            String serviceRate = mServiceRate.getText().toString();

            if (!serviceName.isEmpty() && !serviceRate.isEmpty()) {
                mRef.child("Services").child(serviceName).setValue(serviceRate);
                Toast.makeText(AddService.this, "Change made", Toast.LENGTH_SHORT).show();
                mServiceName.setText("");
                mServiceRate.setText("");
            } else {
                Toast.makeText(AddService.this, "Text field blank", Toast.LENGTH_SHORT).show();
            }
        } else {
           Toast.makeText(AddService.this, "You do not have the required permission", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickDeleteService(View view){

        String userId = user.getUid();
        if(userId.equals("hjOUPe63AgRaulruxjlgayLzsr52")) {
            EditText mServiceName = (EditText) findViewById(R.id.serviceName);
            String serviceName = mServiceName.getText().toString();
            mRef.child("Services").child(serviceName).removeValue();
            Toast.makeText(AddService.this, "Service Deleted", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(AddService.this, "You do not have the required permission", Toast.LENGTH_SHORT).show();
        }
    }

  //  public void onClickEditService(View view){
  //      String userId = user.getUid();
  //  }
}
