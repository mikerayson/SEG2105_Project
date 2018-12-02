package com.example.mike9.seg2105_project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.internal.AccountType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.security.AccessController.getContext;

public class SignUp extends AppCompatActivity {
    //Firebase Stuff
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private String userID;

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    String accountType;
    private boolean licensed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Carries over the user sign in
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        userID = user.getUid();

        final LinearLayout serviceLayout = findViewById(R.id.comp_info);

        spinner = findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.account_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                accountType = parent.getItemAtPosition(position).toString();
                if(accountType.equals("Service Provider")){
                    serviceLayout.setVisibility(View.VISIBLE);
                } else{
                    serviceLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
    //Helper method for reaching welcome page
    private void openWelcomePage(){
        Intent openWelcome = new Intent(getApplicationContext(), WelcomeScreen.class);
        startActivity(openWelcome);
    }
    private void openSPWelcomePage(){
        Intent servicePIN = new Intent(getApplicationContext(), SPWelcomeScreen.class);
        startActivity(servicePIN);
    }

    public void onClickLicensed(View view){
        licensed = true;
    }
    public void onClickNotLicensed(View view){
        licensed = false;
    }

    public void onClickConfirm(View v){
        //Retrieves the first name and last name
        EditText et1 = findViewById(R.id.Firstname);
        EditText et2 = findViewById(R.id.Lastname);
        String firstName = et1.getText().toString();
        String lastName = et2.getText().toString();
        //Retrieves company information
        EditText mCompName = findViewById(R.id.comp_name);
        EditText mPhoneNum = findViewById(R.id.phone_num);
        EditText mAdress = findViewById(R.id.adress);
        EditText mDescription = findViewById(R.id.description);
        //Converts them to strings
        String CompName = mCompName.getText().toString();
        String PhoneNum = mPhoneNum.getText().toString();
        String Adress = mAdress.getText().toString();
        String Description = mDescription.getText().toString();

        if(firstName.isEmpty()){
            et1.setError("Enter your first name");
            et1.requestFocus();
            return;
        }

        if(lastName.isEmpty()){
            et2.setError("Enter your last name");
            et2.requestFocus();
            return;
        }
        //Adds the information to the database
        if (accountType.equals("Home Owner") ) {
            mRef = mRef.child("Users").child(accountType).child(userID);
            mRef.child("firstname").setValue(firstName);
            mRef.child("lastname").setValue(lastName);
        }
        else {
            //Need to add a check in for mandatory fields and validation
            if (accountType.equals("Service Provider")) {
                if (CompName.isEmpty()){
                    mCompName.setError("Enter a company name");
                    mCompName.requestFocus();
                    return;
                }
                if (Description.isEmpty()) {
                    mDescription.setError("Enter a description for your company");
                    mDescription.requestFocus();
                    return;
                }
                mRef = mRef.child("Users").child(accountType).child(userID);
                mRef.child("firstname").setValue(firstName);
                mRef.child("lastname").setValue(lastName);
                mRef.child("companyName").setValue(CompName);
                mRef.child("phoneNum").setValue(PhoneNum);
                mRef.child("adress").setValue(Adress);
                mRef.child("license").setValue(licensed);
                mRef.child("description").setValue(Description);
                mRef.child("Services").child("Service").setValue("List of Services");
            }
        }

        Toast.makeText(SignUp.this, "Account added", Toast.LENGTH_SHORT).show();
        if(accountType.equals("Service Provider")){
            openSPWelcomePage();                                                                           //Goes to welcome screen after registering
        }else{
            openWelcomePage();
        }
    }
}

