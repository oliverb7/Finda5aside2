package com.example.oliverbaird.finda5aside;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class GamesDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout menuDrawerLayout;
    private ActionBarDrawerToggle menuToggle;

    private static final int CALL_PERMISSION = 1;
    private static final int TEXT_PERMISSION = 1;
    private long mLastClickTime = 0;

    private int notificationId;

    DatabaseReference databaseGames;
    DatabaseReference databaseGamesPrivate;
    FirebaseAuth mAuth;

    String numberDetail,textDetail, locationDetail, costDetail, spacesDetail,
            dateDetail, skillDetail, nameDetail,timeDetail;

    Button buttonCall, buttonText, buttonBook, buttonLocation;

    //DECLARING

    private TextView textViewDetailsLocation,textViewDetailsCost, textViewDetailsSpaces, textViewDetailsDate,
            textViewDetailsSkill,textViewDetailsName,textViewDetailsTime, textViewDetailsNumber;


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

        //INITIALISING

        textViewDetailsLocation = findViewById(R.id.textViewDetailsLocation);
        textViewDetailsCost = findViewById(R.id.textViewDetailsCost);
        textViewDetailsSpaces = findViewById(R.id.textViewDetailsSpaces);
        textViewDetailsDate = findViewById(R.id.textViewDetailsDate);
        textViewDetailsSkill = findViewById(R.id.textViewDetailsSkill);
        textViewDetailsName = findViewById(R.id.textViewDetailsName);
        textViewDetailsTime = findViewById(R.id.textViewDetailsTime);
        textViewDetailsNumber = findViewById(R.id.textViewDetailsNumber);

        buttonCall = findViewById(R.id.buttonCall);
        buttonLocation = findViewById(R.id.buttonLocation);
        buttonText = findViewById(R.id.buttonText);
        buttonBook = findViewById(R.id.buttonBook);

        //ensuring that the booking button is not clicked more than once
        findViewById(R.id.buttonBook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mis-clicking prevention, using threshold of 1000 ms
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        final String uid = mAuth.getCurrentUser().getUid();
        databaseGamesPrivate = FirebaseDatabase.getInstance().getReference("gamesPersonal").child(uid);
        databaseGames = FirebaseDatabase.getInstance().getReference("games");

        //Bundle used for passing data from previous activity
        final Bundle detailBundle = getIntent().getExtras();

        if (detailBundle != null)

        {
            locationDetail = detailBundle.getString("location");
            costDetail = detailBundle.getString("cost");
            spacesDetail = detailBundle.getString("spaces");
            dateDetail = detailBundle.getString("date");
            skillDetail = detailBundle.getString("skill");
            numberDetail = detailBundle.getString("number");
            nameDetail = detailBundle.getString("name");
            timeDetail = detailBundle.getString("time");
            textDetail = "Remember you have a 5aside on" + detailBundle.getString("date" ) + "at" +
                    detailBundle.getString("time") + detailBundle.getString("location");

            textViewDetailsLocation.setText(locationDetail);
            textViewDetailsCost.setText(costDetail);
            textViewDetailsSpaces.setText(spacesDetail);
            textViewDetailsDate.setText(dateDetail);
            textViewDetailsSkill.setText(skillDetail);
            textViewDetailsNumber.setText(numberDetail);
            textViewDetailsName.setText(nameDetail);
            textViewDetailsTime.setText(timeDetail);

        }

        //click listeners for finding location, texting, calling and booking.

        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {

                buttonCallClick();
            }
        });

        buttonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {

                buttonTextClick();
            }
        });

        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {

                buttonClickLocation();
            }
        });


        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {

                buttonClickBook();
            }
        });

    }

    private void buttonClickBook(){

        final Bundle detailBundle = getIntent().getExtras();

        String spaces2 = detailBundle.getString("spaces");
        int spacesInt = Integer.parseInt(spaces2);

        if(spacesInt >= 1) {

            spacesInt--;
            Toast.makeText(GamesDetails.this, "You have successfully booked a place", Toast.LENGTH_SHORT).show();
            String spacesRemaining = String.valueOf(spacesInt);
            textViewDetailsSpaces.setText(spacesRemaining);
            String id = detailBundle.getString("id");
            databaseGames.child(id).child("gameSpaces").setValue(spacesRemaining);
            databaseGamesPrivate.child(id).child("gameSpaces").setValue(spacesRemaining);
            buttonBook.setEnabled(false);

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numberDetail , null, textDetail, null, null);

            //creation of the notification for the user once they have booked into a game

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(GamesDetails.this)
                    .setSmallIcon(R.drawable.logologin)
                    .setContentTitle("FA5 Booking")

                    //setting a style of a lot of text

                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("Remember you have a game at " + detailBundle.getString("location" ) + " at " +
                                    detailBundle.getString("time") + " on " + detailBundle.getString("date")))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(GamesDetails.this);

            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(notificationId, mBuilder.build());


        } else if (spacesInt == 0){

            Toast.makeText(GamesDetails.this, "There are no spaces left for this game", Toast.LENGTH_SHORT).show();
        }

    }

    private void buttonClickLocation(){

        final Bundle detailBundle = getIntent().getExtras();

        Intent intent2 = new Intent(getApplicationContext(), Location.class);
        String location = detailBundle.getString("location");
        intent2.putExtra("location", location);
        startActivity(intent2);

    }

    private void buttonTextClick(){

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

        if (checkPermission(Manifest.permission.SEND_SMS)) {
            buttonText.setEnabled(true);
        } else {
            buttonText.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, TEXT_PERMISSION);
        }
    }

    private void buttonCallClick(){

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

        if( id == R.id.mygames)
        {
            startActivity(new Intent(this, MyGames.class));
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
