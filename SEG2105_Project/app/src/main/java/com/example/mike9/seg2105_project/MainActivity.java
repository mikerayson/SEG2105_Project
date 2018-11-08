package com.example.mike9.seg2105_project;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }
    public String getUserEmail(){
        EditText et1 = (EditText)findViewById(R.id.Email);
        String email = et1.getText().toString();
        return email;
    }
    public String getUserPass(){
        EditText et2 = (EditText)findViewById(R.id.Password);
        String password = et2.getText().toString();
        return password;
    }

    public void onClickLogin(View v){
        String email = getUserEmail();
        String password = getUserPass();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent login = new Intent(getApplicationContext(), WelcomeScreen.class);
                            startActivity(login);
                        } else {
                            Toast.makeText(MainActivity.this, "Authentication failed :(", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void onClickRegister(View v){
        String email = getUserEmail();
        String password = getUserPass();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent register = new Intent(getApplicationContext(), SignUp.class);
                            startActivity(register);
                        } else {
                            Toast.makeText(MainActivity.this, "Authentication failed :(", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
