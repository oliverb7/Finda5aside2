package com.example.oliverbaird.finda5aside;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;
import java.util.List;


public class GamesDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //DECLARING

    private static final int m_PAYPAL_REQUEST_CODE = 7171;
    Intent m_service;
    PayPalConfiguration m_configuration;
    String amount = "";
    public static final String PAYPAL_CLIENT_ID = "AZKFiPvkx92lr65JU-ofrg4OlIC0lhvG42SNMzldvkuagGY0tSRZmXCNY0fN8obcyGBM8AWxRLoVI7if";

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
            dateDetail, skillDetail, nameDetail,timeDetail, reviewNumber;

    String upordownVote = " before? Leave a vote or downvote based on your experience";

    String haveYou = "Have you played with ";

    TextView textViewSpacesText;

    EditText editTextNameBook, editTextNumberBook;

    String userName, userNumber;

    Button buttonBook;

    ImageButton imageCall, imageLocation, imageQuestion, imageMessage, imageButtonArrowUp, imageButtonArrowDown;

    private TextView textViewDetailsLocation,textViewDetailsCost, textViewDetailsSpaces, textViewDetailsDate,
            textViewDetailsSkill,textViewDetailsName,textViewDetailsTime, textViewDetailsNumber, textViewReviewName, textViewReviewNumber;


    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        textViewReviewName = findViewById(R.id.textViewReviewName);
        textViewReviewNumber = findViewById(R.id.textViewReviewNumber);
        textViewSpacesText = findViewById(R.id.textViewSpacesText);

        buttonBook = findViewById(R.id.buttonBook);

        editTextNameBook = findViewById(R.id.editTextNameBook);
        editTextNumberBook = findViewById(R.id.editTextNumberBook);

        imageCall = findViewById(R.id.imageCall);
        imageLocation = findViewById(R.id.imageLocation);
        imageQuestion = findViewById(R.id.imageQuestion);
        imageMessage = findViewById(R.id.imageMessage);
        imageButtonArrowDown = findViewById(R.id.imageButtonArrowDown);
        imageButtonArrowUp = findViewById(R.id.imageButtonArrowUp);

        m_configuration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) //sandbox for test
                .clientId(PAYPAL_CLIENT_ID);

        //start PayPal Service
        m_service = new Intent (this, PayPalService.class);
        m_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,m_configuration);
        startService(m_service);



        //ensuring that the booking button is not clicked more than once
//        findViewById(R.id.buttonBook).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // mis-clicking prevention, using threshold of 1000 ms
//                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
//                    return;
//                }
//                mLastClickTime = SystemClock.elapsedRealtime();
//            }
//        });

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
            reviewNumber = detailBundle.getString("votes");

            textViewDetailsLocation.setText(locationDetail);
            textViewDetailsCost.setText(costDetail);
            textViewDetailsSpaces.setText(spacesDetail);
            textViewDetailsDate.setText(dateDetail);
            textViewDetailsSkill.setText(skillDetail);
            textViewDetailsNumber.setText(numberDetail);
            textViewDetailsName.setText(nameDetail);
            textViewDetailsTime.setText(timeDetail);
            textViewReviewNumber.setText(reviewNumber);


            textViewReviewName.setText(haveYou +
                    nameDetail + upordownVote);

        }

        //click listeners for finding location, texting, calling and booking.

        imageCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {

                buttonCallClick();
            }
        });

        imageMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {

                buttonTextClick();
            }
        });

        imageLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {

                buttonClickLocation();
            }
        });

        imageQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {

                startActivity(new Intent(GamesDetails.this, Information.class));
            }
        });


        imageButtonArrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {

                upVote();
            }
        });

        imageButtonArrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {

                downVote();
            }
        });

    }

    void pay(View view) {

        String name = editTextNameBook.getText().toString().trim();
        String number = editTextNumberBook.getText().toString().trim();

        final Bundle detailBundle = getIntent().getExtras();

        String spaces2 = detailBundle.getString("spaces");
        int spacesInt = Integer.parseInt(spaces2);

            if(name.isEmpty()){
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
            } else

            if(number.isEmpty()){
                Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
            } else

            if(spacesInt >= 1) {


                String costPay = detailBundle.getString("cost");

                int costPayPal = Integer.parseInt(costPay);

                PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(costPayPal), "GBP",
                        "Pay for football", PayPalPayment.PAYMENT_INTENT_SALE);

                Intent intent = new Intent(this, PaymentActivity.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
                startActivityForResult(intent, m_PAYPAL_REQUEST_CODE);

            } else if (spacesInt == 0){

                Toast.makeText(GamesDetails.this, "There are no spaces left for this game", Toast.LENGTH_SHORT).show();
            }


        }


    public void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == m_PAYPAL_REQUEST_CODE)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if(confirmation != null)
                {
                    String state = confirmation.getProofOfPayment().getState();

                    if (state.equals("approved")) {//if the payment worked, the state equals approved
                        Toast.makeText(this, "Payment Approved", Toast.LENGTH_SHORT).show();
                        buttonClickBook();
                    } else
                        Toast.makeText(this, "Payment Declined", Toast.LENGTH_SHORT).show();
                }

            }
        }
//        else Toast.makeText(this, "Payment Declined", Toast.LENGTH_SHORT).show();
    }

    private void upVote(){

            final Bundle detailBundle = getIntent().getExtras();

            String votes = detailBundle.getString("votes");
            int votesOverall = Integer.parseInt(votes);
            votesOverall++;
            String stringVotes = String.valueOf(votesOverall);
            textViewReviewNumber.setText(stringVotes);
            String id = detailBundle.getString("id");
            databaseGames.child(id).child("reviewNumber").setValue(stringVotes);
        }

    private void downVote(){

            final Bundle detailBundle = getIntent().getExtras();

            String votes = detailBundle.getString("votes");
            int votesOverall = Integer.parseInt(votes);
            votesOverall--;
            String stringVotes = String.valueOf(votesOverall);
            textViewReviewNumber.setText(stringVotes);
            String id = detailBundle.getString("id");
            databaseGames.child(id).child("reviewNumber").setValue(stringVotes);
    }

    public void buttonClickBook(){

            final Bundle detailBundle = getIntent().getExtras();

            String spaces2 = detailBundle.getString("spaces");
            int spacesInt = Integer.parseInt(spaces2);

            spacesInt--;
            Toast.makeText(GamesDetails.this, "You have successfully booked a place", Toast.LENGTH_SHORT).show();
            String spacesRemaining = String.valueOf(spacesInt);
            textViewDetailsSpaces.setText(spacesRemaining);
            String id = detailBundle.getString("id");
            databaseGames.child(id).child("gameSpaces").setValue(spacesRemaining);
            databaseGamesPrivate.child(id).child("gameSpaces").setValue(spacesRemaining);


            userName = editTextNameBook.getText().toString();
            userNumber = editTextNumberBook.getText().toString();

            textDetail = "Hi " + detailBundle.getString("name") + " my name is " + userName + " and I have just signed up to play at your game on "
                    + detailBundle.getString("date") + "." + " If there are any changes my phone number is " + userNumber + ".";

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

//            buttonBook.setEnabled(false);

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
            imageMessage.setEnabled(true);
        } else {
            imageMessage.setEnabled(false);
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
                    imageCall.setEnabled(true);
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

        if( id == R.id.information)
        {
            startActivity(new Intent(this, Information.class));
        }

        if( id == R.id.settings)
        {
            startActivity(new Intent(this, Settings.class));
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
