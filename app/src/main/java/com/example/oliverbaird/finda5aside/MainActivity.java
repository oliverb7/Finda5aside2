package com.example.oliverbaird.finda5aside;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;


/**
 * Created by oliverbaird on 29/01/2018.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    EditText editTextEmail, editTextPassword;
    TextView forgotPassword;
    private int counter = 5;
    Button buttonSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        forgotPassword = findViewById(R.id.forgotPassword);
        buttonSignIn = findViewById(R.id.buttonSignIn);

        findViewById(R.id.textViewSignUp).setOnClickListener(this);
        findViewById(R.id.buttonSignIn).setOnClickListener(this);

        //if the user is already logged in keep them signed in

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(this, FindGame.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ForgotPassword.class));
            }
        });

    }

    private void userLogin(){

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Please enter an email");
            editTextEmail.requestFocus();
            return;
        }

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
                //if the login is successful, check the email is verified
                if(task.isSuccessful()){
                        checkEmailVerification();
                } else{

                    Toast.makeText(MainActivity.this,"Login failed. Attempts remaining:" + counter, Toast.LENGTH_LONG).show();
                    counter--;
                    if (counter == 0){
                        buttonSignIn.setEnabled(false);
                    }
                }
            }
        });
    }

    private void checkEmailVerification(){
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        Boolean emailFlag = firebaseUser.isEmailVerified();

        //if the email is verified move to the home page

        if(emailFlag){
            startActivity(new Intent(MainActivity.this, FindGame.class));
        } else{
            Toast.makeText(MainActivity.this, "You must verify your email", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }
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