package com.example.oliverbaird.finda5aside;


import android.content.Intent;
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


/**
 * Created by oliverbaird on 29/01/2018.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;

    EditText editTextEmail, editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        findViewById(R.id.textViewSignUp).setOnClickListener(this);
        findViewById(R.id.buttonSignIn).setOnClickListener(this);

    }

    private void userLogin(){

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Please enter an email");
            editTextEmail.requestFocus();
            return;
        }

        //If the email does not match a valid email address


        if(password.isEmpty()){
            editTextPassword.setError("Please enter a password");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length()<5){
            editTextPassword.setError("Please enter a longer password");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //if the login is successful move to the next activity
                if(task.isSuccessful()){
                        Intent intent = new Intent(MainActivity.this, FindGame.class);
                        //clear all activities at the top of the stack
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick (View view)
    {
        switch(view.getId()){
            case R.id.textViewSignUp:

                startActivity(new Intent(this, SignUpActivity.class));

                break;

            case R.id.buttonSignIn:
                userLogin();
                break;

        }
    }
}