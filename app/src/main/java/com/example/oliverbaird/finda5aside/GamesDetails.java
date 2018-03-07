package com.example.oliverbaird.finda5aside;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import com.google.firebase.auth.FirebaseAuth;


public class GamesDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout menuDrawerLayout;
    private ActionBarDrawerToggle menuToggle;
    Button buttonCall;
    Button buttonText;
    Button buttonBook;
    Button buttonLocation;
    private static final int CALL_PERMISSION = 1;
    private static final int TEXT_PERMISSION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_details);


        menuDrawerLayout = findViewById(R.id.drawerMenu);
        menuToggle = new ActionBarDrawerToggle(GamesDetails.this, menuDrawerLayout, R.string.open, R.string.close);
        menuDrawerLayout.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        final TextView textViewDetailsNumber = findViewById(R.id.textViewDetailsNumber);


        TextView textViewDetailsLocation = findViewById(R.id.textViewDetailsLocation);
        TextView textViewDetailsCost = findViewById(R.id.textViewDetailsCost);
        TextView textViewDetailsSpaces = findViewById(R.id.textViewDetailsSpaces);
        TextView textViewDetailsDate = findViewById(R.id.textViewDetailsDate);
        TextView textViewDetailsSkill = findViewById(R.id.textViewDetailsSkill);
        TextView textViewDetailsName = findViewById(R.id.textViewDetailsName);


        Bundle detailBundle = getIntent().getExtras();

        if (detailBundle != null)

        {
            String locationDetail = detailBundle.getString("location");
            String costDetail = detailBundle.getString("cost");
            String spacesDetail = detailBundle.getString("spaces");
            String dateDetail = detailBundle.getString("date");
            String skillDetail = detailBundle.getString("skill");
            String numberDetail = detailBundle.getString("number");
            String nameDetail = detailBundle.getString("name");

            textViewDetailsLocation.setText(locationDetail);
            textViewDetailsCost.setText(costDetail);
            textViewDetailsSpaces.setText(spacesDetail);
            textViewDetailsDate.setText(dateDetail);
            textViewDetailsSkill.setText(skillDetail);
            textViewDetailsNumber.setText(numberDetail);
            textViewDetailsName.setText(nameDetail);
        }

        //click listener for the ability of a user to text about a game

        buttonText = findViewById(R.id.buttonText);

        buttonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                String phoneNumber = textViewDetailsNumber.getText().toString();

                if (!TextUtils.isEmpty(phoneNumber)) {
                    if (checkPermission(Manifest.permission.SEND_SMS)) {
                        String dial = "smsto:" + phoneNumber;
                        startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse(dial)));
                    } else {
                        Toast.makeText(GamesDetails.this, "Permission Text denied", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GamesDetails.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (checkPermission(Manifest.permission.SEND_SMS)) {
            buttonText.setEnabled(true);
        } else {
            buttonText.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, TEXT_PERMISSION);
        }


        //click listener for a user to find the location

        buttonLocation = findViewById(R.id.buttonLocation);

        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Toast.makeText(GamesDetails.this, "Find the pitch", Toast.LENGTH_SHORT).show();
            }
        });


        //click listener for user to be able to book a pitch

        buttonBook = findViewById(R.id.buttonBook);

        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Toast.makeText(GamesDetails.this, "Book a place", Toast.LENGTH_SHORT).show();
            }
        });

        //click listener which gives the user the ability call about a game

        buttonCall = findViewById(R.id.buttonCall);

        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {

                String phoneNumber = textViewDetailsNumber.getText().toString();

                if (!TextUtils.isEmpty(phoneNumber)) {
                    if (checkPermission(Manifest.permission.CALL_PHONE)) {
                        String dial = "tel:" + phoneNumber;
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                    } else {
                        Toast.makeText(GamesDetails.this, "Permission Call Phone denied", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GamesDetails.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CALL_PERMISSION:
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    buttonCall.setEnabled(true);
                    Toast.makeText(this, "You can call the number by clicking on the button", Toast.LENGTH_SHORT).show();
                }
                return;


        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(menuToggle.onOptionsItemSelected(item)){

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){

        int id = item.getItemId();

        if( id == R.id.home)
        {
            startActivity(new Intent(this, FindGame.class));
        }

        if( id == R.id.addgame)
        {
            startActivity(new Intent(this, CreateGame.class));
        }

        if( id == R.id.profile)
        {
            startActivity(new Intent(this, EditProfile.class));
        }

        if( id == R.id.settings)
        {
            Toast.makeText(this, "You have selected settings", Toast.LENGTH_SHORT).show();
        }

        if( id == R.id.logOut)
        {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "You have successfully signed out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        return false;
    }
}
