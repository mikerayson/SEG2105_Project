package com.example.mike9.seg2105_project;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static java.security.AccessController.getContext;

public class SignUp extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

    }

    public void onClickConfirm(View v){
        //Retrieves the email and password
        EditText et1 = (EditText)findViewById(R.id.Firstname);
        EditText et2 = (EditText)findViewById(R.id.Lastname);
        String firstName = et1.getText().toString();
        String lastName = et2.getText().toString();

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
        //Creates the user within firebase
        /*
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                } else {
                    Toast.makeText(getApplicationContext(), "Authentication Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        */
    }
}

