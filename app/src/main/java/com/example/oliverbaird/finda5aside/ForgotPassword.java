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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText editTextEmailForgot;
    private Button buttonResetPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmailForgot = findViewById(R.id.editTextEmailForgot);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);
        firebaseAuth = FirebaseAuth.getInstance();

        //the user will enter their email and if it is correct will be sent a password reset email

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail = editTextEmailForgot.getText().toString().trim();

                if (userEmail.equals("")){
                    Toast.makeText(ForgotPassword.this, "Please enter an email", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ForgotPassword.this,"Password reset email has been sent!", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(ForgotPassword.this,MainActivity.class));
                            } else {
                                Toast.makeText(ForgotPassword.this,"An error has occurred, please make sure your email is correct!", Toast.LENGTH_LONG).show();
                            }


                        }
                    });
                }

            }
        });
    }
}
