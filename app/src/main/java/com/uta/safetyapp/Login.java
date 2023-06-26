package com.uta.safetyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText l_email,l_password;
    Button loginButton;
    TextView createAccountButton;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuthentication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        l_email = findViewById(R.id.Email);
        l_password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        createAccountButton = findViewById(R.id.createButton);
        progressBar = findViewById(R.id.progressBar);
        firebaseAuthentication = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_email = l_email.getText().toString().trim();
                String s_password = l_password.getText().toString().trim();

                if(TextUtils.isEmpty(s_email)){
                    l_email.setError("Please enter a valid email address");
                    return;
                }
                if(TextUtils.isEmpty(s_password)){
                    l_password.setError("Enter a valid password");
                    return;
                }

                if(s_password.length() < 6) {
                    l_password.setError("Password needs to be atleast 6 characters long");
                    return;
                }

                progressBar.setVisibility(view.VISIBLE);

                firebaseAuthentication.signInWithEmailAndPassword(s_email,s_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Logged In!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else {
                            Toast.makeText(Login.this, "Error Occured!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignUp.class));
            }
        });




    }
}