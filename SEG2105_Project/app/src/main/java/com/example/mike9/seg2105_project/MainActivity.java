package com.example.mike9.seg2105_project;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Patterns;
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
    EditText et1, et2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        et1 = findViewById(R.id.UsernameMain);
        et2 = findViewById(R.id.PasswordMain);
    }
    public String getUserEmail(){
        EditText et1 = (EditText)findViewById(R.id.UsernameMain);
        String email = et1.getText().toString();
        return email;
    }
    public String getUserPass(){
        EditText et2 = (EditText)findViewById(R.id.PasswordMain);
        String password = et2.getText().toString();
        return password;
    }
    public void openSignup(){
        Intent register = new Intent(getApplicationContext(), SignUp.class);
        startActivity(register);
    }
    public void openWelcomePage(){
        Intent login = new Intent(getApplicationContext(), WelcomeScreen.class);
        startActivity(login);
    }

    public void onClickLogin(View v){
        String email = getUserEmail();
        String password = getUserPass();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity.this, "Email/Password is empty", Toast.LENGTH_LONG).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                openWelcomePage();
                            } else {
                                Toast.makeText(MainActivity.this, "Authentication failed :(", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    public void onClickRegister(View v) {
        String email = getUserEmail();
        String password = getUserPass();

        if(email.isEmpty()){
            et1.setError("Enter an email");
            et1.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et1.setError("Enter a valid email");
            et1.requestFocus();
            return;
        }

        if(password.isEmpty()){
            et2.setError("Enter a password");
            et1.requestFocus();
            return;
        }

        


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                openSignup();
                            } else {
                                Toast.makeText(MainActivity.this, "Authentication failed :(", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

    }
}